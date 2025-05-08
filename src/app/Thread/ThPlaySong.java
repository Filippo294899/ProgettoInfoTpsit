package app.Thread;

import app.riproduzioneMp3.RiproduzioneMp3;

/**
 * La classe {@code ThPlaySong} estende {@link Thread} e gestisce l'esecuzione di una canzone in modo asincrono.
 * Quando il thread viene eseguito, avvia la riproduzione di un file audio utilizzando la classe {@link RiproduzioneMp3}.
 * 
*/
public class ThPlaySong extends Thread {

    /**
     * Esegue il thread che avvia la riproduzione di una canzone.
     * <p>
     * Quando il thread viene eseguito, chiama il metodo {@link RiproduzioneMp3#play()} per avviare la riproduzione audio.
     * </p>
     */
    public void run() {
        RiproduzioneMp3.play();
    }
}
