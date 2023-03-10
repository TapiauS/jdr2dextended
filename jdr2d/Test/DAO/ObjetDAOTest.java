package DAO;

import ServerPart.DAO.ObjetDAO;
import jdr2dcore.Arme;
import jdr2dcore.Map;
import jdr2dcore.Objet;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjetDAOTest {
    char[][] map= {{' ',' ','#'},{' ',' ',' '},{'#',' ',' '},{'J',' ',' '}};
    int[] dim=new int[] {4,3};
    Map carteref=new Map(dim,map,"Tarante",1);
    Objet o=new Objet(0,1,carteref,"Fusil a silex",0).setId(2);

   // Personnage p;

    //{
      //  try {
      //      p = PersonnageDAO.getchar(1);
       // } catch (SQLException e) {
        //    throw new RuntimeException(e);
       // }
    // }


    @Test
    void create() throws SQLException {
        ObjetDAO.create(o);
    }

   @Test

    void getObjet() throws SQLException {
        assertEquals(o.getNomObjet(),ObjetDAO.getObjet(2).getNomObjet());
    }

    //@Test

    //void pickObjet() throws SQLException{
      //  ObjetDAO.pickObjet(p,o);
    //}

    @Test

    void dropObjet() throws SQLException{
        //ObjetDAO.dropObjet(o);
    }

    @Test
    void addObjetCoffre() throws SQLException{
        ObjetDAO.addObjetCoffre(new Arme("Epee",1,5,2,1),5);
    }
}

