package Control;

import Graphic.FullLogInterface;
import jdr2dcore.Personnage;
import jdr2dcore.Utilisateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class NextPictureButton extends AbstractAction {

    private FullLogInterface fenetre;

    private Personnage personnage;

    private Utilisateur util;

    private ArrayList<File> availableportrait;

    private int indexportrait;

    private ValidePictureChoice observer;


    public NextPictureButton(FullLogInterface fenetre,Personnage nomperso,Utilisateur util,ArrayList<File> availableportrait,String message,ValidePictureChoice observer){
        super(message);
        this.fenetre=fenetre;
        this.personnage=nomperso;
        this.util=util;
        this.availableportrait=availableportrait;
        this.observer=observer;
        this.indexportrait=0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(indexportrait+1<availableportrait.size())
            indexportrait++;
        else
            indexportrait=0;
        observer.update(indexportrait);
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(availableportrait.get(indexportrait));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        fenetre.getToplabel().setIcon(new ImageIcon(myPicture));
        fenetre.refresh();
    }
}
