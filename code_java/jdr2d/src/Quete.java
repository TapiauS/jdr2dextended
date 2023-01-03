public class Quete {
    protected String nomQuete;
    protected String descriptionQuete;
    protected Objectifs[] objectifs;
    /*protected Echange declencheur;*/
    protected Objet[] recompenses;

    //getters


    public String getNomQuete() {
        return nomQuete;
    }

    public String getDescriptionQuete() {
        return descriptionQuete;
    }

    public Objectifs[] getObjectifs() {
        return objectifs;
    }


   /* public Echange getDeclencheur() {
        return declencheur;
    }*/

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

    public Quete setObjectifs(Objectifs[] objectifs) {
        this.objectifs = objectifs;
        return this;
    }

   /* public Quete setDeclencheur(Echange declencheur) {
        this.declencheur = declencheur;
        return this;
    }
    */


    public Quete setRecompenses(Objet[] recompenses) {
            this.recompenses = recompenses;
            return this;
    }

    //builders

    public Quete(){
        this.setNomQuete("r")
                .setDescriptionQuete("r")
                .setObjectifs(new Objectifs[] {new ObjectifT()})
                /*.setDeclencheur(new Echange())*/
                .setRecompenses(new Objet[]{new Objet()});

    }

    public Quete(String nomQuete,String descriptionQuete,Objectifs[] objectifs,Objet[] recompense){
        this.setNomQuete(nomQuete)
                .setDescriptionQuete(descriptionQuete)
                .setObjectifs(objectifs)
               /* .setDeclencheur(declencheur)*/
                .setRecompenses(recompense);
    }

    //methodes


}
