package Control;

import DAO.PersonnageDAO;
import Graphic.GameInterface;
import jdr2dcore.Direction;
import jdr2dcore.PNJ;
import jdr2dcore.Personnage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class PersoThread extends Thread{

    private ArrayList<PNJ> pnjs;

    private Thread iaPnj;

    private boolean switchmap;

    private static GameInterface  fenetre;

    public static final long WALKSTEPDURATIONSMS =500;

    public static final long DEATHRESPAWNDELAYMSEC =10000;


    public PersoThread(ArrayList<PNJ> pnjs, GameInterface fenetres){
        super();
        this.setPnjs(pnjs);
        this.switchmap=true;
        fenetre=fenetres;
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
    }

    public void run(){
        while (this.switchmap){
            ia();
        }
        System.out.println("Je sors !!!!!!!!!!!!");
    }

    public static void respawn(PNJ p) {
        p.setInteract(true);
        Thread t = new Thread(() -> {
            //super.run();
            for (int i = 0; i < DEATHRESPAWNDELAYMSEC ; i++) {
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
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

    public static void respawn(Personnage p){
        fenetre.setInteraction(true);
        Thread t = new Thread(() -> {
            //super.run();
            for (int i = 0; i < DEATHRESPAWNDELAYMSEC / 1000; i++) {
                fenetre.getEventHistory().addLine(String.valueOf(10-i));
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            p.setpV(p.getpVmax());
            fenetre.getFenetreInfo().update();
            fenetre.setInteraction(false);
        });
        t.start();
    }

}
