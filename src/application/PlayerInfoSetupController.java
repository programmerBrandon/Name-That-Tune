package application;

import java.io.IOException;
import java.util.ArrayList;

import javax.print.attribute.standard.NumberOfDocuments;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
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
import javafx.scene.control.ListCell;


public class PlayerInfoSetupController {
	GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
	HeaderButtonsController headerButtonsController = new HeaderButtonsController();
	GameData gameData = new GameData();
	private int numPlayersAdded = 0; //Keeps track of how many players were added to the list (To compare to how many players are in the game).
	//ArrayList<Player> tempPlayerList = new ArrayList<>(); //This does the job of the array above using the Player class. 
	private ObservableList<Player> tempPlayerList = FXCollections.observableArrayList();
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
	
	// PlayerInfoSetup.fxml specific FXML objects //
	
	@FXML private ListView<Player> playerList;
	@FXML private Label playerInstructions;
	@FXML private Label editingInstructions;
	@FXML private TextField playerNameField; //Textbox for user to enter player's name/username.
	@FXML private Button addButton;
	@FXML private Button saveButton;
	@FXML private Label messageLabel;
	@FXML private Button continueButton;
	@FXML private Button editButton;
	@FXML private Button cancelButton;
	
	// End of PlayerInfoSetup.fxml specific objects
	
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
		/*playerInstructions.setText("Using the textbox below, please enter the name or username for each player. After typing in the "
				+ "information, double check that it was typed in correctly (i.e no typos) and then hit enter on your keyboard or press the "
				+ "'Add' button. The player's name/username will then appear in the list above. Repeat this step until all players are added. ");*/
		playerInstructions.setText("You add players to the game by following these simple steps:\n"
				+ "1. Enter the player name into the textbox below.\n"
				+ "2. Click the 'Add' button.\n"
				+ "3. Repeat the steps until all players are added.\n\n"
				+ "Note: Players cannot be edited after leaving this screen so be sure to check for typos before proceeding to the next screen.");
		
		editingInstructions.setText("You can edit a player that has already been added to the list using these simple steps:\n"
				+ "1. Select the player you want to edit from the list.\n"
				+ "2. Press the edit button at the bottom of the screen.\n"
				+ "3. Make the changes you want and then press save.\n\n"
				+ "If you change your mind about editing a song, just click cancel to abort.");
		playerList.setPlaceholder(new Label("No players to display"));
		/*playerNameField.focusedProperty().addListener((observable, oldValue, newValue) ->
		handleTextBox() );*/
		
		//Validate input as the user types by calling handleTextBox() method.
		playerNameField.setOnKeyTyped(event -> {
			handleTextBox();
		});
		
		playerList.setCellFactory(param -> new ListCell<Player>() {
	        @Override
	        protected void updateItem(Player player, boolean empty){
	        super.updateItem(player, empty);
	            if(empty || player == null){
	                setText("");
	            }
	            else{
	                setText(player.getName());
	            }
	         }
		});
		
		tempPlayerList.addListener((ListChangeListener<? super Player>) new ListChangeListener<Player>() {
			  public void onChanged(Change<? extends Player> c) {
				playerList.getItems().setAll(tempPlayerList);
			  }
			});
		
		playerList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if(playerList.getSelectionModel().getSelectedIndex() == -1) {
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
		
		addButton.setOnAction((event) -> {
			addButtonHandler();
		});
		
		
		continueButton.setOnAction(e -> {
			savePlayerList(tempPlayerList);
			try {
				Parent setPlayerScores = FXMLLoader.load(getClass().getResource("SetPlayerScores.fxml"));
				Scene setPlayerScoresScene = new Scene(setPlayerScores, globalValues.getProgramWidth(), globalValues.getProgramHeight());
				setPlayerScoresScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage setPlayerScoresStage = (Stage)((Node)e.getSource()).getScene().getWindow();
				setPlayerScoresStage.setScene(setPlayerScoresScene);
				setPlayerScoresStage.show();
				System.out.println("SetPlayerScores.fxml loaded successfully!");
			} catch (IOException e1) {
				e1.printStackTrace();
				messageLabel.setText("Error: Failed to load SetPlayerScores.fxml. If this error persists, please notify the developer.");
				System.out.println("Failed to load SetPlayerScores.fxml!");
			}
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
	}
	
	/** 
	 * Create and initialize a new Player object using data from the TextFIelds.
	 * @return A Player object
	 */
	private Player createPlayer() {
		Player player = new Player();
		
		player.setName(playerNameField.getText());
		
		return player;
	}
	
	/**
	 * Converts the temporary player list into a normal ArrayList and then saves it as the global player list.
	 * @param tempList An ObservableList containing Player objects.
	 */
	private void savePlayerList(ObservableList<Player> tempList) {
		ArrayList<Player> tempSongListCopy = new ArrayList<>(tempList);
		
		GameData.setPlayerList(tempSongListCopy);
		
		/*
		 //DEBUGGING STATEMENTS!
		 tempSongListCopy = gameData.getSongList();
		
		//FIXME FOR LOOP
		for(int i = 0; i < tempSongListCopy.size(); i++) {
			System.out.println("FIXME: songList(" + i + "): " + tempSongListCopy.get(i)); //FIXME
		}*/
	}
	
	/**
	 * Handler for the player name TextField that performs all text validation and displays error messages to the user
	 * if one or more of the validation checks fail. Also prevents the user for progressing any further until errors are resolved.
	 */
	public void handleTextBox() {
		//System.out.println("FIXME: handleTextBox() method called!"); //FIXME
	
		if(playerNameField.getText().trim().isEmpty()) {
			  messageLabel.setText("Error: Player name field cannot be left blank!");
			  //playerNameField.setStyle("-fx-text-box-border: #ff0000;");
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
		
		playerNameField.setOnKeyPressed( event -> {
			if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
				  
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
	 * Handler method for the add button. It handles all logic required to add a new player to the temporary player list. 
	 */
	public void addButtonHandler() { 
		Player player;
		
		//System.out.println("FIXME: addButtonHandler() method called!"); //FIXME
		
		if ((numPlayersAdded < gameData.getNumOfPlayers())) {
			numPlayersAdded++;
			//System.out.println("Fixme: numPlayersAdded: " + numPlayersAdded); //FIXME
			player = createPlayer();
			//playerNameList[numPlayersAdded - 1] = playerNameField.getText();
			//System.out.println("FIXME: playerNameList[" + (numPlayersAdded - 1) + "] (Local): " + playerNameList[numPlayersAdded - 1]);
			
			//player.setName(playerNameField.getText());
			//player.setPoints(0);
			
			
			
			if(duplicateChecker(tempPlayerList, player)) {
				//messageLabel.setText("Error: " + song.getName() + " By: " + song.getArtist() + " is already in the list. "
				//		+ "Please enter a different song!");
				messageLabel.setText("Error: Player is already in the list. Please enter a different name!");
				messageLabel.setVisible(true);
				addButton.setDisable(true);
				numPlayersAdded--;
				playerNameField.clear();
				playerNameField.requestFocus();
			}
			
			else {
				tempPlayerList.add(player);				
				//playerList.getItems().add(player);
				//songList.getItems().add(songNameField.getText());
				  
				
				messageLabel.setText("Players " + numPlayersAdded + " of " + gameData.getNumOfPlayers() + " added!");
				messageLabel.setVisible(true);

				
				if (numPlayersAdded == gameData.getNumOfPlayers()) {
					ArrayList<Player> tempPlayerListCopy = new ArrayList<Player>(); 
					GameData.setPlayerList(tempPlayerListCopy);
					messageLabel.setText("Players " + numPlayersAdded + " of " + gameData.getNumOfPlayers() + " added! Click 'Continue' below to proceed!");
					continueButton.setDisable(false);
					continueButton.setVisible(true);
					continueButton.setDisable(false);
					continueButton.setVisible(true);
					playerNameField.setDisable(true); 
					addButton.setDisable(true);
					continueButton.requestFocus();
					
				
				}
				
				playerNameField.clear();
				addButton.setDisable(true);
				playerNameField.requestFocus();
			
			/*messageLabel.setText("Player " + numPlayersAdded + " of " + gameData.getNumOfPlayers() + " added!");
			messageLabel.setVisible(true);
			playerNameField.clear();
			playerNameField.requestFocus();*/
			}
		}
		
		/*if (numPlayersAdded == gameData.getNumOfPlayers()) {
			
			//DEBUGGING STATEMENTS
			for(int i = 0; i < tempPlayerList.size(); i++) {
				System.out.println("FIXME: tempPlayerList.get(" + i + ") " + tempPlayerList.get(i)); //FIXME
			}
		}*/
	}
	
	/**
	 * Handler for the edit button. It is responsible for putting the program in edit mode.
	 */
	private void editButtonHandler() {
		if(playerList.getSelectionModel().getSelectedItem() == null) {
			editModeEnabled = false;
			editButton.setDisable(true);
		}
		
		else {
			editModeEnabled = true;
			addButton.setVisible(false);
			
			
			playerNameField.setDisable(false);
			playerNameField.setText(playerList.getSelectionModel().getSelectedItem().getName());
			
			saveButton.setDisable(false);
			saveButton.setVisible(true);
			editButton.setDisable(true);
			editButton.setVisible(false);
			continueButton.setVisible(false);
			cancelButton.setDisable(false);
			cancelButton.setVisible(true);
			
			playerNameField.requestFocus();
			messageLabel.setText("*Now editing player " + playerList.getSelectionModel().getSelectedItem().getName() + " *");
		}
	}
	
	/**
	 * Cancels edit mode and returns the program back to the normal player adding process.
	 * @param editSaved A boolean value - True if a successful edit was done. False in all other cases.
	 */
	private void cancelButtonHandler(boolean editSaved) {
		editModeEnabled = false;
		playerNameField.clear();
		saveButton.setVisible(false);
		saveButton.setDisable(true);
		cancelButton.setVisible(false);
		cancelButton.setDisable(true);
		addButton.setVisible(true);
		
		if(numPlayersAdded < gameData.getNumOfPlayers()) {
			if(editSaved) {
				messageLabel.setText("*Edit saved successfully! Please continue where you left off.*");
			}
			
			else {
				messageLabel.setText("*Edit cancelled. Please continue where you left off.*");
			}
			
			messageLabel.setVisible(true);
			playerNameField.requestFocus();
		}
		
		else {
			if(editSaved) {
				messageLabel.setText("*Edit saved successfully! Please click 'Continue' below to proceed to the next step.*");
			}
			
			else {
				messageLabel.setText("*Edit cancelled. Please click 'Continue' below to proceed to the next step.*");
			}
			
			messageLabel.setVisible(true);
			playerNameField.setDisable(true);
			continueButton.setVisible(true);
			continueButton.setDisable(false);
			continueButton.requestFocus();
		}
	}
	
	/**
	 * Handler for the save button that is responsible for getting the new player information, validating it and then saving the 
	 * new player data to the temporary player list. 
	 */
	private void saveButtonHandler() {
		Player tempPlayer = createPlayer();
		String editedPlayerName = playerNameField.getText();
		int index = playerList.getSelectionModel().getSelectedIndex();
		Player editedPlayer = editPlayer(tempPlayer, editedPlayerName);
		
		if(!duplicateChecker(tempPlayerList, editedPlayer)) {
			tempPlayerList.set(index, editedPlayer);
			playerList.getSelectionModel().clearSelection();
			cancelButtonHandler(true);
		}
		else {
			messageLabel.setText("Error: Duplicate player detected! The edit failed because a player with the same name "
					+ "is already in the list. Please try again!");
			messageLabel.setVisible(true);
			playerNameField.requestFocus();
			
		}
	}
	
	
	private Player editPlayer(Player player, String playerName) {
		player.setName(playerName);
		
		return player;
	}
	
	/**
	 * Accepts an ArrayList of type Player and a Player object as arguments.
	 * Iterates through the argument ArrayList and checks to see if the list contains
	 * a player matching the argument Player object (compared by name).
	 * @param tempPlayerList2
	 * @param player
	 * @return true is a duplicate player (case insensitive) is found, false otherwise.
	 */
	public boolean duplicateChecker(ObservableList<Player> tempPlayerList2, Player player) {
		tempPlayerList2 = tempPlayerList;
		
			for(int i = 0; i < tempPlayerList2.size(); i++) {
				if(tempPlayerList2.get(i).equals(player)) {
					/*messageLabel.setText("Error: " + song.getName() + " " + song.getArtist() + " has already been added. Duplicates are "
						+ "not permitted. Please enter a different song!");
					messageLabel.setVisible(true);*/
					//System.out.println("FIXME: Duplicate entry detected!");
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
