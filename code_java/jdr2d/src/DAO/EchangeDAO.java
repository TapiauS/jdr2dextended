package DAO;
import jdr2dcore.Echange;
import jdr2dcore.ObjectifT;
import jdr2dcore.PNJ;
import jdr2dcore.Quete;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class EchangeDAO extends DAOObject{

    //JE CROIS que c'est pour les objectifs de quete que je suis sencé utiliser getparleur
    public static PNJ getparleur(int id) throws SQLException{
        return new PNJ();
    }
    public static Echange getEchangetree(PNJ perso) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(perso.getId()));
        ResultSet rs=query("SELECT dialogue.id_dialogue as dial,contenu_dialogue,choix,embranchement.id_embranchement,interaction.id_interaction,objectif.id_objectif FROM dialogue JOIN \n" +
                "                precede ON precede.id_dialogue=(SELECT startdialogue(?)) AND precede.id_dialogue=dialogue.id_dialogue JOIN \n" +
                "                embranchement ON embranchement.id_embranchement=precede.id_embranchement LEFT JOIN \n" +
                "                donne ON donne.id_dialogue=dialogue.id_dialogue LEFT JOIN interaction\n" +
                "                ON interaction.id_interaction=donne.id_interaction LEFT JOIN objectif\n" +
                "                ON dialogue.id_dialogue=objectif.id_dialogue;",args);

        ArrayList<Integer> listid=new ArrayList<>();
        String reponse="";
        Quete q=null;
        int id=0;
        ObjectifT o=null;
        while (rs.next()){
            if(rs.getInt("id_objectif")!=0)
                o= (ObjectifT) ObjectifsDAO.getObjectif(rs.getInt("id_objectif"));
            id=rs.getInt("dial");
            if(rs.getInt("id_interaction")!=0)
                q=QueteDAO.getQuete(rs.getInt("id_interaction"));
            listid.add(rs.getInt("id_embranchement"));
            reponse=rs.getString("contenu_dialogue");
        }
        if(id==0)
            return null;
        Echange [] echangsuiv=new Echange[listid.size()];
        for (int i=0;i<listid.size();i++) {
            System.out.println("id_embranchement = "+listid.get(i));
            echangsuiv[i]=getechangesuivant(perso,listid.get(i));
        }
        if(q==null) {
            if(o==null) {
                Echange retour = new Echange(perso, null, reponse, echangsuiv);
                retour.setId(id);
                rs.getStatement().close();
                close();
                return retour;

            }
            else{
                Echange retour = new Echange(perso, null, reponse, echangsuiv,false,null,new Echange(),o);
                retour.setId(id);
                rs.getStatement().close();
                close();
                return retour;
            }
        }
        else{
            if(o==null) {
                Echange retour = new Echange(perso, null, reponse, echangsuiv,true,q);
                retour.setId(id);
                rs.getStatement().close();
                close();
                return retour;
            }
            else{
                Echange retour = new Echange(perso, null, reponse, echangsuiv,true,q,new Echange(),o);
                retour.setId(id);
                rs.getStatement().close();
                close();
                return retour;
            }
        }
    }

    private static Echange getechangesuivant(PNJ perso,int id) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(id));
        ResultSet rs=query("SELECT dialogue.id_dialogue,eb0.choix,contenu_dialogue,eb1.id_embranchement  as id_suiv,interaction.id_interaction,id_objectif FROM embranchement as eb0 JOIN" +
                "                 dialogue ON eb0.id_dialogue=dialogue.id_dialogue AND eb0.id_embranchement=? LEFT JOIN precede" +
                "                          ON precede.id_dialogue=dialogue.id_dialogue LEFT JOIN embranchement as eb1" +
                "                          ON eb1.id_embranchement=precede.id_embranchement LEFT JOIN donne " +
                "                          ON dialogue.id_dialogue=donne.id_dialogue LEFT JOIN interaction " +
                "                          ON donne.id_interaction=interaction.id_interaction LEFT JOIN objectif" +
                "                          ON dialogue.id_dialogue=objectif.id_dialogue;",args);
        ArrayList<Integer> listid=new ArrayList<>();
        String reponse="";
        String question="";
        Quete q=null;
        ObjectifT o=null;
        int ids=0;
        while (rs.next()){
            ids=rs.getInt("id_dialogue");
            if(rs.getInt("id_objectif")!=0)
                o= (ObjectifT) ObjectifsDAO.getObjectif(rs.getInt("id_objectif"));
            if(rs.getInt("id_interaction")!=0)
                q=QueteDAO.getQuete(rs.getInt("id_interaction"));
            if(rs.getInt("id_suiv")!=0)
                listid.add(rs.getInt("id_suiv"));
            reponse=rs.getString("contenu_dialogue");
            question=rs.getString("choix");
        }
        if(listid.isEmpty())
            if(q==null){
                if(o==null) {
                    Echange retour = new Echange(perso, question, reponse, null, false, null);
                    retour.setId(ids);
                    rs.getStatement().close();
                    close();
                    return retour;
                }
                else{
                    Echange retour = new Echange(perso, null, reponse, null,false,null,null,o);
                    retour.setId(ids);
                    rs.getStatement().close();
                    close();
                    return retour;
                }
            }
        else{
            if(o==null) {
                Echange retour = new Echange(perso, question, reponse, null, true, q);
                retour.setId(ids);
                rs.getStatement().close();
                close();
                return retour;
            }
            else {
                Echange retour = new Echange(perso, question, reponse, null,true,q,null,o);
                retour.setId(ids);
                rs.getStatement().close();
                close();
                return retour;
            }
            }
        else{
            Echange [] echangsuiv=new Echange[listid.size()];
            for (int i=0;i<listid.size();i++) {
                echangsuiv[i]=getechangesuivant(perso,listid.get(i));
            }
            if(q==null) {
                if(o==null) {
                    Echange retour = new Echange(perso, question, reponse, echangsuiv, false, null);
                    rs.getStatement().close();
                    close();
                    retour.setId(ids);
                    return retour;
                }
                else {
                    Echange retour = new Echange(perso, question, reponse, echangsuiv,false,null,null,o);
                    retour.setId(ids);
                    rs.getStatement().close();
                    close();
                    return retour;
                }
            }
            else {
                if(o==null) {
                    Echange retour = new Echange(perso, question, reponse, echangsuiv, true, q);
                    retour.setId(ids);
                    rs.getStatement().close();
                    close();
                    return retour;
                }
                else{
                    Echange retour = new Echange(perso, question, reponse, echangsuiv,true,q,null,o);
                    retour.setId(ids);
                    rs.getStatement().close();
                    close();
                    return retour;
                }
            }
        }
    }
}
