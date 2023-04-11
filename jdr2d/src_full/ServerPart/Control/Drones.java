package ServerPart.Control;

import Entity.Coffre;
import Entity.PNJ;
import Entity.Point;

import java.util.ArrayList;
import java.util.Random;

public class Drones extends ArrayList<PNJ>{



    private PNJ seed;

    public static final int size=10;

    public static final int areasize=30;
    private final ArrayList<Point> limits;

    //builders

    public Drones(PNJ seed){
        super();
        this.seed=seed;
        limits=new ArrayList<>();
        for (int i = 0; i < seed.getLieux().getCarte()[0].length; i++) {
            for (int j = 0; j < seed.getLieux().getCarte().length; j++) {
                Point potentielajout=new Point(i,j,seed.getLieux());
                if(seed.distance(potentielajout)<areasize&&potentielajout.getLieux().getCarte()[j][i]!='#')
                    limits.add(potentielajout);
            }
        }
        fillmeute();
    }


    private void fillmeute(){
        Random rand=new Random();
        int pos;
        for (int i = 0; i < size; i++) {
            pos=rand.nextInt(this.limits.size());
            PNJ drone=new PNJ(limits.get(pos).getX(),limits.get(pos).getY(),seed.getLieux(),seed.getArme(),seed.getArmure(),seed.getNomPersonnage(),seed.getpV(),new Coffre(),seed.getpVmax(),seed.getRace(),false);
            drone.setId(seed.getId());
            this.add(drone);
        }
    }

    public void addrone(){
        Random rand=new Random();
        int pos=rand.nextInt(this.limits.size());
        this.add(new PNJ(limits.get(pos).getX(),limits.get(pos).getY(),seed.getLieux(),seed.getArme(),seed.getArmure(),seed.getNomPersonnage(),seed.getpV(),new Coffre(),seed.getpVmax(),seed.getRace(),false));
    }


}
