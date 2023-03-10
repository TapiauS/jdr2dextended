package ServerPart.Control;

import Control.OutputType;
import ServerPart.GameZone;
import ServerPart.MapPool;
import ServerPart.ServerGameOutputType;
import jdr2dcore.PNJ;
import jdr2dcore.Personnage;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class IAProtocolServer extends Thread{

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

    public void figth(Personnage joueur, PNJ adversaire, GameZone zone) throws IOException {
        output.writeObject(ServerGameOutputType.PNJATK);
        output.writeObject(adversaire);
        output.reset();
        try {
            System.out.println("avant les PV");
            joueur.setpV((Integer) input.readObject());
            System.out.println("avant les premierPV");
            adversaire.setpV((Integer) input.readObject());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (joueur.getpV() <= 0)
            zone.getClient(joueur).setInteragit(true);
    }
}
