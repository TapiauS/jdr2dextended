package Control;

import Graphic.FullLogInterface;
import jdr2dcore.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Hashtable;

public class CreateCharAction extends AbstractAction {

    private Utilisateur util;

    private FullLogInterface fenetre;

    private Hashtable<String, Integer> refperso;

    public CreateCharAction(String texte, FullLogInterface fenetre, Utilisateur util, Hashtable<String,Integer> refperso){
        super(texte);
        this.refperso=refperso;
        this.fenetre=fenetre;
        this.util=util;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(fenetre.getToptextfield() instanceof JList && ((JList) fenetre.getToptextfield()).getModel().getSize()<4) {
            this.fenetre.getToplabel().setText("Entrer un nom pour votre personnage");
            try {
                ClientPart.getServeroutput().writeObject(ConnexionOutput.CREATECHAR);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.fenetre.setToptextfield(new JTextField(10));
            this.fenetre.setTop(new JButton(new FirstCharAction(this.fenetre, "Valider", this.util)));
            this.fenetre.getBottomlabel().setVisible(false);
            this.fenetre.setBottom(new JButton(new CharSelectionAction("Choisir un personnage", this.util, this.fenetre, this.refperso)));
            this.fenetre.getBottommidlecomponent().setVisible(false);
            this.fenetre.refresh();
        }
        else {
            JOptionPane.showMessageDialog(null, "Vous ne pouvez pas avoir plus de 4 personnages");
        }
    }
}
