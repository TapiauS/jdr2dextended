//les objectifs ou il faut tuer un PNJ

public class ObjectifK extends Objectifs{
    PNJ target;

    //getters


    public PNJ getTarget() {
        return target;
    }

    //setters


    public ObjectifK setTarget(PNJ target) {
        this.target = target;
        return this;
    }

    //builders

    public ObjectifK(){
        super();
        this.setTarget(new PNJ());
    }

    public ObjectifK(boolean valide,PNJ target){
        super(valide);
        this.setTarget(target);
    }
}
