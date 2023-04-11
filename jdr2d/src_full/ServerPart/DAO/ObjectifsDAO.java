package ServerPart.DAO;
import Entity.*;

import static Logging.Jdr2dLogger.LOGGER;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ObjectifsDAO extends DAOObject{
    public static Objectifs getObjectif(int id) throws DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(id));
            ResultSet rs = query("SELECT * FROM objectif WHERE id_objectif=?", args);
            rs.next();
            if (rs.getInt("id_personnage") != 0) {
                ObjectifK retour = new ObjectifK((PNJ) PersonnageDAO.getchar(rs.getInt("id_personnage")));
                retour.setId(id);
                rs.getStatement().close();
                ;
                return retour;
            }
            if (rs.getInt("id_objet") != 0) {
                ObjectifF retour = new ObjectifF(ObjetDAO.getObjet(rs.getInt("id_objet")));
                retour.setId(id);
                rs.getStatement().close();
                ;
                return retour;
            }
            if (rs.getInt("id_dialogue") != 0) {
                ObjectifT retour = new ObjectifT(rs.getInt("id_dialogue"));
                retour.setId(id);
                rs.getStatement().close();
                ;
                return retour;
            }
            throw new DAOException("Objectif d'aucun type définie",ErrorType.SQLENTRY);
        }
        catch (SQLException sqe){
            if(22000<sqe.getErrorCode()&&sqe.getErrorCode()<23000) {
                LOGGER.warning(sqe.getMessage());
                throw new DAOException(sqe,ErrorType.SQLENTRY);
            }
            else {
                LOGGER.warning(sqe.getMessage());
                throw new DAOException(sqe,ErrorType.SQLSEVERE);
            }
        }
        catch (Exception e){
            LOGGER.severe(e.getMessage());
            throw new DAOException(e,ErrorType.GENERALSEVERE);
        }
    }

    //TODO gere les objectis talks, les objectifs find et kill sont correctements update
    public static void setobj(Personnage player,Objectifs o) throws  DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(player.getId(), o.getId()));
            queryUDC("UPDATE valide SET validation=TRUE WHERE id_personnage=? AND id_objectif=?", args);
        }
        catch (SQLException sqe){
            LOGGER.severe(sqe.getMessage());
            throw new DAOException(sqe,ErrorType.SQLENTRY);
        }
        catch (Exception e){
            LOGGER.severe(e.getMessage());
            throw new DAOException(e,ErrorType.GENERALSEVERE);
        }
    }

}
