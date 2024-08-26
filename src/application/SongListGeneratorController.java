package application;

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
import javafx.stage.Window;

public class SongListGeneratorController {
	GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
	HeaderButtonsController headerButtonsController = new HeaderButtonsController();
	GameData gameData = new GameData();
	GameSetupController gameSetupController = new GameSetupController();
	SongListHelper songListHelper = new SongListHelper();
	Save save = new Save();
	private int numSongsAdded = 0; //Keeps track of how many songs were added to the list (To compare to how many songs are in the game).
	//ArrayList<Song> tempSongList = new ArrayList<>();
	private ObservableList<Song> tempSongList = FXCollections.observableArrayList();
	private boolean editModeEnabled = false;
	private Title title = new Title("");
	
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
	
	// SongListGenerator.fxml specific objects. //
	@FXML private TextField titleTextField;
	@FXML private ListView<Song> songList;
	@FXML private Label songInstructions;
	@FXML private Label editingInstructions;
	@FXML private Label aboutFeature;
	@FXML private TextField songNameField; //Textbox for user to enter song name.
	@FXML private TextField artistNameField; //Textbox for user to enter artist name.
	@FXML private Button addButton; //Button to add song to 'songList' listview and songList[] (GamaData class).
	@FXML private Button saveButton;
	@FXML private Label messageLabel;
	//@FXML private Button continueButton;
	@FXML private Button editButton;
	@FXML private Button cancelButton;
	@FXML private Button generateButton;
	@FXML private Button mainMenuButton;
	@FXML private Button clearButton;
	@FXML private Button setTieBreakerBtn;
	// End of SongListGenerator.fxml specific objects. //
	
	// Footer items //
	
	@FXML private Label copyrightText;
	@FXML private Label versionText;
	
	//  End of Global FXML Objects //
	
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
			songList.setPlaceholder(new Label("No songs to display"));
			songInstructions.setText("Using the textboxes below, please enter the song name and artist name for each song. After typing in the "
				+ "information, double check that it was typed in correctly (i.e no typos) and then hit enter on your keyboard or press the "
				+ "'Add' button. The song and artist will then appear in the list above. Repeat this step until all songs are added. ");
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
			
			aboutFeature.setText("The song list generator allows you to create a song list and save it to a text file.\n"
					+ "You can use the save file to import the song list during game setup. This allows you \n"
					+ " to save time during setup by not having to input all the songs manually.");
		
			//Validate input as the user types by calling handleTextBox() method.
			songNameField.setOnKeyTyped(event -> {
				handleTextBox();
			});
			
			
			// Event handler for titleTextField.
			titleTextField.setOnKeyTyped(event -> {
				handleTitleTextField();
			});
			
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
					
					setTieBreakerBtn.setDisable(false);
					setTieBreakerBtn.setVisible(true);
					
					if(!editModeEnabled) {
						clearButton.setVisible(false);
						editButton.setVisible(true);
						//editButton.setDisable(false);
					
						//continueButton.setVisible(true);
					}
					
					else {
						editButtonHandler();
					}
				}
				
			});
			
			generateButton.setOnAction(e -> {
				title.setPrefix("Song List");
				title.saveTitle();
				saveSongList(tempSongList);
				//System.out.println("FIXME: GameData.getTitle(): " + GameData.getTitle().toString());
				Window window = generateButton.getScene().getWindow();
				save.openSaveDialog(window);
				mainMenuButton.setVisible(true);
				mainMenuButton.setDisable(false);
				clearButton.setVisible(true);
				editButton.setVisible(false);
				setTieBreakerBtn.setVisible(false);
				setTieBreakerBtn.setDisable(true);
			});
			
			clearButton.setOnAction(e -> {
				clear();
			});
			
			mainMenuButton.setOnAction(e -> {
				try {
					Parent parent = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
					Scene scene = new Scene(parent, globalValues.getProgramWidth(), globalValues.getProgramHeight());
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
					stage.setScene(scene);
					stage.show();
					System.out.println("MainScene.fxml loaded successfully!");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println("Failed to load MainScene.fxml!");
				}
			});
			
			setTieBreakerBtn.setOnAction(e -> {
				setTieBreakerBtnHandler();
			});
		}
		
		/**
		 * Validates the text in title TextField to ensure it does not contain any illegal characters.
		 */
		public void handleTitleTextField() {
			title = new Title(titleTextField.getText());
			titleTextField.setStyle("-fx-text-box-border: transparent; -fx-focus-color: #039ED3; -fx-text-fill: #000; ");
			messageLabel.setVisible(false);
			//continueButton.setDisable(false);
			//Pattern invalidCharsPattern = Pattern.compile("[<>:/|?\"\\\\*.]");
			//Matcher matcher = invalidCharsPattern.matcher(titleTextField.getText());
			
			if(title.validateTitle()) {
				titleTextField.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: ff0000; -fx-text-fill: #ff0000;");
				messageLabel.setText("Error: Invalid character entered! Title cannot contain the following characters: < > : \" / \\ . | ? *");
				messageLabel.setVisible(true);
				generateButton.setDisable(true);
				songNameField.setDisable(true);
				artistNameField.setDisable(true);
				editButton.setDisable(true);
				addButton.setVisible(false);
				//addButton.setDisable(true);
				//continueButton.setDisable(true);
				return;
			}
			
			else {
				titleTextField.setStyle("-fx-text-box-border: transparent; -fx-focus-color: #039ED3; -fx-text-fill: #000; ");
				messageLabel.setVisible(false);
				generateButton.setDisable(false);
				songNameField.setDisable(false);
				artistNameField.setDisable(false);
				editButton.setDisable(false);
				addButton.setVisible(true);
				//continueButton.setDisable(false);
				return;
			}
		}
		
		/**
		 * Handler for the song name and artist textboxes that performs all text validation and displays error messages to the user
		 * if one or more of the validation checks fail. Also prevents the user for progressing any further until errors are resolved.
		 */
		public void handleTextBox() {
			songNameField.setStyle("-fx-text-box-border: transparent; -fx-focus-color: #039ED3; -fx-text-fill: #000; ");
			//messageLabel.setVisible(false);
			//generateButton.setDisable(true);
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

			if ((numSongsAdded <= 9)) {
				numSongsAdded++;
				song = songListHelper.createSong(numSongsAdded, songNameField.getText(), artistNameField.getText());
				//song.setNumber(numSongsAdded);
				//song.setName(songNameField.getText());
				//song.setArtist(artistNameField.getText());
				//System.out.println("FIXME: song: " + song.toString());
				//duplicateChecker(tempSongList, song);
				
				//System.out.println("FIXME: songListHelper.duplicateChecker() returned " + songListHelper.duplicateChecker(tempSongList, song)); //FIXME
				if(songListHelper.duplicateChecker(tempSongList, song)) {
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
					messageLabel.setText("Song " + numSongsAdded +  " added!");
					messageLabel.setVisible(true);
					
					if(numSongsAdded >= 1) {
						generateButton.setDisable(false);
						generateButton.setVisible(true);
					}

					/*
					if (numSongsAdded == 10) {
						messageLabel.setText("Song " + numSongsAdded + " of " + gameData.getNumOfSongs() + " added! Click 'Continue' below to proceed!");
						//continueButton.setDisable(false);
						//continueButton.setVisible(true);
						songNameField.setDisable(true);
						artistNameField.setDisable(true);
						addButton.setDisable(true);
						//continueButton.requestFocus();
					}
					*/
					
					if(numSongsAdded == 10) {
						songNameField.setDisable(true);
						artistNameField.setDisable(true);
						addButton.setDisable(true);
						messageLabel.setText("Max number of songs has been reached!");
						messageLabel.setVisible(true);
						generateButton.requestFocus();
					}

					songNameField.clear();
					artistNameField.clear();
					songNameField.requestFocus();
					editButton.setVisible(false);
					setTieBreakerBtn.setVisible(false);
				}
				/*
				 //DEBUGGING STATEMENNTS 
				 for(int i = 0; i < tempSongList.size(); i++) {
					System.out.println("FIXME: tempSongList.get(" + i + ") " + tempSongList.get(i)); //FIXME
				}			
				//System.out.println("Fixme: numSongsAdded: " + numSongsAdded); //FIXME
				 */
			}
			
			else {
				/*songNameField.setDisable(true);
				artistNameField.setDisable(true);
				addButton.setDisable(true);
				messageLabel.setText("Max number of songs has been reached. Please click the 'Generate List' button to generate the list file!");
				messageLabel.setVisible(true);*/
			}
		}
		
		/**
		 * Handler for the edit button. It is responsible for putting the program in edit mode.
		 */
		private void editButtonHandler() {
			if(songList.getSelectionModel().getSelectedItem() == null) {
				editModeEnabled = false;
				editButton.setVisible(false);
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
				//editButton.setDisable(true);
				editButton.setVisible(false);
				generateButton.setVisible(false);
				//clearButton.setVisible(false);
				cancelButton.setDisable(false);
				cancelButton.setVisible(true);
				mainMenuButton.setDisable(true);
				mainMenuButton.setVisible(false);
				setTieBreakerBtn.setVisible(false);
				
				
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
			generateButton.setVisible(true);
			//generateButton.setDisable(false);
			
			if(numSongsAdded < 10) {
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
					messageLabel.setText("*Edit saved successfully! Please click 'Generate List' below to proceed.*");
				}
				
				else {
					messageLabel.setText("*Edit cancelled. Please click 'Generate List' below to proceed.*");
				}
				
				
				messageLabel.setVisible(true);
				songNameField.setDisable(true);
				artistNameField.setDisable(true);
				generateButton.requestFocus();
			}
			
			if(mainMenuButton.isVisible()) {
				editButton.setVisible(false);
				clearButton.setVisible(true);
				clearButton.setDisable(false);
				mainMenuButton.setDisable(false);
			}
		}
		
		/**
		 * Handler for the save button that is responsible for getting the new song information, validating it and then saving the 
		 * new song data to the temporary song list. 
		 */
		private void saveButtonHandler() {
			//Song selectedSong = songList.getSelectionModel().getSelectedItem();
			Song tempSong = songListHelper.createSong(songList.getSelectionModel().getSelectedItem().getNumber(), songNameField.getText(), artistNameField.getText());
			//tempSong.setNumber(songList.getSelectionModel().getSelectedItem().getNumber());
			//String editedSongName = songNameField.getText();
			//String editedArtistName = artistNameField.getText();
			int index = songList.getSelectionModel().getSelectedIndex();

			if(!songListHelper.duplicateChecker(tempSongList, tempSong)) {
				tempSongList.set(index, tempSong);
				songList.getSelectionModel().clearSelection();
				cancelButtonHandler(true);
			}
			else {
				messageLabel.setText("Error: Duplicate song detected! The edit failed because a song with the same name and artist "
						+ "is already in the list. Please try again!");
				messageLabel.setVisible(true);
				songNameField.requestFocus();
				
			}
		}
		
		/**
		 * 
		 */
		private void setTieBreakerBtnHandler() {
			if(songList.getSelectionModel().getSelectedItem() == null) {
				return;
			}
			
			else {
				Song selectedSong = songList.getSelectionModel().getSelectedItem();
				TieBreakerMode.setTieBreakerSong(selectedSong);
				//selectedSong.setAsTieBreaker(true);
				
				messageLabel.setText("Song " + TieBreakerMode.getTieBreakerSong().getNumber() + " set as tiebreaker song!");
				//System.out.println("FIXME: getIsTieBreaker(): " + selectedSong.getIsTieBreaker()); //FIXME 
			}
		}
		
		/**
		 * Clears all TextFields and sets all buttons back to their default settings.
		 */
		private void clear() {
			tempSongList.clear();
			titleTextField.clear();
			songNameField.clear();
			artistNameField.clear();
			numSongsAdded = 0;
			GameData.setTitle(new Title());
			saveSongList(tempSongList);
			generateButton.setDisable(true);
			mainMenuButton.setVisible(false);
			clearButton.setVisible(false);
			addButton.setDisable(true);
			messageLabel.setText("All Data cleared!");
		}
		
		/**
		 * 
		 * @param tempList
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
}
