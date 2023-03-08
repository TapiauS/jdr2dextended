package ServerPart;

import Control.Interaction;
import DAO.PersonnageDAO;
import Graphic.GameInterface;
import ServerPart.GameZone;
import jdr2dcore.Direction;
import jdr2dcore.PNJ;
import jdr2dcore.Personnage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class GameZonePersoThread extends Thread{
    private ArrayList<PNJ> pnjs;

    private Thread iaPnj;

    private boolean switchmap;

    private GameZone gameZone;

    public static final long WALKSTEPDURATIONSMS =2000;

    public static final long DEATHRESPAWNDELAYMSEC =10000;


    public GameZonePersoThread(GameZone zone){
        this.gameZone=zone;
        this.pnjs=gameZone.getPnjs();
    }


    private void randommoove(PNJ perso) throws IOException {
        Random rand = new Random();
        for (Personnage player: gameZone.getJoueurs()) {
            if(player.distance(perso)<1&&!perso.isNomme()){
                Client client=gameZone.getClient(player);
                client.getInteractionoutput().writeObject(ServerGameOutputType.PNJATK);
                if(client.getInteractioninput().readBoolean());
                {
                    Interaction inter = new Interaction(player, perso);
                    inter.combat();
                }
        }
        /*
        if(!perso.isNomme()&&perso.distance(player)<4&&!fenetre.isInteraction()){
            int moov=rand.nextInt(2);
            if(perso.getX()>player.getX()&&perso.getY()>player.getY()){
                if(moov==0){
                    perso.depl(Direction.OUEST);
                }
                else {
                    perso.depl(Direction.NORD);
                }
                return;
            }
            if(perso.getX()<player.getX()&&perso.getY()>player.getY()){
                if(moov==0){
                    perso.depl(Direction.EST);
                }
                else {
                    perso.depl(Direction.NORD);
                }
                return;
            }
            if(perso.getX()<player.getX()&&perso.getY()<player.getY()){
                if(moov==0){
                    perso.depl(Direction.EST);
                }
                else {
                    perso.depl(Direction.SUD);
                }
                return;
            }
            if(perso.getX()>player.getX()&&perso.getY()<player.getY()){
                if(moov==0){
                    perso.depl(Direction.OUEST);
                }
                else {
                    perso.depl(Direction.SUD);
                }
                return;
            }
            if(perso.getX()== player.getX()&&perso.getY()>player.getY()) {
                perso.depl(Direction.NORD);
                return;
            }
            if (perso.getX()==player.getX()&&perso.getY()<player.getY()) {
                perso.depl(Direction.SUD);
                return;
            }
            if(perso.getY()==player.getY()&&perso.getX()>player.getX()) {
                perso.depl(Direction.OUEST);
                return;
            }
            if(perso.getY()==player.getY()&&perso.getX()<player.getX()){
                perso.depl(Direction.EST);
            }
        }
        else{
            int nextdepl = rand.nextInt(4);
            switch (nextdepl) {
                case 0 -> perso.depl(Direction.NORD);
                case 1 -> perso.depl(Direction.EST);
                case 2 -> perso.depl(Direction.SUD);
                case 3 -> perso.depl(Direction.OUEST);
            }*/
        }

    }



/*
    public void ia(){
        for (int i = 0; i < pnjs.size() ; i++) {
            PNJ p=pnjs.get(i);
            if(p.getpV()>0&&!p.isInteract()){
                randommoove(p);
                try {
                    sleep(WALKSTEPDURATIONSMS/pnjs.size());
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
            this.setPnjs(fenetre.getPnjs());
            ia();
        }
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
        //fenetre.setInteraction(true);
        Thread t = new Thread(() -> {
            //super.run();
            for (int i = 0; i < DEATHRESPAWNDELAYMSEC / 1000; i++) {
                //fenetre.getEventHistory().addLine(String.valueOf(10-i));
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            p.setpV(p.getpVmax());
            //fenetre.getFenetreInfo().update();
            //fenetre.setInteraction(false);
        });
        t.start();
    }
*/
}
