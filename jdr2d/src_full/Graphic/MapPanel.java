package Graphic;


import Entity.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MapPanel extends JPanel {

    private Personnage player;

    private GameInterface fenetre;

    private ArrayList<Porte> sorties;

    private ArrayList<PNJ> pnjs;

    private static final int CAMERA_WIDTH=30;

    private static final BufferedImage doorassets;

    static {
        try {
            doorassets = ImageIO.read(new File("Assets/door.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final BufferedImage knigthassets;

    static {
        try {
            knigthassets = ImageIO.read(new File("Assets/knigth.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final BufferedImage chetasset;

    static {
        try {
            chetasset = ImageIO.read(new File("Assets/chest.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final BufferedImage wolfassets;

    static {
        try {
            wolfassets = ImageIO.read(new File("Assets/loups.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final BufferedImage playeravatar;

    static {
        try {
            playeravatar = ImageIO.read(new File("Assets/malejoueur.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static public final int MAP_WIDTH = (GameInterface.WINDOW_WIDTH*2/3);
    static public final int MAP_HEIGH =(GameInterface.WINDOWS_HEIGH*2/3);

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
        return MAP_HEIGH;
    }

    public int getWINDOWS_WIDTH() {
        return MAP_WIDTH;
    }

    //setters


    public void setFenetre(GameInterface fenetre) {
        this.fenetre = fenetre;
    }

    public void setSorties(ArrayList<Porte> sorties) {
        this.sorties = sorties;
    }

    public void setPlayer(Personnage player) {
        this.player = player;
        this.unit_size = MAP_WIDTH /CAMERA_WIDTH;
    }


    //constructeur

    MapPanel(Personnage player,ArrayList<PNJ> pnjs,GameInterface fenetre){
        super();
        this.setLayout(new FlowLayout());
        this.pnjs=pnjs;
        this.fenetre=fenetre;
        this.sorties=fenetre.getSorties();
        this.setPlayer(player);
        this.setPreferredSize(new Dimension(MAP_WIDTH,MAP_HEIGH));
        this.setBackground(new Color(0,0,0,0));
        this.setOpaque(false);
        this.setVisible(false);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        for (int i = 0; i < CAMERA_WIDTH; i++) {
            for (int j = 0; j < CAMERA_WIDTH; j++) {
                boolean isempty = true;
                int relativi = player.getX() + (i - 15);
                int relativj = player.getY() + (j - 15);
                if (relativi < 0 || relativi >= player.getLieux().getDimensions()[0] || relativj < 0
                        || relativj >= player.getLieux().getDimensions()[1]) {
                    g.setColor(Color.black);
                    g.fillRect(i * unit_size, j * unit_size, unit_size, unit_size);
                } else {
                    if(((relativj-player.getY())*(relativj-player.getY())+(relativi-player.getX())*(relativi-player.getX()))<14*14) {
                        if (player.getLieux().getCarte()[relativj][relativi] == '#') {
                            g.setColor(Color.black);
                            g.fillRect(i * unit_size, j * unit_size, unit_size, unit_size);
                            isempty = false;
                        }
                        if (relativi == player.getX() && relativj == player.getY() && player.getpV() > 0) {
                            g.drawImage(playeravatar, i * unit_size, j * unit_size, unit_size, unit_size, null);
                            isempty = false;
                        }
                        for (PNJ p : pnjs) {
                            if (relativi == p.getX() && relativj == p.getY() && p.getpV() > 0) {
                                if (p.isNomme()) {
                                    g.drawImage(knigthassets, i * unit_size, j * unit_size, unit_size, unit_size, null);
                                } else {
                                    g.drawImage(wolfassets, i * unit_size, j * unit_size, unit_size, unit_size, null);
                                }
                                isempty = false;
                            }
                        }
                        for (Coffre c : fenetre.getCoffres()) {
                            if (c.getY() == relativj && c.getX() == relativi) {
                                g.drawImage(chetasset, i * unit_size, j * unit_size, unit_size, unit_size, null);
                                isempty = false;
                            }
                        }
                        for (Porte p : sorties) {
                            if (p.getX() == relativi && p.getY() == relativj) {
                                g.drawImage(doorassets, i * unit_size, j * unit_size, unit_size, unit_size, null);
                                isempty = false;
                            }
                        }
                        if (isempty) {
                            g.setColor(Color.white);
                            g.fillRect(i * unit_size, j * unit_size, unit_size, unit_size);
                        }
                    }
                    else {
                        g.setColor(Color.black);
                        g.fillRect(i * unit_size, j * unit_size, unit_size, unit_size);
                    }
                }
            }
        }
    }

    //getters

    public ArrayList<PNJ> getPnjs() {
        return pnjs;
    }

    //setters


    public void setPnjs(ArrayList<PNJ> pnjs) {
        this.pnjs = pnjs;
    }

    public void setUnit_size(int unit_size) {
        this.unit_size = unit_size;
    }

}
