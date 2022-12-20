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

}
