package jdr2dcore;

import Control.PNJThread;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

public class PNJ extends Personnage {
    protected boolean nomme;

    protected Point posinit;

    private Instant respawntime;
    private boolean interact;


    //getters


    public boolean isNomme() {
        return nomme;
    }


    public Instant getRespawntime() {
        return respawntime;
    }

    public boolean isnomme() {
        return nomme;
    }

    public Point getPosinit() {
        return posinit;
    }

    public boolean isInteract() {
        return interact;
    }


    //setters

    public void setInteract(boolean interact) {
        this.interact = interact;
    }

    public PNJ setnomme(boolean nomme) {
        this.nomme = nomme;
        return this;
    }

    public PNJ setposinit(Point posinit){
        this.posinit=posinit;
        return this;
    }

    public void setNomme(boolean nomme) {
        this.nomme = nomme;
    }

    public void setPosinit(Point posinit) {
        this.posinit = posinit;
    }

    public void setRespawntime(Instant respawntime) {
        this.respawntime = respawntime;
    }

    //builders

    public PNJ(){
        super();
        this.setnomme(false).setposinit(new Point());
    }

    public PNJ(ArrayList<Arme> arme, ArrayList<Armure> armure, String nomPersonnage, int pV, Coffre inventaire, int pVmax, ArrayList<Quete>  quetesuivie, Race race, Point posinit, Boolean nomme){
        super(arme, armure, nomPersonnage, pV, inventaire, pVmax, quetesuivie, race);
        this.setnomme(nomme);
    }

    public PNJ(int x, int y, Map lieux, ArrayList<Arme> arme, ArrayList<Armure> armure, String nomPersonnage, int pV, Coffre inventaire, int pVmax, Race race, Boolean nomme){
        super(x, y, lieux, arme, armure, nomPersonnage, pV, inventaire, pVmax,new ArrayList<>(), race);
        this.setnomme(nomme).setposinit(new Point(x,y,lieux));
        Random rand=new Random();
        this.interact=false;
        //int randomdelayms=rand.nextInt(100);.plus(randomdelayms,ChronoUnit.MILLIS)
        this.respawntime=null;
    }


    @Override
    public void depl(Direction dir) {
        super.depl(dir);
        if(this.distance(posinit)>5){
            super.depl(dir.opposite());
        }
    }
}
