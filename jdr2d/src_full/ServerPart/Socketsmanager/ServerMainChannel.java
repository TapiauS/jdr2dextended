package ServerPart.Socketsmanager;

import Control.ConnexionOutput;
import Control.OutputType;
import Log.LogLevel;
import Log.Loggy;
import ServerPart.Control.Interaction;
import ServerPart.Control.PersoThread;
import ServerPart.DAO.*;
import ServerPart.Control.GameZone;
import Entity.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import static Logging.Jdr2dLogger.LOGGER;

@SuppressWarnings({"unchecked"})

public class ServerMainChannel extends Thread implements Serializable, JDRDSocket {
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
    public ServerMainChannel(Socket socket) throws IOException {
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

    private void connect() throws IOException, ClassNotFoundException, SQLException, DAOException {
        String pseudo;
        String mdp;
        ConnexionOutput conn=null;
        while (util==null&&conn!= ConnexionOutput.QUIT) {
            conn=read();
            switch (conn) {
                case CONNEXION -> {
                    pseudo = read();
                    mdp = read();
                    try {
                        util = UtilisateurDAO.connectcompte(pseudo, mdp);
                        output.writeObject(true);
                        output.writeObject(util);
                    } catch (DAOException daoe) {
                        if (daoe.getErrortype()==ErrorType.SQLENTRY) {
                            write(false);
                            write(ErrorType.IDENTIFICATIONERROR);
                            System.out.println("erreur");
                            break;
                        }
                        if (daoe.getErrortype()==ErrorType.NOTAVAILABLE){
                            write(false);
                            write(ErrorType.NOTAVAILABLE);
                        }
                        else{
                            LOGGER.severe(daoe.getMessage());
                            System.exit(-1);
                        }
                    }
                }
                case CREATION -> create();
                case QUIT -> connected=false;
            }
        }
        Hashtable<String,Integer> display;
        display = UtilisateurDAO.displaypersonnage(util);
        output.writeObject(display);
    }
    private void create() throws IOException, ClassNotFoundException, SQLException, DAOException {
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
                    teste_pseudo =  read();
                    success = UtilisateurDAO.checkpseudo(teste_pseudo);
                    if (success)
                        pseudo = teste_pseudo;
                    write(success);
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
                    teste_mdp = (String) input.readObject();
                    success = UtilisateurDAO.checkmdp(teste_mdp);
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
                    teste_mail = (String) input.readObject();
                    success = UtilisateurDAO.checkmail(teste_mail);
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
        util=UtilisateurDAO.createcompte(pseudo,mdp,mail);
        output.writeObject(util);
    }
    private void pick() throws IOException, SQLException, ClassNotFoundException, DAOException {
        Hashtable<String,Integer> display=UtilisateurDAO.displaypersonnage(util);
        String nomperso=read();
        avatar= PersonnageDAO.getchar(display.get(nomperso));
        output.writeObject(avatar);
    }

    private void createchar() throws IOException, ClassNotFoundException, SQLException, DAOException {
        String charname="";
        boolean success=false;
        ConnexionOutput conn;
        while (!success){
            conn= read();
            switch (conn){
                case VALIDCHOICE -> {
                    charname= (String) input.readObject();
                    success= PersonnageDAO.checkcharname(charname);
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
        }
    }

    private void pickpicture() throws SQLException, IOException, ClassNotFoundException, DAOException {
        Hashtable<Integer, BufferedImage> caroussel= ImageDAO.loadfullimagebank("portrait");
        int indexportrait=0;
        List<Integer> keystoarray= caroussel.keySet().stream().toList();
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
    }

    public void run(){
        ConnexionOutput connect;
        try {
            connect = read();
            switch (connect) {
                case CONNEXION -> connect();
                case CREATION -> create();
            }
            while (avatar==null){
                connect = read();
                System.out.println("Je passe ici");
                System.out.println(connect);
                switch (connect) {
                    case PICKCHAR -> pick();
                    case CREATECHAR -> createchar();
                    case DELETECHAR -> {
                        String charname = read();
                        System.out.println(charname);
                        PersonnageDAO.delete(charname);
                    }
                }
                }
            System.out.println(avatar.getLieux().getId());
            MapPool.addClient(this);
            sendMap();
            bytestream=new ByteArrayOutputStream();
            ImageIO.write(PersonnageDAO.getcharportrait(avatar.getId()),"png",bytestream);
            byte [] imgbyte= bytestream.toByteArray();
            output.writeObject(imgbyte.length);
            out.write(imgbyte);
            OutputType outputType;
            do {
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
                        interagit = false;
                        avatar.setpV(avatar.getpVmax());
                    }
                    case TALK -> {
                        this.interagit = true;
                        int id = read();
                        for (PNJ p : map.getPnjs()) {
                            if (p.getId() == id) {
                                p.setInteract(true);
                                break;
                            }
                        }
                        ArrayList<Quete> quetes= read();
                        Quete quete;
                        if (!quetes.isEmpty()) {
                            quete = quetes.get(0);
                            avatar.addsQuete(quete);
                            QueteDAO.update(quete, avatar);
                        }
                        ArrayList<Integer> ids = read();
                        for (int i = 0; i < ids.size(); i++) {
                            for (Quete q : avatar.getQueteSuivie()) {
                                for (int j = 0; j < q.getObjectifs().size(); j++) {
                                    if (q.getObjectifs().get(j).getId() == ids.get(i)) {
                                        q.getObjectifs().get(j).update();
                                        ObjectifsDAO.setobj(avatar, q.getObjectifs().get(j));
                                    }
                                }
                            }
                        }
                    }
                    case PICK -> exploreCoffre();
                    case INVENTAIRE -> explorInvent();
                    case FIGTH -> {
                        int idadversaire = read();
                        PNJ adversaire = null;
                        for (PNJ perso : map.getPnjs()) {
                            if (perso.getId() == idadversaire && avatar.distance(perso) < 1) {
                                write(perso.isInteract());
                                if (!perso.isInteract()) {
                                    Interaction inter = new Interaction(avatar, perso, this);
                                    inter.combatPNJ();
                                    if (avatar.getpV() < 0)
                                        PersoThread.respawn(avatar);
                                    if (perso.getpV() > 0)
                                        PersoThread.respawn(perso);
                                }
                                break;
                            }
                        }
                    }
                    case DOOR -> {
                        int idporte = read();
                        ArrayList<Porte> sorties = map.getSorties();
                        Porte door = null;
                        for (Porte p : sorties) {
                            if (p.getId() == idporte) {
                                door = p;
                                break;
                            }
                        }
                        door.traverse(avatar);
                        sendMap();
                        map.removeClient(this);
                        map=MapPool.getGameZone(avatar.getLieux().getId());
                        assert map != null;
                        map.addClient(this);
                        System.out.println("mapname= "+avatar.getLieux().getNomLieu());
                    }
                    case FIGTHHUMAN -> {
                        Personnage opponent=read();
                        ServerMainChannel oppclient=map.getClient(opponent);
                        if(!oppclient.interagit){
                            oppclient.write(OutputType.FIGTHHUMAN);
                            oppclient.write(avatar);
                            boolean accept=oppclient.read();
                            write(accept);
                            if(accept){
                                Interaction inter=new Interaction(avatar,opponent,this,oppclient);
                                inter.combat();
                            }
                        }
                    }
                }
            } while (connected);
        }
        catch (ClassNotFoundException e) {
            LOGGER.severe(e.getMessage());
        }
        catch (IOException cne){
            LOGGER.warning("FERMETURE BRUTALE CAUSEE PAR :"+cne.getMessage());
        }
        catch (SQLException sqe){
            if(!(22000<sqe.getErrorCode()&&sqe.getErrorCode()<23000)) {
                LOGGER.warning("PROBLEME AVEC LA BASE DE DONNEE :"+sqe.getMessage());
            }
            else {
                LOGGER.severe(sqe.getMessage());
                System.exit(-1);
            }
        }
        catch (DAOException daoe){
            LOGGER.severe("ERREUR TRES IMPREVISIBLE "+daoe.getMessage());
        }
        catch (Exception e){
            LOGGER.severe("ERREUR TRES IMPREVISIBLE "+e.getLocalizedMessage());
        }
        finally {
            try {
                if(util!=null)
                    UtilisateurDAO.update(util.getId());
                if (avatar!=null)
                    PersonnageDAO.updatedatabase(avatar);
                if(map!=null)
                    map.removeClient(this);
                input.close();
                output.close();
                socket.close();
            } catch (IOException e) {
                LOGGER.info("FERMETURE BRUTALE "+e.getMessage());
            } catch (DAOException e) {
                LOGGER.info(e.getMessage());
            }
        }
    }

    public void write(Object objet) throws IOException {
        output.writeObject(objet);
        output.reset();
    }

    private void sendMap() throws IOException {
        map=Objects.requireNonNull(MapPool.getGameZone(avatar.getLieux().getId()));
        write(avatar.getLieux());
        write(map.getPnjs());
        write(map.getEchanges());
        write(map.getCoffres());
        write(map.getSorties());

    }

    public <T> T read() throws IOException, ClassNotFoundException {
        return (T) input.readObject();
    }

    private void exploreCoffre() throws IOException, ClassNotFoundException, SQLException, DAOException {
        int coffrelvl=0;
        Coffre openedcoffre = null;
        int idopenedcoffre=read();
        int indicepicked;
        OutputType commande;
        ArrayList<Coffre> parentcoffre=new ArrayList<>();
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
                    Objet pickedobjet=openedcoffre.getContenu().get(indicepicked);
                    boolean iscoffre;
                    iscoffre=pickedobjet instanceof Coffre;
                    write(iscoffre);
                    if (!iscoffre){
                        boolean cantake=(avatar.getInventaire().getPoid()+pickedobjet.getPoid())<=avatar.getMaxpoid();
                        write(cantake);
                        if (cantake) {
                            avatar.addObjet(pickedobjet);
                            ObjetDAO.pickObjet(avatar, openedcoffre.getContenu().get(indicepicked));
                            write(avatar.getInventaire());
                            write(avatar.getNomPersonnage() + " a ramassé " + pickedobjet.getNomObjet());
                            openedcoffre.remove(indicepicked);
                            write(openedcoffre);
                        }
                    }
                    else {
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

    private void explorInvent() throws IOException, ClassNotFoundException, SQLException, DAOException {
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
                                avatar.removeObjet(selectedobjet);
                                avatar.addArme((Arme) selectedobjet);
                                write(avatar.getNomPersonnage() + " a equipé l'arme:"
                                        + selectedobjet.getNomObjet());
                                write(openedcoffre);
                                write(avatar.getArme());
                            }
                            if (selectedobjet instanceof Armure) {
                                avatar.removeObjet(selectedobjet);
                                avatar.addArmure((Armure) selectedobjet);
                                write(avatar.getNomPersonnage() + " a equipé l'arme:"
                                        + selectedobjet.getNomObjet());
                                write(avatar.getInventaire());
                                write(avatar.getArme());
                            }
                            if (selectedobjet instanceof Potion) {
                                avatar.removeObjet(selectedobjet);
                                Time.drinkpotion((Potion) selectedobjet, avatar);
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
