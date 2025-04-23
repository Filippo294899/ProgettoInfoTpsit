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

public class GenereApi {

	public static String detectGenre(File mp3File) {
		try {
			AudioFile audioFile = AudioFileIO.read(mp3File);
			Tag tag = audioFile.getTag();
			
			if (tag != null) {
				String genre = tag.getFirst(FieldKey.GENRE);
				if (genre != null && !genre.isEmpty()) {
					return genre;
				}
			}
			
			// Se non è stato trovato nessun genere o c'è stato un problema
			return "Sconosciuto";

		} catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e) {
			System.err.println("Errore durante la lettura dei metadati: " + e.getMessage());
			return "Sconosciuto";
		}
	}
}