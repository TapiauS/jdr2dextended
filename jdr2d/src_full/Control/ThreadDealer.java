package Control;

import Graphic.FullLogInterface;
import Graphic.GameInterface;
import Entity.Personnage;
import Entity.Utilisateur;

import java.sql.SQLException;

public class ThreadDealer implements EventListenerWindow{

    private  Personnage player;
    private Utilisateur util;
    
    private Thread pnjloop;

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
        window.setVisible(true);//On la rend visible
    }

    @Override
    public void update(FullLogInterface fullLogInterface) throws InterruptedException, SQLException {
        this.player=fullLogInterface.getPerso();
        this.util=fullLogInterface.getUtil();
        GameInterface game = new GameInterface(player,util,fullLogInterface);
        player.setFenetre(game);
        fullLogInterface.reset();
    }



}
