package TestClient;

import ServerPart.MapState;

import java.io.*;
import java.net.Socket;


public class ClientPart {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String hostName ="127.0.0.1";
        int port=5987;
        Socket echoSocket = new Socket(hostName, port);
        PrintWriter out =   new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        ObjectInputStream mapstate=new ObjectInputStream(echoSocket.getInputStream());
        MapState resulte= (MapState) mapstate.readObject();

        System.out.println("premier test réussit ? "+resulte.getPnjs().size());
    }

}
