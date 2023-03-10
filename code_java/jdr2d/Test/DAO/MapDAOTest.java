package DAO;

import jdr2dcore.Map;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDAOTest {
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
