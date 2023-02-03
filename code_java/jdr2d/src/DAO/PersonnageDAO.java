package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jdr2dcore.*;
import tableau.*;
public abstract class PersonnageDAO extends DAOObject {

    public static void createchar(String nom, Race race, Utilisateur util) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(nom, race.getNomRace(),util.getId()));
        queryUDC("INSERT INTO personnage(nom_personnage,race,id_compte_utilisateur,id_lieu) VALUES (?,?,?,(SELECT id_lieu FROM lieu WHERE nom_lieu='Tarante'));",args);
        close();
    }



    public static Personnage getchar(int id) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(id));
        ResultSet rs=query("SELECT * FROM personnage WHERE id_personnage=?;",args);
        rs.next();
        //version trés basique a des fin de test, le gros du taf va se jouer sur la récupération des objectifs et des objets associées au personnage
        Personnage retour;
        if(rs.getInt("id_compte_utilisateur")!=0) {
           retour = new Personnage(rs.getInt("x"), rs.getInt("y"), MapDAO.getmap(rs.getInt("id_lieu")), getarme(id), getarmure(id), rs.getString("nom_personnage"), rs.getInt("pv"), getinv(id), rs.getInt("pvmax"),new ArrayList<>() , null);
            for (Quete q:QueteDAO.getqueteSuivie(id)) {
                retour.addsQuete(q);
            }
        }
        else {
            retour = new PNJ(rs.getInt("x"), rs.getInt("y"), MapDAO.getmap(rs.getInt("id_lieu")), getarme(id), getarmure(id), rs.getString("nom_personnage"), rs.getInt("pv"), getinv(rs.getInt("id_personnage")), rs.getInt("pvmax"), null, null,null,true);
        }
        retour.setId(rs.getInt("id_personnage"));
        rs.getStatement().close();
        return retour;
    }

    public static Coffre getinv(int id) throws SQLException{
        ArrayList<Object> args=new ArrayList<>(List.of(id));
        Coffre invent=new Coffre();
        ResultSet rs=query("SELECT id_objet FROM objet WHERE id_personnage=?;",args);
        while (rs.next()){
            invent.add(ObjetDAO.getObjet(rs.getInt("id_objet")));
        }
        return invent;
    }

    public static ArrayList<Arme> getarme(int id) throws SQLException {
        ArrayList<Arme> retour=new ArrayList<>();
        ArrayList<Object> args=new ArrayList<>(List.of(id));
        ResultSet rs=query("SELECT id_objet FROM objet JOIN" +
                "                   type_objet ON objet.id_type_objet=type_objet.id_type_objet AND nom_type_objet='Arme' " +
                "               WHERE id_personnage_equipe=?",args);
        while (rs.next()){
            retour.add((Arme) ObjetDAO.getObjet(rs.getInt(1)));
        }
        rs.getStatement().close();
        close();
        return retour;
    }


    public static ArrayList<Armure> getarmure(int id) throws SQLException{
        ArrayList<Armure> retour=new ArrayList<>();
        ArrayList<Object> args=new ArrayList<>(List.of(id));
        ResultSet rs=query("SELECT id_objet FROM objet JOIN" +
                "                   type_objet ON objet.id_type_objet=type_objet.id_type_objet AND nom_type_objet='Armure' " +
                "               WHERE id_personnage_equipe=?",args);
        while (rs.next()){
            retour.add((Armure) ObjetDAO.getObjet(rs.getInt(1)));
        }
        rs.getStatement().close();
        close();
        return retour;
    }

    public static ArrayList<Personnage> getcharclose(Personnage p, int dist) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(p.getId(),dist));
        ResultSet rs=query("SELECT persocomp FROM distperso WHERE persoref=? AND diff<?;",args);
        ArrayList<Personnage> retour=new ArrayList<>();
        while (rs.next()){
                retour.add(PersonnageDAO.getchar(rs.getInt(1)));
        }
        rs.getStatement().close();
        close();
        return retour;
    }

    //TODO getPNJCLOSE

    public static ArrayList<Objet> getobjetclose(Personnage p, int dist) throws SQLException{
        ArrayList<Object> args=new ArrayList<>(List.of(p.getId(),dist));
        ResultSet rs=query("SELECT id_objet FROM distobjet WHERE id_perso=? AND diff<?;",args);
        ArrayList<Objet> retour=new ArrayList<>();
        while (rs.next()){
                retour.add((ObjetDAO.getObjet(rs.getInt(1))));
        }
        rs.getStatement().close();
        close();
        return retour;
    }
}
