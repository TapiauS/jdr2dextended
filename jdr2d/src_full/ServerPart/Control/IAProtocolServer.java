package ServerPart.Control;

import Control.OutputType;
import Log.LogLevel;
import Log.Loggy;
import ServerPart.GameZone;
import ServerPart.MapPool;
import ServerPart.ServerGameOutputType;
import jdr2dcore.ObjectifK;
import jdr2dcore.PNJ;
import jdr2dcore.Personnage;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class IAProtocolServer  implements JDRDSocket{

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
        Loggy.writlog("IAPROTOCOLSERVER WRITED"+objet,LogLevel.NOTICE);
        output.writeObject(objet);
        output.reset();
    }

    public <T> T read() throws IOException, ClassNotFoundException {
        return (T) input.readObject();
    }

    public void figth(Personnage joueur, PNJ adversaire, GameZone zone) throws IOException {
        IAProtocolServer me=this;
        Thread t = new Thread(() -> {
            try {
                write(ServerGameOutputType.PNJATK);
                boolean isinteract = read();
                System.out.println(isinteract);
                if (!isinteract) {
                    write(adversaire.getId());
                    write(adversaire.getNomPersonnage());
                    Interaction inter = new Interaction(joueur, adversaire, me);
                    inter.combat();
                    if (joueur.getpV() < 0)
                        PersoThread.respawn(joueur);
                    if (joueur.getpV() > 0)
                        PersoThread.respawn(adversaire);

                    if (joueur.getpV() <= 0)
                        zone.getClient(joueur).setInteragit(true);
                }
            }
            catch(IOException | ClassNotFoundException e){
                Loggy.writlog("ERREUR DE DECONNEXION SERVER" +e.getMessage(), LogLevel.DEBUG);
                throw new RuntimeException(e);
            }
        });
        t.start();
    }

}
