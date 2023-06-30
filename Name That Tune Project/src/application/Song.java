package application;

public class Song {
	private int number;
	private String name;
	private String artist;
	
	public Song() {
		number = 0;
		name = "";
		artist = "";
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
		
		//return false;
	}
}
