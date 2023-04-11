package Control;

import Graphic.FullLogInterface;
import Entity.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Hashtable;

public class CharSelectionAction extends AbstractAction {

    private FullLogInterface fenetre;

    private Utilisateur util;

    private Hashtable<String,Integer> refperso;

    public CharSelectionAction(String texte, Utilisateur util, FullLogInterface fenetre, Hashtable<String,Integer> refperso){
        super(texte);
        this.fenetre=fenetre;
        this.refperso=refperso;
        this.util=util;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            ClientPart.write(ConnexionOutput.PICKCHAR);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        if(!refperso.isEmpty()) {
            String[] data = refperso.keySet().toArray(new String[refperso.size()]);
            JList<String> lisperso = new JList<>(data);
            this.fenetre.setToptextfield(lisperso);
            this.fenetre.setToplabel(new JLabel("Choisissez un personnage ou creez en un nouveau"));
            this.fenetre.setTop(new JButton(new ValideCharSelAction(this.fenetre, util, "Validation", refperso)));
        }
        else{
            this.fenetre.getTop().setVisible(false);
            this.fenetre.getToplabel().setText("Aucun personnage pour ce compte, veuillez creer un personnage");
            this.fenetre.getToptextfield().setVisible(false);
        }
        this.fenetre.getBottommidlecomponent().setVisible(false);
        this.fenetre.getBottomlabel().setVisible(false);
        this.fenetre.setBottom(new JButton(new CreateCharAction("Creer un personnage",this.fenetre,util,refperso)));
        this.fenetre.refresh();
    }
}
