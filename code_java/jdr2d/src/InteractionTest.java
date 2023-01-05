import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InteractionTest {
    private Armure gantelet=new Armure("Gantelet",1,1,2);
    private Armure plastron=new Armure("Plastron",3,0,7);
    private Arme epee=new Arme("Epée",2,8,2,1);
    private Personnage joueur=new Personnage(new ArrayList<Arme>(List.of(epee)),new ArrayList<Armure>(List.of(plastron,gantelet,gantelet)),"Virgile",10,null,10,null,new Race());
    //private PNJ adversaire=new Personnage(new ArrayList<Arme>(List.of(epee)),new ArrayList<Armure>(List.of(plastron,gantelet)),"Virgile",10,null,10,null,null,new Race());
    //private Interaction bagarretest=new Interaction(joueur,adversaire);
    //@Test
    //void combat() {
        //assertEquals(true,bagarretest.combat());
    //

}
