package Graphic;

import Control.ClientPart;
import Control.OutputType;
import jdr2dcore.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static Logging.Jdr2dLogger.LOGGER;

public class InventaireInterface extends InteractionInterface{


    private JList<Object> itemdisplay;

    private ArrayList<Coffre> parentscoffre;

    private String[] data;

    private JButton equip;

    private JButton drop;

    private JButton exit;

    private JButton goback;

    private int coffrelvl;

    private Coffre openedcoffre;


    public InventaireInterface(GameInterface fenetre, Personnage player) {
        super(fenetre, player);
        //on rajoute la Jlist
        itemdisplay=new JList<>();
        this.setLayout(new BorderLayout());
        //itemdisplay.setBounds(MapPanel.MAP_WIDTH,INTERACTION_HEIGH,INTERACTION_WIDTH,INTERACTION_HEIGH*9/10);
        itemdisplay.setVisible(true);
        itemdisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemdisplay.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        itemdisplay.setVisibleRowCount(-1);
        itemdisplay.setPreferredSize(new Dimension(INTERACTION_WIDTH,INTERACTION_HEIGH*9/10));
        itemdisplay.setBackground(new Color(0,0,0,0));
        itemdisplay.setOpaque(false);
        itemdisplay.setForeground(Color.white);
        this.add(itemdisplay,BorderLayout.NORTH);
        JPanel bottompane=new JPanel();
        bottompane.setBackground(new Color(0,0,0,0));
        bottompane.setOpaque(false);
        //on initialise parentcoffre
        parentscoffre=new ArrayList<>();
        //on rajoute le bouton pick
        equip=new JButton("Equiper ou utiliser");
        equip.addActionListener(e -> {
            if(itemdisplay.getSelectedIndex()>-1) {
                try {
                    ClientPart.write(OutputType.EQUIP);
                    ClientPart.write(itemdisplay.getSelectedIndex());
                    boolean iscoffre=ClientPart.read();
                    if (!iscoffre) {
                        Objet selecteobjet=ClientPart.read();
                        if (selecteobjet instanceof Arme) {
                            try {
                                fenetre.getEventHistory().addLine(ClientPart.read());
                                fenetre.getPlayer().setInventaire(ClientPart.read());
                                fenetre.getPlayer().setArmes(ClientPart.read());
                                fenetre.getFenetreInfo().update();
                            } catch (ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        if (selecteobjet instanceof Armure) {
                            fenetre.getEventHistory().addLine(ClientPart.read());
                            fenetre.getPlayer().setInventaire(ClientPart.read());
                            fenetre.getPlayer().setArmes(ClientPart.read());
                            fenetre.getFenetreInfo().update();
                        }
                        if (selecteobjet instanceof Potion) {
                            Time.drinkpotion((Potion) selecteobjet, player);
                            player.removeObjet(selecteobjet);
                            fenetre.getEventHistory().addLine(player.getNomPersonnage() + " a bu la potion :"
                                    + selecteobjet.getNomObjet());
                        }
                        setOpenedcoffre(ClientPart.read());
                    } else {
                        setOpenedcoffre(ClientPart.read());
                        coffrelvl++;
                        exit.setVisible(false);
                        goback.setVisible(true);
                    }
                }
                catch (IOException ioe) {
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
                refreshfocus();
            }
        });
        equip.setVisible(true);
        bottompane.add(equip);
        // on cree drop
        drop=new JButton("Jeter");
        drop.addActionListener(
                e -> {
                    try {
                        if (itemdisplay.getSelectedIndex() > -1) {
                            ClientPart.write(OutputType.DROP);
                            ClientPart.write(itemdisplay.getSelectedIndex());
                            openedcoffre.setContenu(ClientPart.read());
                            udpateref();
                            fenetre.getMapPanel().repaint();
                            fenetre.getMapPanel().revalidate();
                            itemdisplay.setListData(data);
                            refreshfocus();
                        }
                    }
                    catch (IOException ioe) {
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
        drop.setVisible(true);
        bottompane.add(drop);
        //creation du bouton exit
        exit=new JButton("Quitter");
        exit.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
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
        exit.setVisible(true);
        bottompane.add(exit);
        goback=new JButton("Revenir en arriére");
        goback.addActionListener(
                new ActionListener() {
                    @Override
                        public void actionPerformed(ActionEvent e) {
                        try {
                            ClientPart.write(OutputType.GOBACK);
                            setOpenedcoffre(ClientPart.read());
                            coffrelvl--;
                            if (coffrelvl == 0) {
                                goback.setVisible(false);
                                exit.setVisible(true);
                            }
                            refreshfocus();
                        } catch (IOException ioe) {
                            LOGGER.severe(ioe.getMessage());
                            JOptionPane.showMessageDialog(null, "Une erreur inconnue a eu lieu", "", JOptionPane.ERROR_MESSAGE);
                            System.exit(-3);
                        } catch (ClassNotFoundException cne) {
                            LOGGER.severe(cne.getMessage());
                            JOptionPane.showMessageDialog(null, "Une erreur inconnue a eu lieu", "", JOptionPane.ERROR_MESSAGE);
                            System.exit(-2);
                        } catch (Exception ex) {
                            LOGGER.severe(ex.getMessage());
                            JOptionPane.showMessageDialog(null, "Une erreur inconnue a eu lieu", "", JOptionPane.ERROR_MESSAGE);
                            System.exit(-5);
                        }
                    }
        });

        goback.setVisible(false);
        bottompane.add(goback);
        bottompane.setVisible(true);
        this.add(bottompane,BorderLayout.SOUTH);
        System.out.println();
    }



    //getters


    public JList<Object> getItemdisplay() {
        return itemdisplay;
    }

    public void setItemdisplay(JList<Object> itemdisplay) {
        this.itemdisplay = itemdisplay;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public JButton getEquip() {
        return equip;
    }

    public void setEquip(JButton equip) {
        this.equip = equip;
    }

    public JButton getDrop() {
        return drop;
    }

    public void setDrop(JButton drop) {
        this.drop = drop;
    }

    public JButton getExit() {
        return exit;
    }


    //setters


    public void setExit(JButton exit) {
        this.exit = exit;
    }

    public JButton getGoback() {
        return goback;
    }

    public void setGoback(JButton goback) {
        this.goback = goback;
    }

    public int getCoffrelvl() {
        return coffrelvl;
    }

    public void setCoffrelvl(int coffrelvl) {
        this.coffrelvl = coffrelvl;
    }

    public Coffre getOpenedcoffre() {
        return openedcoffre;
    }

    public void setOpenedcoffre(Coffre openedcoffre) {
        this.openedcoffre = openedcoffre;
        this.udpateref();
        this.itemdisplay.setListData(data);
        this.setVisible(true);
        this.repaint();
        this.revalidate();
    }

    //methodes

    private void udpateref(){
        data=new String[openedcoffre.getContenu().size()];
        for (int i = 0; i < data.length; i++) {
            data[i]=openedcoffre.getContenu().get(i).getNomObjet();
        }
    }
}
