package ServerPart.DAO;
import Entity.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import static Logging.Jdr2dLogger.LOGGER;

public abstract class UtilisateurDAO extends DAOObject {

    public static Utilisateur connectcompte(String nom, String mdp) throws DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(nom, mdp));
            ResultSet rs = query("SELECT * FROM compte_utilisateur WHERE pseudo_compte=? AND mdp_compte=?;", args);
            Utilisateur retour = new Utilisateur();
            if (rs.next()) {
                boolean isavailable=rs.getBoolean("active");
                if(!isavailable) {
                    retour = new Utilisateur(rs.getString("couriel_compte"), rs.getString("mdp_compte"), rs.getString("pseudo_compte"), true, rs.getInt("id_compte_utilisateur"));
                    queryUDC("UPDATE compte_utilisateur SET active=true WHERE pseudo_compte=? AND mdp_compte=?;",args);
                    return retour;
                }
                else {
                    throw new DAOException("ACCOUNT ALREADY USED",ErrorType.NOTAVAILABLE);
                }
            } else {
                throw new DAOException("AUTHENTIFICATION ERROR",ErrorType.SQLENTRY);
            }
        } catch (SQLException sqe) {
            throw new DAOException(sqe,ErrorType.SQLSEVERE);
        }
    }




    public static boolean checkmdp(String mdp) throws SQLException, DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(mdp));
            ResultSet rs = query("SELECT * FROM compte_utilisateur WHERE mdp_compte=?", args);
            if (rs.next()) {
                rs.getStatement().close();
                return false;
            } else {
                rs.getStatement().close();
                ;
                return true;
            }
        }
        catch (SQLException sqe){
            LOGGER.severe(sqe.getMessage());
            throw new DAOException(sqe,ErrorType.SQLSEVERE);
        }
    }

    public static boolean checkpseudo(String pseudo) throws  DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(pseudo));
            ResultSet rs = query("SELECT * FROM compte_utilisateur WHERE pseudo_compte=?", args);
            if (rs.next()) {
                rs.getStatement().close();
                return false;
            } else {
                rs.getStatement().close();
                return true;
            }
        }
        catch (SQLException sqe){
            LOGGER.severe(sqe.getMessage());
            throw new DAOException(sqe,ErrorType.SQLSEVERE);
        }
    }



    public static boolean checkmail(String mail) throws DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(mail));
            ResultSet rs = query("SELECT * FROM compte_utilisateur WHERE couriel_compte=?", args);
            if (rs.next()) {
                rs.getStatement().close();
                ;
                return false;
            } else {
                rs.getStatement().close();
                ;
                return true;
            }
        }
        catch (SQLException sqe){
            LOGGER.severe(sqe.getMessage());
            throw new DAOException(sqe,ErrorType.SQLSEVERE);
        }
    }

    public static Utilisateur createcompte(String nom, String mdp,String mail) throws  DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(mail, nom, mdp));
            ResultSet rs = query("INSERT INTO compte_utilisateur(couriel_compte,pseudo_compte,mdp_compte,active) VALUES (?,?,?,true) RETURNING *;", args);
            rs.next();
            Utilisateur retour = new Utilisateur(rs.getString("couriel_compte"), rs.getString("mdp_compte"), rs.getString("pseudo_compte"), true, rs.getInt("id_compte_utilisateur"));
            rs.getStatement().close();
            return retour;
        }
        catch (SQLException sqe){
            if(22000<=sqe.getErrorCode()&&sqe.getErrorCode()<23000)
            {
                LOGGER.warning(sqe.getMessage());
                throw new DAOException(sqe,ErrorType.SQLENTRY);
            }
            else {
                LOGGER.severe(sqe.getMessage());
                throw new DAOException(sqe,ErrorType.SQLSEVERE);
            }
        }
    }

    public static Hashtable<String, Integer> displaypersonnage(Utilisateur util) throws SQLException, DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(util.getId()));
            ResultSet rs = query("SELECT id_personnage,nom_personnage FROM personnage WHERE id_compte_utilisateur=? ORDER BY id_personnage;", args);
            Hashtable<String, Integer> retour = new Hashtable<>();
            int i = 0;
            while (rs.next()) {
                retour.put(rs.getString(2), rs.getInt(1));
                i++;
            }
            rs.getStatement().close();
            return retour;
        }
        catch (SQLException sqe){
            LOGGER.severe(sqe.getMessage());
            throw new DAOException(sqe,ErrorType.SQLSEVERE);
        }
    }

    public static void update(int id) throws DAOException {
        try {
            System.out.println(id);
            queryUDC("UPDATE compte_utilisateur SET active=false WHERE id_compte_utilisateur=?;",new ArrayList<>(List.of(id)));
        } catch (SQLException e) {
            throw new DAOException(e,ErrorType.SQLSEVERE);
        }
    }

}
