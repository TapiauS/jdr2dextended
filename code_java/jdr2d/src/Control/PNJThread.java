package Control;

import Graphic.GameInterface;
import jdr2dcore.Direction;
import jdr2dcore.PNJ;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

public class PNJThread extends Thread{

    private ArrayList<PNJ> pnjs;

    private Thread iaPnj;

    private boolean switchmap;

    private GameInterface fenetre;

    public static final long WALKSTEPDURATIONS =1;

    public static final long DEATHRESPAWNDELAYSEC =10;


    public PNJThread(ArrayList<PNJ> pnjs,GameInterface fenetre){
        super();
        this.setPnjs(pnjs);
        this.switchmap=true;
        this.fenetre=fenetre;
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

    //setters

    public void setPnjs(ArrayList<PNJ> pnjs) {
        this.pnjs = pnjs;
    }

    public void setIaPnj(Thread iaPnj) {
        this.iaPnj = iaPnj;
    }

    public void setSwitchmap(boolean switchmap) {
        this.switchmap = switchmap;
    }

    private void randommoove(PNJ perso) {
        Random rand = new Random();
        int nextdepl = rand.nextInt(4);
        switch (nextdepl) {
            case 0:
                perso.depl(Direction.NORD);
                break;
            case 1:
                perso.depl(Direction.EST);
                break;
            case 2:
                perso.depl(Direction.SUD);
            case 3:
                perso.depl(Direction.OUEST);
        }
    }




    public void ia(){
        for (PNJ p: pnjs) {
            if(p.getpV()>0&& Instant.now().isAfter(p.getNextmactiontime())){
                randommoove(p);
                p.setNextmactiontime(Instant.now().plus(WALKSTEPDURATIONS, ChronoUnit.SECONDS));
            }
            System.out.println("pnj pv="+p.getpV());
            if (p.getpV()<=0&&p.getRespawntime()==null){
                p.setRespawntime(Instant.now().plus(DEATHRESPAWNDELAYSEC,ChronoUnit.SECONDS));
            } else if (p.getpV()<=0&&Instant.now().isAfter(p.getRespawntime())) {
                p.setPvmax(20);
                p.setpV(15);
            }
        }
        fenetre.repaint();
        fenetre.revalidate();
    }

    public void run(){
        while (switchmap){
            ia();
        }
    }


}
