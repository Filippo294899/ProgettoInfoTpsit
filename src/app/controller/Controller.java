package app.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import app.Thread.*;
import app.model.Model;
import app.view.*;

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
	
	public Boolean coockieLogin() {
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
        selettore.setFileFilter(new FileNameExtensionFilter("File di testo (.txt)", "txt"));	//da cambiare ad mp3 senno non va

        // Mostra la finestra di selezione
        int risultato = selettore.showOpenDialog(frame);
        
        if (risultato == JFileChooser.APPROVE_OPTION) 
        	model.SaveFile(selettore.getSelectedFiles());	//ritorna array file
        else 
            System.out.println("Selezione file annullata.");
            
    
	}
}
