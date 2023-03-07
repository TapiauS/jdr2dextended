package Control;

import Graphic.FullLogInterface;
import jdr2dcore.Personnage;
import jdr2dcore.Utilisateur;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class NextPictureButton extends AbstractAction {

    private FullLogInterface fenetre;

    private Personnage personnage;

    private Utilisateur util;

    private Hashtable<Integer,BufferedImage> availableportrait;

    private int indexportrait;

    private ValidePictureChoice observer;


    public NextPictureButton(FullLogInterface fenetre,Personnage nomperso,Utilisateur util,BufferedImage availableportrait,String message,ValidePictureChoice observer){
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
            (ClientPart.getServeroutput()).writeObject(ConnexionInput.NEXTPICTURE);
            myPicture=ImageIO.read(ClientPart.getImginput());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        fenetre.getToplabel().setIcon(new ImageIcon(myPicture));
        fenetre.refresh();
    }
}
