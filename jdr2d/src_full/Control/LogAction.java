package Control;

import Graphic.FullLogInterface;
import ServerPart.DAO.ErrorType;
import Entity.Utilisateur;

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
        try {
            JTextField textField = (JTextField) fenetre.getToptextfield();
            String pseudo = textField.getText();
            String mdp = ((JTextField) this.fenetre.getBottommidlecomponent()).getText();
            Utilisateur util;
            boolean success;

            ClientPart.write(ConnexionOutput.CONNEXION);
            ClientPart.write(pseudo);
            ClientPart.write(mdp);
            success = ClientPart.read();
            if (!success) {
                ErrorType reason = ClientPart.read();
                if(reason==ErrorType.IDENTIFICATIONERROR) {
                    this.fenetre.setToplabel(new JLabel("Pseudo ou mot de passe incorrect, réessayez"));
                    this.fenetre.refresh();
                    return;
                }
                if(reason==ErrorType.NOTAVAILABLE){
                    this.fenetre.setToplabel(new JLabel("Ce compte est déja utilisé par quelqu'un d'autre"));
                    this.fenetre.refresh();
                    return;
                }
            }
            util = (Utilisateur) ClientPart.getServerinput().readObject();
            this.fenetre.setUtil(util);
            Hashtable<String, Integer> refperso;
            try {
                refperso = (Hashtable<String, Integer>) ClientPart.getServerinput().readObject();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            if (!refperso.isEmpty()) {
                String[] data = refperso.keySet().toArray(new String[0]);
                DefaultListModel<String> defaultListModel = new DefaultListModel<>();
                JList<String> lisperso = new JList<>(defaultListModel);
                lisperso.setListData(data);
                this.fenetre.setToptextfield(lisperso);
                this.fenetre.setToplabel(new JLabel("Choisissez un personnage ou creez en un nouveau"));
                this.fenetre.setTop(new JButton(new ValideCharSelAction(this.fenetre, util, "Validation", refperso)));
            } else {
                this.fenetre.getTop().setVisible(false);
                this.fenetre.getToplabel().setText("Aucun personnage pour ce compte, veuillez creer un personnage");
                this.fenetre.getToptextfield().setVisible(false);
            }
            this.fenetre.setBottommidlecomponent(new JButton(new DeleteCharAction(this.fenetre,"Supprimer")));
            this.fenetre.getBottomlabel().setVisible(false);
            this.fenetre.setBottom(new JButton(new CreateCharAction("Creer un personnage", this.fenetre, util, refperso)));
            this.fenetre.refresh();
        }
        catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

}
