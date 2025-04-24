package app.controller;

import app.Thread.*;
import app.model.Model;
import app.riproduzioneMp3.RiproduzioneMp3_javaFX;
import app.view.*;
import java.io.File;
import java.util.ArrayList;
import java.util.function.Predicate;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controller {
	private Model model;
	private FrameLogin frame;
	
	
	public Controller(Model m) {
		model=m;		
		frame=new FrameLogin(this);
	}
	public void start() {
		frame.setVisible(true);
	}
			
	public boolean login(String nome, String email, String psw) {
		return UtenteEsiste(nome, email, psw);
	}	
	public void RegistrazioneUtente(String nome, String email, String psw) {
		new ThRegistraUtente(nome,email,psw).start();
	}	

	private boolean UtenteEsiste(String nome, String email, String psw) {
		return model.IsUtenteEsistente(nome, email, psw);
	}
	
	public Boolean cookieLogin() {
		if(new File("Credenziali/").listFiles().length>0)
			return true;
		return false;
	}

    public String getElementoFileLogin(Predicate<String> p) {
    	return model.getElementoFileLogin(p);
    }
	
	public void logout() {
	    ThLogout logout=new ThLogout();
	    logout.start();
	}
	
	public void setPlaylistRiprodotta(String nomeplaylistRiprodotta) {
		model.setNomeCartellaRiprodotta(nomeplaylistRiprodotta);
	}
	
	public void setCartellaPrincipale(String nomeCartella) {
		model.setNomeCartellaPrincipale(nomeCartella);
		new ThCreaCartellaPlaylist(nomeCartella).start();
	}
	
	public ArrayList<String> getElencoCartelleUtente(){
		return model.getElencoCartelleUtente();
	}
	public ArrayList<String> getElencoCanzoniCartellaRiprodotta(){
		return model.getElencoCanzoniCartellaRiprodotta();
	}
	public String togliTXTtoCanzone(String s) {
		return model.togliEstensione(s,c-> c!='.');
	}
	public void AddFileMp3() {
        // Crea il selettore di file
        JFileChooser selettore = new JFileChooser();
        selettore.setMultiSelectionEnabled(true);
        selettore.setFileFilter(new FileNameExtensionFilter("File audio (.mp3)", "mp3"));	//da cambiare ad mp3 senno non va

        // Mostra la finestra di selezione
        int risultato = selettore.showOpenDialog(frame);
        
        if (risultato == JFileChooser.APPROVE_OPTION) 
        	model.SaveFile(selettore.getSelectedFiles());	//ritorna array file
        else 
            System.out.println("Selezione file annullata.");
            
    
	}
	
	
	public void AccodaCanzone(String canzone) {
		RiproduzioneMp3_javaFX.addSong(canzone);
	}
	public void SkipCanzone() {
		RiproduzioneMp3_javaFX.coda(t -> t.equals(">>"));
	}
	public void ScalaCanzone() {
		RiproduzioneMp3_javaFX.coda(t -> t.equals("<<"));;
	}
	public void playSong() {
		new ThPlaySong().start();
	}
	public void stopSong() {
		new ThStopSong().start();
	}
	public String getStatoCanzone() {
		return RiproduzioneMp3_javaFX.getStato();
	}
	
	
	
	
}