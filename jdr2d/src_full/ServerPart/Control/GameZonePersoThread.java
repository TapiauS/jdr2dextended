package ServerPart.Control;

import ServerPart.Socketsmanager.ServerMainChannel;
import ServerPart.DAO.*;
import Entity.Direction;
import Entity.PNJ;
import Entity.Personnage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class GameZonePersoThread extends Thread{
    private ArrayList<PNJ> pnjs;

    private Thread iaPnj;

    private boolean running;

    private GameZone gameZone;

    public static final long WALKSTEPDURATIONSMS =2000;

    public static final long DEATHRESPAWNDELAYMSEC =10000;


    public GameZonePersoThread(GameZone zone){
        this.gameZone=zone;
        this.pnjs=gameZone.getPnjs();
        System.out.println("pnj= "+this.pnjs.size());
        running=true;
        start();
    }



    private void randommoove(PNJ perso) throws IOException {
        Random rand = new Random();
        ArrayList<Personnage> closestsclients = new ArrayList<>();
        int closestdst = 4;
        for (Personnage player : gameZone.getJoueurs()) {
            ServerMainChannel client = gameZone.getClient(player);
            System.out.println("j'ai trouve quelqun");
            System.out.println("distance= "+player.distance(perso));
            if (player.distance(perso) < 1 && !perso.isNomme()) {
                gameZone.getChannel(player).figth(player,perso,gameZone);
                return;
            }
            if (!perso.isNomme() && perso.distance(player) < closestdst && !client.isInteragit()) {
                closestdst = perso.distance(player);
                closestsclients.add(player);
            }
        }
        for (Personnage player : closestsclients) {
            if (player.distance(perso) <= closestdst) {
                int moov = rand.nextInt(2);
                if (perso.getX() > player.getX() && perso.getY() > player.getY()) {
                    if (moov == 0) {
                        perso.depl(Direction.OUEST);
                    } else {
                        perso.depl(Direction.NORD);
                    }
                    return;
                }
                if (perso.getX() < player.getX() && perso.getY() > player.getY()) {
                    if (moov == 0) {
                        perso.depl(Direction.EST);
                    } else {
                        perso.depl(Direction.NORD);
                    }
                    return;
                }
                if (perso.getX() < player.getX() && perso.getY() < player.getY()) {
                    if (moov == 0) {
                        perso.depl(Direction.EST);
                    } else {
                        perso.depl(Direction.SUD);
                    }
                    return;
                }
                if (perso.getX() > player.getX() && perso.getY() < player.getY()) {
                    if (moov == 0) {
                        perso.depl(Direction.OUEST);
                    } else {
                        perso.depl(Direction.SUD);
                    }
                    return;
                }
                if (perso.getX() == player.getX() && perso.getY() > player.getY()) {
                    perso.depl(Direction.NORD);
                    return;
                }
                if (perso.getX() == player.getX() && perso.getY() < player.getY()) {
                    perso.depl(Direction.SUD);
                    return;
                }
                if (perso.getY() == player.getY() && perso.getX() > player.getX()) {
                    perso.depl(Direction.OUEST);
                    return;
                }
                if (perso.getY() == player.getY() && perso.getX() < player.getX()) {
                    perso.depl(Direction.EST);
                    return;
                }
            }
        }
        int nextdepl = rand.nextInt(4);
        switch (nextdepl) {
            case 0 -> perso.depl(Direction.NORD);
            case 1 -> perso.depl(Direction.EST);
            case 2 -> perso.depl(Direction.SUD);
            case 3 -> perso.depl(Direction.OUEST);
        }
    }



    public void ia() throws IOException {
        for (int i = 0; i < pnjs.size() ; i++) {
            PNJ p=pnjs.get(i);
            if(p.getpV()>0&&!p.isInteract()){
                randommoove(p);
                try {
                    sleep(WALKSTEPDURATIONSMS/pnjs.size());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(p.getpV()<=0&&!p.isInteract()) {
                respawn(p);
            }
        }
    }

    public void run(){
        while (running){
            try {
                ia();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
            } catch (DAOException e) {
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


}
