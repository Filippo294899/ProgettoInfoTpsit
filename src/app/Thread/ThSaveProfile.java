package app.Thread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;


public class ThSaveProfile extends Thread{
	private String nome;
	private String email;
	private String psw;
	
	
	
	public ThSaveProfile(String nome, String email, String psw) {
		super();
		this.nome = nome;
		this.email = email;
		this.psw = psw;
	}



	public void run(){
		File file = new File("Credenziali/cookieCredenziali.txt");
		
		if(file.exists())
			return;
		
		try {
			if(!file.createNewFile()) {
				System.out.println("errore nella creazione del file login");
				Thread.currentThread().stop();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try (FileWriter writer = new FileWriter(file)) {
			writer.write(nome+"\n");
			writer.write(email+"\n");
			writer.write(psw+"\n");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
