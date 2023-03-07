package ServerPart;

import jdr2dcore.Personnage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class InputReceiver {

    private ServerSocket serverSocket;


    private Personnage avatar;
    private Socket clientSocket;
    private final int portNumber=6000;


    public InputReceiver(){
        try {
        serverSocket = new ServerSocket(portNumber);
        while (true) {
                clientSocket = serverSocket.accept();
                new Client(clientSocket);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    /*public static void main(String[] args){
        InputReceiver server=new InputReceiver();
    }*/




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
