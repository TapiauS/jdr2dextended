package Gamegenerator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarteDealerTest {
    char[][] map= {{' ',' ','#'},{' ',' ',' '},{'#',' ',' '},{'J',' ',' '}};
    CarteDealer c=new CarteDealer();
    @Test
    void testToString() {
        assertEquals("{{'' '','' '',''#''},{'' '','' '','' ''},{''#'','' '','' ''},{''J'','' '','' ''}" +
                "}",c.toString(map));
    }

    @Test

    void testcarteParser(){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                assertEquals(map[i][j],CarteDealer.carteParser(c.toString(map))[i][j]);
            }
        }
    }
}