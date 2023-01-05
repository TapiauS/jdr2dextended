//les objectifs ou il faut tuer un PNJ

public class ObjectifK extends Objectifs implements EventListenerK{
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

    public ObjectifK(PNJ target){
        super();
        this.setTarget(target);
    }

    @Override
    public void update(PNJ opposant) {
        this.setValide(true);
    }

    @Override
    public void update(Objectifs o) {

    }
}
