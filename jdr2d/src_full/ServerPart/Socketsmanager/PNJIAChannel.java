package ServerPart.Socketsmanager;

import ServerPart.Control.AutoUpdater;
import ServerPart.Control.IAProtocolServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PNJIAChannel extends Thread{
    private static final int portnumber=5010;

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
