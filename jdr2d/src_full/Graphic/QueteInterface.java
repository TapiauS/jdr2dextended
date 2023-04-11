package Graphic;

import Entity.*;

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
        this.setLayout(new BorderLayout());
        setjlist(listequete);
        listequete.setVisible(true);
        listequete.setBackground(new Color(0,0,0,0));
        listequete.setOpaque(false);
        listequete.setForeground(Color.white);
        add(listequete,BorderLayout.CENTER);
        //listrecompense
        recompenses=new JList<>();
        setjlist(recompenses);
        recompenses.setVisible(false);
        add(recompenses,BorderLayout.EAST);
        //on initialise description fenetre
        descriptionfenetre=new JLabel();
        descriptionfenetre.setText("Quetes en cours");
        descriptionfenetre.setVisible(true);
        add(descriptionfenetre,BorderLayout.NORTH);
        JPanel buttonpanel=new JPanel();
        buttonpanel.setPreferredSize(new Dimension(INTERACTION_WIDTH,INTERACTION_HEIGH/10));
        add(buttonpanel,BorderLayout.SOUTH);
        //on cree montrecompense
        montrerecompense=new JButton("Recompense");
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
        montrerecompense.setBackground(new Color(0,0,0,0));
        montrerecompense.setOpaque(false);
        montrerecompense.setForeground(Color.white);
        buttonpanel.add(montrerecompense);
        //goback
        goback=new JButton("Revenir en arriére");
        goback.addActionListener(
                e -> {
                    recompenses.setVisible(false);
                    descriptionfenetre.setText("Quêtes en cours");
                    montrerecompense.setVisible(true);
                    quit.setVisible(true);
                    goback.setVisible(false);
                    refreshfocus();
                });
        goback.setVisible(false);
        buttonpanel.add(goback);
        //quit
        quit=new JButton("Quitter");
        quit.addActionListener(
                e -> {
                    returndefault();
                    refreshfocus();
                });
        quit.setVisible(true);
        buttonpanel.add(quit);
        buttonpanel.setBackground(new Color(0,0,0,0));
        buttonpanel.setOpaque(false);
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
        int ListWidth=INTERACTION_WIDTH;
        int ListHeigh=INTERACTION_HEIGH*8/10;
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

    public void updateQuete(){
        String[] retourquete=new String[player.getQueteSuivie().size()];
        ArrayList<Quete> listquete=player.getQueteSuivie();
        for (int i = 0; i < listquete.size(); i++) {
            Objectifs o= player.getQueteSuivie().get(i).getObjectifs().get(0);
            Quete q=listquete.get(i);
            if(o instanceof ObjectifT) {
                retourquete[i]="Vous suivez la quete " + q.getNomQuete() + " dont l'objectif suivant est de parler a ";
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
