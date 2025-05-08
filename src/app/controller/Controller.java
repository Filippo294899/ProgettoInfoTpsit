package app.controller;

import app.Thread.*;
import app.api.GenereApi;
import app.model.Model;
import app.riproduzioneMp3.RiproduzioneMp3;
import app.view.*;
import java.io.File;
import java.util.ArrayList;
import java.util.function.Predicate;

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
		return model.IsUtenteEsistente(nome, email, psw);
	}	
	public void RegistrazioneUtente(String nome, String email, String psw) {
		new ThRegistraUtente(nome,email,psw).start();
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
		return Model.togliEstensione(s,c-> c!='.');
	}
	public void AddFileMp3(File[] files) {
		model.SaveFile(files);
	}
	
	
	public void AccodaCanzone(String canzone) {
		RiproduzioneMp3.addSong(canzone);
	}
	public void SkipCanzone() {
		RiproduzioneMp3.coda(t -> t.equals(">>"));
	}
	public void ScalaCanzone() {
		RiproduzioneMp3.coda(t -> t.equals("<<"));;
	}
	public void playSong() {
		new ThPlaySong().start();
	}
	public void stopSong() {
		new ThStopSong().start();
	}
	public String getStatoCanzone() {
		return RiproduzioneMp3.getStato();
	}
	public String getTimeSong() {
		return RiproduzioneMp3.getTimeSong();
	}
	public int getLenghtSong() {
		return RiproduzioneMp3.getLenghtSong();
	}
	public void setTimeSong(int time) {
		RiproduzioneMp3.setTimeSong(time);
	}
	
	public ArrayList<String> getCodaCanzoni(){
		return RiproduzioneMp3.getCoda();
	}
	public int getCurrentSong(){
		return RiproduzioneMp3.getCurrentSongIdx();
	}
	public boolean IsCodaExist() {
		return RiproduzioneMp3.IsCodaExist();
	}
	public void setCurrentSong(int IDXsong) {
		RiproduzioneMp3.setCurrentSong(IDXsong);
	}
	public String getGenereCanzone(){
		return GenereApi.detectGenre(model.getFileByName(RiproduzioneMp3.getNameCurrentSong()));
	}
	public String[] getAllgeneriMusicali(){
		return GenereApi.getAllGenre();
	}
	public void generaPlaylist(ArrayList<String> generi,String nome) {
		model.generaPLaylist(generi,nome);
	}
	public File getSongFile() {
		return model.getFileByName(RiproduzioneMp3.getNameCurrentSong());
	}
	
}