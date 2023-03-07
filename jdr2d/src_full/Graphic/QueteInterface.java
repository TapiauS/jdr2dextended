package Graphic;

import DAO.EchangeDAO;
import jdr2dcore.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueteInterface extends InteractionInterface{

    private JList<Object> listequete;


    private JList<Object> recompenses;

    private String[] dataquete;

    private String[] dataobjet;

    private JButton montrerecompense;

    private JButton quit;

    private JButton goback;

    private JLabel descriptionfenetre;


    public QueteInterface(GameInterface fenetre, Personnage player) {
        super(fenetre, player);
        //on initialise les tableaux
        dataquete=new String[0];
        dataobjet=new String[0];
        //on cree les Jlists
        //listquete

        setjlist(listequete);
        listequete.setVisible(true);
        //listrecompense
        recompenses=new JList<>();
        setjlist(recompenses);
        recompenses.setVisible(false);
        //on initialise description fenetre
        descriptionfenetre=new JLabel();
        descriptionfenetre.setBounds((int) (MapPanel.MAP_WIDTH*1.0),  INTERACTION_HEIGH, INTERACTION_WIDTH, INTERACTION_HEIGH /10);
        descriptionfenetre.setText("Quetes en cours");
        descriptionfenetre.setVisible(true);
        //on cree montrecompense
        montrerecompense=new JButton("Recompense");
        montrerecompense.setBounds(MapPanel.MAP_WIDTH+INTERACTION_WIDTH/5,INTERACTION_HEIGH+INTERACTION_HEIGH*9/10
                ,INTERACTION_WIDTH/5,INTERACTION_HEIGH/10);
        montrerecompense.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(listequete.getSelectedIndex()>-1){
                            int refquete=listequete.getSelectedIndex();
                            updaterec(player.getQueteSuivie().get(refquete));
                            descriptionfenetre.setText("Recompense de la quete "+player.getQueteSuivie().get(refquete).getNomQuete());
                            recompenses.setListData(dataobjet);
                            recompenses.setVisible(true);
                            montrerecompense.setVisible(false);
                            quit.setVisible(false);
                            goback.setVisible(true);
                        }
                        refreshfocus();
                    }
                });
        montrerecompense.setVisible(true);
        this.add(montrerecompense);
        //goback
        goback=new JButton("Revenir en arriére");
        goback.setBounds(MapPanel.MAP_WIDTH+INTERACTION_WIDTH/3,INTERACTION_HEIGH+INTERACTION_HEIGH*9/10
                ,INTERACTION_WIDTH/3,INTERACTION_HEIGH/10);
        goback.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        recompenses.setVisible(false);
                        descriptionfenetre.setText("Quêtes en cours");
                        montrerecompense.setVisible(true);
                        quit.setVisible(true);
                        goback.setVisible(false);
                        refreshfocus();
                    }
                });
        goback.setVisible(false);
        this.add(goback);
        //quit
        quit=new JButton("Quitter");
        quit.setBounds(MapPanel.MAP_WIDTH+INTERACTION_WIDTH*3/5,INTERACTION_HEIGH+INTERACTION_HEIGH*9/10
                ,INTERACTION_WIDTH/5,INTERACTION_HEIGH/10);
        quit.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        returndefault();
                        refreshfocus();
                    }
                });
        quit.setVisible(true);
        this.add(quit);

    }

    //getters


    public String[] getDataquete() {
        return dataquete;
    }

    public String[] getDataobjet() {
        return dataobjet;
    }

    public JList<Object> getListequete() {
        return listequete;
    }

    public JList<Object> getRecompenses() {
        return recompenses;
    }

    public JButton getMontrerecompense() {
        return montrerecompense;
    }

    public JButton getQuit() {
        return quit;
    }

    public JButton getGoback() {
        return goback;
    }

    public JLabel getDescriptionfenetre() {
        return descriptionfenetre;
    }

    //setters

    @Override
    public void setPlayer(Personnage player) throws SQLException {
        listequete=new JList<>();
        this.player = player;
        this.updateQuete();
    }



    public void setDataquete(String[] dataquete) {
        this.dataquete = dataquete;
        listequete.setListData(dataquete);
    }

    public void setDataobjet(String[] dataobjet) {
        this.dataobjet = dataobjet;
    }

    public void setListequete(JList<Object> listequete) {
        this.listequete = listequete;
    }

    public void setRecompenses(JList<Object> recompenses) {
        this.recompenses = recompenses;
    }

    public void setMontrerecompense(JButton montrerecompense) {
        this.montrerecompense = montrerecompense;
    }

    public void setQuit(JButton quit) {
        this.quit = quit;
    }

    public void setGoback(JButton goback) {
        this.goback = goback;
    }

    public void setDescriptionfenetre(JLabel descriptionfenetre) {
        this.descriptionfenetre = descriptionfenetre;
    }


    //methodes
    private void setjlist(JList<Object> ref){
        int ListPosWidth=MapPanel.MAP_WIDTH;
        int ListPosHeigh=INTERACTION_HEIGH+INTERACTION_HEIGH*9/10;
        int ListWidth=INTERACTION_WIDTH;
        int ListHeigh=INTERACTION_HEIGH*8/10;
        ref.setBounds(ListPosWidth,ListPosHeigh,ListWidth,ListHeigh);
        ref.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ref.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        ref.setVisibleRowCount(-1);
        ref.setPreferredSize(new Dimension(ListWidth,ListHeigh));
    }

    private void updaterec(Quete ref){
        int reclength=ref.getRecompenses().length;
        String[] datar=new String[reclength];
        for (int i = 0; i <reclength ; i++) {
            datar[i]=ref.getRecompenses()[i].getNomObjet();
        }
        this.setDataobjet(datar);
    }

    public void updateQuete() throws SQLException {
        String[] retourquete=new String[player.getQueteSuivie().size()];
        ArrayList<Quete> listquete=player.getQueteSuivie();
        for (int i = 0; i < listquete.size(); i++) {
            Objectifs o= player.getQueteSuivie().get(i).getObjectifs().get(0);
            Quete q=listquete.get(i);
            if(o instanceof ObjectifT) {
                retourquete[i]="Vous suivez la quete " + q.getNomQuete() + " dont l'objectif suivant est de parler a "
                        + EchangeDAO.getparleur(o.getId());
            }
            if(o instanceof ObjectifF){
                retourquete[i]="Vous suivez la quete " + q.getNomQuete() + " dont l'objectif suivant est de trouver l'objet "
                        + ((ObjectifF) o).getObjetquete().getNomObjet();
            }
            if(o instanceof ObjectifK){
                retourquete[i]="Vous suivez la quete " + q.getNomQuete() + " dont l'objectif suivant est de tuer "
                        + ((ObjectifK) o).getTarget().getNomPersonnage();
            }
        }
        this.setDataquete(retourquete);
        this.setVisible(true);
    }

}