package ServerPart;

import ServerPart.Socketsmanager.AutoUpdateChannel;
import ServerPart.Socketsmanager.InputReceiver;
import ServerPart.Socketsmanager.PNJIAChannel;

import java.io.IOException;
import java.util.logging.LogManager;

public class ServerLauncher {


    public static void main(String[] args){

        try {
            LogManager.getLogManager().readConfiguration(
                    Jdr2dLogger.class.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        new InputReceiver();
        new AutoUpdateChannel();
        new PNJIAChannel();
    }
}
