package app.Thread;

import app.riproduzioneMp3.RiproduzioneMp3;

/**
 * La classe {@code ThStopSong} estende {@link Thread} e gestisce l'interruzione della riproduzione di una canzone in modo asincrono.
 * Quando il thread viene eseguito, ferma la riproduzione di una canzone utilizzando la classe {@link RiproduzioneMp3}.
 * 
 */
public class ThStopSong extends Thread {

    /**
     * Esegue il thread che interrompe la riproduzione di una canzone.
     * <p>
     * Quando il thread viene eseguito, chiama il metodo {@link RiproduzioneMp3#stop()} per fermare la riproduzione audio.
     * </p>
     */
    public void run() {
        RiproduzioneMp3.stop();
    }
}
