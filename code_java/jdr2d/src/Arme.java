public class Arme extends Equipement{
    protected int nbrmain;

    //getters

    public int getNbrmain(){return nbrmain;}

    //setters

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
        this.setNbrmain(0);
    }

    public Arme(String nomObjet,int poid,int degat,int redudegat,int nbrmain){
        super(nomObjet,poid,degat,redudegat);
                this.setNbrmain(nbrmain);
    }

    public Arme(int x,int y,Map lieux,String nomObjet,int poid,int degat,int redudegat,int nbrmain){
        super(x,y,lieux,nomObjet,poid,degat,redudegat);
        this.setNbrmain(nbrmain);
    }
}
