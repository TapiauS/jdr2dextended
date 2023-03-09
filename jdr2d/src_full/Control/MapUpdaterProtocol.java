package Control;

import Graphic.GameInterface;
import ServerPart.AutoUpdateChannel;
import ServerPart.MapState;
import jdr2dcore.PNJ;

import java.io.IOException;
import java.util.ArrayList;

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
                AutoUpdateSocket.getServeroutput().writeObject(fenetre.getCarte().getId());
                System.out.println("je passe le write");
                MapState ref= (MapState) AutoUpdateSocket.getServerinput().readObject();
                System.out.println("x = "+ref.getPnjs().get(0).getX()+"y= "+ref.getPnjs().get(0).getY());
                fenetre.updatstate(ref);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
