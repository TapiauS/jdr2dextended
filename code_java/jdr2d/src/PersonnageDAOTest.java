import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PersonnageDAOTest {
    Race humain=new Race("Humain",null);

    Utilisateur ut;

    {
        try {
            ut = UtilisateurDAO.connectcompte("Ahuizolte","Asczdvefb1");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void createchar() throws SQLException {
        PersonnageDAO.createchar("Preste",humain,ut);
    }

    @Test

    void getchar() throws SQLException{
        assertEquals("Virgile",PersonnageDAO.getchar(1).getNomPersonnage());
    }
}