package app.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;

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
	
	@SuppressWarnings("resource")
	private boolean UtenteEsiste(String nome, String email, String psw) {
		File cartella=new File("UtentiRegistrati/");
		for(String s: cartella.list()) {
	        try {
	            Scanner scanner = new Scanner(new File("UtentiRegistrati/"+s));
	            
	            String riga1 = scanner.nextLine();
	            if(!riga1.equals(nome)) return false;
	            String riga2 = scanner.nextLine();	
	            if(!riga2.equals(email)) return false;
	            String riga3 = scanner.nextLine();	
	            if(!riga2.equals(psw)) return false;
	             
	            scanner.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}

		
		SaveProfile(nome, email, psw);
		return true;
	}
	
	//gestione file
	private void SaveProfile(String nome, String email, String psw) {
		ThSaveProfile SaveProfile=new ThSaveProfile(nome, email, psw);
		SaveProfile.start();
	}
	
	public Boolean coockieLogin() {
		if(new File("Credenziali/").listFiles().length>0)
			return true;
		return false;
	}

    public String getElementoFileLogin(Predicate<String> p) {
        String stringa = "";
        try {
            Scanner s = new Scanner(new File("Credenziali/cookieCredenziali.txt"));
            
            if (s.hasNextLine()) {
                String riga1 = s.nextLine();
                if (!p.test("email")) 
                    stringa = riga1;
                else 
                    if (s.hasNextLine()) 
                        stringa = s.nextLine();
                    
                }
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringa; 
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

}
