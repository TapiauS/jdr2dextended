package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jdr2dcore.*;
import tableau.*;
public abstract class CoffreDAO extends DAOObject {
    public static Coffre createcoffre(int id) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(id,id));
        ResultSet rs= DAOObject.query("SELECT * FROM objet WHERE contenant=? OR id_objet=?;",args);
        ArrayList<Objet> contenu=new ArrayList<>();
        Coffre retour=new Coffre();
        while (rs.next()){
            if(rs.getInt("id_objet")==id) {
                retour.setNomObjet(rs.getString("nom_objet")).setX(rs.getInt("x")).setY(rs.getInt("y")).setLieux(MapDAO.getmap(rs.getInt("id_lieu")));
            }
            else {
                contenu.add(ObjetDAO.getObjet(rs.getInt("id_objet")));
            }
        }
        retour.setContenu(contenu);
        rs.getStatement().close();
        DAOObject.close();
        return retour;
    }
}
