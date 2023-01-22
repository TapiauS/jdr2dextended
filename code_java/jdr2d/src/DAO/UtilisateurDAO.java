package DAO;
import jdr2dcore.*;
import tableau.*;
import DAO.DAOObject;

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
            retour=null;
        }
        rs.getStatement().close();
        close();
        return retour;
    }

    public static void createcompte(String nom, String mdp,String mail) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(mail,mdp,nom));
        queryUDC("CALL add_user(?,?,?);",args);
        close();
    }

    public static Hashtable<Integer, Integer> displaypersonnage(Utilisateur util) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(util.getId()));

        ResultSet rs = query("SELECT id_personnage FROM personnage WHERE id_compte_utilisateur=?;",args);
        Hashtable<Integer,Integer> retour=new Hashtable<>();
        int i=0;
        while (rs.next()){
            retour.put(i,rs.getInt(1));
            i++;
        }

        rs.getStatement().close();
        close();

        return retour;
    }

}
