import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class InputReceiver {
    private String hostName;

    private ServerSocket serverSocket;

    private Socket clientSocket;
    private int portNumber;


    public InputReceiver(String hostName,int portNumber){
        try {
            serverSocket = new ServerSocket( portNumber);
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}
