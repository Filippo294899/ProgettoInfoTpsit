package app.riproduzioneMp3;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.function.Predicate;

import app.Thread.ThPlaySong;
import app.Thread.ThStopSong;
import app.model.Model;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * La classe {@code RiproduzioneMp3} gestisce la riproduzione di file MP3
 * in un'applicazione basata su JavaFX ma avviata in ambiente Swing.
 * <p>
 * Supporta operazioni come play, stop, avanzamento nella coda, gestione dello stato
 * del lettore e sincronizzazione tra thread tramite un semaforo.
 * </p>
 * 
 * <p>Utilizza internamente {@link javafx.scene.media.MediaPlayer} per la riproduzione audio.</p>
 * 
 * <p><b>Nota:</b> alcuni comportamenti sono da migliorare, in particolare la gestione del lock
 * nei thread e la gestione del metodo {@code stop}.</p>
 * 
 */
public class RiproduzioneMp3 {

    /** Lista delle canzoni caricate. */
    private static ArrayList<String> songs = new ArrayList<>();

    /** Indice della canzone attualmente in riproduzione. */
    private static Integer idxCurrentSong = null;

    /** Nome della canzone attuale. */
    private static String currentSong = "";

    /** Oggetto MediaPlayer per la riproduzione. */
    private static MediaPlayer mediaPlayer = null;

    /** Flag per controllare se JavaFX è stato inizializzato. */
    private static boolean javafxInizializzato = false;

    /** Flag che indica se la canzone è in pausa. */
    private static boolean isMediaPLayerStopped = false;

    /** Semaforo per sincronizzare i thread di riproduzione. */
    private static Semaphore mutexPlay = new Semaphore(1);

    /** Inizializza il toolkit JavaFX se non è già stato fatto. */
    private static void inizializzaToolkit() {
        if (!javafxInizializzato) {
            Platform.startup(() -> {});
            javafxInizializzato = true;
        }
    }

    /**
     * Aggiunge una nuova canzone alla coda.
     *
     * @param canzone il nome del file MP3 da aggiungere
     */
    public static void addSong(String canzone) {
        songs.add(canzone);
        if (idxCurrentSong == null) {
            idxCurrentSong = 0;
            setCurrentSong();
        }
    }

    /**
     * Sposta la coda indietro (<<) o avanti (>>) in base al predicato passato.
     * Se è in riproduzione, riparte automaticamente dalla nuova canzone.
     *
     * @param p il predicato che indica l'azione (<< o >>)
     */
    public static void coda(Predicate<String> p) {
        if (p.test("<<")) {
            if (songs.size() <= 1 || (idxCurrentSong - 1) < 0)
                return;
            idxCurrentSong -= 1;
        } else if (p.test(">>")) {
            if (songs.size() <= 1 || songs.size() < (idxCurrentSong + 2))
                return;
            idxCurrentSong += 1;
        }
        setCurrentSong();

        isMediaPLayerStopped = false;
        if (isPlaying()) {
            mediaPlayer.stop();
            new ThPlaySong().start();
            mutexPlay.release();
        }
    }

    /**
     * Avvia la riproduzione della canzone attuale.
     * Se una canzone è già in riproduzione, viene ignorato.
     */
    public static void play() {
        try {
            mutexPlay.acquire();

            inizializzaToolkit();
            File fileAudio = new File(Model.getActuallyDirectory() + currentSong);
            Media media = new Media(fileAudio.toURI().toString());

            if (!isMediaPLayerStopped)
                mediaPlayer = new MediaPlayer(media);

            mediaPlayer.play();
            isMediaPLayerStopped = false;

            mediaPlayer.setOnEndOfMedia(() -> {
                if (songs.size() >= (idxCurrentSong + 2)) {
                    idxCurrentSong += 1;
                    setCurrentSong();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    new ThPlaySong().start();
                } else {
                    mediaPlayer.dispose();
                }
                mutexPlay.release();
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ferma (pausa) la riproduzione corrente.
     */
    public synchronized static void stop() {
        inizializzaToolkit();

        if (isPlaying()) {
            mediaPlayer.pause();
            isMediaPLayerStopped = true;
            mutexPlay.release();
        }
    }

    /** Imposta il nome della canzone corrente in base all'indice. */
    private static void setCurrentSong() {
        currentSong = songs.get(idxCurrentSong);
    }

    /**
     * Imposta direttamente l'indice della canzone attuale e avvia la riproduzione.
     *
     * @param IDXsong l'indice della canzone nella lista
     */
    public static void setCurrentSong(int IDXsong) {
        idxCurrentSong = IDXsong;
        setCurrentSong();
        isMediaPLayerStopped = false;
        if (isPlaying()) {
            mediaPlayer.stop();
            new ThPlaySong().start();
            mutexPlay.release();
        }
    }

    /**
     * Verifica se il player è attualmente in riproduzione.
     *
     * @return true se una canzone è in riproduzione
     */
    private static boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    /**
     * Restituisce una descrizione dello stato del player.
     *
     * @return lo stato attuale in formato stringa
     */
    public static String getStato() {
        if (isPlaying())
            return "In riproduzone " + Model.togliEstensione(currentSong, t -> t != '.') + "....";
        if (isMediaPLayerStopped)
            return "Canzone in pausa";
        return "Nessuna canzone in riproduzione";
    }

    /**
     * Restituisce il tempo attuale della canzone in minuti.
     *
     * @return il tempo trascorso in formato stringa
     */
    public static String getTimeSong() {
        if (isPlaying() || isMediaPLayerStopped)
            return String.format("%.2f", mediaPlayer.getCurrentTime().toMinutes());
        return "0";
    }

    /**
     * Restituisce la durata totale della canzone in secondi.
     *
     * @return la durata in secondi
     */
    public static int getLenghtSong() {
        if (isPlaying() || isMediaPLayerStopped)
            return (int) mediaPlayer.getTotalDuration().toSeconds();
        return 1;
    }

    /**
     * Imposta la posizione corrente della canzone.
     *
     * @param time il tempo in secondi a cui saltare
     */
    public static void setTimeSong(int time) {
        if (isPlaying())
            mediaPlayer.seek(Duration.seconds(time));
    }

    /**
     * Restituisce la lista delle canzoni caricate.
     *
     * @return la lista delle canzoni
     */
    public static ArrayList<String> getCoda() {
        return songs;
    }

    /**
     * Restituisce l'indice della canzone attuale.
     *
     * @return l'indice corrente
     */
    public static int getCurrentSongIdx() {
        return idxCurrentSong;
    }

    /**
     * Restituisce il nome della canzone corrente.
     *
     * @return il nome del file MP3
     */
    public static String getNameCurrentSong() {
        return songs.get(RiproduzioneMp3.getCurrentSongIdx());
    }

    /**
     * Verifica se la coda contiene almeno una canzone.
     *
     * @return true se la coda non è vuota
     */
    public static boolean IsCodaExist() {
        return songs.size() > 0;
    }

    /**
     * Verifica se la canzone è stata fermata (pausata).
     *
     * @return true se è in pausa
     */
    public static boolean IsCanzoneStopped() {
        return isMediaPLayerStopped;
    }
}
