

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;
import java.sql.*;

import Graphic.FullInterface;
import Graphic.LogInterface;
import jdr2dcore.Map;
import jdr2dcore.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        //Input.game();
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                //On crée une nouvelle instance de notre JWindow
                FullInterface window = new FullInterface();
                window.setSize(300, 200);//On lui donne une taille pour qu'on puisse la voir
                window.setVisible(true);//On la rend visible
            }
        });
    }
}

