package Control;

import Graphic.FullLogInterface;
import Entity.Personnage;
import Entity.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ValidePictureChoiceAction extends AbstractAction {

    private FullLogInterface fenetre;

    private Utilisateur util;

    private Personnage personnage;

    private BufferedImage actualportraits;


    public ValidePictureChoiceAction(FullLogInterface fenetre, Utilisateur util, Personnage personnage, BufferedImage actualportraits, String message){
        super(message);
        this.fenetre=fenetre;
        this.util=util;
        this.personnage=personnage;
        this.actualportraits=actualportraits;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            (ClientPart.getServeroutput()).writeObject(ConnexionOutput.VALIDPICTURE);
            this.fenetre.setPerso(personnage);
        } catch (InterruptedException | IOException ex) {
            throw new RuntimeException(ex);
        }
        this.fenetre.setVisible(false);
    }

}
