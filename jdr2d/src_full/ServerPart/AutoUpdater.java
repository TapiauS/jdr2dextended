package ServerPart;

import Control.ClientPart;

import java.io.IOException;

public class AutoUpdater extends Thread{

    private Client client;

    public AutoUpdater(Client client){
        this.client=client;
        this.start();
    }

    @Override
    public void run() {
        super.run();
        while (client.isConnected()) {
            try {
                client.getAutooutputstream().writeObject(client.getMap().getStatut());
                sleep(30);
                System.out.println("je passe bien ici");
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
