package Entity;


public class Porte extends Point {
    private Porte portelie;

    private int id;

    //getters

    public Porte getPortelie() {
        return portelie;
    }

    public int getId() {
        return id;
    }

    //setters


    public void setId(int id) {
        this.id = id;
    }

    public Porte setPortelie(Porte portelie) {
        this.portelie = portelie;
        if(portelie.getPortelie()==null){
            portelie.setPortelie(this);
        }
        return this;
    }

    //builders

    public Porte(Map m, int x, int y){
        super(x,y,m);
    }

    public Porte(Map m,int x,int y,Porte porte){
        super(x,y,m);
        this.setPortelie(porte);
    }

    //methodes

    public void traverse(Personnage joueur){
        joueur.setLieux(portelie.getLieux());
        joueur.setX(portelie.getX());
        joueur.setY(portelie.getY());
    }


}
