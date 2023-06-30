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


public class PlayerInfoSetupController {
	GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
	HeaderButtonsController headerButtonsController = new HeaderButtonsController();
	GameData gameData = new GameData();
	private int numPlayersAdded = 0; //Keeps track of how many players were added to the list (To compare to how many players are in the game).
	ArrayList<Player> tempPlayerList = new ArrayList<>(); //This does the job of the array above using the Player class. 
	
	//IDEA FOR CODE RESTRUCTURE: STORE NAMES IN A HASHMAP WITH POINTS INTIALIZED TO ZERO, THEN AFTER ALL POINTS ARE ADDED, MAKE AN ARRAYLIST OF
	//TYPE 'Player' for sorting the results and checking for ties.
	
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
	
	@FXML private ListView<String> playerList;
	@FXML private Label instructionsLabel; //Used to give user instructions on what data to provide, etc.
	@FXML private TextField playerNameField; //Textbox for user to enter player's name/username.
	@FXML private Button addButton; //Button to add song to 'playerListView' listview and playerListView[] (GamaData class).
	@FXML private Label messageLabel;
	@FXML private Button continueButton;
	
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
		instructionsLabel.setText("Using the textbox below, please enter the name or username for each player. After typing in the "
				+ "information, double check that it was typed in correctly (i.e no typos) and then hit enter on your keyboard or press the "
				+ "'Add' button. The player's name/username will then appear in the list above. Repeat this step until all players are added. ");
		playerList.setPlaceholder(new Label("No players to display"));
		playerNameField.focusedProperty().addListener((observable, oldValue, newValue) ->
		handleTextBox() );
		addButton.setOnAction((event) -> {
			addButtonHandler();
		});
		continueButton.setOnAction(e -> {
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
	}
	
	//Method to check that the textbox is not blank (i.e user didn't type anything), and if textbox isn't blank it will enable add button
	//and give focus to the add button once the user has pressed enter or tab on their keyboard.
	public void handleTextBox() {
		System.out.println("FIXME: handleTextBox() method called!"); //FIXME
	
		playerNameField.setOnKeyTyped(event -> {
			
			if(!playerNameField.getText().trim().isEmpty()) {
				addButton.setDisable(false);
				messageLabel.setVisible(false);
				
				if(!addButton.isDisabled()) {
					playerNameField.setOnKeyPressed(e -> { 
						if(e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.TAB) { 
							addButton.requestFocus();
						}
					});
				}
		} 
			else {
				addButton.setDisable(true);
			
			/*if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) { 
				messageLabel.setText("Error: Song name field cannot be left blank!");
				messageLabel.setVisible(true);
			} 
			*/
			}
		});

	}
	
	public void addButtonHandler() { 
		Player player = new Player();
		
		System.out.println("FIXME: addButtonHandler() method called!"); //FIXME
		
		if ((numPlayersAdded < gameData.getNumOfPlayers())) {
			numPlayersAdded++;
			System.out.println("Fixme: numPlayersAdded: " + numPlayersAdded); //FIXME
			//playerNameList[numPlayersAdded - 1] = playerNameField.getText();
			//System.out.println("FIXME: playerNameList[" + (numPlayersAdded - 1) + "] (Local): " + playerNameList[numPlayersAdded - 1]);
			
			player.setName(playerNameField.getText());
			player.setPoints(0);
			
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
				playerList.getItems().add(player.toStringNoPoints());
					//songList.getItems().add(songNameField.getText());
				  
				
				messageLabel.setText("Players " + numPlayersAdded + " of " + gameData.getNumOfPlayers() + " added!");
				messageLabel.setVisible(true);

				
				if (numPlayersAdded == gameData.getNumOfPlayers()) {
					gameData.setPlayerList(tempPlayerList);
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
		
		if (numPlayersAdded == gameData.getNumOfPlayers()) {
			
			for(int i = 0; i < tempPlayerList.size(); i++) {
				System.out.println("FIXME: tempPlayerList.get(" + i + ") " + tempPlayerList.get(i)); //FIXME
			}
		}
	}
	
	/**
	 * Accepts an ArrayList of type Player and a Player object as arguments.
	 * Iterates through the argument ArrayList and checks to see if the list contains
	 * a player matching the argument Player object (compared by name).
	 * @param tempList
	 * @param player
	 * @return true is a duplicate player (case insensitive) is found, false otherwise.
	 */
	public boolean duplicateChecker(ArrayList<Player> tempList, Player player) {
		tempList = tempPlayerList;
		
			for(int i = 0; i < tempList.size(); i++) {
				if(tempList.get(i).equals(player)) {
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
