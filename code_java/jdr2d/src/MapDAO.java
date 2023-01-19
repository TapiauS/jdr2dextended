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


   // le string en retour est temporaire
    public static ArrayList<String> getchar(Map m) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(m.getId()));
        ResultSet rs=query("SELECT nom_personnage FROM personnage WHERE id_lieu=?;",args);
        ArrayList<String> retour=new ArrayList<>();
        while (rs.next()){
            retour.add(rs.getString(1));
        }
        rs.getStatement().close();
        close();
        return retour;

    }
}
