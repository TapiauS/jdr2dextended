package Graphic;

import Control.ClientPart;
import Control.OutputType;
import Log.LogLevel;
import Log.Loggy;
import jdr2dcore.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class DefaultInteractionInterface extends InteractionInterface{


    private JButton afficheQuete;

    private JButton afficheInventaire;

    private JButton openCoffre;

    private JButton talk;

    private static final int BUTTON_WIDTH=INTERACTION_WIDTH/3;
    private static final int BUTTON_HEIGH=INTERACTION_HEIGH/9;

    //builders

    public DefaultInteractionInterface(GameInterface fenetre, Personnage player) {
        super(fenetre, player);
        //on definit les boutons
        //affichequete
        this.setLayout(new GridLayout(2,2));
        afficheQuete=new JButton("Journal de quete");
        afficheQuete.setVisible(true);
        afficheQuete.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGH));
        afficheQuete.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            fenetre.getQuetedisplayer().updateQuete();
                        } catch (SQLException ex) {
                            //TODO comme d'hab
                            throw new RuntimeException(ex);
                        }
                        setVisible(false);
                        refreshfocus();
                    }
                }
        );
        this.add(afficheQuete);
        //openCoffre
        openCoffre=new JButton("Ouvrir un coffre");
        openCoffre.setVisible(true);
        openCoffre.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGH));
        //openCoffre.setLocation(INTERACTION_WIDTH+BUTTON_WIDTH,INTERACTION_HEIGH+BUTTON_HEIGH*3);
        openCoffre.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (Coffre c : fenetre.getCoffres()) {
                            if(c.distance(player)<2){
                                try {
                                    ClientPart.write(OutputType.PICK);
                                    ClientPart.write(c.getId());
                                    boolean available;
                                    available=ClientPart.read();
                                    if (available) {
                                        fenetre.getCoffredealer().setOpenedcoffre(c);
                                        setVisible(false);
                                        fenetre.setInteraction(true);
                                        break;
                                    }
                                    else {
                                        fenetre.getEventHistory().addLine("Ce coffre est déja fouillé par quelqu'un");
                                    }
                                } catch (IOException | ClassNotFoundException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                        refreshfocus();
                    }
        });
        this.add(openCoffre);
        //talk
        talk=new JButton("Parler");
        talk.setVisible(true);
        talk.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGH));
        talk.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (PNJ p: fenetre.getPnjs()) {
                            if(p.distance(player)<1&&p.getpV()>0){
                                System.out.println("distance=" + p.distance(player));
                                for (Echange ech: fenetre.getEchanges()) {
                                    if(ech.getParleur()==p) {
                                        fenetre.getDialogdealer().setPresentechange(ech);
                                        fenetre.getDialogdealer().setVisible(true);
                                        fenetre.getDialogdealer().buildObserver();
                                        setVisible(false);
                                        fenetre.setInteraction(true);
                                        break;
                                    }
                                }
                            }
                        }
                        refreshfocus();
                    }

                }
        );
        this.add(talk);
        //inventaire
        afficheInventaire=new JButton("Inventaire");
        afficheInventaire.setVisible(true);
        afficheInventaire.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGH));
        afficheInventaire.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fenetre.getDefaultInteractionInterface().setVisible(false);
                        fenetre.getInventdealer().setVisible(true);
                        try {
                            ClientPart.write(OutputType.INVENTAIRE);
                            fenetre.getInventdealer().setOpenedcoffre(player.getInventaire());
                            fenetre.setInteraction(true);
                            fenetre.pack();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
        );
        this.add(afficheInventaire);

    }


    //getters

    public JButton getAfficheQuete() {
        return afficheQuete;
    }

    public JButton getAfficheInventaire() {
        return afficheInventaire;
    }

    public JButton getOpenCoffre() {
        return openCoffre;
    }

    //setters


    public void setAfficheQuete(JButton afficheQuete) {
        this.afficheQuete = afficheQuete;
    }

    public void setAfficheInventaire(JButton afficheInventaire) {
        this.afficheInventaire = afficheInventaire;
    }

    public void setOpenCoffre(JButton openCoffre) {
        this.openCoffre = openCoffre;
    }


}
