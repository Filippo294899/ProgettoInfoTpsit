package app.Thread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * La classe {@code ThRegistraUtente} estende {@link Thread} e gestisce la registrazione di un nuovo utente in un file di testo.
 * Quando il thread viene eseguito, crea un file per l'utente e memorizza il nome, l'email e la password in esso.
 * Se il file esiste già, il thread termina senza fare modifiche.
 * 
 */
public class ThRegistraUtente extends Thread {
    private String nome;
    private String email;
    private String psw;
    private String path;
    
    /**
     * Costruttore per creare un nuovo thread di registrazione utente.
     * 
     * @param nome Il nome dell'utente.
     * @param email L'email dell'utente.
     * @param psw La password dell'utente.
     */
    public ThRegistraUtente(String nome, String email, String psw) {
        path = "UtentiRegistrati/Utente-";
        this.nome = nome;
        this.email = email;
        this.psw = psw;
    }
    
    /**
     * Esegue il thread che crea un file per l'utente e vi scrive le informazioni di registrazione.
     * <p>
     * Se il file dell'utente esiste già, il thread termina senza fare alcuna modifica. Altrimenti, crea un nuovo file
     * e scrive il nome, l'email e la password dell'utente al suo interno.
     * </p>
     * Se si verifica un errore durante la creazione del file o la scrittura, viene stampato un messaggio di errore.
     */
    public void run() {
        File fileUtente = new File(path + nome + ".txt");

        // Se il file esiste già, non fare nulla
        if (fileUtente.exists())
            return;
        
        try {
            // Tentativo di creazione del file
            if (!fileUtente.createNewFile()) {
                System.out.println("Errore nella creazione del file login");
                Thread.currentThread().stop();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Scrittura dei dati nel file
        try (FileWriter writer = new FileWriter(fileUtente)) {
            writer.write(nome + "\n");
            writer.write(email + "\n");
            writer.write(psw + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
