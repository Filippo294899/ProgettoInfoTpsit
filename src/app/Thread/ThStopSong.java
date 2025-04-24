package app.Thread;

import app.riproduzioneMp3.RiproduzioneMp3_javaFX;

public class ThStopSong extends Thread {

	
	public void run() {
		RiproduzioneMp3_javaFX.stop();
	}
}
