import java.util.ArrayList;
import java.util.Scanner;

public class Input {
    public void deplacement(Personnage player){

        Scanner scanner=new Scanner(System.in);
        System.out.println("Choose a direction");
        try {
            player.depl(scanner.next().charAt(0));
        }
        catch (IllegalArgumentException e){
            System.out.println("Direction invalide!!!, choix possibles : E,N,S,O");
            this.deplacement(player);
        }
    }

    public void pick(Personnage player,Coffre[] coffres){
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
                            player.setInventaire(player.getInventaire().add(o));
                            for(Quete q: player.getQueteSuivie()){
                                int compteur0=0;
                                for(Objectifs ob: q.getObjectifs()){
                                    if(ob instanceof ObjectifF){
                                        if(((ObjectifF) ob).getObjetquete()==o){
                                            ob.setValide(true);
                                        }
                                        if(ob.isValide()){
                                            compteur0++;
                                        }
                                    }
                                }
                                if (compteur0==q.getObjectifs().length){
                                    for(Objet ob:q.getRecompenses())
                                    player.setInventaire(player.getInventaire().add(ob));
                                    player.removesQuete(q);
                                }
                            }
                        }
                        index = 0;
                    }
                    }
                }
            if (compteur==0){System.out.println("Aucun coffre a portée");}
            }
        catch (Exception e){
            System.out.println("Entrée invalide");
            this.pick(player,coffres);
        }
    }

    public void drink(Personnage player){
        int inputs3 = 0;
        Scanner scanner=new Scanner(System.in);
        try{
        while (inputs3 != -1) {
            int[] indexglob = new int[player.getInventaire().getContenu().size()];
            ArrayList<Potion> buvable = new ArrayList<>();
            System.out.println("Votre inventaire contient les potions:");
            for (Objet o : player.getInventaire().getContenu()) {
                // attention il va y avoir un probléme avec les coffres dans l'inventaire !
                int index = 0;
                if (o instanceof Potion) {
                    System.out.println(index + " : " + o.getNomObjet() + " deg=" + ((Potion) o).getEffets()[0] + " redudegat=" + ((Potion) o).getEffets()[1] + " Pv=  " + ((Potion) o).getEffets()[2] + " Pvmax= " + ((Potion) o).getEffets()[3]);
                    buvable.add((Potion) o);
                    indexglob[index] = player.getInventaire().getContenu().indexOf(o);
                    index = index + 1;
                }
            }
            System.out.println("Tapez le numero de la potion que vous voulez boire ou -1 si vous voulez quitter");
                inputs3 = scanner.nextInt();
                if (inputs3 >= 0) {
                    Potion p = buvable.get(inputs3);
                    buvable.remove(inputs3);
                    Time t = new Time();
                    player.setInventaire(player.getInventaire().remove(indexglob[inputs3]));
                    t.drinkpotion(p, player);
                }

            }
        }
        catch (Exception e) {
            System.out.println("Entrée invalide");
            this.drink(player);
        }
    }

    public void talk(Personnage player,PNJ[] pnjs,Echange[] dialogue) {
        int compteur = 0;
        Scanner scanner = new Scanner(System.in);
        try {
            for (PNJ p : pnjs) {
                if (p.distance(player) <= 1) {
                    compteur = compteur + 1;
                    for (Echange e : dialogue) {
                        if (e.getParleur() == p && e.getQuestion() == null) {
                            System.out.println(p.getNomPersonnage() + " :" + e.getReponse());
                            Echange nextechange = e;
                            do {
                                int entre = 0;
                                //pour l'instant on peut avoir plusieur fois la même quête , il faudrait probablement faire de echange suivant un array list pour éviter ce probléme
                                if (nextechange.isQuete()) {
                                    player.addsQuete(nextechange.getQuete());
                                }
                                nextechange.dialogue();
                                System.out.println("Choisissez le numero de votre réponse :");
                                entre = scanner.nextInt();
                                nextechange = nextechange.getDialogueSuivant()[entre];
                                System.out.println(p.getNomPersonnage() + " : " + nextechange.getReponse());

                            } while (nextechange.getDialogueSuivant() != null);
                        }
                    }
                }
            }
            if (compteur == 0) {
                System.out.println("Personne n'est assez proche pour parler");
            }
        } catch (Exception ex) {
            System.out.println("Entrée invalide");
            talk(player, pnjs, dialogue);
        }
    }

    public void weapon(Personnage player) {
        Scanner scanner = new Scanner(System.in);
        int inputs = 0;
        try {
            while (inputs != -1) {
                int[] indexglob = new int[player.getInventaire().getContenu().size()];
                ArrayList<Arme> equipable = new ArrayList<>();
                System.out.println("Votre inventaire contient les armes:");
                for (Objet o : player.getInventaire().getContenu()) {
                    // attention il va y avoir un probléme avec les coffres dans l'inventaire !
                    int index = 0;
                    if (o instanceof Arme) {
                        System.out.println(index + " : " + o.getNomObjet() + " deg=" + ((Arme) o).getDeg() + " redudegat=" + ((Arme) o).getRedudeg() + " arme a " + ((Arme) o).getNbrmain() + " mains");
                        equipable.add((Arme) o);
                        System.out.println("Test de index of " + player.getInventaire().getContenu().indexOf(o));
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


    public void armure(Personnage player) {
        int inputs2 = 0;
        Scanner scanner = new Scanner(System.in);
        try {
            while (inputs2 != -1) {
                int[] indexglob = new int[player.getInventaire().getContenu().size()];
                ArrayList<Armure> equipable = new ArrayList<>();
                System.out.println("Votre inventaire contient les armures:");
                for (Objet o : player.getInventaire().getContenu()) {
                    // attention il va y avoir un probléme avec les coffres dans l'inventaire !
                    int index = 0;
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
                    player.setInventaire(player.getInventaire().remove(indexglob[inputs2]));
                    player.addArmure(a);
                }

            }
        } catch (Exception e) {
            System.out.println("Entrée invalide");
            armure(player);
        }
    }

    public void playerinput(String input, Personnage player, PNJ[] pnjs, Coffre[] coffres,Echange[] dialogue) {
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
                this.deplacement(player);
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
                this.talk(player,pnjs,dialogue);
                break;

                //ramasser un objet dans un coffre

            case "Open":
                this.pick(player,coffres);
                break;

                //equiper une arme

            case "EquipWeapon":
                this.weapon(player);
                break;

                //equiper une armure

            case "EquipArmure" :
                this.armure(player);
                break;

                //boire une potion

            case "Drink":
                this.drink(player);
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