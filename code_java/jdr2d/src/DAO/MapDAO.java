package DAO;


import jdr2dcore.*;
import jdr2dcore.Map;
import gamegenerator.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MapDAO extends DAOObject {


    public static int createMap(Map m) throws SQLException {
        CarteDealer c=new CarteDealer();
        ArrayList<Object> args=new ArrayList<>(List.of(m.getNomLieu(),c.toString(m.getCarte())));
        queryUDC("INSERT INTO lieu(nom_lieu,carte_lieu) VALUES (?,?)",args);
        ResultSet rs=query("SELECT MAX(id_lieu) FROM lieu");
        rs.next();
        int retour=rs.getInt(1);
        rs.getStatement().close();

        return retour;
    }


    public static Map getmap(int id) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(id));
        ResultSet rs=query("SELECT * FROM lieu WHERE id_lieu=?;",args);
        if(rs.next()) {
            char[][] carte = CarteDealer.carteParser(rs.getString("carte_lieu"));
            int[] dim = new int[]{carte.length, carte[0].length};
            Map retour=new Map(dim, carte, rs.getString("nom_lieu"), rs.getInt("id_lieu"));
            rs.getStatement().close();
           ;
            return retour;
        }
        rs.getStatement().close();
       ;
        throw new IllegalArgumentException("Cette map n'existe pas !");
    }

    public static ArrayList<Coffre> getcoffres(Map m) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(m.getId()));
        ResultSet rs=query("SELECT id_objet FROM objet WHERE id_lieu=? AND (SELECT is_coffre(objet.id_objet));",args);
        ArrayList<Coffre> retour=new ArrayList<>();
        while (rs.next()){
            retour.add(CoffreDAO.getcoffre(rs.getInt(1)));
        }
        rs.getStatement().close();
       ;
        return retour;
    }

}
