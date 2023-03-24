package ServerPart.DAO;
import jdr2dcore.Map;
import jdr2dcore.Personnage;
import jdr2dcore.Porte;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class PorteDAO extends DAOObject{
    public static ArrayList<Porte> getPorte(Map m) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(m.getId()));
        ArrayList<Porte> retour=new ArrayList<>();
        ResultSet rs=query("SELECT p.id_porte as id,p.x as x,p.y as y,p.id_porte_relie,p1.x as x1,p1.y as y1,p1.id_lieu as lieu FROM porte AS p JOIN" +
                "                      porte AS p1 ON p.id_porte_relie=p1.id_porte AND p.id_lieu=?",args);
        while (rs.next()){
            Porte porterelie=new Porte(MapDAO.getmap(rs.getInt("lieu")),rs.getInt("y1"),rs.getInt("x1"));
            porterelie.setId(rs.getInt("id_porte_relie"));
            Porte door=new Porte(m,rs.getInt("y"),rs.getInt("x"),porterelie);
            door.setId(rs.getInt("id"));
            retour.add(door);
        }
        rs.getStatement().close();

        return retour;
    }

    public static void addPorte(Porte entre) throws SQLException{
         ArrayList<Object> argsentre=new ArrayList<>(List.of(entre.getX(),entre.getY(),entre.getLieux().getId()));
         ArrayList<Object> argssortie=new ArrayList<>(List.of(entre.getPortelie().getX(),entre.getPortelie().getY(),entre.getPortelie().getLieux().getId()));

         ResultSet rs=query("INSERT INTO porte(x,y,id_lieu) VALUES (?,?,?) RETURNING id_porte;",argsentre);
         rs.next();
         int identre=rs.getInt(1);
         argssortie.add(identre);
         rs.getStatement().close();

         ResultSet rs1=query("INSERT INTO porte(x,y,id_lieu,id_porte_relie) VALUES (?,?,?,?) RETURNING id_porte;",argssortie);
         rs1.next();
         int idsortie=rs1.getInt(1);
         rs1.getStatement().close();

         ArrayList<Object> argsupdate=new ArrayList<>(List.of(idsortie,identre));
         queryUDC("UPDATE porte SET id_porte_relie=? WHERE id_porte=?",argsupdate);
    }

    public static void updatedtabase(Personnage player,Porte p) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(player.getId(),p.getPortelie().getLieux().getId()));
        queryUDC("UPDATE personnage SET id_lieu=? WHERE id_personnage=?;",args);
    }
}
