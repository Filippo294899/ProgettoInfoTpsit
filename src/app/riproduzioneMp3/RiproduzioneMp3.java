package app.riproduzioneMp3;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.function.Predicate;

import app.Thread.ThPlaySong;
import app.model.Model;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
/*
 quando fai stop e rilasci il lock per start canzone, se ci sono de thread in coda parte lo stesso
 stop a volte non va
 da sistemare 
  */
public class RiproduzioneMp3 {
	private static ArrayList<String> songs=new ArrayList<String>();
	private static Integer idxCurrentSong=null;
	private static String currentSong="";
	private static MediaPlayer mediaPlayer=null;
	private static boolean javafxInizializzato = false;
	private static boolean isMediaPLayerStopped = false;
	private static Semaphore mutexPlay =new Semaphore(1);
	
    private static void inizializzaToolkit() {
    	//applicazione swing non javafx
        if (!javafxInizializzato) {
            Platform.startup(() -> {});
            javafxInizializzato = true;
        }
    }
    
	public static void addSong(String canzone) {
		songs.add(canzone);
		if(idxCurrentSong==null) {
			idxCurrentSong=0;
			setCurrentSong();
		}
		
	}
	public static void coda(Predicate<String> p) {
		if(p.test("<<")) {					
			System.out.println("indietro....");
			if(songs.size()<=1||(idxCurrentSong-1)<0)
				return;
			idxCurrentSong-=1;
		}else if(p.test(">>")) {
			System.out.println("skip....");
			if(songs.size()<=1||songs.size()<(idxCurrentSong+2))
				return;
			idxCurrentSong+=1;
		}
		setCurrentSong();

		isMediaPLayerStopped=false;
		if(isPlaying()) {
			mediaPlayer.stop();
			new ThPlaySong().start();
			mutexPlay.release();
		}
	}
	public static void play() {
		 try {
			mutexPlay.acquire();
			
			System.out.println("play...." +currentSong);
			
			inizializzaToolkit();
			File fileAudio = new File(Model.getActuallyDirectory()+currentSong);
            Media media = new Media(fileAudio.toURI().toString());

            if(!isMediaPLayerStopped)
            	mediaPlayer = new MediaPlayer(media);
            
            mediaPlayer.play();
            isMediaPLayerStopped=false;
            
            mediaPlayer.setOnEndOfMedia(() -> {	
                System.out.println("Canzone terminata.");
                if(songs.size()>=(idxCurrentSong+2)) {
                	idxCurrentSong+=1;
                    setCurrentSong();
                    try {
						Thread.currentThread().sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                    new ThPlaySong().start();
                }else
                	mediaPlayer.dispose();
                mutexPlay.release();
            });
            
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public synchronized static void stop() {
		inizializzaToolkit();
		
		 if (isPlaying()) {
				System.out.println("stop....");
				mediaPlayer.pause();
				isMediaPLayerStopped=true;
				mutexPlay.release();			
		 }else
			 System.out.println("nn in riproduzione");
	}

	private static void setCurrentSong() {
		currentSong=songs.get(idxCurrentSong);
	}
	public static void setCurrentSong(String song) {
		for(String s:songs) 
			if(s.equals(song)) {
				idxCurrentSong=getIDbyName(s);
				setCurrentSong();
				isMediaPLayerStopped=false;
				new ThPlaySong().start();
				return;
			}
	}
	private static int getIDbyName(String songName) {
		for(int i=0;i<songs.size();i++)
			if(songs.get(i).equals(songName))
				return i;
		return idxCurrentSong;
	}
	private static boolean isPlaying() {
		return mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
	}
	
	public static String getStato() {
		if(isPlaying())
			return "In riproduzone "+Model.togliEstensione(currentSong,t -> t!='.') +"....";
		if(isMediaPLayerStopped)
			return "Canzone in pausa";
		return "Nessuna canzone in riproduzione";
	}
	public static String getTimeSong() {
		if(isPlaying()||isMediaPLayerStopped)
			return String.format("%.2f", mediaPlayer.getCurrentTime().toMinutes());
		return "0";
	}
	public static int getLenghtSong() {
		if(isPlaying()||isMediaPLayerStopped)
			return (int)mediaPlayer.getTotalDuration().toSeconds();
		return 1;
	}
	public static void setTimeSong(int time) {
		if(isPlaying())
			mediaPlayer.seek(Duration.seconds(time));
	}
	public static ArrayList<String> getCoda(){
		return songs;
	}
	public static String getCurrentSong() {
		return currentSong;
	}
	public static boolean IsCodaExist() {
		return songs.size()>0;
	}

}
