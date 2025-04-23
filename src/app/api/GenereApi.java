import java.io.File;
import java.io.IOException;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class GenereApi {

	public String detectGenre(File mp3File) {
		try {
			Mp3File mp3 = new Mp3File(mp3File.getPath());
			String genre = "Sconosciuto";
			
			// Prova a leggere il genere dai tag ID3v2 (più recenti)
			if (mp3.hasId3v2Tag()) {
				ID3v2 id3v2Tag = mp3.getId3v2Tag();
				genre = id3v2Tag.getGenreDescription();
				if (genre != null && !genre.isEmpty()) {
					return genre;
				}
			}
			
			// Se non ha funzionato, prova con i tag ID3v1 (più vecchi)
			if (mp3.hasId3v1Tag()) {
				ID3v1 id3v1Tag = mp3.getId3v1Tag();
				genre = id3v1Tag.getGenreDescription();
				if (genre != null && !genre.isEmpty()) {
					return genre;
				}
			}
			
			// Se non è stato trovato nessun genere o c'è stato un problema
			return "Sconosciuto";

		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
			System.err.println("Errore durante la lettura dei metadati: " + e.getMessage());
			return "Sconosciuto";
		}
	}
}