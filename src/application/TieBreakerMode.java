package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TieBreakerMode {
	//GameData gameData = new GameData();
	private static Song tieBreakerSong = new Song();
	private static ArrayList<Player> tiedPlayerList = new ArrayList<>();
	
	public static ArrayList<Player> getTiedPlayerList() {
		return tiedPlayerList;
	}
	
	public static void setTiedPlayerList(ArrayList<Player> tiedPlayerList) {
		TieBreakerMode.tiedPlayerList = tiedPlayerList;
	}
	
	
	/**
	 * Checks index 0 and index 1 of a List of Player objects and checks whether their points are the same or different.
	 * @param list - A List of Player Objects
	 * @return true if the first 2 players in the list have the same points, false otherwise.
	 */
	public boolean checkForTies(List<Player> list) {
		ArrayList<Player> playerList = new ArrayList<Player>(list);
		
		if(playerList.size() == 1) {
			return false;
		}
		
		else {
			return playerList.get(0).getPoints() == playerList.get(1).getPoints();
		}
		
	}

	/**
	 * Takes the index of a player in the player list as a parameter and 
	 * uses it to add 1 point to that player's score, therefore breaking the tie.
	 * @param index - index of winning player in the Player list.
	 */
	public void setWinner(int index) {
		ArrayList<Player> playerList = new ArrayList<Player>(GameData.getPlayerList());
		Player player = playerList.get(index);
		player.setPoints(playerList.get(index).getPoints() + 1);
		
		playerList.set(index, player);
		//System.out.println(gameData.getPlayerList());
		Collections.sort(playerList, Collections.reverseOrder());
		//System.out.println(playerList);
		GameData.setPlayerList(playerList);
		//System.out.println(gameData.getPlayerList());
	}

	public static Song getTieBreakerSong() {
		return tieBreakerSong;
	}

	public static void setTieBreakerSong(Song tieBreakerSong) {
		TieBreakerMode.tieBreakerSong = tieBreakerSong;
	}
}
