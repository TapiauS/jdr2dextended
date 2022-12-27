import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PersonnageTest {
    private Armure gantelet=new Armure("Gantelet",1,1,2,"Torse");
    private Armure plastron=new Armure("Plastron",3,0,7,"Gant");

    private Arme epee=new Arme("Epée",2,8,2,1);
    private Personnage joueur=new Personnage(new ArrayList<Arme>(List.of(epee,epee)),new ArrayList<Armure>(List.of(gantelet)),"Virgile",10,null,10,null,null,null);
    private Personnage adversaire=new Personnage(new ArrayList<Arme>(List.of(epee,epee)),new ArrayList<Armure>(List.of(plastron,gantelet)),"Virgile",10,null,10,null,null,null);
    @org.junit.jupiter.api.Test
    void getQueteSuivie() {
    }

    @org.junit.jupiter.api.Test
    void getQueteValide() {
    }

    @org.junit.jupiter.api.Test
    void getNomPersonnage() {
    }

    @org.junit.jupiter.api.Test
    void getdeg() {
    }

    @org.junit.jupiter.api.Test
    void getpV() {
    }

    @org.junit.jupiter.api.Test
    void getpVmax() {
    }

    @org.junit.jupiter.api.Test
    void getRace() {
    }

    @org.junit.jupiter.api.Test
    void getInventaire() {
    }

    @org.junit.jupiter.api.Test
    void getArme() {
    }

    @org.junit.jupiter.api.Test
    void getArmure() {
    }

    @org.junit.jupiter.api.Test
    void setQueteSuivie() {
    }

    @org.junit.jupiter.api.Test
    void setQueteValide() {
    }

    @org.junit.jupiter.api.Test
    void setDeg() {
    }

    @org.junit.jupiter.api.Test
    void setArmes() {
    }

    @org.junit.jupiter.api.Test
    void setRace() {
    }

    @org.junit.jupiter.api.Test
    void setpV() {
    }

    @org.junit.jupiter.api.Test
    void setPvmax() {
    }

    @org.junit.jupiter.api.Test
    void setNomPersonnage() {
    }

    @org.junit.jupiter.api.Test
    void setInventaire() {
    }

    @org.junit.jupiter.api.Test
    void setArmure() {
    }

    @org.junit.jupiter.api.Test
    void bagarre() {
        System.out.println("La reduction de dégat d'une épée est :"+epee.getRedudeg());
        assertEquals(15,adversaire.bagarre(joueur));
    }
}