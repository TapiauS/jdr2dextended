package Control;

import Graphic.GameInterface;
import Log.LogLevel;
import Log.Loggy;
import ServerPart.Control.Interaction;
import ServerPart.Control.PersoThread;
import ServerPart.ServerGameOutputType;
import jdr2dcore.ObjectifK;
import jdr2dcore.PNJ;
import jdr2dcore.Personnage;

import java.io.IOException;

public class PNJiaProtocol extends Thread{

    private GameInterface fenetre;

    private boolean running;

    public PNJiaProtocol(GameInterface fenetre){
        this.fenetre=fenetre;
        this.running=true;
        this.start();
    }

    //setters
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        super.run();
        while (running) {
            Personnage player = fenetre.getPlayer();
            int idaversaire = -1;
            try {
                ServerGameOutputType action = null;
                try {
                    action = PNJIASocket.read();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                if (action == ServerGameOutputType.PNJATK) {
                    PNJIASocket.write(fenetre.isInteraction());
                    if (!fenetre.isInteraction()) {
                        idaversaire = PNJIASocket.read();
                        fenetre.getEventHistory().addLine(fenetre.getPlayer().getNomPersonnage() + " est attaqué par " + PNJIASocket.read());
                        boolean stillfigthing = true;
                        fenetre.setInteraction(true);
                        while (stillfigthing) {
                            try {
                                stillfigthing = ClientPart.read();
                                System.out.println(stillfigthing);
                            } catch (IOException | ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            if (stillfigthing) {
                                try {
                                    String newline=ClientPart.read();
                                    System.out.println(newline);
                                    fenetre.getEventHistory().addLine(newline);
                                    fenetre.getEventHistory().addLine(ClientPart.read());
                                } catch (IOException | ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                                fenetre.getFenetreInfo().update();
                            }
                        }
                        fenetre.getPlayer().setpV(PNJIASocket.read());
                        if (fenetre.getPlayer().getpV() <= 0)
                            PersoThread.respawn(fenetre.getPlayer());
                        else {
                            fenetre.setInteraction(false);
                            for (int i = 0; i < player.getQueteSuivie().size(); i++) {
                                for (int j = 0; j < player.getQueteSuivie().get(i).getObjectifs().size(); j++) {
                                    if (player.getQueteSuivie().get(i).getObjectifs().get(j) instanceof ObjectifK) {
                                        if (((ObjectifK) player.getQueteSuivie().get(i).getObjectifs().get(j)).getTarget().getId() == idaversaire) {
                                            player.getQueteSuivie().get(i).getObjectifs().get(j).update();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                Loggy.writlog("ERREUR DE DECONNEXION CLIENT "+e.getMessage(), LogLevel.DEBUG);
                throw new RuntimeException(e);
            }
        }
    }

}
