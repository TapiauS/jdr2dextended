import java.sql.*;
import java.util.Hashtable;

public abstract class UtilisateurDAO {

    public static Utilisateur connectcompte(String nom,String mdp) throws SQLException {
        Connection conn= Connect.conn;
        PreparedStatement st=conn.prepareStatement("SELECT * FROM compte_utilisateur WHERE pseudo_compte=? AND mdp_compte=?");
        st.setString(1,nom);
        st.setString(2,mdp);
        ResultSet rs=st.executeQuery();
        Utilisateur retour=new Utilisateur();
        if(rs.next()){
            retour=new Utilisateur(rs.getString("couriel_compte"),rs.getString("mdp_compte"),rs.getString("pseudo_compte"),true);
        }
        else {
            retour=null;
        }
        rs.close();
        st.close();
        return retour;
    }

    public static void createcompte(String nom, String mdp,String mail) throws SQLException {
        Connection conn =  DriverManager.getConnection("jdbc:postgresql://10.113.28.39:5432/jdr2d_simon", "stapiau", "Afpa54*");
        PreparedStatement st=conn.prepareStatement("CALL add_user(?,?,?);");
        st.setString(2,nom);
        st.setString(1,mail);
        st.setString(3,mdp);
        st.executeUpdate();
        st.close();
        conn.close();
    }

    public static Hashtable<Integer, Integer> displaypersonnage(Utilisateur util) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://10.113.28.39:5432/jdr2d_simon", "stapiau", "Afpa54*");
        PreparedStatement st0 = conn.prepareStatement("SELECT id_compte_utilisateur FROM compte_utilisateur WHERE pseudo_compte=?;");
        st0.setString(1, util.getNomUtilisateur());
        ResultSet rs0 = st0.executeQuery();
        rs0.next();
        int id_compte = rs0.getInt("id_compte_utilisateur");
        PreparedStatement st = conn.prepareStatement("SELECT id_personnage FROM personnage WHERE id_compte_utilisateur=?;");
        st.setInt(1, id_compte);
        ResultSet rs = st.executeQuery();
        Hashtable<Integer,Integer> retour=new Hashtable<>();
        int i=0;
        while (rs.next()){
            retour.put(i,rs.getInt(1));
            i++;
        }
        rs.close();
        st.close();
        rs0.close();
        st0.close();
        conn.close();
        return retour;
    }

}
