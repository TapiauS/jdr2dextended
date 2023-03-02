package Graphic;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsDisplayer extends JFrame {

    private GameInterface fenetre;

    private JButton valider;

    private JButton goback;

    private JSlider volume;

    private float previousvolume;

    private JLabel volumusic;


    //builder

    public SettingsDisplayer(GameInterface fenetre){
        super();
        this.fenetre=fenetre;
        JPanel panel=new JPanel();
        panel.setLayout(new BorderLayout());
        this.setContentPane(panel);

        previousvolume=this.fenetre.getMusic().getVolume()*100;

        volume=new JSlider(0,100, (int) previousvolume);
        volume.addChangeListener(e -> {
            fenetre.getMusic().setVolume(((float) volume.getValue())/100);
        });


        volumusic=new JLabel("Volume de la musique");

        JPanel bottom=new JPanel();
        bottom.setLayout(new BorderLayout());

        panel.add(volumusic,BorderLayout.NORTH);
        panel.add(volume,BorderLayout.CENTER);
        panel.add(bottom,BorderLayout.SOUTH);


        valider=new JButton("Valider");
        //des fonctionnalité vont se greffer ici
        valider.addActionListener(e -> {
            fenetre.getMusic().setVolume(((float) volume.getValue())/100);
            fenetre.setInteraction(false);
            dispose();
        });
        bottom.add(valider,BorderLayout.EAST);

        goback=new JButton("Annuler");
        goback.addActionListener(e->{
            fenetre.getMusic().setVolume(previousvolume/100);
            fenetre.setInteraction(false);
            dispose();
        });
        bottom.add(goback,BorderLayout.WEST);
        this.pack();
        this.setVisible(true);
    }


}
