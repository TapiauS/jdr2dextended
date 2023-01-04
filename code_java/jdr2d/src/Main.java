import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //declaration de la map de test et tout ses protagonistes
        char[][] labytest=new char[][] {{'J',' ','#',' ',' '},{' ',' ','#',' ',' '},{' ',' ',' ',' ',' '},{' ',' ',' ',' ',' '},{'C',' ',' ',' ','E'}};
        Map maptest=new Map(new int[] {5,5},labytest,"Arcanum");
        Coffre coftest=new Coffre();
        Arme bataille =new Arme("Bat'aille",1,7,3,2);
        Armure plastraille=new Armure("Plastr'aille",3,0,8,"Torse");
        ArrayList<Arme> armedefault=new ArrayList<>(List.of(new Arme("Poing",0,0,0,0)));
        ArrayList<Arme> armevampire=new ArrayList<>(List.of(new Arme("Griffe Vampirique",0,10,0,0)));
        ArrayList<Armure> armuredefault=new ArrayList<Armure>(List.of(new Armure("Peau",0,0,0)));
        ArrayList<Armure> armurevampire=new ArrayList<Armure>(List.of(new Armure("Peau Vampirique",0,0,4)));
        PNJ jeanluc=new PNJ(1,0,maptest,armedefault,armuredefault,"Jean Luc le rouge",5,new Coffre(),5,null,null,new Race("Humain",null),null,true);
        PNJ jeanma=new PNJ(4,4,maptest,armevampire,armurevampire,"Jean Marie Le PNJ",30,new Coffre(),30,null,null,new Race("Vampire",null),null,true);
        Personnage joueur=new Personnage(0,0,maptest,armedefault,armuredefault,"Donatien",30,new Coffre(),30,new ArrayList<>(),new ArrayList<>(),new Race("Humain",null));
        coftest.setLieux(maptest).setX(0).setY(4);
        coftest.add(bataille).add(plastraille);
        Scanner scanner=new Scanner(System.in);
        String input="Rien";
        ObjectifF batte=new ObjectifF(bataille);
        Quete quete=new Quete("La mort de jeanmarie","flemme",new Objectifs[] {batte},new Objet[] {plastraille});
        Echange dialognegatif=new Echange(jeanluc,"C'est non !","A 600 000 voix prés !!",null);
        Echange dialogueconcl=new Echange(jeanluc,"C'est bien normal","Merci beaucoup, tu pourra trouver de l'équipement au sud",null);
        Echange dialoguepos=new Echange(jeanluc,"Oui bien sur","Enfin un brave prés a lutter contre cette peste vampirique",new Echange[] {dialogueconcl},true,quete);
        Echange dialogueintro=new Echange(jeanluc,null,"Bonjour voulez vous m'aidez à purger ce lieux ?",new Echange[] {dialognegatif,dialoguepos});
        Echange []  listedialogue=new Echange[] {dialognegatif,dialogueintro,dialoguepos,dialogueconcl};
        Potion ptest=new Potion("Potion de Force",1,new int[] {5,0,0,0}, Duration.of(15, ChronoUnit.SECONDS));
        joueur.setInventaire(joueur.getInventaire().add(ptest));


        System.out.println("Vous devez tuer Jean Marie le PNJ, attention il vous faudra peut être vous équiper");
        while(!Objects.equals(input, "Quit") && jeanma.getpV()>0 && joueur.getpV()>0){
            for (int i=0 ; i<5 ; i++){
                for (int j=0 ;j<5;j++){
                 System.out.print(labytest[i][j]);
                }
                System.out.print('\n');
            }
            System.out.println("Tapez une commande pour votre personnage, tapez \"Help\" pour la liste des commandes");
            input=scanner.next();
            Input.playerinput(input,joueur,new PNJ[] {jeanma,jeanluc},new Coffre[] {coftest},listedialogue);
            if(jeanma.getpV()<=0){
                System.out.println("Felicitation pour votre victoire");
            } else if (joueur.getpV()<=0) {
                System.out.println("Malheuresement Jean marie vivra encore quelques années");
            }
        }
    }
}