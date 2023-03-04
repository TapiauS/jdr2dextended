package Control;

import Graphic.GameInterface;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SoundEffect {

    private static Clip clip;


    private static AudioInputStream audioInputStream;
    private static GameInterface fenetre;

    public SoundEffect(GameInterface fenetres) throws LineUnavailableException {
        fenetre=fenetres;
        clip= AudioSystem.getClip();
        setVolume(0.5f);
    }

    //methodes

    public static void playSound(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(clip!=null)
            clip.close();
        File source=new File("SoundEffect"+"\\"+filename+".wav");
        audioInputStream=AudioSystem.getAudioInputStream(source);
        clip=AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(1);
        setVolume(1f);
    }

    public static void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    public static float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }
}
