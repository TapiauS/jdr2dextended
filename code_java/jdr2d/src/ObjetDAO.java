import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ObjetDAO extends DAOObject{
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
        rs.next();
        Objet retour=new Objet(rs.getInt("x"),rs.getInt("y"),MapDAO.getmap(rs.getInt("id_lieu")),rs.getString("nom_objet"),rs.getInt("poid"));
        rs.getStatement().close();
        return retour;
    }

    public static void pickObjet(Personnage p,Objet o) throws SQLException{
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
