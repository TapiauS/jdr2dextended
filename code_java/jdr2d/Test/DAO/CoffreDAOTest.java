package DAO;

import jdr2dcore.Coffre;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;



class CoffreDAOTest {

    Coffre test;

    {
        try {
            test = CoffreDAO.getcoffre(3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getcoffre() throws SQLException {
        Coffre test1=(Coffre) test.getContenu().get(1);
        assertEquals(2,test.getContenu().size());
        assertInstanceOf(Coffre.class,test.getContenu().get(1));
        assertEquals(1,test1.getContenu().size());
    }
}