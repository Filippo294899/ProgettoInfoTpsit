package app.Thread;

import app.riproduzioneMp3.RiproduzioneMp3;

public class ThStopSong extends Thread {

	
	public void run() {
		RiproduzioneMp3.stop();
	}
}
