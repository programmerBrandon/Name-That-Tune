package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TieBreakerSongController {
	GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
	HeaderButtonsController headerButtonsController = new HeaderButtonsController();
	GameData gameData = new GameData();
	TieBreakerMode tieBreakerMode = new TieBreakerMode();
	SongInfoSetupController songInfoSetupController = new SongInfoSetupController();

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

	// TieBreakerSongScreen.fxml specific FXML objects //

	@FXML private Label instructionsLabel;
	@FXML private TextField songField;
	@FXML private TextField artistField;
	@FXML private Button addButton;
	@FXML private Button continueButton;
	@FXML private Label messageLabel;		
	// End of TieBreakerSongScreen.fxml specific objects

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
		addButton.setDisable(true);
		continueButton.setVisible(false);
		
		instructionsLabel.setText("The process for breaking a tie is pretty straight forward and consists of \n"
				+ "just 2 steps: \n"
				+ "1. Pick a song that will be used to break the tie.\n"
				+ "2. Play about 10 seconds of the song and determine winner \n\n"
				+ "The winner is determined 1 of 2 ways:\n"
				+ "1. If a player guesses the song name and/or artist correctly and the\n"
				+ "other tied player(s) do not then that player is the winner.\n"
				+ "2. If multiple tied players guess the tiebreaking song correctly, the \n"
				+ "player who guessed it the quickest is the winner!");	
		
		//Validate input as the user types by calling handleTextBox() method.
		songField.setOnKeyTyped(event -> {
			handleTextBox();
		});
		
		//Add tiebreaker song to song list when add button is clicked by calling addButtonHandler().
		addButton.setOnAction((event) -> {
			addButtonHandler();
		});
		
		continueButton.setOnAction(event -> { 
			loadSelectWinnerScene(event);
			//loadResultsScene(event);
		});
	}
	


	private void handleTextBox() {
		if(!songField.getText().trim().isEmpty()) { 
			addButton.setDisable(false);
		}
		
		else {
			addButton.setDisable(true);
		}
		
		songField.setOnKeyPressed( event -> {
			  if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
				  
				  if(songField.getText().trim().isEmpty()) {
					  messageLabel.setText("Error: Song name field cannot be left blank!");
					  songField.setStyle("-fx-text-box-border: #ff0000;");
					  messageLabel.setVisible(true);
					  addButton.requestFocus();
					  addButton.setDisable(true);
					  //continueButton.setDisable(true);
					  } else {
						  messageLabel.setVisible(false);
						  addButton.setDisable(false);
						  songField.setStyle("-fx-text-box-border: transparent;");
						  artistField.requestFocus();
						  //addButtonHandler();
					  }
			  }			  
				});
		
		artistField.setOnKeyPressed( event -> {
			  if(event.getCode() == KeyCode.ENTER) {
				  addButton.requestFocus();
			  } 
				});
	}
	
	//@SuppressWarnings("unchecked")
	private void addButtonHandler() {
		Song song = new Song();
		song.setNumber(gameData.getNumOfSongs() + 1);
		song.setName(songField.getText());
		song.setArtist(artistField.getText());
		System.out.println("FiXME: song: " + song.toString());
		ArrayList<Song>  tempList =  new ArrayList<Song>(gameData.getSongList());
		ObservableList<Song> tempSongList = FXCollections.observableArrayList(tempList);
		
		//tempSongList = (ObservableList<Song>) gameData.getSongList();
		System.out.println(tempSongList);
		System.out.println(songInfoSetupController.duplicateChecker(tempSongList, song));
		
		if(songInfoSetupController.duplicateChecker(tempSongList, song)) {
			//messageLabel.setText("Error: " + song.getName() + " By: " + song.getArtist() + " is already in the list. "
			//		+ "Please enter a different song!");
			messageLabel.setText("Error: Song is already in the list. Please enter a different song!");
			messageLabel.setVisible(true);
			addButton.setDisable(true);
			songField.clear();
			artistField.clear();
			songField.requestFocus();
		}
		
		else {
			tempSongList.add(song);
			TieBreakerMode.setTieBreakerSong(song);
			System.out.println("FIXME: getTieBreakerSong: " + TieBreakerMode.getTieBreakerSong());
			ArrayList<Song> tempSongListCopy = new ArrayList<>(tempSongList);
			gameData.setSongList(tempSongListCopy); //Add song to permanent song list
			System.out.println("FIXME: Song List: " + gameData.getSongList());
			addButton.setDisable(true);
			songField.setDisable(true);
			artistField.setDisable(true);
			messageLabel.setText("Song added to song list! Click 'Continue' below to move to the next step!");
			messageLabel.setVisible(true);
			continueButton.setVisible(true);
			continueButton.setDisable(false);
			continueButton.requestFocus();
		}
	}
	
	private void loadSelectWinnerScene(javafx.event.ActionEvent e) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("TieBreakerWinnerSelection.fxml"));
			Scene selectWinnerScene = new Scene(parent, globalValues.getProgramWidth(), globalValues.getProgramHeight());
			selectWinnerScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			stage.setScene(selectWinnerScene);
			stage.show();
			System.out.println("TieBreakerWinnerSelection.fxml loaded successfully!");
		} catch (Exception e1) {
			e1.printStackTrace();
			messageLabel.setText("Error: Failed to load TieBreakerWinnerSelection.fxml. If this error persists, please notify the developer.");
			System.out.println("Failed to load TieBreakerWinnerSelection.fxml!");
		}
	}
	
	private void loadResultsScene(javafx.event.ActionEvent e) {
		try {
			Parent resultsPage = FXMLLoader.load(getClass().getResource("Results.fxml"));
			Scene resultsPageScene = new Scene(resultsPage, globalValues.getProgramWidth(), globalValues.getProgramHeight());
			resultsPageScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage resultsStage = (Stage)((Node)e.getSource()).getScene().getWindow();
			resultsStage.setScene(resultsPageScene);
			resultsStage.show();
			System.out.println("Results.fxml loaded successfully!");
		} catch (Exception e1) {
			e1.printStackTrace();
			messageLabel.setText("Error: Failed to load Results.fxml. If this error persists, please notify the developer.");
			System.out.println("Failed to load Results.fxml!");
		}
	}
}
