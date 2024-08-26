package application;

/**
 * Represents a Song.
 * @author Brandon Green
 *
 */
public class Song {
	private int number;
	private String name;
	private String artist;
	private boolean isTiebreaker;
	
	public Song() {
		number = 0;
		name = "";
		artist = "";
		isTiebreaker = false;
	}
	
	public Song(int number, String name, String artist) {
		this.number = number;
		this.name = name;
		this.artist = artist;
		isTiebreaker = false;
	}
	
	public Song(int number, String name, String artist, boolean isTiebreaker) {
		this.number = number;
		this.name = name;
		this.artist = artist;
		this.isTiebreaker = isTiebreaker;
	}
	
	public void setNumber(int number){
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public void setAsTieBreaker(boolean isTiebreaker) {
		this.isTiebreaker = isTiebreaker;
	}
	
	public boolean getIsTieBreaker() {
		return isTiebreaker;
	}
	
	/**
	 * Simple toString() method that returns the song number, name and artist as a String.
	 * If the artist is blank, then just the song number and name is returned.
	 * @return Song number, name and artist (if artist is blank it is omitted) as a String.
	 */
	public String toString() {
		if(artist.strip().isEmpty()) {
			return number + ". " + name;
		}
		
		else {
			return number + ". " + name + " By: " + artist;
		}
	}
	
	/**
	 * A version of the toString method that will show the song name and artist without the song number.
	 * @return A String containing the name of the song plus the artist (if there is one) without the song number.
	 */
	public String toStringNoNumber() {
		if(artist.strip().isEmpty()) {
			return name;
		}
		
		else {
			return name + " By: " + artist;
		}
	}
	
	/**
	 * Checks if a song has default properties. If it does, the song is considered blank.
	 * @return true if the song is blank, false otherwise.
	 */
	public boolean isBlank() {
		return number == 0 && name.isBlank() && artist.isBlank();
	}
	
	/**
	 * Compares (by song and artist name only - case insensitive) 2 Song objects to see if they are equal.
	 * @param obj
	 * @return true if the 2 song objects are equal when compared by song and artist name, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		
		if(!(obj instanceof Song)) {
			return false;
		}
		
		else {
			Song song = (Song) obj;
			return name.equalsIgnoreCase(song.name) && artist.equalsIgnoreCase(song.artist);
		}
		
	}
}
