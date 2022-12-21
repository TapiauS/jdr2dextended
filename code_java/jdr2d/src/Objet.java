public class Objet extends Point{
    protected String nomObjet;
    protected int Poid;

    //getters


    public String getNomObjet(){
        return nomObjet;
    }

    public int getPoid() {
        return Poid;
    }

    //setters

    public Objet setNomObjet(String nomObjet){
        this.nomObjet=nomObjet;
        return this;
    }

    public Objet setPoid(int poid) {
        Poid = poid;
        return this;
    }

    //builder

    public Objet(){
        super();
        this.setNomObjet("neant").setPoid(1);
    }

    public Objet(String nomObjet,int poid){
        this.setPoid(poid).setNomObjet(nomObjet);
    }

    public Objet(int x,int y,Map lieux,String nomObjet,int poid){
        super(x,y,lieux);
        this.setPoid(poid).setNomObjet(nomObjet);
    }


    //methodes



}
