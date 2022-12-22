public class Arme extends Objet{
    protected int degat;
    protected int nbrmain;

    //getters

    public int getDegat() {
        return degat;
    }

    public int getNbrmain(){return nbrmain;}

    //setters


    public Arme setDegat(int degat) {
        this.degat = degat;
        return this;
    }

    public Arme setNbrmain(int nbrmain){
        if(nbrmain<3 && nbrmain>=0){
        this.nbrmain=nbrmain;
        return this;
        }
        else throw new IllegalArgumentException("Personne n'a trois mains, ou zeros");
    }
    //builder

    public Arme(){
        super();
        this.setDegat(0);
        this.setNbrmain(0);
    }

    public Arme(String nomObjet,int poid,int degat,int nbrmain){
        super(nomObjet,poid);
        this.setDegat(degat)
                .setNbrmain(nbrmain);
    }

    public Arme(int x,int y,Map lieux,String nomObjet,int poid,int degat,int nbrmain){
        super(x,y,lieux,nomObjet,poid);
        this.setDegat(degat)
                .setNbrmain(nbrmain);
    }
}
