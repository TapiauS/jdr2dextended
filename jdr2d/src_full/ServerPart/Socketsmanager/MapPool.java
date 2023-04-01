package ServerPart.Socketsmanager;

import ServerPart.Control.GameZone;
import ServerPart.DAO.MapDAO;

import java.sql.SQLException;
import java.util.ArrayList;



public abstract class MapPool {

    private static final ArrayList<GameZone> listmaps;

    static {
        try {
            listmaps = init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //methodes


    public static ArrayList<GameZone> init() throws SQLException {
        int idmap=1;
        while (MapDAO.getmap(idmap)==null) {
            idmap++;
        }
        ArrayList<GameZone> maps=new ArrayList<>();
        while (MapDAO.getmap(idmap)!=null){
            System.out.println("lancement ?");
            maps.add(new GameZone(idmap));
            idmap++;
        }
        return maps;
    }

    public static void addClient(ServerMainChannel client){
        for (GameZone m: listmaps) {
            if(client.getAvatar().getLieux().getId()==m.getCarte().getId()) {
                m.addClient(client);
                client.setMap(m);
                return;
            }
        }
        throw new IllegalArgumentException("Cette map n'existe pas");
    }


    public static GameZone getGameZone(int idmap){
        for (GameZone zone:listmaps) {
            if(zone.getCarte().getId()==idmap)
                return zone;
        }
        return null;
    }

    //getters

    public static ArrayList<GameZone> getLismaps(){
        return listmaps;
    }

}
