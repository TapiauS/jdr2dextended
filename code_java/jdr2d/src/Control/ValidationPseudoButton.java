package Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import DAO.*;
import Graphic.FullLogInterface;

public class ValidationPseudoButton extends AbstractAction {
    private FullLogInterface fenetre;


    //getters

    public FullLogInterface getFenetre() {
        return fenetre;
    }

    //setters

    public void setFenetre(FullLogInterface fenetre) {
        this.fenetre = fenetre;
    }

    public ValidationPseudoButton(FullLogInterface fenetre, String texte){
        super(texte);
        this.setFenetre(fenetre);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField textField= (JTextField) fenetre.getToptextfield();
        String pseudo = textField.getText();
        boolean val;
        try {
            val = UtilisateurDAO.checkpseudo(pseudo);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        if(val){

            JTextField createchar = new JTextField();
            createchar.setColumns(10);
            JLabel create=new JLabel("Choisir un mot de passe");
            this.fenetre.setToplabel(create);
            this.fenetre.setToptextfield(createchar);
            this.fenetre.setTop(new JButton(new MdpValidationButton(this.fenetre,"Validation",pseudo)));
            this.fenetre.pack();
            this.fenetre.repaint();
            this.fenetre.revalidate();
        }
        else {
            this.fenetre.setToplabel(new JLabel("Pseudo non disponible, veuillez re essayer"));
            this.fenetre.pack();
            this.fenetre.repaint();
            this.fenetre.revalidate();
        }
    }
}
