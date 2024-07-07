package application;

import java.util.ArrayList;

/**
 * Class that stores game data that is needed throughout various parts of the program.
 * @author Brandon Green
 *
 */
public class GameData {
	private static String gameTitle = ""; //DEPRECIATED AS OF VERSION 0.4.0-ALPHA, REMOVE AFTER 1 VERSION IF NO BUGS WITH NEW CODE.
	private static Title title = new Title();
	private static int numOfSongs = 1;
	private static int numOfPlayers = 1;
	private static int tieBreakerMode = 1; // 0 for Off, 1 for On.
	private static ArrayList<String> sortedPlayerList = new ArrayList<String>();
	private static ArrayList<Song> songList = new ArrayList<>();
	private static ArrayList<Player> playerList = new ArrayList<>();

	public GameData() {}

	public int getNumOfSongs() {
		return numOfSongs;
	}

	public void setNumOfSongs(int numOfSongs) {
		GameData.numOfSongs = numOfSongs;
	}

	public int getNumOfPlayers() {
		return numOfPlayers;
	}

	public void setNumOfPlayers(int numOfPlayers) {
		GameData.numOfPlayers = numOfPlayers;
	}

	public int getTieBreakerMode() {
		return tieBreakerMode;
	}

	public void setTieBreakerMode(int tieBreakerMode) {
		GameData.tieBreakerMode = tieBreakerMode;
	}

	
	public static ArrayList<String> getSortedPlayerList() {
		return sortedPlayerList;
	}

	public static void setSortedPlayerList(ArrayList<String> sortedPlayerList) {
		GameData.sortedPlayerList = sortedPlayerList;
	}


	public void setSongList(ArrayList<Song> songList) {
		GameData.songList = songList;
	}

	public ArrayList<Song> getSongList() {
		return songList;
	}

	public static ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public static void setPlayerList(ArrayList<Player> playerList) {
		GameData.playerList = playerList;
	}
	
	/*
	 * DEPRECIATED AS OF VERSION 0.4.0-ALPHA, REMOVE AFTER 1 VERSION IF NO BUGS WITH NEW CODE.
	 * 
	public static String getGameTitle() {
		return gameTitle;
	}
	*/
	
	public static Title getTitle() {
		return title;
	}

	//DEPRECIATED AS OF VERSION 0.4.0-ALPHA, REMOVE AFTER 1 VERSION IF NO BUGS WITH NEW CODE.
	public static void setGameTitle(String gameTitle) {
		GameData.gameTitle = gameTitle;
	}
	
	public static void setTitle(Title title) {
		GameData.title = title;
	}
	
	/**
	 * DEPRECIATED AS OF VERSION 0.4.0-ALPHA, REMOVE AFTER 1 VERSION IF NO BUGS WITH NEW CODE.
	 * 
	 * Method to check if game title is empty or not.
	 * @return true if title is empty, false otherwise
	 
	public static boolean isTitleEmpty() {
		return gameTitle == "";
	}
	*/

}
