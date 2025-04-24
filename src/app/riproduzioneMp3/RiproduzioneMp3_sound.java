package app.riproduzioneMp3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.function.Predicate;

import javax.sound.sampled.*;

import app.model.Model;

public class RiproduzioneMp3_sound {
    private static ArrayList<String> songs = new ArrayList<>();
    private static Integer idxCurrentSong = null;
    private static String currentSong = null;
    private static Clip audioClip = null;
    private static boolean isMediaPlayerStopped = false;
    private static Semaphore mutexPlay = new Semaphore(1);

    public static void addSong(String canzone) {
        songs.add(canzone);
        if (idxCurrentSong == null) {
            idxCurrentSong = 0;
            setCurrentSong();
        }
    }

    public static void coda(Predicate<String> p) {
        if (p.test("<<")) {
            System.out.println("indietro....");
            if (songs.size() <= 1 || (idxCurrentSong - 1) < 0)
                return;
            idxCurrentSong -= 1;
        } else if (p.test(">>")) {
            System.out.println("skip....");
            if (songs.size() <= 1 || songs.size() < (idxCurrentSong + 2))
                return;
            idxCurrentSong += 1;
        }
        setCurrentSong();

        isMediaPlayerStopped = false;
        if (isPlaying()) {
            stop();
            play();
        }
    }

    public static void play() {
        try {
            mutexPlay.acquire();
            System.out.println("play...." + currentSong);

            // Stop previous audio if playing
            if (audioClip != null && audioClip.isRunning()) {
                audioClip.stop();
                audioClip.close();
            }

            // Load the audio file
            File fileAudio = new File( Model.getActuallyDirectory() +currentSong);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(fileAudio);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);

            // Start playback
            audioClip.start();
            isMediaPlayerStopped = false;

            // Listener for end of audio
            audioClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    System.out.println("Canzone terminata.");
                    audioClip.close();
                    if (songs.size() > (idxCurrentSong + 2)) {
                        idxCurrentSong += 1;
                        setCurrentSong();
                        play();
                    }
                    mutexPlay.release();
                }
            });

        } catch (InterruptedException | UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void stop() {
        if (isPlaying()) {
            System.out.println("stop....");
            audioClip.stop();
            audioClip.close();
            isMediaPlayerStopped = true;
            mutexPlay.release();
        } else {
            System.out.println("nn in riproduzione");
        }
    }

    private static void setCurrentSong() {
        currentSong = songs.get(idxCurrentSong);
    }

    private static boolean isPlaying() {
        return audioClip != null && audioClip.isRunning();
    }

    public static String getStato() {
        if (isPlaying())
            return "In riproduzione " + currentSong + "....";
        return "Nessuna canzone in riproduzione";
    }
}