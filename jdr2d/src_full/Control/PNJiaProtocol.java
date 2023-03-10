package Control;

import Graphic.GameInterface;
import ServerPart.Control.Interaction;
import ServerPart.Control.PersoThread;
import ServerPart.ServerGameOutputType;
import jdr2dcore.PNJ;

import java.io.IOException;

public class PNJiaProtocol extends Thread{

    private GameInterface fenetre;

    private boolean running;

    public PNJiaProtocol(GameInterface fenetre){
        this.fenetre=fenetre;
        this.running=true;
        this.start();
    }

    //setters
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        super.run();
        while (running){
            try {
                System.out.println("ca bloque :");
                ServerGameOutputType action= PNJIASocket.read();
                System.out.println("ici ?");
                if(action==ServerGameOutputType.PNJATK){
                    PNJ adversaire=PNJIASocket.read();
                    Interaction inter=new Interaction(fenetre.getPlayer(),adversaire,fenetre);
                    inter.combat();
                    PNJIASocket.write(fenetre.getPlayer().getpV());
                    PNJIASocket.write(adversaire.getpV());
                    if (fenetre.getPlayer().getpV()<=0)
                        PersoThread.respawn(fenetre.getPlayer());
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
