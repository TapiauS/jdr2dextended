package Graphic;

import DAO.QueteDAO;
import jdr2dcore.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

public class DialogueInterface extends InteractionInterface {
    private Echange presentechange;

    private String[] reponses;

    private Personnage player;
    private Hashtable<String,Echange> linkrepEchange;

    private JButton valider;

    private JLabel question;
    private JList choix;

    private ArrayList<EventListenerTalk>  observerT;


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
        valider.addActionListener(e -> {
            setPresentechange(linkrepEchange.get(choix.getSelectedValue().toString()));
            for (int i=0;i<observerT.size();i++) {
                if (observerT.get(i) instanceof ObjectifT) {
                    if (((ObjectifT) observerT.get(i)).getConvaincre() == presentechange.getId()) {
                        observerT.get(i).update();
                        observerT.remove(i);
                    }
                }
            }
            if (presentechange.isQuete()) {
                if(!player.getQueteSuivie().contains(presentechange.getQuete())) {
                    player.addsQuete(presentechange.getQuete());
                    fenetre.getEventHistory().addLine("Vous venez de revoir la quête "+presentechange.getQuete().getNomQuete());
                    try {
                        QueteDAO.update(presentechange.getQuete(),player);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    setPresentechange(new Echange(presentechange.getParleur(),presentechange.getQuestion(),
                            " Vous avez déja recu ce travail ",null));
                }
            }
            refreshfocus();
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

    public void setPlayer(Personnage player){
        this.player = player;
        this.observerT=new ArrayList<>();
        for (Quete q : player.getQueteSuivie()) {
            for (Objectifs o : q.getObjectifs() ) {
                if(o instanceof ObjectifT && q.getObjectifs().indexOf(o)==0) {
                    this.addObserverT((EventListenerTalk) o);
                }
            }
        }
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

    public void addObserverT(EventListenerTalk objectif){
        this.observerT.add(objectif);
    }

    public void updateObserver(Quete q){
        for (Objectifs o : q.getObjectifs() ) {
            if(o instanceof ObjectifT && q.getObjectifs().indexOf(o)==0) {
                this.addObserverT((EventListenerTalk) o);
            }
        }
    }
}


