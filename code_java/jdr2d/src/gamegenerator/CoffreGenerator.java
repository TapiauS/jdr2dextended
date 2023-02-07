package gamegenerator;

import jdr2dcore.Arme;
import jdr2dcore.Armure;
import jdr2dcore.Objet;
import jdr2dcore.Potion;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CoffreGenerator {

    private final static Arme epee=new Arme("Epee",1,5,2,1);
    private final static Arme marteaudeguerre=new Arme("Marteau",6,10,1,2);
    private final static Arme bouclier=new Arme("Bouclier",1,1,5,1);
    private final static Arme pavois=new Arme("Pavois",5,-1,9,1);

    private final static Armure casquelourd=new Armure("Haume",2,0,3,"CASQUE");
    private final static Armure gantelet=new Armure("Gantelet",1,2,3,"GANT");
    private final static Armure plastrail=new Armure("Plastron",5,-1,7,"TORSE");
    private final static Armure jambiere=new Armure("Jambiere",2,0,9,"JAMBIERE");
    private final static Armure bottes=new Armure("Botte",1,1,3,"BOTTE");

    private final static Potion potionsoin=new Potion("Potion de soin",0,new int[]{0,0,5,0}, Duration.of(1, ChronoUnit.SECONDS));
    private final static Potion potionforce=new Potion("Potion de force",0,new int[] {3,0,0,0},Duration.of(600,ChronoUnit.SECONDS));
    private final static Potion potionarmure=new Potion("Potion d'armure",0,new int[] {0,5,0,0},Duration.of(600,ChronoUnit.SECONDS));
    private final static Potion potionconstitution=new Potion("Potion de constitution",0,new int[] {0,0,0,10},Duration.of(1200,ChronoUnit.SECONDS));

    private final static ArrayList<Objet> contenuposs=new ArrayList<>(List.of(epee,marteaudeguerre,bouclier,pavois,casquelourd,gantelet,plastrail,jambiere,bottes,potionsoin,potionforce,potionarmure,potionconstitution));


}
