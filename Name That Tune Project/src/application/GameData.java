package application;

import java.util.ArrayList;
import java.util.HashMap;

public class GameData {
	private static int numOfSongs = 1;
	private static int numOfPlayers = 1;
	private static int tieBreakerMode = 1; // 0 for Off, 1 for On.
	private static ArrayList<String> sortedPlayerList = new ArrayList<String>();
	private static ArrayList<Song> songList = new ArrayList<>();
	private static ArrayList<Player> playerList = new ArrayList<>();

	public GameData() {
	
	}

	public int getNumOfSongs() {
		return this.numOfSongs;
	}

	public void setNumOfSongs(int numOfSongs) {
		//System.out.println("FIXME: setNumOfSongs() called!"); //FIXME
		this.numOfSongs = numOfSongs;
	}

	public int getNumOfPlayers() {
		return this.numOfPlayers;
	}

	public void setNumOfPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}

	public int getTieBreakerMode() {
		return this.tieBreakerMode;
	}

	public void setTieBreakerMode(int tieBreakerMode) {
		this.tieBreakerMode = tieBreakerMode;
	}

	
	public static ArrayList<String> getSortedPlayerList() {
		return sortedPlayerList;
	}

	public static void setSortedPlayerList(ArrayList<String> sortedPlayerList) {
		GameData.sortedPlayerList = sortedPlayerList;
	}


	public void setSongList(ArrayList<Song> songList2) {
		this.songList = songList2;
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

}
