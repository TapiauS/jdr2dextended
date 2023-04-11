package Entity;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class Potion extends Objet {
    protected int[] effets;
    protected Duration duree;

    //getters


    public int[] getEffets() {
        return effets;
    }

    public Duration getDuree() {
        return duree;
    }

    //setters


    public Potion setEffets(int[] effets) {
        this.effets = effets;
        return this;
    }


    public Potion setDuree(Duration duree) {
        this.duree = duree;
        return this;
    }

    //Builders

    public Potion(){
        super();
        this.setEffets(new int[] {0}).setDuree(Duration.of(0, ChronoUnit.SECONDS));
    }

    public Potion(String nomObjet,int poid,int[] effets,Duration duree){
        super(nomObjet,poid);
        this.setEffets(effets)
                .setDuree(duree);
    }

    public Potion(int x, int y, Map lieux, String nomObjet, int poid, int[] effets, Duration duree){
        super(x,y,lieux,nomObjet,poid);
        this.setEffets(effets)
                .setDuree(duree);
    }



}
