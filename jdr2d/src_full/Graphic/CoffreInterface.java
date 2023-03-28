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
        //on initialise le label
        cofrename=new JLabel();
        cofrename.setBounds((int) (MapPanel.MAP_WIDTH*1.0), (int) (INTERACTION_HEIGH*1.01), INTERACTION_WIDTH, INTERACTION_HEIGH /10);
        cofrename.setVisible(true);
        this.add(cofrename);
        //on initialise le panneau de choix
        choix.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        choix.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        choix.setVisibleRowCount(-1);/*
        choix.setBounds(MapPanel.MAP_WIDTH, (int) (INTERACTION_HEIGH + 1.01*INTERACTION_HEIGH /10), INTERACTION_WIDTH, (int) (INTERACTION_HEIGH *0.8));*/
        choix.setPreferredSize(new Dimension(INTERACTION_WIDTH,(int) (INTERACTION_HEIGH *0.8)));
        choix.setVisible(true);
        this.add(choix);
        //on définit pick
        pick=new JButton("Pick");
        pick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });/*
        pick.setBounds(MapPanel.MAP_WIDTH+ INTERACTION_WIDTH /5, (int) (INTERACTION_HEIGH +9.01*INTERACTION_HEIGH /10),
                INTERACTION_WIDTH /5,(int) (INTERACTION_HEIGH *0.1));*/
        pick.setVisible(true);
        this.add(pick);
        //on definit exit
        exit=new JButton("Quit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        this.add(exit);
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
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                setOpenedcoffre(parentcoffres.get(parentcoffres.size()-1));
                parentcoffres.remove(parentcoffres.size()-1);
                coffrelvl--;
                if(coffrelvl==0){
                    goBack.setVisible(false);
                    exit.setVisible(true);
                }
                refreshfocus();
            }
        });
        goBack.setVisible(false);
        this.add(goBack);
/*
        goBack.setBounds(MapPanel.MAP_WIDTH+3* INTERACTION_WIDTH /5, (int) (INTERACTION_HEIGH +9.01* INTERACTION_HEIGH /10)
                , INTERACTION_WIDTH /5,(int) (INTERACTION_HEIGH *0.05));
*/

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
