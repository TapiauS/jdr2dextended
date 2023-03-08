package ServerPart;

import DAO.MapDAO;

import java.sql.SQLException;
import java.util.ArrayList;



public abstract class MapPool {

    private static final ArrayList<GameZone> listmaps;

    static {
        try {
            listmaps = getmaps();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //methodes


    private static ArrayList<GameZone> getmaps() throws SQLException {
        int idmap=1;
        while (MapDAO.getmap(idmap)==null) {
            idmap++;
        }
        ArrayList<GameZone> maps=new ArrayList<>();
        while (MapDAO.getmap(idmap)!=null){
            maps.add(new GameZone(idmap));
            idmap++;
        }
        return maps;
    }

    public static void addClient(Client client){
        for (GameZone m: listmaps) {
            if(client.getAvatar().getLieux().getId()==m.getCarte().getId()) {
                m.addClient(client);
                client.setMap(m);
                return;
            }
        }
        throw new IllegalArgumentException("Cette map n'existe pas");
    }



    //getters

    public static ArrayList<GameZone> getLismaps(){
        return listmaps;
    }

}
