package DAO;

import jdr2dcore.Race;
import jdr2dcore.Utilisateur;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;


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
    @Test
    void getcharclose() throws SQLException{
        assertEquals("Sogg Hydromel",PersonnageDAO.getcharclose(PersonnageDAO.getchar(1),5).get(0).getNomPersonnage());
    }
}