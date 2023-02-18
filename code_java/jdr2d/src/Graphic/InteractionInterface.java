package Graphic;

import jdr2dcore.Personnage;

import javax.swing.*;

public class InteractionInterface extends JPanel {
    protected static final int INTERACTION_WIDTH = (int) (GameInterface.WINDOW_WIDTH*0.4);
    protected static final int INTERACTION_HEIGH = (int) (GameInterface.WINDOW_WIDTH*0.3);

    protected GameInterface fenetre;

    protected Personnage player;


    //builders

    public InteractionInterface(GameInterface fenetre,Personnage player){
        super();
        this.fenetre=fenetre;
        this.player=player;
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

    public void setPlayer(Personnage player) {
        this.player = player;
    }
}
