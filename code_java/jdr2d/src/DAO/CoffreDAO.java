package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdr2dcore.Coffre;
import jdr2dcore.Objet;

public abstract class CoffreDAO extends DAOObject {
    public static Coffre getcoffre(int id) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(id,id));
        ResultSet rs= DAOObject.query("SELECT * FROM objet WHERE contenant=? OR id_objet=?;",args);
        ArrayList<Objet> contenu=new ArrayList<>();
        Coffre retour=new Coffre();

            while (rs.next()) {
                if(rs.getInt("id_lieu")!=0) {
                    if (rs.getInt("id_objet") == id) {
                        retour.setNomObjet(rs.getString("nom_objet")).setLieux(MapDAO.getmap(rs.getInt("id_lieu")))
                                .setX(rs.getInt("y"))
                                .setY(rs.getInt("x"));
                    } else {
                        contenu.add(ObjetDAO.getObjet(rs.getInt("id_objet")));
                    }
                }
                else{
                    if (rs.getInt("id_objet") == id) {
                        System.out.println("je ne suis pas sencer passer par la");
                        retour.setNomObjet(rs.getString("nom_objet"));
                    } else {
                        contenu.add(ObjetDAO.getObjet(rs.getInt("id_objet")));
                    }
            }
        }
        retour.setContenu(contenu);
        rs.getStatement().close();

        return retour;
    }

}
