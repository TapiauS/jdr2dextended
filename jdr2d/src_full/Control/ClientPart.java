package Control;



import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.net.Socket;


public abstract class ClientPart {

    private static final String hostName="127.0.0.1";
    private static final int port=6000;

    private static OutputStream out;


    private static ObjectInputStream serverinput;

    private static ObjectOutputStream serveroutput;

    private static InputStream in;

    private static ImageInputStream imginput;


    public static void launch() throws IOException, ClassNotFoundException, InterruptedException {
        Socket echoSocket = new Socket(hostName, port);
        in=echoSocket.getInputStream();
        out=echoSocket.getOutputStream();
        //Loggy.writlog("Check Client", LogLevel.DEBUG);
        serveroutput=new ObjectOutputStream(echoSocket.getOutputStream());
        serverinput=new ObjectInputStream(echoSocket.getInputStream());
        imginput= ImageIO.createImageInputStream(ClientPart.getIn());
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

    public static ImageInputStream getImginput() {
        return imginput;
    }

    //setters



    public static void setServerinput(ObjectInputStream serverinput) {
        ClientPart.serverinput = serverinput;
    }


}
