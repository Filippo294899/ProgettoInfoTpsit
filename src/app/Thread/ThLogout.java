package app.Thread;

import java.io.File;

public class ThLogout extends Thread{
	public void run() {
		File file = new File("Credenziali/cookieCredenziali.txt");
	    file.delete();
	}

}
