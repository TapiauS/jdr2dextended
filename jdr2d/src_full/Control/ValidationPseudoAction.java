package Control;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import Graphic.FullLogInterface;

public class

ValidationPseudoAction extends AbstractAction {
    private FullLogInterface fenetre;


    //getters

    public FullLogInterface getFenetre() {
        return fenetre;
    }

    //setters

    public void setFenetre(FullLogInterface fenetre) {
        this.fenetre = fenetre;
    }

    public ValidationPseudoAction(FullLogInterface fenetre, String texte){
        super(texte);
        this.setFenetre(fenetre);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField textField= (JTextField) fenetre.getToptextfield();
        String pseudo = textField.getText();
        boolean val;
        try {
            (ClientPart.getServeroutput()).writeObject(ConnexionOutput.VALIDCHOICE);
            (ClientPart.getServeroutput()).writeObject(pseudo);
            val= (boolean) ClientPart.getServerinput().readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        if(val){
            JTextField createchar = new JTextField();
            createchar.setColumns(10);
            JLabel create=new JLabel("Choisir un mot de passe");
            this.fenetre.setToplabel(create);
            this.fenetre.setToptextfield(createchar);
            this.fenetre.setTop(new JButton(new MdpValidationAction(this.fenetre,"Validation",pseudo)));
            this.fenetre.refresh();
        }
        else {
            this.fenetre.setToplabel(new JLabel("Pseudo non disponible, veuillez re essayer"));
            this.fenetre.refresh();
        }
    }
}
