package ServerPart;

import Control.InputType;
import DAO.UtilisateurDAO;
import jdr2dcore.Direction;
import jdr2dcore.Personnage;
import jdr2dcore.Utilisateur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class Client extends Thread{

    private final Socket socket;

    private boolean connected;

    private ObjectInputStream input;

    private ObjectOutputStream output;
    private Personnage avatar;

    private Utilisateur util;
    private MapState map;


    public Client(Socket socket) throws IOException {
        this.socket=socket;
        connected=true;
        avatar=null;
        util=null;
        input=new ObjectInputStream(socket.getInputStream());
        output=new ObjectOutputStream(socket.getOutputStream());
        start();

    }

    private void connect() throws IOException, ClassNotFoundException {
        String pseudo;
        String mdp;
        while (util==null) {
            pseudo= (String) input.readObject();
            mdp=(String) input.readObject();
            try {
                util=UtilisateurDAO.connectcompte(pseudo, mdp);
                output.writeObject(true);
            } catch (SQLException e) {
                if (e instanceof SQLDataException) {
                    output.writeObject(false);
                }
            }
        }

    }

    private void create(){

    }

    public void run(){
        //TODO sequence de connection
        boolean connect;
        try {
            connect = input.readBoolean();
            if (connect) {
                connect();
            } else {
                create();
            }
        }


        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        MapPool.addClient(this);
        InputType inputType ;
        do {
            try {
                inputType = (InputType) input.readObject();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        switch (inputType){
            case MOUVNORD -> avatar.depl(Direction.NORD);
            case MOUVWEST -> avatar.depl(Direction.OUEST);
            case MOUVEAST -> avatar.depl(Direction.SUD);
            case MOUVSOUTH -> avatar.depl(Direction.SUD);

            /*
            case TALK -> break;
            case FIGTH -> break;
            case PICK -> break;
            case QUEST -> break;
            case QUIT -> break;
            case INVENTAIRE -> break;
            case EQUIP -> break;
            case USE -> break;
            case DROP -> break;
            */
        }
        }while (inputType!=InputType.QUIT&&connected);

    }

    //getters et setters

    public Socket getSocket() {
        return socket;
    }

    public boolean isConnected() {
        return connected;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public Personnage getAvatar() {
        return avatar;
    }

    public MapState getMap() {
        return map;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public void setAvatar(Personnage avatar) {
        this.avatar = avatar;
    }

    public void setMap(MapState map) {
        this.map = map;
    }
}
