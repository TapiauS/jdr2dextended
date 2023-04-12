package Control;

import Graphic.FullLogInterface;
import Entity.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.regex.Pattern;

public class MailAction extends AbstractAction {

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

    public MailAction(FullLogInterface fenetre, String message, String pseudo, String mdp){
        super(message);
        this.fenetre=fenetre;
        this.pseudo=pseudo;
        this.mdp=mdp;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField textField= (JTextField) fenetre.getToptextfield();
        String mail=textField.getText();

        if(Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",mail)){
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
            this.fenetre.setTop(new JButton(new FirstCharAction(this.fenetre,"Validation",util)));
            this.fenetre.setToptextfield(zone);
            this.fenetre.refresh();
        }
        else {
            this.fenetre.setToplabel(new JLabel("Adresse mail non disponible, veuillez re essayer"));
            this.fenetre.refresh();
        }
    }
    else{
        JOptionPane.showMessageDialog(null,"Adresse mail invalide");
        }
    }

}
