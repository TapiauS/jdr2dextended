package Control;

import Graphic.FullLogInterface;
import jdr2dcore.Personnage;
import jdr2dcore.Potion;
import jdr2dcore.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

public class ValidePictureChoice extends AbstractAction {

    private FullLogInterface fenetre;

    private Utilisateur util;

    private Personnage personnage;

    private ArrayList<File> availableportrait;

    private int indexportrait;

    public ValidePictureChoice(FullLogInterface fenetre, Utilisateur util, Personnage personnage, ArrayList<File> availableportrait, String message){
        super(message);
        this.fenetre=fenetre;
        this.util=util;
        this.personnage=personnage;
        this.availableportrait=availableportrait;
        this.indexportrait=0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.fenetre.setPerso(personnage);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        this.fenetre.setVisible(false);
    }

    public void update(int position){
        this.indexportrait=position;
    }
}
