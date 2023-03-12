package ServerPart;

import ServerPart.Control.GameZonePersoThread;
import ServerPart.Control.IAProtocolServer;
import ServerPart.DAO.EchangeDAO;
import ServerPart.DAO.MapDAO;
import ServerPart.DAO.PersonnageDAO;
import ServerPart.DAO.PorteDAO;
import jdr2dcore.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

public class GameZone {

    private Map carte;

    private ArrayList<ClientMainChannel> clients;

    private ArrayList<PNJ> pnjs;

    private ArrayList<Personnage> joueurs;

    private ArrayList<Coffre> coffres;

    private ArrayList<Echange> echanges;

    private ArrayList<Porte> sorties;

    private Hashtable<Integer, IAProtocolServer> communicationadresses;


    private MapState statut;
    //builder

    public GameZone(int id){
        try {
            carte=MapDAO.getmap(id);
            clients=new ArrayList<>();
            communicationadresses=new Hashtable<>();
            mapload();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        joueurs=new ArrayList<>();
        statut=new MapState(this);
        new GameZonePersoThread(this);
    }


    public void updateClients(){
        for (ClientMainChannel c:clients) {
            try {
                c.getOutput().writeObject(statut);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //methodes

    public ClientMainChannel getClient(Personnage player){
        for (ClientMainChannel c: clients) {
            for (Personnage perso: joueurs) {
                if(c.getAvatar().equals(perso))
                    return c;
            }

        }
        return null;
    }

    public void addPlayer(Personnage joueur){
        this.joueurs.add(joueur);
    }

    public void removePlayer(Personnage joueur){
        this.joueurs.remove(joueur);
    }

    private void mapload() throws SQLException {
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

    public void addClient(ClientMainChannel client){
        this.clients.add(client);
        this.joueurs.add(client.getAvatar());
    }

    public void removeClient(ClientMainChannel client){
        this.clients.remove(client);
        this.joueurs.remove(client.getAvatar());
    }

    public void addAdress(int idperso, IAProtocolServer adress){
        communicationadresses.put(idperso,adress);
        System.out.println("je rajoute l'adresse");
    }

    public IAProtocolServer getChannel(Personnage joueur){
        return communicationadresses.get(joueur.getId());
    }

    //builder et setters


    public MapState getStatut() {
        return statut=new MapState(this);
    }

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
