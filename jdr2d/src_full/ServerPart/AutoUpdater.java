package ServerPart;

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
            System.out.println("j'ai avancé");
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
                idmap=input.readInt();
                output.writeObject(MapPool.getGameZone(idmap).getStatut());
                sleep(30);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
