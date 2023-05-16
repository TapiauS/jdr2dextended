package ServerPart;

import ServerPart.Control.GameZone;
import ServerPart.DAO.*;
import ServerPart.Socketsmanager.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import static Logging.Jdr2dLogger.LOGGER;
public class ServerLauncher {

    static {
        Runtime.getRuntime().addShutdownHook(
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        for (GameZone game: MapPool.getLismaps()) {
                            for (ServerMainChannel client :game.getClients()) {
                                try {
                                    PersonnageDAO.updatedatabase(client.getAvatar());
                                    UtilisateurDAO.update(client.getUtil().getId());
                                } catch (DAOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }

                    }


        );

    }
    public static final Properties connexionprop=new Properties();
    static {
        try {
            FileInputStream fis = new FileInputStream("connexions.properties");
            connexionprop.load(fis);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        System.setProperty("java.util.logging.config.file", "logging.properties");
        new InputReceiver();
        new AutoUpdateChannel();
        new PNJIAChannel();
    }
}
