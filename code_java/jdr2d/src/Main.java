

import java.sql.*;
import java.util.ArrayList;

import Control.ThreadDealer;
import DAO.PersonnageDAO;
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

       char[][] carte= Verslaby.construitgroslaby(150,150,0.5);
        Map map=new Map(new int[]{carte.length, carte[0].length},carte,"GriseColline",5);
        Personnage player=new Personnage(1,0,map,new ArrayList<>(),new ArrayList<>(),"test",10,new Coffre(),15,new ArrayList<>(),new Race());

        GameInterface testmap=new GameInterface(player);

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

