package ServerPart;

import Control.ConnexionOutput;
import Control.OutputType;
import Log.LogLevel;
import Log.Loggy;
import ServerPart.Control.Interaction;
import ServerPart.Control.JDRDSocket;
import ServerPart.Control.PersoThread;
import ServerPart.DAO.*;
import jdr2dcore.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unchecked")

public class ClientMainChannel extends Thread implements Serializable, JDRDSocket {

    private final Socket socket;

    private boolean connected;

    private InputStream in;

    private OutputStream out;

    private ObjectInputStream input;

    private ObjectOutputStream output;
    private Personnage avatar;

    private Coffre openedcoffre;

    private ByteArrayOutputStream bytestream;
    private Utilisateur util;
    private GameZone map;

    private boolean interagit;





    //getters et setters

    public boolean isInteragit() {
        return interagit;
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



    //setters


    public void setInteragit(boolean interagit) {
        this.interagit = interagit;
    }

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

    public ClientMainChannel(Socket socket) throws IOException {
        this.socket=socket;
        connected=true;
        avatar=null;
        util=null;
        bytestream=new ByteArrayOutputStream();
        out=socket.getOutputStream();
        in=socket.getInputStream();
        output=new ObjectOutputStream(socket.getOutputStream());
        input=new ObjectInputStream(socket.getInputStream());
        openedcoffre=null;
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
                    try {
                        util = UtilisateurDAO.connectcompte(pseudo, mdp);
                        output.writeObject(true);
                        output.writeObject(util);
                    } catch (SQLException e) {
                        if (e instanceof SQLDataException) {
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
            String nomperso=read();
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
                conn= read();
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
                        conn = read();
                        switch (conn) {
                            case PICKCHAR -> pick();
                            case CREATECHAR -> createchar();
                        }
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
                choice=read();
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
                connect =read();
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
        try {
            GameZone zone=Objects.requireNonNull(MapPool.getGameZone(avatar.getLieux().getId()));
            write(avatar.getLieux());
            write(zone.getPnjs());
            write(zone.getEchanges());
            write(zone.getCoffres());
            write(zone.getSorties());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        OutputType outputType;
            do {
                try {
                    outputType = (OutputType) input.readObject();
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
                    case RESPAWN -> {
                        interagit=false;
                        avatar.setpV(avatar.getpVmax());
                    }
                    case TALK -> {
                            this.interagit=true;
                            int id=read();
                        for (PNJ p: map.getPnjs()) {
                            if(p.getId()==id) {
                                p.setInteract(true);
                                break;
                            }
                        }
                        ArrayList<Quete> quetes=read();
                        for (Quete q: quetes) {
                            avatar.addsQuete(q);
                        }
                        ArrayList<Integer> ids=read();
                        for (int i=0;i<ids.size();i++){
                            for (Quete q:avatar.getQueteSuivie()) {
                                for (int j=0;j<q.getObjectifs().size();j++) {
                                    if (q.getObjectifs().get(j).getId()==ids.get(i)) {
                                        q.getObjectifs().get(j).update();
                                        ObjectifsDAO.setobj(avatar,q.getObjectifs().get(j));
                                    }
                                }
                            }
                        }
                    }
                    case PICK -> exploreCoffre();
                    case INVENTAIRE -> explorInvent();
                    case FIGTH -> {
                        int idadversaire=read();
                        PNJ adversaire=null;
                        for (PNJ perso: map.getPnjs()) {
                            if(perso.getId()==idadversaire)
                                if(!perso.isInteract())
                                {
                                    write(perso.isInteract());
                                    Interaction inter=new Interaction(avatar,perso);
                                    inter.combat();
                                    if(avatar.getpV()<0)
                                        PersoThread.respawn(avatar);
                                    if(perso.getpV()>0)
                                        PersoThread.respawn(perso);
                                    write(avatar.getpV());
                                }
                        }
                    }
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
                } catch (IOException | ClassNotFoundException | SQLException e) {
                    throw new RuntimeException(e);
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

    public void write(Object objet) throws IOException {
        output.writeObject(objet);
        Loggy.writlog("SERVER WRITED"+objet.toString(),LogLevel.NOTICE);
        output.reset();
    }


    public <T> T read() throws IOException, ClassNotFoundException {
        return (T) input.readObject();
    }

    private void exploreCoffre() throws IOException, ClassNotFoundException, SQLException {
        int coffrelvl=0;
        Coffre openedcoffre = null;
        int idopenedcoffre=read();
        int indicepicked;
        OutputType commande;
        ArrayList<Coffre> parentcoffre=new ArrayList<>();
        Loggy.writlog("idopended= "+idopenedcoffre,LogLevel.NOTICE);
        for (Coffre coffre: map.getCoffres()) {
            if(idopenedcoffre==coffre.getId()) {
                openedcoffre = coffre;
                if(!openedcoffre.isOpened()) {
                    write(true);
                    openedcoffre.setOpened(true);
                    Loggy.writlog("Check coffre", LogLevel.NOTICE);
                    break;
                }
                else {
                    write(false);
                    return;
                }
            }
        }
        while (true){
            commande=read();
            switch (commande){
                case PICK -> {
                    indicepicked=read();
                    Loggy.writlog("INDICE PICKED "+indicepicked,LogLevel.NOTICE);
                    Objet pickedobjet=openedcoffre.getContenu().get(indicepicked);
                    boolean iscoffre;
                    iscoffre=pickedobjet instanceof Coffre;
                    write(iscoffre);
                    if (!iscoffre){
                        Loggy.writlog("OBJET NORMAL",LogLevel.NOTICE);
                        avatar.addObjet(pickedobjet);
                        ObjetDAO.pickObjet(avatar,openedcoffre.getContenu().get(indicepicked));
                        write(avatar.getInventaire());
                        write(avatar.getNomPersonnage()+" a ramassé "+pickedobjet.getNomObjet());
                        openedcoffre.remove(indicepicked);
                        write(openedcoffre);
                    }
                    else {
                        Loggy.writlog("OBJET COFFRE",LogLevel.NOTICE);
                        parentcoffre.add(openedcoffre);
                        openedcoffre= (Coffre) pickedobjet;
                        write(openedcoffre);
                        coffrelvl++;
                    }
                }
                case GOBACK -> {
                    openedcoffre=parentcoffre.get(parentcoffre.size()-1);
                    parentcoffre.remove(parentcoffre.size()-1);
                    coffrelvl--;
                }
                case QUIT -> {
                    openedcoffre.setOpened(false);
                    return;
                }
            }
        }
    }

    private void explorInvent() throws IOException, ClassNotFoundException {
        ArrayList<Coffre> parentscoffre=new ArrayList<>();
        Coffre openedcoffre=avatar.getInventaire();
        Objet selectedobjet;
        OutputType instruction=null;
        int idobjet;
        while (true){
            instruction=read();
            switch (instruction){
                case EQUIP -> {
                    idobjet=read();
                    System.out.println(avatar.getInventaire().getContenu().size());
                    selectedobjet=openedcoffre.getContenu().get(idobjet);
                    boolean iscoffre=selectedobjet instanceof Coffre;
                    write(iscoffre);
                    if(!iscoffre){
                        write(selectedobjet);
                        if (selectedobjet instanceof Arme) {
                            try {
                                avatar.removeObjet(selectedobjet);
                                avatar.addArme((Arme) selectedobjet);
                                write(avatar.getNomPersonnage() + " a equipé l'arme:"
                                        + selectedobjet.getNomObjet());
                                write(openedcoffre);
                                write(avatar.getArme());

                            } catch (SQLException ex) {
                                //TODO gere exception
                                throw new RuntimeException(ex);
                            }
                        }
                        if(selectedobjet instanceof Armure){
                            try {
                                avatar.removeObjet(selectedobjet);
                                avatar.addArmure((Armure) selectedobjet);
                                write(avatar.getNomPersonnage() + " a equipé l'arme:"
                                        + selectedobjet.getNomObjet());
                                write(avatar.getInventaire());
                                write(avatar.getArme());
                            }
                            catch (SQLException sq){
                                throw new RuntimeException(sq);
                            }
                        }
                        if (selectedobjet instanceof Potion){
                            avatar.removeObjet(selectedobjet);
                            Time.drinkpotion((Potion) selectedobjet,avatar);
                        }
                        write(openedcoffre);
                    }
                    else {
                        write(selectedobjet);
                        parentscoffre.add((Coffre) selectedobjet);
                        openedcoffre= (Coffre) selectedobjet;
                    }
                }
                case DROP -> {
                    idobjet=read();
                    selectedobjet=openedcoffre.getContenu().get(idobjet);
                    write(openedcoffre.getContenu());
                    try {
                        boolean incoffre = false;
                        avatar.dropObjet(selectedobjet);
                        for (Coffre c : map.getCoffres()) {
                            if (avatar.distance(c) == 0) {
                                c.add(selectedobjet);
                                incoffre = true;
                                break;
                            }
                        }
                        if (!incoffre)
                            map.getCoffres().add((Coffre) ((Coffre) (new Coffre(selectedobjet))
                                    .setLieux(avatar.getLieux()).setX(avatar.getX()).setY(avatar.getY())).setNomObjet("tas"));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                case GOBACK -> {
                    openedcoffre=parentscoffre.get(parentscoffre.size()-1);
                    parentscoffre.remove(parentscoffre.size()-1);
                    write(openedcoffre);
                }
                case QUIT -> {

                    return;
                }
            }
        }

    }

    //getters et setters


    public Coffre getOpenedcoffre() {
        return openedcoffre;
    }

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
