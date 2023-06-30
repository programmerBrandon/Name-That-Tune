package application;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SongInfoSetupController {
	GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
	HeaderButtonsController headerButtonsController = new HeaderButtonsController();
	GameData gameData = new GameData();
	private int numSongsAdded = 0; //Keeps track of how many songs were added to the list (To compare to how many songs are in the game).
	ArrayList<Song> tempSongList = new ArrayList<>();
	
	// Begin 'Global' FXML objects. //
	@FXML private AnchorPane programBody; //Main AnchorPane
	@FXML private BorderPane header; //Top BorderPane that is used as a container for the header.
	@FXML private BorderPane footer; //Bottom BorderPane used as a container for footer items.
	
	//Header objects
	@FXML private ImageView headerIcon;
	@FXML private Label programName;
	@FXML private ImageView homeIcon;
	@FXML private ImageView infoIcon;
	
	//Menu and menu items (also part of header items)
	@FXML private Button homeButtonHeader;
	@FXML private Button infoButtonHeader;
	
	// End of Header Objects //
	
	// Footer items //
	
	@FXML private Label copyrightText;
	@FXML private Label versionText;
	
	//  End of Global FXML Objects //
	
	// SongInfoSetup.fxml specific FXML objects //
	
	@FXML private ListView<String> songList;
	@FXML private Label instructionsLabel; //Used to give user instructions on what data to provide, etc.
	@FXML private TextField songNameField; //Textbox for user to enter song name.
	@FXML private TextField artistNameField; //Textbox for user to enter artist name.
	@FXML private Button addButton; //Button to add song to 'songList' listview and songList[] (GamaData class).
	@FXML private Label messageLabel;
	@FXML private Button continueButton;
	
	// End of SongInfoSetup.fxml specific objects
	
	//Set text values for header and footer text using values from 'GlobalValues'.
	@FXML
	public void initialize()  {
		headerIcon.setImage(globalValues.getProgramIcon());
		homeIcon.setImage(globalValues.getHomeIcon());
		infoIcon.setImage(globalValues.getInfoIcon());
		programName.setText(globalValues.getProgramName());
		copyrightText.setText(globalValues.getCopyright());
		versionText.setText(globalValues.getVersionNumber());
		homeButtonHeader.setOnAction(headerButtonsController);
		infoButtonHeader.setOnAction(headerButtonsController);
		instructionsLabel.setText("Using the textboxes below, please enter the song name and artist name for each song. After typing in the "
			+ "information, double check that it was typed in correctly (i.e no typos) and then hit enter on your keyboard or press the "
			+ "'Add' button. The song and artist will then appear in the list above. Repeat this step until all songs are added. ");
		songList.setPlaceholder(new Label("No songs to display"));
	/*songNameField.focusedProperty().addListener((observable, oldValue, newValue) ->
	handleTextBox() );*/
	
		//Validate input as the user types by calling handleTextBox() method.
		songNameField.setOnKeyTyped(event -> {
			handleTextBox();
		});
	
		addButton.setOnAction((event) -> {
			addButtonHandler();
		});
	
	
		continueButton.setOnAction(e -> {
			try {
				Parent playerInfoSetup = FXMLLoader.load(getClass().getResource("PlayerInfoSetup.fxml"));
				Scene playerInfoSetupScene = new Scene(playerInfoSetup, globalValues.getProgramWidth(), globalValues.getProgramHeight());
				playerInfoSetupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage playerInfoSetupStage = (Stage)((Node)e.getSource()).getScene().getWindow();
				playerInfoSetupStage.setScene(playerInfoSetupScene);
				playerInfoSetupStage.show();
				System.out.println("PlayerInfoSetup.fxml loaded successfully!");
			} catch (IOException e1) {
				e1.printStackTrace();
				messageLabel.setText("Error: Failed to load PlayerInfoSetup.fxml. If this error persists, please notify the developer.");
				System.out.println("Failed to load PlayerInfoSetup.fxml!");
			}
		});
	}
	
	public void handleTextBox() {
		songNameField.setStyle("-fx-text-box-border: transparent; -fx-focus-color: #039ED3; -fx-text-fill: #000; ");
		messageLabel.setVisible(false);
		continueButton.setDisable(true);
		
		//System.out.println("FIXME: handleTextBox() method called!"); //FIXME
		
		if(!songNameField.getText().trim().isEmpty()) { 
			addButton.setDisable(false);
		}
		
		else {
			addButton.setDisable(true);
		}
		
		songNameField.setOnKeyPressed( event -> {
			  if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
				  
				  if(songNameField.getText().trim().isEmpty()) {
					  messageLabel.setText("Error: Song name field cannot be left blank!");
					  songNameField.setStyle("-fx-text-box-border: #ff0000;");
					  messageLabel.setVisible(true);
					  addButton.requestFocus();
					  addButton.setDisable(true);
					  //continueButton.setDisable(true);
					  } else {
						  messageLabel.setVisible(false);
						  addButton.setDisable(false);
						  songNameField.setStyle("-fx-text-box-border: transparent;");
						  artistNameField.requestFocus();
						  //addButtonHandler();
					  }
			  }			  
				});
		
		artistNameField.setOnKeyPressed( event -> {
			  if(event.getCode() == KeyCode.ENTER) {
				  //if(!(songNameField.getText().trim().isEmpty())) {
				  addButton.requestFocus();
				  //}
				  
				  if(!(artistNameField.getText().trim().isEmpty()) ) {
						  //addButtonHandler();
					  }
				  }
			  
			  
				});
	}
	
	public void addButtonHandler() {
		//System.out.println("FIXME: addButtonHandler() method called!"); //FIXME
		Song song = new Song();
		
		if ((numSongsAdded < gameData.getNumOfSongs())) {
		numSongsAdded++;
		//System.out.println("Fixme: numSongsAdded: " + numSongsAdded); //FIXME
		
		song.setNumber(numSongsAdded);
		song.setName(songNameField.getText());
		song.setArtist(artistNameField.getText());
		System.out.println("FiXME: song: " + song.toString());
		
		//duplicateChecker(tempSongList, song);
		
		if(duplicateChecker(tempSongList, song)) {
			//messageLabel.setText("Error: " + song.getName() + " By: " + song.getArtist() + " is already in the list. "
			//		+ "Please enter a different song!");
			messageLabel.setText("Error: Song is already in the list. Please enter a different song!");
			messageLabel.setVisible(true);
			addButton.setDisable(true);
			numSongsAdded--;
			songNameField.clear();
			artistNameField.clear();
			songNameField.requestFocus();
		}
		
		else {
			tempSongList.add(song);			
			
			if(!(artistNameField.getText().trim().isEmpty()) ) {
				songList.getItems().add(song.toString());
			  } 
			
			else {
				
				songList.getItems().add(song.toString());
				//songList.getItems().add(songNameField.getText());
			  }
			
			messageLabel.setText("Song " + numSongsAdded + " of " + gameData.getNumOfSongs() + " added!");
			messageLabel.setVisible(true);

			
			if (numSongsAdded == gameData.getNumOfSongs()) {
				gameData.setSongList(tempSongList);
				//gameData.setSongNameList(songNameList);
				messageLabel.setText("Song " + numSongsAdded + " of " + gameData.getNumOfSongs() + " added! Click 'Continue' below to proceed!");
				continueButton.setDisable(false);
				continueButton.setVisible(true);
				songNameField.setDisable(true);
				artistNameField.setDisable(true);
				addButton.setDisable(true);
				continueButton.requestFocus();
				
			
		}
			
			songNameField.clear();
			artistNameField.clear();
			songNameField.requestFocus();
		
		
		/*for(int i = 0; i < tempSongList.size(); i++) {
			if(tempSongList.get(i).equals(song)) {
				messageLabel.setText("Error: " + song.getName() + " " + song.getArtist() + " has already been added. Duplicates are "
						+ "not permitted. Please enter a different song!");
				messageLabel.setVisible(true);
				System.out.println("FIXME: Duplicate entry detected!");
			}
			
			else {
				tempSongList.add(song);
			}
		}*/
		/*if(tempSongList.contains(song)) {
			messageLabel.setText("Error: " + song.getName() + " " + song.getArtist() + " has already been added. Duplicates are "
					+ "not permitted. Please enter a different song!");
			messageLabel.setVisible(true);
			System.out.println("FIXME: tempSong contains " + song);
		
		}
		
		else {
			tempSongList.add(song);
		}*/

		}
			for(int i = 0; i < tempSongList.size(); i++) {
				System.out.println("FIXME: tempSongList.get(" + i + ") " + tempSongList.get(i)); //FIXME
			}			
			//System.out.println("Fixme: numSongsAdded: " + numSongsAdded); //FIXME
		}
	}
	
	/**
	 * Accepts an ArrayList of type Song and a Song object as arguments.
	 * Iterates through the argument ArrayList and checks to see if the list contains
	 * a song matching the argument song object (compared by song name and artist).
	 * @param tempList
	 * @param song
	 * @return true if a duplicate song (case insensitive) is found in the list, false otherwise.
	 */
	public boolean duplicateChecker(ArrayList<Song> tempList, Song song) {
			//tempList = tempSongList;
			System.out.println("FIXME: duplicateChecker() called!");
		
			for(int i = 0; i < tempList.size(); i++) {
				if(tempList.get(i).equals(song)) {
					/*messageLabel.setText("Error: " + song.getName() + " " + song.getArtist() + " has already been added. Duplicates are "
						+ "not permitted. Please enter a different song!");
					messageLabel.setVisible(true);*/
					System.out.println("FIXME: Duplicate entry detected!");
					return true;
				}
		
				/*else if(i == tempList.size() - 1) {
					return false;
				}*/
				
				else {
					continue;
				}
		}
		
			return false;
	}
}
