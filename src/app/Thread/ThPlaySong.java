package app.Thread;

import app.riproduzioneMp3.RiproduzioneMp3;

public class ThPlaySong extends Thread {

	
	public void run() {
		RiproduzioneMp3.play();
	}
}
