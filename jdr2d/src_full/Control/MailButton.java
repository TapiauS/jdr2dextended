package Control;

import Graphic.FullLogInterface;
import jdr2dcore.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class MailButton extends AbstractAction {

    private FullLogInterface fenetre;

    private String pseudo;

    private String mdp;

    //getters


    public FullLogInterface getFenetre() {
        return fenetre;
    }

    public String getMdp() {
        return mdp;
    }

    public String getPseudo() {
        return pseudo;
    }

    //builder

    public MailButton(FullLogInterface fenetre,String message,String pseudo,String mdp){
        super(message);
        this.fenetre=fenetre;
        this.pseudo=pseudo;
        this.mdp=mdp;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField textField= (JTextField) fenetre.getToptextfield();
        String mail=textField.getText();

        boolean val;
        try {
            (ClientPart.getServeroutput()).writeObject(ConnexionOutput.VALIDCHOICE);
            (ClientPart.getServeroutput()).writeObject(mail);
            val= (boolean) ClientPart.getServerinput().readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        if(val){
            JLabel perso =new JLabel("Choisir un nom pour votre personnage");
            this.fenetre.setToplabel(perso);
            JTextField zone=new JTextField();
            zone.setColumns(10);
            Utilisateur util;
            try {
                util= (Utilisateur) ClientPart.getServerinput().readObject();
                this.fenetre.setUtil(util);
            } catch ( IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            this.fenetre.setTop(new JButton(new FirstCharButton(this.fenetre,"Validation",util)));
            this.fenetre.setToptextfield(zone);
            this.fenetre.refresh();
        }
        else {
            this.fenetre.setToplabel(new JLabel("Adresse mail non disponible, veuillez re essayer"));
            this.fenetre.refresh();
        }
    }
}
