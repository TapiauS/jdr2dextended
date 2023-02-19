package Graphic;

import jdr2dcore.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                    Objet selecteobjet=openedcoffre.getContenu().get(itemdisplay.getSelectedIndex());
                    if (!(selecteobjet instanceof Coffre newcoffre)) {
                        if(selecteobjet instanceof Arme){
                            try {
                                player.removeObjet(selecteobjet);
                                player.addArme((Arme) selecteobjet);
                                fenetre.getEventHistory().addLine(player.getNomPersonnage() + " a equipé l'arme:"
                                        + selecteobjet.getNomObjet());
                                fenetre.getFenetreInfo().update();
                            } catch (SQLException ex) {
                                //TODO gere exception
                                throw new RuntimeException(ex);
                            }
                        }
                        if(selecteobjet instanceof Armure) {
                            try {
                                player.removeObjet(selecteobjet);
                                player.addArmure((Armure) selecteobjet);
                                fenetre.getEventHistory().addLine(player.getNomPersonnage() + " a equipé l'armure:"
                                        + selecteobjet.getNomObjet());
                            } catch (SQLException ex) {
                                //TODO gere exception
                                throw new RuntimeException(ex);
                            }
                        }
                        if(selecteobjet instanceof Potion) {
                            Time.drinkpotion((Potion) selecteobjet, player);
                            player.removeObjet(selecteobjet);
                            fenetre.getEventHistory().addLine(player.getNomPersonnage() + " a bu la potion :"
                                    + selecteobjet.getNomObjet());
                        }
                        udpateref();
                        itemdisplay.setListData(data);
                    } else {
                        parentscoffre.add((Coffre) selecteobjet);
                        setOpenedcoffre(newcoffre);
                        coffrelvl++;
                        exit.setVisible(false);
                        goback.setVisible(true);
                    }
                }
                refreshfocus();
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
                    if(itemdisplay.getSelectedIndex()>-1){
                        Objet selecteobjet=openedcoffre.getContenu().get(itemdisplay.getSelectedIndex());
                        try {
                            boolean incoffre=false;
                            player.dropObjet(selecteobjet);
                            for (Coffre c: fenetre.getCoffres()) {
                                if (player.distance(c) == 0) {
                                    c.add(selecteobjet);
                                    incoffre=true;
                                    break;
                                }
                            }
                            if(!incoffre)
                                fenetre.getCoffres().add((Coffre) ((Coffre) (new Coffre(selecteobjet))
                                    .setX(player.getX()).setY(player.getY()).setLieux(player.getLieux())).setNomObjet("tas"));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        itemdisplay.setListData(data);
                        udpateref();
                        refreshfocus();
                    }
                }
                });
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
                            setOpenedcoffre(parentscoffre.get(parentscoffre.size()-1));
                            parentscoffre.remove(parentscoffre.size()-1);
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
