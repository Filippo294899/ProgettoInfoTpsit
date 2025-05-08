package app.Thread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;

/**
 * La classe {@code ThSaveProfile} estende {@link Thread} e gestisce il salvataggio delle credenziali di accesso dell'utente
 * in un file di testo. Quando il thread viene eseguito, verifica se il file delle credenziali esiste già. Se non esiste,
 * crea un nuovo file e memorizza al suo interno il nome, l'email e la password dell'utente.
 * 
 */
public class ThSaveProfile extends Thread {
    private String nome;
    private String email;
    private String psw;
    
    /**
     * Costruttore per creare un nuovo thread che salverà le credenziali dell'utente.
     * 
     * @param nome Il nome dell'utente.
     * @param email L'email dell'utente.
     * @param psw La password dell'utente.
     */
    public ThSaveProfile(String nome, String email, String psw) {
        super();
        this.nome = nome;
        this.email = email;
        this.psw = psw;
    }

    /**
     * Esegue il thread che salva le credenziali in un file.
     * <p>
     * Quando il thread viene eseguito, verifica se il file delle credenziali esiste già. Se esiste, non fa nulla e termina.
     * Se il file non esiste, viene creato e vi vengono scritti il nome, l'email e la password dell'utente.
     * </p>
     * Se si verifica un errore durante la creazione del file o durante la scrittura dei dati, viene stampato un messaggio
     * di errore e il thread viene interrotto.
     */
    public void run() {
        File file = new File("Credenziali/cookieCredenziali.txt");

        // Se il file esiste già, non fare nulla
        if (file.exists())
            return;

        // Tentativo di creazione del file
        try {
            if (!file.createNewFile()) {
                System.out.println("Errore nella creazione del file login");
                Thread.currentThread().stop();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Scrittura dei dati nel file
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(nome + "\n");
            writer.write(email + "\n");
            writer.write(psw + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
