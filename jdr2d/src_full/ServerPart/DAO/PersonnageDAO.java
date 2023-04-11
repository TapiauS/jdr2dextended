package ServerPart.DAO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static Logging.Jdr2dLogger.LOGGER;

import ServerPart.Control.Drones;
import Entity.*;

public abstract class PersonnageDAO extends DAOObject {

    public static BufferedImage getcharportrait(int id) throws SQLException, IOException, DAOException {
        try {
            ResultSet rs = query("SELECT id_portrait FROM personnage WHERE id_personnage=?;", new ArrayList<>(List.of(id)));
            if (rs.next()) {
                BufferedImage retour = ImageDAO.readoneimage(rs.getInt(1), "portrait");
                return retour;
            } else {
                throw new SQLDataException("Portrait indisponible");
            }
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
    public static int createchar(String nom, Utilisateur util) throws  DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(nom, util.getId()));
            queryUDC("INSERT INTO personnage(nom_personnage,id_compte_utilisateur,id_lieu) VALUES (?,?,(SELECT id_lieu FROM lieu WHERE nom_lieu='Tarante'));", args);
            ResultSet rs = query("SELECT id_personnage FROM personnage WHERE nom_personnage=? AND id_compte_utilisateur=?", args);
            rs.next();
            int retour = rs.getInt(1);
            rs.getStatement().close();
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

    public static ArrayList<Personnage> getPersonnages(Map m,Utilisateur util) throws  DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(m.getId(), util.getId()));
            ArrayList<Personnage> persos = new ArrayList<>();
            ResultSet rs = query("SELECT id_personnage FROM personnage WHERE id_lieu=? AND (id_compte_utilisateur!=? OR id_compte_utilisateur IS NULL)", args);
            while (rs.next()) {
                Personnage perso = PersonnageDAO.getchar(rs.getInt(1));
                if (perso instanceof PNJ) {
                    if (((PNJ) perso).isnomme())
                        persos.add(perso);
                    else {
                        Drones drones = new Drones((PNJ) perso);
                        for (PNJ p : drones) {
                            persos.add(p);
                        }
                    }
                }
            }
            rs.getStatement().close();
            return persos;
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



    public static Personnage getchar(int id) throws SQLException, DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(id));
            ResultSet rs = query("SELECT * FROM fichperso WHERE id_personnage=?;", args);
            rs.next();
            Personnage retour;
            if (rs.getInt("id_compte_utilisateur") != 0) {
                retour = new Personnage(rs.getInt("x"), rs.getInt("y"), MapDAO.getmap(rs.getInt("id_lieu")), getarme(id), getarmure(id), rs.getString("nom_personnage"), rs.getInt("pv"), getinv(id), rs.getInt("pvmax"), new ArrayList<>(), null);
                for (Quete q : QueteDAO.getqueteSuivie(id)) {
                    retour.addsQuete(q);
                }
            } else {
                retour = new PNJ(rs.getInt("x"), rs.getInt("y"), MapDAO.getmap(rs.getInt("id_lieu")), getarme(id), getarmure(id), rs.getString("nom_personnage"), rs.getInt("pv"), getinv(rs.getInt("id_personnage")), rs.getInt("pvmax"), null, rs.getBoolean("nomme"));
            }
            retour.setId(rs.getInt("id_personnage"));
            rs.getStatement().close();
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

    public static void updatepnj(PNJ p) throws SQLException, DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(p.getpV(), p.getId()));
            System.out.println("pnj hp= " + p.getpV());
            System.out.println("pnj id= " + p.getId());
            queryUDC("UPDATE caracterise SET valeur=? WHERE id_personnage=? AND id_statistique=(SELECT id_statistique FROM statistique WHERE nom_statistique='pV');", args);
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

    public static Coffre getinv(int id) throws DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(id));
            Coffre invent = new Coffre();
            ResultSet rs = query("SELECT id_objet FROM objet WHERE id_personnage_possede=?;", args);
            while (rs.next()) {
                invent.add(ObjetDAO.getObjet(rs.getInt("id_objet")));
            }
            return invent;
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

    public static ArrayList<Arme> getarme(int id) throws SQLException, DAOException {
        try {
            ArrayList<Arme> retour = new ArrayList<>();
            ArrayList<Object> args = new ArrayList<>(List.of(id));
            ResultSet rs = query("SELECT id_objet FROM objet JOIN" +
                    "                   type_objet ON objet.id_type_objet=type_objet.id_type_objet AND nom_type_objet='Arme' " +
                    "               WHERE id_personnage_equipe=?", args);
            while (rs.next()) {
                retour.add((Arme) ObjetDAO.getObjet(rs.getInt(1)));
            }
            rs.getStatement().close();
            ;
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


    public static ArrayList<Armure> getarmure(int id) throws DAOException {
        try {
            ArrayList<Armure> retour = new ArrayList<>();
            ArrayList<Object> args = new ArrayList<>(List.of(id));
            ResultSet rs = query("SELECT id_objet FROM objet JOIN" +
                    "                   type_objet ON objet.id_type_objet=type_objet.id_type_objet AND nom_type_objet='Armure' " +
                    "               WHERE id_personnage_equipe=?", args);
            while (rs.next()) {
                retour.add((Armure) ObjetDAO.getObjet(rs.getInt(1)));
            }
            rs.getStatement().close();
            ;
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

    public static ArrayList<Personnage> getcharclose(Personnage p, int dist) throws DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(p.getId(), dist));
            ResultSet rs = query("SELECT persocomp FROM distperso WHERE persoref=? AND diff<?;", args);
            ArrayList<Personnage> retour = new ArrayList<>();
            while (rs.next()) {
                retour.add(PersonnageDAO.getchar(rs.getInt(1)));
            }
            rs.getStatement().close();
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

    //TODO getPNJCLOSE

    public static ArrayList<Objet> getobjetclose(Personnage p, int dist) throws  DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(p.getId(), dist));
            ResultSet rs = query("SELECT id_objet FROM distobjet WHERE id_perso=? AND diff<?;", args);
            ArrayList<Objet> retour = new ArrayList<>();
            while (rs.next()) {
                retour.add((ObjetDAO.getObjet(rs.getInt(1))));
            }
            rs.getStatement().close();
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

    public static void updatedatabase(Personnage p) throws  DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(p.getX(), p.getY(), p.getLieux().getId(), p.getId()));
            queryUDC("UPDATE personnage SET x=?,y=?,id_lieu=? WHERE id_personnage=?;", args);
            ArrayList<Object> args1 = new ArrayList<>(List.of(p.getpV(), p.getId()));
            queryUDC("UPDATE caracterise SET valeur=? WHERE id_personnage=? AND id_statistique=(SELECT id_statistique FROM statistique WHERE nom_statistique='pV');", args1);
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

    public static boolean checkcharname(String name) throws  DAOException {
        try {
            ArrayList<Object> args = new ArrayList<>(List.of(name));
            ResultSet rs = query("SELECT * FROM personnage WHERE nom_personnage=?;", args);
            boolean retour = !rs.next();
            rs.getStatement().close();
            ;
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

    public static void delete(String personame) throws DAOException {
        try {
            queryUDC("DELETE FROM personnage WHERE nom_personnage=?",new ArrayList<>(List.of(personame)));
        } catch (SQLException e) {
            LOGGER.severe(e.getMessage());
            throw new DAOException(e,ErrorType.SQLSEVERE);
        }
    }
}
