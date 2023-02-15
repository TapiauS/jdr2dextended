package Graphic;

import DAO.EchangeDAO;
import DAO.MapDAO;
import DAO.PersonnageDAO;
import DAO.PorteDAO;
import jdr2dcore.*;


import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class GameInterface extends JFrame  implements KeyListener {

    private Personnage player;

    private ArrayList<PNJ> pnjs;

    private Utilisateur util;

    private Map carte;

    private ArrayList<Porte> sorties;

    private ArrayList<Echange> echanges;
    protected static ArrayList<Coffre> coffres;

    public GameInterface(Personnage player,Utilisateur util) throws SQLException {
        super();
        this.player=player;
        this.util=util;
        mapload();
        MapPanel map=new MapPanel(this.player);
        this.setContentPane(map);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        this.setVisible(true);
        addKeyListener(this);

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==38) {
            player.depl(Direction.NORD);
        }
        if (e.getKeyCode()==37) {
            player.depl(Direction.OUEST);
        }
        if(e.getKeyCode()==39) {
            player.depl(Direction.EST);
        }
        if(e.getKeyCode()==40) {
            player.depl(Direction.SUD);
        }

        revalidate();
        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //methodes

    private  void mapload() throws SQLException {
        carte=player.getLieux();
        pnjs=new ArrayList<>();
        echanges=new ArrayList<>();
        coffres= MapDAO.getcoffres(carte);
        sorties= PorteDAO.getPorte(carte);
        for (Personnage p: PersonnageDAO.getPersonnages(carte,util)) {
            if(p instanceof PNJ){
                System.out.println("Vous venez d'arriver à "+carte.getNomLieu());
                pnjs.add((PNJ) p);
                Echange start= EchangeDAO.getEchangetree((PNJ) p);
                if(start!=null)
                    echanges.add(start);
            }
        }

    }

}
