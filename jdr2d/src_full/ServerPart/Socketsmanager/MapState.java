package ServerPart.Socketsmanager;

import ServerPart.Control.GameZone;
import jdr2dcore.*;

import java.io.Serializable;
import java.util.ArrayList;

public class MapState implements Serializable {
    private Map carte;

    private ArrayList<PNJ> pnjs;

    private ArrayList<Personnage> joueurs;

    private ArrayList<Coffre> coffres;

    //private ArrayList<Echange> echanges;

    //private ArrayList<Porte> sorties;

    public MapState(GameZone zone){
        this.carte=zone.getCarte();
        this.pnjs=zone.getPnjs();
        this.joueurs=zone.getJoueurs();
        this.coffres=zone.getCoffres();
    }

    //getter


    public Map getCarte() {
        return carte;
    }

    public ArrayList<PNJ> getPnjs() {
        return pnjs;
    }

    public ArrayList<Personnage> getJoueurs() {
        return joueurs;
    }

    public ArrayList<Coffre> getCoffres() {
        return coffres;
    }

    //setters


    public void setCarte(Map carte) {
        this.carte = carte;
    }

    public void setPnjs(ArrayList<PNJ> pnjs) {
        this.pnjs = pnjs;
    }

    public void setJoueurs(ArrayList<Personnage> joueurs) {
        this.joueurs = joueurs;
    }

    public void setCoffres(ArrayList<Coffre> coffres) {
        this.coffres = coffres;
    }
}
