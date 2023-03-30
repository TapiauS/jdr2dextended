package Graphic;

import ServerPart.DAO.PersonnageDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showConfirmDialog;

public class QuitMenu extends JMenu {

    GameInterface fenetre;

    JMenuItem quitgame;

    JMenuItem changeaccount;

    public QuitMenu(GameInterface fenetre,String message){
        super(message);
        this.fenetre=fenetre;

        quitgame=new JMenuItem("Quitter le jeu");
        quitgame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int retour = showConfirmDialog("Voulez-vous vraiment quitter?","Quitter");
                if(retour==0) {
                    fenetre.dispatchEvent(new WindowEvent(fenetre, WindowEvent.WINDOW_CLOSING));
                    fenetre.getLog().dispose();
                }
            }
        });

        changeaccount=new JMenuItem("Se deconnecter");
        changeaccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int retour=showConfirmDialog("Voulez vous vraiment vous deconnecter ?","Deconnexion");
                fenetre.getIa().setSwitchmap(false);
                fenetre.dispose();
                }
        });

        this.add(quitgame);
        this.add(changeaccount);
    }

    private int showConfirmDialog(String message,String titre){
        return JOptionPane.showConfirmDialog(
                null,
                message,
                titre,
                JOptionPane.YES_NO_OPTION);
    }

}
