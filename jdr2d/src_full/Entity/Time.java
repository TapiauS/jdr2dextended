package Entity;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class Time{
    public static void drinkpotion(Potion p, Personnage joueur){
        Thread t =new Thread(){
            @Override
            public void run() {
                super.run();
                joueur.addPotion(p);
                System.out.println("Le joueur a bu "+p.getNomObjet());
                try {
                    TimeUnit.SECONDS.sleep(p.getDuree().get(ChronoUnit.SECONDS));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                joueur.removePotion(p);
                System.out.println("Le joueur n'est plus sous l'effet de la potion "+p.getNomObjet());
            }
        };
        t.start();
    }
}
