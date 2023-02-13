

import java.sql.*;

import Control.ThreadDealer;
import Graphic.FullLogInterface;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        //Input.game();
        FullLogInterface window = new FullLogInterface();
        window.setVisible(true);//On la rend visible

        /*SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                //On crée une nouvelle instance de notre JWindow
                FullLogInterface window = new FullLogInterface();
                window.setVisible(true);//On la rend visible
            }
        });
        */
         
    }
}

