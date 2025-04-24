package app.riproduzioneMp3;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.function.Predicate;

import app.Thread.ThPlaySong;
import app.model.Model;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.media.Media;
import uk.co.caprica.vlcj.media.MediaEventListener;
import uk.co.caprica.vlcj.media.MediaParsedStatus;
import uk.co.caprica.vlcj.media.MediaRef;
import uk.co.caprica.vlcj.media.Meta;
import uk.co.caprica.vlcj.media.Picture;
import uk.co.caprica.vlcj.media.TrackType;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.base.State;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;


/*
 quando fai stop e rilasci il lock per start canzone, se ci sono de thread in coda parte lo stesso
 stop a volte non va
 da sistemare 
  */
public class RiproduzioneMp3_VLCJ{ // con javafx
	private static ArrayList<String> songs = new ArrayList<String>();
	private static Integer idxCurrentSong = null;
	private static String currentSong = null;
	private static MediaPlayer mediaPlayer = null;
	private static boolean isMediaPLayerStopped = false;
	private static Semaphore mutexPlay = new Semaphore(1);
	private static final MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();

	public static void addSong(String canzone) {
		songs.add(canzone);
		if (idxCurrentSong == null) {
			idxCurrentSong = 0;
			setCurrentSong();
		}

	}

	public static void coda(Predicate<String> p) {
		if (p.test("<<")) {
			System.out.println("indietro....");
			if (songs.size() <= 1 || (idxCurrentSong - 1) < 0)
				return;
			idxCurrentSong -= 1;
		} else if (p.test(">>")) {
			System.out.println("skip....");
			if (songs.size() <= 1 || songs.size() < (idxCurrentSong + 2))
				return;
			idxCurrentSong += 1;
		}
		setCurrentSong();

		isMediaPLayerStopped = false;
		if (isPlaying()) {
			mediaPlayer.controls().stop();;
			new ThPlaySong().start();
			mutexPlay.release();
		}
	}

	public static void play() {
		try {
			mutexPlay.acquire();

			System.out.println("play...." + currentSong);

			File fileAudio = new File(Model.getActuallyDirectory() + currentSong);
			Media media = mediaPlayerFactory.media().newMedia(fileAudio.toURI().toString());

			if (!isMediaPLayerStopped) {
				mediaPlayer = mediaPlayerFactory.mediaPlayers().newMediaPlayer();
				mediaPlayer.media().play(fileAudio.toURI().toString());
			}

			mediaPlayer.controls().play();;
			isMediaPLayerStopped = false;

			mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventListener() {

				
				@Override
				public void finished(MediaPlayer arg0) {
					System.out.println("Canzone terminata.");
					if (songs.size() > (idxCurrentSong + 2)) {
						idxCurrentSong += 1;
						setCurrentSong();
					} else
						mediaPlayer.controls().stop();
					mutexPlay.release();
					
				}

				@Override
				public void audioDeviceChanged(MediaPlayer arg0, String arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void backward(MediaPlayer arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void buffering(MediaPlayer arg0, float arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void chapterChanged(MediaPlayer arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void corked(MediaPlayer arg0, boolean arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void elementaryStreamAdded(MediaPlayer arg0, TrackType arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void elementaryStreamDeleted(MediaPlayer arg0, TrackType arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void elementaryStreamSelected(MediaPlayer arg0, TrackType arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void error(MediaPlayer arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void forward(MediaPlayer arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void lengthChanged(MediaPlayer arg0, long arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mediaChanged(MediaPlayer arg0, MediaRef arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mediaPlayerReady(MediaPlayer arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void muted(MediaPlayer arg0, boolean arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void opening(MediaPlayer arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void pausableChanged(MediaPlayer arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void paused(MediaPlayer arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void playing(MediaPlayer arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void positionChanged(MediaPlayer arg0, float arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void scrambledChanged(MediaPlayer arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void seekableChanged(MediaPlayer arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void snapshotTaken(MediaPlayer arg0, String arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void stopped(MediaPlayer arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void timeChanged(MediaPlayer arg0, long arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void titleChanged(MediaPlayer arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void videoOutput(MediaPlayer arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void volumeChanged(MediaPlayer arg0, float arg1) {
					// TODO Auto-generated method stub
					
				}
				
			});

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized static void stop() {
		if (isPlaying()) {
			System.out.println("stop....");
			mediaPlayer.controls().stop();
			isMediaPLayerStopped = true;
			mutexPlay.release();
		} else
			System.out.println("nn in riproduzione");
	}

	private static void setCurrentSong() {
		currentSong = songs.get(idxCurrentSong);
	}

	private static boolean isPlaying() {
		return mediaPlayer != null && mediaPlayer.status().isPlaying();
	}

	public static String getStato() {
		if (isPlaying())
			return "In riproduzone " + Model.togliEstensione(currentSong, t -> t != '.') + "....";
		return "Nessuna canzone in riproduzione";
	}
}
