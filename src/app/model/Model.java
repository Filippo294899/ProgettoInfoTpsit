package app.model;

import java.io.File;
import java.util.ArrayList;

public class Model {
	private String nomeCartellaPrincipale;
	private String nomeCartellaRiprodotta;

	public Model() {
		nomeCartellaPrincipale="";
		nomeCartellaRiprodotta="";
	}
	
	public ArrayList<String> getElencoCartelleUtente(){
		ArrayList<String> s=new ArrayList<String>();
		File cartella = new File("CartelleFileMp3/"+nomeCartellaPrincipale+"/");
		for(String nomeFile : cartella.list())
			s.add(nomeFile);
		return s;
	}
	public ArrayList<String> getElencoCanzoniCartellaRiprodotta(){
		ArrayList<String> s=new ArrayList<String>();
		File canzoni=new File("CartelleFileMp3/"+nomeCartellaPrincipale+"/"+nomeCartellaRiprodotta+"/");
		for(String tit:canzoni.list())
			s.add(tit);
		return s;
	}
	public String togliTXTtoCanzone(String s) {
		String finalString="";
		for(int i=0;i<s.length();i++)
			if(s.charAt(i)!='.')
				finalString+=s.charAt(i);
			else
				break;
		return finalString;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public String getNomeCartellaRiprodotta() {
		return nomeCartellaRiprodotta;
	}

	public void setNomeCartellaRiprodotta(String nomeCartellaRiprodotta) {
		this.nomeCartellaRiprodotta = nomeCartellaRiprodotta;
	}
	public String getNomeCartellaPrincipale() {
		return nomeCartellaPrincipale;
	}

	public void setNomeCartellaPrincipale(String nomeCartellaPrincipale) {
		this.nomeCartellaPrincipale = nomeCartellaPrincipale;
	}
}
