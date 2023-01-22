package DAO;
import jdr2dcore.*;
import tableau.*;
import DAO.DAOObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

//il va falloir modifier la base de donnée pour que cette méthode fonctionne mais l'esprit reste le même, faire attention aux noms des colonnes
//il ne faut surtout pas oublier d'ajouter les objectifs 1 par 1 pour que les observateurs fonctionent
//faire un nouveau builder pour les PNJ et les personnages en géneral, sans les coordonées pour les désigner comme cible
//Pour le 23/01 : il faut stocker toutes les infos de la quéte qu'on est en train de build à chaque étape de la boucle


public abstract class QueteDAO extends DAOObject {
    public ArrayList<Quete> getqueteSuivie(int id) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(id,id));
        ResultSet rs=query("Select id_interaction,nom_interaction,nom_objectif,id_target,id_find,accorde.id_objet,id_objectif,id_talk,valide FROM joue_un_role JOIN" +
                                    "interaction ON code_role_quete='Q' AND id_personnage=? AND quete.id_quete=joue_un_role.id_quete"+
                                    "JOIN promet ON promet.id_quete=recompense.id_quete"                                             +
                                    "JOIN objet ON quete.id_quete=objet.id_quete"                                                    +
                                    "JOIN objectif on objectif.id_quete=quete.id_quete"                                              +
                                    "JOIN valide ON objectif.id_objectif=valide.id_objectif and valide.id_personnage=? ORDER BY ordre_objectif",args);
        ArrayList<Quete> retour=new ArrayList<>();
        ArrayList<Integer> listid=new ArrayList<>();
        Hashtable<Integer,Objectifs> ordreobj=new Hashtable<>();
        while (rs.next()){
            if(listid.contains(rs.getInt("id_quete"))||listid.isEmpty()){
                if(listid.isEmpty())
                    listid.add(rs.getInt("id_quete"));
                if(rs.getInt("target")!=0)
                ordreobj.put(rs.getInt("ordre"),new ObjectifK(new PNJ()))
            }
            else {
            retour.add(,rs.))
        }
    }
}
