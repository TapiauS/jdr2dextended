package ServerPart;

import DAO.MapDAO;

import java.sql.SQLException;
import java.util.ArrayList;



public abstract class MapPool {

    private static final ArrayList<MapState> listmaps;

    static {
        try {
            listmaps = getmaps();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //methodes


    private static ArrayList<MapState> getmaps() throws SQLException {
        int idmap=1;
        while (MapDAO.getmap(idmap)==null) {
            idmap++;
        }
        ArrayList<MapState> maps=new ArrayList<>();
        while (MapDAO.getmap(idmap)!=null){
            maps.add(new MapState(idmap));
            idmap++;
        }
        return maps;
    }

    public static void addClient(Client client){
        for (MapState m: listmaps) {
            if(client.getAvatar().getLieux().getId()==m.getCarte().getId()) {
                m.addClient(client);
                client.setMap(m);
                break;
            }
        }
        throw new IllegalArgumentException("Cette map n'existe pas");
    }

    //getters

    public static ArrayList<MapState> getLismaps(){
        return listmaps;
    }

}
