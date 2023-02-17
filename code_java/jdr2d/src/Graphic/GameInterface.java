package Graphic;

import DAO.EchangeDAO;
import DAO.MapDAO;
import DAO.PersonnageDAO;
import DAO.PorteDAO;
import jdr2dcore.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class GameInterface extends JFrame  implements KeyListener {

    private Personnage player;

    private MapPanel mapPanel;

    private ArrayList<PNJ> pnjs;

    private Utilisateur util;

    private Map carte;

    private EventHistory eventHistory;

    private ArrayList<Porte> sorties;

    private ArrayList<Echange> echanges;
    protected ArrayList<Coffre> coffres;

    public static final int WINDOWS_HEIGH=1000;

    public static final int WINDOW_WIDTH=1000;
    
    private JPanel container;

    public GameInterface(Personnage player,Utilisateur util) throws SQLException {
        super();
        this.setLayout(null);
        this.player=player;
        this.util=util;
        this.setSize(new Dimension(WINDOW_WIDTH,WINDOWS_HEIGH));
        this.setLocationRelativeTo(null);
        mapload();
        mapPanel=new MapPanel(this.player);
        eventHistory=new EventHistory();
        this.container=new JPanel();
        container.setBounds(0,0,WINDOW_WIDTH,WINDOWS_HEIGH);
        container.setVisible(true);
        container.add(mapPanel);
        container.setLayout(null);
        container.add(eventHistory);

        this.setContentPane(container);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
            System.out.println("??");
            eventHistory.addLine("haut");
        }
        if (e.getKeyCode()==37) {
            player.depl(Direction.OUEST);
            eventHistory.addLine("droite");
        }
        if(e.getKeyCode()==39) {
            player.depl(Direction.EST);
            eventHistory.addLine("gauche");
        }
        if(e.getKeyCode()==40) {
            player.depl(Direction.SUD);
            eventHistory.addLine("bas");
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
