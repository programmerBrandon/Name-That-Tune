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

public class SongInfoSetupController {
	GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
	HeaderButtonsController headerButtonsController = new HeaderButtonsController();
	Main mainClass = new Main();
	GameData gameData = new GameData();
	private String artistName; //Stores the text from the artistNameField textbox temporarily for data handling purposes.
	private int numSongsAdded = 0; //Keeps track of how many songs were added to the list (To compare to how many songs are in the game).
	String[] songNameList = new String[gameData.getNumOfSongs()];
	
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
	
	@FXML private ListView songList;
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
	globalValues.currentFXML = "MainScene.fxml";
	headerIcon.setImage(globalValues.programIcon);
	homeIcon.setImage(globalValues.homeIcon);
	infoIcon.setImage(globalValues.infoIcon);
	headerIcon.setImage(globalValues.programIcon);
	homeIcon.setImage(globalValues.homeIcon);
	infoIcon.setImage(globalValues.infoIcon);
	programName.setText(globalValues.programNameText);
	copyrightText.setText(globalValues.copyrightText);
	versionText.setText(globalValues.versionNumberText);
	homeButtonHeader.setOnAction(headerButtonsController);
	infoButtonHeader.setOnAction(headerButtonsController);
	instructionsLabel.setText("Using the textboxes below, please enter the song name and artist name for each song. After typing in the "
			+ "information, double check that it was typed in correctly (i.e no typos) and then hit enter on your keyboard or press the "
			+ "'Add' button. The song and artist will then appear in the list above. Repeat this step until all songs are added. ");
	songList.setPlaceholder(new Label("No songs to display"));
	songNameField.focusedProperty().addListener((observable, oldValue, newValue) ->
	handleTextBox() );
	addButton.setOnAction((event) -> {
		addButtonHandler();
	});
	continueButton.setOnAction(e -> {
		try {
			Parent playerInfoSetup = FXMLLoader.load(getClass().getResource("PlayerInfoSetup.fxml"));
			Scene playerInfoSetupScene = new Scene(playerInfoSetup, globalValues.programWidth, globalValues.programHeight);
			playerInfoSetupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage playerInfoSetupStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		    playerInfoSetupStage.setScene(playerInfoSetupScene);
		    playerInfoSetupStage.show();
		    System.out.println("PlayerInfoSetup.fxml loaded successfully!");
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("Failed to load PlayerInfoSetup.fxml!");
		}
	});
	}
	
	public void handleTextBox() {
		System.out.println("FIXME: handleTextBox() method called!"); //FIXME
		
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
		System.out.println("FIXME: addButtonHandler() method called!"); //FIXME
		
		if ((numSongsAdded < gameData.getNumOfSongs())) {
		numSongsAdded += 1;
		System.out.println("Fixme: numSongsAdded: " + numSongsAdded); //FIXME
		songNameList[numSongsAdded - 1] = songNameField.getText();
		System.out.println("FIXME: songNameList[" + (numSongsAdded - 1) + "] (Local): " + songNameList[numSongsAdded - 1]);
		gameData.setSongList(songNameField.getText(), artistNameField.getText());
		
		if(!(artistNameField.getText().trim().isEmpty()) ) {

			songList.getItems().add(songNameField.getText() + " By " + artistNameField.getText());
		  } else {
			  songList.getItems().add(songNameField.getText());
		  }
		messageLabel.setText("Song " + numSongsAdded + " of " + gameData.getNumOfSongs() + " added!");
		messageLabel.setVisible(true);
		songNameField.clear();
		artistNameField.clear();
		songNameField.requestFocus();
		}
		
		if (numSongsAdded == gameData.getNumOfSongs()) {
			gameData.setSongNameList(songNameList);
			messageLabel.setText("Song " + numSongsAdded + " of " + gameData.getNumOfSongs() + " added! Click 'Continue' below to proceed!");
			continueButton.setDisable(false);
			continueButton.setVisible(true);
			songNameField.setDisable(true);
			artistNameField.setDisable(true);
			addButton.setDisable(true);
			continueButton.requestFocus();
			
			String[] songNameList1 = gameData.getSongNameList();
			
			for(int i = 0; i < gameData.getNumOfSongs(); i++ ) {
				System.out.println("FIXME: songNameList[" + i + "] " + songNameList1[i]); //FIXME
				System.out.println("FIXME: Artist Name from songList: " + gameData.getSongList(songNameList1[i])); //FIXME
			}
			
			
			//System.out.println("Fixme: numSongsAdded: " + numSongsAdded); //FIXME
		}
	}
}
