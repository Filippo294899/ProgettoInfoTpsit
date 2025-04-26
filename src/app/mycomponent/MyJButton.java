package app.mycomponent;

import javax.swing.JButton;

public class MyJButton extends JButton{
	private int idx;
	
	public MyJButton(String nome) {
		super(nome);
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	

}
