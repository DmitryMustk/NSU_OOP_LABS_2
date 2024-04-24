package ru.nsu.dmustakaev.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SoundEngine {
    private MediaPlayer mediaPlayer;

    public void setMusic(String soundName) {
       if(mediaPlayer != null) {
           mediaPlayer.stop();
       }
        Media sound = new Media(getClass().getResource(soundName).toString());
        mediaPlayer = new MediaPlayer(sound);
    }

    public void playSound(String soundName) {
        Media sound = new Media(getClass().getResource(soundName).toString());
        MediaPlayer soundPlayer = new MediaPlayer(sound);
        soundPlayer.play();
    }

    public void playMusic() {
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setStartTime(Duration.seconds(0));
        mediaPlayer.setVolume(0.01);
        mediaPlayer.play();
    }

    public void stopMusic() {
        mediaPlayer.stop();
    }
}
