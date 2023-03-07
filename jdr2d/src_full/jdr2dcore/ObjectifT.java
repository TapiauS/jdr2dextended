package jdr2dcore;//les objectif ou il faut dire quelquechose de precis a un personnage précis

public class ObjectifT extends Objectifs implements EventListenerTalk {
    int convaincre;

    //getters

    public int getConvaincre() {
        return convaincre;
    }

    //setters

    public ObjectifT setConvaincre(int convaincre) {
            this.convaincre = convaincre;
            return this;
    }

    //builders

    public ObjectifT(){
        super();
        this.setConvaincre(new Echange().getId());
    }

    public ObjectifT(int convaincre){
        super();
        this.setConvaincre(convaincre);
    }

    @Override
    public void update() {
        this.setValide(true);
    }

}
