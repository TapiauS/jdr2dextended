import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Interaction {
    protected Personnage joueur;
    protected PNJ opposant;

    protected Echange dialogue;

    protected boolean agressif;

    protected ArrayList<EventListenerK> observerK;


    //getters


    public Personnage getJoueur() {
        return joueur;
    }

    public PNJ getOpposant() {
        return opposant;
    }

    public Echange getDialogue(){return dialogue;}

    public boolean getAgressif() {
        return agressif;
    }

    public ArrayList<EventListenerK> getObserverK() {
        return observerK;
    }

    //setters


    public Interaction setJoueur(Personnage joueur) {
        this.joueur = joueur;
        for (Quete q:joueur.getQueteSuivie()) {
            for (Objectifs o: q.getObjectifs()) {
                if(o instanceof ObjectifK){
                    this.addObserverK((EventListenerK) o);
                }
            }
        }
        return this;
    }

    public Interaction setOpposant(PNJ opposant){
        this.opposant=opposant;
        return this;
    }

    public Interaction setDialogue(Echange dialogue) {
        if(dialogue.getQuestion()==null) {
            this.dialogue = dialogue;
            return this;
        }
        else throw new IllegalArgumentException("On doit avoir un dialogue de début de conversation");
    }

    public Interaction setAgressif(boolean agressif) {
        this.agressif = agressif;
        return this;
    }

    public Interaction setObserverK(ArrayList<EventListenerK> observerK) {
        this.observerK = observerK;
        return this;
    }


    //builders

    public Interaction(){
        this.setJoueur(null);
        this.setOpposant(null);
    }

    public Interaction(Personnage joueur,PNJ opposant) {
        this.setJoueur(joueur)
                .setOpposant(opposant);
    }

    public Interaction(Personnage joueur,PNJ opposant,Echange dialogue,boolean agressif){
        this.setJoueur(joueur)
            .setOpposant(opposant)
                .setDialogue(dialogue)
                .setAgressif(agressif);
    }

    //methode

    public void notifyOneobs(EventListenerK ev){
        ev.update(this.getOpposant());
    }

    public Interaction addObserverK(EventListenerK ev){
        this.observerK.add(ev);
        return this;
    }

    public Interaction removeObserK(EventListenerK ev){
        this.observerK.remove(ev);
        return this;
    }

    public Boolean combat(){
        while(this.getJoueur().getpV()>0 && this.getOpposant().getpV()>0){
            this.getOpposant().setpV(this.getOpposant().getpV()-this.getJoueur().bagarre(this.getOpposant()));
            this.getJoueur().setpV(this.getJoueur().getpV()-this.getOpposant().bagarre(this.getJoueur()));
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(this.getOpposant().getpV()<=0) {
            for (EventListenerK e: this.getObserverK()) {
                if (e instanceof ObjectifK){
                    this.notifyOneobs(e);
                    this.removeObserK(e);
                }
            }
            return true;
        }
        else return false;
    }

}
