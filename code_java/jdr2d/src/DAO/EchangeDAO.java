package DAO;
import jdr2dcore.*;
import tableau.*;

import java.sql.ResultSet;
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
    public static Echange getEchangetree(PNJ perso){
        ArrayList<Object> args=new ArrayList<>(List.of(perso.getId()));
        ResultSet rs=query("SELECT contenu_dialogue,choix,embranchement.id_dialogue FROM dialogue JOIN" +
                                    "precede ON precede.id_dialogue=(SELECT startdialogue(?)) JOIN" +
                "                    embranchement ON embranchement.id_embranchement=precede.id_embranchement;")
    }

    private static Echange getechangesuivant(int id){

    }



}
