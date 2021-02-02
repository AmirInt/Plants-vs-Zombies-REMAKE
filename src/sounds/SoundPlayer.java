package sounds;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Class SoundPlayer is responsible for playing the sound effects and
 * soundtracks of the game
 */
public class SoundPlayer implements Runnable {

    private boolean isFinished;
    private final int playTime;
    private final boolean loop;
    private boolean pauseState;
    private Clip clip;
    private AudioInputStream audioInputStream;
    private FloatControl control;

    /**
     * Instantiates this class
     * @param path The relative path of the file
     * @param playTime The playback time
     * @param loop Boolean determining if the sound must loop or not
     */
    public SoundPlayer(String path, int playTime, boolean loop) {
        this.playTime = playTime;
        this.loop = loop;
        isFinished = false;
        pauseState = false;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(path));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if(loop)
                control.setValue(-30.0f);
            else
                control.setValue(-10.0f);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ignore) { }
    }

    @Override
    public void run() {
        try {
            if(loop) {
                clip.start();
                for (int i = 0; i < 5; i++) {
                    try {
                        if(isFinished)
                            break;
                        Thread.sleep(500);
                    } catch (InterruptedException ignore) { }
                    control.setValue(control.getValue() + 3);
                }

                try {
                    while (!isFinished || pauseState)
                        Thread.sleep(200);
                } catch (InterruptedException ignore) { }

                for (int i = 0; i < 5; i++) {
                    control.setValue(control.getValue() - 3);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignore) {
                    }
                }
            }
            else try {
                clip.start();
                Thread.sleep(playTime);
                } catch (InterruptedException ignore) { }
            clip.close();
            audioInputStream.close();
        } catch (IOException ignore) { }
    }

    /**
     * Sets this sound's finished status
     * @param finished Determines if the need for this sound has ended
     */
    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public void pause() {
        this.pauseState = true;
        clip.stop();
    }

    public void resume() {
        pauseState = false;
        clip.start();
    }
}
