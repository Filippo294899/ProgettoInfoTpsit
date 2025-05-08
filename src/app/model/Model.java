package app.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;

import app.Thread.ThSaveProfile;
import app.api.GenereApi;

/**
 * Classe Model che gestisce i dati e le operazioni di backend dell'applicazione musicale.
 * Si occupa di gestire file, directory, utenti e generazione di playlist.
 */
public class Model {
    
    private static String nomeCartellaPrincipale;
    private static String nomeCartellaRiprodotta;

    /**
     * Costruttore della classe Model.
     * Inizializza i nomi delle cartelle a stringhe vuote.
     */
    public Model() {
        nomeCartellaPrincipale = "";
        nomeCartellaRiprodotta = "";
    }

    /**
     * Restituisce l'elenco delle sottocartelle dell'utente.
     *
     * @return ArrayList contenente i nomi delle cartelle dell'utente.
     */
    public ArrayList<String> getElencoCartelleUtente() {
        ArrayList<String> s = new ArrayList<>();
        File cartella = new File("CartelleFileMp3/" + nomeCartellaPrincipale + "/");
        for (String nomeFile : cartella.list())
            s.add(nomeFile);
        return s;
    }

    /**
     * Restituisce l'elenco dei file MP3 nella cartella attualmente riprodotta.
     *
     * @return ArrayList con i nomi dei file nella cartella corrente.
     */
    public ArrayList<String> getElencoCanzoniCartellaRiprodotta() {
        ArrayList<String> s = new ArrayList<>();
        File canzoni = new File("CartelleFileMp3/" + nomeCartellaPrincipale + "/" + nomeCartellaRiprodotta + "/");
        for (String tit : canzoni.list())
            s.add(tit);
        return s;
    }

    /**
     * Rimuove l'estensione da una stringa secondo un predicato.
     *
     * @param s stringa su cui operare.
     * @param p predicato che stabilisce se un carattere può essere incluso.
     * @return stringa senza estensione.
     */
    public static String togliEstensione(String s, Predicate<Character> p) {
        String finalString = "";
        for (int i = 0; i < s.length(); i++)
            if (p.test(s.charAt(i)))
                finalString += s.charAt(i);
            else
                break;
        return finalString;
    }

    /**
     * Verifica se un utente è già registrato.
     *
     * @param nome  nome utente.
     * @param email email utente.
     * @param psw   password utente.
     * @return true se l'utente esiste, false altrimenti.
     */
    public boolean IsUtenteEsistente(String nome, String email, String psw) {
        File cartella = new File("UtentiRegistrati/");
        for (String s : cartella.list()) {
            try (Scanner scanner = new Scanner(new File("UtentiRegistrati/" + s))) {
                String riga1 = scanner.nextLine();
                String riga2 = scanner.nextLine();
                String riga3 = scanner.nextLine();

                if (riga1.equals(nome) && riga2.equals(email) && riga3.equals(psw)) {
                    SaveProfile(nome, email, psw);
                    nomeCartellaPrincipale = nome;
                    creaCartellaLibreria();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Crea la cartella "Libreria" per l'utente.
     */
    private void creaCartellaLibreria() {
        File cartella1 = new File("CartelleFileMp3/" + nomeCartellaPrincipale);
        File cartella = new File(cartella1.getPath() + "/Libreria");

        if (!cartella.exists()) {
            if (cartella.mkdirs()) {
                System.out.println("Cartella creata: " + cartella.getPath());
            } else {
                System.out.println("Errore nella creazione della cartella.");
            }
        } else {
            System.out.println("La cartella esiste già.");
        }
    }

    /**
     * Avvia il salvataggio asincrono del profilo utente.
     */
    private void SaveProfile(String nome, String email, String psw) {
        new ThSaveProfile(nome, email, psw).start();
    }

    /**
     * Recupera una riga (email o nome) dal file di login, secondo un predicato.
     *
     * @param p predicato sulla stringa da restituire.
     * @return la stringa corrispondente, oppure vuota.
     */
    public String getElementoFileLogin(Predicate<String> p) {
        String stringa = "";
        try (Scanner s = new Scanner(new File("Credenziali/cookieCredenziali.txt"))) {
            if (s.hasNextLine()) {
                String riga1 = s.nextLine();
                if (!p.test("email"))
                    stringa = riga1;
                else if (s.hasNextLine())
                    stringa = s.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringa;
    }

    /**
     * Salva una serie di file MP3 nella libreria utente.
     *
     * @param files array di file da copiare.
     */
    public void SaveFile(File[] files) {
        File cartella = new File("CartelleFileMp3/" + nomeCartellaPrincipale);
        String path = cartella.getPath() + "/Libreria";

        if (cartella.list().length == 0) {
            System.out.println("cartella vuota");
            new File(path).mkdir();
        }

        for (File f : files) {
            if (f.exists() && f.isFile()) {
                File destinazione = new File(path + "/" + f.getName());
                try {
                    Files.copy(f.toPath(), destinazione.toPath());
                    System.out.println(testGenere(f));
                } catch (IOException e) {
                    System.err.println("Errore durante il salvataggio del file " + f.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    /**
     * Ottiene un file dalla directory attuale dato il nome.
     *
     * @param name nome del file.
     * @return il file, oppure null se non esiste.
     */
    public File getFileByName(String name) {
        File file = new File(Model.getActuallyDirectory() + name);
        return file.exists() ? file : null;
    }

    /**
     * Restituisce tutti i file audio nella directory attualmente selezionata.
     *
     * @return array di file.
     */
    public static File[] getAllFileSongs() {
        File directory = new File(Model.getActuallyDirectory());
        return directory.listFiles();
    }

    /**
     * Restituisce il path completo della directory attualmente selezionata.
     *
     * @return stringa con il percorso.
     */
    public static String getActuallyDirectory() {
        return "CartelleFileMp3/" + nomeCartellaPrincipale + "/" + nomeCartellaRiprodotta + "/";
    }

    /**
     * Rileva il genere musicale di un file tramite API.
     *
     * @param file file da analizzare.
     * @return stringa con il genere.
     */
    public String testGenere(File file) {
        return GenereApi.detectGenre(file);
    }

    // Getters e Setters

    public String getNomeCartellaRiprodotta() {
        return nomeCartellaRiprodotta;
    }

    public void setNomeCartellaRiprodotta(String nomeCartellaRiprodotta) {
        this.nomeCartellaRiprodotta = nomeCartellaRiprodotta;
    }

    public String getNomeCartellaPrincipale() {
        return nomeCartellaPrincipale;
    }

    public void setNomeCartellaPrincipale(String nomeCartellaPrincipale) {
        this.nomeCartellaPrincipale = nomeCartellaPrincipale;
    }

    /**
     * Genera una playlist filtrando i brani per genere e copiandoli in una nuova cartella.
     *
     * @param generiMusicali lista dei generi desiderati.
     * @param nome           nome della nuova playlist.
     */
    public void generaPLaylist(ArrayList<String> generiMusicali, String nome) {
        if (nome.equals("")) return;

        File newDirectory = new File("CartelleFileMp3/" + nomeCartellaPrincipale + "/" + nome);
        if (!newDirectory.exists())
            newDirectory.mkdir();

        File[] songs = new File("CartelleFileMp3/" + nomeCartellaPrincipale + "/Libreria").listFiles();
        for (File f : songs) {
            if (IsGenreOfSong(generiMusicali, f)) {
                try {
                    Files.copy(f.toPath(), new File(newDirectory, f.getName()).toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Verifica se una canzone appartiene a uno dei generi desiderati.
     *
     * @param generiMusicali lista dei generi da confrontare.
     * @param song           file della canzone.
     * @return true se appartiene, false altrimenti.
     */
    private boolean IsGenreOfSong(ArrayList<String> generiMusicali, File song) {
        String genereCanzone = GenereApi.detectGenre(song);
        for (String s : generiMusicali)
            if (genereCanzone.equals(s))
                return true;
        return false;
    }
}
