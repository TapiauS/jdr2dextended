package DAO;
import jdr2dcore.*;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurDAOTest {

    @Test
    void connectcompte() throws SQLException {
        assertEquals(1,UtilisateurDAO.connectcompte("j","g").getId());
    }

    @Test
    void checkmdp() {
    }

    @Test
    void checkpseudo() {
    }

    @Test
    void createcompte() {
    }

    @Test
    void displaypersonnage() {
    }
}