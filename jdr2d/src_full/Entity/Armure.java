package Entity;

import java.util.Objects;

public class Armure extends Equipement {
    protected static final String[] typearmures= {"CASQUE","TORSE","GANT","BOTTE","JAMBIERE","NATIF"};
    protected String typearmure;

    //getters

    public String getTypearmure(){ return typearmure; }

    //setters

    public Armure setTypearmure(String typearmure){
        typearmure.toUpperCase();
        boolean contient=false;
        for (String s: typearmures) {
            if(Objects.equals(s, typearmure)){
                contient=true;
            }
        }
        if(contient)
        {
            this.typearmure = typearmure;
            return this;
        }
        return null;
    }

    //builder

    public Armure(){
        super();
        this.setTypearmure("Natif");
    }
    public Armure(String nomObjet,int poid,int deg,int reduDeg){
        super(nomObjet,poid,deg,reduDeg);
        this.setTypearmure("Natif");
    }

    public Armure(String nomObjet,int poid,int deg,int reduDeg,String typearmure){
        super(nomObjet,poid,deg,reduDeg);
        this.setTypearmure(typearmure);
    }


    public Armure(int x, int y, Map lieux, String nomObjet, int poid, int deg, int reduDeg, String typearmure){
        super(x,y,lieux,nomObjet,poid,deg,reduDeg);
        this.setTypearmure(typearmure);
    }
}
