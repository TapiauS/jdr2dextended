

import java.sql.*;

import Graphic.FullLogInterface;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        //Input.game();
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                //On crée une nouvelle instance de notre JWindow
                FullLogInterface window = new FullLogInterface();
                window.setSize(300, 200);//On lui donne une taille pour qu'on puisse la voir
                window.setVisible(true);//On la rend visible
            }
        });
    }
}

