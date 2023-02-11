package Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import DAO.*;
import Graphic.FullLogInterface;

public class ValidationButton extends AbstractAction {
    private FullLogInterface fenetre;

    public ValidationButton(FullLogInterface fenetre, String texte){
        super(texte);

        this.fenetre = fenetre;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String pseudo = fenetre.getTextField().getText();
        boolean val;
        try {
            val = UtilisateurDAO.checkpseudo(pseudo);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        if(val){
            this.fenetre.remove(this.fenetre.getLabel());
            this.fenetre.remove((Component) e.getSource());
            this.fenetre.add(new JLabel("Bravo"));
            this.fenetre.repaint();
            this.fenetre.revalidate();
        }
        else {
            JButton c= (JButton) e.getSource();
            this.fenetre.remove(this.fenetre.getLabel());
            this.fenetre.add(c);
            this.fenetre.setLabel(new JLabel("Pseudo non disponible, veuillez re essayer"));
            this.fenetre.add(this.fenetre.getLabel());
        }
    }
}
