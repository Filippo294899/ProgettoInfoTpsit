package app.riproduzioneMp3;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.function.Predicate;

import app.Thread.ThPlaySong;
import app.model.Model;
import javazoom.jlgui.basicplayer.BasicPlayer;

public class RiproduzioneMp3_javazoom { // basic player

    private static ArrayList<String> songs = new ArrayList<>();
    private static Integer idxCurrentSong = null;
    private static String currentSong = null;
    private static BasicPlayer mediaPlayer = null;
    private static boolean isMediaPlayerStopped = false;
    private static Semaphore mutexPlay = new Semaphore(1);
    private static Thread playThread = null;

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
        stop();
        new ThPlaySong().start();  // ThPlaySong chiama play()
    }

    public static void play() {
        try {
            mutexPlay.acquire();

            System.out.println("play...." + currentSong);
            FileInputStream fis = new FileInputStream(Model.getActuallyDirectory() + currentSong);

            mediaPlayer = new BasicPlayer();
            mediaPlayer.open(fis);
            
            playThread = new Thread(() -> {
                try {
                    mediaPlayer.play();  // bloccante
                    System.out.println("Canzone terminata.");

                    if (songs.size() > (idxCurrentSong + 1)) {
                        idxCurrentSong += 1;
                        setCurrentSong();
                        play(); // autoplay next
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mutexPlay.release();
                }
            });

            playThread.start();
            isMediaPlayerStopped = false;

        } catch (Exception e) {
            e.printStackTrace();
            mutexPlay.release();
        }
    }

    public synchronized static void stop() {
        if (playThread != null && playThread.isAlive()) {
            System.out.println("stop....");
            playThread.interrupt();
            isMediaPlayerStopped = true;
            try {
                mediaPlayer.stop();  // Ferma la riproduzione in corso
            } catch (Exception e) {
                e.printStackTrace();
            }
            mutexPlay.release();
        } else {
            System.out.println("nn in riproduzione");
        }
    }

    private static void setCurrentSong() {
        currentSong = songs.get(idxCurrentSong);
    }

    private static boolean isPlaying() {
        return playThread != null && playThread.isAlive();
    }

    public static String getStato() {
        if (isPlaying())
            return "In riproduzione " + Model.togliEstensione(currentSong, t -> t != '.') + "....";
        return "Nessuna canzone in riproduzione";
    }
}
