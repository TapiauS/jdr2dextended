package Graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreateAccountButton extends AbstractAction {

    private FullInterface fenetre;
    public CreateAccountButton(FullInterface full,String texte){
        super(texte);
        this.fenetre=full;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField create = new JTextField();
        create.setColumns(10);
        JButton valide=new JButton(new ValidationButton(this.fenetre,"Validation"));
        this.fenetre.remove((Component) e.getSource());
        this.fenetre.remove(this.fenetre.getLabel());
        this.fenetre.setTextField(create);
        this.fenetre.add(this.fenetre.getTextField());
        this.fenetre.add(valide);
        this.fenetre.setLabel(new JLabel("Entrer un pseudo"));
        this.fenetre.add(this.fenetre.getLabel());
        this.fenetre.repaint();
        this.fenetre.revalidate();
    }
}

