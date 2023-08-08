package application;

import java.util.ArrayList;

/**
 * Class that stores game data that is needed throughout various parts of the program.
 * @author Brandon Green
 *
 */
public class GameData {
	private static String gameTitle = "";
	private static int numOfSongs = 1;
	private static int numOfPlayers = 1;
	private static int tieBreakerMode = 1; // 0 for Off, 1 for On.
	private static ArrayList<String> sortedPlayerList = new ArrayList<String>();
	private static ArrayList<Song> songList = new ArrayList<>();
	private static ArrayList<Player> playerList = new ArrayList<>();

	public GameData() {
	
	}

	public int getNumOfSongs() {
		return numOfSongs;
	}

	public void setNumOfSongs(int numOfSongs) {
		//System.out.println("FIXME: setNumOfSongs() called!"); //FIXME
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

	public static String getGameTitle() {
		return gameTitle;
	}

	public static void setGameTitle(String gameTitle) {
		GameData.gameTitle = gameTitle;
	}

}
