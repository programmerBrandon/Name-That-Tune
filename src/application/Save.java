package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	 * A method that will open a FileChooser save dialog box so the user can select a directory
	 * to save the file, and if desired give it a custom file name.
	 * @param window - The current window	 
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
		 //Used for debugging and testing purposes.s
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
		//System.out.println("createFile() called!");
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
		writeResultsToFile(file);
	}
	
	/**
	 * Attempts to create a new FileWriter and if successful, writes the game data to the text file passed to it.
	 * @param file - The file to write the data to. Should be a text file.
	 */
	private void writeResultsToFile(File file) {
		FileWriter writer;
		try {
			writer = new FileWriter(file, true);
			
			if(!GameData.getGameTitle().equals("")) {
				
				writer.write("Title: Name That Tune - " + GameData.getGameTitle() + "\n");
			}
			
			else {
				writer.write("Title: Name That Tune - " + date.getDateUSFormat() + "\n");
			}
			
			writer.write("\nSong List: \n");
			
			for(int i = 0; i < gameData.getSongList().size(); i++) {
				if(gameData.getSongList().get(i).equals(TieBreakerMode.getTieBreakerSong())) {
					writer.write(gameData.getSongList().get(i).toString() + " (Tiebreaker)\n");
				}
				else {
					writer.write(gameData.getSongList().get(i).toString() + "\n");
				}
			}
			
			writer.write("\n\nResults:\n");
			
			for(int i = 0; i < GameData.getSortedPlayerList().size(); i++) {
				writer.write(GameData.getSortedPlayerList().get(i) + "\n");
			}
			
			writer.write("\n\nWarning: Modifying this file may result in not being able to load results from this file in the future.\n");
			writer.write("\nFile Generated: " + date.getDateUSFormat() + "\n");
			
			writer.close();
			
			alert("Game results have been successfully saved to file!", AlertType.INFORMATION);
			//alert.setAlertType(AlertType.INFORMATION);
			//alert.setContentText("Game results have been successfully saved to file!");
			//alert.show();
			System.out.println("Game results have been successfully saved to file!");
			
		} catch (IOException e) {
			e.printStackTrace();
			alert("An error occurred while writing to the file. Please try again later!", AlertType.ERROR);
		}
		
		//System.out.println("writeResultsToFile() called!");
		
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
	 * @return A String containing the default file name.
	 */
	public String defaultFileName() {
		if(GameData.getGameTitle().equals("")) {
			return "Name That Tune - " + date.getDateUSFormatDashes() + ".txt";
		}
		
		else {
			return "Name That Tune - " + GameData.getGameTitle() + ".txt";
		}
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
			//System.out.println("FIXME: No Errors Detected With file."); //FIXME
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
