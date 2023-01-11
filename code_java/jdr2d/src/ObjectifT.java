//les objectif ou il faut dire quelquechose de precis a un personnage précis

public class ObjectifT extends Objectifs implements EventListenerTalk{
    Echange convaincre;

    //getters

    public Echange getConvaincre() {
        return convaincre;
    }

    //setters

    public ObjectifT setConvaincre(Echange convaincre) {
            this.convaincre = convaincre;
            convaincre.setObjectifs(true);
            convaincre.setObjectifsT(this);
            return this;
    }

    //builders

    public ObjectifT(){
        super();
        this.setConvaincre(new Echange());
    }

    public ObjectifT(Echange convaincre){
        super();
        this.setConvaincre(convaincre);
    }

    @Override
    public void update() {
        System.out.println("J'update");
        this.setValide(true);
    }

}
