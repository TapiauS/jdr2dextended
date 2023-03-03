package Graphic;

import jdr2dcore.*;


import javax.swing.*;
import java.awt.*;

public class PlayerInfo extends JPanel {

    private GameInterface fenetre;

    private JTextArea infodisplayer;

    private JTextArea equipement;

    private String infos;

    private String equip;

    private final static String[] refs={"Nom: ","pV: ","pVmax: ","degats: ","redudegats: "};

    private final static String[] equipref={"Armes: ","Tête: ","Torse: ","Mains: ","Jambes: ","Botte: "};

    private Personnage player;

    public static final int PLAYERINFO_WIDTH=GameInterface.WINDOW_WIDTH*4/10;

    public static final int PLAYERINFO_HEIGH= (int) (GameInterface.WINDOWS_HEIGH*2.9/10);

    //builders

    public PlayerInfo(Personnage player,GameInterface fenetre){
        super();
        this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        this.player=player;
        this.fenetre=fenetre;
        //on gere infodiplayer
        infodisplayer=new JTextArea();
        infodisplayer.setBackground(Color.white);
        infodisplayer.setEditable(false);
        infodisplayer.setRequestFocusEnabled(false);
        infodisplayer.setBounds(MapPanel.MAP_WIDTH,0,PLAYERINFO_WIDTH/2,PLAYERINFO_HEIGH);
        infodisplayer.setVisible(true);
        infodisplayer.setFont(new Font("Segoe Script", Font.BOLD, 15));
        infodisplayer.setForeground(Color.red);
        //on gere equipement
        equipement=new JTextArea();
        equipement.setBackground(Color.white);
        equipement.setEditable(false);
        equipement.setRequestFocusEnabled(false);
        equipement.setBounds(MapPanel.MAP_WIDTH+PLAYERINFO_WIDTH/2,0,PLAYERINFO_WIDTH/2,PLAYERINFO_HEIGH);
        equipement.setVisible(true);
        equipement.setFont(new Font("Segoe Script", Font.BOLD, 15));
        equipement.setForeground(Color.red);
        this.update();
        this.setBounds(MapPanel.MAP_WIDTH,0,PLAYERINFO_WIDTH,PLAYERINFO_HEIGH);
        this.add(equipement);
        this.add(infodisplayer);
        this.setVisible(true);
    }

    //getters


    public JTextArea getInfodisplayer() {
        return infodisplayer;
    }

    public String getInfos() {
        return infos;
    }

    public Personnage getPlayer() {
        return player;
    }

    //setters


    public void setEquip(String equip) {
        this.equip = equip;
        this.equipement.setText(equip);
        this.fenetre.repaint();
        this.fenetre.revalidate();
    }

    public void setEquipement(JTextArea equipement) {
        this.equipement = equipement;
    }

    public void setInfodisplayer(JTextArea infodisplayer) {
        this.infodisplayer = infodisplayer;
    }

    public void setInfos(String infos) {
        this.infos = infos;
        this.infodisplayer.setText(infos);
        this.fenetre.repaint();
        this.fenetre.revalidate();
    }

    public void setPlayer(Personnage player) {
        this.player = player;
    }

    //methodes

    public void update(){
        StringBuffer equip=new StringBuffer();
        int deg=0;
        int redudeg=0;
        for (Arme a: player.getArme()) {
            if(a.getNbrmain()>0)
                equip.append(equipref[0]).append(a.getNomObjet()).append('\n');
            deg+=a.getDeg();
            redudeg+=a.getRedudeg();
        }
        for (Armure ar: player.getArmure()) {
            switch (ar.getTypearmure()){
                case "CASQUE": equip.append(equipref[1]).append(ar.getNomObjet()).append('\n');
                                break;
                case "TORSE":equip.append(equipref[2]).append(ar.getNomObjet()).append('\n');
                                break;
                case "GANT":equip.append(equipref[3]).append(ar.getNomObjet()).append('\n');
                            break;
                case "JAMBIERE":equip.append(equipref[4]).append(ar.getNomObjet()).append('\n');
                            break;
                case "BOTTE":equip.append(equipref[5]).append(ar.getNomObjet()).append('\n');
                            break;
                default : break;
            }
            deg+=ar.getDeg();
            redudeg+=ar.getRedudeg();
        }
        this.setEquip(equip.toString());
        StringBuffer info=new StringBuffer();
        info.append(refs[0]).append(player.getNomPersonnage())
                .append('\n').append(refs[1]).append(player.getpV())
                .append('\n').append(refs[2]).append(player.getpVmax())
                .append('\n').append(refs[3]).append(deg)
                .append('\n').append(refs[4]).append(redudeg);
        this.setInfos(info.toString());
    }
}
