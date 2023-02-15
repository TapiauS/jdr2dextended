package Graphic;


import jdr2dcore.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class MapPanel extends JPanel implements KeyListener {

    private Personnage player;

    private ArrayList<Porte> sorties;

    private ArrayList<PNJ> pnjs;

    private ArrayList<Echange> echanges;



    static private final int WINDOWS_WIDTH=790;
    static private final int WINDOWS_HEIGH=790;

    private  int unit_size;

    //getters


    public ArrayList<Porte> getSorties() {
        return sorties;
    }

    public Personnage getPlayer() {
        return player;
    }


    public int getUnit_size() {
        return unit_size;
    }

    public int getWINDOWS_HEIGH() {
        return WINDOWS_HEIGH;
    }

    public int getWINDOWS_WIDTH() {
        return WINDOWS_WIDTH;
    }

    //setters

    public void setSorties(ArrayList<Porte> sorties) {
        this.sorties = sorties;
    }

    public void setPlayer(Personnage player) {
        this.player = player;
        this.unit_size = Math.min(WINDOWS_WIDTH/(player.getLieux().getCarte().length),WINDOWS_HEIGH/player.getLieux().getCarte()[0].length);
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
        for (int i = 0; i <player.getLieux().getCarte()[0].length; i++) {
            for (int j = 0; j < player.getLieux().getCarte().length; j++) {
                if(player.getLieux().getCarte()[j][i]=='#') {
                    g.setColor(Color.black);
                    g.fillRect(i * unit_size, j * unit_size, unit_size, unit_size);
                }
                if(player.getLieux().getCarte()[j][i]=='C'){
                    g.setColor(Color.cyan);
                    g.fillOval(i * unit_size,j * unit_size, unit_size, unit_size);
                }
                if(player.getLieux().getCarte()[j][i]=='E'||player.getLieux().getCarte()[j][i]=='S'){
                    g.setColor(Color.red);
                    g.fillOval(i * unit_size,j * unit_size, unit_size, unit_size);
                }
                if(i==player.getX()&&j==player.getY()) {
                    g.setColor(Color.green);
                    g.fillOval(i * unit_size, j * unit_size, unit_size, unit_size);
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
