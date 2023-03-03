package Control;

import Graphic.GameInterface;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundEffect {

    private Clip clip;

    //private File source;

    private AudioInputStream audioInputStream;

    private GameInterface fenetre;

    public SoundEffect(GameInterface fenetre){
        this.fenetre=fenetre;

    }
}
