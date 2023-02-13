package Control;

import DAO.PersonnageDAO;
import Graphic.FullLogInterface;
import jdr2dcore.Personnage;
import jdr2dcore.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class FirstCharButton extends AbstractAction {

    private Utilisateur util;

    private FullLogInterface fenetre;

    //getters


    public Utilisateur getUtil() {
        return util;
    }

    public FullLogInterface getFenetre() {
        return fenetre;
    }

    //builders

    public FirstCharButton(FullLogInterface fenetre, String texte, Utilisateur util){
        super(texte);
        this.fenetre=fenetre;
        this.util=util;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField textField= (JTextField) fenetre.getToptextfield();
        String charname=textField.getText();
        if(!charname.isEmpty()) {
            boolean validation;
            Personnage perso;
            try {
                validation = PersonnageDAO.checkcharname(charname);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if (validation) {
                try {
                    perso = PersonnageDAO.getchar(PersonnageDAO.createchar(charname, util));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    this.fenetre.setPerso(perso);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                this.fenetre.dispatchEvent(new WindowEvent(this.fenetre, WindowEvent.WINDOW_CLOSING));
            } else {
                this.fenetre.setToplabel(new JLabel("Personnage non disponible, choisir un autre nom"));
                this.fenetre.refresh();
            }
        }
    }
}
