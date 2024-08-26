package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.stage.Window;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

/**
 * Provides the methods necessary to save game results to a text file. 
 * @author Brandon Green
 *
 */
public class Save {
	Date date = new Date();
	GameData gameData = new GameData();
	//Alert alert = new Alert(AlertType.ERROR);
	
	Save() {
		
	}
	
	/**
	 * A method to initialize the process of saving to a file. This method will call the other
	 * methods in this class depending on the mode passed to it.
	 * @param window The current window.
	 * @param mode The save mode to use. 0 for normal game save, 1 for song list save.
	 */
	public void initializeSave(Window window, int mode) {
		
	}
	
	/**
	 * A method that will open a FileChooser save dialog box so the user can select a directory
	 * to save the file, and if desired give it a custom file name.
	 * @param window - The current window.
	 * @param mode - The save mode to use. 0 for normal save, 1 for song list only save.	 
	 */
	public void openSaveDialog(Window window) {
		FileChooser fileChooser = new FileChooser();
		//System.out.println("FIXME: defaultFileName(): " + defaultFileName()); //FIXME
 		fileChooser.setInitialFileName(defaultFileName());
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Text Document (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(filter);
		File file = fileChooser.showSaveDialog(window);
		checkForErrors(file);
		
		/*
		 //Used for debugging and testing purposes.
		if(file != null) {
			//System.out.println("FIXME: file: " + file.getName());
			//System.out.println("FIXME: file path: " + file.getPath());
		}
		*/
	}
	
	/**
	 * Attempts to create the file passed to it at the selected directory. If successful calls writeResultsToFile() method.
	 * @param file - File to create
	 * @throws IOException - If an I/O error occurred
	 */
	public void createFile(File file) throws IOException {
		System.out.println("createFile() called!"); //FIXME
		/*if(file.createNewFile()) {
			System.out.println("File created successfully!");
			writeResultsToFile(file);
		}
		
		else {
			System.out.println("File could not be saved!");
			//alert.setContentText("A problem occurred and the file could not be saved! Please try again!");
			//alert.show();
		}*/
		
		file.createNewFile();
		if(GameData.getTitle().getPrefix().equalsIgnoreCase("Song List")) {
			 writeSongListToFile(file);
		}
		else {
			writeResultsToFile(file);
		}
	}
	
	/**
	 * Attempts to create a new FileWriter and if successful, writes the game data to the text file passed to it.
	 * @param file - The file to write the data to. Should be a text file.
	 */
	private void writeResultsToFile(File file) {
		FileWriter writer;
		try {
			//Attempts to create a new FileWriter object in append mode.
			writer = new FileWriter(file, true);
			
			writer.write("Title: " + GameData.getTitle().toString() + "\n");
			/*
			  if(!GameData.isTitleEmpty()) {
			 
				
				writer.write("Title: Name That Tune - " + GameData.getGameTitle() + "\n");
			}
			
			else {
				writer.write("Title: Name That Tune - " + date.getDateUSFormat() + "\n");
			}
			*/
			
			/*writer.write("\nSong List: \n");
			
			for(int i = 0; i < gameData.getSongList().size(); i++) {
				if(gameData.getSongList().get(i).equals(TieBreakerMode.getTieBreakerSong())) {
					writer.write(gameData.getSongList().get(i).toString() + " (Tiebreaker)\n");
				}
				else {
					writer.write(gameData.getSongList().get(i).toString() + "\n");
				}
			}*/
			
			writeSongList(writer);
			
			writer.write("\n\nResults:\n");
			
			for(int i = 0; i < GameData.getSortedPlayerList().size(); i++) {
				writer.write(GameData.getSortedPlayerList().get(i) + "\n");
			}
			
			writeScoringHistory(writer);
			
			writer.write("\n\nWarning: Modifying this file may result in not being able to load results from this file in the future.\n");
			writer.write("\nFile Generated: " + date.getDateUSFormat() + "\n");
			
			writer.close();
			
			alert("Game results have been successfully saved to file!", AlertType.INFORMATION);
			System.out.println("Game results have been successfully saved to file!");
			
		} catch (IOException e) {
			e.printStackTrace();
			alert("An error occurred while writing to the file. Please try again later!", AlertType.ERROR);
		}
		
		System.out.println("writeResultsToFile() called!");
		
	}
	
	
	/**
	 * Method that writes song list data to a file.
	 * @param file The file to write the data to.
	 */
	public void writeSongListToFile(File file) {
		//System.out.println("FIXME: WriteSongListToFile() called!");
		FileWriter writer;
		try {
			//Attempts to create a new FileWriter object in append mode.
			writer = new FileWriter(file, true);
			writer.write("Title: " + GameData.getTitle().toString() + "\n");
			
			writeSongList(writer);
			writer.write("\n\nWarning: Modifying this file may result in not being able to load song list from this file in the future.\n");
			writer.write("\nFile Generated: " + date.getDateUSFormat() + "\n");
			
			writer.close();
			alert("Game results have been successfully saved to file!", AlertType.INFORMATION);
		} catch (IOException e) {
			e.printStackTrace();
			alert("An error occurred while writing to the file. Please try again later!", AlertType.ERROR);
		}
	}
	
	/**
	 * Helper method that iterates through the song list and writes it to a file.
	 * @param writer
	 */
	private void writeSongList(Writer writer) {
		//System.out.println("FIXME: file: " + file.getAbsolutePath());
		try {
			//Attempts to create a new FileWriter object in append mode.
			//Writer writer = new FileWriter(file, true);
			writer.write("\nSong List: \n");
			
			for(int i = 0; i < gameData.getSongList().size(); i++) {
				if(gameData.getSongList().get(i).equals(TieBreakerMode.getTieBreakerSong())) {
					writer.write(gameData.getSongList().get(i).toString() + " (Tiebreaker)\n");
					//System.out.println("FIXME: Tiebreaker Song: " + gameData.getSongList().get(i).toString() + " (Tiebreaker)\n"); //FIXME
					
				}
				else {
					writer.write(gameData.getSongList().get(i).toString() + "\n");
					//System.out.println("FIXME: Song" + i + ": " + gameData.getSongList().get(i).toString() + "\n"); //FIXME
				}
			}
			
			//writer.close();
		} catch (IOException e) {
			System.out.println("ERROR: Unable to write song list to file!");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Writes the scoring action history to the text file when game results are saved.
	 * @param writer
	 */
	private void writeScoringHistory(Writer writer) {
		ArrayList<String> tempList = gameData.getScoringHistory(); 
		
		try {
			writer.write("\nScoring History:\n");
			
			for(int i=0; i < tempList.size(); i++) {
				if(i == tempList.size() - 1) {
					writer.write(tempList.get(i));
				}
				
				else {
					writer.write(tempList.get(i) + "\n");
				}
			}
		} catch (Exception e) {
			
			System.out.println("ERROR: Unable to write scoring history to file!");
			e.printStackTrace();
		}
		
		//TODO
	}
	
	/**
	 * Confirms that there are no illegal characters in the file name.
	 * @param file
	 * @return true if illegal characters are found, false otherwise.
	 */
	private boolean validateFileName(File file) {
		Pattern invalidCharsPattern = Pattern.compile("[<>:/|?\"\\\\*]");
		Matcher matcher = invalidCharsPattern.matcher(file.getName());
		return matcher.find();
	}
	
	/**
	 * Creates a default file name containing the game title (if specified) 
	 * or the current date in US format if no game title was specified by the user.
	 * @param mode Used to change the beginning of the file name based on the part of the program calling it. 0 for normal, 1 for song list.
	 * @return A String containing the default file name.
	 */
	public String defaultFileName() {
		/*String titlePreFix = "";
		
		//If method is called from results page of a game.
		if(mode == 0) {
			titlePreFix = "Name That Tune";
		}
		
		//If method is called from the Song List Generator.
		if(mode == 1) {
			titlePreFix = "Song List";
		}
		
		if(GameData.isTitleEmpty()) {
			//return "Name That Tune - " + date.getDateUSFormatDashes() + ".txt";
			return titlePreFix + " - " + date.getDateUSFormatDashes() + ".txt";
		}
		
		else {
			//return "Name That Tune - " + GameData.getGameTitle() + ".txt";
			return titlePreFix + " - " + GameData.getGameTitle() + ".txt";
		}*/
		
		return GameData.getTitle().toString() + ".txt";
	}
	
	/**
	 * Checks for errors in the file name that could cause file creation to fail.
	 * If an error is found, an error alert is provided to the user and the save process is aborted.
	 * Otherwise if no errors are detected, the file is passed to the createFile() method for creation.
	 * @param file - The file to be checked for errors.
	 */
	public void checkForErrors(File file) {
		if(file == null) {
			System.out.println("Could not save game because file is null!");
			alert("Error: Save has been cancelled! If you didn't cancel the save, please try again!", AlertType.ERROR);
			//alert("Error: Could not save results because no valid file was selected or the dialog box was closed without pressing save.", AlertType.ERROR);
			//Alert alert = new Alert(AlertType.ERROR);
			//alert.setContentText("Error: Could not save results because no valid file was selected or the dialog box was closed without pressing save.");
			//alert.show();
			return;
		}
		
		if(!file.toString().endsWith(".txt")) {
			System.out.println("Error: Could not save results because file extension is invalid.");
			alert("Error: Could not save results because file extension is invalid. Please make sure to only save as .txt", AlertType.ERROR);
			//Alert alert = new Alert(AlertType.ERROR);
			//alert.setContentText("Error: Could not save results because file extension is invalid. Please make sure to only save as .txt");
			//alert.show();
			return;
		}
		
		if(validateFileName(file)) {
			System.out.println("Error: Could not save results because one or more illegal characters has been detected in the file name.");
			alert("Error: Illegal character has been detected in the file name. Please try again with a new different name.", AlertType.ERROR);
			//alert.setContentText("Error: Illegal character has been detected in the file name. Please try again with a new different name.");
			//alert.show();
			return;
			
			
		}
		
		
		else {
			
			//System.out.println("FIXME: File Name Length (including path): " + file.toString().length());
			try {
			createFile(file);
			System.out.println("FIXME: No Errors Detected With file.");
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("File could not be created due to an error.");
				alert("An error occurred and the file could not be created. Please try again later!", AlertType.ERROR);
				//alert.setContentText("An error occurred and the file could not be created. Please try again later!");
			}
		}
		
	}
	
	/**
	 * Creates and displays a new alert box with the message and type passed to it.
	 * @param message - The message (content text) to be displayed in the alert
	 * @param alertType - The alert type
	 */
	private void alert(String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		
		alert.setContentText(message);
		alert.show();
	}

}
