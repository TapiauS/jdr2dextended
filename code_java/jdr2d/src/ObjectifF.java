//les objectifs ou il faut trouver un objet

public class ObjectifF extends Objectifs{
    Objet objetquete;

    //getters


    public Objet getObjetquete() {
        return objetquete;
    }

    //setters


    public ObjectifF setObjetquete(Objet objetquete) {
        this.objetquete = objetquete;
        return this;
    }

    //builders

    public ObjectifF(){
        super();
        this.setObjetquete(new Objet());
    }

    public ObjectifF(Objet objetquete){
        super();
        this.setObjetquete(objetquete);
    }
}