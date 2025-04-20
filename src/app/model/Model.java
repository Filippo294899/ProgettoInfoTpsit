package app.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;

import app.Thread.ThSaveProfile;

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
	public String togliEstensione(String s,Predicate<Character> p) {
		String finalString="";
		for(int i=0;i<s.length();i++)
			if(/*s.charAt(i)!='.'*/p.test(s.charAt(i)))
				finalString+=s.charAt(i);
			else
				break;
		return finalString;
	}
	@SuppressWarnings("resource")
	public boolean IsUtenteEsistente(String nome, String email, String psw) {
		File cartella=new File("UtentiRegistrati/");
		for(String s: cartella.list()) {
	        try {
	            Scanner scanner = new Scanner(new File("UtentiRegistrati/"+s));
	            
	            boolean Briga1,Briga2,Briga3;
	            
	            String riga1 = scanner.nextLine();
	            if(riga1.equals(nome))
	            	Briga1=true;
	            else
	            	Briga1=false;
	            
	            String riga2 = scanner.nextLine();	
	            if(riga2.equals(email))
	            	Briga2=true;
		        else
		            Briga2=false;
		            
	            
	            String riga3 = scanner.nextLine();	
	            if(riga2.equals(psw))
	            	Briga3=true;
		        else
		            Briga3=false;
		            
	            if(Briga1&&Briga2&&Briga3) {
	            	SaveProfile(nome, email, psw);
	            	return true;
	            }

	            scanner.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}

		return false;
	}
	private void SaveProfile(String nome, String email, String psw) {
		new ThSaveProfile(nome, email, psw).start();;
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
	
	
	public void SaveFile(File[] files) {
		// da implementare 
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
