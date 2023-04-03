package ServerPart.DAO;
import jdr2dcore.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;
import static Logging.Jdr2dLogger.LOGGER;
public abstract class PotionDAO extends DAOObject{
    public static Potion getpotion(ResultSet rs) throws DAOException {
        try {
            int[] effets = new int[4];
            effets[0] = rs.getInt("deg");
            effets[1] = rs.getInt("redudeg");
            effets[2] = rs.getInt("pv");
            effets[3] = rs.getInt("pvmax");

            Duration d;
            d = Duration.of(rs.getInt("duree"), ChronoUnit.SECONDS);
            Potion retour = new Potion(rs.getString("nom_objet"), rs.getInt("poid"), effets, d);
            return retour;
        }
        catch (SQLException sqe){
            LOGGER.severe(sqe.getMessage());
            throw new DAOException(sqe,ErrorType.SQLSEVERE);
        }
        catch (Exception e){
            LOGGER.severe(e.getMessage());
            throw new DAOException(e,ErrorType.GENERALSEVERE);
        }
    }
}
