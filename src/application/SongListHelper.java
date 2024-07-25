package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SongListHelper {
	private ObservableList<Song> tempSongList = FXCollections.observableArrayList();
	
	
	public SongListHelper() {}
	
	/** 
	 * Create and initialize a new Song object using data from the textfields.
	 * @return A Song object
	 */
	public Song createSong(int numSongsAdded, String songName, String artistName) {
		Song song = new Song();
		
		song.setNumber(numSongsAdded);
		song.setName(songName);
		song.setArtist(artistName);
		
		//System.out.println("FIXME: song: " + song.toString());
		
		return song;
	}
	
	/**
	 * Helper method that edits a song object with the data that is passed to it via its parameters.
	 * @param song Song object to be edited.
	 * @param songName String to be used as the new song name.
	 * @param artistName String to be used as the new artist name.
	 * @return A Song object with the modified data properties.
	 */
	public Song editSong(Song song, String songName, String artistName) {
		song.setName(songName);
		song.setArtist(artistName);
		
		return song;
	}
	
	
	/**
	 * Accepts an ArrayList of type Song and a Song object as arguments.
	 * Iterates through the argument ArrayList and checks to see if the list contains
	 * a song matching the argument song object (compared by song name and artist).
	 * @param tempList
	 * @param song
	 * @return true if a duplicate song (case insensitive) is found in the list, false otherwise.
	 */
	public boolean duplicateChecker(ObservableList<Song> tempList, Song song) {
			//System.out.println("FIXME: duplicateChecker() called!");
		
			for(int i = 0; i < tempList.size(); i++) {
				//System.out.println("FIXME: tempList.get(" + i + "): " + tempList.get(i).toString());
				if(tempList.get(i).equals(song)) {
					
					System.out.println("Duplicate song entry detected!");
					return true;
				}
				else {
					continue;
				}
		}
		
			return false;
	}
	
	
	/**
	 * Accepts an ArrayList of type Song and a Song object as arguments.
	 * Iterates through the argument ArrayList and checks to see if the list contains
	 * a song matching the argument song object (compared by song name and artist).
	 * @param tempList
	 * @param song
	 * @return true if a duplicate song (case insensitive) is found in the list, false otherwise.
	 */
	public boolean duplicateChecker(ArrayList<Song> tempList, Song song) {
		//System.out.println("FIXME: duplicateChecker() called!");
	
		for(int i = 0; i < tempList.size(); i++) {
			//System.out.println("FIXME: tempList.get(" + i + "): " + tempList.get(i).toString());
			if(tempList.get(i).equals(song)) {
				
				System.out.println("Duplicate song entry detected!");
				return true;
			}
			else {
				continue;
			}
	}
	
		return false;
}
	
}
