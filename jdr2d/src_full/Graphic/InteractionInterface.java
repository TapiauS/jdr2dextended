package Graphic;

import jdr2dcore.Personnage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class InteractionInterface extends JPanel {
    protected static final int INTERACTION_WIDTH = (GameInterface.WINDOW_WIDTH /3);
    protected static final int INTERACTION_HEIGH = (GameInterface.WINDOW_WIDTH /3);

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
