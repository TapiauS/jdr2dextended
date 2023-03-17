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
        itemdisplay.setBounds(MapPanel.MAP_WIDTH,INTERACTION_HEIGH,INTERACTION_WIDTH,INTERACTION_HEIGH*9/10);
        itemdisplay.setVisible(true);
        itemdisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemdisplay.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        itemdisplay.setVisibleRowCount(-1);
        itemdisplay.setPreferredSize(new Dimension(INTERACTION_WIDTH,INTERACTION_HEIGH*9/10));
        this.add(itemdisplay);
        //on initialise parentcoffre
        parentscoffre=new ArrayList<>();
        //on rajoute le bouton pick
        equip=new JButton("Equiper ou utiliser");
        equip.setBounds(MapPanel.MAP_WIDTH+INTERACTION_WIDTH/7, (int) (INTERACTION_HEIGH+INTERACTION_HEIGH*9/10),
                INTERACTION_WIDTH/7, (int) (INTERACTION_HEIGH*0.5/10));
        equip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    catch (IOException | ClassNotFoundException io){
                        throw new RuntimeException(io);
                    }
                    refreshfocus();
                }
            }

        });
        equip.setVisible(true);
        this.add(equip);
        // on cree drop
        drop=new JButton("Jeter");
        drop.setBounds(MapPanel.MAP_WIDTH+INTERACTION_WIDTH*3/7, (int) (INTERACTION_HEIGH+INTERACTION_HEIGH*9/10),
                INTERACTION_WIDTH/7, (int) (INTERACTION_HEIGH*0.5/10));
        drop.addActionListener(
                new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
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
                    catch (IOException | ClassNotFoundException io){
                        throw new RuntimeException(io);
                    }
                }});
        drop.setVisible(true);
        this.add(drop);
        //creation du bouton exit
        exit=new JButton("Quitter");
        exit.setBounds(MapPanel.MAP_WIDTH+INTERACTION_WIDTH*5/7, (int) (INTERACTION_HEIGH+INTERACTION_HEIGH*9/10),
                INTERACTION_WIDTH/7, (int) (INTERACTION_HEIGH*0.5/10));
        exit.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        try {
                            ClientPart.write(OutputType.QUIT);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        returndefault();
                        fenetre.setInteraction(false);
                        openedcoffre=null;
                        data=null;
                        refreshfocus();
                    }
                });
        exit.setVisible(true);
        this.add(exit);
        //creation du bouton goback
        goback=new JButton("Revenir en arriére");
        goback.setBounds(MapPanel.MAP_WIDTH+INTERACTION_WIDTH*5/7, (int) (INTERACTION_HEIGH+INTERACTION_HEIGH*9.5/10),
                INTERACTION_WIDTH/7, (int) (INTERACTION_HEIGH*0.5/10));
        goback.addActionListener(
                new ActionListener() {
                    @Override
                        public void actionPerformed(ActionEvent e) {
                        try {
                            ClientPart.write(OutputType.GOBACK);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        try {
                            setOpenedcoffre(ClientPart.read());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                        coffrelvl--;
                        if(coffrelvl==0){
                            goback.setVisible(false);
                            exit.setVisible(true);
                        }
                        refreshfocus();
                        }
        });
        goback.setVisible(false);
        this.add(goback);
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
