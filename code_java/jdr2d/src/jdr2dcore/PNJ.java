package jdr2dcore;

import Control.PNJThread;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class PNJ extends Personnage {
    protected boolean nomme;

    protected Point posinit;

    private Instant nextmactiontime;

    private Instant respawntime;

    //getters


    public boolean isNomme() {
        return nomme;
    }

    public Instant getNextmactiontime() {
        return nextmactiontime;
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

    //setters

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

    public void setNextmactiontime(Instant nextmactiontime) {
        this.nextmactiontime = nextmactiontime;
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
        this.nextmactiontime=Instant.now().plus(PNJThread.WALKSTEPDURATIONS, ChronoUnit.SECONDS);
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
