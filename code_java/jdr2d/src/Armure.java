public class Armure extends  Objet{
    protected int reduDeg;

    //getters

    public int getReduDeg() {
        return reduDeg;
    }

    //setters

    public Armure setReduDeg(int redu){
        this.reduDeg=redu;
        return this;
    }

    //builder

    public Armure(){
        super();
        this.setReduDeg(0);
    }
    public Armure(String nomObjet,int poid,int reduDeg){
        super(nomObjet,poid);
        this.setReduDeg(reduDeg);
    }


    public Armure(int x,int y,Map lieux,String nomObjet,int poid,int reduDeg){
        super(x,y,lieux,nomObjet,poid);
        this.setReduDeg(reduDeg);
    }
}
