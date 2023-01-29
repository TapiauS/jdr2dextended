package DAO;
import jdr2dcore.*;
import tableau.*;

import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ObjectifsDAO extends DAOObject{
    public static Objectifs getObjectif(int id) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(id));
        ResultSet rs=query("SELECT * FROM objectif WHERE id_objectif=?",args);
        rs.next();
        if(rs.getInt("id_personnage")!=0){
            ObjectifK retour=new ObjectifK((PNJ) PersonnageDAO.getchar(rs.getInt("id_personnage")));
            retour.setId(id);
            return retour;
        }
        if(rs.getInt("id_objet")!=0){
            ObjectifF retour=new ObjectifF(ObjetDAO.getObjet(rs.getInt("id_objet")));
            retour.setId(id);
            return retour;
        }
        /*
        if(rs.getInt("id_dialogue")!=0){
            return new ObjectifT(ObjetDAO.getObjet(rs.getInt("id_objet")));
        }
        */
        throw new SQLDataException("Objectif d'aucun type définie");
    }
}
