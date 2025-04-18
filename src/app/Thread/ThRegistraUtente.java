package app.Thread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ThRegistraUtente extends Thread{
	private String nome;
	private String email;
	private String psw;
	private String path;
	
	public ThRegistraUtente(String nome, String email, String psw) {
		path="UtentiRegistrati/Utente";
		this.nome=nome;
		this.email=email;
		this.psw=psw;
	}
	
	public void run() {
		File fileUtente=new File(path+nome+".txt");
		
		if(fileUtente.exists())
			return;
		
		try {
			if(!fileUtente.createNewFile()) {
				System.out.println("errore nella creazione del file login");
				Thread.currentThread().stop();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try (FileWriter writer = new FileWriter(fileUtente)) {
			writer.write(nome+"\n");
			writer.write(email+"\n");
			writer.write(psw+"\n");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
