import java.util.ArrayList;
import java.util.List;

public class Personnage extends Point{
    private String nomPersonnage;
    private int pV;
    private int pvmax;
    private String race;
    private Coffre inventaire;
    private ArrayList<Arme> armes;
    private ArrayList<Armure> armure;
    private ArrayList<Quete> queteSuivie;
    private ArrayList<Quete> queteValide;



    //getters


    public ArrayList<Quete> getQueteSuivie() {
        return queteSuivie;
    }

    public ArrayList<Quete> getQueteValide() {
        return queteValide;
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

    public String getRace(){
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

    //setters


    public Personnage setQueteSuivie(ArrayList<Quete> queteSuivie) {
        this.queteSuivie = queteSuivie;
        return this;
    }

    public Personnage setQueteValide(ArrayList<Quete> queteValide){
        if (queteValide!=null){
        for(Quete q :queteValide)
        {
            boolean [] v=q.getValidations();
            boolean [] val=new boolean[v.length];
            for(int i=0;i<v.length;i++) {
              val[i]=true;
            }
            q.setValidations(val);
        }
        this.queteValide=queteValide;
        return this;
        }
        else return this;
    }



    public Personnage setRace(String race) {
        this.race = race;
        return this;
    }

    public Personnage setpV(int pV) {
        this.pV = pV;
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
    // gestion des arrays

    public Personnage addsQuete(Quete queteaf){
        this.queteSuivie.add(queteaf);
        return this;
    }

    public Personnage removesQuete(Quete queteaf){
        this.queteSuivie.remove(queteaf);
        return this;
    }

    public Personnage addvQuete(Quete quetev){
        this.removesQuete(quetev);
        boolean [] v=quetev.getValidations();
        boolean [] val=new boolean[v.length];
        for(int i=0;i<v.length;i++) {
            val[i]=true;
        }
        quetev.setValidations(val);
        this.queteValide.add(quetev);
        return this;
    }

    public Personnage removevQuete(Quete quetev){
        this.queteValide.add(quetev);
        return this;
    }


    public Personnage addArme(Arme arme){
        this.armes.add(arme);
        return this;
    }

    public Personnage removeArme(Arme arme){
        this.armes.remove(arme);
        return this;
    }

    public Personnage addArmure(Armure armure) {
        this.armure.add(armure) ;
        return this;
    }


    public Personnage removArmure(Armure armure){
        this.armure.remove(armure);
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


    //Builders

    public Personnage(){
        super();
        ArrayList<Arme> armedefault=new ArrayList<>(List.of(new Arme("Poing",0,0)));
        ArrayList<Armure> armuredefault=new ArrayList<Armure>(List.of(new Armure("peau",0,0)));
        this.setArmes(armedefault)
                .setArmure(armuredefault)
                .setNomPersonnage("tki")
                .setpV(1)
                .setInventaire(null)
                .setPvmax(1)
                .setQueteSuivie(null)
                .setQueteValide(null)
                .setRace(null);
    }

    public Personnage(int x, int y, Map lieux, ArrayList<Arme> arme,ArrayList<Armure> armure,String nomPersonnage,int pV,Coffre inventaire,int pVmax,ArrayList<Quete> quetesuivie,ArrayList<Quete>  queteValide,String race){
        super(x,y,lieux);
        this.setArmes(arme)
                .setArmure(armure)
                .setNomPersonnage(nomPersonnage)
                .setpV(pV)
                .setInventaire(inventaire)
                .setPvmax(pVmax)
                .setQueteSuivie(quetesuivie)
                .setQueteValide(queteValide)
                .setRace(race);
    }

    public Personnage( ArrayList<Arme> arme,ArrayList<Armure> armure,String nomPersonnage,int pV,Coffre inventaire,int pVmax,ArrayList<Quete>  quetesuivie,ArrayList<Quete>  queteValide,String race)
    {
        this.setArmes(arme)
                .setArmure(armure)
                .setNomPersonnage(nomPersonnage)
                .setpV(pV)
                .setInventaire(inventaire)
                .setPvmax(pVmax)
                .setQueteSuivie(quetesuivie)
                .setQueteValide(queteValide)
                .setRace(race);
    }



    //methodes

    public int bagarre(Personnage opposant){
        int reduopp=0;
        ArrayList<Armure> equipopp;
        equipopp=opposant.getArmure();
        int deg=0;
        for (Armure a:equipopp){
            reduopp=a.getReduDeg()+reduopp;
        }
        for (Arme a: this.getArme()){
            deg=deg+a.getDegat();
        }
        if(deg-reduopp<=0){
            return 1;
        }
        else{
            return deg-reduopp;
        }
    }

   // public Personnage boire(Potion p){
   //     this.setDeg(this.getdeg()+p.getEffets()[0]).this.
    // }






}
