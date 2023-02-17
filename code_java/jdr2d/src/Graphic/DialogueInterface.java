package Graphic;

import jdr2dcore.Echange;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class DialogueInterface extends JPanel {
    private Echange presentechange;

    private String[] reponses;

    private Hashtable<String,Echange> linkrepEchange;

    private JButton valider;

    private JLabel question;
    private JList choix;


    private String[] data;
    private static final int DIAL_WIDTH= (int) (GameInterface.WINDOW_WIDTH*0.4);
    private static final int DIAL_HEIGH= (int) (GameInterface.WINDOW_WIDTH*0.3);

    //builder

    public DialogueInterface(){
        super();
        question=new JLabel();
        question.setBounds(MapPanel.MAP_WIDTH,DIAL_HEIGH,DIAL_WIDTH,DIAL_HEIGH/10);
        data=new String[0];
        linkrepEchange=new Hashtable<>();
        choix=new JList<>();
        choix.setBounds(MapPanel.MAP_WIDTH,DIAL_HEIGH+DIAL_HEIGH/10,DIAL_WIDTH, (int) (DIAL_HEIGH*0.8));
        valider=new JButton();
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPresentechange(linkrepEchange.get(choix.getSelectedValue().toString()));
                repaint();
                revalidate();
            }
        });
        this.setVisible(false);
        this.add(choix);
        }


        //setters


    public void setPresentechange(Echange presentechange) {
        if(presentechange.getDialogueSuivant()!=null) {
            this.presentechange = presentechange;
            udpateref();
            choix.setListData(data);
            question.setText(presentechange.getParleur().getNomPersonnage()+":"+presentechange.getReponse());
        }
        else{
         this.presentechange=presentechange;
         question.setText(presentechange.getParleur().getNomPersonnage()+":"+presentechange.getReponse());
         choix.setVisible(false);
         valider.setVisible(false);
        }
    }

    public void setChoix(JList choix) {
        this.choix = choix;
    }

    //methodes

    private void udpateref(){
        data=new String[presentechange.getDialogueSuivant().length];
        linkrepEchange=new Hashtable<>();
        for (int i = 0; i < data.length; i++) {
            data[i]=presentechange.getDialogueSuivant()[i].getQuestion();
            linkrepEchange.put(presentechange.getDialogueSuivant()[i].getQuestion(),presentechange.getDialogueSuivant()[i]);
        }
    }
}


