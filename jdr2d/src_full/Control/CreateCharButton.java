package Control;

import Graphic.FullLogInterface;
import jdr2dcore.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class CreateCharButton extends AbstractAction {

    private Utilisateur util;

    private FullLogInterface fenetre;

    public CreateCharButton(String texte,FullLogInterface fenetre,Utilisateur util){
        super(texte);
        this.fenetre=fenetre;
        this.util=util;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        this.fenetre.getToplabel().setText("Entrer un nom pour votre personnage");
        try {
            ClientPart.getServeroutput().writeObject(ConnexionOutput.CREATECHAR);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.fenetre.setToptextfield(new JTextField(10));
        this.fenetre.setTop(new JButton(new FirstCharButton(this.fenetre,"Valider",this.util)));
        this.fenetre.getBottomlabel().setVisible(false);
        this.fenetre.setBottom(new JButton(new CharSelectionButton("Choisir un personnage",this.util,this.fenetre)));
        this.fenetre.getBottomtextfield().setVisible(false);
        this.fenetre.refresh();
    }
}
