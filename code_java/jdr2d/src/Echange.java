public class Echange {

    protected PNJ parleur;
    protected String question;
    protected String reponse;
    protected Echange[] dialogueSuivant;

    protected boolean donnequete;
    protected Quete quete;

    //getters

    public String getQuestion() {
        return question;
    }

    public String getReponse(){return reponse;}

    public Echange[] getDialogueSuivant() {
        return dialogueSuivant;
    }

    public boolean isQuete() {
        return donnequete;
    }

    public PNJ getParleur() {
        return parleur;
    }

    public Quete getQuete() {
        return quete;
    }

    //setters

    public Echange setquestion(String question) {
        this.question = question;
        return this;
    }

    public Echange setdonneQuete(boolean donnequete) {
        this.donnequete = donnequete;
        return this;
    }

    public Echange setReponse(String reponse) {
        this.reponse = reponse;
        return this;
    }

    public Echange setParleur(PNJ parleur){
        this.parleur=parleur;
        return this;
    }

    public Echange setDialogueSuivant(Echange[] dialogueSuivant){
        this.dialogueSuivant=dialogueSuivant;
        return this;
    }

    public Echange setQuete(Quete quete) {
        if(this.isQuete()) {
            this.quete = quete;
            return this;
        }
        else {
            System.err.println("Ce dialogue ne donne pas de quete");
            return this;
        }
    }

    //builders

    public Echange(){
        Echange echansuiv = null;
        this.setquestion("Bonjour").setReponse("Bonjour").setDialogueSuivant(new Echange[]{echansuiv});
    }

    public Echange(PNJ parleur,String question,String reponse,Echange[] dialogueSuivant){
        this.setquestion(question)
                .setReponse(reponse)
                .setDialogueSuivant(dialogueSuivant)
                .setParleur(parleur);
    }

    public Echange(PNJ parleur,String question,String reponse,Echange[] dialogueSuivant,boolean donnequete,Quete quete){
        this.setquestion(question)
                .setReponse(reponse)
                .setDialogueSuivant(dialogueSuivant)
                .setdonneQuete(donnequete)
                .setQuete(quete)
                .setParleur(parleur);
    }

    //fonctions

    public void dialogue(){
        int i=0;
        if(this.getDialogueSuivant()!=null) {
            for (Echange e : this.getDialogueSuivant()) {
                System.out.println(i + ": " + e.getQuestion());
                i++;
            }
        }
        else {System.out.println("Fin de la conversation");}
    }

}
