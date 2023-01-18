import java.sql.*;

public abstract class PersonnageDAO {

    public static void createchar(String nom,Race race,Utilisateur util) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://10.113.28.39:5432/jdr2d_simon", "stapiau", "Afpa54*");
        PreparedStatement st = conn.prepareStatement("INSERT INTO personnage(nom_personnage,race,id_compte_utilisateur,id_lieu) VALUES (?,?,?,(SELECT id_lieu FROM lieu WHERE nom_lieu='Tarante'));");


        st.setString(1,nom);
        st.setString(2,race.getNomRace());
        st.setInt(3,util.getId());
        st.executeQuery();


        st.close();
        conn.close();
    }

}
