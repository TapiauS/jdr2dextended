package Control;

import Graphic.GameInterface;
import ServerPart.ServerGameOutputType;
import jdr2dcore.PNJ;

import java.io.IOException;

public class PNJiaProtocol extends Thread{

    private GameInterface fenetre;

    private boolean running;

    public PNJiaProtocol(GameInterface fenetre){
        this.fenetre=fenetre;
        this.start();
    }

    //setters
/*
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        super.run();
        while (running){
            try {
                ServerGameOutputType action= (ServerGameOutputType) ClientPart.getInteractioniainputchannel().readObject();
                if(action==ServerGameOutputType.PNJATK){
                    PNJ adversaire= (PNJ) ClientPart.getInteractioniainputchannel().readObject();
                    Interaction inter=new Interaction(fenetre.getPlayer(),adversaire,fenetre);
                    System.out.println("c'est la bagarre");
                    inter.combat();
                    ClientPart.getInteractioniaoutputchannel().writeInt(fenetre.getPlayer().getpV());
                    ClientPart.getInteractioniaoutputchannel().writeInt(adversaire.getpV());
                    if (fenetre.getPlayer().getpV()<=0)
                        PersoThread.respawn(fenetre.getPlayer());
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    */

}
