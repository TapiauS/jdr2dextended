package ServerPart.Control;

import Control.OutputType;
import ServerPart.GameZone;
import ServerPart.MapPool;
import ServerPart.ServerGameOutputType;
import jdr2dcore.ObjectifK;
import jdr2dcore.PNJ;
import jdr2dcore.Personnage;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class IAProtocolServer{

    private Socket socket;

    private ObjectInputStream input;

    private ObjectOutputStream output;

    private InputStream in;

    private OutputStream out;


    public IAProtocolServer(Socket clientSocket) {
        this.socket=clientSocket;
        try {
            out=socket.getOutputStream();
            in=socket.getInputStream();
            input=new ObjectInputStream(in);
            output=new ObjectOutputStream(out);
            int idperso= (int) input.readObject();
            int idmap=(int) input.readObject();
            System.out.println("je m'active");
            Objects.requireNonNull(MapPool.getGameZone(idmap)).addAdress(idperso,this);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    //methode

    public void write(Object objet) throws IOException {
        output.writeObject(objet);
        output.reset();
    }

    public <T> T read() throws IOException, ClassNotFoundException {
        return (T) input.readObject();
    }

    public void figth(Personnage joueur, PNJ adversaire, GameZone zone) throws IOException {
        Thread t = new Thread(() -> {
            try {
                write(ServerGameOutputType.PNJATK);
                write(adversaire);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                System.out.println("avant les PV");
                joueur.setpV(read());
                System.out.println("avant les premierPV");
                adversaire.setpV(read());
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
            if (joueur.getpV() <= 0)
                zone.getClient(joueur).setInteragit(true);
            else{
                for (int i = 0; i < joueur.getQueteSuivie().size(); i++) {
                    for(int j=0;j<joueur.getQueteSuivie().get(i).getObjectifs().size();j++){
                        if (joueur.getQueteSuivie().get(i).getObjectifs().get(j) instanceof ObjectifK){
                            if ( ((ObjectifK) joueur.getQueteSuivie().get(i).getObjectifs().get(j)).getTarget().getId()==adversaire.getId()) {
                                joueur.getQueteSuivie().get(i).getObjectifs().get(j).update();
                                break;
                            }
                        }
                    }
                }
            }
        });
        t.start();
    }
}
