package Control;

import Graphic.GameInterface;
import ServerPart.Client;
import ServerPart.MapState;

import java.io.IOException;

public class MapUpdater extends Thread{
    private GameInterface fenetre;

    private boolean running;
    public MapUpdater(GameInterface fenetre){
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
                fenetre.updatstate((MapState) ClientPart.getAutoupdatechannel().readObject());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
