package DAO;
import jdr2dcore.Map;
import jdr2dcore.Porte;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class PorteDAO extends DAOObject{
    public static ArrayList<Porte> getPorte(Map m) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(m.getId()));
        ArrayList<Porte> retour=new ArrayList<>();
        ResultSet rs=query("SELECT p.x as x,p.y as y,p.id_porte_relie,p1.x as x1,p1.y as y2,p1.id_lieu as lieu FROM porte AS p JOIN" +
                "                      porte AS p1 ON p.id_porte_relie=p1.id_porte AND p.id_lieu=?",args);
        while (rs.next()){
            Porte porterelie=new Porte(MapDAO.getmap(rs.getInt("lieu")),rs.getInt("x1"),rs.getInt("y1"));
            retour.add(new Porte(m,rs.getInt("x"),rs.getInt("y"),porterelie));
        }
        rs.getStatement().close();
        close();
        return retour;
    }
}
