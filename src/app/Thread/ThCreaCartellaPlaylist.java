package app.Thread;

import java.io.File;

/**
 * La classe {@code ThCreaCartellaPlaylist} rappresenta un thread che si occupa
 * della creazione di una cartella per una playlist di file MP3.
 * 
 * <p>La cartella viene creata all'interno del percorso {@code CartelleFileMp3/}
 * con il nome specificato nel costruttore.
 * 
 * <p>Se la cartella esiste già, non viene fatta alcuna operazione.
 * 
 * <p>Esempio d'uso:
 * <pre>{@code
 * ThCreaCartellaPlaylist thread = new ThCreaCartellaPlaylist("Rock");
 * thread.start();
 * }</pre>
 * 
 */
public class ThCreaCartellaPlaylist extends Thread {

    /**
     * Il nome della cartella da creare.
     */
    private String nomecartella;

    /**
     * Costruttore della classe {@code ThCreaCartellaPlaylist}.
     * 
     * @param nomecartella Il nome della nuova cartella da creare
     */
    public ThCreaCartellaPlaylist(String nomecartella) {
        this.nomecartella = nomecartella;
    }

    /**
     * Metodo eseguito quando il thread viene avviato.
     * Se la cartella specificata non esiste, viene creata.
     */
    @Override
    public void run() {
        File cartella = new File("CartelleFileMp3/" + nomecartella);
        if (!cartella.exists())
            cartella.mkdir();
    }
}
