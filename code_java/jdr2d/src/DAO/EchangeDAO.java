package DAO;
import jdr2dcore.*;
import tableau.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class EchangeDAO extends DAOObject{
    /*
    protected PNJ parleur;
    protected String question;
    protected String reponse;
    protected Echange[] dialogueSuivant;
    protected boolean donnequete;
    protected boolean objectifquete;
    protected ObjectifT objectifT; // on va ignorer pour l'instant
    protected Quete quete; //idem
    protected Echange dialoguealternatif;
    protected int id;
     */

    //il manque la recupération des quéte données
    public static Echange getEchangetree(PNJ perso) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(perso.getId()));
        ResultSet rs=query("SELECT contenu_dialogue,choix,embranchement.id_dialogue FROM dialogue JOIN" +
                                    "precede ON precede.id_dialogue=(SELECT startdialogue(?)) JOIN" +
                "                    embranchement ON embranchement.id_embranchement=precede.id_embranchement;",args);
        ArrayList<Integer> listid=new ArrayList<>();
        String reponse="";
        while (rs.next()){
            listid.add(rs.getInt("id_embranchement"));
            reponse=rs.getString("contenu_dialogue");
        }
        Echange [] echangsuiv=new Echange[listid.size()];
        for (int i=0;i<listid.size();i++) {
            echangsuiv[i]=getechangesuivant(listid.get(i));
        }
        Echange retour=new Echange(perso,null,reponse,echangsuiv);
        rs.getStatement().close();
        close();
        return retour;
    }

    private static Echange getechangesuivant(int id) throws SQLException {
        ArrayList<Object> args=new ArrayList<>(List.of(id));
        ResultSet rs=query("SELECT eb0.choix,contenu_dialogue,eb1.id_embranchement,interaction.id_interaction as id_suiv FROM embranchement as eb0 JOIN" +
                "                 dialogue ON embranchement.id_dialogue=dialogue.id_dialogue AND eb0.id_embranchement=? LEFTJOIN precede" +
                "                          ON precede.id_dialogue=dialogue.id_dialogue JOIN embranchement as eb1" +
                "                          ON eb1.id_embranchement=precede.id_embranchement LEFTJOIN donne " +
                "                          ON dialogue.id_dialogue=donne.id_dialogue JOIN interaction" +
                "                          ON donne.id_interaction=interaction.id_interaction;",args);
        ArrayList<Integer> listid=new ArrayList<>();
        String reponse="";
        String question="";
        Quete q=null;
        while (rs.next()){
            if(rs.getInt("id_interaction")!=0)
                q=QueteDAO.getQuete(rs.getInt("id_interaction"));
            if(rs.getInt("id_suiv")!=0)
                listid.add(rs.getInt("id_suiv"));
            reponse=rs.getString("contenu_dialogue");
            question=rs.getString("choix");
        }
        if()

    }



}
