package Control;

import Graphic.FullLogInterface;
import jdr2dcore.Personnage;
import jdr2dcore.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Hashtable;

public class ValideCharSelAction extends AbstractAction {
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

    public ValideCharSelAction(FullLogInterface fenetre, Utilisateur util, String text, Hashtable<String,Integer> reperso){
        super(text);
        this.fenetre=fenetre;
        this.util=util;
        this.refperso=reperso;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JList list=(JList) this.fenetre.getToptextfield();
        String charname= (String) list.getSelectedValue();
        Personnage perso;
        try {
            ClientPart.write(ConnexionOutput.PICKCHAR);
            ClientPart.write(charname);
            perso=ClientPart.read();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        this.fenetre.getToptextfield().setVisible(false);
        this.fenetre.getBottomlabel().setVisible(false);
        this.fenetre.getBottom().setVisible(false);
        this.fenetre.getTop().setVisible(false);
        try {
            this.fenetre.setPerso(perso);
        } catch ( InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        this.fenetre.setVisible(false);
        this.fenetre.refresh();
    }
}
