package ServerPart;

import DAO.EchangeDAO;
import DAO.MapDAO;
import DAO.PersonnageDAO;
import DAO.PorteDAO;
import jdr2dcore.*;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public class MapState implements Serializable {

    private Map carte;

    private ArrayList<Client> clients;

    private ArrayList<PNJ> pnjs;

    private ArrayList<Personnage> joueurs;

    private ArrayList<Coffre> coffres;

    private ArrayList<Echange> echanges;

    private ArrayList<Porte> sorties;


    //builder

    public MapState(int id){
        try {
            carte=MapDAO.getmap(id);
            clients=new ArrayList<>();
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

    private void mapload() throws SQLException {
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

    public void addClient(Client client){
        this.clients.add(client);
    }


    //builder et setters
    public Map getCarte() {
        return carte;
    }

    public void setCarte(Map carte) {
        this.carte = carte;
    }

    public ArrayList<PNJ> getPnjs() {
        return pnjs;
    }

    public void setPnjs(ArrayList<PNJ> pnjs) {
        this.pnjs = pnjs;
    }

    public ArrayList<Personnage> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(ArrayList<Personnage> joueurs) {
        this.joueurs = joueurs;
    }

    public ArrayList<Coffre> getCoffres() {
        return coffres;
    }

    public void setCoffres(ArrayList<Coffre> coffres) {
        this.coffres = coffres;
    }

    public ArrayList<Echange> getEchanges() {
        return echanges;
    }

    public void setEchanges(ArrayList<Echange> echanges) {
        this.echanges = echanges;
    }

    public ArrayList<Porte> getSorties() {
        return sorties;
    }

    public void setSorties(ArrayList<Porte> sorties) {
        this.sorties = sorties;
    }
}
