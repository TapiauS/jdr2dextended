public class Porte {
    private Porte[] porteArriv;

    private Porte[] porteSortie;

    private boolean entree;

    private Point pos;

    //getters


    public Porte[] getPorteArriv() {
        return porteArriv;
    }

    public Porte[] getPorteSortie() {
        return porteSortie;
    }

    public Point getPos() {
        return pos;
    }

    public boolean isentree(){
        return entree;
    }

    //setters


    public void setPos(Point pos) {
        this.pos = pos;
    }

    public void setEntree(boolean entree) {
        this.entree = entree;
    }


}
