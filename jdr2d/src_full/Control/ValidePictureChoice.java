package Control;

import DAO.DAOObject;
import Graphic.FullLogInterface;
import jdr2dcore.Personnage;
import jdr2dcore.Potion;
import jdr2dcore.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ValidePictureChoice extends AbstractAction {

    private FullLogInterface fenetre;

    private Utilisateur util;

    private Personnage personnage;

    private BufferedImage actualportraits;


    public ValidePictureChoice(FullLogInterface fenetre, Utilisateur util, Personnage personnage,BufferedImage actualportraits, String message){
        super(message);
        this.fenetre=fenetre;
        this.util=util;
        this.personnage=personnage;
        this.actualportraits=actualportraits;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            (ClientPart.getServeroutput()).writeObject(ConnexionInput.VALIDPICTURE);
            this.fenetre.setPerso(personnage);
        } catch (InterruptedException | IOException ex) {
            throw new RuntimeException(ex);
        }
        this.fenetre.setVisible(false);
    }

}
