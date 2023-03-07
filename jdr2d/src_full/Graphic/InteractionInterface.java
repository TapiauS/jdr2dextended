package Graphic;

import jdr2dcore.Personnage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class InteractionInterface extends JPanel {
    protected static final int INTERACTION_WIDTH = (int) (GameInterface.WINDOW_WIDTH*0.4);
    protected static final int INTERACTION_HEIGH = (int) (GameInterface.WINDOW_WIDTH*0.3);

    protected GameInterface fenetre;

    protected Personnage player;


    //builders

    public InteractionInterface(GameInterface fenetre,Personnage player){
        super();
        this.setFenetre(fenetre);
        try {
            this.setPlayer(player);
        } catch (SQLException e) {
            //TODO COMME D'HAB
            throw new RuntimeException(e);
        }
        this.setBackground(Color.white);
        this.setBounds(MapPanel.MAP_WIDTH, INTERACTION_HEIGH, INTERACTION_WIDTH, INTERACTION_HEIGH);
        this.setVisible(false);
    }

    //getters
    public GameInterface getFenetre() {
        return fenetre;
    }

    public Personnage getPlayer() {
        return player;
    }

    //setters


    public void setFenetre(GameInterface fenetre) {
        this.fenetre = fenetre;
    }

    public void setPlayer(Personnage player) throws SQLException {
        this.player = player;
    }

    //methodes

    protected void refreshfocus(){
        fenetre.requestFocus();
        fenetre.revalidate();
        fenetre.repaint();
    }

    protected void returndefault(){
        this.setVisible(false);
        this.fenetre.getDefaultInteractionInterface().setVisible(true);
    }
}
