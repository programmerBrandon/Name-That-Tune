package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SetPlayerScoresController {
	GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
	HeaderButtonsController headerButtonsController = new HeaderButtonsController();
	Main mainClass = new Main();
	GameData gameData = new GameData();
	private ObservableList<String> observablePlayerList = FXCollections.observableArrayList();
	String playerSelected;
	String playerSelectedParsed;
	
	
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
	
	// SetPlayerScores.fxml specific FXML objects //
	
	@FXML private ListView<String> playerList;
	@FXML private ListView<String> songList;
	@FXML private Label instructionsLabel; //Used to give user instructions on what data to provide, etc.
	@FXML private Label songSelectedLabel; //Shows the current song selected in the list (without the artist)
	@FXML private Label playerSelectedLabel; //Shows current player selected in the list (without points)
	@FXML private Button songOnlyButton; //Adds 1 point to selected user's total points.
	@FXML private Button artistOnlyButton; //Adds 1 point to selected user's total points
	@FXML private Button bothButton; //Is clicked if user gets both song and artist correct. Adds 2 points to selected user's total points
	@FXML private Label messageLabel;
	@FXML private Button finishButton;
	
	// End of SetPlayerScores.fxml specific objects

	@FXML
	public void initialize()  {
	headerIcon.setImage(globalValues.programIcon);
	homeIcon.setImage(globalValues.homeIcon);
	infoIcon.setImage(globalValues.infoIcon);
	//Setting text values for header and footer text using values from 'GlobalValues'.
	programName.setText(globalValues.programNameText);
	copyrightText.setText(globalValues.copyrightText);
	versionText.setText(globalValues.versionNumberText);
	
	//Calling the headerButtonsController class (which handles button functionality) when a user presses or clicks on the home or info button.
	homeButtonHeader.setOnAction(headerButtonsController);
	infoButtonHeader.setOnAction(headerButtonsController);
	
	initializeListData();
	instructionsLabel.setText("From the lists above, select a song and a player. The name of the song and player selected will be shown below."
			+ " Once a song has been selected, select each player one at a time and use the buttons below to add points if the "
			+ "player got the song name, artist or both correct. Getting only the artist or song correct awards a player 1 point. If a "
			+ "player gets both the song name and artist correct, they are awarded 2 points. Repeat this process for each song in the list. "
			+ "When you have finished awarding all of the points, press the finish button to generate the final scoreboard.");
	
	//ADD COMMENT HERE
	songOnlyButton.setOnAction((event) -> {
		onePointButtonHandler();
	});
	
	artistOnlyButton.setOnAction((event) -> {
		onePointButtonHandler();
	});
	
	bothButton.setOnAction((event) -> {
		twoPointButtonHandler();
	});
	
	//Creates a listener to detect when an item in playerList is selected and then calls listSelectionHandler() method.
	playerList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			listSelectionHandler();
			}	
		});
	
	//Creates a listener to detect when an item in songList is selected and then calls listSelectionHandler() method.
	songList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

		@Override
		public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			listSelectionHandler();
			}	
		});
	
	finishButton.setOnAction(e -> {
		try {
			Parent resultsPage = FXMLLoader.load(getClass().getResource("Results.fxml"));
			Scene resultsPageScene = new Scene(resultsPage, globalValues.programWidth, globalValues.programHeight);
			resultsPageScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage resultsStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		    resultsStage.setScene(resultsPageScene);
		    resultsStage.show();
		    System.out.println("Results.fxml loaded successfully!");
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("Failed to load Results.fxml!");
		}
	});
	
	}
	
	//Takes the song and player data inputted by the user in the previous 2 scenes (SongInfoSetup.fxml & PlayerInfoSetup.fxml) and 
	//adds the data to 2 lists, song and artist data is added to songList and player data added to the playerList along with their current
	//points. Player data is also added to an observable Arraylist called observablePlayerList so that the playerList listview box will
	//automatically update when the score is updated.
	public void initializeListData() {
		String[] songNameList = gameData.getSongNameList(); //Used to read elements of songNameList in GameData class.
		String[] playerNameList = gameData.getPlayerNameList(); //Used to read elements of playerNameList in GameData class.
		
		for(int i = 0; i < gameData.getNumOfSongs(); i++) {
			if(gameData.getSongList(songNameList[i]) != "") {
				songList.getItems().add(songNameList[i] + " By: " + gameData.getSongList(songNameList[i]));
			}
			else {
				songList.getItems().add(songNameList[i]);
			}
		}
		
		for(int i = 0; i < gameData.getNumOfPlayers(); i++) {
			observablePlayerList.add(playerNameList[i] + " - Points: " + gameData.getPlayerScores(playerNameList[i]));
			playerList.getItems().add(observablePlayerList.get(i));
		}
		
	}
	
	//ADD COMMENTS HERE
	public void listSelectionHandler() {
		String songSelected = (String) songList.getSelectionModel().getSelectedItem();
		
		if(playerList.getSelectionModel().getSelectedItem() != null) {
		playerSelected = (String) playerList.getSelectionModel().getSelectedItem();
		messageLabel.setVisible(false);
		
		System.out.println("FIXME: songSelected: " + songSelected); //FIXME
		System.out.println("FIXME: playerSelected: " + playerSelected); //FIXME
		}
		

		
		//Checks to see if the user entered an artist for the song by checking for 'By:' in the selection text which is only added
		//by the program if the artist name was not left blank. If there is an artist, it parses the selection text down to just the 
		//song name by getting index for 'By:', and then displays the song name in the label 'songSelectedLabel'.
		//If there is no artist, then it simply displays the selection text as is in the label 'songSelectedLabel'.
		if(songSelected.contains("By:")) {
			if(songSelected.indexOf("By:") != -1) {
				int indexOfBy = songSelected.indexOf(" By:");
				System.out.println("FIXME: Index of ' By:': " + indexOfBy); //FIXME"
				String songSelectedParsed = songSelected.substring(0, indexOfBy);
				System.out.println("FIXME: songSelectedParsed: " + songSelectedParsed); //FIXME
				
				songSelectedLabel.setText("Song Selected: " + songSelectedParsed);
			}
		}
		
		else {
			songSelectedLabel.setText("Song Selected: " + songSelected);
		}
		
		//Creating a substring that shows just the player name/username without points displays it on the label playerSelectedLabel.
		//Code works by searching for ' - Points:' in the playerList selection text and storing the index for it in variable 'index'.
		int index = playerSelected.indexOf(" - Points:");
		System.out.println("FIXME: Index of ' - Points:': " + index); //FIXME
		playerSelectedParsed = playerSelected.substring(0, index);
		playerSelectedLabel.setText("Player Selected: " + playerSelectedParsed);
		
		//Enable the songOnly, artistOnly and Both buttons if a selection has been made to both songList and playerList.
		if (songList.getSelectionModel().getSelectedItem() != null && playerList.getSelectionModel().getSelectedItem() != null) {
			songOnlyButton.setDisable(false);
			artistOnlyButton.setDisable(false);
			bothButton.setDisable(false);
			
			
		}
	}
	
	//Called when songOnly or artistOnly button is pressed or clicked, this method will add 1 to the selected player's total points
	//and updates the points in the Hashmap playerList using the setPlayersList method in the GameData class. It will then update the 
	//observable list in order to update the current points in the ListView 'playerList'.
	private void onePointButtonHandler() {
		System.out.println("FIXME: " + playerSelectedParsed + "'s score: " + gameData.getPlayerScores(playerSelectedParsed)); //FIXME
		
		int newScore = gameData.getPlayerScores(playerSelectedParsed) + 1;
		gameData.setPlayerScores(playerSelectedParsed, newScore);
		
		System.out.println("FIXME: " + playerSelectedParsed + "'s new score: " + gameData.getPlayerScores(playerSelectedParsed)); //FIXME
		
		int index = observablePlayerList.indexOf(playerSelected);
		System.out.println("FIXME: index of player selected: " + index); //FIXME
		observablePlayerList.set(index, playerSelectedParsed + " - Points: " + gameData.getPlayerScores(playerSelectedParsed));
		playerList.getItems().setAll(observablePlayerList);
		songOnlyButton.setDisable(true);
		artistOnlyButton.setDisable(true);
		bothButton.setDisable(true);
		messageLabel.setText("1 point has been added to " + playerSelectedParsed + "'s total points!");
		messageLabel.setVisible(true);
		playerSelectedLabel.setText("Player Selected: N/A");
	}
	
	//Called when Both button is pressed or clicked, this method will add 2 to the selected player's total points
	//and updates the points in the Hashmap playerList using the setPlayersList method in the GameData class. It will then update the 
	//observable list in order to update the current points in the ListView 'playerList'.
	private void twoPointButtonHandler() {
		System.out.println("FIXME: " + playerSelectedParsed + "'s score: " + gameData.getPlayerScores(playerSelectedParsed)); //FIXME
		
		int newScore = gameData.getPlayerScores(playerSelectedParsed) + 2;
		gameData.setPlayerScores(playerSelectedParsed, newScore);
		
		System.out.println("FIXME: " + playerSelectedParsed + "'s new score: " + gameData.getPlayerScores(playerSelectedParsed)); //FIXME
		
		int index = observablePlayerList.indexOf(playerSelected);
		System.out.println("FIXME: index of player selected: " + index); //FIXME
		observablePlayerList.set(index, playerSelectedParsed + " - Points: " + gameData.getPlayerScores(playerSelectedParsed));
		playerList.getItems().setAll(observablePlayerList);
		songOnlyButton.setDisable(true);
		artistOnlyButton.setDisable(true);
		bothButton.setDisable(true);
		messageLabel.setText("2 points have been added to " + playerSelectedParsed + "'s total points!");
		messageLabel.setVisible(true);
		playerSelectedLabel.setText("Player Selected: N/A");
	}
	

}
