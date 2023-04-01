package Graphic;

import Control.ClientPart;
import Control.OutputType;
import ServerPart.DAO.ObjetDAO;
import jdr2dcore.Coffre;
import jdr2dcore.Objet;
import jdr2dcore.Personnage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static Logging.Jdr2dLogger.LOGGER;

public class CoffreInterface extends InteractionInterface {



    private ArrayList<Coffre> parentcoffres;

    private Coffre openedcoffre;



    private JButton pick;

    private JButton goBack;

    private JButton exit;

    private JList<Object> choix;
    private String[] data;

    private JLabel cofrename;

    private int coffrelvl;


    //builder

    public CoffreInterface(Personnage player,GameInterface fenetre){
        super(fenetre,player);
        this.parentcoffres=new ArrayList<>();
        coffrelvl=0;
        data=new String[0];
        choix=new JList<>();
        this.setLayout(new BorderLayout());
        //on initialise le label
        cofrename=new JLabel();
        cofrename.setPreferredSize(new Dimension(INTERACTION_WIDTH, INTERACTION_HEIGH /10));
        cofrename.setVisible(true);
        cofrename.setForeground(Color.white);
        cofrename.setBackground(new Color(0,0,0,0));
        cofrename.setOpaque(false);
        cofrename.setForeground(Color.white);
        this.add(cofrename,BorderLayout.NORTH);
        //on initialise le panneau de choix
        choix.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        choix.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        choix.setVisibleRowCount(-1);
        choix.setPreferredSize(new Dimension(INTERACTION_WIDTH,(int) (INTERACTION_HEIGH *0.8)));
        choix.setVisible(true);
        choix.setBackground(new Color(0,0,0,0));
        choix.setOpaque(false);
        choix.setForeground(Color.white);
        this.add(choix,BorderLayout.EAST);

        JPanel bottompanel=new JPanel();
        //on définit pick
        pick=new JButton("Pick");
        pick.addActionListener(e -> {
            try {
                if(choix.getSelectedIndex()>-1) {
                    ClientPart.write(OutputType.PICK);
                    //Objet selectobjet=openedcoffre.getContenu().get(choix.getSelectedIndex());
                    ClientPart.write(choix.getSelectedIndex());
                    boolean iscoffre=ClientPart.read();
                    if (!iscoffre) {
                        boolean cantake=ClientPart.read();
                        if(cantake) {
                            fenetre.getPlayer().setInventaire(ClientPart.read());
                            fenetre.getEventHistory().addLine(ClientPart.read());
                            setOpenedcoffre(ClientPart.read());
                            udpateref();
                        }
                        else{
                            fenetre.getEventHistory().addLine("Vous ne pouvez pas porter autant");
                        }
                    } else {
                        Coffre newcoffre = ClientPart.read();
                        parentcoffres.add(openedcoffre);
                        setOpenedcoffre(newcoffre);
                        coffrelvl++;
                        exit.setVisible(false);
                        goBack.setVisible(true);
                    }
                }
                refreshfocus();
            } catch (IOException ioe) {
                LOGGER.severe(ioe.getMessage());
                JOptionPane.showMessageDialog(null,"Une erreur inconnue a eu lieu","",JOptionPane.ERROR_MESSAGE);
                System.exit(-3);
            } catch (ClassNotFoundException cne) {
                LOGGER.severe(cne.getMessage());
                JOptionPane.showMessageDialog(null,"Une erreur inconnue a eu lieu","",JOptionPane.ERROR_MESSAGE);
                System.exit(-2);
            }
            catch (Exception ex){
                LOGGER.severe(ex.getMessage());
                JOptionPane.showMessageDialog(null,"Une erreur inconnue a eu lieu","",JOptionPane.ERROR_MESSAGE);
                System.exit(-5);
            }
        });
        pick.setVisible(true);
        bottompanel.add(pick);
        //on definit exit
        exit=new JButton("Quit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ClientPart.write(OutputType.QUIT);
                    returndefault();
                    fenetre.setInteraction(false);
                    openedcoffre = null;
                    data = null;
                    refreshfocus();
                }
                catch (IOException ioe) {
                    LOGGER.severe(ioe.getMessage());
                    JOptionPane.showMessageDialog(null,"Une erreur inconnue a eu lieu","",JOptionPane.ERROR_MESSAGE);
                    System.exit(-3);
                }
                catch (Exception ex){
                    LOGGER.severe(ex.getMessage());
                    JOptionPane.showMessageDialog(null,"Une erreur inconnue a eu lieu","",JOptionPane.ERROR_MESSAGE);
                    System.exit(-5);
                }
            }
        });
        bottompanel.add(exit);
        exit.setVisible(true);/*
        exit.setBounds(MapPanel.MAP_WIDTH+3* INTERACTION_WIDTH /5, (int) (INTERACTION_HEIGH +9.01* INTERACTION_HEIGH /10),
                INTERACTION_WIDTH /5,(int) (INTERACTION_HEIGH *0.05));*/
        //on définit goback
        goBack=new JButton("Go back");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ClientPart.write(OutputType.GOBACK);
                    setOpenedcoffre(parentcoffres.get(parentcoffres.size() - 1));
                    parentcoffres.remove(parentcoffres.size() - 1);
                    coffrelvl--;
                    if (coffrelvl == 0) {
                        goBack.setVisible(false);
                        exit.setVisible(true);
                    }
                }
                catch (IOException ioe) {
                    LOGGER.severe(ioe.getMessage());
                    JOptionPane.showMessageDialog(null,"Une erreur inconnue a eu lieu","",JOptionPane.ERROR_MESSAGE);
                    System.exit(-3);
                }
                catch (Exception ex){
                    LOGGER.severe(ex.getMessage());
                    JOptionPane.showMessageDialog(null,"Une erreur inconnue a eu lieu","",JOptionPane.ERROR_MESSAGE);
                    System.exit(-5);
                }
                refreshfocus();
            }
        });
        goBack.setVisible(false);
        bottompanel.add(goBack);
        bottompanel.setBackground(new Color(0,0,0,0));
        this.add(bottompanel,BorderLayout.SOUTH);
        this.fenetre.requestFocus();
    }


    //getters


    public Personnage getPlayer() {
        return player;
    }

    public Coffre getOpenedcoffre() {
        return openedcoffre;
    }

    public JButton getPick() {
        return pick;
    }


    //setters


    public void setOpenedcoffre(Coffre openedcoffre) {
        this.openedcoffre = openedcoffre;
        this.udpateref();
        this.cofrename.setText(openedcoffre.getNomObjet()+" contient les objets suivants:");
        this.choix.setListData(data);
        this.setVisible(true);
        this.repaint();
        this.revalidate();
    }

    public void setPick(JButton pick) {
        this.pick = pick;
    }



    public void setGoBack(JButton goBack) {
        this.goBack = goBack;
    }

    public void setExit(JButton exit) {
        this.exit = exit;
    }


    public void setData(String[] data) {
        this.data = data;
    }

    public void setCofrename(JLabel cofrename) {
        this.cofrename = cofrename;
    }

    public void setCoffrelvl(int coffrelvl) {
        this.coffrelvl = coffrelvl;
    }

    private void udpateref(){
        data=new String[openedcoffre.getContenu().size()];
        for (int i = 0; i < data.length; i++) {
            data[i]=openedcoffre.getContenu().get(i).getNomObjet();
        }
    }
}
