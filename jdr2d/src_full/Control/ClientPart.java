package Control;

import Log.LogLevel;
import Log.Loggy;

import java.io.*;
import java.net.Socket;


public abstract class ClientPart {

    private static final String hostName="127.0.0.1";
    private static final int port=5987;

    private static PrintWriter out;


    private static ObjectInputStream serverinput;

    private static ObjectOutputStream serveroutput;

    private static BufferedReader in;
    public static void launch() throws IOException, ClassNotFoundException, InterruptedException {
        Socket echoSocket = new Socket(hostName, port);
        //Loggy.writlog("Check Client", LogLevel.DEBUG);
        serveroutput=new ObjectOutputStream(echoSocket.getOutputStream());
        serverinput=new ObjectInputStream(echoSocket.getInputStream());
        ThreadDealer t=new ThreadDealer();
        t.launch();
    }


    //getters


    public static ObjectOutputStream getServeroutput() {
        return  serveroutput;
    }

    public static PrintWriter getOut() {
        return out;
    }

    public static ObjectInputStream getServerinput() {
        return serverinput;
    }

    public static BufferedReader getIn() {
        return in;
    }

    //setters


    public static void setOut(PrintWriter out) {
        ClientPart.out = out;
    }

    public static void setServerinput(ObjectInputStream serverinput) {
        ClientPart.serverinput = serverinput;
    }

    public static void setIn(BufferedReader in) {
        ClientPart.in = in;
    }
}
