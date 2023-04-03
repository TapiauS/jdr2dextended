package ServerPart.DAO;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdr2dcore.Arme;
import jdr2dcore.Armure;
import jdr2dcore.Coffre;
import jdr2dcore.Objet;

import static Logging.Jdr2dLogger.LOGGER;

public abstract class CoffreDAO extends DAOObject {
    public static Coffre getcoffre(int id) throws DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(id, id));
            ResultSet rs = DAOObject.query("SELECT id_objet,nom_objet,deg,redudeg,x,y,nbrmain,emplacement,id_lieu,poid,is_coffre(id_objet),nom_type_objet,pv,pvmax,duree FROM fichobjet WHERE contenant=? OR id_objet=?;", args);
            //ArrayList<Objet> contenu=new ArrayList<>();
            Coffre retour = new Coffre();
            while (rs.next()) {
                if (rs.getInt("id_objet") == id) {
                    if (rs.getInt("id_lieu") != 0) {
                        retour.setNomObjet(rs.getString("nom_objet")).setLieux(MapDAO.getmap(rs.getInt("id_lieu")))
                                .setX(rs.getInt("y"))
                                .setY(rs.getInt("x"));
                        retour.setId(id);
                        System.out.println("coffre posX= " + retour.getX());
                        System.out.println("coffre posY= " + retour.getY());
                    } else {
                        retour.setNomObjet(rs.getString("nom_objet"));
                    }
                } else {
                    if (rs.getBoolean("is_coffre")) {
                        retour.add(CoffreDAO.getcoffre(rs.getInt("id_objet")));
                    } else {
                        switch (rs.getString("nom_type_objet")) {
                            case "Arme":
                                retour.add(new Arme(rs.getString("nom_objet"), rs.getInt("poid"), rs.getInt("deg"), rs.getInt("redudeg"), rs.getInt("nbrmain")).setId(rs.getInt("id_objet")));
                                break;
                            case "Armure":
                                retour.add(new Armure(rs.getString("nom_objet"), rs.getInt("poid"), rs.getInt("deg"), rs.getInt("redudeg"), rs.getString("emplacement")).setId(rs.getInt("id_objet")));
                                break;
                            case "Potion":
                                retour.add(PotionDAO.getpotion(rs));
                                break;
                            default:
                                retour.add(new Objet(rs.getString("nom_objet"), rs.getInt("poid")));
                        }
                    }
                }
            }
            rs.getStatement().close();
            return retour;
        } catch (SQLException sqe) {
            LOGGER.severe(sqe.getMessage());
            throw new DAOException(sqe, ErrorType.SQLSEVERE);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new DAOException(e, ErrorType.GENERALSEVERE);
        }
    }
}
