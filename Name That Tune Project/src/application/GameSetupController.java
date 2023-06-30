package application;

import java.io.IOException;

import javax.swing.event.ChangeEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class GameSetupController {
	//Controller class for GameSetup.fxml.
	GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
	HeaderButtonsController headerButtonsController = new HeaderButtonsController();
	GameData gameData = new GameData();
	final int MAX_PLAYERS = 25; //Maximum number of players supported per game.
	
	// Begin 'Global' FXML objects. //
	@FXML private AnchorPane programBody; //Main AnchorPane
	@FXML private BorderPane header; //Top BorderPane that is used as a container for the header.
	@FXML private BorderPane footer; //Bottom BorderPane used as a container for footer items.
	
	//Header objects
	@FXML private ImageView headerIcon;
	@FXML private Label programName;
	
	//Menu and menu items (also part of header items)
	@FXML private Button homeButtonHeader;
	@FXML private Button infoButtonHeader;
	@FXML private ImageView homeIcon;
	@FXML private ImageView infoIcon;
	
	// End of Header Objects //
	
	// Footer items //
	
	@FXML private Label copyrightText;
	@FXML private Label versionText;
	
	//  End of Global FXML Objects //
	
	// GameSetup.fxml specific FXML objects //
	@FXML private Label instructionsLabel;
	@FXML private ChoiceBox<Integer> numOfSongsSelector;
	@FXML private TextField textBoxNumOfPlayers;
	@FXML private Slider tieBreakerSlider;
	@FXML private Label tieBreakerStatus;
	@FXML private Label warningLabel;
	@FXML private Button continueButton;
	// End of GameSetup.fxml specific objects //
	
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
		instructionsLabel.setText("This program requires some basic information to get started. This information is: the number of songs that "
			+ "will be played, the number of players participating (Note; the maximum players supported is " + MAX_PLAYERS + "). Using "
					+ "the dropdown box provided below, select the number of songs that will be played. "
					+ "After you have selected the number of songs, click the textbox below and enter (in numerical form)"
					+ " the number of players. After you have finished typing, press 'Enter' to verify that what you have"
					+ " entered is valid. If you have entered a valid number, then the 'Continue' button will be outlined. Press "
					+ "'Continue' to proceed to the next step of the process."
			);
	
		// Code adding the choices to the ChoiceBox numOfSongsSelector 
		numOfSongsSelector.setValue(1);
		numOfSongsSelector.getItems().add(1);
		numOfSongsSelector.getItems().add(2);
		numOfSongsSelector.getItems().add(3);
		numOfSongsSelector.getItems().add(4);
		numOfSongsSelector.getItems().add(5);
		numOfSongsSelector.getItems().add(6);
		numOfSongsSelector.getItems().add(7);
		numOfSongsSelector.getItems().add(8);
		numOfSongsSelector.getItems().add(9);
		numOfSongsSelector.getItems().add(10);
	
		gameData.setNumOfSongs(1); //Sets default value of 1 (will be overwritten by user selected value if choiceBoxNumSongsPush() is called.
	
		// Event handler code for when a value is selected from the Choice Box
		numOfSongsSelector.setOnAction((event) -> {
			choiceBoxNumSongsPush();
		});
	
		//Validate input as the user types by calling handleTextBoxText() method.
		textBoxNumOfPlayers.setOnKeyTyped(event -> {
			handleTextBoxText();
		});
	
	/*textBoxNumOfPlayers.focusedProperty().addListener((observable, oldValue, newValue) ->
	handleTextBoxText() );*/
		
		tieBreakerSlider.setValue(tieBreakerSlider.getMax());
		tieBreakerModeToggle();
	
		tieBreakerSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) { 
				tieBreakerModeToggle();
			}
		});
	
	continueButton.setOnAction(e -> {
		try {
			Parent songInfoSetup = FXMLLoader.load(getClass().getResource("SongInfoSetup.fxml"));
			Scene songInfoSetupScene = new Scene(songInfoSetup, globalValues.getProgramWidth(), globalValues.getProgramHeight());
			songInfoSetupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage releaseInfoStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		    releaseInfoStage.setScene(songInfoSetupScene);
		    releaseInfoStage.show();
		    System.out.println("SongInfoSetup.fxml loaded successfully!");
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("Failed to load SongInfoSetup.fxml!");
		}
	});
	}
	
	public void choiceBoxNumSongsPush() {
		//System.out.println("FIXME: choiceBoxNumSongsPush() method has been called successfully!"); //FIXME
		int numSelected = (int) numOfSongsSelector.getValue();
		gameData.setNumOfSongs(numSelected);
		//System.out.println("FIXME: numOfSongs: " + gameData.getNumOfSongs()); //FIXME
	}
	
	/**
	 * Helper method to validate the input of the number of players text box. If an input is invalid, the continue button
	 * will be automatically disabled and an error message displayed. The method is called at every time a key pressed
	 * while the text box is in focus. 
	 */
	public void handleTextBoxText() {
		textBoxNumOfPlayers.setStyle("-fx-text-box-border: transparent; -fx-focus-color: #039ED3; -fx-text-fill: #000; ");
		warningLabel.setVisible(false);
		continueButton.setDisable(true);
		String textBoxInput; // Variable to temporarily hold input value so it can be checked for validity
		
		//Checking the input validation to ensure user enters a valid number.
		try {
			textBoxInput = textBoxNumOfPlayers.getText().trim();
			
			int inputToInt = Integer.parseInt(textBoxInput);
			//System.out.println("FIXME: " + textBoxInput + " successfully converted to an integer!");
			
			
			//Checks if the number of players entered by user exceeds MAX_PLAYERS limit. Gives error message to user if it does.
			if(inputToInt > MAX_PLAYERS) { 
				textBoxNumOfPlayers.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: ff0000; -fx-text-fill: #ff0000;");
				warningLabel.setText("Error: The maximum number of players allowed is " + MAX_PLAYERS + ". Please try again!");
				warningLabel.setVisible(true);
				continueButton.setDisable(true);
				return;
			}
			
			if(inputToInt < 0) {
				textBoxNumOfPlayers.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: ff0000; -fx-text-fill: #ff0000;");
				warningLabel.setText("Error: Number entered cannot be negative. Please try again!");
				warningLabel.setVisible(true);
				continueButton.setDisable(true);
				return;
			}
			
			if(inputToInt == 0) {
				textBoxNumOfPlayers.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: ff0000; -fx-text-fill: #ff0000;");
				warningLabel.setText("Error: Number entered cannot be 0. Please try again!");
				warningLabel.setVisible(true);
				continueButton.setDisable(true);
				return;
			}
			
			//If the input is a valid int AND the integer is <= MAX_PLAYERS limit, then continue button is enabled and the warning label
			//will be hidden if it was visible. The number entered by the user is then saved in the numOfPlayers variable in class GameData.
			else {
				textBoxNumOfPlayers.setStyle("-fx-text-box-border: transparent; -fx-focus-color: #039ED3; -fx-text-fill: #000; ");
				warningLabel.setVisible(false);
				continueButton.setDisable(false);
				gameData.setNumOfPlayers(inputToInt);
				//System.out.println("FIXME: numOfPlayers: " + gameData.getNumOfPlayers());
			}
		} 
		
		//Handles all errors caused by invalid user input except for no input/blank textbox and MAX_PLAYERS limit being exceeded. 
		catch(NumberFormatException e) {
			if(!textBoxNumOfPlayers.getText().trim().isEmpty()) {
				textBoxNumOfPlayers.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: ff0000; -fx-text-fill: #ff0000;");
				warningLabel.setText("Error: Invalid character entered! Please enter a number!");
				warningLabel.setVisible(true);
				continueButton.setDisable(true);
		 }
		}
		
		textBoxNumOfPlayers.setOnKeyPressed( event -> {
			  if( event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
				  continueButton.setDisable(false);
				  continueButton.requestFocus();
				  
				  //Check if textbox is blank/no input entered by user. 
				  if(textBoxNumOfPlayers.getText().trim().isEmpty()) {
					  warningLabel.setText("Error: Number of players field cannot be left blank!");
					  textBoxNumOfPlayers.setStyle("-fx-text-box-border: #ff0000;");
					  warningLabel.setVisible(true);
					  continueButton.setDisable(true);
					  }
				  }
				});

	}
	
	
	public void tieBreakerModeToggle() {
		System.out.println("FIXME: tieBreakerModeToggle() called!"); //FIXME
		System.out.println("FIXME: tieBreakerSlider value: " + tieBreakerSlider.getValue()); //FIXME
		if(tieBreakerSlider.getValue() == 0.0) {
			tieBreakerStatus.setText("Off");
		}
		
		if(tieBreakerSlider.getValue() == 1.0) {
			tieBreakerStatus.setText("On");
		}
		
		int tieBreakerValue = (int)tieBreakerSlider.getValue();
		gameData.setTieBreakerMode(tieBreakerValue);
		int tieBreakerMode = gameData.getTieBreakerMode(); //FIXME
		System.out.println("FIXME: tieBreakerMode value is: " + tieBreakerMode); //FIXME
	}

}
