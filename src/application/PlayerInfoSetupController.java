package application;

import java.io.IOException;

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
	Main mainClass = new Main();
	GameData gameData = new GameData();
	private int numPlayersAdded = 0; //Keeps track of how many players were added to the list (To compare to how many players are in the game).
	String[] playerNameList = new String[gameData.getNumOfPlayers()];
	
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
	
	@FXML private ListView playerList;
	@FXML private Label instructionsLabel; //Used to give user instructions on what data to provide, etc.
	@FXML private TextField playerNameField; //Textbox for user to enter player's name/username.
	@FXML private Button addButton; //Button to add song to 'playerList' listview and playerList[] (GamaData class).
	@FXML private Label messageLabel;
	@FXML private Button continueButton;
	
	// End of PlayerInfoSetup.fxml specific objects
	
	//Set text values for header and footer text using values from 'GlobalValues'.
	@FXML
	public void initialize()  {
	globalValues.currentFXML = "MainScene.fxml";
	headerIcon.setImage(globalValues.programIcon);
	homeIcon.setImage(globalValues.homeIcon);
	infoIcon.setImage(globalValues.infoIcon);
	programName.setText(globalValues.programNameText);
	copyrightText.setText(globalValues.copyrightText);
	versionText.setText(globalValues.versionNumberText);
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
			Scene setPlayerScoresScene = new Scene(setPlayerScores, globalValues.programWidth, globalValues.programHeight);
			setPlayerScoresScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage setPlayerScoresStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		    setPlayerScoresStage.setScene(setPlayerScoresScene);
		    setPlayerScoresStage.show();
		    System.out.println("SetPlayerScores.fxml loaded successfully!");
		} catch (IOException e1) {
			e1.printStackTrace();
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
		System.out.println("FIXME: addButtonHandler() method called!"); //FIXME
		
		if ((numPlayersAdded < gameData.getNumOfPlayers())) {
			numPlayersAdded += 1;
			System.out.println("Fixme: numPlayersAdded: " + numPlayersAdded); //FIXME
			playerNameList[numPlayersAdded - 1] = playerNameField.getText();
			System.out.println("FIXME: playerNameList[" + (numPlayersAdded - 1) + "] (Local): " + playerNameList[numPlayersAdded - 1]);
			gameData.setPlayerScores(playerNameField.getText(), 0); //Adds player name as key in playerList Hashmap, with value of 0 (points).
			playerList.getItems().add(playerNameField.getText());
			
			messageLabel.setText("Player " + numPlayersAdded + " of " + gameData.getNumOfPlayers() + " added!");
			messageLabel.setVisible(true);
			playerNameField.clear();
			playerNameField.requestFocus();
		}
		
		if (numPlayersAdded == gameData.getNumOfPlayers()) {
			gameData.setPlayerNameList(playerNameList);
			messageLabel.setText("Players " + numPlayersAdded + " of " + gameData.getNumOfPlayers() + " added! Click 'Continue' below to proceed!");
			continueButton.setDisable(false);
			continueButton.setVisible(true);
			playerNameField.setDisable(true); 
			addButton.setDisable(true);
			
			String[] playerNameList1 = gameData.getPlayerNameList();
			
			for(int i = 0; i < gameData.getNumOfPlayers(); i++ ) {
				System.out.println("FIXME: playerNameList[" + i + "] " + playerNameList1[i]); //FIXME
				System.out.println("FIXME: Player points from PlayersList: " + gameData.getPlayerScores(playerNameList1[i])); //FIXME
			}
		}
	}
}
