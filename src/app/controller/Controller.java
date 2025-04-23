package app.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import app.Thread.*;
import app.model.Model;
import app.model.Mp3Player;
import app.view.*;

public class Controller {
    private Model model;
    private Mp3Player player;
    private FrameLogin frame;
    private PanelPlayer panelPlayer;

    public Controller(Model m) {
        model = m;        
        player = new Mp3Player();
        frame = new FrameLogin(this);
    }
    
    public void start() {
        File mainDir = new File("CartelleFileMp3"); // crea la cartella principale
        if (!mainDir.exists()) {
            boolean success = mainDir.mkdir();
            if (success) {
                System.out.println("Creata cartella principale: " + mainDir.getPath());
            } else {
                System.out.println("Impossibile creare la cartella principale: " + mainDir.getPath());
            }
        }
        
        frame.setVisible(true);
    }
            
    public boolean login(String nome, String email, String psw) {
        return UtenteEsiste(nome, email, psw);
    }    
    
    public void RegistrazioneUtente(String nome, String email, String psw) {
        new ThRegistraUtente(nome,email,psw).start();
    }    

    private boolean UtenteEsiste(String nome, String email, String psw) {
        return model.IsUtenteEsistente(nome, email, psw);
    }
    
    public Boolean coockieLogin() {
        if(new File("Credenziali/").listFiles().length>0)
            return true;
        return false;
    }

    public String getElementoFileLogin(Predicate<String> p) {
        return model.getElementoFileLogin(p);
    }
    
    public void logout() {
        ThLogout logout = new ThLogout();
        logout.start();
    }
    
    public void setPlaylistRiprodotta(String nomeplaylistRiprodotta) {
        model.setNomeCartellaRiprodotta(nomeplaylistRiprodotta);
        // Carica la playlist nel player
        player.loadPlaylist(model.getNomeCartellaPrincipale(), nomeplaylistRiprodotta);
    }
    
    public void setCartellaPrincipale(String nomeCartella) {
        model.setNomeCartellaPrincipale(nomeCartella);
        new ThCreaCartellaPlaylist(nomeCartella).start();
    }
    
    public ArrayList<String> getElencoCartelleUtente(){
        return model.getElencoCartelleUtente();
    }
    
    public ArrayList<String> getElencoCanzoniCartellaRiprodotta(){
        return model.getElencoCanzoniCartellaRiprodotta();
    }
    
    public String togliTXTtoCanzone(String s) {
        return model.togliEstensione(s, c -> c!='.');
    }
    
    public void playSong() {
        player.play();
        if(panelPlayer != null) {
            panelPlayer.updateCurrentSong(player.getCurrentSongName());
        }
    }
    
    public void pauseSong() {
        player.pause();
    }
    
    public void nextSong() {
        player.next();
        if(panelPlayer != null) {
            panelPlayer.updateCurrentSong(player.getCurrentSongName());
        }
    }
    
    public void previousSong() {
        player.previous();
        if(panelPlayer != null) {
            panelPlayer.updateCurrentSong(player.getCurrentSongName());
        }
    }
    
    public void playSongByName(String songName) {
        // Costruisci il percorso della canzone
        String songPath = "CartelleFileMp3/" + model.getNomeCartellaPrincipale() + "/" + 
                         model.getNomeCartellaRiprodotta() + "/" + songName;
        player.loadSong(songPath);
        player.play();
        
        if(panelPlayer != null) {
            panelPlayer.updateCurrentSong(songName);
        }
    }
    
    public void setPanelPlayer(PanelPlayer panel) {
        this.panelPlayer = panel;
    }
    
    public void AddFileMp3() {
        // Assicurati che il nome dell'utente sia impostato
        if (model.getNomeCartellaPrincipale() == null || model.getNomeCartellaPrincipale().isEmpty()) {
            String nomeUtente = getElementoFileLogin(t -> t.equals("nome"));
            if (nomeUtente != null && !nomeUtente.isEmpty()) {
                model.setNomeCartellaPrincipale(nomeUtente);
                System.out.println("Nome utente impostato a: " + nomeUtente);
            } else {
                System.out.println("Errore: impossibile determinare il nome utente");
                return;
            }
        }

        // Crea il selettore di file
        JFileChooser selettore = new JFileChooser();
        selettore.setMultiSelectionEnabled(true);
        selettore.setFileFilter(new FileNameExtensionFilter("File MP3", "mp3"));

        // Mostra la finestra di selezione
        int risultato = selettore.showOpenDialog(frame);
        
        if (risultato == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = selettore.getSelectedFiles();
            System.out.println("File selezionati: " + selectedFiles.length);
            model.SaveFile(selectedFiles);
            
            // Dopo aver caricato i file, ricarica la playlist
            if (model.getNomeCartellaRiprodotta() != null && !model.getNomeCartellaRiprodotta().isEmpty()) {
                player.loadPlaylist(model.getNomeCartellaPrincipale(), model.getNomeCartellaRiprodotta());
                System.out.println("Playlist ricaricata dopo aver aggiunto nuovi file");
            }
        } else {
            System.out.println("Selezione file annullata.");
        }
    }
}