package Control;

import DAO.PersonnageDAO;
import Graphic.FullLogInterface;
import jdr2dcore.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Hashtable;

public class ValideCharSelButton extends AbstractAction {
    private FullLogInterface fenetre;

    private Utilisateur util;

    private Hashtable<String,Integer> refperso;

    //getters


    public Utilisateur getUtil() {
        return util;
    }

    public FullLogInterface getFenetre() {
        return fenetre;
    }

    public Hashtable<String, Integer> getRefperso() {
        return refperso;
    }

    //builder

    public ValideCharSelButton(FullLogInterface fenetre,Utilisateur util,String text,Hashtable<String,Integer> reperso){
        super(text);
        this.fenetre=fenetre;
        this.util=util;
        this.refperso=reperso;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JList list=(JList) this.fenetre.getToptextfield();
        String charname= (String) list.getSelectedValue();
        this.fenetre.setToplabel(new JLabel("Vous avez sélectionné "+charname));
        this.fenetre.getToptextfield().setVisible(false);
        this.fenetre.getBottomlabel().setVisible(false);
        this.fenetre.getBottom().setVisible(false);
        this.fenetre.getTop().setVisible(false);
        try {
            this.fenetre.setPerso(PersonnageDAO.getchar(refperso.get(charname)));
        } catch (SQLException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        this.fenetre.refresh();
    }
}
