public class Potion extends Objet{
    protected int[] effets;

    //getters


    public int[] getEffets() {
        return effets;
    }

    //setters


    public Potion setEffets(int[] effets) {
        this.effets = effets;
        return this;
    }

    //Builders

    public Potion(){
        super();
        this.setEffets(new int[] {0});
    }

    public Potion(int x,int y,Map lieux,String nomObjet,int poid,int[] effets){
        super(x,y,lieux,nomObjet,poid);
        this.setEffets(effets);
    }
}
