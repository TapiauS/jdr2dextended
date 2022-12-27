public abstract class Objectifs {
    protected boolean valide;

    //getters

    public boolean isValide() {
        return valide;
    }

    //setters

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    //builders

    public Objectifs(){
        this.setValide(false);
    }

    public Objectifs(boolean valide){
        this.setValide(valide);
    }
}
