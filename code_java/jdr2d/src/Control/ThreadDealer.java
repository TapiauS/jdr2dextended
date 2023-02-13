package Control;

import Graphic.FullLogInterface;
import jdr2dcore.Personnage;

public class ThreadDealer implements EventListenerWindow{

    private  Personnage player;



    //getters


    public Personnage getPlayer() {
        return player;
    }




    //setters




    public void setPlayer(Personnage player) {
        this.player = player;
    }


    public void launch() throws InterruptedException {
        FullLogInterface window = new FullLogInterface();
        window.setObserver(this);
        /*Thread t =new Thread(){*/
           // @Override
           // public void run() {
            //super.run();
        window.setVisible(true);//On la rend visible

       // }
   // };
        //t.start();

        System.out.println("Test join");
    }


    @Override
    public void update(FullLogInterface fullLogInterface) throws InterruptedException {
        this.player=fullLogInterface.getPerso();

       Thread t =new Thread(){
        @Override
        public void run() {
        super.run();
        System.out.println("Le thread magique");
             }
             };
            t.start();
    }
}
