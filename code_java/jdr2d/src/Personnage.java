public class Personnage {
    private String nomPersonnage;
    private int deg;
    private int pV;
    private int pvmax;
    private String race;
    private Point pos;
    private Objet[] inventaire;
    private Objet[] equipement;
    private Quete[] queteSuivie;
    private Quete[] queteValide;
    private Map lieux;


    //getters


    public Quete[] getQueteSuivie() {
        return queteSuivie;
    }

    public Quete[] getQueteValide() {
        return queteValide;
    }

    public Map getLieux() {
        return lieux;
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

    public Point getPos(){
        return pos;
    }

    public Objet[] getInventaire(){
        return inventaire;
    }

    public Objet[] getEquipement(){
        return equipement;
    }

    //setters


    public void setQueteSuivie(Quete[] queteSuivie) {
        this.queteSuivie = queteSuivie;
    }

    public void setQueteValide(Quete[] queteValide){
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
    }



    public void setLieux(Map lieux) {
        this.lieux = lieux;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public void setEquipement(Objet[] equipement) {
        this.equipement = equipement;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public void setpV(int pV) {
        this.pV = pV;
    }

    public void setPvmax(int pvmax) {
        this.pvmax = pvmax;
    }

    public void setNomPersonnage(String nomPersonnage) {
        this.nomPersonnage = nomPersonnage;
    }

    public void setInventaire(Objet[] inventaire) {
        this.inventaire = inventaire;
    }

    //methodes

    public int bagarre(Personnage opposant){
        int reduopp=0;
        Objet[] equipopp;
        equipopp=opposant.getEquipement();
        for (Objet o:equipopp){
            reduopp=o.getRedu()+reduopp;
        }
        if(this.deg-reduopp<=0){
            return 1;
        }
        else{
            return (this.deg-reduopp);
        }
    }
}
