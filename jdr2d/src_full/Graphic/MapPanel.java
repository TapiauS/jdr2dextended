package Graphic;


import jdr2dcore.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MapPanel extends JPanel {

    private Personnage player;

    private GameInterface fenetre;

    private ArrayList<Porte> sorties;

    private ArrayList<PNJ> pnjs;

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
        this.unit_size = Math.min(MAP_WIDTH /(player.getLieux().getCarte().length), MAP_HEIGH /player.getLieux().getCarte()[0].length);
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
        this.setVisible(true);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
            //MapGraph graph=new MapGraph();
            //System.out.println(graph.affichmap(player.getLieux().getCarte()));
        for (int i = 0; i < player.getLieux().getCarte()[0].length; i++) {
            for (int j = 0; j < player.getLieux().getCarte().length; j++) {
                boolean isempty=true;
                if (player.getLieux().getCarte()[j][i] == '#') {
                    g.setColor(Color.black);
                    g.fillRect(i * unit_size, j * unit_size, unit_size, unit_size);
                    isempty=false;
                }
                if (i == player.getX() && j == player.getY()&&player.getpV()>0) {
                    g.drawImage(playeravatar,i * unit_size,j * unit_size,unit_size,unit_size,null);
                    isempty=false;
                }
                for (PNJ p : pnjs) {
                    if (i == p.getX() && j == p.getY()&&p.getpV()>0) {
                        if(p.isNomme()) {
                            g.drawImage(knigthassets,i * unit_size,j * unit_size,unit_size,unit_size,null);
                        }
                        else {
                            g.drawImage(wolfassets,i * unit_size,j * unit_size,unit_size,unit_size,null);
                        }
                        isempty=false;
                    }
                }
                if(isempty){
                    g.setColor(Color.white);
                    g.fillRect(i*unit_size,j*unit_size,unit_size,unit_size);
                }
            }
        }

        for (Coffre c:fenetre.getCoffres()) {
            for (int i = 0; i < player.getLieux().getCarte()[0].length; i++) {
                for (int j = 0; j < player.getLieux().getCarte().length; j++) {
                    if (c.getY() == j && c.getX() == i) {
                        g.drawImage(chetasset,i * unit_size,j*unit_size,unit_size,unit_size,null);
                    }
                }
            }
        }
/*        for (int i = player.getLieux().getCarte()[0].length; i*unit_size <MAP_WIDTH ; i++) {
            for (int j = 0; j*unit_size <MAP_WIDTH ; j++) {
                g.setColor(Color.black);
                g.fillRect(i*unit_size,j*unit_size,unit_size,unit_size);
            }

        }
        for (int j = player.getLieux().getCarte().length; j*unit_size <MAP_HEIGH ; j++) {
            for (int i = 0; i*unit_size<MAP_HEIGH; i++) {
                g.setColor(Color.black);
                g.fillRect(i*unit_size,j*unit_size,unit_size,unit_size);
            }
        }*/
        for (Porte p: sorties) {
            for (int i = 0; i < player.getLieux().getCarte()[0].length; i++) {
                for (int j = 0; j < player.getLieux().getCarte().length; j++) {
                    if(p.getX()==i&&p.getY()==j){
                        g.drawImage(doorassets,i*unit_size,j*unit_size,unit_size,unit_size,null);
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
