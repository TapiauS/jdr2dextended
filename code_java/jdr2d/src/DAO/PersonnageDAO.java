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
        Personnage retour= new Personnage(rs.getInt("x"),rs.getInt("y"), MapDAO.getmap(rs.getInt("id_lieu")),null,null,rs.getString("nom_personnage"),rs.getInt("pv"),null,rs.getInt("pvmax"),null,null).setId(rs.getInt("id_personnage"));

        rs.getStatement().close();

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
