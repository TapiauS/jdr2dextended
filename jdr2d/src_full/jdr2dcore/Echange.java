package jdr2dcore;

import java.io.Serializable;

public class Echange implements Serializable {

    protected PNJ parleur;
    protected String question;
    protected String reponse;
    protected Echange[] dialogueSuivant;
    protected boolean donnequete;

    protected Quete quete;
    protected boolean objectifquete;

    protected ObjectifT objectifT;
    protected Echange dialoguealternatif;

    protected int id;

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

    public Echange getDialoguealternatif() {
        return dialoguealternatif;
    }

    public boolean isObjectifquete() {
        return objectifquete;
    }

    public ObjectifT getObjectifT() {
        return objectifT;
    }

    public int getId() {
        return id;
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
            return this;
        }
    }

    public Echange setObjectifs(Boolean b){
        if(b&&this.dialoguealternatif==null){
                this.setdialoguealternatif(new Echange(this.getParleur(),null,"Vous n'avez pas encore cette quete,au revoir",null));
        }
        this.objectifquete=b;
        return this;
    }

    public Echange setdialoguealternatif(Echange e){
        this.dialoguealternatif=e;
        return this;
    }

    public Echange setObjectifsT(ObjectifT objectifT){
        this.objectifT=objectifT;
        return this;
    }

    public Echange setId(int id){
        this.id=id;
        return this;
    }

    //builders

    public Echange(){
        Echange echansuiv = null;
        this.setquestion("Bonjour").setReponse("Bonjour").setDialogueSuivant(new Echange[]{echansuiv});
    }

    public Echange(PNJ parleur, String question, String reponse, Echange[] dialogueSuivant){
        this.setquestion(question)
                .setReponse(reponse)
                .setDialogueSuivant(dialogueSuivant)
                .setParleur(parleur)
                .setdonneQuete(false)
        .setObjectifs(false);
    }

    public Echange(PNJ parleur, String question, String reponse, Echange[] dialogueSuivant, boolean donnequete, Quete quete){
        this.setquestion(question)
                .setReponse(reponse)
                .setDialogueSuivant(dialogueSuivant)
                .setdonneQuete(donnequete)
                .setQuete(quete)
                .setParleur(parleur)
                .setObjectifs(false);
    }

    public Echange(PNJ parleur, String question, String reponse, Echange[] dialogueSuivant, boolean donnequete, Quete quete, Echange dialoguealternatif,ObjectifT o){
        this.setquestion(question)
                .setReponse(reponse)
                .setDialogueSuivant(dialogueSuivant)
                .setdonneQuete(donnequete)
                .setQuete(quete)
                .setParleur(parleur)
                .setdialoguealternatif(dialoguealternatif)
                .setObjectifs(true)
                .setObjectifsT(o);


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
