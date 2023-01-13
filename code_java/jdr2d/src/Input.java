import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Input {
    public static void deplacement(Personnage player){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Choose a direction");
        try {
            player.depl(scanner.next().charAt(0));
        }
        catch (IllegalArgumentException e){
            System.out.println("Direction invalide!!!, choix possibles : E,N,S,O");
            deplacement(player);
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
                int[] indexglob = new int[player.getInventaire().getContenu().size()];
                ArrayList<Arme> equipable = new ArrayList<>();
                System.out.println("Votre inventaire contient les armes:");
                int index = 0;
                for (Objet o : player.getInventaire().getContenu()) {
                    // attention il va y avoir un probléme avec les coffres dans l'inventaire !
                    if (o instanceof Arme) {
                        System.out.println(index + " : " + o.getNomObjet() + " deg=" + ((Arme) o).getDeg() + " redudegat=" + ((Arme) o).getRedudeg() + " arme a " + ((Arme) o).getNbrmain() + " mains");
                        equipable.add((Arme) o);
                        indexglob[index] = player.getInventaire().getContenu().indexOf(o);
                        index = index + 1;
                    }
                }
                System.out.println("Tapez le numero de l'objet que vous voulez equiper ou -1 si vous voulez quitter");
                inputs = scanner.nextInt();
                if (inputs >= 0) {
                    Arme a = equipable.get(inputs);
                    equipable.remove(inputs);
                    System.out.println("Test indexglob " + indexglob[inputs]);
                    player.setInventaire(player.getInventaire().remove(indexglob[inputs]));
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
                int[] indexglob = new int[player.getInventaire().getContenu().size()];
                ArrayList<Armure> equipable = new ArrayList<>();
                System.out.println("Votre inventaire contient les armures:");
                int index = 0;
                for (Objet o : player.getInventaire().getContenu()) {
                    // attention il va y avoir un probléme avec les coffres dans l'inventaire !
                    if (o instanceof Armure) {
                        System.out.println(index + " : " + o.getNomObjet() + " deg=" + ((Armure) o).getDeg() + " redudegat=" + ((Armure) o).getRedudeg() + " armure de type  " + ((Armure) o).getTypearmure());
                        equipable.add((Armure) o);
                        System.out.println("Test de index of" + player.getInventaire().getContenu().indexOf(o));
                        indexglob[index] = player.getInventaire().getContenu().indexOf(o);
                        index = index + 1;
                    }
                }
                System.out.println("Tapez le numero de l'objet que vous voulez equiper ou -1 si vous voulez quitter");
                inputs2 = scanner.nextInt();
                if (inputs2 >= 0) {
                    Armure a = equipable.get(inputs2);
                    equipable.remove(inputs2);
                    player.addArmure(a);
                }
            }
        } catch (Exception e) {
            System.out.println("Entrée invalide");
            armure(player);
        }
    }

    public static void playerinput(String input, Personnage player, PNJ[] pnjs, Coffre[] coffres,Echange[] dialogue) {
        Scanner scanner = new Scanner(System.in);
        int compteur = 0;
        switch (input) {
            //le deplacement
            case "Move":
                char[][] carte=player.getLieux().getCarte();
                for (int i=0 ; i<player.getLieux().getDimensions()[0] ; i++){
                    for (int j=0 ;j<player.getLieux().getDimensions()[1];j++){
                        if(i==player.getX() && j==player.getY()){
                            carte[j][i]=' ';
                        }
                    }
                }
                //bidouillage!!!! a changer plus tard !
                deplacement(player);
                for (int i=0 ; i<player.getLieux().getDimensions()[0] ; i++){
                    for (int j=0 ;j<player.getLieux().getDimensions()[1];j++){
                        if(i==player.getX() && j==player.getY()){
                            carte[j][i]='J';
                        }
                    }
                }
                player.setLieux(player.getLieux().setCarte(carte));
                break;

            // la baston
            case "Figth":
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

            case "Talk":
                talk(player,pnjs,dialogue);
                break;

                //ramasser un objet dans un coffre

            case "Open":
                pick(player,coffres);
                break;

                //equiper une arme

            case "EquipWeapon":
                weapon(player);
                break;

                //equiper une armure

            case "EquipArmure" :
                armure(player);
                break;

                //boire une potion

            case "Drink":
                drink(player);
                break;

                //liste des taches

            case "Help":
                System.out.println("""
                        Pour se déplacer : Move
                        Pour se battre : Figth
                        Pour fouiller un coffre : Open
                        Pour equiper une arme : EquipWeapon
                        Pour equiper une armure : EquipArmure
                        Pour parler : Talk
                        Pour boire une potion : Drink
                        Pour quitter le jeu : Quit""");
                break;

            case "Quit":
                break;
        }
    }
}