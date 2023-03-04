import DAO.EchangeDAO;
import DAO.MapDAO;
import DAO.PersonnageDAO;
import DAO.PorteDAO;
import Graphic.MapGraph;
import jdr2dcore.*;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public class MapState implements Serializable {

    private Map carte;

    private ArrayList<PNJ> pnjs;

    private ArrayList<Personnage> joueurs;

    private ArrayList<Coffre> coffres;

    private ArrayList<Echange> echanges;

    private ArrayList<Porte> sorties;

    //builder

    public MapState(int id){
        try {
            carte=MapDAO.getmap(id);
            mapload();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        joueurs=new ArrayList<>();
    }

    //methodes

    public void addPlayer(Personnage joueur){
        this.joueurs.add(joueur);
    }

    public void removePlayer(Personnage joueur){
        this.joueurs.remove(joueur);
    }

    private  void mapload() throws SQLException {
        System.out.println("id lieu="+carte.getId());
        pnjs=new ArrayList<>();
        echanges=new ArrayList<>();
        coffres= MapDAO.getcoffres(carte);
        sorties= PorteDAO.getPorte(carte);
        for (Personnage p: PersonnageDAO.getPersonnages(carte,new Utilisateur())) {
            if(p instanceof PNJ){
                pnjs.add((PNJ) p);
                Echange start= EchangeDAO.getEchangetree((PNJ) p);
                if(start!=null)
                    echanges.add(start);
            }
        }
    }
}
