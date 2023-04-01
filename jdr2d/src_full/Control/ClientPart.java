package Control;

import  Log.*;
import ServerPart.ServerLauncher;
import com.sun.tools.javac.Main;
import jdr2dcore.Quete;

import java.io.*;
import java.net.Socket;


public abstract class ClientPart {

    private static final String hostName= ClientStarter.connexionprop.getProperty("ipadress");
    private static final int port= Integer.parseInt(ClientStarter.connexionprop.getProperty("mainportnumber"));

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
        Loggy.writlog("Check ServerMainChannel", LogLevel.ERROR);
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
        System.out.println(" ???????????????? "+envoie.getClass().getName());
        try {
            serveroutput.writeObject(envoie);
            serveroutput.reset();
        }
        catch (NotSerializableException NSE){
            System.out.println(" WUT "+envoie.getClass().getName());
        }
    }

    public static <T> T read() throws IOException, ClassNotFoundException {
        return (T) serverinput.readObject();
    }

    //setters



    public static void setServerinput(ObjectInputStream serverinput) {
        ClientPart.serverinput = serverinput;
    }




}
