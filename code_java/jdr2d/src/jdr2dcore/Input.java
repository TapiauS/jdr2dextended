package jdr2dcore;

import DAO.*;

import java.sql.*;
import java.util.*;

public abstract class Input {
    protected static Map carte;
    protected static ArrayList<Coffre> coffres;
    protected static ArrayList<PNJ> pnjs;
    protected static ArrayList<Echange> echanges;
    protected static ArrayList<Porte> sorties;
    protected static Personnage player;
    protected static Scanner scanner;
    protected static Utilisateur util;
    public static void game() throws SQLException {
        String input;
        scanner=new Scanner(System.in);
        System.out.println("Bienvenu dans Afpanums, la démo de la révolution du jeu vidéo imaginé à l'Afpa de pompey ");
        System.out.println("Avez vous déja un compte ? O/N ?");
        try{
            launch();
        }
        catch (InputMismatchException e){
            System.out.println("Ecrire O ou N ");
            launch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        mapload();


    }

    //Le but de connectaccount et start account est de récupérer le personnage du joueur, c'est pourquoi dés que celui ci est définit on force la sortie de la fonction
    private static void connectaccount() throws SQLException {
        String pseudo;
        String mdp;
        boolean sucess = false;
        while (!sucess) {
            try {
                System.out.println("Taper votre pseudo");
                pseudo = scanner.next();
                System.out.println("Taper votre mot de passe");
                mdp = scanner.next();
                util = UtilisateurDAO.connectcompte(pseudo, mdp);
                sucess = true;
            } catch (Exception e) {
                String retry;
                while (!sucess) {
                    try {
                        System.out.println("pseudo ou mdp incorect,reessayer ? O/N");
                        retry = scanner.next().toUpperCase();
                        switch (retry) {
                            case "O":
                                sucess = true;
                                break;
                            case "N":
                                startaccount();
                                return;
                            default:
                                break;
                        }
                    } catch (Exception e0) {
                        sucess = false;
                    }
                }
                sucess = false;
            }
        }
        Hashtable<String,Integer> persos=UtilisateurDAO.displaypersonnage(util);
        if(persos.isEmpty()){
            sucess=false;
            while (!sucess) {
                try {
                    System.out.println("Ce compte n'a aucun personnage, choisissez un nom pour votre premier perso");
                    String nomperso = scanner.next();
                    player=PersonnageDAO.getchar(PersonnageDAO.createchar(nomperso,util));
                    return;
                }
                catch (Exception e){
                    System.out.println("Nom de personnage déja utilisé, entrer un autre nom");
                }
            }
        }
        int i=0;
        Hashtable<Integer,String> posid=new Hashtable<>();
        for (String s: persos.keySet()) {
            posid.put(i,s);
            i++;
        }
        sucess=false;
        while (!sucess) {
            try {
                System.out.println("Vos personnages disponibles sont:");
                for (int j : posid.keySet()) {
                    System.out.println(j + ": " + posid.get(j));
                }
                System.out.println("Choisissez le numéro du personnage que vous voulez jouer");
                int num=scanner.nextInt();
                player=PersonnageDAO.getchar(persos.get(posid.get(num)));
                sucess=true;
            }
            catch (InputMismatchException e){
                System.out.println("Taper le numero du personnage que vous souhaitez jouer");
            }
            catch (Exception e){
                System.out.println("Une erreur inconnu c'est produite, veuillez recommencer");
            }
        }
    }

    private static void startaccount() throws SQLException {
        String pseudo;
        String mdp;
        String mail;
        String nom_perso;

        System.out.println("Choisir un pseudo");
        pseudo= scanner.next();
        while(!UtilisateurDAO.checkpseudo(pseudo)){
            System.out.println("Pseudo non disponible,choisir un autre pseudo");
            pseudo=scanner.next();
        }
        System.out.println("Choisir un mot de passe");
        mdp=scanner.next();
        while (!UtilisateurDAO.checkmdp(mdp)){
            System.out.println("Mot de passe non disponible,choisir un autre pseudo");
            mdp=scanner.next();
        }
        System.out.println("Rentrer une adresse mail");
        mail=scanner.next();
        UtilisateurDAO.createcompte(pseudo,mdp,mail);
        util=UtilisateurDAO.connectcompte(pseudo,mdp);
        boolean success=false;
        while (!success) {
            try {
                System.out.println("Choisissez un nom pour votre premier personnage");
                nom_perso = scanner.next();
                player = PersonnageDAO.getchar(PersonnageDAO.createchar(nom_perso, util));
                success=true;
            }
            catch (InputMismatchException e){
                System.out.println("Nom de personnage non disponible");
            }
        }
    }


    private static void launch() throws SQLException {
        String input;
        boolean error=false;
        input = scanner.next().toUpperCase();
                switch (input) {
                    case "N":
                        error = true;
                        startaccount();
                        break;
                    case "O":
                        error = true;
                        connectaccount();
                        break;
                    default:
                        throw new InputMismatchException();
                }
            }
        // a ce stade seulement player est définit,on veut maintenant définir tout les élements interactable sur une carte
        private static void mapload() throws SQLException{
            carte=player.getLieux();
            pnjs=new ArrayList<>();
            echanges=new ArrayList<>();
            coffres= MapDAO.getcoffres(carte);
            sorties=PorteDAO.getPorte(carte);
            for (Personnage p:PersonnageDAO.getPersonnages(carte,util)) {
                if(p instanceof PNJ){
                    pnjs.add((PNJ) p);
                    echanges.add(EchangeDAO.getEchangetree((PNJ) p));
                }
            }
        }

        //on a maintenant définit tout ce dont on a besoin pour jouer, il est temps de lancer la boucle principal du jeu


    private static void deplacement() throws SQLException {
        Scanner scanner=new Scanner(System.in);
        char input='f';
        try {
            while (input != 'J') {
                for (Porte p : sorties) {
                    if (p.distance(player) < 1) {
                        System.out.println("Il y a une porte à proximité voulez vous la traverser ? y/n");
                        String inputs = scanner.next().toUpperCase();
                        if (inputs.equals("Y")) {
                            p.traverse(player);
                            mapload();
                            System.out.println("Vous venez d'arriver à " + player.getLieux().getNomLieu());
                            return;
                        }
                    }
                }
                System.out.println("Choisir une direction, taper J pour sortir du deplacement ");
                input = scanner.next().toUpperCase().charAt(0);
                if (input != 'J') {
                    player.depl(input);
                }
                for (int i = 0; i < player.getLieux().getDimensions()[0]; i++) {
                    for (int j = 0; j < player.getLieux().getDimensions()[1]; j++) {
                        if (j != player.getX() || i != player.getY()) {
                            System.out.print(player.getLieux().getCarte()[i][j]);
                        } else {
                            System.out.print('J');
                        }
                    }
                    System.out.print('\n');
                }
            }
        }
        catch (Exception e){
            System.out.println("Direction invalide!!!, choix possibles : E,N,S,O pour se déplacer , J pour sortir");
            deplacement();
        }
    }








    private static void pick(){
        Scanner scanner=new Scanner(System.in);
        int compteur=0;
        try {
        for (Coffre c : coffres) {
            if (c.distance(player) <= 1) {
                compteur=compteur+1;
                int inputs = 0;
                int index = 0;
                while (inputs != -1) {
                    System.out.println("Ce coffre contient :");
                    for (Objet o : c.getContenu()) {
                        System.out.println(index + ":" + o.getNomObjet());
                        index = index + 1;
                    }
                        System.out.println("Tapez le numero de l'objet que vous voulez prendre ou -1 si vous voulez quitter");
                        inputs = scanner.nextInt();
                        if (inputs >= 0 ) {
                            Objet o = c.getContenu().get(inputs);
                            c.remove(inputs);
                            player.addObjet(o);
                            ObjetDAO.pickObjet(player,o);
                                }
                    index = 0;
                                }
                    }
                }
            if (compteur==0){System.out.println("Aucun coffre a portée");}
            }
        catch (Exception e){
            System.out.println("Entrée invalide");
            pick();
        }
    }





    private static void drink(){
        int inputs3 = 0;
        Scanner scanner=new Scanner(System.in);
        try{
        while (inputs3 != -1) {
            System.out.println("Votre inventaire contient les potions:");
            LinkedHashMap<Integer, String> listpot=player.getInventaire().findpotion();
            for(int i=0;i<listpot.size();i++) {
                Potion p = (Potion) player.getInventaire().find(listpot.get(i));
                System.out.println(i + " : " + p.getNomObjet() + " deg=" + p.getEffets()[0] + " redudegat=" + p.getEffets()[1] + " Pv=  " + p.getEffets()[2] + " Pvmax= " + p.getEffets()[3]);
            }

            System.out.println("Tapez le numero de la potion que vous voulez boire ou -1 si vous voulez quitter");
                inputs3 = scanner.nextInt();
                if (inputs3 >= 0) {
                    Potion p = (Potion) player.getInventaire().findremove(listpot.get(inputs3));
                    Time.drinkpotion(p, player);
                }

            }
        }
        catch (Exception e) {
            System.out.println("Entrée invalide");
            drink();
        }
    }

    private static void talk() {
        int compteur = 0;
        try {
            for (PNJ p : pnjs) {
                if (p.distance(player) <= 1) {
                    compteur = compteur + 1;
                    for (Echange e : echanges) {
                        if (e.getParleur() == p && e.getQuestion() == null) {
                            boolean quisq=false;
                            if(!e.isObjectifquete()){
                                quisq=true;
                            }
                            for (Quete q: player.getQueteSuivie()) {
                                for (Objectifs o: q.getObjectifs()) {
                                    if(o instanceof ObjectifT){
                                        if(e.isObjectifquete()&&e.getObjectifT()==o){
                                            quisq=true;
                                        }
                                    }
                                }
                            }
                            if(quisq) {
                                Interaction parlote = new Interaction(player, p, e, false);
                                parlote.dialogue();
                            }
                        }
                    }
                }
            }
            if (compteur == 0) {
                System.out.println("Personne n'est assez proche pour parler");
            }
        } catch (Exception ex) {
            System.out.println("Entrée invalide");
            talk();
        }
    }

    private static void weapon() {
        Scanner scanner = new Scanner(System.in);
        int inputs = 0;
        try {
            while (inputs != -1) {
                System.out.println("Votre inventaire contient les armes:");
                LinkedHashMap<Integer, String> listarme=player.getInventaire().findweapon();
                for(int i=0;i<listarme.size();i++) {
                    Arme a = (Arme) player.getInventaire().find(listarme.get(i));
                    System.out.println(i + " : " + a.getNomObjet() + " deg=" + a.getDeg() + " redudegat=" + a.getRedudeg() + " jdr2dcore.Arme à  " + a.getNbrmain() + " mains ");
                }
                System.out.println("Tapez le numero de l'objet que vous voulez equiper ou -1 si vous voulez quitter");
                inputs = scanner.nextInt();
                if (inputs >= 0) {
                    Arme a= (Arme) player.getInventaire().findremove(listarme.get(inputs));
                    player.addArme(a);
                    ObjetDAO.equip(player,a);
                }
                }
            }
        catch(Exception e){
            System.out.println("Entrée invalide");
            weapon();
        }
        }


    private static void armure() {
        int inputs2 = 0;
        Scanner scanner = new Scanner(System.in);
        try {
            while (inputs2 != -1) {
                System.out.println("Votre inventaire contient les armes:");
                LinkedHashMap<Integer, String> listarmure=player.getInventaire().findarmure();
                for(int i=0;i<listarmure.size();i++) {
                    Armure a = (Armure) player.getInventaire().find(listarmure.get(i));
                    System.out.println(i + " : " + a.getNomObjet() + " deg=" + a.getDeg() + " redudegat=" + a.getRedudeg() + " armure de type : "+a.getTypearmure());
                }
                System.out.println("Tapez le numero de l'objet que vous voulez equiper ou -1 si vous voulez quitter");
                inputs2 = scanner.nextInt();
                if (inputs2 >= 0) {
                    Armure a= (Armure) player.getInventaire().findremove(listarmure.get(inputs2));
                    player.addArmure(a);
                    ObjetDAO.equip(player,a);
                }
            }
        } catch (Exception e) {
            System.out.println("Entrée invalide");
            armure();
        }
    }

    public static void playerinput(String input) throws SQLException {
        String playername=player.getNomPersonnage();
        Connection conn= DriverManager.getConnection("jdbc:postgresql://10.113.28.39:5432/jdr2d_simon","stapiau","Afpa54*");
        Statement st0=conn.createStatement();
        //ResultSet rs0= st0.executeQuery("SELECT id_personnage FROM personnage WHERE nom_personnage="+playername);
        PreparedStatement st=conn.prepareStatement("SELECT id_personnage FROM map WHERE ");
        int compteur = 0;
        switch (input) {
            //le deplacement
            case "MOVE":
                deplacement();
                break;

            // la baston
            case "FIGTH":
                for (PNJ p : pnjs) {
                    if (p.distance(player) <= 1) {
                        compteur = compteur + 1;
                        Interaction figth = new Interaction(player, p);
                        if (figth.combat()) {
                            System.out.println("Victoire!");
                        } else {
                            System.out.println("Defaite");
                            break;
                        }
                    }
                }
                if (compteur == 0) {
                    System.out.println("Personne à portée");
                }
                break;

                //la discussion

            case "TALK":
                talk();
                break;

                //ramasser un objet dans un coffre

            case "OPEN":
                pick();
                break;

                //equiper une arme

            case "EQUIPWEAPON":
                weapon();
                break;

                //equiper une armure

            case "EQUIPARMURE" :
                armure();
                break;

                //boire une potion

            case "DRINK":
                drink();
                break;

                //liste des taches
            case "QUETE":
                for (Quete q:player.getQueteSuivie()) {
                    Objectifs o=q.getObjectifs().get(0);
                    if(o instanceof ObjectifT) {
                        System.out.println("Vous suivez la quete " + q.getNomQuete() + " dont l'objectif suivant est de parler a " + EchangeDAO.getparleur(o.getId()));
                    }
                    if(o instanceof ObjectifF){
                        System.out.println("Vous suivez la quete " + q.getNomQuete() + " dont l'objectif suivant est de trouver l'objet " + ((ObjectifF) o).getObjetquete().getNomObjet());
                    }
                    if(o instanceof ObjectifK){
                        System.out.println("Vous suivez la quete " + q.getNomQuete() + " dont l'objectif suivant est de tuer " + ((ObjectifK) o).getTarget().getNomPersonnage());
                    }
                }
                break;

            case "HELP":
                System.out.println("""
                        Pour se déplacer : Move
                        Pour se battre : Figth
                        Pour fouiller un coffre : Open
                        Pour equiper une arme : EquipWeapon
                        Pour equiper une armure : EquipArmure
                        Pour parler : Talk
                        Pour consulter la liste des quêtes: Quete
                        Pour boire une potion : Drink
                        Pour quitter le jeu : Quit""");
                break;

            case "QUIT":
                break;
        }
    }
}