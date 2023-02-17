package Graphic;

import jdr2dcore.Coffre;
import jdr2dcore.Personnage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CoffreInterface extends JPanel {

    private ArrayList<Coffre> parentcoffres;

    private Coffre openedcoffre;

    private Personnage player;

    private JButton pick;

    private JButton goBack;

    private JButton exit;

    private JList<Object> choix;
    private String[] data;

    private JLabel cofrename;

    private int coffrelvl;
    private static final int COFFRE_WIDTH= (int) (GameInterface.WINDOW_WIDTH*0.4);
    private static final int COFFRE_HEIGH= (int) (GameInterface.WINDOW_WIDTH*0.3);


    //builder

    public CoffreInterface(Personnage player){
        super();
        this.player=player;
        this.parentcoffres=new ArrayList<>();
        coffrelvl=0;
        cofrename=new JLabel();
        cofrename.setBounds(MapPanel.MAP_WIDTH,COFFRE_HEIGH,COFFRE_WIDTH,COFFRE_HEIGH/10);
        cofrename.setVisible(true);
        data=new String[0];
        choix=new JList<>();
        choix.setBounds(MapPanel.MAP_WIDTH,COFFRE_HEIGH+COFFRE_HEIGH/10,COFFRE_WIDTH, (int) (COFFRE_HEIGH*0.8));
        choix.setVisible(true);
        this.add(choix);
        //on définit pick
        pick=new JButton("Pick");
        pick.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(openedcoffre.getContenu().get(choix.getSelectedIndex()) instanceof Coffre)) {
                    player.addObjet(openedcoffre.getContenu().get(choix.getSelectedIndex()));
                    openedcoffre.remove(choix.getSelectedIndex());
                    //GameInterface game = (GameInterface) getParent().getParent().g;
                    udpateref();
                    choix.setListData(data);
                    //game.getEventHistory().addLine(player.getNomPersonnage() + " a ramassé :" + openedcoffre.getContenu().get(choix.getSelectedIndex()).getNomObjet());
                }
                else{
                    parentcoffres.add((Coffre) openedcoffre.getContenu().get(choix.getSelectedIndex()));
                    Coffre newcoffre= (Coffre) openedcoffre.getContenu().get(choix.getSelectedIndex());
                    setOpenedcoffre(newcoffre);
                    coffrelvl++;
                    exit.setVisible(false);
                    goBack.setVisible(true);
                }
                repaint();
                revalidate();
            }
        });
        pick.setBounds(MapPanel.MAP_WIDTH+COFFRE_WIDTH/5,COFFRE_HEIGH+9*COFFRE_HEIGH/10,COFFRE_WIDTH/5,(int) (COFFRE_HEIGH*0.1));
        pick.setVisible(true);
        this.add(pick);
        //on definit exit
        exit=new JButton("Quit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                openedcoffre=null;
                data=null;
                repaint();
                revalidate();
            }
        });
        this.add(exit);
        exit.setVisible(true);
        exit.setBounds(MapPanel.MAP_WIDTH+3*COFFRE_WIDTH/5,COFFRE_HEIGH+9*COFFRE_HEIGH/10,COFFRE_WIDTH/5,(int) (COFFRE_HEIGH*0.1));
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
                repaint();
                revalidate();
            }
        });
        goBack.setVisible(false);
        this.add(goBack);
        goBack.setBounds(MapPanel.MAP_WIDTH+3*COFFRE_WIDTH/5,COFFRE_HEIGH+9*COFFRE_HEIGH/10,COFFRE_WIDTH/5,(int) (COFFRE_HEIGH*0.1));
        this.setBounds(MapPanel.MAP_WIDTH,COFFRE_HEIGH,COFFRE_WIDTH,COFFRE_HEIGH);
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
        System.out.println("debugage de l'interface");
        this.openedcoffre = openedcoffre;
        this.udpateref();
        this.cofrename.setText(openedcoffre.getNomObjet());
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
