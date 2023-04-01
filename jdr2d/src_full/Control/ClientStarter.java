package Control;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import Control.ClientPart;

import static Logging.Jdr2dLogger.LOGGER;

public class ClientStarter {
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
        try {
            ClientPart.launch();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


