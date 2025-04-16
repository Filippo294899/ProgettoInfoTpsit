package app.Thread;

import java.io.File;

public class ThCreaCartellaPlaylist extends Thread {
	private String nomecartella;
	public ThCreaCartellaPlaylist(String x) {
		nomecartella=x;
	}
	
	public void run() {
		File cartella = new File("CartelleFileMp3/"+nomecartella);

        if (!cartella.exists()) 
            cartella.mkdir();
	
     }
}

