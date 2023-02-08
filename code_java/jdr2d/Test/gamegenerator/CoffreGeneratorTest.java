package gamegenerator;

import DAO.MapDAO;
import jdr2dcore.Map;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CoffreGeneratorTest {
    Map test=new Map(new int[] {1,4},new char[][] {{' ','C','C','C'}},"test",0);

    @Test
    void filldatabase() throws SQLException {

        test.setId(3);
        CoffreGenerator.filldatabase(test);
    }
}