package Control;

import DAO.UtilisateurDAO;
import Graphic.FullLogInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class MdpValidationButton extends AbstractAction {


    private FullLogInterface fenetre;
    private String pseudo;



    //getters


    public FullLogInterface getFenetre() {
        return fenetre;
    }


    public void setFenetre(FullLogInterface fenetre) {
        this.fenetre = fenetre;
    }

    public MdpValidationButton(FullLogInterface fenetre, String texte,String pseudo){
        super(texte);
        this.setFenetre(fenetre);
        this.pseudo=pseudo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField textField= (JTextField) fenetre.getToptextfield();
        String mdp = textField.getText();
        boolean val;
        try {
            val = UtilisateurDAO.checkpseudo(mdp);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        if(val){
            JLabel mail =new JLabel("Rentrer votre adresse mail");
            this.fenetre.setToplabel(mail);
            JTextField zone=new JTextField();
            zone.setColumns(10);
            this.fenetre.setToptextfield(zone);
            this.fenetre.setTop(new JButton(new MailButton(this.fenetre,"Validation",this.pseudo,mdp)));
            this.fenetre.setToptextfield(zone);
            this.fenetre.refresh();
        }
        else {
            JButton c= (JButton) e.getSource();
            this.fenetre.add(c);
            this.fenetre.setToplabel(new JLabel("Mot de passe non disponible, veuillez re essayer"));
            this.fenetre.refresh();
        }

    }
}
