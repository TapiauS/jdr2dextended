package ServerPart.Socketsmanager;

import Log.LogLevel;
import Log.Loggy;
import ServerPart.Socketsmanager.MapPool;
import ServerPart.Socketsmanager.MapState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AutoUpdater extends Thread implements JDRDSocket{

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
                idmap= read();
                MapState state= MapPool.getGameZone(idmap).getStatut();
                write(state);
                sleep(30);
            } catch (IOException | InterruptedException | ClassNotFoundException e) {
                Loggy.writlog("CONNEXION ENDED", LogLevel.NOTICE);
                throw new RuntimeException(e);
            }
            finally {
                try {
                    client.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void write(Object o) throws IOException {
        output.writeObject(o);
        output.reset();
    }

    @Override
    public <T> T read() throws IOException, ClassNotFoundException {
        return (T) input.readObject();
    }
}
