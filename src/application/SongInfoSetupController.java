package application;

import java.io.File;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SongInfoSetupController {
	GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
	HeaderButtonsController headerButtonsController = new HeaderButtonsController();
	GameData gameData = new GameData();
	private int numSongsAdded = 0; //Keeps track of how many songs were added to the list (To compare to how many songs are in the game).
	//ArrayList<Song> tempSongList = new ArrayList<>();
	private ObservableList<Song> tempSongList = FXCollections.observableArrayList();
	private boolean editModeEnabled = false;
	SongListHelper songListHelper = new SongListHelper();
	
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
	@FXML private Tab addSongsTab;
	@FXML private Tab editSongsTab;
	@FXML private Tab importSongListTab;
	@FXML private Label songInstructions;
	@FXML private Label editingInstructions;
	@FXML private Label importInstructions;
	@FXML private TextField songNameField; //Textbox for user to enter song name.
	@FXML private TextField artistNameField; //Textbox for user to enter artist name.
	@FXML private Button addButton; //Button to add song to 'songList' listview and songList[] (GamaData class).
	@FXML private Button saveButton;
	@FXML private Label messageLabel;
	@FXML private Label orLabel;
	@FXML private Button continueButton;
	@FXML private Button editButton;
	@FXML private Button cancelButton;
	@FXML private Button importButton;
	
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
		/*songInstructions.setText("Using the textboxes below, please enter the song name and artist name for each song. After typing in the "
			+ "information, double check that it was typed in correctly (i.e no typos) and then hit enter on your keyboard or press the "
			+ "'Add' button. The song and artist will then appear in the list above. Repeat this step until all songs are added. ");*/
		songInstructions.setText("You add songs to the game by following these simple steps:\n"
				+ "1. Enter the song name and if desired song artist into the textboxes below.\n"
				+ "2. Click the 'Add' button.\n"
				+ "3. Repeat the steps until all songs are added.\n\n"
				+ "Note: Songs cannot be edited after leaving this screen so be sure to check for typos before proceeding to the next screen.");
		
		editingInstructions.setText("You can edit a song that has already been added to the list using these simple steps:\n"
				+ "1. Select the song you want to edit from the list.\n"
				+ "2. Press the edit button at the bottom of the screen.\n"
				+ "3. Make the changes you want and then press save.\n\n"
				+ "If you change your mind about editing a song, just click cancel to abort.");
		
		importInstructions.setText("You can import a song list from a text file by following these steps:\n "
				+ "1. Click the 'Import From File' button at the bottom of the screen.\n"
				+ "2. Using the dialog box, locate and select the text file which contains the song list.\n"
				+ "3. Press 'Open' on the dialog box.\n"
				+ "4. Verify that all songs imported correctly and that the info for each song is correct.\n\n"
				+ "Please note:\n"
				+ "-Only unaltered files that were generated by this software using either the song list generator or save results feature "
				+ "should be imported.\n"
				+ "-Some songs may be skipped during the import process if formatting errors are detected in the file.");
		
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
				if(songList.getSelectionModel().getSelectedIndex() == -1) {
					return;
				}
				
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
			if(tempSongList.size() != gameData.getNumOfSongs()) {
				gameData.setNumOfSongs(tempSongList.size());
			}
			
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
		
		importButton.setOnAction(e -> {
			ImportData importData = new ImportData();
			Window window = importButton.getScene().getWindow();
			File file = importData.showOpenFileDialog(window);
			importData.importSongListFile(file);
			//System.out.println("FIXME: numSongsAdded: " + numSongsAdded); //FIXME
			//System.out.println("FIXME: gameData.getNumOfSongs(): " + gameData.getNumOfSongs()); //FIXME
			
			if(importData.getSongList().size() != 0) {
				for (Song song : importData.getSongList()) {
					if(tempSongList.size() < globalValues.getMaxSongsSupported()) {
						tempSongList.add(song);
					}
					
					else {
						System.out.println("Maximum number of songs reached! No further songs will be imported!");
						break;
					}
				}
				
				if(tempSongList.size() != 0) {
					numSongsAdded = tempSongList.size();
					//System.out.println("FIXME: numSongsAdded: " + numSongsAdded); //FIXME
					
					if(numSongsAdded != gameData.getNumOfSongs()) {
						gameData.setNumOfSongs(tempSongList.size());
						songNameField.clear();
						artistNameField.clear();
						songNameField.setDisable(true);
						artistNameField.setDisable(true);
						//System.out.println("FIXME: gameData.getNumOfSongs(): " + gameData.getNumOfSongs()); //FIXME
					}
					/*
					 * Removed for now due to a bug with how imported TieBreaker song is handled.
					 * if(numSongsAdded >= gameData.getNumOfSongs()) {
						gameData.setNumOfSongs(tempSongList.size());
						songNameField.clear();
						artistNameField.clear();
						songNameField.setDisable(true);
						artistNameField.setDisable(true);
						//System.out.println("FIXME: gameData.getNumOfSongs(): " + gameData.getNumOfSongs()); //FIXME
					}*/
					
					System.out.println("Import Results:");
					System.out.println(importData.getNumSongsSkipped() + " songs failed to import due to error(s).");
					System.out.println(numSongsAdded + " songs imported successfully!");
					/*System.out.print("Import Success Rate: ");
					System.out.printf("%.2f", numSongsAdded / (numSongsAdded + importData.getNumSongsSkipped()));
					System.out.println("%");*/
					System.out.println();
					messageLabel.setText(numSongsAdded + " songs imported from file. Please verify that song info is correct and then click continue!");
					messageLabel.setVisible(true);
					
					//System.out.println("FIXME: tempSongList: " + tempSongList.toString()); //FIXME
					//System.out.println("FIXME: tempSongList.size(): " + tempSongList.size()); //FIXME
					//System.out.println("FIXME: importData.getSongList.size(): " + importData.getSongList().size()); //FIXME
				}
				
				importButton.setVisible(false);
				orLabel.setVisible(false);
				continueButton.setVisible(true);
				continueButton.setDisable(false);
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
		addButton.setDisable(true);
		saveButton.setDisable(true);
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
	 * DEPRECATED AS OF VERSION 0.4.0-ALPHA, REMOVE AFTER 1 VERSION IF NO BUGS WITH NEW CODE.
	 * 
	 * Create and initialize a new Song object using data from the textfields.
	 * @return A Song object
	 * @deprecated
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
	 * Disables the ability to add new songs via manual entry mode.
	 */
	private void disableSongAdding() {
		songNameField.clear();
		artistNameField.clear();
		songNameField.setDisable(true);
		artistNameField.setDisable(true);
		addButton.setDisable(true);
		continueButton.setVisible(true);
		continueButton.setDisable(false);
		continueButton.requestFocus();
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

		/*if(songNameField.getText().isBlank()) {
			messageLabel.setText("Error: Song name field cannot be left blank!");
			  songNameField.setStyle("-fx-text-box-border: #ff0000;");
			  messageLabel.setVisible(false);
		}*/

		if ((numSongsAdded < gameData.getNumOfSongs())) {
			numSongsAdded++;
			importButton.setVisible(false);
			orLabel.setVisible(false);
			song = songListHelper.createSong(numSongsAdded, songNameField.getText(), artistNameField.getText());
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
				addButton.setDisable(true);
				messageLabel.setText("Song " + numSongsAdded + " of " + gameData.getNumOfSongs() + " added!");
				messageLabel.setVisible(true);


				if (numSongsAdded == gameData.getNumOfSongs()) {
					messageLabel.setText("Song " + numSongsAdded + " of " + gameData.getNumOfSongs() + " added! Click 'Continue' below to proceed!");
					/*continueButton.setDisable(false);
				continueButton.setVisible(true);
				songNameField.setDisable(true);
				artistNameField.setDisable(true);
				addButton.setDisable(true);
				continueButton.requestFocus();*/
					disableSongAdding();


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
		int songNum = songList.getSelectionModel().getSelectedItem().getNumber();
		Song tempSong = songListHelper.createSong(songNum, songNameField.getText(), artistNameField.getText());
		tempSong.setNumber(songList.getSelectionModel().getSelectedItem().getNumber());
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
	 * DEPRECATED AS OF VERSION 0.4.0-ALPHA, REMOVE AFTER 1 VERSION IF NO BUGS WITH NEW CODE.
	 * TRANSITION ANYTHING STILL USING THIS TO NEW METHOD IN SONGLISTHELPER CLASS. //TODO
	 * 
	 * Helper method that edits a song object with the data that is passed to it via its parameters.
	 * @param song Song object to be edited.
	 * @param songName String to be used as the new song name.
	 * @param artistName String to be used as the new artist name.
	 * @return A Song object with the modified data properties.
	 * @deprecated
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
