package Control;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Soundtrackscontroller {
    private Long currentFrame;

    private Clip clip;

    private File source;

    private AudioInputStream audioInputStream;

    public Soundtrackscontroller(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super();

    }
}
