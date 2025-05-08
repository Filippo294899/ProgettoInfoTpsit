package app.Thread;

import java.io.File;

/**
 * La classe {@code ThLogout} estende {@link Thread} e gestisce l'eliminazione del file delle credenziali
 * in modo asincrono. Quando il thread viene eseguito, cancella il file che contiene le credenziali
 * di login.
 * 
 */
public class ThLogout extends Thread {

    /**
     * Esegue il thread che elimina il file delle credenziali al termine della sessione.
     * <p>
     * Quando il thread viene eseguito, tenta di eliminare il file denominato
     * {@code "Credenziali/cookieCredenziali.txt"}.
     * </p>
     * Se il file non esiste o non può essere eliminato, non viene sollevata alcuna eccezione.
     * 
     * @see File#delete()
     */
    public void run() {
        File file = new File("Credenziali/cookieCredenziali.txt");
        file.delete();
    }

}
