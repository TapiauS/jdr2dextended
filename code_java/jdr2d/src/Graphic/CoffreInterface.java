package Graphic;

import jdr2dcore.Coffre;
import jdr2dcore.Personnage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        cofrename=new JLabel();
        cofrename.setBounds((int) (MapPanel.MAP_WIDTH*1.01), (int) (INTERACTION_HEIGH*1.01), INTERACTION_WIDTH, INTERACTION_HEIGH /10);
        cofrename.setVisible(true);
        this.add(cofrename);
        data=new String[0];
        choix=new JList<>();
        choix.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        choix.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        choix.setVisibleRowCount(-1);
        choix.setBounds(MapPanel.MAP_WIDTH, (int) (INTERACTION_HEIGH + 1.01*INTERACTION_HEIGH /10), INTERACTION_WIDTH, (int) (INTERACTION_HEIGH *0.8));
        choix.setPreferredSize(new Dimension(INTERACTION_WIDTH,(int) (INTERACTION_HEIGH *0.8)));
        choix.setVisible(true);
        this.add(choix);
        //on définit pick
        pick=new JButton("Pick");
        pick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(choix.getSelectedIndex()>-1) {
                    if (!(openedcoffre.getContenu().get(choix.getSelectedIndex()) instanceof Coffre)) {
                        player.addObjet(openedcoffre.getContenu().get(choix.getSelectedIndex()));
                        fenetre.getEventHistory().addLine(player.getNomPersonnage() + " a ramassé :" + openedcoffre.getContenu().get(choix.getSelectedIndex()).getNomObjet());
                        openedcoffre.remove(choix.getSelectedIndex());
                        udpateref();
                        choix.setListData(data);

                    } else {
                        parentcoffres.add(openedcoffre);
                        Coffre newcoffre = (Coffre) openedcoffre.getContenu().get(choix.getSelectedIndex());
                        setOpenedcoffre(newcoffre);
                        coffrelvl++;
                        exit.setVisible(false);
                        goBack.setVisible(true);
                    }
                }
                fenetre.requestFocus();
                repaint();
                revalidate();
            }
        });
        pick.setBounds(MapPanel.MAP_WIDTH+ INTERACTION_WIDTH /5, (int) (INTERACTION_HEIGH +9.01*INTERACTION_HEIGH /10), INTERACTION_WIDTH /5,(int) (INTERACTION_HEIGH *0.1));
        pick.setVisible(true);
        this.add(pick);
        //on definit exit
        exit=new JButton("Quit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                fenetre.setInteraction(false);
                openedcoffre=null;
                data=null;
                repaint();
                revalidate();
                fenetre.requestFocus();
            }
        });
        this.add(exit);
        exit.setVisible(true);
        exit.setBounds(MapPanel.MAP_WIDTH+3* INTERACTION_WIDTH /5, (int) (INTERACTION_HEIGH +9.01* INTERACTION_HEIGH /10), INTERACTION_WIDTH /5,(int) (INTERACTION_HEIGH *0.05));
        //on définit goback
        goBack=new JButton("Go back");
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setOpenedcoffre(parentcoffres.get(parentcoffres.size()-1));
                parentcoffres.remove(parentcoffres.size()-1);
                coffrelvl--;
                if(coffrelvl==0){
                    goBack.setVisible(false);
                    exit.setVisible(true);
                }
                fenetre.requestFocus();
                repaint();
                revalidate();
            }
        });
        goBack.setVisible(false);
        this.add(goBack);

        goBack.setBounds(MapPanel.MAP_WIDTH+3* INTERACTION_WIDTH /5, (int) (INTERACTION_HEIGH +9.01* INTERACTION_HEIGH /10), INTERACTION_WIDTH /5,(int) (INTERACTION_HEIGH *0.05));
        this.setBounds(MapPanel.MAP_WIDTH, INTERACTION_HEIGH, INTERACTION_WIDTH, INTERACTION_HEIGH);
        this.setVisible(false);
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

    public void setPlayer(Personnage player) {
        this.player = player;
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
