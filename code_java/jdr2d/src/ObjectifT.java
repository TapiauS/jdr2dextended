//les objectif ou il faut dire quelquechose de precis a un personnage précis

public class ObjectifT extends Objectifs{
    Echange convaincre;

    //getters

    public Echange getConvaincre() {
        return convaincre;
    }

    //setters

    public ObjectifT setConvaincre(Echange convaincre) {
        this.convaincre = convaincre;
        return this;
    }

    //builders

    public ObjectifT(){
        super();
        this.setConvaincre(new Echange());
    }
}
