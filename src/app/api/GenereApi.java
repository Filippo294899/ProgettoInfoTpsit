package app.api;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import app.model.Model;
import app.riproduzioneMp3.RiproduzioneMp3;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenereApi {

	static {
		// Silenzia i log di Jaudiotagger
		Logger.getLogger("org.jaudiotagger").setLevel(Level.SEVERE);
	}

	private static final String[] ID3_GENRES = { "Blues", "Classic Rock", "Country", "Dance", "Disco", "Funk", "Grunge",
			"Hip-Hop", "Jazz", "Metal", "New Age", "Oldies", "Other", "Pop", "R&B", "Rap", "Reggae", "Rock", "Techno",
			"Industrial", "Ballad","Sconosciuto"
			// Aggiungi altri se necessario (fino a 147)
	};

	public static String detectGenre(File mp3File) {
		try {
			AudioFile audioFile = AudioFileIO.read(mp3File);
			Tag tag = audioFile.getTag();

			if (tag != null) {
				String genre = tag.getFirst(FieldKey.GENRE);
				if (genre != null && !genre.isEmpty()) {
					// Controlla se è nel formato "(numero)"
					if (genre.matches("\\(\\d+\\)")) {
						int index = Integer.parseInt(genre.replaceAll("[()]", ""));
						if (index >= 0 && index < ID3_GENRES.length) {
							return ID3_GENRES[index];
						}
					}
					return genre;
				}
			}

			return "Sconosciuto";

		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
				| InvalidAudioFrameException e) {
			System.err.println("Errore durante la lettura dei metadati: " + e.getMessage());
			return "Sconosciuto";
		}
	}
	public static String[] getAllGenre(){
		return ID3_GENRES;
	}
}
