public class Quete {
    private String nomQuete;
    private String descriptionQuete;
    private String[] objectifs;
    private boolean[] validations;
    private Echange declencheur;

    protected Objet[] recompenses;

    //getters


    public String getNomQuete() {
        return nomQuete;
    }

    public String getDescriptionQuete() {
        return descriptionQuete;
    }

    public String[] getObjectifs() {
        return objectifs;
    }

    public boolean[] getValidations() {
        return validations;
    }

    public Echange getDeclencheur() {
        return declencheur;
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

    public Quete setObjectifs(String[] objectifs) {
        this.objectifs = objectifs;
        return this;
    }

    public Quete setDeclencheur(Echange declencheur) {
        this.declencheur = declencheur;
        return this;
    }

    public Quete setValidations(boolean[] validations) {
        if(validations.length==this.objectifs.length) {
            this.validations = validations;
            return this;
        }
        else{
            throw new IllegalArgumentException("Il doit y avoir autant de validations que d'objectifs !");
        }
    }

    public Quete setRecompenses(Objet[] recompenses) {
        if(recompenses.length==this.objectifs.length) {
            this.recompenses = recompenses;
            return this;
        }
        else{
            throw new IllegalArgumentException("Il doit y avoir autant de validations que d'objectifs !");
        }
    }

    //builders

    public Quete(){
        this.setNomQuete("r")
                .setDescriptionQuete("r")
                .setObjectifs(new String[]{"r"})
                .setValidations(new boolean[]{false})
                .setDeclencheur(new Echange())
                .setRecompenses(new Objet[]{new Objet()});

    }

    public Quete(String nomQuete,String descriptionQuete,String[] objectifs,Echange declencheur,boolean[] validations,Objet[] recompense){
        this.setNomQuete(nomQuete)
                .setDescriptionQuete(descriptionQuete)
                .setValidations(validations)
                .setObjectifs(objectifs)
                .setDeclencheur(declencheur)
                .setRecompenses(recompense);
    }

    //methodes


}
