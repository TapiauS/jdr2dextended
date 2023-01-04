import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class Time{
    public static void drinkpotion(Potion p,Personnage joueur){
        Thread t =new Thread(){
            @Override
            public void run() {
                super.run();
                joueur.addPotion(p);
                try {
                    TimeUnit.SECONDS.sleep(p.getDuree().get(ChronoUnit.SECONDS));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                joueur.removePotion(p);
            }
        };
        t.start();
    }
}
