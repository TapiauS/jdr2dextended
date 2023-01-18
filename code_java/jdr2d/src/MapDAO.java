import java.sql.*;
import java.util.ArrayList;

public abstract class MapDAO {
    public static Map getmap(String nom) throws SQLException {
        Connection conn =  DriverManager.getConnection("jdbc:postgresql://10.113.28.39:5432/jdr2d_simon", "stapiau", "Afpa54*");
        PreparedStatement st=conn.prepareStatement("SELECT * FROM lieu WHERE nom_lieu=?;");
        st.setString(1,nom);
        ResultSet rs=st.executeQuery();
        while(rs.next()) {
            char[][] carte = CarteDealer.carteParser(rs.getString("carte_lieu"));
            int[] dim = new int[]{carte.length, carte[0].length};
            return new Map(dim, carte, rs.getString("nom_lieu"), rs.getInt("id_lieu"));
        }
        throw new IllegalArgumentException("Cette map n'existe pas !");
    }


   // le string en retour est temporaire
    public static ArrayList<String> getchar(Map m) throws SQLException {
        Connection conn =  DriverManager.getConnection("jdbc:postgresql://10.113.28.39:5432/jdr2d_simon", "stapiau", "Afpa54*");
        PreparedStatement st=conn.prepareStatement("SELECT nom_personnage FROM personnage WHERE id_lieu=?;");
        st.setInt(1,m.getId());
        ResultSet rs=st.executeQuery();
        ArrayList<String> retour=new ArrayList<>();
        while (rs.next()){
            retour.add(rs.getString(1));
        }
        return retour;
    }
}
