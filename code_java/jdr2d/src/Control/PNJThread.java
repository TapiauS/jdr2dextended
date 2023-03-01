package Control;

import DAO.PersonnageDAO;
import Graphic.GameInterface;
import jdr2dcore.Direction;
import jdr2dcore.PNJ;
import jdr2dcore.Personnage;

import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

public class PNJThread extends Thread{

    private ArrayList<PNJ> pnjs;

    private Thread iaPnj;

    private boolean switchmap;

    private GameInterface fenetre;

    public static final long WALKSTEPDURATIONSMS =500;

    public static final long DEATHRESPAWNDELAYMSEC =10000;


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
            if(p.getpV()>0&&!p.isInteract()){
                randommoove(p);
                try {
                    System.out.println("j'attend");
                    sleep(WALKSTEPDURATIONSMS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                fenetre.repaint();
                fenetre.revalidate();
            }
            else if(p.getpV()<=0&&!p.isInteract()) {
                respawn(p);
                fenetre.repaint();
                fenetre.revalidate();
            }
        }
        //System.out.println("ou suis je ???");
    }

    public void run(){
        while (this.switchmap){
            ia();
        }
        System.out.println("Je sors !!!!!!!!!!!!");
    }

    public void respawn(PNJ p) {
        p.setInteract(true);
        Thread t = new Thread(() -> {
            //super.run();
            for (int i = 0; i < DEATHRESPAWNDELAYMSEC ; i++) {
                if(isSwitchmap()) {
                    try {
                        sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    p.setpV(p.getpVmax());
                    try {
                        PersonnageDAO.updatepnj(p);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    p.setInteract(false);
                    return;
                }
            }
            p.setpV(p.getpVmax());
            try {
                PersonnageDAO.updatepnj(p);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            p.setInteract(false);
        });
        t.start();
    }

}
