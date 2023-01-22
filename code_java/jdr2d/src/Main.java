

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.sql.*;
import jdr2dcore.*;
import jdr2dcore.Map;
import tableau.*;
import DAO.*;

    public class Main {
        public static void main(String[] args) throws SQLException {
            //declaration de la map de test et tout ses protagonistes
            char[][] labytest = new char[][]{{' ', ' ', '#', ' ', ' '}, {' ', ' ', '#', ' ', ' '}, {' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' '}, {'C', ' ', ' ', ' ', 'E'}};
            char[][] labytest1=new char[][]{{' ','#',' '},{' ','#',' '},{' ',' ',' '}};
            Map maptest = new Map(new int[]{5, 5}, labytest, "Arcanum",0);
            Map maptest1=new Map(new int[]{3,3},labytest1,"Tarante",0);
            Porte porte1=new Porte(maptest1,0,0);
            Porte porte0=new Porte(maptest,4,0,porte1);
            Porte [] portes=new Porte[] {porte0,porte1};
            Coffre coftest = new Coffre();
            Arme bataille = new Arme("Bat'aille", 1, 7, 3, 2);
            Armure plastraille = new Armure("Plastr'aille", 3, 0, 8, "Torse");
            ArrayList<Arme> armedefault = new ArrayList<>(List.of(new Arme("Poing", 0, 0, 0, 0)));
            ArrayList<Arme> armevampire = new ArrayList<>(List.of(new Arme("Griffe Vampirique", 0, 10, 0, 0)));
            ArrayList<Armure> armuredefault = new ArrayList<Armure>(List.of(new Armure("Peau", 0, 0, 0, "Natif")));
            ArrayList<Armure> armurevampire = new ArrayList<Armure>(List.of(new Armure("Peau Vampirique", 0, 0, 4, "Natif")));
            PNJ jeanluc = new PNJ(1, 0, maptest, armedefault, armuredefault, "Jean Luc le rouge", 5, new Coffre(), 5, null, new Race("Humain", null), null, true);
            PNJ jeanma = new PNJ(4, 4, maptest, armevampire, armurevampire, "Jean Marie Le PNJ", 30, new Coffre(), 30, null, new Race("Vampire", null), null, true);
            Personnage joueur = new Personnage(1, 0, maptest, armedefault, armuredefault, "Donatien", 30, new Coffre(), 30, new ArrayList<>(), new Race("Humain", null));
            coftest.setLieux(maptest).setX(0).setY(4);
            coftest.add(bataille).add(plastraille);
            Scanner scanner = new Scanner(System.in);
            String input = "Rien";
            ObjectifF batte = new ObjectifF(bataille);
            String outputest=new String();
            for(int i=0;i<labytest.length;i++){
                for(int j=0;j<labytest[i].length;j++) {
                    int n=labytest[i].length;
                    switch (j) {
                        case(0):
                            outputest += "|" + labytest[i][j];
                            break;
                        case(4) :
                            outputest += labytest[i][j] + "|";
                            break;
                        default:
                            outputest += " " + labytest[i][j];
                            break;
                    }
                }
                outputest+='\n';
            }


            JOptionPane.showMessageDialog(null,outputest,"Carte",JOptionPane.PLAIN_MESSAGE);

            Quete quete = new Quete("La mort de jeanmarie", "flemme", new ArrayList<Objectifs>(), new Objet[]{plastraille});
            quete.addObjectifs(batte);
            Echange dialognegatif = new Echange(jeanluc, "C'est non !", "A 600 000 voix prés !!", null);
            Echange dialogueconcl = new Echange(jeanluc, "C'est bien normal", "Merci beaucoup, tu pourra trouver de l'équipement au sud", null);
            Echange dialoguequete = new Echange(jeanluc, "C'est fait", "Bravo vous avez trouver l'equipement!", null);
            Echange dialoguepos = new Echange(jeanluc, "Oui bien sur", "Enfin un brave prés a lutter contre cette peste vampirique", new Echange[]{dialogueconcl}, true, quete);
            Echange dialogue_r=new Echange(jeanluc, "A propos du travail que vous proposez", "Avez vous récupéré l'equipement?", new Echange[] {dialoguequete}, false, null, true, null);
            Echange dialogueintro = new Echange(jeanluc, null, "Bonjour voulez vous m'aidez à purger ce lieux ?", new Echange[]{dialognegatif, dialoguepos,dialogue_r});

            ObjectifT obvious = new ObjectifT(dialogue_r);
            Echange[] listedialogue = new Echange[]{ dialogueintro,dialoguequete,dialognegatif,dialogue_r, dialoguepos, dialogueconcl};
            Potion ptest = new Potion("Potion de Force", 1, new int[]{5, 0, 0, 0}, Duration.of(15, ChronoUnit.SECONDS));
            Potion ptest1= new Potion("Potion de Force", 1, new int[]{5, 0, 0, 0}, Duration.of(15, ChronoUnit.SECONDS));
            joueur.addObjet(ptest).addObjet(ptest1);

            quete.addObjectifs(obvious);


            System.out.println("Vous devez tuer Jean Marie le PNJ, attention il vous faudra peut être vous équiper");
            while (!Objects.equals(input, "Quit") && jeanma.getpV() > 0 && joueur.getpV() > 0) {
                for (int i = 0; i < joueur.getLieux().getDimensions()[0]; i++) {
                    for (int j = 0; j < joueur.getLieux().getDimensions()[1]; j++) {
                        if(j!= joueur.getX() || i!=joueur.getY()) {
                            System.out.print(joueur.getLieux().getCarte()[i][j]);
                        }
                        else{
                            System.out.print('J');
                        }
                    }
                    System.out.print('\n');
                }

                System.out.println("Tapez une commande pour votre personnage, tapez \"Help\" pour la liste des commandes");
                input = scanner.next().toUpperCase();
                Input.playerinput(input, joueur, new PNJ[]{jeanma, jeanluc}, new Coffre[]{coftest}, listedialogue,portes);
                if (jeanma.getpV() <= 0) {
                    System.out.println("Felicitation pour votre victoire");
                } else if (joueur.getpV() <= 0) {
                    System.out.println("Malheuresement Jean marie vivra encore quelques années");
                }
            }
        }
    }

