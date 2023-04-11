package Entity;

public class Race {
    protected String nomRace;
    protected int[] effetBM;

    //getters

    public String getNomRace() {
        return nomRace;
    }

    public int[] getEffetBM() {
        return effetBM;
    }

    //setters


    public Race setNomRace(String nomRace) {
        this.nomRace = nomRace;
        return this;
    }

    public Race setEffetBM(int[] effetBM) {
        this.effetBM = effetBM;
        return this;
    }

    //builders

    public Race(){
        this.setEffetBM(new int[] {0,0,0,0}).setNomRace("Void");
    }

    public Race(String nomRace,int[] effet){
        this.setNomRace(nomRace).setEffetBM(effet);
    }
}
