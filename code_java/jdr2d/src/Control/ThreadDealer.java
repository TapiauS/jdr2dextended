package Control;

import Graphic.FullLogInterface;

import javax.swing.*;

public class ThreadDealer {



    public static void launch(){
        Thread t =new Thread(){
            @Override
            public void run() {
            super.run();
            //On crée une nouvelle instance de notre JWindow
            FullLogInterface window = new FullLogInterface();
            window.setVisible(true);//On la rend visible
        }
    };
        t.start();
    }


}
