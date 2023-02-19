package Graphic;

import jdr2dcore.*;


import javax.swing.*;
import java.awt.*;

public class PlayerInfo extends JPanel {

    private GameInterface fenetre;

    private JTextArea infodisplayer;

    private String infos;

    private final static String[] refs={"Nom: ","pV: ","pVmax: ","degats: ","redudegats: "};

    private Personnage player;

    public static final int PLAYERINFO_WIDTH=GameInterface.WINDOW_WIDTH*4/10;

    public static final int PLAYERINFO_HEIGH= (int) (GameInterface.WINDOWS_HEIGH*2.9/10);

    //builders

    public PlayerInfo(Personnage player,GameInterface fenetre){
        super();
        this.setLayout(new BorderLayout());
        this.player=player;
        this.fenetre=fenetre;
        infodisplayer=new JTextArea();
        infodisplayer.setBackground(Color.white);
        infodisplayer.setEditable(false);
        infodisplayer.setRequestFocusEnabled(false);
        infodisplayer.setBounds(MapPanel.MAP_WIDTH,0,PLAYERINFO_WIDTH,PLAYERINFO_HEIGH);
        infodisplayer.setVisible(true);
        infodisplayer.setFont(new Font("Segoe Script", Font.BOLD, 15));
        infodisplayer.setForeground(Color.red);
        this.update();
        this.setBounds(MapPanel.MAP_WIDTH,0,PLAYERINFO_WIDTH,PLAYERINFO_HEIGH);
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
        int deg=0;
        int redudeg=0;
        for (Arme a: player.getArme()) {
            deg+=a.getDeg();
            redudeg+=a.getRedudeg();
        }
        for (Armure ar: player.getArmure()) {
            deg+=ar.getDeg();
            redudeg+=ar.getRedudeg();
        }
        StringBuffer info=new StringBuffer();
        info.append(refs[0]).append(player.getNomPersonnage())
                .append('\n').append(refs[1]).append(player.getpV())
                .append('\n').append(refs[2]).append(player.getpVmax())
                .append('\n').append(refs[3]).append(deg)
                .append('\n').append(refs[4]).append(redudeg);
        this.setInfos(info.toString());
    }
}
