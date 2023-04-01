package ServerPart;

import ServerPart.Socketsmanager.AutoUpdateChannel;
import ServerPart.Socketsmanager.InputReceiver;
import ServerPart.Socketsmanager.PNJIAChannel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.LogManager;
import static Logging.Jdr2dLogger.LOGGER;
public class ServerLauncher {

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
