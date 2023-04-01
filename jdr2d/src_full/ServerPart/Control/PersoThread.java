package ServerPart.Control;

import Graphic.GameInterface;
import ServerPart.Socketsmanager.ServerMainChannel;
import jdr2dcore.PNJ;
import jdr2dcore.Personnage;

import java.util.ArrayList;

public class PersoThread extends Thread{

    private ArrayList<PNJ> pnjs;

    private Thread iaPnj;

    private boolean switchmap;

    private static GameInterface  fenetre;

    public static final long DEATHRESPAWNDELAYMSEC =10000;


    public PersoThread(ArrayList<PNJ> pnjs, GameInterface fenetres){
        super();
        this.setPnjs(pnjs);
        this.switchmap=true;
        fenetre=fenetres;
        this.start();
    }


    public PersoThread(GameZone zone){
        super();
        this.setPnjs(zone.getPnjs());
        this.start();
    }


    //getters

    public ArrayList<PNJ> getPnjs() {
        return pnjs;
    }

    public Thread getIaPnj() {
        return iaPnj;
    }

    public boolean isSwitchmap() {
        return switchmap;
    }



    public void setPnjs(ArrayList<PNJ> pnjs) {
        this.pnjs = pnjs;

    }

    public void setIaPnj(Thread iaPnj) {
        this.iaPnj = iaPnj;
    }

    public void setSwitchmap(boolean switchmap) {
        this.switchmap = switchmap;
    }

    public static void respawn(Personnage p){
        if (fenetre!=null)
            fenetre.setInteraction(true);
        Thread t = new Thread(() -> {
            //super.run();
            for (int i = 0; i < DEATHRESPAWNDELAYMSEC / 1000; i++) {
                if (fenetre!=null)
                    fenetre.getEventHistory().addLine(String.valueOf(10-i));
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            p.setpV(p.getpVmax());
            if(fenetre!=null) {
                fenetre.getFenetreInfo().update();
                fenetre.setInteraction(false);
            }
        });
        t.start();
    }

    public static void respawn(Personnage p, ServerMainChannel client){
        if (fenetre!=null)
            fenetre.setInteraction(true);
        Thread t = new Thread(() -> {
            //super.run();
            for (int i = 0; i < DEATHRESPAWNDELAYMSEC / 1000; i++) {
                if (fenetre!=null)
                    fenetre.getEventHistory().addLine(String.valueOf(10-i));
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            p.setpV(p.getpVmax());
            client.setInteragit(false);
        });
        t.start();
    }

}
