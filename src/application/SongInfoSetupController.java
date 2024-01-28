package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SongInfoSetupController {
	GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
	HeaderButtonsController headerButtonsController = new HeaderButtonsController();
	GameData gameData = new GameData();
	private int numSongsAdded = 0; //Keeps track of how many songs were added to the list (To compare to how many songs are in the game).
	//ArrayList<Song> tempSongList = new ArrayList<>();
	private ObservableList<Song> tempSongList = FXCollections.observableArrayList();
	private boolean editModeEnabled = false;
	
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
	
	@FXML private ListView<Song> songList;
	@FXML private Label instructionsLabel; //Used to give user instructions on what data to provide, etc.
	@FXML private TextField songNameField; //Textbox for user to enter song name.
	@FXML private TextField artistNameField; //Textbox for user to enter artist name.
	@FXML private Button addButton; //Button to add song to 'songList' listview and songList[] (GamaData class).
	@FXML private Button saveButton;
	@FXML private Label messageLabel;
	@FXML private Button continueButton;
	@FXML private Button editButton;
	@FXML private Button cancelButton;
	
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
	
		//Validate input as the user types by calling handleTextBox() method.
		songNameField.setOnKeyTyped(event -> {
			handleTextBox();
		});
	
		addButton.setOnAction((event) -> {
			addButtonHandler();
		});
		
		editButton.setOnAction((event) -> {
			editButtonHandler();
		});
		
		saveButton.setOnAction((event) -> {
			saveButtonHandler();
		});
		
		cancelButton.setOnAction((event) -> {
			cancelButtonHandler(false);
		});
		
		
		tempSongList.addListener((ListChangeListener<? super Song>) new ListChangeListener<Song>() {
			  public void onChanged(Change<? extends Song> c) {
				songList.getItems().setAll(tempSongList);
			  }
			});
		
		
		songList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if(!editModeEnabled) {
					editButton.setVisible(true);
					editButton.setDisable(false);
					continueButton.setVisible(true);
				}
				
				else {
					editButtonHandler();
				}
			}
			
		});
		
		
		continueButton.setOnAction(e -> {
			saveSongList(tempSongList);
			
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
	
	/**
	 * Handler for the song name and artist textboxes that performs all text validation and displays error messages to the user
	 * if one or more of the validation checks fail. Also prevents the user for progressing any further until errors are resolved.
	 */
	public void handleTextBox() {
		songNameField.setStyle("-fx-text-box-border: transparent; -fx-focus-color: #039ED3; -fx-text-fill: #000; ");
		//messageLabel.setVisible(false);
		continueButton.setDisable(true);
		//System.out.println("FIXME: handleTextBox() method called!"); //FIXME
		
		if(songNameField.getText().trim().isEmpty()) {
			  messageLabel.setText("Error: Song name field cannot be left blank!");
			  songNameField.setStyle("-fx-text-box-border: #ff0000;");
			  messageLabel.setVisible(false);
			  
			  if(!editModeEnabled) { 
					addButton.setDisable(true);
				}
				
				else {
			  		saveButton.setDisable(true);
			  	}
		}
		
		else {
			if(!editModeEnabled) { 
				addButton.setDisable(false);
			}
			
			else {
		  		saveButton.setDisable(false);
		  	}
		}
		
		songNameField.setOnKeyPressed( event -> {
			  if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
					  	songNameField.setStyle("-fx-text-box-border: transparent;");
				  		artistNameField.requestFocus();
					}				  
				});
		
		artistNameField.setOnKeyPressed( event -> {
			if(event.getCode() == KeyCode.ENTER) {
				  
				  if(!editModeEnabled) {
					  addButton.requestFocus();
				  } 
				  
				  else {
					  saveButton.requestFocus();
				  }
			}
		});
	}
	
	/** 
	 * Create and initialize a new Song object using data from the textfields.
	 * @return A Song object
	 */
	private Song createSong() {
		Song song = new Song();
		
		song.setNumber(numSongsAdded);
		song.setName(songNameField.getText());
		song.setArtist(artistNameField.getText());
		
		//System.out.println("FIXME: song: " + song.toString());
		
		return song;
	}
	
	/**
	 * Converts the temporary song list into a normal ArrayList and then saves it as the global song list.
	 * @param tempList An ObservableList containing Song objects.
	 */
	private void saveSongList(ObservableList<Song> tempList) {
		ArrayList<Song> tempSongListCopy = new ArrayList<>(tempList);
		
		gameData.setSongList(tempSongListCopy);
		
		/*
		 //DEBUGGING STATEMENTS!
		 tempSongListCopy = gameData.getSongList();
		
		//FIXME FOR LOOP
		for(int i = 0; i < tempSongListCopy.size(); i++) {
			System.out.println("FIXME: songList(" + i + "): " + tempSongListCopy.get(i)); //FIXME
		}*/
	}
	
	/**
	 * Handler method for the add button. It handles all logic required to add a new song to the temporary song list. 
	 */
	private void addButtonHandler() {
		//System.out.println("FIXME: addButtonHandler() method called!"); //FIXME
		//Song song = new Song();
		Song song;

		if ((numSongsAdded < gameData.getNumOfSongs())) {
			numSongsAdded++;
			song = createSong();
			//song.setNumber(numSongsAdded);
			//song.setName(songNameField.getText());
			//song.setArtist(artistNameField.getText());
			//System.out.println("FIXME: song: " + song.toString());
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
				messageLabel.setText("Song " + numSongsAdded + " of " + gameData.getNumOfSongs() + " added!");
				messageLabel.setVisible(true);


				if (numSongsAdded == gameData.getNumOfSongs()) {
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

			}
			/*
			 //DEBUGGING STATEMENNTS 
			 for(int i = 0; i < tempSongList.size(); i++) {
				System.out.println("FIXME: tempSongList.get(" + i + ") " + tempSongList.get(i)); //FIXME
			}			
			//System.out.println("Fixme: numSongsAdded: " + numSongsAdded); //FIXME
			 */
		}
	}
	
	/**
	 * Handler for the edit button. It is responsible for putting the program in edit mode.
	 */
	private void editButtonHandler() {
		if(songList.getSelectionModel().getSelectedItem() == null) {
			editModeEnabled = false;
			editButton.setDisable(true);
		}
		
		else {
			editModeEnabled = true;
			addButton.setVisible(false);
			
			songNameField.setDisable(false);
			artistNameField.setDisable(false);
			songNameField.setText(songList.getSelectionModel().getSelectedItem().getName());
			artistNameField.setText(songList.getSelectionModel().getSelectedItem().getArtist());
			
			saveButton.setDisable(false);
			saveButton.setVisible(true);
			editButton.setDisable(true);
			editButton.setVisible(false);
			continueButton.setVisible(false);
			cancelButton.setDisable(false);
			cancelButton.setVisible(true);
			
			songNameField.requestFocus();
			messageLabel.setText("*Now editing song " + songList.getSelectionModel().getSelectedItem().getNumber() + " *");
		}
	}
	
	/**
	 * Cancels edit mode and returns the program back to the normal song adding process.
	 * @param editSaved A boolean value - True if a successful edit was done. False in all other cases.
	 */
	private void cancelButtonHandler(boolean editSaved) {
		editModeEnabled = false;
		songNameField.clear();
		artistNameField.clear();
		saveButton.setVisible(false);
		saveButton.setDisable(true);
		cancelButton.setVisible(false);
		cancelButton.setDisable(true);
		addButton.setVisible(true);
		
		if(numSongsAdded < gameData.getNumOfSongs()) {
			if(editSaved) {
				messageLabel.setText("*Edit saved successfully! Please continue where you left off.*");
			}
			
			else {
				messageLabel.setText("*Edit cancelled. Please continue where you left off.*");
			}
			
			messageLabel.setVisible(true);
			songNameField.requestFocus();
		}
		
		else {
			if(editSaved) {
				messageLabel.setText("*Edit saved successfully! Please click 'Continue' below to proceed to the next step.*");
			}
			
			else {
				messageLabel.setText("*Edit cancelled. Please click 'Continue' below to proceed to the next step.*");
			}
			
			messageLabel.setVisible(true);
			songNameField.setDisable(true);
			artistNameField.setDisable(true);
			continueButton.setVisible(true);
			continueButton.setDisable(false);
			continueButton.requestFocus();
		}
		
	}
	
	/**
	 * Handler for the save button that is responsible for getting the new song information, validating it and then saving the 
	 * new song data to the temporary song list. 
	 */
	private void saveButtonHandler() {
		//Song selectedSong = songList.getSelectionModel().getSelectedItem();
		Song tempSong = createSong();
		String editedSongName = songNameField.getText();
		String editedArtistName = artistNameField.getText();
		int index = songList.getSelectionModel().getSelectedIndex();
		
		
		//tempSongList.set(index, editSong(selectedSong, editedSongName, editedArtistName));
		Song editedSong = editSong(tempSong, editedSongName, editedArtistName);
		//System.out.println("FIXME: editedSong.getName(): " + editedSong.getName()); //FIXME
		//System.out.println("FIXME: editedSong.getArtist(): " + editedSong.getArtist()); //FIXME
		//System.out.println("FIXME: editedSong: " + editedSong.toString()); //FIXME
		
		if(!duplicateChecker(tempSongList, editedSong)) {
			tempSongList.set(index, editedSong);
			songList.getSelectionModel().clearSelection();
			cancelButtonHandler(true);
		}
		else {
			messageLabel.setText("Error: Duplicate song detected! The edit failed because a song with the same name and artist "
					+ "is already in the list. Please try again!");
			messageLabel.setVisible(true);
			songNameField.requestFocus();
			
		}
		//return;
	}
	
	/**
	 * Helper method that edits a song object with the data that is passed to it via its parameters.
	 * @param song Song object to be edited.
	 * @param songName String to be used as the new song name.
	 * @param artistName String to be used as the new artist name.
	 * @return A Song object with the modified data properties.
	 */
	private Song editSong(Song song, String songName, String artistName) {
		song.setName(songName);
		song.setArtist(artistName);
		
		return song;
	}
	
	/**
	 * Accepts an ArrayList of type Song and a Song object as arguments.
	 * Iterates through the argument ArrayList and checks to see if the list contains
	 * a song matching the argument song object (compared by song name and artist).
	 * @param tempList
	 * @param song
	 * @return true if a duplicate song (case insensitive) is found in the list, false otherwise.
	 */
	public boolean duplicateChecker(ObservableList<Song> tempList, Song song) {
			//System.out.println("FIXME: duplicateChecker() called!");
		
			for(int i = 0; i < tempList.size(); i++) {
				//System.out.println("FIXME: tempList.get(" + i + "): " + tempList.get(i).toString());
				if(tempList.get(i).equals(song)) {
					
					System.out.println("FIXME: Duplicate entry detected!");
					return true;
				}
				else {
					continue;
				}
		}
		
			return false;
	}
}
