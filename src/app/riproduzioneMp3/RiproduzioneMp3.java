package app.riproduzioneMp3;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.sound.sampled.*;
import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class RiproduzioneMp3 {
	private static Queue<String> songQueue = new LinkedList<>();
	private static ArrayList<String> playedSongs = new ArrayList<>();
	private static Player player;
	private static boolean isPlaying = false;
	private static int currentSongIndex = -1;
	private static Thread playerThread;
	
	public static void addSong(String songPath) {
		songQueue.add(songPath);
		if (!isPlaying) {
			playNextSong();
		}
	}
	
	public static void skip() {
		stopPlayer();
		playNextSong();
	}
	
	public static void tornaIndietro() {
		if (currentSongIndex > 0) {
			stopPlayer();
			currentSongIndex--;
			String previousSong = playedSongs.get(currentSongIndex);
			playSong(previousSong);
		}
	}
	
	public static void play() {
		if (!isPlaying && currentSongIndex >= 0) {
			String currentSong = playedSongs.get(currentSongIndex);
			playSong(currentSong);
		} else if (!isPlaying && !songQueue.isEmpty()) {
			playNextSong();
		}
	}
	
	public static void stop() {
		stopPlayer();
	}
	
	private static void playNextSong() {
		if (!songQueue.isEmpty()) {
			String songPath = songQueue.poll();
			playedSongs.add(songPath);
			currentSongIndex = playedSongs.size() - 1;
			playSong(songPath);
		}
	}
	
	private static void playSong(String songPath) {
		stopPlayer();
		
		playerThread = new Thread(() -> {
			try {
				FileInputStream fileInputStream = new FileInputStream(songPath);
				player = new Player(fileInputStream);
				isPlaying = true;
				player.play();
				
				// When song finishes naturally
				isPlaying = false;
				playNextSong();
			} catch (Exception e) {
				System.out.println("Error playing file: " + e.getMessage());
				isPlaying = false;
			}
		});
		
		playerThread.start();
	}
	
	private static void stopPlayer() {
		isPlaying = false;
		if (player != null) {
			player.close();
			player = null;
		}
		if (playerThread != null) {
			playerThread.interrupt();
			playerThread = null;
		}
	}
}