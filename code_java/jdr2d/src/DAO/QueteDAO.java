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
//il ne faut surtout pas oublier d'ajouter les objectifs 1 par 1 pour que les observateurs fonctionnent
//faire un nouveau builder pour les PNJ et les personnages en géneral, sans les coordonées pour les désigner comme cible
//Pour le 23/01 : il faut stocker toutes les infos de la quéte qu'on est en train de build à chaque étape de la boucle


public abstract class QueteDAO extends DAOObject {


    public ArrayList<Quete> getqueteSuivie(int id) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(id,id));
        ResultSet rs=query("SELECT id_interaction,nom_interaction,nom_objectif,id_target,id_find,accorde.id_objet,id_objectif,id_talk,valide FROM joue_un_role JOIN" +
                                    "interaction ON code_role_quete='Q' AND id_personnage=? AND quete.id_quete=joue_un_role.id_quete"+
                                    "JOIN promet ON promet.id_quete=recompense.id_quete"                                             +
                                    "JOIN objet ON quete.id_quete=objet.id_quete"                                                    +
                                    "JOIN objectif on objectif.id_quete=quete.id_quete"                                              +
                                    "JOIN valide ON objectif.id_objectif=valide.id_objectif and valide.id_personnage=? ORDER BY id_interaction,ordre DESC;",args);
        ArrayList<Quete> retour=new ArrayList<>();
        String nom = "";
        String descript = "";
        ArrayList<Integer> listid=new ArrayList<>();
        ArrayList<Objectifs> objs=new ArrayList<>();
        ArrayList<Integer> listo=new ArrayList<>();
        ArrayList<Objet> recompense=new ArrayList<>();
        while (rs.next()){
            if(listid.contains(rs.getInt("id_quete"))||listid.isEmpty()){
                nom=rs.getString("nom_interaction");
                descript=rs.getString("description_interaction");
                if(listid.isEmpty())
                    listid.add(rs.getInt("id_quete"));
                if(!rs.getBoolean("valide"))
                    objs.add(ObjectifsDAO.getObjectif(rs.getInt("id_objectif")));
                if(listo.isEmpty()){
                    listo.add(rs.getInt("recompense"));
                }
                if(!listo.contains(rs.getInt("recompense"))){
                    listo.add(rs.getInt("recompense"));
                }
            }
            else {
                Objet[] rec = new Objet[listo.size()];
                for (int i=0;i<listo.size();i++) {
                    rec[i]=ObjetDAO.getObjet(listo.get(i));
                }
                Quete q=new Quete(nom,descript,new ArrayList<>(),rec);
                for (Objectifs o:objs) {
                    q.addObjectifs(o);
                }
                retour.add(q);
                listo.removeAll(listo);
                objs.removeAll(objs);
                listo.add(rs.getInt("recompense"));
                objs.add(ObjectifsDAO.getObjectif(rs.getInt("id_objectif")));
                nom=rs.getString("nom_interaction");
                descript=rs.getString("description_interaction");
        }
    }
        Objet[] rec = new Objet[listo.size()];
        for (int i=0;i<listo.size();i++) {
            rec[i]=ObjetDAO.getObjet(listo.get(i));
        }
        Quete q=new Quete(nom,descript,new ArrayList<>(),rec);
        for (Objectifs o:objs) {
            q.addObjectifs(o);
        }
        retour.add(q);
       return retour;
}
}
