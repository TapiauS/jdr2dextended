package ServerPart.Control;

import ServerPart.Socketsmanager.MapPool;
import ServerPart.Socketsmanager.MapState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AutoUpdater extends Thread{

    private final Socket client;

    private ObjectOutputStream output;

    private ObjectInputStream input;

    private int idmap;

    public AutoUpdater( Socket client){
        this.client=client;
        try {
            input=new ObjectInputStream(client.getInputStream());
            output=new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.start();
    }

    @Override
    public void run() {
        super.run();
        while (client.isConnected()) {
            try {
                idmap= (int) input.readObject();
                MapState state= MapPool.getGameZone(idmap).getStatut();
                output.writeObject(state);
                output.reset();
                sleep(30);
            } catch (IOException | InterruptedException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
