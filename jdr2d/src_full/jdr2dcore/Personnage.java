package jdr2dcore;

import ServerPart.Control.PersoThread;
import ServerPart.DAO.ObjectifsDAO;
import ServerPart.DAO.ObjetDAO;
import Graphic.GameInterface;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Personnage extends Point implements EventListenerQuete {
    protected int id;
    protected String nomPersonnage;
    protected int pV;
    protected int pvmax;
    protected Race race;
    protected Coffre inventaire;
    protected ArrayList<Arme> armes;
    protected ArrayList<Armure> armure;
    protected ArrayList<Quete> queteSuivie;
    protected ArrayList<Potion> effetpotion;
    protected ArrayList<LocalDateTime> datefin;

    protected ArrayList<EventListenerF> observerF ;

    private GameInterface fenetre;


    //getters


    public GameInterface getFenetre() {
        return fenetre;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Quete> getQueteSuivie() {
        return queteSuivie;
    }


    public String getNomPersonnage(){
        return nomPersonnage;
    }

    public int getpV(){
        return pV;
    }

    public int getpVmax(){
        return pvmax;
    }

    public Race getRace(){
        return race;
    }

    public Coffre getInventaire(){
        return inventaire;
    }

    public  ArrayList<Arme> getArme(){
        return armes;
    }

    public ArrayList<Armure> getArmure() {
        return armure;
    }

    public ArrayList<Potion> getEffetpotion() {
        return effetpotion;
    }

    public ArrayList<EventListenerF> getObserverF() {
        return observerF;
    }

    

    //setters


    public void setFenetre(GameInterface fenetre) {
        this.fenetre = fenetre;
        if(this.pV<=0)
            PersoThread.respawn(this);
    }

    public void setId(int id) {
        this.id = id;
    }

    public Personnage setQueteSuivie(ArrayList<Quete> queteSuivie) {
        this.queteSuivie = queteSuivie;
        return this;
    }

    public Personnage setRace(Race race) {
        this.race = race;
        return this;
    }

    public Personnage setpV(int pV) {
        if(pV>=this.getpVmax()){
            this.pV=pvmax;
        }
        else {
            this.pV = pV;
        }
        if(this.pV<=0&&fenetre!=null)
            PersoThread.respawn(this);
        return this;
    }

    public Personnage setPvmax(int pvmax) {
        this.pvmax = pvmax;
        return this;
    }

    public Personnage setNomPersonnage(String nomPersonnage) {
        this.nomPersonnage = nomPersonnage;
        return this;
    }

    public Personnage setInventaire(Coffre inventaire) {
        this.inventaire = inventaire;
        return this;
    }

    public Personnage setdateFin(ArrayList<LocalDateTime> datefin){
        this.datefin=datefin;
        return this;
    }
    // gestion des arrays


    public Personnage addPotion(Potion p){
        this.effetpotion.add(p);
        this.setPvmax(this.getpVmax()+p.getEffets()[3]);
        this.setpV(this.pV+p.getEffets()[2]);
        this.datefin.add(LocalDateTime.now().plus(p.getDuree()));
        return this;
    }

    public Personnage removePotion(Potion p){
        int i=this.effetpotion.indexOf(p);
        this.effetpotion.remove(p);
        this.setPvmax(this.getpVmax()-p.getEffets()[3]);
        this.datefin.remove(i-1);
        return this;
    }

    public Personnage addObserver(EventListenerF of){
        this.observerF.add(of);
        return this;
    }

    public Personnage removeObserver(EventListenerF of){
        this.observerF.remove(of);
        return this;
    }

    public Personnage addsQuete(Quete queteaf){
        this.queteSuivie.add(queteaf);
        queteaf.addPersonnage(this);
        for (Objectifs o: queteaf.getObjectifs()) {
            if(o instanceof ObjectifF){
                this.addObserver((ObjectifF) o);
            }
        }
        return this;
    }

    public Personnage removesQuete(Quete queteaf){
        this.queteSuivie.remove(queteaf);
        return this;
    }

    public Personnage setObserverF(ArrayList<EventListenerF> observerF) {
        this.observerF = observerF;
        return this;
    }

    //addition d'une arme en gérant le nombre de mains équipée

    public Personnage addArme(Arme arme) throws SQLException {
        int compteurmain=0;
        ObjetDAO.equip(this,arme);
        for(Arme a:this.getArme()){
            compteurmain=compteurmain+a.getNbrmain();
        }
        if(compteurmain>2) {
            for (int i=0;i<this.armes.size() ; i++) {
                if (this.armes.get(i).nbrmain != 0) {
                    this.addObjet(this.armes.get(i));
                    this.removeArme(this.armes.get(i));
                }
            }
        }
        if(compteurmain==2){
            if(arme.getNbrmain()==2){
                for (int i=0;i<this.armes.size() ; i++) {
                    if (this.armes.get(i).nbrmain != 0) {
                        this.addObjet(this.armes.get(i));
                        this.removeArme(this.armes.get(i));
                    }
                }
                this.armes.add(arme);
            }
            else{
                Arme a=this.armes.get(armes.size()-1);
                this.removeArme(a);
                this.armes.add(arme);
                this.addObjet(a);
            }
        } else if (compteurmain==1 || compteurmain==0) {
            if(arme.getNbrmain()==2){
                for (int i=0;i<this.armes.size() ; i++) {
                    if (this.armes.get(i).nbrmain != 0) {
                        this.addObjet(this.armes.get(i));
                        this.removeArme(this.armes.get(i));
                    }
                }
                this.armes.add(arme);
            }
            else{
                this.armes.add(arme);
            }
        }
        return this;
    }

    public Personnage removeArme(Arme arme) throws SQLException {
        this.armes.remove(arme);
        ObjetDAO.desequip(arme);
        this.addObjet(arme);
        return this;
    }

    public Personnage addArmure(Armure armure) throws SQLException {
        boolean emplacementlibre=true;
        Armure armurer=new Armure();
        ObjetDAO.equip(this,armure);
        for(Armure a:this.getArmure()){
            if(a.getTypearmure().equals(armure.getTypearmure())){
                emplacementlibre=false;
                armurer=a;
            }
        }
        if(emplacementlibre) {
            this.armure.add(armure);
        }
        else{
            this.removArmure(armurer);
            this.armure.add(armure);
        }
        return this;
    }



    public Personnage addObjet(Objet objet){
        this.inventaire.add(objet);
        for (int j=0;j<this.observerF.size();j++) {
            if(observerF.get(j) instanceof ObjectifF){
                if(objet.getId()==((ObjectifF) observerF.get(j)).getObjetquete().getId()) {
                    notifyoneEventF(observerF.get(j));
                    try {
                        ObjectifsDAO.setobj(this,(ObjectifF) observerF.get(j));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    this.removeObserver(observerF.get(j));
                }
            }
        }
        return this;
    }

    public Personnage dropObjet(Objet objet) throws SQLException {
        this.inventaire.remove(objet);
        ObjetDAO.dropObjet(objet,this);
        return this;
    }

    public Personnage removeObjet(Objet objet){
        this.inventaire.remove(objet);
        return this;
    }


    public Personnage removArmure(Armure armure) throws SQLException {
        this.armure.remove(armure);
        ObjetDAO.desequip(armure);
        this.inventaire.add(armure);
        return this;
    }

    public Personnage setArmure(ArrayList<Armure> armure) {
        this.armure = armure;
        return this;
    }

    public Personnage setArmes(ArrayList<Arme> armes) {
        this.armes = armes;
        return this;
    }

    public Personnage setEffetpotion(ArrayList<Potion> effetpotion) {
        this.effetpotion = effetpotion;
        return this;
    }

    //Builders

    public Personnage(){
        super();
        ArrayList<Arme> armedefault=new ArrayList<>(List.of(new Arme("Poing",0,0,0,0)));
        ArrayList<Armure> armuredefault=new ArrayList<Armure>(List.of(new Armure("Peau",0,0,0)));
        this.setArmes(armedefault)
                .setArmure(armuredefault)
                .setNomPersonnage("tki")
                .setPvmax(1)
                .setpV(1)
                .setInventaire(null)
                .setQueteSuivie(null)
                .setRace(null)
                .setdateFin(new ArrayList<LocalDateTime>())
                .setEffetpotion(new ArrayList<Potion>(List.of(new Potion("Systeme digestif",0, new int[]{0, 0, 0, 0} , Duration.of(100000000, ChronoUnit.HOURS)))))
                .setObserverF(new ArrayList<>());
    }

    public Personnage(int x, int y, Map lieux, ArrayList<Arme> arme, ArrayList<Armure> armure, String nomPersonnage, int pV, Coffre inventaire, int pVmax, ArrayList<Quete> quetesuivie, Race race){
        super(x,y,lieux);
        this.setArmes(arme)
                .setArmure(armure)
                .setNomPersonnage(nomPersonnage)
                .setPvmax(pVmax)
                .setpV(pV)
                .setInventaire(inventaire)
                .setQueteSuivie(quetesuivie)
                .setRace(race)
                .setdateFin(new ArrayList<LocalDateTime>())
                .setEffetpotion(new ArrayList<Potion>(List.of(new Potion("Systeme digestif",0, new int[]{0, 0, 0, 0} , Duration.of(10000000, ChronoUnit.HOURS)))))
                .setObserverF(new ArrayList<>());
    }

    public Personnage(ArrayList<Arme> arme, ArrayList<Armure> armure, String nomPersonnage, int pV, Coffre inventaire, int pVmax, ArrayList<Quete>  quetesuivie, Race race)
    {
        this.setArmes(arme)
                .setArmure(armure)
                .setNomPersonnage(nomPersonnage)
                .setPvmax(pVmax)
                .setpV(pV)
                .setInventaire(inventaire)
                .setQueteSuivie(quetesuivie)
                .setRace(race)
                .setdateFin(new ArrayList<LocalDateTime>())
                .setEffetpotion(new ArrayList<Potion>(List.of(new Potion("Systeme digestif",0, new int[]{0, 0, 0, 0} , Duration.of(100000, ChronoUnit.DAYS)))))
                .setObserverF(new ArrayList<>());
    }



    //methodes

    public int bagarre(Personnage opposant){
        int reduopp=0;
        ArrayList<Armure> equipopp;
        int deg=0;
        //il faudra revenir sur les potions
        for (Armure a:opposant.getArmure()) {
            reduopp = a.getRedudeg()+ reduopp;
        }
        for (Armure ap : this.getArmure()) {
            deg =  ap.getDeg() + deg;
        }
        for (Arme ar : opposant.getArme()) {
            reduopp = ar.getRedudeg() + reduopp;
        }
        for (Potion p: this.getEffetpotion()) {
            deg=deg+p.getEffets()[0];
        }
        for (Potion p : opposant.getEffetpotion()) {
            reduopp=reduopp+p.getEffets()[1];
        }
        for(Arme arp:this.getArme()) {
            deg = arp.getDeg() + deg;
        }
        if(deg-reduopp<=0){
            return 1;
        }
        else{
            return deg-reduopp;
        }
    }

    public void notifyoneEventF(EventListenerF ef){
        ef.update();
    }

    @Override
    public void update(Quete q) {
        this.removesQuete(q);
        for (Objet o: q.getRecompenses()) {
            this.addObjet(o);
           System.out.println(o.getNomObjet());
        }
    }
}
