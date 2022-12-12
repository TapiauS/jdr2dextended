public class Quete {
    private String nomQuete;
    private String descriptionQuete;
    private String[] objectifs;
    private Boolean[] validations;

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

    public Boolean[] getValidations() {
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

    public void setValidations(Boolean[] validations) {
        if(validations.length==this.objectifs.length) {
            this.validations = validations;
        }
        else{
            throw new IllegalArgumentException("Il doit y avoir autant de validations que d'objectifs !");
        }
    }
}
