package jdr2dcore;

import java.io.Serializable;
import java.util.ArrayList;

public class Quete implements EventListenerO, Serializable {
    protected String nomQuete;
    protected String descriptionQuete;

    protected ArrayList<Objectifs> objectifs;
    /*protected jdr2dcore.Echange declencheur;*/
    protected Objet[] recompenses;

    protected ArrayList<EventListenerQuete> observer;

    protected int id;

    //getters


    public int getId() {
        return id;
    }

    public String getNomQuete() {
        return nomQuete;
    }

    public String getDescriptionQuete() {
        return descriptionQuete;
    }

    public ArrayList<Objectifs> getObjectifs() {
        return objectifs;
    }


    public Objet[] getRecompenses() {
        return recompenses;
    }

    //setters


    public Quete setNomQuete(String nomQuete) {
        this.nomQuete = nomQuete;
        return this;
    }

    public Quete setDescriptionQuete(String descriptionQuete) {
        this.descriptionQuete = descriptionQuete;
        return this;
    }

    public Quete setObjectifs(ArrayList<Objectifs> objectifs) {
        this.objectifs = objectifs;
        return this;
    }



    public Quete setRecompenses(Objet[] recompenses) {
            this.recompenses = recompenses;
            return this;
    }

    public Quete setObserver(ArrayList<EventListenerQuete> observer) {
        this.observer = observer;
        return this;
    }

    //builders


    public Quete setId(int id) {
        this.id = id;
        return this;
    }

    public Quete(){
        this.setNomQuete("r")
                .setDescriptionQuete("r")
                /*.setDeclencheur(new jdr2dcore.Echange())*/
                .setRecompenses(new Objet[]{new Objet()})
                .setObserver(new ArrayList<EventListenerQuete>());
    }

    public Quete(String nomQuete, String descriptionQuete, ArrayList<Objectifs> objectifs, Objet[] recompense){
        this.setNomQuete(nomQuete)
                .setDescriptionQuete(descriptionQuete)
                .setObjectifs(objectifs)
                .setRecompenses(recompense)
                .setObserver(new ArrayList<EventListenerQuete>());
    }

    //methodes


    public Quete addPersonnage(EventListenerQuete p){
        this.observer.add(p);
        return this;
    }

    public Quete removePersonnage(EventListenerQuete p){
        this.observer.remove(p);
        return this;
    }

    public void notifyAllp(){
        for (EventListenerQuete q: observer) {
            q.update(this);
        }
    }

    public Quete removeObjectif(Objectifs o){
        this.objectifs.remove(o);
        if(objectifs.size()<1){
            notifyAllp();
        }
        return this;
    }

    public Quete addObjectifs(Objectifs o){
        this.objectifs.add(o);
        o.addQuete(this);
        return this;
    }


    @Override
    public void update(Objectifs o) {
        if(this.getObjectifs().indexOf(o)==0) {
            this.removeObjectif(o);
        }
        else{
            o.setValide(false);
        }
    }

    //methodes

}
