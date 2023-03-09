package Control;

import Graphic.GameInterface;
import ServerPart.AutoUpdateChannel;
import ServerPart.MapState;

import java.io.IOException;

public class MapUpdaterProtocol extends Thread{
    private GameInterface fenetre;

    private boolean running;
    public MapUpdaterProtocol(GameInterface fenetre){
        this.fenetre=fenetre;
        this.running=true;
        start();
    }


    //setter


    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        super.run();
        while (this.running) {
            try {
                System.out.println("je tourne");
                AutoUpdateSocket.getServeroutput().writeInt(fenetre.getCarte().getId());
                MapState ref= (MapState) AutoUpdateSocket.getServerinput().readObject();
                fenetre.updatstate(ref);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
