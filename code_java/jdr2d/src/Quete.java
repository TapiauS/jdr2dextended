public class Quete {
    private String nomQuete;
    private String descriptionQuete;
    private String[] objectifs;
    private boolean[] validations;
    private Dialogue declencheur;

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

    //setters


    public void setNomQuete(String nomQuete) {
        this.nomQuete = nomQuete;
    }

    public void setDescriptionQuete(String descriptionQuete) {
        this.descriptionQuete = descriptionQuete;
    }

    public void setObjectifs(String[] objectifs) {
        this.objectifs = objectifs;
    }

    public void setDeclencheur(Dialogue declencheur) {
        this.declencheur = declencheur;
    }



    //methodes

    public void setValidations(boolean[] validations) {
        if(validations.length==this.objectifs.length) {
            this.validations = validations;
        }
        else{
            throw new IllegalArgumentException("Il doit y avoir autant de validations que d'objectifs !");
        }
    }
}
