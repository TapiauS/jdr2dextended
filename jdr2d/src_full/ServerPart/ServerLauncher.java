package ServerPart;

import ServerPart.Socketsmanager.AutoUpdateChannel;
import ServerPart.Socketsmanager.InputReceiver;
import ServerPart.Socketsmanager.PNJIAChannel;

import java.io.IOException;
import java.util.logging.LogManager;

public class ServerLauncher {


    public static void main(String[] args) {
        System.setProperty("java.util.logging.config.file", "logging.properties");
        new InputReceiver();
        new AutoUpdateChannel();
        new PNJIAChannel();
    }
}
