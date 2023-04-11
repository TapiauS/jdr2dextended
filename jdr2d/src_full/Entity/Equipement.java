package Entity;

public class Equipement extends Objet {
    int deg;
    int redudeg;

    //getters

    public int getDeg() {
        return deg;
    }

    public int getRedudeg() {
        return redudeg;
    }


    //setters

    public Equipement setDeg(int deg){
        this.deg=deg;
        return this;
    }


    public Equipement setRedudeg(int redudeg){
        this.redudeg=redudeg;
        return this;
    }

    //builders

    public Equipement(){
        super();
        this.setDeg(0).
                setRedudeg(0);
    }

    public Equipement(String nomobjet,int poid,int deg,int redudeg){
        super(nomobjet,poid) ;
        this.setDeg(deg)
                .setRedudeg(redudeg);

    }

    public Equipement(int x, int y, Map lieux, String nomobjet, int poid, int deg, int redudeg){
        super(x,y,lieux,nomobjet,poid);
        this.setDeg(deg)
                .setRedudeg(redudeg);
    }
}
