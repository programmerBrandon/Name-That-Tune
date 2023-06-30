package application;

public class Player implements Comparable<Player> {
	private String name;
	private int points;
	//private String lastSongScored;
	
	
	public Player() {
		name = "";
		points = 0;
		//lastSongScored = "";
	}
	
	/*public void setLastSongScored(String lastSongScored) {
		this.lastSongScored = lastSongScored;
	}
	
	public String getLastSongScored() {
		return lastSongScored;
	}*/
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getPoints() {
		return points;
	}
	
	
	/**
	 * Compares (by name only - case insensitive) 2 Player objects to see if they are equal.
	 * @param obj
	 * @return true if the 2 player objects are equal when compared by name, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Player)) {
			return false;
		}
		
		else {
			Player player = (Player) obj;
			return name.equalsIgnoreCase(player.name);
		}
		
		/*boolean isEqual = false;

	    if (val instanceof String){
	    	String tempVal = (String) val;
	        isEqual = this.name.equals(tempVal);
	    }

	    return isEqual;*/
	   }
	
	
	/**
	 * A variation of the toString() method that returns the player name without points as a string.
	 * @return Player name without points as a String.
	 */
	public String toStringNoPoints() {
		return name;
	}
	
	/**
	 * A variation of the toString() method that returns the player name and points with an alternate format.
	 * @return Player name  and points as a String formatted as either 'name - 2 Points' or 'name - 1 Points'.
	 */
	public String toStringAltFormat() {
		if(points == 1) {
			return name + " - " + points + " Point";
		}
		
		else {
			return name + " - " + points + " Points";
		}
	}
	
	/**
	 * Simple toString() method that returns the player name and points as a String.
	 * @return Player name and points as a String.
	 */
	public String toString() {
		return name + " - Points: " + points;
	}

	@Override
	public int compareTo(Player player) {
		return (this.points - player.points);	}

}
