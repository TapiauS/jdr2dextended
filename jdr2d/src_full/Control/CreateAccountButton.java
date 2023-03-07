package Control;

import Graphic.FullLogInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CreateAccountButton extends AbstractAction {

    private FullLogInterface fenetre;
    public CreateAccountButton(FullLogInterface full, String texte){
        super(texte);
        this.fenetre=full;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField create = new JTextField();
        try {
            new ObjectOutputStream(ClientPart.getServeroutput()).writeObject(ConnexionInput.CREATION);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        create.setColumns(10);
        JButton valide=new JButton(new ValidationPseudoButton(this.fenetre,"Validation"));
        this.fenetre.setTop(valide);
        this.fenetre.setToptextfield(create);
        this.fenetre.setToplabel(new JLabel("Entrer un pseudo"));
        this.fenetre.setBottom(new JButton(new ConnexionButton(this.fenetre,"Se connecter")));
        this.fenetre.getBottomlabel().setVisible(false);
        this.fenetre.getBottomtextfield().setVisible(false);
        this.fenetre.refresh();
    }
}
