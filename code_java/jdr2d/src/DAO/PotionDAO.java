package DAO;
import jdr2dcore.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public abstract class PotionDAO {
    public static Potion getpotion(ResultSet rs) throws SQLException {
        int [] effets=new int[4];
        effets[0]=rs.getInt("deg");
        effets[1]=rs.getInt("redudeg");
        effets[2]=rs.getInt("pv");
        effets[3]=rs.getInt("pvmax");

        Duration d;
        d=Duration.of(rs.getInt("duree"), ChronoUnit.SECONDS);
        Potion retour=new Potion(rs.getString("nom_objet"),rs.getInt("poid"),effets,d);
        return retour;
    }
}
