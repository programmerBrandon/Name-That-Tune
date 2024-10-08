package application;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
	private Title title = new Title("");
	
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
	@FXML private TextField titleTextField;
	@FXML private ChoiceBox<Integer> numOfSongsSelector;
	@FXML private TextField textBoxNumOfPlayers;
	@FXML private Slider tieBreakerSlider;
	@FXML private Label tieBreakerStatus;
	@FXML private Label messageLabel;
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
			+ "will be played, the number of players participating (Note: the maximum players supported is " + MAX_PLAYERS + "). Using "
					+ "the dropdown box provided below, select the number of songs that will be played. "
					+ "After you have selected the number of songs, click the textbox below and enter (in numerical form)"
					+ " the number of players. After you have finished typing, press 'Enter' to verify that what you have"
					+ " entered is valid. If you have entered a valid number, then the 'Continue' button will be outlined. Press "
					+ "'Continue' to proceed to the next step of the process."
			);
		continueButton.setVisible(false);
		continueButton.setDisable(false);
		
	
		// Add the choices to the ChoiceBox numOfSongsSelector 
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
		
		// Event handler for titleTextField.
		titleTextField.setOnKeyTyped(event -> {
			handleTitleTextField();
		});
	
		// Event handler code for when a value is selected from the Choice Box.
		numOfSongsSelector.setOnAction((event) -> {
			choiceBoxNumSongsPush();
		});
	
		//Validate input as the user types by calling handleTextBoxText() method.
		textBoxNumOfPlayers.setOnKeyTyped(event -> {
			if(textBoxNumOfPlayers.getText().length() > 2) {
				removeTextFieldOverflow();
			}
			handlePlayerTextBoxText();
		});
		
		//Set the tie breaker toggle switch's default value.
		tieBreakerSlider.setValue(tieBreakerSlider.getMax());
		tieBreakerModeToggle(); //Initialize tie breaker mode settings.
		
		//Listen for changes to the tie breaker toggle switch, call tieBreakerModeToggle() method when a change occurs.
		tieBreakerSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) { 
				tieBreakerModeToggle();
			}
		});
	
		
		continueButton.setOnAction(e -> {
			title.saveTitle();
			
			try {
				//GameData.setGameTitle(titleTextField.getText());
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
			continueButton.setDisable(true);
			return;
		}
		
		else {
			titleTextField.setStyle("-fx-text-box-border: transparent; -fx-focus-color: #039ED3; -fx-text-fill: #000; ");
			messageLabel.setVisible(false);
			continueButton.setDisable(false);
			return;
		}
	}
	
	/**
	 * DEPRECATED AS OF VERSION 0.4.0-ALPHA, REMOVE AFTER 1 VERSION IF NO BUGS WITH NEW CODE.
	 * 
	 * A helper method that verifies the title passed to it does not contain any illegal characters.
	 * @param title - The title to validate.
	 * @return true if title contains illegal characters, false otherwise.
	 * @deprecated
	 */
	public boolean validateTitle(String title) {
		Pattern invalidCharsPattern = Pattern.compile("[<>:/|?\"\\\\*.]");
		Matcher matcher = invalidCharsPattern.matcher(title);
		
		return matcher.find();
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
	public void handlePlayerTextBoxText() {
		textBoxNumOfPlayers.setStyle("-fx-text-box-border: transparent; -fx-focus-color: #039ED3; -fx-text-fill: #000; ");
		messageLabel.setVisible(false);
		continueButton.setVisible(false);
		//continueButton.setDisable(true);
		String textBoxInput; // Variable to temporarily hold input value so it can be checked for validity
		
		textBoxNumOfPlayers.setOnKeyPressed( event -> {
			  if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
					  	continueButton.requestFocus();
					}				  
				});
		
		//Checking the input validation to ensure user enters a valid number.
		try {
			textBoxInput = textBoxNumOfPlayers.getText().trim();
			
			if(textBoxNumOfPlayers.getText().trim().isEmpty()) {
				continueButton.setVisible(false);
				return;
			}
			
			int inputToInt = Integer.parseInt(textBoxInput);
			//System.out.println("FIXME: " + textBoxInput + " successfully converted to an integer!");
			
			
			//Checks if the number of players entered by user exceeds MAX_PLAYERS limit. Gives error message to user if it does.
			if(inputToInt > globalValues.getMaxPlayersSupported()) { 
				textBoxNumOfPlayers.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: ff0000; -fx-text-fill: #ff0000;");
				messageLabel.setText("Error: The maximum number of players allowed is " + MAX_PLAYERS + ". Please try again!");
				messageLabel.setVisible(true);
				//continueButton.setDisable(true);
				continueButton.setVisible(false);
				return;
			}
			
			if(inputToInt < 0) {
				textBoxNumOfPlayers.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: ff0000; -fx-text-fill: #ff0000;");
				messageLabel.setText("Error: Number entered cannot be negative. Please try again!");
				messageLabel.setVisible(true);
				//continueButton.setDisable(true);
				continueButton.setVisible(false);
				return;
			}
			
			if(inputToInt == 0) {
				textBoxNumOfPlayers.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: ff0000; -fx-text-fill: #ff0000;");
				messageLabel.setText("Error: Number entered cannot be 0. Please try again!");
				messageLabel.setVisible(true);
				//continueButton.setDisable(true);
				continueButton.setVisible(false);
				return;
			}
			
			
			//If the input is a valid int AND the integer is <= MAX_PLAYERS limit, then continue button is enabled and the warning label
			//will be hidden if it was visible. The number entered by the user is then saved in the numOfPlayers variable in class GameData.
			else {
				textBoxNumOfPlayers.setStyle("-fx-text-box-border: transparent; -fx-focus-color: #039ED3; -fx-text-fill: #000; ");
				messageLabel.setVisible(false);
				//continueButton.setDisable(false);
				continueButton.setVisible(true);
				gameData.setNumOfPlayers(inputToInt);
				if(textBoxNumOfPlayers.getText().length() == 2) {
					continueButton.requestFocus();
				}
				//System.out.println("FIXME: numOfPlayers: " + gameData.getNumOfPlayers());
				//continueButton.requestFocus();
				return;
			}
		} 
		
		//Handles all errors caused by invalid user input except for no input/blank TextField and maximum players limit being exceeded. 
		catch(NumberFormatException e) {
			if(!textBoxNumOfPlayers.getText().trim().isEmpty()) {
				textBoxNumOfPlayers.setStyle("-fx-text-box-border: #ff0000; -fx-focus-color: ff0000; -fx-text-fill: #ff0000;");
				messageLabel.setText("Error: Invalid character entered! Please enter a number!");
				messageLabel.setVisible(true);
				//continueButton.setDisable(true);
				continueButton.setVisible(false);
		 }
		}
		
		textBoxNumOfPlayers.setOnKeyPressed( event -> {
			  if( event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
				  continueButton.setDisable(false);
				  continueButton.requestFocus();
				  
				  //Check if TextField is blank/no input entered by user. 
				  if(textBoxNumOfPlayers.getText().trim().isEmpty()) {
					  messageLabel.setText("Error: Number of players field cannot be left blank!");
					  textBoxNumOfPlayers.setStyle("-fx-text-box-border: #ff0000;");
					  messageLabel.setVisible(true);
					  continueButton.setDisable(true);
					  }
				  }
				});
	}
	
	/**
	 * Removes any extra characters if user attempts to type more than 2 characters in the number of players TextField.
	 */
	private void removeTextFieldOverflow() {
		String tempString = textBoxNumOfPlayers.getText().substring(0, 2);
		//System.out.println("FIXME: removeTextFieldOverflow() tempString: " + tempString); //FIXME
		textBoxNumOfPlayers.setText(tempString);
		textBoxNumOfPlayers.end();
	}
	
	/**
	 * Handler for the tiebreaker mode toggle switch. Values: 1 = ON, 0 = OFF.
	 */
	public void tieBreakerModeToggle() {
		if(tieBreakerSlider.getValue() == 0.0) {
			tieBreakerStatus.setText("Off");
		}
		
		if(tieBreakerSlider.getValue() == 1.0) {
			tieBreakerStatus.setText("On");
		}
		
		int tieBreakerValue = (int)tieBreakerSlider.getValue();
		gameData.setTieBreakerMode(tieBreakerValue);
		
		/*
		//Debugging print statements.
		
		//System.out.println("FIXME: tieBreakerModeToggle() called!"); //FIXME
		//System.out.println("FIXME: tieBreakerSlider value: " + tieBreakerSlider.getValue()); //FIXME
		//int tieBreakerMode = gameData.getTieBreakerMode(); //FIXME
		//System.out.println("FIXME: tieBreakerMode value is: " + tieBreakerMode); //FIXME
		 */
	}

}
