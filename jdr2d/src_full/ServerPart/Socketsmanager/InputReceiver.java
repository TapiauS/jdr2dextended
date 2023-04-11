package ServerPart.Socketsmanager;

import ServerPart.Control.GameZone;
import ServerPart.ServerLauncher;
import Entity.Personnage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class InputReceiver extends Thread{

    private ServerSocket serverSocket;
    private Personnage avatar;
    private Socket clientSocket;
    private final int portNumber= Integer.parseInt(ServerLauncher.connexionprop.getProperty("mainportnumber"));

    public InputReceiver(){
        start();
    }

    @Override
    public void run() {
        super.run();
        try {
            ArrayList<GameZone> maps= MapPool.getLismaps();
            serverSocket = new ServerSocket(portNumber);
            //MapPool.init();
            while (true) {
                clientSocket = serverSocket.accept();
                new ServerMainChannel(clientSocket);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    //getters

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public Personnage getAvatar() {
        return avatar;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public int getPortNumber() {
        return portNumber;
    }

    //setters


    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }


    public void setAvatar(Personnage avatar) {
        this.avatar = avatar;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
