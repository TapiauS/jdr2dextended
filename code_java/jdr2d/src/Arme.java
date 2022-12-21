public class Arme extends Objet{
    protected int degat;

    //getters

    public int getDegat() {
        return degat;
    }

    //setters


    public Arme setDegat(int degat) {
        this.degat = degat;
        return this;
    }

    //builder

    public Arme(){
        super();
        this.setDegat(0);
    }

    public Arme(String nomObjet,int poid,int degat){
        super(nomObjet,poid);
        this.setDegat(degat);
    }

    public Arme(int x,int y,Map lieux,String nomObjet,int poid,int degat){
        super(x,y,lieux,nomObjet,poid);
        this.setDegat(degat);
    }
}
