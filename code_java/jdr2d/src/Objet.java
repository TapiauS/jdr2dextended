public class Objet {
    private String nomObjet;
    private int deg;
    private int redu;
    private String emplacement;
    private Point pos;


    //getters


    public String getNomObjet(){
        return nomObjet;
    }

    public int getDeg(){
        return deg;
    }

    public int getRedu(){
        return redu;
    }

    public String getEmplacement(){
        return emplacement;
    }

    public Point getPos(){
        return pos;
    }

    //setters


    public void setNomObjet(String nomObjet){
        this.nomObjet=nomObjet;
    }

    public void setDeg(int deg){
        this.deg=deg;
    }

    public void setRedu(int redu) {
        this.redu=redu;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement=emplacement;
    }

    public void setPos(Point pos){
        this.pos=pos;
    }

    //methodes




}
