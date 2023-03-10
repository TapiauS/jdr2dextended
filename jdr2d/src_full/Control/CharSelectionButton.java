package Control;

import ServerPart.DAO.UtilisateurDAO;
import Graphic.FullLogInterface;
import jdr2dcore.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Hashtable;

public class CharSelectionButton extends AbstractAction {

    private FullLogInterface fenetre;

    private Utilisateur util;

    public CharSelectionButton(String texte,Utilisateur util,FullLogInterface fenetre){
        super(texte);
        this.fenetre=fenetre;
        this.util=util;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Hashtable<String,Integer> refperso;
        try {
            refperso= UtilisateurDAO.displaypersonnage(util);
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
