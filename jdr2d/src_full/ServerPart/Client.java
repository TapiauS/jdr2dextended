package ServerPart;

import Control.ConnexionOutput;
import Control.OutputType;
import DAO.DAOObject;
import DAO.ImageDAO;
import DAO.PersonnageDAO;
import DAO.UtilisateurDAO;
import jdr2dcore.Direction;
import jdr2dcore.Personnage;
import jdr2dcore.Utilisateur;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class Client extends Thread implements Serializable{

    private final Socket socket;

    private boolean connected;

    private InputStream in;

    private OutputStream out;

    private ObjectInputStream input;

    private ObjectOutputStream autooutputstream;
    private ObjectOutputStream output;
    private Personnage avatar;

    private ByteArrayOutputStream bytestream;
    private Utilisateur util;
    private GameZone map;

    private ObjectOutputStream interactionoutput;
    private ObjectInputStream interactioninput;
    //getters et setters


    public ObjectOutputStream getInteractionoutput() {
        return interactionoutput;
    }

    public ObjectInputStream getInteractioninput() {
        return interactioninput;
    }

    public InputStream getIn() {
        return in;
    }

    public OutputStream getOut() {
        return out;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public ByteArrayOutputStream getBytestream() {
        return bytestream;
    }

    public Utilisateur getUtil() {
        return util;
    }

    public ObjectOutputStream getAutooutputstream() {
        return autooutputstream;
    }



    //setters


    public void setIn(InputStream in) {
        this.in = in;
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    public void setBytestream(ByteArrayOutputStream bytestream) {
        this.bytestream = bytestream;
    }

    public void setUtil(Utilisateur util) {
        this.util = util;
    }

    public Client(Socket socket) throws IOException {
        this.socket=socket;
        connected=true;
        avatar=null;
        util=null;
        bytestream=new ByteArrayOutputStream();
        out=socket.getOutputStream();
        in=socket.getInputStream();
        output=new ObjectOutputStream(socket.getOutputStream());
        input=new ObjectInputStream(socket.getInputStream());
        autooutputstream=new ObjectOutputStream(socket.getOutputStream());
        interactionoutput=new ObjectOutputStream(out);
        interactioninput=new ObjectInputStream(in);
        System.out.println("je passe bien ?");
        start();

    }

    private void connect() throws IOException, ClassNotFoundException {
        String pseudo;
        String mdp;
        ConnexionOutput conn=null;
        while (util==null&&conn!= ConnexionOutput.QUIT) {
            conn=(ConnexionOutput) input.readObject();
            switch (conn) {
                case CONNEXION -> {
                    pseudo = (String) input.readObject();
                    mdp = (String) input.readObject();
                    System.out.println("?????");
                    try {
                        util = UtilisateurDAO.connectcompte(pseudo, mdp);
                        output.writeObject(true);
                        output.writeObject(util);
                        System.out.println("je passe ici");
                    } catch (SQLException e) {
                        if (e instanceof SQLDataException) {
                            System.out.println("je passe la");
                            output.writeObject(false);
                        }
                    }
                }
                case CREATION -> create();
                case QUIT -> connected=false;
            }
        }
        Hashtable<String,Integer> display= null;
        try {
            display = UtilisateurDAO.displaypersonnage(util);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        output.writeObject(display);
    }

    private void create() throws IOException, ClassNotFoundException {
        String pseudo="";
        String mdp="";
        ConnexionOutput conn = null;
        String mail="";
        boolean success = false;
        while (!success) {
            conn = (ConnexionOutput) input.readObject();
            switch (conn) {
                case VALIDCHOICE -> {
                    String teste_pseudo;
                    try {
                        teste_pseudo = (String) input.readObject();
                        success = UtilisateurDAO.checkpseudo(teste_pseudo);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (success)
                        pseudo = teste_pseudo;
                    output.writeObject(success);
                }
                case CONNEXION -> {
                    connect();
                    return;
                }
                case QUIT -> {
                    connected = false;
                    return;
                }
            }
        }
        success = false;
        while (!success) {
            conn = (ConnexionOutput) input.readObject();
            switch (conn) {
                case VALIDCHOICE -> {
                    String teste_mdp;
                    try {
                        teste_mdp = (String) input.readObject();
                        success = UtilisateurDAO.checkmdp(teste_mdp);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (success)
                        mdp = teste_mdp;
                    output.writeObject(success);
                }
                case CONNEXION -> {
                    connect();
                    return;
                }
                case QUIT -> {
                    connected = false;
                    return;
                }
            }
        }
        success = false;
        while (!success) {
            conn = (ConnexionOutput) input.readObject();
            switch (conn) {
                case VALIDCHOICE -> {
                    String teste_mail;
                    try {
                        teste_mail = (String) input.readObject();
                        success = UtilisateurDAO.checkmail(teste_mail);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (success)
                        mail = teste_mail;
                    output.writeObject(success);
                }
                case CONNEXION -> {
                    connect();
                    return;
                }
                case QUIT -> {
                    connected = false;
                    return;
                }
            }
        }
        try {
            UtilisateurDAO.createcompte(pseudo,mdp,mail);
            util=UtilisateurDAO.connectcompte(pseudo,mdp);
            output.writeObject(util);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void pick(){
        try {
            Hashtable<String,Integer> display=UtilisateurDAO.displaypersonnage(util);
            String nomperso= (String) input.readObject();
            avatar= PersonnageDAO.getchar(display.get(nomperso));
            output.writeObject(avatar);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void createchar(){
        String charname="";
        boolean success=false;
        ConnexionOutput conn;
        while (!success){
            try {
                conn= (ConnexionOutput) input.readObject();
                switch (conn){
                    case VALIDCHOICE -> {
                        charname= (String) input.readObject();
                        success=PersonnageDAO.checkcharname(charname);
                        output.writeObject(success);
                        if(success) {
                            int id=PersonnageDAO.createchar(charname,util);
                            avatar=PersonnageDAO.getchar(id);
                            output.writeObject(avatar);
                            pickpicture();
                        }
                    }
                    case PICKCHAR -> {
                        pick();
                        return;
                    }
                    case QUIT -> {
                        connected=false;
                        return;
                    }
                }
            } catch (IOException | ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void pickpicture(){
        try {
            Hashtable<Integer, BufferedImage> caroussel= ImageDAO.loadfullimagebank("portrait");
            int indexportrait=0;
            List<Integer> keystoarray= caroussel.keySet().stream().toList();
            System.out.println("Oscour "+keystoarray);
            boolean valid=false;
            ConnexionOutput choice;
            while (!valid){
                bytestream=new ByteArrayOutputStream();
                ImageIO.write(caroussel.get(keystoarray.get(indexportrait)),"png",bytestream);
                byte [] imgbyte= bytestream.toByteArray();
                output.writeObject(imgbyte.length);
                out.write(imgbyte);
                choice=(ConnexionOutput) input.readObject();
                switch (choice){
                    case NEXTPICTURE -> {
                        if(indexportrait+1<keystoarray.size())
                            indexportrait++;
                        else
                            indexportrait=0;
                    }
                    case VALIDPICTURE -> {
                        DAOObject.queryUDC("UPDATE personnage SET id_portrait=? WHERE id_personnage=?;",new ArrayList<>(List.of(keystoarray.get(indexportrait),avatar.getId())));
                        valid=true;
                    }
                    case QUIT -> {
                        connected=false;
                        return;
                    }
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(){
            ConnexionOutput connect;
            try {
                connect = (ConnexionOutput) input.readObject();
                switch (connect) {
                    case CONNEXION -> connect();
                    case CREATION -> create();
                }
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            try {
                connect = (ConnexionOutput) input.readObject();
                switch (connect) {
                    case PICKCHAR -> pick();
                    case CREATECHAR -> createchar();
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            System.out.println(avatar.getLieux().getId());
            MapPool.addClient(this);
            new AutoUpdater(this);
            OutputType outputType;
            do {
                try {
                    outputType = (OutputType) input.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                switch (outputType) {
                    case MOUVNORD -> {
                        avatar.depl(Direction.NORD);
                    }
                    case MOUVWEST -> {
                        avatar.depl(Direction.OUEST);
                    }
                    case MOUVEAST -> {
                        avatar.depl(Direction.EST);
                    }
                    case MOUVSOUTH -> {
                        avatar.depl(Direction.SUD);
                    }
                    case QUIT -> connected = false;
            /*
            case TALK -> break;
            case FIGTH -> break;
            case PICK -> break;
            case QUEST -> break;

            case INVENTAIRE -> break;
            case EQUIP -> break;
            case USE -> break;
            case DROP -> break;
            */
                }
            } while (connected);
            try {
                input.close();
                map.removeClient(this);
                output.close();
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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

    public GameZone getMap() {
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

    public void setMap(GameZone map) {
        this.map = map;
    }
}
