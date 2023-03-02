package Control;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class Soundtrackscontroller {



    private Clip clip;

    private File source;

    private AudioInputStream audioInputStream;

    public Soundtrackscontroller(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        super();
        source=new File("music\\"+filepath);
        audioInputStream = AudioSystem.getAudioInputStream(source.getAbsoluteFile());
        clip=AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        setVolume(0.5f);
    }


    public Clip getClip() {
        return clip;
    }

    public File getSource() {
        return source;
    }

    public AudioInputStream getAudioInputStream() {
        return audioInputStream;
    }

    //setters


    public void setSource(File source) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        clip.stop();
        audioInputStream.close();
        this.source = source;
        audioInputStream = AudioSystem.getAudioInputStream(source.getAbsoluteFile());
        clip=AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }
}
