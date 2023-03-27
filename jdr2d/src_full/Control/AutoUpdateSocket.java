package Control;

import Graphic.GameInterface;
import Log.LogLevel;
import Log.Loggy;

import java.io.*;
import java.net.Socket;

public class AutoUpdateSocket {

    private static final String serveradress="127.0.0.1";

    private static final int serverport=5000;

    private static OutputStream out;


    private static ObjectInputStream serverinput;


    private static ObjectOutputStream serveroutput;

    private static InputStream in;

    public static void launch(GameInterface fenetre) throws IOException, ClassNotFoundException, InterruptedException {
        Socket echoSocket = new Socket(serveradress, serverport);
        in=echoSocket.getInputStream();
        out=echoSocket.getOutputStream();
        serveroutput=new ObjectOutputStream(out);
        serverinput=new ObjectInputStream(in);
        new MapUpdaterProtocol(fenetre);
    }

    //getters
    public static void write(Object envoie) throws IOException {
        Loggy.writlog("CLIENT WRITED "+ envoie, LogLevel.NOTICE);
        serveroutput.writeObject(envoie);
        serveroutput.reset();
    }

    public static <T> T read() throws IOException, ClassNotFoundException {
        return (T) serverinput.readObject();
    }


    public static OutputStream getOut() {
        return out;
    }

    public static ObjectInputStream getServerinput() {
        return serverinput;
    }

    public static ObjectOutputStream getServeroutput() {
        return serveroutput;
    }

    public static InputStream getIn() {
        return in;
    }
}
