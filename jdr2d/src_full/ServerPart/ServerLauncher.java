package ServerPart;

public class ServerLauncher {
    public static void main(String[] args){
        new InputReceiver();
        new AutoUpdateChannel();
        new PNJIAChannel();
    }
}
