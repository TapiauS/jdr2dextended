package DAO;
import ServerPart.DAO.ObjectifsDAO;
import Entity.ObjectifK;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


class
ObjectifsDAOTest {
    ObjectifK ref;

    {
        try {
            ref = (ObjectifK) ObjectifsDAO.getObjectif(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getObjectif() throws SQLException {

        assertInstanceOf(ObjectifK.class, ObjectifsDAO.getObjectif(1));
        assertEquals(2, ref.getTarget().getId());
    }

}