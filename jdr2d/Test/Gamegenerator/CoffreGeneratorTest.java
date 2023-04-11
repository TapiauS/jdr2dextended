package Gamegenerator;

import ServerPart.DAO.DAOException;
import Entity.Map;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class CoffreGeneratorTest {
    Map test=new Map(new int[] {1,4},new char[][] {{' ','C','C','C'}},"test",0);

    @Test
    void filldatabase() throws SQLException, DAOException {

        test.setId(3);
        CoffreGenerator.filldatabase(test);
    }
}