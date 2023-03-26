package Graphic;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Hashtable;
import java.util.Properties;

public class SettingsDisplayer extends JFrame {

    private GameInterface fenetre;
    private final Properties previousversion;
    private JButton valider;
    private JButton goback;
    private JSlider volume;
    private float previousvolume;
    private JLabel volumusic;
    private JLabel effectvolume;
    private JSlider effectvalue;
    private float previouseffect;
    private JLabel est;
    private JTextField valeurest;
    private JLabel ouest ;
    private JTextField valeurouest;
    private JLabel sud;
    private JTextField valeursud;
    private JLabel nord;
    private JTextField valeurnord;
    private JLabel figth;
    private JTextField figthkey;
    private JLabel invent;
    private JTextField inventkey;
    private JLabel quete;
    private JTextField questkey;
    private JLabel coffre;
    private JTextField coffrekey;
    private JLabel dial;
    private JTextField dialkey;

    private JButton defaultsettings;

    //builder
    public SettingsDisplayer(GameInterface fenetre){
        super();
        this.fenetre=fenetre;
        JPanel panel=new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints=new GridBagConstraints();
        this.setContentPane(panel);

        previousvolume=this.fenetre.getMusic().getVolume()*100;

        volume=new JSlider(0,100, (int) previousvolume);
        volume.addChangeListener(e -> {
            fenetre.getMusic().setVolume(((float) volume.getValue())/100);
        });


        volumusic=new JLabel("Volume de la musique");

        JPanel bottom=new JPanel();
        bottom.setLayout(new BorderLayout());

        constraints.fill=GridBagConstraints.HORIZONTAL;
        constraints.gridx=0;
        constraints.gridy=0;
        panel.add(volumusic,constraints);
        constraints.gridx=1;
        constraints.gridy=0;
        panel.add(volume,constraints);


        effectvolume=new JLabel("Volume des effets");
        effectvalue=new JSlider(0,100, (int) (100*Float.parseFloat(fenetre.getProperties().getProperty("EFFECTVOLUME"))));

        constraints.gridx=2;
        constraints.gridy=0;
        panel.add(effectvolume,constraints);
        constraints.gridx=3;
        panel.add(effectvalue,constraints);


        est=new JLabel("Aller vers l'est");
        valeurest=new JTextField();


        constraints.gridx=0;
        constraints.gridy=1;
        panel.add(est,constraints);
        constraints.gridx=1;
        panel.add(valeurest,constraints);

        ouest=new JLabel("Aller vers l'ouest");
        valeurouest=new JTextField();

        constraints.gridx=2;
        constraints.gridy=1;
        panel.add(ouest,constraints);
        constraints.gridx=3;
        panel.add(valeurouest,constraints);

        sud=new JLabel("Aller vers le sud");
        valeursud=new JTextField();


        constraints.gridx=0;
        constraints.gridy=2;
        panel.add(sud,constraints);
        constraints.gridx=1;
        panel.add(valeursud,constraints);

        nord=new JLabel("Aller vers le nord");
        valeurnord=new JTextField();


        constraints.gridx=2;
        constraints.gridy=2;
        panel.add(nord,constraints);
        constraints.gridx=3;
        panel.add(valeurnord,constraints);

        coffre=new JLabel("Ouvrir un coffre");
        coffrekey=new JTextField();


        constraints.gridx=0;
        constraints.gridy=4;
        panel.add(coffre,constraints);
        constraints.gridx=1;
        panel.add(coffrekey,constraints);

        invent=new JLabel("Ouvrir l'inventaire");
        inventkey=new JTextField();


        constraints.gridx=2;
        constraints.gridy=4;
        panel.add(invent,constraints);
        constraints.gridx=3;
        panel.add(inventkey,constraints);

        quete=new JLabel("Journal de quete");
        questkey=new JTextField();


        constraints.gridx=0;
        constraints.gridy=5;
        panel.add(quete,constraints);
        constraints.gridx=1;
        panel.add(questkey,constraints);

        dial=new JLabel("Commencer un dialogue");
        dialkey=new JTextField();


        constraints.gridx=2;
        constraints.gridy=5;
        panel.add(dial,constraints);
        constraints.gridx=3;
        panel.add(dialkey,constraints);

        figth=new JLabel("Commencer un combat");
        figthkey=new JTextField();


        constraints.gridx=0;
        constraints.gridy=6;
        panel.add(figth,constraints);
        constraints.gridx=1;
        panel.add(figthkey,constraints);

        valider=new JButton("Valider");
        //des fonctionnalité vont se greffer ici
        valider.addActionListener(e -> {
            fenetre.getMusic().setVolume(((float) volume.getValue())/100);
            Properties fenetreprop=fenetre.getProperties();
            fenetreprop.setProperty("MUSICVOLUME", String.valueOf((float) volume.getValue()/100));
            fenetreprop.setProperty("EFFECTVOLUME",String.valueOf((float) effectvalue.getValue()/100));
            try (FileOutputStream fos = new FileOutputStream("control.properties")) {
                fenetreprop.store(fos,"");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            fenetre.setInteraction(false);
            dispose();
        });
        constraints.gridx=2;
        constraints.gridy=7;
        constraints.gridwidth=2;
        add(valider,constraints);

        previousversion=new Properties(fenetre.getProperties());
        goback=new JButton("Annuler");
        goback.addActionListener(e->{
            fenetre.getMusic().setVolume(previousvolume/100);
            fenetre.setInteraction(false);
            fenetre.setProperties(previousversion);
            try {
                OutputStream outputStream=new FileOutputStream("control.properties");
                fenetre.getProperties().store(outputStream,"");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });
        constraints.gridx=0;
        constraints.gridy=7;
        constraints.gridwidth=2;
        add(goback,constraints);

        defaultsettings=new JButton("Réglages par défaut");
        defaultsettings.addActionListener(e->{
            try {
                Properties defaultprop = new Properties();
                InputStream input=new FileInputStream("defaultcontrol.properties");
                defaultprop.load(input);
                fenetre.setProperties(defaultprop);
                iniate();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });
        constraints.gridx=4;
        constraints.gridy=7;
        constraints.gridwidth=2;
        panel.add(defaultsettings,constraints);
        iniate();
        this.pack();
        this.setVisible(true);
    }


    private void setField(JTextField target,String key){
        target.setText(KeyEvent.getKeyText(Integer.parseInt(fenetre.getProperties().getProperty(key))));
        target.setEditable(false);
        if(target.getKeyListeners().length<1) {
            target.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    target.setText(KeyEvent.getKeyText(e.getKeyCode()));
                    fenetre.getProperties().setProperty(key, String.valueOf(e.getKeyCode()));
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });


            target.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (target.getBounds().contains(e.getLocationOnScreen()))
                        target.requestFocus();
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
    }

    private void iniate(){
        setField(inventkey,"INVENTAIRE");
        setField(valeurest,"MOVEEAST");
        setField(valeurouest,"MOVEWEST");
        setField(valeursud,"MOVESOUTH");
        setField(valeurnord,"MOVENORTH");
        setField(coffrekey,"COFFRE");
        setField(questkey,"QUETE");
        setField(figthkey,"FIGTH");
        setField(dialkey,"TALK");
    }

}
