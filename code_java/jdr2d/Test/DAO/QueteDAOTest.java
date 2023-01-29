package DAO;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class QueteDAOTest {

    @Test
    void getqueteSuivie() throws SQLException {
        assertEquals(1,QueteDAO.getqueteSuivie(1).get(0).getId());
        assertEquals(2,QueteDAO.getqueteSuivie(1).get(0).getObjectifs().size());
        assertEquals(1,QueteDAO.getqueteSuivie(1).get(0).getObjectifs().get(0).getId());
        assertEquals(2,QueteDAO.getqueteSuivie(1).size());
        assertEquals(1,QueteDAO.getqueteSuivie(1).get(1).getObjectifs().size());
    }
}