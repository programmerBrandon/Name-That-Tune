package application;

//import java.awt.Window;
import java.io.File;
import javafx.stage.Window;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;


public class Save {
	Date date = new Date();
	
	Save() {
		//TODO
	}
	
	public void openSaveDialog(Window window) {
		FileChooser fileChooser = new FileChooser();
		System.out.println("FIXME: defaultFileName(): " + defaultFileName()); //FIXME
 		fileChooser.setInitialFileName(defaultFileName());
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Text Document (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(filter);
		File file = fileChooser.showSaveDialog(window);
		System.out.println("FIXME: file: " + file.getName());
		System.out.println("FIXME: file path: " + file.getPath());
		
		checkForErrors(file);
	}
	
	public void createFile(File file) {
		//TODO
		System.out.println("FIXME: createFile() called!");
	}
	
	private void writeResultsToFile(File file) {
		//TODO
	}
	
	private String sanitizeFileName(String string) {
		//TODO
		return ""; //FIXME
	}
	
	private void renameFile(File file, String name) {
		//TODO
	}
	
	public String defaultFileName() {
		if(GameData.getGameTitle().equals("")) {
			return "Name That Tune - " + date.getDateUSFormatDashes() + ".txt";
		}
		
		else {
			return "Name That Tune - " + GameData.getGameTitle() + ".txt";
		}
	}
	
	public void checkForErrors(File file) {
		if(file == null) {
			System.out.println("Could not save game because file is null!");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error: Could not save results because no valid file was selected or the dialog box was closed without pressing save.");
			alert.show();
		}
		
		if(!file.toString().endsWith(".txt")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error: Could not save results because file extension is invalid. Please make sure to only save as .txt");
			alert.show();
		}
		
		
		
		else {
			createFile(file);
			System.out.println("FIXME: No Errors Detected With file.");
		}
		
	}

}
