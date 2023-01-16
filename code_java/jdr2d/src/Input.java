import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Input {

    public static void deplacement(Personnage player,Porte[] portes){
        Scanner scanner=new Scanner(System.in);
        char input='f';
        try {
            while (input != 'J') {
                for (Porte p : portes) {
                    if (p.distance(player) < 1) {
                        System.out.println("Il y a une porte à proximité voulez vous la traverser ? y/n");
                        String inputs = scanner.next().toUpperCase();
                        if (inputs.equals("Y")) {
                            p.traverse(player);
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
            deplacement(player,portes);
        }
    }

    public static void pick(Personnage player,Coffre[] coffres){
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
                                }
                    index = 0;
                                }
                    }
                }
            if (compteur==0){System.out.println("Aucun coffre a portée");}
            }
        catch (Exception e){
            System.out.println("Entrée invalide");
            pick(player,coffres);
        }
    }

    public static void drink(Personnage player){
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
            drink(player);
        }
    }

    public static void talk(Personnage player,PNJ[] pnjs,Echange[] dialogue) {
        int compteur = 0;
        try {
            for (PNJ p : pnjs) {
                if (p.distance(player) <= 1) {
                    compteur = compteur + 1;
                    for (Echange e : dialogue) {
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
            System.out.println("Je passe par le catch");
            System.out.println("Entrée invalide");
            talk(player, pnjs, dialogue);
        }
    }

    public static void weapon(Personnage player) {
        Scanner scanner = new Scanner(System.in);
        int inputs = 0;
        try {
            while (inputs != -1) {
                System.out.println("Votre inventaire contient les armes:");
                LinkedHashMap<Integer, String> listarme=player.getInventaire().findweapon();
                for(int i=0;i<listarme.size();i++) {
                    Arme a = (Arme) player.getInventaire().find(listarme.get(i));
                    System.out.println(i + " : " + a.getNomObjet() + " deg=" + a.getDeg() + " redudegat=" + a.getRedudeg() + " Arme à  " + a.getNbrmain() + " mains ");
                }
                System.out.println("Tapez le numero de l'objet que vous voulez equiper ou -1 si vous voulez quitter");
                inputs = scanner.nextInt();
                if (inputs >= 0) {
                    Arme a= (Arme) player.getInventaire().findremove(listarme.get(inputs));
                    player.addArme(a);
                }
                }
            }
        catch(Exception e){
            System.out.println("Entrée invalide");
            weapon(player);
        }
        }


    public static void armure(Personnage player) {
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
                }
            }
        } catch (Exception e) {
            System.out.println("Entrée invalide");
            armure(player);
        }
    }

    public static void playerinput(String input, Personnage player, PNJ[] pnjs, Coffre[] coffres,Echange[] dialogue,Porte[] portes) {
        Scanner scanner = new Scanner(System.in);
        int compteur = 0;
        switch (input) {
            //le deplacement
            case "MOVE":
                deplacement(player,portes);
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
                talk(player,pnjs,dialogue);
                break;

                //ramasser un objet dans un coffre

            case "OPEN":
                pick(player,coffres);
                break;

                //equiper une arme

            case "EQUIPWEAPON":
                weapon(player);
                break;

                //equiper une armure

            case "EQUIPARMURE" :
                armure(player);
                break;

                //boire une potion

            case "DRINK":
                drink(player);
                break;

                //liste des taches
            case "QUETE":
                for (Quete q:player.getQueteSuivie()) {
                    Objectifs o=q.getObjectifs().get(0);
                    if(o instanceof ObjectifT) {
                        System.out.println("Vous suivez la quete " + q.getNomQuete() + " dont l'objectif suivant est de parler a " + ((ObjectifT) o).getConvaincre().getParleur());
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