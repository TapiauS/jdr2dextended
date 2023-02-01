package DAO;
import org.junit.jupiter.api.Test;
import jdr2dcore.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class ObjectifsDAOTest {
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