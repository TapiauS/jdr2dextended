package ServerPart.Socketsmanager;

import ServerPart.ServerLauncher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AutoUpdateChannel extends Thread{

    private final int updateportNumber= Integer.parseInt(ServerLauncher.connexionprop.getProperty("autoUpdatePort"));

    private Socket clientSocket;

    private ServerSocket serverSocket;

    public AutoUpdateChannel(){
        try {
            serverSocket=new ServerSocket(updateportNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        start();
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("je passe ici ");
                new AutoUpdater(clientSocket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
