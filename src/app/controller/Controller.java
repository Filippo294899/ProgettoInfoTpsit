package app.controller;

import app.Thread.*;
import app.api.GenereApi;
import app.model.Model;
import app.riproduzioneMp3.RiproduzioneMp3;
import app.view.*;
import java.io.File;
import java.util.ArrayList;
import java.util.function.Predicate;
/**
 * La classe {@code Controller} gestisce la logica di controllo dell'applicazione,
 * fungendo da intermediario tra la vista (GUI), il modello (dati) e i thread di esecuzione.
 * 
 * <p>Si occupa di autenticazione, gestione delle playlist, riproduzione musicale e 
 * manipolazione dei file MP3.
 */
public class Controller {
    private Model model;
    private FrameLogin frame;

    /**
     * Costruttore della classe {@code Controller}.
     *
     * @param m Il modello da utilizzare per la logica di business e gestione dati.
     */
    public Controller(Model m) {
        model = m;
        frame = new FrameLogin(this);
    }

    /**
     * Avvia l'applicazione mostrando il frame di login.
     */
    public void start() {
        frame.setVisible(true);
    }

    /**
     * Esegue il login dell'utente verificando le credenziali.
     *
     * @param nome  Il nome dell'utente.
     * @param email L'email dell'utente.
     * @param psw   La password dell'utente.
     * @return {@code true} se l'utente esiste, altrimenti {@code false}.
     */
    public boolean login(String nome, String email, String psw) {
        return UtenteEsiste(nome, email, psw);
    }

    /**
     * Registra un nuovo utente in un thread separato.
     *
     * @param nome  Il nome dell'utente.
     * @param email L'email dell'utente.
     * @param psw   La password dell'utente.
     */
    public void RegistrazioneUtente(String nome, String email, String psw) {
        new ThRegistraUtente(nome, email, psw).start();
    }

    /**
     * Verifica se l'utente esiste nel sistema.
     *
     * @param nome  Il nome dell'utente.
     * @param email L'email dell'utente.
     * @param psw   La password dell'utente.
     * @return {@code true} se l'utente esiste, altrimenti {@code false}.
     */
    private boolean UtenteEsiste(String nome, String email, String psw) {
        return model.IsUtenteEsistente(nome, email, psw);
    }

    /**
     * Controlla se sono presenti credenziali salvate (cookie login).
     *
     * @return {@code true} se ci sono file di credenziali salvate, altrimenti {@code false}.
     */
    public Boolean cookieLogin() {
        return new File("Credenziali/").listFiles().length > 0;
    }

    /**
     * Restituisce un elemento dalle credenziali salvate che soddisfa un certo predicato.
     *
     * @param p Il predicato da applicare agli elementi del file.
     * @return L'elemento che soddisfa il predicato.
     */
    public String getElementoFileLogin(Predicate<String> p) {
        return model.getElementoFileLogin(p);
    }

    /**
     * Esegue il logout avviando il relativo thread.
     */
    public void logout() {
        new ThLogout().start();
    }

    /**
     * Imposta il nome della playlist attualmente riprodotta.
     *
     * @param nomeplaylistRiprodotta Il nome della playlist.
     */
    public void setPlaylistRiprodotta(String nomeplaylistRiprodotta) {
        model.setNomeCartellaRiprodotta(nomeplaylistRiprodotta);
    }

    /**
     * Imposta la cartella principale delle playlist e avvia la creazione della directory.
     *
     * @param nomeCartella Il nome della cartella principale.
     */
    public void setCartellaPrincipale(String nomeCartella) {
        model.setNomeCartellaPrincipale(nomeCartella);
        new ThCreaCartellaPlaylist(nomeCartella).start();
    }

    /**
     * Restituisce l'elenco delle cartelle delle playlist dell'utente.
     *
     * @return Lista di nomi delle cartelle.
     */
    public ArrayList<String> getElencoCartelleUtente() {
        return model.getElencoCartelleUtente();
    }

    /**
     * Restituisce l'elenco delle canzoni della playlist corrente.
     *
     * @return Lista dei nomi dei file canzoni.
     */
    public ArrayList<String> getElencoCanzoniCartellaRiprodotta() {
        return model.getElencoCanzoniCartellaRiprodotta();
    }

    /**
     * Rimuove l'estensione .txt da una stringa rappresentante una canzone.
     *
     * @param s Il nome del file.
     * @return Il nome del file senza estensione.
     */
    public String togliTXTtoCanzone(String s) {
        return Model.togliEstensione(s, c -> c != '.');
    }

    /**
     * Salva uno o più file MP3.
     *
     * @param files Array di file da salvare.
     */
    public void AddFileMp3(File[] files) {
        model.SaveFile(files);
    }

    /**
     * Aggiunge una canzone alla coda di riproduzione.
     *
     * @param canzone Il nome della canzone.
     */
    public void AccodaCanzone(String canzone) {
        RiproduzioneMp3.addSong(canzone);
    }

    /**
     * Salta alla canzone successiva nella coda.
     */
    public void SkipCanzone() {
        RiproduzioneMp3.coda(t -> t.equals(">>"));
    }

    /**
     * Torna alla canzone precedente nella coda.
     */
    public void ScalaCanzone() {
        RiproduzioneMp3.coda(t -> t.equals("<<"));
    }

    /**
     * Avvia la riproduzione della canzone.
     */
    public void playSong() {
        new ThPlaySong().start();
    }

    /**
     * Ferma la riproduzione della canzone.
     */
    public void stopSong() {
        new ThStopSong().start();
    }

    /**
     * Restituisce lo stato attuale della riproduzione (in riproduzione, fermo, ecc.).
     *
     * @return Lo stato come stringa.
     */
    public String getStatoCanzone() {
        return RiproduzioneMp3.getStato();
    }

    /**
     * Restituisce il tempo attuale di riproduzione della canzone.
     *
     * @return Tempo trascorso in formato stringa.
     */
    public String getTimeSong() {
        return RiproduzioneMp3.getTimeSong();
    }

    /**
     * Restituisce la lunghezza della canzone in secondi.
     *
     * @return La durata della canzone.
     */
    public int getLenghtSong() {
        return RiproduzioneMp3.getLenghtSong();
    }

    /**
     * Imposta il tempo di riproduzione della canzone.
     *
     * @param time Il tempo in secondi.
     */
    public void setTimeSong(int time) {
        RiproduzioneMp3.setTimeSong(time);
    }

    /**
     * Restituisce la coda delle canzoni in riproduzione.
     *
     * @return Lista dei nomi delle canzoni.
     */
    public ArrayList<String> getCodaCanzoni() {
        return RiproduzioneMp3.getCoda();
    }

    /**
     * Restituisce l'indice della canzone attualmente in riproduzione.
     *
     * @return L'indice della canzone corrente.
     */
    public int getCurrentSong() {
        return RiproduzioneMp3.getCurrentSongIdx();
    }

    /**
     * Controlla se la coda di canzoni esiste.
     *
     * @return {@code true} se esiste una coda, altrimenti {@code false}.
     */
    public boolean IsCodaExist() {
        return RiproduzioneMp3.IsCodaExist();
    }

    /**
     * Imposta l'indice della canzone corrente da riprodurre.
     *
     * @param IDXsong L'indice della canzone.
     */
    public void setCurrentSong(int IDXsong) {
        RiproduzioneMp3.setCurrentSong(IDXsong);
    }

    /**
     * Rileva il genere musicale della canzone attualmente in riproduzione.
     *
     * @return Il genere rilevato oppure "Sconosciuto".
     */
    public String getGenereCanzone() {
        return GenereApi.detectGenre(model.getFileByName(RiproduzioneMp3.getNameCurrentSong()));
    }

    /**
     * Restituisce l'elenco di tutti i generi musicali conosciuti.
     *
     * @return Array di stringhe contenente i generi.
     */
    public String[] getAllgeneriMusicali() {
        return GenereApi.getAllGenre();
    }

    /**
     * Genera una nuova playlist basata su una lista di generi.
     *
     * @param generi Lista di generi musicali.
     * @param nome   Nome della nuova playlist.
     */
    public void generaPlaylist(ArrayList<String> generi, String nome) {
        model.generaPLaylist(generi, nome);
    }

    /**
     * Restituisce il file della canzone attualmente in riproduzione.
     *
     * @return Il file della canzone.
     */
    public File getSongFile() {
        return model.getFileByName(RiproduzioneMp3.getNameCurrentSong());
    }

    /**
     * Verifica se la canzone è ancora in riproduzione (non fermata).
     *
     * @return {@code true} se la canzone è in esecuzione, altrimenti {@code false}.
     */
    public boolean IsCanzoneNNstopped() {
        return !RiproduzioneMp3.IsCanzoneStopped();
    }
}
