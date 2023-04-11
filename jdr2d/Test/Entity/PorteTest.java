package Entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PorteTest {
    char[][] labytest = new char[][]{{'J', ' ', '#', ' ', ' '}, {' ', ' ', '#', ' ', ' '}, {' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' '}, {'C', ' ', ' ', ' ', 'E'}};
    char[][] labytest1=new char[][]{{' ','#',' '},{' ','#',' '},{' ',' ',' '}};
    Map maptest = new Map(new int[]{5, 5}, labytest, "Arcanum",0);
    Map maptest1=new Map(new int[]{3,3},labytest1,"Tarante",0);
    Porte porte1=new Porte(maptest1,1,2);
    Porte porte0=new Porte(maptest,4,0,porte1);
    ArrayList<Arme> armedefault = new ArrayList<>(List.of(new Arme("Poing", 0, 0, 0, 0)));
    ArrayList<Armure> armuredefault = new ArrayList<Armure>(List.of(new Armure("Peau", 0, 0, 0, "Natif")));
    Personnage joueur = new Personnage(0, 0, maptest, armedefault, armuredefault, "Donatien", 30, new Coffre(), 30, new ArrayList<>(), new Race("Humain", null));


    @Test
    void setPortelie() {
        assertEquals(porte0,porte1.getPortelie());
    }

    @Test
    void traverse() {
        porte0.traverse(joueur);
        assertEquals(1,joueur.getX());
        assertEquals(2,joueur.getY());
        assertEquals(maptest1,joueur.getLieux());
    }
}