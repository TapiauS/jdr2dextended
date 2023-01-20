import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MapDAO extends DAOObject{
    public static Map getmap(int id) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(id));
        ResultSet rs=query("SELECT * FROM lieu WHERE id_lieu=?;",args);
        if(rs.next()) {
            char[][] carte = CarteDealer.carteParser(rs.getString("carte_lieu"));
            int[] dim = new int[]{carte.length, carte[0].length};
            Map retour=new Map(dim, carte, rs.getString("nom_lieu"), rs.getInt("id_lieu"));
            rs.getStatement().close();
            close();
            return retour;
        }
        rs.getStatement().close();
        close();
        throw new IllegalArgumentException("Cette map n'existe pas !");


    }


   // les inventaires et équipements pas encore gérés, ca arrive promis


}
