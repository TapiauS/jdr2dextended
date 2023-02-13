package DAO;
import jdr2dcore.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public abstract class UtilisateurDAO extends DAOObject {

    public static Utilisateur connectcompte(String nom, String mdp) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(nom,mdp));
        ResultSet rs=query("SELECT * FROM compte_utilisateur WHERE pseudo_compte=? AND mdp_compte=?",args);
        Utilisateur retour=new Utilisateur();
        if(rs.next()){
            retour=new Utilisateur(rs.getString("couriel_compte"),rs.getString("mdp_compte"),rs.getString("pseudo_compte"),true,rs.getInt("id_compte_utilisateur"));
        }
        else {
            System.err.println("coucou je passe ici");
            throw new SQLDataException();
        }
        rs.getStatement().close();
        close();
        return retour;
    }




    public static boolean checkmdp(String mdp) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(mdp));
        ResultSet rs=query("SELECT * FROM compte_utilisateur WHERE mdp_compte=?",args);
        if(rs.next()) {
            rs.getStatement().close();
            close();
            return false;
        }
        else {
            rs.getStatement().close();
            close();
            return true;
        }
    }

    public static boolean checkpseudo(String pseudo) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(pseudo));
        ResultSet rs=query("SELECT * FROM compte_utilisateur WHERE pseudo_compte=?",args);
        if(rs.next()) {
            rs.getStatement().close();
            close();
            return false;
        }
        else {
            rs.getStatement().close();
            close();
            return true;
        }
    }



    public static boolean checkmail(String mail) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(mail));
        ResultSet rs=query("SELECT * FROM compte_utilisateur WHERE couriel_compte=?",args);
        if(rs.next()) {
            rs.getStatement().close();
            close();
            return false;
        }
        else {
            rs.getStatement().close();
            close();
            return true;
        }
    }

    public static void createcompte(String nom, String mdp,String mail) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(mail,nom,mdp));
        queryUDC("CALL add_user(?,?,?);",args);
        close();
    }

    public static Hashtable<String, Integer> displaypersonnage(Utilisateur util) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(util.getId()));

        ResultSet rs = query("SELECT id_personnage,nom_personnage FROM personnage WHERE id_compte_utilisateur=? ORDER BY id_personnage;",args);
        Hashtable<String,Integer> retour=new Hashtable<>();
        int i=0;
        while (rs.next()){
            retour.put(rs.getString(2), rs.getInt(1));
            i++;
        }

        rs.getStatement().close();
        close();

        return retour;
    }

}
