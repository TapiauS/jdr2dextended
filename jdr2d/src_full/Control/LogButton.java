package Control;

import DAO.UtilisateurDAO;
import Graphic.FullLogInterface;
import jdr2dcore.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Hashtable;

public class LogButton extends AbstractAction {

    private FullLogInterface fenetre;
    public LogButton(FullLogInterface fenetre,String texte){
        super(texte);
        this.fenetre=fenetre;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField textField= (JTextField) fenetre.getToptextfield();
        String pseudo=textField.getText();
        String mdp=this.fenetre.getBottomtextfield().getText();
        Utilisateur util = new Utilisateur();
        boolean success;
        try {
            ClientPart.getServeroutput().writeObject(ConnexionInput.CONNEXION);
            ClientPart.getServeroutput().writeObject(pseudo);
            ClientPart.getServeroutput().writeObject(mdp);
            success= (boolean) ClientPart.getServerinput().readObject();
        } catch (IOException | ClassNotFoundException ex) {
        throw new RuntimeException(ex);
        }
        if(!success){
            this.fenetre.setToplabel(new JLabel("Pseudo ou mot de passe incorrect, réessayez"));
            this.fenetre.refresh();
            return;
        }
        try {
            util= (Utilisateur) ClientPart.getServerinput().readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        this.fenetre.setUtil(util);
        Hashtable<String,Integer> refperso;
        try {
            refperso= (Hashtable<String, Integer>) ClientPart.getServerinput().readObject();
        } catch ( IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        if(!refperso.isEmpty()) {
            String[] data = refperso.keySet().toArray(new String[0]);
            JList<String> lisperso = new JList<>(data);
            this.fenetre.setToptextfield(lisperso);
            this.fenetre.setToplabel(new JLabel("Choisissez un personnage ou creez en un nouveau"));
            this.fenetre.setTop(new JButton(new ValideCharSelButton(this.fenetre, util, "Validation", refperso)));
        }
        else{
            this.fenetre.getTop().setVisible(false);
            this.fenetre.getToplabel().setText("Aucun personnage pour ce compte, veuillez creer un personnage");
            this.fenetre.getToptextfield().setVisible(false);
        }
        this.fenetre.getBottomtextfield().setVisible(false);
        this.fenetre.getBottomlabel().setVisible(false);
        this.fenetre.setBottom(new JButton(new CreateCharButton("Creer un personnage",this.fenetre,util)));
        this.fenetre.refresh();
    }
}
