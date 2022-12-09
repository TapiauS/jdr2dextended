public class Personnage {
    private String nomPersonnage;
    private int deg;
    private int pV;
    private int pvmax;
    private String race;
    private Point pos;
    private Objet[] inventaire;
    private Objet[] equipement;


    //getters

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
        int reduopp;
        Objet[] equipopp;

        equipopp=opposant.getEquipement();
        for (int i=0; length.equipopp; )
    }
}
