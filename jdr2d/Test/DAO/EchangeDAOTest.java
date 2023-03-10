package DAO;

import ServerPart.DAO.EchangeDAO;
import jdr2dcore.PNJ;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class EchangeDAOTest {
    PNJ parleurtest=new PNJ();

    @Test
    void getEchangetree() throws SQLException {
        parleurtest.setId(5);
        System.out.println("parleur test id="+parleurtest.getId());
        assertEquals("Bonjour", EchangeDAO.getEchangetree(parleurtest).getReponse());
        assertEquals(1,EchangeDAO.getEchangetree(parleurtest).getDialogueSuivant()[0].getDialogueSuivant().length);
    }
}