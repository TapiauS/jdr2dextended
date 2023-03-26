package ServerPart.Socketsmanager;

import ServerPart.Control.AutoUpdater;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AutoUpdateChannel extends Thread{

    private final int updateportNumber=5000;

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
