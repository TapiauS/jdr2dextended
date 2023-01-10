import java.util.ArrayList;

public abstract class Objectifs {
    protected boolean valide;

    protected ArrayList<EventListenerO> observer;



    //getters

    public boolean isValide() {
        return valide;
    }


    //setters

    public Objectifs setValide(boolean valide) {
        if(valide){
            this.notifyAllQuete();
            this.removeAllQuete();
        }
        this.valide = valide;
        return this;
    }

    public Objectifs setObserver(ArrayList<EventListenerO> e){
        this.observer=e;
        return this;
    }

    //builders

    public Objectifs(){
        this.setValide(false)
                .setObserver(new ArrayList<EventListenerO>());
    }

    public Objectifs(boolean valide){
        this.setValide(valide)
                .setObserver(new ArrayList<EventListenerO>());
    }

    //methodes

    public Objectifs addQuete(EventListenerO eventListenerO){
        this.observer.add(eventListenerO);
        return this;
    }

    public Objectifs removeQuete(EventListenerO eventListenerO){
        this.observer.remove(eventListenerO);
        return this;
    }

    public Objectifs removeAllQuete(){
        for (int i=0;i<observer.size();i++) {
            this.removeQuete(observer.get(i));
        }
        return this;
    }

    public void notifyAllQuete(){
        for (EventListenerO o: observer) {
            o.update(this);
        }
    }


}
