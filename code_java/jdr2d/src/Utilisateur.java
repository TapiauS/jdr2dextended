public class Utilisateur {
    private String nomUtilisateur;
    private String mdpUtilisateur;
    private String mailUtilisateur;
    private boolean Validation;

    //getters
    public String getNomUtilisateur(){
        return nomUtilisateur;
    }

    public String getMailUtilisateur() {
        return mailUtilisateur;
    }

    public boolean getValidation() {
        return Validation;
    }

    // setters

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur=nomUtilisateur;
    }

    public void setMdpUtilisateur(String mdpUtilisateur) {
        this.mdpUtilisateur=mdpUtilisateur;
    }

    public void setmailUtilisateur(String mailUtilisateur){
        this.mailUtilisateur=mailUtilisateur;
    }

    public void setValidation(boolean Validation){
        this.Validation=Validation;
    }

    //methodes

    public boolean verifiemdp(String mdptest){
        if(mdptest==this.mdpUtilisateur){
            return true;
        }
        return false;
    }
}
