package application;

import java.util.ArrayList;
import java.util.HashMap;

public class GameData {
private static int numOfSongs = 1;
private static int numOfPlayers = 1;
private static int tieBreakerMode = 0; // 0 for Off, 1 for On.
private static String[] songNameList;
private static String[] playerNameList;
private static HashMap<String, String> songList = new HashMap<String, String>();
private static HashMap<String, Integer> playerScores = new HashMap<String, Integer>();
private static HashMap<Integer, String> playerListNames = new HashMap<Integer, String>();
private static ArrayList<String> sortedPlayerList = new ArrayList<String>();

public GameData() {
	
}

public int getNumOfSongs() {
	return this.numOfSongs;
}

public void setNumOfSongs(int numOfSongs) {
	System.out.println("FIXME: setNumOfSongs() called!"); //FIXME
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

public void setSongNameList(String[] songNameList) {
	this.songNameList = songNameList;
}

public String[] getSongNameList() {
	return songNameList;
}

public void setSongList(String songName, String artistName) {
	songList.put(songName, artistName);
	System.out.println("FIXME: Song data added to HashMap songList!"); //FIXME 
}

public String getSongList(String songName) {
	String artistName = songList.get(songName);
	return artistName;
}

public void setPlayerNameList(String[] playerNameList) {
	this.playerNameList = playerNameList;
}

public String[] getPlayerNameList() {
	return playerNameList;
}

public void setPlayerScores(String playerName, int points) {
	playerScores.put(playerName, points);
	System.out.println("FIXME: Player data added to HashMap playerScores!"); //FIXME 
}

public int getPlayerScores(String playerName) {
	int playerPoints = playerScores.get(playerName);
	return playerPoints;
}

public void setPlayerListNames(int points, String playerName) {
	playerListNames.put(points, playerName);
	System.out.println("FIXME: Player data added to HashMap playerListName!"); //FIXME 
}

public String getPlayerListNames(int points) {
	String playerName = playerListNames.get(points);
	return playerName;
}

public static ArrayList<String> getSortedPlayerList() {
	return sortedPlayerList;
}

public static void setSortedPlayerList(ArrayList<String> sortedPlayerList) {
	GameData.sortedPlayerList = sortedPlayerList;
}

}
