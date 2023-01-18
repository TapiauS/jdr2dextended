import java.sql.*;
import java.util.Hashtable;

public class Utilisateur {
    private int id;
    private String nomUtilisateur;
    private String mdpUtilisateur;
    private String mailUtilisateur;
    private boolean Validation;

    //getters
    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public String getMailUtilisateur() {
        return mailUtilisateur;
    }

    public boolean getValidation() {
        return Validation;
    }

    public int getId() {
        return id;
    }

    // setters

    public Utilisateur setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
        return this;
    }

    public Utilisateur setMdpUtilisateur(String mdpUtilisateur) {
        this.mdpUtilisateur = mdpUtilisateur;
        return this;
    }

    public Utilisateur setmailUtilisateur(String mailUtilisateur) {
        this.mailUtilisateur = mailUtilisateur;
        return this;
    }

    public Utilisateur setValidation(boolean Validation) {
        this.Validation = Validation;
        return this;
    }

    public Utilisateur setId(int id) {
        this.id = id;
        return this;
    }

    //methodes

    public boolean verifiemdp(String mdptest) {
        if (mdptest == this.mdpUtilisateur) {
            return true;
        }
        return false;
    }

    public Utilisateur() {
        this.setmailUtilisateur("");
        this.setMdpUtilisateur("");
        this.setNomUtilisateur("");
        this.setValidation(false);
    }

    public Utilisateur(String mailUtilisateur, String mdpUtilisateur, String nomUtilisateur, boolean validation,int id) {
        this.setmailUtilisateur(mailUtilisateur);
        this.setValidation(validation);
        this.setNomUtilisateur(nomUtilisateur);
        this.setMdpUtilisateur(mailUtilisateur);
        this.setId(id);
    }

    //methodes



}
