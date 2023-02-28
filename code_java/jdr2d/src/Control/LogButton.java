package Control;

import DAO.UtilisateurDAO;
import Graphic.FullLogInterface;
import jdr2dcore.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
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
        try {
            util=UtilisateurDAO.connectcompte(pseudo,mdp);
        }
        catch (SQLException ex) {
            if(ex instanceof SQLDataException) {
                this.fenetre.setToplabel(new JLabel("Pseudo ou mot de passe incorrect, réessayez"));
                this.fenetre.refresh();
                return;
            }
            else {
                JOptionPane.showMessageDialog(null,"Une erreur de connexion inconnue c'est produite","Erreur de connexion",JOptionPane.ERROR_MESSAGE);
            }
        }
        this.fenetre.setUtil(util);
        GroupLayout group= (GroupLayout) this.fenetre.getContentPane().getLayout();
        Hashtable<String,Integer> refperso;
        try {
            refperso=UtilisateurDAO.displaypersonnage(util);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        if(!refperso.isEmpty()) {
            String[] data = refperso.keySet().toArray(new String[refperso.size()]);
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
