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
    private Thread save;
    private boolean interaction;
    private CoffreInterface coffredealer;
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
        this.interaction=false;
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
        coffredealer=new CoffreInterface(this.player,this);
        container.add(coffredealer);
        this.setContentPane(container);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        save = new Thread(() -> {
            try {
                PersonnageDAO.updatedatabase(player);
            } catch (SQLException e) {
                //TODO gérer cette exception
                throw new RuntimeException(e);
            }
        });
        Runtime.getRuntime().addShutdownHook(save);
        addKeyListener(this);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Est ce que le probléme ce situe ici");
            if (e.getKeyCode() == 38 && !interaction) {
                player.depl(Direction.NORD);
                System.out.println("??");
                eventHistory.addLine("haut");
            }
            if (e.getKeyCode() == 37 && !interaction) {
                player.depl(Direction.OUEST);
                eventHistory.addLine("droite");
            }
            if (e.getKeyCode() == 39 && !interaction) {
                player.depl(Direction.EST);
                eventHistory.addLine("gauche");

            }
            if (e.getKeyCode() == 40 && !interaction) {
                player.depl(Direction.SUD);
                eventHistory.addLine("bas");

            }
        if(e.getKeyChar()=='i') {
            for (Coffre c: coffres) {
                if(c.distance(player)<2){
                    coffredealer.setOpenedcoffre(c);
                    this.setInteraction(true);
                    break;
                }
            }
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
    //getters


    public boolean isInteraction() {
        return interaction;
    }

    public Personnage getPlayer() {
        return player;
    }

    public Utilisateur getUtil() {
        return util;
    }

    public ArrayList<PNJ> getPnjs() {
        return pnjs;
    }

    public ArrayList<Coffre> getCoffres() {
        return coffres;
    }

    public ArrayList<Echange> getEchanges() {
        return echanges;
    }

    public ArrayList<Porte> getSorties() {
        return sorties;
    }

    public EventHistory getEventHistory() {
        return eventHistory;
    }


    public JPanel getContainer() {
        return container;
    }

    public CoffreInterface getCoffredealer() {
        return coffredealer;
    }


    public MapPanel getMapPanel() {
        return mapPanel;
    }

    public Map getCarte() {
        return carte;
    }

    //setters

    public void setCoffredealer(CoffreInterface coffredealer) {
        this.coffredealer = coffredealer;
    }

    public void setInteraction(boolean interaction) {
        System.out.println("debugage de l'interaction");
        this.interaction = interaction;
    }

    public void setPlayer(Personnage player) {
        this.player = player;
    }

    public void setMapPanel(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
    }

    public void setPnjs(ArrayList<PNJ> pnjs) {
        this.pnjs = pnjs;
    }

    public void setUtil(Utilisateur util) {
        this.util = util;
    }

    public void setCarte(Map carte) {
        this.carte = carte;
    }

    public void setEventHistory(EventHistory eventHistory) {
        this.eventHistory = eventHistory;
    }

    public void setSorties(ArrayList<Porte> sorties) {
        this.sorties = sorties;
    }

    public void setEchanges(ArrayList<Echange> echanges) {
        this.echanges = echanges;
    }

    public void setCoffres(ArrayList<Coffre> coffres) {
        this.coffres = coffres;
    }

    public void setContainer(JPanel container) {
        this.container = container;
    }
}
