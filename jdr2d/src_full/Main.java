

import java.sql.*;
import java.util.ArrayList;

import Control.ThreadDealer;
import DAO.PersonnageDAO;
import Graphic.EventHistory;
import Graphic.FullLogInterface;
import Graphic.GameInterface;
import gamegenerator.Verslaby;
import jdr2dcore.Coffre;
import jdr2dcore.Map;
import jdr2dcore.Personnage;
import jdr2dcore.Race;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        //Input.game();
        ThreadDealer test=new ThreadDealer();
        test.launch();
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

