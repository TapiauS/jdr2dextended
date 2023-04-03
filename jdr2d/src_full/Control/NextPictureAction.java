package Control;

import Graphic.FullLogInterface;
import jdr2dcore.Personnage;
import jdr2dcore.Utilisateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

public class NextPictureAction extends AbstractAction {

    private FullLogInterface fenetre;

    private Personnage personnage;

    private Utilisateur util;

    private Hashtable<Integer,BufferedImage> availableportrait;

    private int indexportrait;

    private ValidePictureChoiceAction observer;


    public NextPictureAction(FullLogInterface fenetre, Personnage nomperso, Utilisateur util, BufferedImage availableportrait, String message, ValidePictureChoiceAction observer){
        super(message);
        this.fenetre=fenetre;
        this.personnage=nomperso;
        this.util=util;
        this.observer=observer;
        this.indexportrait=0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage myPicture ;
        try {
            ClientPart.write(ConnexionOutput.NEXTPICTURE);
            int length=ClientPart.read();
            byte [] imgbyte=ClientPart.getIn().readNBytes(length);
            myPicture= ImageIO.read(new ByteArrayInputStream(imgbyte));
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        fenetre.getToplabel().setIcon(new ImageIcon(myPicture));
        fenetre.refresh();
    }
}
