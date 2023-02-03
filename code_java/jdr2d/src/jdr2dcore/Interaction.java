package jdr2dcore;

import DAO.QueteDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Interaction {
    protected Personnage joueur;
    protected PNJ opposant;

    protected Echange dialogue;

    protected boolean agressif;

    protected ArrayList<EventListenerK> observerK;

    protected ArrayList<EventListenerTalk> observerT;


    //getters


    public Personnage getJoueur() {
        return joueur;
    }

    public PNJ getOpposant() {
        return opposant;
    }

    public Echange getDialogue() {
        return dialogue;
    }

    public boolean getAgressif() {
        return agressif;
    }

    public ArrayList<EventListenerK> getObserverK() {
        return observerK;
    }

    public ArrayList<EventListenerTalk> getObserverT() {
        return observerT;
    }

    //setters


    public Interaction setJoueur(Personnage joueur) {
        this.joueur = joueur;
        for (Quete q : joueur.getQueteSuivie()) {
            for (Objectifs o : q.getObjectifs() ) {
                if (o instanceof ObjectifK && q.getObjectifs().indexOf(o)==0) {
                    this.addObserverK((EventListenerK) o);
                }
                if(o instanceof ObjectifT && q.getObjectifs().indexOf(o)==0) {
                    this.addObserverT((EventListenerTalk) o);
                }
            }
        }
        return this;
    }

    public Interaction setOpposant(PNJ opposant) {
        this.opposant = opposant;
        return this;
    }

    public Interaction setDialogue(Echange dialogue) {
        if (dialogue.getQuestion() == null) {
            this.dialogue = dialogue;
            return this;
        } else throw new IllegalArgumentException("On doit avoir un dialogue de début de conversation");
    }



    public Interaction setAgressif(boolean agressif) {
        this.agressif = agressif;
        return this;
    }

    public Interaction setObserverK(ArrayList<EventListenerK> observerK) {
        this.observerK = observerK;
        return this;
    }

    public Interaction setObserverT(ArrayList<EventListenerTalk> observerT){
        this.observerT=observerT;
        return this;
    }


    //builders

    public Interaction() {
        this.setJoueur(new Personnage());
        this.setOpposant(null)
                .setObserverK(new ArrayList<EventListenerK>())
                .setObserverT(new ArrayList<EventListenerTalk>());
    }

    public Interaction(Personnage joueur, PNJ opposant) {
        this.setObserverK(new ArrayList<EventListenerK>())
                .setObserverT(new ArrayList<EventListenerTalk>())
                .setJoueur(joueur)
                .setOpposant(opposant);

    }

    public Interaction(Personnage joueur, PNJ opposant, Echange dialogue, boolean agressif) {
        this.setObserverK(new ArrayList<EventListenerK>())
                .setObserverT(new ArrayList<EventListenerTalk>())
                .setJoueur(joueur)
                .setOpposant(opposant)
                .setDialogue(dialogue)
                .setAgressif(agressif);
    }

    //methode

    public void notifyOneobs(EventListenerK ev) {
        ev.update();
    }

    public Interaction addObserverT(EventListenerTalk ev){
        this.observerT.add(ev);
        return this;
    }

    public Interaction removeObserT(EventListenerTalk ev){
        this.observerT.remove(ev);
        return this;
    }

    public Interaction addObserverK(EventListenerK ev) {
        this.observerK.add(ev);
        return this;
    }

    public Interaction removeObserK(EventListenerK ev) {
        this.observerK.remove(ev);
        return this;
    }

    public void notifyOneobsT(EventListenerTalk ev){
        ev.update();
    }

    public Boolean combat() {
        while (this.getJoueur().getpV() > 0 && this.getOpposant().getpV() > 0) {
            this.getOpposant().setpV(this.getOpposant().getpV() - this.getJoueur().bagarre(this.getOpposant()));
            this.getJoueur().setpV(this.getJoueur().getpV() - this.getOpposant().bagarre(this.getJoueur()));
        }
        if (this.getOpposant().getpV() <= 0) {
            for (EventListenerK e : this.getObserverK()) {
                if (e instanceof ObjectifK) {
                    if(((ObjectifK) e).getTarget().getId()==this.opposant.getId()) {
                        this.notifyOneobs(e);
                        this.removeObserK(e);
                    }
                }
            }
            return true;
        } else return false;
    }

    public void dialogue() throws SQLException {
        // Attention ! quand on crée un arbre de dialgue la position des dialogues donneurs de quete et des dialogues objectifs d'une quete doit être bien reflechie.
        //le point d'acces
        Scanner scanner = new Scanner(System.in);
        System.out.println(this.getOpposant().getNomPersonnage() + " :" + this.getDialogue().getReponse());
        Echange nextechange = this.getDialogue();

        if (nextechange.isQuete()) {
            if(!joueur.getQueteSuivie().contains(nextechange.getQuete())) {
                this.joueur.addsQuete(nextechange.getQuete());
                QueteDAO.update(nextechange.getQuete(),this.getJoueur());
            }
            else {
                nextechange=new Echange(this.opposant,nextechange.getQuestion()," Vous avez déja recu ce travail ",null);
                System.out.println(this.opposant.getNomPersonnage() + " : " + nextechange.getReponse());
                return;
            }
        }

        do {
            int entre = 0;
            nextechange.dialogue();
            System.out.println("Choisissez le numero de votre réponse :");
            entre = scanner.nextInt();
            for (int i=0;i<this.getObserverT().size();i++) {
                if (this.getObserverT().get(i) instanceof ObjectifT) {
                    if (((ObjectifT) this.getObserverT().get(i)).getConvaincre() == nextechange.getDialogueSuivant()[entre].getId()) {
                        this.getObserverT().get(i).update();
                    }
                }
            }
            nextechange = nextechange.getDialogueSuivant()[entre];
            if(nextechange.isObjectifquete()&&!this.getObserverT().contains(nextechange.getObjectifT())) {
                System.out.println("Je passe par le cas ou on a pas encore la quete");
                nextechange = nextechange.getDialoguealternatif();
            }
            if (nextechange.isQuete()) {
                if(!joueur.getQueteSuivie().contains(nextechange.getQuete())) {
                    System.out.println("J'ai bien recu la quete "+nextechange.getQuete().getNomQuete());
                    this.joueur.addsQuete(nextechange.getQuete());
                }
                else {
                    nextechange=new Echange(this.opposant,nextechange.getQuestion()," Vous avez déja recu ce travail ",null);
                    System.out.println(this.opposant.getNomPersonnage() + " : " + nextechange.getReponse());
                    return;
                }
            }
            System.out.println(this.opposant.getNomPersonnage() + " : " + nextechange.getReponse());
        } while (nextechange.getDialogueSuivant() != null);
        System.out.println("Je sors de la boucle de dialogue");
    }
}
