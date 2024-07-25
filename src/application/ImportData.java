package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Window;


/**
 * A class for importing data from saved text and data files. Primarily used to import songs and game result data from previously saved
 * song lists or game results.
 * @author Brandon Green
 *
 */
public class ImportData {
	SongListHelper songListHelper = new SongListHelper();
	private File file = new File("");
	private BufferedReader reader2;
	private Title title = new Title();
	private ArrayList<Song> songList = new ArrayList<Song>();
	private ArrayList<String> resultsList = new ArrayList<String>();
	private Song tieBreakerSong = new Song();
	private int numSongsSkipped = 0;
	
	
	public ImportData() {}
	
	public ImportData(File file) throws Exception {
		this.file = file;
		
		try {
			reader2 = new BufferedReader(new FileReader(file));
		} catch (Exception e) {
			reader2 = new BufferedReader(new FileReader(new File("")));
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("An error occurred and the file could not be imported. Please try again!");
			alert.show();
			e.printStackTrace();
		}
		
	}
	
	public Title getTitle() {
		return title;
	}
	
	public ArrayList<Song> getSongList() {
		return songList;
	}
	
	public ArrayList<String> getResultsList() {
		return resultsList;
	}
	
	public Song getTieBreakerSong() {
		return tieBreakerSong;
	}
	
	public int getNumSongsSkipped() {
		return numSongsSkipped;
	}
	
	/**
	 * Creates a new FileChooser object and then opens the 'Open Dialog' box to allow the user to select a file.
	 * @param window The current window.
	 * @return
	 */
	public File showOpenFileDialog(Window window) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Text Document (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(filter);
		File file = fileChooser.showOpenDialog(window);
		System.out.println("file: " + file);
		
		return file;
	}
	
	public void importGameFile(File file) {
		//TODO
	}
	
	/**
	 * Initiates the process for importing a song list from a text file.
	 * @param file The text file containing the song list to be imported.
	 */
	public void importSongListFile(File file) {
		this.file = file;
		System.out.println("importSongListFile() called!"); //FIXME
		try {
			//String line;
			BufferedReader reader = new BufferedReader(new FileReader(file));
			//this.songlist = importSongList(reader);
			
			/*while( (line = reader.readLine()) != null) {
				System.out.println("FIXME: reader.ReadLine(): " + line);
			}*/
			
			songList = importSongList(reader);
			//System.out.println(); //FIXME
			//testSongList(songList); //FIXME
			
		} catch (Exception e) {
			//reader = new BufferedReader(new FileReader(new File("")));
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("An error occurred and the file could not be imported. Please try again!");
			alert.show();
			e.printStackTrace();
		}
	}
	
	/**
	 * Imports the game or song list title from a file.
	 */
	private void importTitle(BufferedReader reader) {
		//TODO
		//return new Title(); //FIXME
	}
	
	/**
	 * Imports a song list from a file.
	 * @param reader A BufferedReader object used to read the imported file.
	 * @return a List object of type Song containing a list of imported songs.
	 */
	public ArrayList<Song> importSongList(BufferedReader reader) {
		ArrayList<Song> tempList = new ArrayList<>();
		boolean addToList = false;
		System.out.println("FIXME: importSongList() called!");
		try {
			String line;
			while( (line = reader.readLine()) != null) {
				//System.out.println("FIXME: line: " + line); //FIXME
				if(line.equalsIgnoreCase("Song List: ")) {
					addToList = true;
					//System.out.println("FIXME: addToList: " + addToList); //FIXME
					continue;
				}
				
				if(line.equals("") && addToList) {
					break;
				}
				
				if(addToList) {
					//System.out.println("FIXME: line: " + line); //FIXME
					//songList.add(importSong(line));
					if(songFormatValidator(line) && !songListHelper.duplicateChecker(tempList, importSong(line))) {
						tempList.add(importSong(line));
					}
					else {
						numSongsSkipped++;
						System.out.println("A song has been skipped due to error(s) being detected.");
						continue;
					}
				}
			}
			
			System.out.println(numSongsSkipped + " song(s) skipped due to errors!\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//testSongList(tempList);
		//return new ArrayList<Song>(); //FIXME
		return tempList;
	}
	
	/**
	 * Takes an imported song in string format and converts it to a Song object.
	 * @param songString A string object containing an imported song.
	 * @return
	 */
	public Song importSong(String songString) {
		int indexOfDot = songString.indexOf(".");
		String tempSongNum = songString.substring(0, indexOfDot);
		int songNum = Integer.parseInt(tempSongNum) - numSongsSkipped;
		String songName = "";
		int indexOfBy = songString.indexOf("By:");
		String artistName = "";
		
		if(indexOfBy != -1) {
			songName = songString.substring(indexOfDot + 2, indexOfBy - 1);
			artistName = songString.substring(indexOfBy + 4);
		}
		
		else {
			songName = songString.substring(indexOfDot + 2);
		}
		
		
		/*
		 //Debugging statements
		System.out.println("FIXME: songString: " +  songString); //FIXME
		System.out.println("FIXME: indexOfDot: " + indexOfDot); //FIXME
		System.out.println("FIXME: tempSongNum: " + tempSongNum); //FIXME
		System.out.println("FIXME: songNum: " + songNum); //FIXME
		System.out.println("FIXME: indexOfBy: " + indexOfBy); //FIXME
		System.out.println("FIXME: songName: " + songName); //FIXME
		System.out.println("FIXME: artistName: " + artistName); //FIXME
		System.out.println(); //FIXME
		*/
		
		return new Song(songNum, songName, artistName);
	}
	
	/**
	 * Checks the string passed to it to make sure that there are no formatting errors that would prevent
	 * the importSong() method from creating a Song object from an imported string.
	 * @param songString The string object to be checked.
	 * @return
	 */
	private boolean songFormatValidator(String songString) {
		//System.out.println("songFormatValidator() called!"); //FIXME
		int indexOfDot = songString.indexOf(".");
		int occurrencesOfBy = songString.split("By:", -1).length - 1;
		
		/*System.out.println("FIXME: indexOfDot: " + indexOfDot); //FIXME
		System.out.println("FIXME: occurrencesOfBy: " + occurrencesOfBy); //FIXME
		System.out.println();*/
		
		if(indexOfDot == -1 || indexOfDot > 2) {
			System.out.println("The song '" + songString + "' failed validation checks!");
			return false;
		}
		
		if(occurrencesOfBy > 1) {
			System.out.println("The song '" + songString + "' failed validation checks!");
			return false;
		}
		
		else {
			return true;
		}
	}
	
	/**
	 * Imports a player list from a file.
	 * @return a List object of type Player containing a list of imported players and their points.
	 */
	public List<Player> importPlayerList(BufferedReader reader) {
		//TODO
		return new ArrayList<Player>(); //FIXME
	}
	
	/**
	 * Imports a list of scoring history from a file with data from a previously saved game. 
	 * @return a List object of type String containing a list of scoring actions imported from a file.
	 */
	public List<String> importScoringHistory() {
		//TODO
		return new ArrayList<String>(); //FIXME
	}
	
	private void testSongList(ArrayList<Song> songList) {
		System.out.println("FIXME: testSongList() called!"); //FIXME
		System.out.println("FIXME: songList.size(): " + songList.size()); //FIXME
		for(int i = 0; i < songList.size(); i++) {
			System.out.println("FIXME: songList(" + i + "): " + songList.get(i)); //FIXME
		}
		System.out.println();
	}
}
