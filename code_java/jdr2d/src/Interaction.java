import java.util.concurrent.TimeUnit;

public class Interaction {
    protected Personnage joueur;
    protected PNJ opposant;

    protected Echange dialogue;

    protected boolean agressif;


    //getters


    public Personnage getJoueur() {
        return joueur;
    }

    public Personnage getOpposant() {
        return opposant;
    }

    public Echange getDialogue(){return dialogue;}

    public boolean getAgressif() {
        return agressif;
    }

    //setters


    public Interaction setJoueur(Personnage joueur) {
        this.joueur = joueur;
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
        if(this.getOpposant().getpV()<=0) return true;
        else return false;
    }

}
