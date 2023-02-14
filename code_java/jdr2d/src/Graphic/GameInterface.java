package Graphic;

import jdr2dcore.*;


import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameInterface extends JFrame  implements KeyListener {

    private Personnage player;

    private Map carte;

    public GameInterface(Personnage player){
        super();
        this.player=player;
        MapPanel map=new MapPanel(this.player);
        this.setContentPane(map);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        this.setVisible(true);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
