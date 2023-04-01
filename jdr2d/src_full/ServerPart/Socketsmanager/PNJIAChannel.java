package ServerPart.Socketsmanager;

import ServerPart.Control.IAProtocolServer;
import ServerPart.ServerLauncher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PNJIAChannel extends Thread{
    private static final int portnumber= Integer.parseInt(ServerLauncher.connexionprop.getProperty("pniaport"));

    private Socket clientSocket;

    private ServerSocket serverSocket;

    public PNJIAChannel(){
        try {
            serverSocket=new ServerSocket(portnumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        start();
    }


    public void run() {
        super.run();
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                new IAProtocolServer(clientSocket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
