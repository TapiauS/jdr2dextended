package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdr2dcore.*;

public abstract class ObjetDAO extends DAOObject {
    public static void create(Objet o) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(o.getNomObjet(),o.getPoid(),o.getX(),o.getY(),o.getLieux().getId()));
        queryUDC("INSERT INTO objet(nom_objet,poid,x,y,id_lieu) VALUES (?,?,?,?,?)",args);
        close();
    }

    public static String getObjettype(int id) throws SQLException{
        ArrayList<Object> args=new ArrayList<>(List.of(id));
        ResultSet rs=query("SELECT nom_type_objet FROM type_objet JOIN objet ON objet.id_type_objet=type_objet.id_type_objet WHERE id_objet=?",args);
        String retour="";
        if(rs.next()) {
            retour = rs.getString(1);
        }
        rs.getStatement().close();
        close();
        return retour;
    }

    public static Objet getObjet(int id) throws SQLException{
        ArrayList<Object> args=new ArrayList<>(List.of(id));
        ResultSet rs=query("SELECT id_objet,nom_objet,deg,redudeg,x,y,nbrmain,emplacement,id_lieu,poid,is_coffre(id_objet),nom_type_objet,pv,pvmax,duree FROM fichobjet WHERE id_objet=?;",args);
        Objet retour=null;
        rs.next();
        if(rs.getBoolean("is_coffre")) {
            retour = CoffreDAO.getcoffre(rs.getInt("id_objet"));
            rs.getStatement().close();
            close();
            return retour;
        }
        if(rs.getInt("id_lieu")!=0) {
            switch (rs.getString("nom_type_objet")) {
                case "Arme":
                    retour = new Arme(rs.getInt("y"), rs.getInt("x"), MapDAO.getmap(rs.getInt("id_lieu")), rs.getString("nom_objet"), rs.getInt("poid"), rs.getInt("deg"), rs.getInt("deg"), rs.getInt("nbrmain")).setId(rs.getInt("id_objet"));
                    break;
                case "Armure":
                    retour = new Armure(rs.getInt("y"), rs.getInt("x"), MapDAO.getmap(rs.getInt("id_lieu")), rs.getString("nom_objet"), rs.getInt("poid"), rs.getInt("deg"), rs.getInt("deg"), rs.getString("type_armure")).setId(rs.getInt("id_objet"));
                    break;
                case "Potion":
                    retour=PotionDAO.getpotion(rs);
                    retour.setLieux(MapDAO.getmap(rs.getInt("id_lieu"))).setX(rs.getInt("y")).setY(rs.getInt("x"));
                    break;
                default:
                    retour = new Objet(rs.getInt("y"), rs.getInt("x"), MapDAO.getmap(rs.getInt("id_lieu")), rs.getString("nom_objet"), rs.getInt("poid"));
                    break;
            }
        }
        else{
            switch (rs.getString("nom_type_objet")) {
                case "Arme":
                    retour = new Arme( rs.getString("nom_objet"), rs.getInt("poid"), rs.getInt("deg"), rs.getInt("deg"), rs.getInt("nbrmain")).setId(rs.getInt("id_objet"));
                    break;
                case "Armure":
                    retour = new Armure( rs.getString("nom_objet"), rs.getInt("poid"), rs.getInt("deg"), rs.getInt("deg"), rs.getString("emplacement")).setId(rs.getInt("id_objet"));
                    break;
                case "Potion":
                    retour=PotionDAO.getpotion(rs);
                    break;
                default:
                    retour = new Objet( rs.getString("nom_objet"), rs.getInt("poid"));
                    break;
            }
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

    public static void equip(Personnage p,Objet a) throws SQLException{
        ArrayList<Object> args=new ArrayList<>(List.of(p.getId(),a.getId()));
        queryUDC("UPDATE objet SET id_personnage_equipe=? WHERE id_objet=?;",args);
        close();
    }

    public static void desequip(Objet o) throws SQLException{
        ArrayList<Object> args=new ArrayList<>(List.of(o.getId()));
        queryUDC("UPDATE objet SET id_personnage_equipe=null WHERE id_objet=?;",args);
        close();
    }

    public static void dropObjet(Objet o) throws SQLException{
        ArrayList<Object> args=new ArrayList<>(List.of(o.getId()));
        queryUDC("UPDATE objet SET id_personnage_possede=null WHERE id_objet=?",args);
        close();
    }

    public static void addObjetCoffre(Objet o,int id) throws SQLException{
        switch (o.getClass().getName()){
            case "jdr2dcore.Arme":
                Arme a=(Arme) o;
                ArrayList<Object> args0=new ArrayList<>(List.of(a.getNomObjet(),a.getPoid(),a.getDeg(),a.getRedudeg(),a.getNbrmain(),id));
                queryUDC("CALL add_arme(?,?,?,?,?,?);",args0);
                close();
                break;
            case "jdr2dcore.Armure":
                Armure ar=(Armure) o;
                ArrayList<Object> argar=new ArrayList<>(List.of(ar.getNomObjet(),ar.getPoid(),ar.getDeg(),ar.getRedudeg(),ar.getTypearmure(),id));
                queryUDC("CALL add_armure(?,?,?,?,?,?);",argar);
                close();
                break;
            case "jdr2dcore.Potion":
                Potion pot=(Potion) o;
                ArrayList<Object> argpot=new ArrayList<>(List.of(pot.getNomObjet(),pot.getPoid(),pot.getEffets()[0],pot.getEffets()[1],pot.getEffets()[2],pot.getEffets()[3],(int) pot.getDuree().getSeconds(),id));
                queryUDC("CALL add_potion(?,?,?,?,?,?,?,?);",argpot);
                close();
                break;
            case "jdr2dcore.Coffre":

                break;
            default:
        }
    }

    public static int addcoffre(String nomcoffre,int x,int y,Map m) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(nomcoffre,x,y,m.getId()));
        ResultSet rs=query("SELECT add_coffre(?,?,?,?)",args);
        rs.next();
        int retour=rs.getInt(1);
        rs.getStatement().close();
        close();
        return retour;
    }

    public static int addcoffre(String nomcoffre,int id) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(nomcoffre,id));
        ResultSet rs=query("SELECT add_coffre(?,?)",args);
        rs.next();
        int retour=rs.getInt(1);
        rs.getStatement().close();
        close();
        return retour;
    }

}
