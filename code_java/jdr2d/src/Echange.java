public class Echange extends Interaction{
    private String question;
    private String reponse;
    private Echange[] dialogueSuivant;
    private boolean quete;

    //getters

    public String getQuestion() {
        return question;
    }

    public String getReponse(){return reponse;}

    public Echange[] getDialogueSuivant() {
        return dialogueSuivant;
    }

    public boolean isQuete() {
        return quete;
    }

    //setters

    public Echange setquestion(String question) {
        this.question = question;
        return this;
    }

    public Echange setQuete(boolean quete) {
        this.quete = quete;
        return this;
    }

    public Echange setDialogueSuivant(Echange[] dialogueSuivant) {
        int compteur = 0;
        for (Echange e : dialogueSuivant) {
            if (e.getJoueur() == this.getJoueur() && e.getOpposant() == this.getOpposant()) {
                compteur = compteur + 1;
            }
        }
            if(compteur==dialogueSuivant.length) {
                this.dialogueSuivant = dialogueSuivant;
                return this;
            }
            else throw new IllegalArgumentException("Les dialogues suivants sont forcement entre les mêmes personnages");
        }


    public Echange setReponse(String reponse) {
        this.reponse = reponse;
        return this;
    }

    //builders

    public Echange(){
        super();
        Echange echansuiv = null;
        echansuiv.setquestion(null);
        echansuiv.setReponse(null);
        echansuiv.setJoueur(this.getJoueur());
        echansuiv.setOpposant(this.getOpposant());
        this.setquestion("Bonjour").setReponse("Bonjour").setDialogueSuivant(new Echange[]{echansuiv});
    }

    public Echange(Personnage joueur,Personnage opposant,String question,String reponse,Echange[] dialogueSuivant){
        super(joueur,opposant);
        this.setquestion(question)
                .setReponse(reponse)
                .setDialogueSuivant(dialogueSuivant);
    }

}
