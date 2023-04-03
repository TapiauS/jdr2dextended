package gamegenerator;

import ServerPart.DAO.DAOException;
import ServerPart.DAO.ObjetDAO;
import jdr2dcore.*;

import java.sql.SQLException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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

    private final static Potion potionsoin=new Potion("Potion de soin",0,new int[]{0,0,10,0}, Duration.of(1, ChronoUnit.SECONDS));
    private final static Potion potionforce=new Potion("Potion de force",0,new int[] {3,0,0,0},Duration.of(600,ChronoUnit.SECONDS));
    private final static Potion potionarmure=new Potion("Potion d'armure",0,new int[] {0,5,0,0},Duration.of(600,ChronoUnit.SECONDS));
    private final static Potion potionconstitution=new Potion("Potion de constitution",0,new int[] {0,0,0,10},Duration.of(1200,ChronoUnit.SECONDS));

    private final static ArrayList<Objet> contenuposs=new ArrayList<>(List.of(epee,marteaudeguerre,bouclier,pavois,casquelourd,gantelet,plastrail,jambiere,bottes,potionsoin,potionforce,potionarmure,potionconstitution));

    public static void filldatabase(Map m) throws SQLException, DAOException {
        for (int i = 0; i < m.getDimensions()[0]; i++) {
            for (int j = 0; j < m.getDimensions()[1]; j++) {
                if(m.getCarte()[i][j]=='C')
                    create_fill(i,j,m);
            }
        }
    }

    private static void petit_coffre(int id) throws DAOException {
        int taille=(int) (Math.random()*10);
        int idc=ObjetDAO.addcoffre("Coffre",id);
        for (int k = 0; k < taille; k++) {
            if(Math.random()<0.9){
                int obj=(int) (Math.random()*contenuposs.size());
                ObjetDAO.addObjetCoffre(contenuposs.get(obj),idc);
            }
            else {
                petit_coffre(idc);
            }
        }
    }

    private static void create_fill(int i,int j,Map m) throws SQLException, DAOException {
        int taille=(int) (Math.random()*10);
        int id=ObjetDAO.addcoffre("Coffre",i,j,m);
        for (int k = 0; k < taille; k++) {
            if(Math.random()<0.9){
                int obj=(int) (Math.random()*contenuposs.size());
                ObjetDAO.addObjetCoffre(contenuposs.get(obj),id);
            }
            else {
                petit_coffre(id);
            }
        }
    }

}
