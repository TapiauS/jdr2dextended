package Control;

import Graphic.FullLogInterface;
import jdr2dcore.Utilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Hashtable;

public class LogAction extends AbstractAction {

    private FullLogInterface fenetre;
    public LogAction(FullLogInterface fenetre, String texte){
        super(texte);
        this.fenetre=fenetre;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField textField= (JTextField) fenetre.getToptextfield();
        String pseudo=textField.getText();
        String mdp=this.fenetre.getBottomtextfield().getText();
        Utilisateur util ;
        boolean success;
        try {
            ClientPart.write(ConnexionOutput.CONNEXION);
            ClientPart.write(pseudo);
            ClientPart.write(mdp);
            success=ClientPart.read();
        } catch (IOException | ClassNotFoundException ex) {
        throw new RuntimeException(ex);
        }
        if(!success){
            this.fenetre.setToplabel(new JLabel("Pseudo ou mot de passe incorrect, réessayez"));
            this.fenetre.refresh();
            return;
        }
        try {
            util= (Utilisateur) ClientPart.getServerinput().readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        this.fenetre.setUtil(util);
        Hashtable<String,Integer> refperso;
        try {
            refperso= (Hashtable<String, Integer>) ClientPart.getServerinput().readObject();
        } catch ( IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        if(!refperso.isEmpty()) {
            String[] data = refperso.keySet().toArray(new String[0]);
            JList<String> lisperso = new JList<>(data);
            this.fenetre.setToptextfield(lisperso);
            this.fenetre.setToplabel(new JLabel("Choisissez un personnage ou creez en un nouveau"));
            this.fenetre.setTop(new JButton(new ValideCharSelAction(this.fenetre, util, "Validation", refperso)));
        }
        else{
            this.fenetre.getTop().setVisible(false);
            this.fenetre.getToplabel().setText("Aucun personnage pour ce compte, veuillez creer un personnage");
            this.fenetre.getToptextfield().setVisible(false);
        }
        this.fenetre.getBottomtextfield().setVisible(false);
        this.fenetre.getBottomlabel().setVisible(false);
        this.fenetre.setBottom(new JButton(new CreateCharAction("Creer un personnage",this.fenetre,util,refperso)));
        this.fenetre.refresh();
    }
}
