public class Armure extends  Objet{
    protected int reduDeg;

    protected String typearmure;

    //getters

    public int getReduDeg() {
        return reduDeg;
    }

    public String getTypearmure(){ return typearmure; }

    //setters

    public Armure setReduDeg(int redu){
        this.reduDeg=redu;
        return this;
    }

    public Armure setTypearmure(String typearmure){
        this.typearmure=typearmure;
        return this;
    }

    //builder

    public Armure(){
        super();
        this.setReduDeg(0);
    }
    public Armure(String nomObjet,int poid,int reduDeg){
        super(nomObjet,poid);
        this.setReduDeg(reduDeg)
                .setTypearmure("Natif");
    }

    public Armure(String nomObjet,int poid,int reduDeg,String typearmure){
        super(nomObjet,poid);
        this.setReduDeg(reduDeg)
                .setTypearmure(typearmure);
    }


    public Armure(int x,int y,Map lieux,String nomObjet,int poid,int reduDeg,String typearmure){
        super(x,y,lieux,nomObjet,poid);
        this.setReduDeg(reduDeg)
                .setTypearmure(typearmure);
    }
}
