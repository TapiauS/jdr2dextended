public class Personnage extends Point{
    private String nomPersonnage;
    private int deg;
    private int pV;
    private int pvmax;
    private String race;
    private Coffre inventaire;
    private Arme[] armes;
    private Armure [] armure;
    private Quete[] queteSuivie;
    private Quete[] queteValide;



    //getters


    public Quete[] getQueteSuivie() {
        return queteSuivie;
    }

    public Quete[] getQueteValide() {
        return queteValide;
    }


    public String getNomPersonnage(){
        return nomPersonnage;
    }

    public int getdeg(){
        return deg;
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

    public Arme[] getArme(){
        return armes;
    }

    public Armure[] getArmure() {
        return armure;
    }

    //setters


    public Personnage setQueteSuivie(Quete[] queteSuivie) {
        this.queteSuivie = queteSuivie;
        return this;
    }

    public Personnage setQueteValide(Quete[] queteValide){
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



    public Personnage setDeg(int deg) {
        this.deg = deg;
        return this;
    }

    public Personnage setArmes(Arme[] armes) {
        this.armes = armes;
        return this;
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

    public Personnage setArmure(Armure[] armure) {
        this.armure = armure;
        return this;
    }

    //Builders

    public Personnage(){
        super();
        this.setArmes(new Arme[] {new Arme()})
                .setArmure(new Armure[] {new Armure()})
                .setDeg(0)
                .setNomPersonnage("tki")
                .setpV(1)
                .setInventaire(null)
                .setPvmax(1)
                .setQueteSuivie(null)
                .setQueteValide(new Quete[]{new Quete()})
                .setRace(null);
    }

    public Personnage(int x, int y, Map lieux, Arme[] arme,Armure[] armure,int deg,String nomPersonnage,int pV,Coffre inventaire,int pVmax,Quete[] quetesuivie,Quete[] queteValide,String race){
        super(x,y,lieux);
        this.setArmes(arme)
                .setArmure(armure)
                .setDeg(deg)
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
        Armure[] equipopp;
        equipopp=opposant.getArmure();
        for (Armure a:equipopp){
            reduopp=a.getReduDeg()+reduopp;
        }
        if(this.deg-reduopp<=0){
            return 1;
        }
        else{
            return this.deg-reduopp;
        }
    }






}
