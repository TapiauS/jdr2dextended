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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ValidePictureChoice extends AbstractAction {

    private FullLogInterface fenetre;

    private Utilisateur util;

    private Personnage personnage;

    private Hashtable<Integer,BufferedImage> availableportrait;

    private int indexportrait;

    public ValidePictureChoice(FullLogInterface fenetre, Utilisateur util, Personnage personnage,Hashtable<Integer,BufferedImage> availableportrait, String message){
        super(message);
        this.fenetre=fenetre;
        this.util=util;
        this.personnage=personnage;
        this.availableportrait=availableportrait;
        this.indexportrait=0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Integer> keystoarray=  availableportrait.keySet().stream().toList();
        try {
            DAOObject.queryUDC("UPDATE personnage SET id_portrait=? WHERE id_personnage=?;",new ArrayList<>(List.of(keystoarray.get(indexportrait),personnage.getId())));
            this.fenetre.setPerso(personnage);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        this.fenetre.setVisible(false);
    }

    public void update(int position){
        this.indexportrait=position;
    }
}
