package Graphic;
import javax.swing.*;

public class MapGraph {



    public void affichmap(char[][] map) {
        StringBuffer labytdraw = new StringBuffer();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                labytdraw.append(map[i][j]);
            }
            labytdraw.append('\n');

        }
    }
}
