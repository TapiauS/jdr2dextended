package Control;



//import Log.LogLevel;
//import Log.Loggy;

import java.io.*;
import java.net.Socket;


public abstract class ClientPart {

    private static final String hostName="127.0.0.1";
    private static final int port=6000;

    private static OutputStream out;

    private static ObjectInputStream autoupdatechannel;

    private static ObjectInputStream serverinput;

    private static ObjectOutputStream interactionoutputchannel;

    private static ObjectInputStream interactioninputchannel;

    private static ObjectOutputStream serveroutput;

    private static InputStream in;

    //private static ByteArrayInputStream imginput;


    public static void launch() throws IOException, ClassNotFoundException, InterruptedException {
        Socket echoSocket = new Socket(hostName, port);
        in=echoSocket.getInputStream();
        out=echoSocket.getOutputStream();
        //Loggy.init();
        //Loggy.writlog("Check Client",LogLevel.ERROR);
        serveroutput=new ObjectOutputStream(echoSocket.getOutputStream());
        serverinput=new ObjectInputStream(echoSocket.getInputStream());
        autoupdatechannel=new ObjectInputStream(echoSocket.getInputStream());
        interactioninputchannel=new ObjectInputStream(echoSocket.getInputStream());
        interactionoutputchannel=new ObjectOutputStream(echoSocket.getOutputStream());
        ThreadDealer t=new ThreadDealer();
        t.launch();
    }



    //getters


    public static ObjectOutputStream getInteractionoutputchannel() {
        return interactionoutputchannel;
    }

    public static ObjectInputStream getInteractioninputchannel() {
        return interactioninputchannel;
    }

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

    public static ObjectInputStream getAutoupdatechannel() {
        return autoupdatechannel;
    }
    //setters



    public static void setServerinput(ObjectInputStream serverinput) {
        ClientPart.serverinput = serverinput;
    }


}
