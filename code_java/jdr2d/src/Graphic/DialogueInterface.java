package Graphic;

import jdr2dcore.Echange;
import jdr2dcore.Personnage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class DialogueInterface extends InteractionInterface {
    private Echange presentechange;

    private String[] reponses;

    private Hashtable<String,Echange> linkrepEchange;

    private JButton valider;

    private JLabel question;
    private JList choix;


    private String[] data;

    //builder

    //TODO:pas finis du tout mais ne pas toucher tant que des PNJ ne sont pas ajouté au jeu
    public DialogueInterface(GameInterface fenetre,Personnage player){
        super(fenetre,player);
        question=new JLabel();
        question.setBounds(MapPanel.MAP_WIDTH,INTERACTION_HEIGH,INTERACTION_WIDTH,INTERACTION_HEIGH/10);
        data=new String[0];
        linkrepEchange=new Hashtable<>();
        choix=new JList<>();
        choix.setBounds(MapPanel.MAP_WIDTH,INTERACTION_HEIGH+INTERACTION_HEIGH/10,INTERACTION_WIDTH, (int) (INTERACTION_HEIGH*0.8));
        valider=new JButton();
        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPresentechange(linkrepEchange.get(choix.getSelectedValue().toString()));
                refreshfocus();
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


