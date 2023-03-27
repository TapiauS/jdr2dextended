package ServerPart;

import ServerPart.Socketsmanager.AutoUpdateChannel;
import ServerPart.Socketsmanager.InputReceiver;
import ServerPart.Socketsmanager.PNJIAChannel;

public class ServerLauncher {
    public static void main(String[] args){

        new InputReceiver();
        new AutoUpdateChannel();
        new PNJIAChannel();
    }
}
