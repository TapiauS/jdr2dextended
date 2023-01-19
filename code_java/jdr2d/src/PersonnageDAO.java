import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public abstract class PersonnageDAO extends DAOObject{

    public static void createchar(String nom,Race race,Utilisateur util) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(nom, race.getNomRace(),util.getId()));
        queryUDC("INSERT INTO personnage(nom_personnage,race,id_compte_utilisateur,id_lieu) VALUES (?,?,?,(SELECT id_lieu FROM lieu WHERE nom_lieu='Tarante'));",args);

        close();
    }

    public static Personnage getchar(int id) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(id));
        ResultSet rs=query("SELECT * FROM personnage WHERE id_personnage=?;",args);
        rs.next();
        //version trés basique a des fin de test, le gros du taf va se jouer sur la récupération des objectifs et des objets associées au personnage
        Personnage retour= new Personnage(rs.getInt("x"),rs.getInt("y"),MapDAO.getmap(rs.getInt("id_lieu")),null,null,rs.getString("nom_personnage"),rs.getInt("pv"),null,rs.getInt("pvmax"),null,null).setId(rs.getInt("id_personnage"));

        rs.getStatement().close();

        return retour;
    }

}
