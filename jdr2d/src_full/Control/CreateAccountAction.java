package Control;

import Graphic.FullLogInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class CreateAccountAction extends AbstractAction {

    private FullLogInterface fenetre;
    public CreateAccountAction(FullLogInterface full, String texte){
        super(texte);
        this.fenetre=full;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField create = new JTextField();
        try {
            ClientPart.write(ConnexionOutput.CREATION);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        create.setColumns(10);
        JButton valide=new JButton(new ValidationPseudoAction(this.fenetre,"Validation"));
        this.fenetre.setTop(valide);
        this.fenetre.setToptextfield(create);
        this.fenetre.setToplabel(new JLabel("Entrer un pseudo"));
        this.fenetre.setBottom(new JButton(new ConnexionAction(this.fenetre,"Se connecter")));
        this.fenetre.getBottomlabel().setVisible(false);
        this.fenetre.getBottomtextfield().setVisible(false);
        this.fenetre.refresh();
    }
}

