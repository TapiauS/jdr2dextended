package Control;

import  Log.*;

import java.io.*;
import java.net.Socket;


public abstract class ClientPart {

    private static final String hostName="127.0.0.1";
    private static final int port=6000;

    private static OutputStream out;


    private static ObjectInputStream serverinput;


    private static ObjectOutputStream serveroutput;

    private static InputStream in;

    //private static ByteArrayInputStream imginput;


    public static void launch() throws IOException, ClassNotFoundException, InterruptedException {
        Socket echoSocket = new Socket(hostName, port);
        in=echoSocket.getInputStream();
        out=echoSocket.getOutputStream();
        Loggy.init();
        Loggy.writlog("Check ClientMainChannel", LogLevel.ERROR);
        serveroutput=new ObjectOutputStream(out);
        serverinput=new ObjectInputStream(in);
        ThreadDealer t=new ThreadDealer();
        t.launch();
    }



    //getters


    public static ObjectOutputStream getServeroutput() {
        return  serveroutput;
    }

    public static OutputStream getOut() {
        return out;
    }

    public static ObjectInputStream getServerinput() {
        return serverinput;
    }

    public static InputStream getIn() {
        return in;
    }

    /*public static ImageInputStream getImginput() {
        return imginput;
    }
*/
    public static void write(Object envoie) throws IOException {
        Loggy.writlog("CLIENT WRITED "+ envoie,LogLevel.NOTICE);
        serveroutput.writeObject(envoie);
        serveroutput.reset();
    }

    public static <T> T read() throws IOException, ClassNotFoundException {
        return (T) serverinput.readObject();
    }

    //setters



    public static void setServerinput(ObjectInputStream serverinput) {
        ClientPart.serverinput = serverinput;
    }




}
