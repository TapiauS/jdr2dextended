package Graphic;

import jdr2dcore.Personnage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MapPanel extends JPanel implements KeyListener {

    private Personnage player;



    static private final int WINDOWS_WIDTH=790;
    static private final int WINDOWS_HEIGH=790;

    private  int UNIT_SIZE;

    //getters


    public Personnage getPlayer() {
        return player;
    }


    public int getUNIT_SIZE() {
        return UNIT_SIZE;
    }

    public int getWINDOWS_HEIGH() {
        return WINDOWS_HEIGH;
    }

    public int getWINDOWS_WIDTH() {
        return WINDOWS_WIDTH;
    }

    //setters


    public void setPlayer(Personnage player) {
        this.player = player;
        this.UNIT_SIZE=(int) Math.sqrt((float) WINDOWS_HEIGH*WINDOWS_WIDTH/(player.getLieux().getCarte().length*player.getLieux().getCarte()[0].length));
    }


    //constructeur

    MapPanel(Personnage player){
        super();
        this.setPlayer(player);
        this.setPreferredSize(new Dimension(WINDOWS_WIDTH,WINDOWS_HEIGH));
        this.setBackground(Color.white);
        this.setVisible(true);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        for (int i = 0; i <player.getLieux().getCarte().length; i++) {
            for (int j = 0; j < player.getLieux().getCarte()[0].length; j++) {
                if(player.getLieux().getCarte()[i][j]=='#') {
                    g.setColor(Color.black);
                    System.out.println("Unit size="+UNIT_SIZE);
                    g.fillRect(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
                }
                if(player.getLieux().getCarte()[i][j]=='C'){
                    g.setColor(Color.cyan);
                    g.fillOval(i * UNIT_SIZE,j * UNIT_SIZE,UNIT_SIZE,UNIT_SIZE);
                }
                if(player.getLieux().getCarte()[i][j]=='E'||player.getLieux().getCarte()[i][j]=='S'){
                    g.setColor(Color.red);
                    g.fillOval(i * UNIT_SIZE,j * UNIT_SIZE,UNIT_SIZE,UNIT_SIZE);
                }
            }
        }
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
