import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class PNJ extends Personnage{
    protected boolean nomme;

    protected Point posinit;

    //getters

    public boolean isnomme() {
        return nomme;
    }

    public Point getPosinit() {
        return posinit;
    }

    //setters

    public PNJ setnomme(boolean nomme) {
        this.nomme = nomme;
        return this;
    }

    public PNJ setposinit(Point posinit){
        this.posinit=posinit;
        return this;
    }

    //builders

    public PNJ(){
        super();
        this.setnomme(false).setposinit(new Point());
    }

    public PNJ(ArrayList<Arme> arme, ArrayList<Armure> armure, String nomPersonnage, int pV, Coffre inventaire, int pVmax, ArrayList<Quete>  quetesuivie, Race race,Point posinit,Boolean nomme){
        super(arme, armure, nomPersonnage, pV, inventaire, pVmax, quetesuivie, race);
        this.setnomme(nomme).setposinit(posinit);
    }

    public PNJ(int x, int y, Map lieux, ArrayList<Arme> arme,ArrayList<Armure> armure,String nomPersonnage,int pV,Coffre inventaire,int pVmax,ArrayList<Quete> quetesuivie,Race race,Point posinit,Boolean nomme){
        super(x, y, lieux, arme, armure, nomPersonnage, pV, inventaire, pVmax, quetesuivie, race);
        this.setnomme(nomme).setposinit(posinit);
    }



}
