package sounds;

import managers.GameManager;
import managers.GamePlayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer implements Runnable {

    private boolean isFinished;
    private final int playTime;
    private final boolean loop;
    private final String path;

    public SoundPlayer(String path, int playTime, boolean loop) {
        this.playTime = playTime;
        this.loop = loop;
        this.path = path;
        isFinished = false;
    }

    @Override
    public void run() {
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                new File(path));
                Clip clip = AudioSystem.getClip()) {
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            clip.start();
            if(loop) {
                control.setValue(-16.0f);
                for (int i = 0; i < 5; i++) {
                    try {
                        if(isFinished)
                            break;
                        Thread.sleep(1000);
                    } catch (InterruptedException ignore) { }
                    control.setValue(control.getValue() + 3);
                }

                try {
                    while (!isFinished)
                        Thread.sleep(200);
                } catch (InterruptedException ignore) { }

                for (int i = 0; i < 4; i++) {
                    control.setValue(control.getValue() - 4);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ignore) {
                    }
                }
            }
            else try {
                    Thread.sleep(playTime);
                } catch (InterruptedException ignore) { }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
