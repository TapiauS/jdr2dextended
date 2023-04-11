package Graphic;

import Control.ClientPart;
import Control.OutputType;
import Entity.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static Logging.Jdr2dLogger.LOGGER;
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
        afficheQuete.setMaximumSize(new Dimension(INTERACTION_WIDTH/5,INTERACTION_HEIGH/5));
        JPanel affichquetepanel=new JPanel();
        affichquetepanel.setBackground(new Color(0,0,0,0));
        affichquetepanel.setOpaque(false);
        afficheQuete.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fenetre.getQuetedisplayer().updateQuete();
                        setVisible(false);
                        refreshfocus();
                    }
                }
        );
        affichquetepanel.add(afficheQuete);
        this.add(affichquetepanel);
        //openCoffre
        openCoffre=new JButton("Ouvrir un coffre");
        openCoffre.setVisible(true);
        openCoffre.setMaximumSize(new Dimension(INTERACTION_WIDTH/5,INTERACTION_HEIGH/5));
        JPanel openCoffrepanel=new JPanel();
        openCoffrepanel.setBackground(new Color(0,0,0,0));
        openCoffrepanel.setOpaque(false);
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
                            }
                        }
                        refreshfocus();
                    }
        });
        openCoffrepanel.add(openCoffre);
        this.add(openCoffrepanel);
        //talk
        talk=new JButton("Parler");
        talk.setVisible(true);
        talk.setMaximumSize(new Dimension(INTERACTION_WIDTH/5,INTERACTION_HEIGH/5));
        JPanel talkpanel=new JPanel();
        talkpanel.setBackground(new Color(0,0,0,0));
        talkpanel.setOpaque(false);
        talk.addActionListener(
                e -> {
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
        );
        talkpanel.add(talk);
        this.add(talkpanel);
        //inventaire
        afficheInventaire=new JButton("Inventaire");
        afficheInventaire.setVisible(true);
        afficheInventaire.setMaximumSize(new Dimension(INTERACTION_WIDTH/5,INTERACTION_HEIGH/5));
        JPanel inventpanel=new JPanel();
        inventpanel.setOpaque(false);
        inventpanel.setBackground(new Color(0,0,0,0));
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
                        } catch (IOException ioe) {
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
                }
        );
        inventpanel.add(afficheInventaire);
        this.add(inventpanel);

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
