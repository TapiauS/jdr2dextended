import java.util.ArrayList;
import java.util.Scanner;

public class Input {
    public void playerinput(String input, Personnage player, PNJ[] pnjs, Coffre[] coffres) {
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
                System.out.println("Choose a direction");
                player.depl(scanner.next().charAt(0));
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
                for (PNJ p : pnjs) {
                    if (p.distance(player) <= 1) {
                    }
                }
                break;

                //ramasser un objet dans un coffre
            case "Open":
                for (Coffre c : coffres) {
                    System.out.println("coucou");
                    if (c.distance(player) <= 1) {
                        int inputs = 0;
                        int index = 0;
                        while (inputs != -1) {
                            System.out.println("Ce coffre contient :");
                            for (Objet o : c.getContenu()) {
                                System.out.println(index + ":" + o.getNomObjet());
                                index=index+1;
                            }
                            System.out.println("Tapez le numero de l'objet que vous voulez prendre ou -1 si vous voulez quitter");
                            inputs = scanner.nextInt();
                            if (inputs >= 0 && inputs < c.getContenu().size()) {
                                Objet o = c.getContenu().get(inputs);
                                System.out.println("L'inventaire devrait récupérer :"+o.getNomObjet());
                                c.remove(inputs);
                                player.setInventaire(player.getInventaire().add(o));
                                System.out.println("Linventaire vient de récupérer"+player.getInventaire().getContenu().get(0).getNomObjet());
                            }
                            index=0;
                        }
                    }
                }
                break;
                //equiper une arme
            case "EquipWeapon":
                int inputs = 0;
                while (inputs != -1) {
                    int[] indexglob = new int[player.getInventaire().getContenu().size()];
                    ArrayList<Arme> equipable = new ArrayList<>();
                for(Objet o: player.getInventaire().getContenu()) {
                    // attention il va y avoir un probléme avec les coffres dans l'inventaire !
                    System.out.println("Votre inventaire contient les armes:");
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
                        if (inputs >= 0 && inputs < equipable.size()) {
                            Arme a = equipable.get(inputs);
                            equipable.remove(inputs);
                            System.out.println("Test indexglob "+indexglob[inputs]);
                            player.setInventaire(player.getInventaire().remove(indexglob[inputs]));
                            player.addArme(a);
                        }
                    }

                break;

            case "EquipArmure" :
                int inputs2 = 0;
                while (inputs2 != -1) {
                    int[] indexglob = new int[player.getInventaire().getContenu().size()];
                    ArrayList<Armure> equipable = new ArrayList<>();
                for(Objet o: player.getInventaire().getContenu()) {
                    // attention il va y avoir un probléme avec les coffres dans l'inventaire !
                    System.out.println("Votre inventaire contient les armures:");
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
                        if (inputs2 >= 0 && inputs2 < equipable.size()) {
                            Armure a = equipable.get(inputs2);
                            equipable.remove(inputs2);
                            System.out.println("Test indexglob"+indexglob[inputs2]);
                            player.setInventaire(player.getInventaire().remove(indexglob[inputs2]));
                            player.addArmure(a);
                        }
                    }

                break;
            case "Help":
                System.out.println("Pour se déplacer : Move"+'\n'+"Pour se battre : Figth"+'\n'+"Pour fouiller un coffre : Open"+'\n'+"Pour equiper une arme : EquipWeapon"+'\n'+"Pour equiper une armure : EquipArmure"+'\n'+"Pour quitter le jeu : Quit");
                break;
            case "Quit":
                break;
        }
    }
}