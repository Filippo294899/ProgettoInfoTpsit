package app.model;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Mp3Player {
    private Clip audioClip;
    private AudioInputStream audioStream;
    private String currentSongPath;
    private Long currentFrame;
    private boolean isPlaying = false;
    private String currentDirectory;
    private String currentPlaylist;
    private int currentSongIndex = 0;
    private String[] songList;

    public Mp3Player() {
        currentDirectory = "";
        currentPlaylist = "";
    }

    public void loadPlaylist(String directory, String playlist) {
        this.currentDirectory = directory;
        this.currentPlaylist = playlist;
        
        // Carica l'elenco delle canzoni
        File playlistDir = new File("CartelleFileMp3/" + directory + "/" + playlist + "/");
        if(playlistDir.exists() && playlistDir.isDirectory()) {
            songList = playlistDir.list((dir, name) -> name.toLowerCase().endsWith(".mp3"));
        }
    }

    public void loadSong(String songPath) {
        try {
            // Resetta lo stato del player
            if(audioClip != null) {
                audioClip.close();
            }
            if(audioStream != null) {
                audioStream.close();
            }
            
            File songFile = new File(songPath);
            currentSongPath = songPath;
            
            // Ottieni un AudioInputStream
            audioStream = AudioSystem.getAudioInputStream(songFile);
            
            // Ottieni un clip che può essere usato per riprodurre l'audio
            audioClip = AudioSystem.getClip();
            
            // Apri l'audio clip e carica i campioni dal AudioInputStream
            audioClip.open(audioStream);
            
            isPlaying = false;
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Errore nel caricamento del file: " + e.getMessage());
        }
    }

    public void play() {
        if (audioClip != null && !isPlaying) {
            audioClip.start();
            isPlaying = true;
        } else if (currentSongPath != null && audioClip == null) {
            // Se il clip non è stato caricato ma abbiamo un percorso, carica e riproduci
            loadSong(currentSongPath);
            // play();
        }
    }

    public void pause() {
        if (audioClip != null && isPlaying) {
            currentFrame = audioClip.getMicrosecondPosition();
            audioClip.stop();
            isPlaying = false;
        }
    }

    public void resume() {
        if (audioClip != null && !isPlaying) {
            audioClip.setMicrosecondPosition(currentFrame);
            audioClip.start();
            isPlaying = true;
        }
    }

    public void stop() {
        if (audioClip != null) {
            audioClip.stop();
            audioClip.close();
            isPlaying = false;
        }
    }

    public void next() {
        if (songList != null && songList.length > 0) {
            currentSongIndex = (currentSongIndex + 1) % songList.length;
            String nextSong = songList[currentSongIndex];
            String songPath = "CartelleFileMp3/" + currentDirectory + "/" + currentPlaylist + "/" + nextSong;
            loadSong(songPath);
            play();
        }
    }

    public void previous() {
        if (songList != null && songList.length > 0) {
            currentSongIndex = (currentSongIndex - 1 + songList.length) % songList.length;
            String prevSong = songList[currentSongIndex];
            String songPath = "CartelleFileMp3/" + currentDirectory + "/" + currentPlaylist + "/" + prevSong;
            loadSong(songPath);
            play();
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public String getCurrentSongName() {
        if (songList != null && currentSongIndex >= 0 && currentSongIndex < songList.length) {
            return songList[currentSongIndex];
        }
        return "";
    }
}