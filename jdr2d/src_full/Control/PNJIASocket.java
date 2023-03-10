package Control;

import Graphic.GameInterface;

import java.io.*;
import java.net.Socket;

public class PNJIASocket{

    private static final String adressIP="127.0.0.1";

    private static final int port=5010;

    private static Socket socket;

    private static ObjectOutputStream output;

    private static ObjectInputStream input;

    private static InputStream in;
    private static OutputStream out;

    public static void launch(GameInterface fenetre){
        try {
            socket=new Socket(adressIP,port);
            in=socket.getInputStream();
            out=socket.getOutputStream();
            output=new ObjectOutputStream(out);
            input=new ObjectInputStream(in);
            output.writeObject(fenetre.getPlayer().getId());
            output.writeObject(fenetre.getCarte().getId());
            System.out.println("je m'active");
            new PNJiaProtocol(fenetre);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(Object objet) throws IOException {
        output.writeObject(objet);
        output.reset();
    }

    public static <T> T read() throws IOException, ClassNotFoundException {
        return (T) input.readObject();
    }

    //getters


    public static Socket getSocket() {
        return socket;
    }

    public static ObjectOutputStream getOutput() {
        return output;
    }

    public static ObjectInputStream getInput() {
        return input;
    }

    public static InputStream getIn() {
        return in;
    }

    public static OutputStream getOut() {
        return out;
    }
}
