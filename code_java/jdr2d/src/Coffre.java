import java.util.ArrayList;

public class Coffre extends Objet{
    protected ArrayList<Objet> contenu;
//getters

    public ArrayList<Objet> getContenu(){
        return contenu;
    }

    //methodes

    public Coffre(){
        this.contenu=new ArrayList<Objet>();
    }

    public Coffre(Objet o){
        this.contenu = new ArrayList<Objet>();
        this.add(o);
    }

    public Coffre add(Objet o){
        this.contenu.add(o);
        return this ;
    }
    public Coffre remove(Objet o){
        this.contenu.remove(o);
        return this;
    }

    public Coffre remove(int ind){
        this.contenu.remove(ind);
        return this;
    }



}
