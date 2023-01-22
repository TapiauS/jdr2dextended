package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jdr2dcore.*;
import tableau.*;
public abstract class ObjetDAO extends DAOObject {
    public static void create(Objet o) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(o.getNomObjet(),o.getPoid(),o.getX(),o.getY(),o.getLieux().getId()));
        queryUDC("INSERT INTO objet(nom_objet,poid,x,y,id_lieu) VALUES (?,?,?,?,?)",args);
        close();
    }

    public static String getObjettype(int id) throws SQLException{
        ArrayList<Object> args=new ArrayList<>(List.of(id));
        ResultSet rs=query("SELECT nom_type_objet FROM type_objet JOIN objet ON objet.id_type_objet=type_objet.id_type_objet WHERE id_objet=?",args);
        String retour=rs.getString(1);
        rs.getStatement().close();
        close();
        return retour;
    }

    public static Objet getObjet(int id) throws SQLException{
        ArrayList<Object> args=new ArrayList<>(List.of(id));
        ResultSet rs=query("SELECT * FROM objet WHERE id_objet=?;",args);
        Objet retour;
        rs.next();
        switch (ObjetDAO.getObjettype(id)) {
            case "jdr2dcore.Arme":
                retour = new Arme(rs.getInt("x"), rs.getInt("y"), MapDAO.getmap(rs.getInt("id_lieu")), rs.getString("nom_objet"), rs.getInt("poid"), rs.getInt("deg"), rs.getInt("deg"), rs.getInt("nbrmain")).setId(rs.getInt("id_objet"));
                break;
            case "jdr2dcore.Armure":
                retour = new Armure(rs.getInt("x"), rs.getInt("y"), MapDAO.getmap(rs.getInt("id_lieu")), rs.getString("nom_objet"), rs.getInt("poid"), rs.getInt("deg"), rs.getInt("deg"), rs.getString("type_armure")).setId(rs.getInt("id_objet"));
                break;
            /*case "jdr2dcore.Potion":
                retour=new jdr2dcore.Potion(rs.getInt("x"),rs.getInt("y"),DAO.MapDAO.getmap(rs.getInt("id_lieu")),rs.getString("nom_objet"),rs.getInt("poid"),rs.getArray("effets"),rs.getObject("duree"));*/
            default:
                retour = new Objet(rs.getInt("x"), rs.getInt("y"), MapDAO.getmap(rs.getInt("id_lieu")), rs.getString("nom_objet"), rs.getInt("poid"));
                break;
        }
        rs.getStatement().close();
        close();
        return retour;
    }

    public static void pickObjet(Personnage p, Objet o) throws SQLException{
        ArrayList<Object> args=new ArrayList<>(List.of(p.getId(),o.getId()));
        queryUDC("UPDATE objet SET id_personnage_possede=? WHERE id_objet=?",args);
        close();
    }

    public static void dropObjet(Objet o) throws SQLException{
        ArrayList<Object> args=new ArrayList<>(List.of(o.getId()));
        queryUDC("UPDATE objet SET id_personnage_possede=null WHERE id_objet=?",args);
        close();
    }
}
