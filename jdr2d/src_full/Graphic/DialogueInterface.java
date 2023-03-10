package Graphic;

import Control.ClientPart;
import jdr2dcore.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class DialogueInterface extends InteractionInterface {
    private Echange presentechange;

    private ArrayList<Quete> quetesdonnees;

    private ArrayList<Integer> objectifrealisesisid;
    private String[] reponses;

    private Personnage player;
    private Hashtable<String,Echange> linkrepEchange;

    private JButton valider;

    private JLabel question;
    private JList<Object> choix;

    private ArrayList<EventListenerTalk>  observerT;


    private String[] data;

    //builder

    //TODO:pas finis du tout mais ne pas toucher tant que des PNJ ne sont pas ajouté au jeu
    public DialogueInterface(GameInterface fenetre,Personnage player){
        super(fenetre,player);
        //this.setLayout(null);
        this.setVisible(false);
        quetesdonnees=new ArrayList<>();
        objectifrealisesisid =new ArrayList<>();
        question=new JLabel();
        question.setBounds(MapPanel.MAP_WIDTH,INTERACTION_HEIGH,INTERACTION_WIDTH,INTERACTION_HEIGH/10);
        this.add(question);
        data=new String[0];
        linkrepEchange=new Hashtable<>();
        choix=new JList<>();
        choix.setLayoutOrientation(JList.VERTICAL_WRAP);
        choix.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        choix.setVisibleRowCount(-1);
        choix.setBounds(MapPanel.MAP_WIDTH, (int) (INTERACTION_HEIGH + 1.01*INTERACTION_HEIGH /10), INTERACTION_WIDTH, (int) (INTERACTION_HEIGH *0.8));
        choix.setPreferredSize(new Dimension(INTERACTION_WIDTH,(int) (INTERACTION_HEIGH *0.8)));
        this.add(choix);
        valider=new JButton("Répondre");
        valider.addActionListener(e -> {
            setPresentechange(linkrepEchange.get(choix.getSelectedValue().toString()));
            for (int i=0;i<observerT.size();i++) {
                if (observerT.get(i) instanceof ObjectifT) {
                    if (((ObjectifT) observerT.get(i)).getConvaincre() == presentechange.getId()) {
                        objectifrealisesisid.add(((ObjectifT) observerT.get(i)).getId());
                        observerT.remove(i);
                    }
                }
            }
            if (presentechange.isQuete()) {
                if(!player.getQueteSuivie().contains(presentechange.getQuete())) {
                    player.addsQuete(presentechange.getQuete());
                    quetesdonnees.add(presentechange.getQuete());
                    fenetre.getEventHistory().addLine("Vous venez de revoir la quête "+presentechange.getQuete().getNomQuete());
                }
                else {
                    setPresentechange(new Echange(presentechange.getParleur(),presentechange.getQuestion(),
                            " Vous avez déja recu ce travail ",null));
                }
            }
            refreshfocus();
        });
        valider.setBounds(MapPanel.MAP_WIDTH+INTERACTION_WIDTH/3,INTERACTION_HEIGH+INTERACTION_HEIGH*9/10,INTERACTION_WIDTH/3, (int) (INTERACTION_HEIGH*0.1));
        this.add(valider);
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
         fenetre.getEventHistory().addLine(presentechange.getParleur().getNomPersonnage()+":"+presentechange.getReponse());
            try {
                ClientPart.write(quetesdonnees);
                ClientPart.write(objectifrealisesisid);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.setVisible(false);
         fenetre.setInteraction(false);
         fenetre.getDefaultInteractionInterface().setVisible(true);
         presentechange.getParleur().setInteract(false);
         quetesdonnees.removeAll(quetesdonnees);
         objectifrealisesisid.removeAll(objectifrealisesisid);
         refreshfocus();
        }
    }

    public void setChoix(JList<Object> choix) {
        this.choix = choix;
    }

    public void setPlayer(Personnage player){
        this.player = player;
        buildObserver();
    }

    //methodes

    public void buildObserver(){
        this.observerT=new ArrayList<>();
        for (Quete q : player.getQueteSuivie()) {
            for (Objectifs o : q.getObjectifs() ) {
                if(o instanceof ObjectifT && q.getObjectifs().indexOf(o)==0) {
                    this.addObserverT((EventListenerTalk) o);
                }
            }
        }
    }
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


