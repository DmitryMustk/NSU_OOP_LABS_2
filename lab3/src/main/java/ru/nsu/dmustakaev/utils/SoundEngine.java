package ru.nsu.dmustakaev.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Objects;

public class SoundEngine {
    private MediaPlayer mediaPlayer;
    private MediaPlayer soundPlayer;

    public void setMusic(String soundName) {
       if(mediaPlayer != null) {
           mediaPlayer.stop();
           mediaPlayer.dispose();
       }
        Media sound = new Media(Objects.requireNonNull(getClass().getResource(soundName)).toString());
        mediaPlayer = new MediaPlayer(sound);
    }

    public void playSound(String soundName) {
        Media sound = new Media(Objects.requireNonNull(getClass().getResource(soundName)).toString());
        soundPlayer = new MediaPlayer(sound);
        soundPlayer.setAutoPlay(true);
        soundPlayer.play();
    }

    public void playMusic() {
//        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//        mediaPlayer.setStartTime(Duration.seconds(0));
        mediaPlayer.setVolume(0.1);
        mediaPlayer.play();
    }

    public void stopMusic() {
        mediaPlayer.stop();
    }
}
