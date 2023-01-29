package DAO;
import org.junit.jupiter.api.Test;
import jdr2dcore.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.AssertJUnit.assertEquals;

class ObjectifsDAOTest {
    ObjectifK ref;

    {
        try {
            ref = (ObjectifK) ObjectifsDAO.getObjectif(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getObjectif() throws SQLException {

        assertInstanceOf(ObjectifK.class,ObjectifsDAO.getObjectif(1));
        assertEquals(2,ref.getTarget().getId());
    }

    static class ObjetDAOTest {

        char[][] map= {{' ',' ','#'},{' ',' ',' '},{'#',' ',' '},{'J',' ',' '}};
        int[] dim=new int[] {4,3};
        Map carteref=new Map(dim,map,"Tarante",1);
        Objet o=new Objet(0,1,carteref,"Fusil a silex",0).setId(2);

        Personnage p;

        {
            try {
                p = PersonnageDAO.getchar(1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        @Test
        void create() throws SQLException {
            ObjetDAO.create(o);
        }

        @Test

        void getObjet() throws SQLException {
            assertEquals(o.getNomObjet(),ObjetDAO.getObjet(2).getNomObjet());
        }

        @Test

        void pickObjet() throws SQLException{
            ObjetDAO.pickObjet(p,o);
        }

        @Test

        void dropObjet() throws SQLException{
            ObjetDAO.dropObjet(o);
        }
    }

    static class MapDAOTest {
        char[][] map= {{' ',' ','#'},{' ',' ',' '},{'#',' ',' '},{'J',' ',' '}};
        int[] dim=new int[] {4,3};
        Map carteref=new Map(dim,map,"Tarante",3);
        ArrayList<String> listeref=new ArrayList<>(){{
            add("Virgile");
            add("Donatien");
        }};



        @Test
        void getmap() throws SQLException {
            MapDAO.createMap(carteref);
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    assertEquals(map[i][j],MapDAO.getmap(1).getCarte()[i][j]);
                }
            }
        }

    }
}