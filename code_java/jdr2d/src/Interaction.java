import java.util.concurrent.TimeUnit;

public class Interaction {
    protected Personnage joueur;
    protected Personnage opposant;

    //getters


    public Personnage getJoueur() {
        return joueur;
    }

    public Personnage getOpposant() {
        return opposant;
    }

    //setters


    public Interaction setJoueur(Personnage joueur) {
        this.joueur = joueur;
        return this;
    }

    public Interaction setOpposant(Personnage opposant){
        this.opposant=opposant;
        return this;
    }

    //builders

    public Interaction(){
        this.setJoueur(null);
        this.setOpposant(null);
    }

    public Interaction(Personnage joueur,Personnage opposant){
        this.setJoueur(joueur)
            .setOpposant(opposant);
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
