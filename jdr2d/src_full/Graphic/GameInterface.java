package Graphic;

import Control.*;
import Log.LogLevel;
import Log.Loggy;
import ServerPart.Control.PersoThread;
import ServerPart.DAO.*;
import ServerPart.Socketsmanager.MapState;
import jdr2dcore.*;


import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Properties;

public class GameInterface extends JFrame  implements KeyListener {
    private final Thread save;

    private FullLogInterface log;
    private PlayerInfo thisInfo;

    private InventaireInterface inventdealer;

    private DefaultInteractionInterface defaultInteractionInterface;

    private QueteInterface quetedisplayer;
    private boolean interaction;
    private CoffreInterface coffredealer;
    private Personnage player;



    private MapPanel mapPanel;

    private float effectvolume=0.3f;
    private ArrayList<PNJ> pnjs;

    private Utilisateur util;

    private Instant nextmactiontime;

    private static final long timestepms=100;

    private Map carte;

    private Soundtrackscontroller music;
    private EventHistory eventHistory;

    private ArrayList<Personnage> players;
    private ArrayList<Porte> sorties;

    private ArrayList<Echange> echanges;
    protected ArrayList<Coffre> coffres;

    protected Properties properties;

    public static final int WINDOWS_HEIGH=900;

    public static final int WINDOW_WIDTH=WINDOWS_HEIGH;
    
    private JPanel container;

    private JLabel portrait;

    private JMenuBar menubar;

    private QuitMenu quitter;

    private DialogueInterface dialogdealer;

    private PersoThread ia;

    private ChatGraphicInterface chat;

    public GameInterface(Personnage player,Utilisateur util,FullLogInterface log) throws SQLException {
        super();
        this.setLayout(new FlowLayout());
        this.setIconImage(new ImageIcon("Portraits/gamicon.png").getImage());
        try {
            FileInputStream in = new FileInputStream("control"+util.getNomUtilisateur()+".properties");
            properties= new Properties();
            properties.load(in);
            in.close();
        }
        catch (FileNotFoundException fne){
            try {
                FileInputStream in = new FileInputStream("defaultcontrol.properties");
                properties= new Properties();
                properties.load(in);
                in.close();
                FileOutputStream fos=new FileOutputStream("control"+util.getNomUtilisateur()+".properties");
                properties.store(fos,"Initialisation");
                fos.close();
            } catch (IOException e) {
                Loggy.writlog("DEFAULT PROPERTIES LOST",LogLevel.NOTICE);
                JOptionPane.showMessageDialog(null,"control.properties introuvable","Erreur Fatale",JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException(e);
            }
        }
        catch (IOException ioe){
            Loggy.writlog("PROPERTIES LOST",LogLevel.NOTICE);
            JOptionPane.showMessageDialog(null,"control.properties introuvable","Erreur Fatale",JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(ioe);
        }
        this.log=log;
        this.nextmactiontime=Instant.now();
        this.interaction=false;
        //this.setLayout(new GridBagLayout());
        this.player=player;
        this.util=util;
        this.setLocationRelativeTo(null);
        mapload();
        ia =new PersoThread(pnjs,this);
        //on definit tout les élements
        thisInfo=new PlayerInfo(this.player,this);
        mapPanel=new MapPanel(this.player,this.pnjs,this);
        mapPanel.setVisible(true);
        defaultInteractionInterface=new DefaultInteractionInterface(this,this.player);
        defaultInteractionInterface.setVisible(true);
        eventHistory=new EventHistory();
        coffredealer=new CoffreInterface(this.player,this);
        inventdealer=new InventaireInterface(this,this.player);
        quetedisplayer=new QueteInterface(this,this.player);
        menubar=new JMenuBar();
        menubar.setVisible(true);
        quitter = new QuitMenu(this,"Menu");
        menubar.add(quitter);
        JMenuItem settings=new JMenuItem("Parametres");
        settings.addActionListener(e->{
            SettingsDisplayer setings=new SettingsDisplayer(this);
            interaction=true;
        });
        menubar.add(settings);
        dialogdealer=new DialogueInterface(this,player);
        BufferedImage myPicture = null;
        try {
            myPicture = PersonnageDAO.getcharportrait(player.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        portrait = new JLabel(new ImageIcon(myPicture));
        portrait.setVisible(true);
        /* on définit la fenétre globale et lui donne tout les élements */

        JPanel panelrigth=new JPanel(new BorderLayout());
        panelrigth.setPreferredSize(new Dimension(2*WINDOW_WIDTH/3,WINDOWS_HEIGH));
        JPanel panelLeft=new JPanel(new BorderLayout());
        panelLeft.setPreferredSize(new Dimension(WINDOW_WIDTH/3,WINDOWS_HEIGH));
        thisInfo.setPreferredSize(new Dimension(WINDOW_WIDTH/3,WINDOWS_HEIGH/3));
        portrait.setPreferredSize(new Dimension(WINDOW_WIDTH/3,WINDOWS_HEIGH/3));
        JScrollPane contevent=new JScrollPane(eventHistory);
        contevent.setPreferredSize(new Dimension(2*WINDOW_WIDTH/3,WINDOWS_HEIGH/3));
        //contevent.setVisible(true);
        this.container=new JPanel(new BorderLayout());

        container.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOWS_HEIGH));

        //container.setBounds(0,menubar.getHeight(),WINDOW_WIDTH,WINDOWS_HEIGH);
        this.setJMenuBar(menubar);

        panelrigth.add(mapPanel,BorderLayout.NORTH);
        panelrigth.add(contevent,BorderLayout.CENTER);

        container.add(panelrigth,BorderLayout.WEST);

        panelLeft.add(thisInfo,BorderLayout.NORTH);

        JPanel containersupp=new JPanel();

        containersupp.add(dialogdealer);
        containersupp.add(inventdealer);
        containersupp.add(coffredealer);
        panelLeft.add(containersupp,BorderLayout.WEST);
        panelLeft.add(quetedisplayer,BorderLayout.CENTER);
        panelLeft.add(defaultInteractionInterface,BorderLayout.EAST);



        panelLeft.add(portrait,BorderLayout.SOUTH);


        container.add(panelLeft,BorderLayout.EAST);


        container.setBackground(Color.black);
        container.setVisible(true);
        this.setContentPane(container);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        save = new Thread(() -> {
            try {
                DAOObject.close();
            } catch (SQLException e) {
                //TODO gérer cette exception
                throw new RuntimeException(e);
            }
        });
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    System.out.println("On sauvegarde en fermant");
                    PersonnageDAO.updatedatabase(player);
                    chat.dispose();
                    ia.setSwitchmap(false);
                    //music.getClip().stop();
                    for (PNJ ps: pnjs) {
                        if(ps.isNomme())
                            PersonnageDAO.updatepnj(ps);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        Runtime.getRuntime().addShutdownHook(save);
        addKeyListener(this);
        try {
            music=new Soundtrackscontroller(player.getLieux().getNomLieu()+".wav");
        } catch (UnsupportedAudioFileException | IOException e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"Erreur lors du chargement de la musique du jeu");
        } catch (LineUnavailableException e) {
            JOptionPane.showMessageDialog(null,"Erreur lors de la lecture de la musique du jeu");
        }
        this.requestFocus();
        try {
            AutoUpdateSocket.launch(this);
            PNJIASocket.launch(this);
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        SoundEffect.setVolumevalue(effectvolume);
        try {
            System.out.println("J'arrive dans le try");
            chat=new ChatGraphicInterface(this);
            System.out.println("J'arrive a lancer cette interface");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("avatar liste quete size"+player.getQueteSuivie().size());
        pack();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == readtable("MOVENORTH") && !interaction && Instant.now().isAfter(nextmactiontime)) {
            nextmactiontime=Instant.now().plus(timestepms, ChronoUnit.MILLIS);
            try {
                ClientPart.write(OutputType.MOUVNORD);
                eventHistory.addLine("north");
                player.depl(Direction.NORD);
                SoundEffect.playSound("walk");
            } catch (UnsupportedAudioFileException |  LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
            checkdoor();
        }
        if (e.getKeyCode() == readtable("MOVEWEST") && !interaction && Instant.now().isAfter(nextmactiontime)) {
            nextmactiontime=Instant.now().plus(timestepms, ChronoUnit.MILLIS);
            player.depl(Direction.OUEST);
            try {
                ClientPart.write(OutputType.MOUVWEST);
                SoundEffect.playSound("walk");
            } catch (UnsupportedAudioFileException | javax.sound.sampled.LineUnavailableException  | IOException ex) {
                throw new RuntimeException(ex);
            }
            checkdoor();
        }
        if (e.getKeyCode() == readtable("MOVEEAST") && !interaction && Instant.now().isAfter(nextmactiontime)) {
            nextmactiontime=Instant.now().plus(timestepms, ChronoUnit.MILLIS);
            player.depl(Direction.EST);
            try {
                ClientPart.write(OutputType.MOUVEAST);
                SoundEffect.playSound("walk");
            } catch (UnsupportedAudioFileException |  LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
            checkdoor();
        }
        if (e.getKeyCode() == readtable("MOVESOUTH") && !interaction && Instant.now().isAfter(nextmactiontime)) {
            nextmactiontime=Instant.now().plus(timestepms, ChronoUnit.MILLIS);
            player.depl(Direction.SUD);
            try {
                SoundEffect.playSound("walk");
                ClientPart.write(OutputType.MOUVSOUTH);
            } catch (UnsupportedAudioFileException |  LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
            checkdoor();
        }
        if(e.getKeyCode()==readtable("COFFRE") &&!interaction) {
            for (Coffre c: coffres) {
                if(c.distance(player)<2){
                    try {
                        ClientPart.write(OutputType.PICK);
                        ClientPart.write(c.getId());
                        boolean available;
                        available=ClientPart.read();
                        Loggy.writlog(String.valueOf(available),LogLevel.NOTICE);
                        if (available) {
                            Loggy.writlog("test des coffres", LogLevel.NOTICE);
                            coffredealer.setOpenedcoffre(c);
                            defaultInteractionInterface.setVisible(false);
                            this.setInteraction(true);
                            break;
                        }
                        else {
                            eventHistory.addLine("Ce coffre est déja fouillé par quelqu'un");
                        }
                    } catch (IOException  | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        if (e.getKeyCode()==readtable("INVENTAIRE")&&!interaction){
            defaultInteractionInterface.setVisible(false);
            try {
                ClientPart.write(OutputType.INVENTAIRE);
                inventdealer.setOpenedcoffre(player.getInventaire());
                this.setInteraction(true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getKeyCode()==readtable("QUETE")&&!interaction){
            try {
                defaultInteractionInterface.setVisible(false);
                quetedisplayer.updateQuete();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getKeyCode()==readtable("TALK")&&!interaction){
            for (PNJ p: this.getPnjs()) {
                if (p.distance(player) < 1&&p.getpV()>0) {
                    p.setInteract(true);
                    for (Echange ech : this.getEchanges()) {
                        if (ech.getParleur().getId() == p.getId()) {
                            this.getDialogdealer().setPresentechange(ech);
                            this.getDialogdealer().setVisible(true);
                            this.getDialogdealer().buildObserver();
                            this.defaultInteractionInterface.setVisible(false);
                            try {
                                ClientPart.write(OutputType.TALK);
                                ClientPart.write(p.getId());
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            this.setInteraction(true);
                            break;
                        }
                    }
                }
            }
        }
        if(e.getKeyCode()==readtable("FIGTH")&&!interaction){
            for (PNJ p: this.getPnjs()) {
                if (p.distance(player) < 1 && p.getpV() > 0) {
                    interaction = true;
                    try {
                        ClientPart.write(OutputType.FIGTH);
                        ClientPart.write(p.getId());
                        boolean isinteract=ClientPart.read();
                        if(!isinteract) {
                            boolean stillfigthing = true;
                            while (stillfigthing) {
                                stillfigthing = ClientPart.read();
                                if (stillfigthing) {
                                    eventHistory.addLine(ClientPart.read());
                                    eventHistory.addLine(ClientPart.read());
                                    getFenetreInfo().update();
                                }
                            }
                            player.setpV(ClientPart.read());
                            if (player.getpV() <= 0)
                                PersoThread.respawn(player);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    interaction = false;
                    thisInfo.update();
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



    private  void mapload(){
        try {
            carte=ClientPart.read();
            pnjs=ClientPart.read();
            echanges=ClientPart.read();
            coffres=ClientPart.read();
            sorties=ClientPart.read();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(mapPanel!=null) {
            mapPanel.setPnjs(pnjs);
            mapPanel.setPlayer(player);
            mapPanel.setSorties(sorties);
        }
    }

    private void checkdoor(){
        for (Porte p: sorties) {
            System.out.println("distance porte= " +p.distance(player));
            if(p.distance(player)<1) {
                try {
                    int res = JOptionPane.showConfirmDialog(this, "Il y a une porte ici voulez vous traverser?", "Porte", JOptionPane.YES_NO_OPTION);
                    if (res == 0) {
                        interaction = true;
                        ClientPart.write(OutputType.DOOR);
                        ClientPart.write(p.getId());
                        p.traverse(player);
                        mapload();
                        File source = new File("music\\" + player.getLieux().getNomLieu() + ".wav");
                        try {
                            music.setSource(source);
                        }
                        catch (FileNotFoundException | LineUnavailableException | UnsupportedAudioFileException lue){
                            Loggy.writlog(lue.getMessage(),LogLevel.WARNING);
                        }
                        interaction=false;
                        ia.setPnjs(pnjs);
                        this.revalidate();
                        this.repaint();
                    }
                }
                catch (IOException ioe){
                    Loggy.writlog("ERREUR DE CONNEXION",LogLevel.ERROR);
                    JOptionPane.showMessageDialog(null,"UNE ERREUR DE CONNEXION INATENDUE A EU LIEU VEUILLEZ CONTACTER LE SERVICE CLIENT","Erreur",JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ioe);
                }
            }
        }
    }

    public void updatstate(MapState mapState){
        this.setPnjs(mapState.getPnjs());
        this.setCoffres(mapState.getCoffres());


        this.mapPanel.setPnjs(this.pnjs);

        repaint();
        revalidate();
    }
    //getters


    public Soundtrackscontroller getMusic() {
        return music;
    }

    public PersoThread getIa() {
        return ia;
    }

    public DialogueInterface getDialogdealer() {
        return dialogdealer;
    }

    public FullLogInterface getLog() {
        return log;
    }

    public Instant getNextmactiontime() {
        return nextmactiontime;
    }

    public JLabel getPortrait() {
        return portrait;
    }

    public JMenuBar getMenubar() {
        return menubar;
    }

    public JMenu getQuitter() {
        return quitter;
    }

    public PlayerInfo getthisInfo() {
        return thisInfo;
    }

    public DefaultInteractionInterface getDefaultInteractionInterface() {
        return defaultInteractionInterface;
    }

    public InventaireInterface getInventdealer() {
        return inventdealer;
    }

    public QueteInterface getQuetedisplayer() {
        return quetedisplayer;
    }

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

    public Properties getProperties() {
        return properties;
    }

    //setters



    public void setDialogdealer(DialogueInterface dialogdealer) {
        this.dialogdealer = dialogdealer;
    }

    public void setCoffredealer(CoffreInterface coffredealer) {
        this.coffredealer = coffredealer;
    }

    public void setInteraction(boolean interaction) {
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

    public void setEffectvolume(float lvl){
        if(lvl<0)
            lvl=0;
        if(lvl>1)
            lvl=1;
        effectvolume=lvl;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
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

    public void setLog(FullLogInterface log) {
        this.log = log;
    }

    public void setthisInfo(PlayerInfo thisInfo) {
        this.thisInfo = thisInfo;
    }

    public void setInventdealer(InventaireInterface inventdealer) {
        this.inventdealer = inventdealer;
    }

    public void setDefaultInteractionInterface(DefaultInteractionInterface defaultInteractionInterface) {
        this.defaultInteractionInterface = defaultInteractionInterface;
    }

    public void setQuetedisplayer(QueteInterface quetedisplayer) {
        this.quetedisplayer = quetedisplayer;
    }

    public void setNextmactiontime(Instant nextmactiontime) {
        this.nextmactiontime = nextmactiontime;
    }

    public void setPortrait(JLabel portrait) {
        this.portrait = portrait;
    }

    public void setMenubar(JMenuBar menubar) {
        this.menubar = menubar;
    }

    public PlayerInfo getFenetreInfo() {
        return thisInfo;
    }

    private int readtable(String key){
        return Integer.parseInt(properties.getProperty(key));
    }

}

