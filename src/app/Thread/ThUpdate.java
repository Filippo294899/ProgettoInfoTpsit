package app.Thread;

import javax.swing.JPanel;

import app.view.PannelMain;

public class ThUpdate extends Thread{
	PannelMain x;
	
	public ThUpdate(PannelMain x) {
		this.x=x;
	}
	
	public void run() {
		try {
			while(x.isVisible()) {
				x.update();
				sleep(1000);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
