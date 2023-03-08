package Control;

import Graphic.FullLogInterface;
import jdr2dcore.Personnage;
import jdr2dcore.Utilisateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;

public class FirstCharButton extends AbstractAction {

    private Utilisateur util;

    private FullLogInterface fenetre;

    //getters


    public Utilisateur getUtil() {
        return util;
    }

    public FullLogInterface getFenetre() {
        return fenetre;
    }

    //builders

    public FirstCharButton(FullLogInterface fenetre, String texte, Utilisateur util){
        super(texte);
        this.fenetre=fenetre;
        this.util=util;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField textField= (JTextField) fenetre.getToptextfield();
        String charname=textField.getText();
        if(!charname.isEmpty()) {
            boolean validation;
            Personnage perso;
            try {
                ClientPart.getServeroutput().writeObject(ConnexionOutput.VALIDCHOICE);
                ClientPart.getServeroutput().writeObject(charname);
                validation= (boolean) ClientPart.getServerinput().readObject();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            if (validation) {
                BufferedImage firstportraits;
                try {
                    perso= (Personnage) ClientPart.getServerinput().readObject();
                    System.out.println("j'arrive ici");
                    int length= (int) ClientPart.getServerinput().readObject();
                    byte [] imgbyte=ClientPart.getIn().readNBytes(length);
                    firstportraits= ImageIO.read(new ByteArrayInputStream(imgbyte));
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                fenetre.getToplabel().setIcon(new ImageIcon(firstportraits));
                fenetre.getToplabel().setText("");
                fenetre.getToptextfield().setVisible(false);
                fenetre.getBottomtextfield().setVisible(false);
                fenetre.getBottomlabel().setVisible(false);
                ValidePictureChoice valid=new ValidePictureChoice(this.fenetre,util,perso,firstportraits,"Valider");
                fenetre.setBottom(new JButton(valid));
                NextPictureButton next=new NextPictureButton(this.fenetre,perso,this.util,firstportraits,"Option Suivante",valid);
                fenetre.setTop(new JButton(next));
                fenetre.refresh();
            } else {
                this.fenetre.setToplabel(new JLabel("Personnage non disponible, choisir un autre nom"));
                this.fenetre.refresh();
            }
        }
    }
}
