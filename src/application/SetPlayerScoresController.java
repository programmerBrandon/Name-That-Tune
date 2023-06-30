package application;

import java.util.ArrayList;
import java.util.Collections;
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
	GameData gameData = new GameData();
	private ObservableList<String> observablePlayerList = FXCollections.observableArrayList();
	ArrayList<Song> songList = gameData.getSongList();
	ArrayList<Player> playerList = gameData.getPlayerList();
	String playerSelected = "";
	String playerSelectedParsed = "";
	String songSelected = "";
	int playerIndex = 0;
	
	
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
	
	@FXML private ListView<String> playerListView;
	@FXML private ListView<String> songListView;
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
		headerIcon.setImage(globalValues.getProgramIcon());
		homeIcon.setImage(globalValues.getHomeIcon());
		infoIcon.setImage(globalValues.getInfoIcon());
		programName.setText(globalValues.getProgramName());
		copyrightText.setText(globalValues.getCopyright());
		versionText.setText(globalValues.getVersionNumber());
		homeButtonHeader.setOnAction(headerButtonsController);
		infoButtonHeader.setOnAction(headerButtonsController);
		
		finishButton.setDisable(true);
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
		playerListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				listSelectionHandler();
			}	
		});

		//Creates a listener to detect when an item in songList is selected and then calls listSelectionHandler() method.
		songListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				listSelectionHandler();
			}	
		});

		finishButton.setOnAction(e -> {
			if(gameData.getTieBreakerMode() == 1 && playerList.size() > 1) {
				Collections.sort(playerList, Collections.reverseOrder());
				TieBreakerMode tieBreakerMode = new TieBreakerMode();
				
				if(tieBreakerMode.checkForTies(playerList)) {
					System.out.println("FIXME: TIES FOUND!");
					loadTieDetectedScene(e);
				}
				
				else {
					loadResultsScene(e);
				}
			}
			
			else {
				loadResultsScene(e);
			}
		});
	
	}
	
	//Takes the song and player data inputed by the user in the previous 2 scenes (SongInfoSetup.fxml & PlayerInfoSetup.fxml) and 
	//adds the data to 2 lists, song and artist data is added to songList and player data added to the playerList along with their current
	//points. Player data is also added to an observable Arraylist called observablePlayerList so that the playerList listview box will
	//automatically update when the score is updated.
	public void initializeListData() {		
		
		for(int i = 0; i < gameData.getNumOfSongs(); i++) {
			songListView.getItems().add(songList.get(i).toString());
			
		}
		
		for(int i = 0; i < gameData.getNumOfPlayers(); i++) {
			observablePlayerList.add(playerList.get(i).toString());
			playerListView.getItems().add(observablePlayerList.get(i));
		}
		
	}
	
	//ADD COMMENTS HERE
	public void listSelectionHandler() {
		if (songListView.getSelectionModel().getSelectedItem() != null) {
			songSelected = (String) songListView.getSelectionModel().getSelectedItem();
			/*for(int i = 0; i < observablePlayerList.size(); i++) {
				observablePlayerList.set(i, playerList.get(i).toString());
			}
			
			playerListView.getItems().setAll(observablePlayerList);*/
		}
		
		if(playerListView.getSelectionModel().getSelectedItem() != null) {
			playerSelected = (String) playerListView.getSelectionModel().getSelectedItem();
			playerIndex = playerListView.getSelectionModel().getSelectedIndex();
			messageLabel.setVisible(false);
			
		
			System.out.println("FIXME: songSelected: " + songSelected); //FIXME
			System.out.println("FIXME: playerSelected: " + playerSelected); //FIXME
			System.out.println("FIXME: playerIndex: " + playerIndex); //FIXME
		}
		
		else {
			playerSelected = "";
			messageLabel.setVisible(false);
		}
		

		
		//Checks to see if the user entered an artist for the song by checking for 'By:' in the selection text which is only added
		//by the program if the artist name was not left blank. If there is an artist, it parses the selection text down to just the 
		//song name by getting index for 'By:', and then displays the song name in the label 'songSelectedLabel'.
		//If there is no artist, then it simply displays the selection text as is in the label 'songSelectedLabel'.
		if(!songSelected.isEmpty() && songSelected.contains("By:")) {
			if(songSelected.indexOf("By:") != -1) {
				int indexOfBy = songSelected.indexOf(" By:");
				System.out.println("FIXME: Index of ' By:': " + indexOfBy); //FIXME
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
		if(!playerSelected.isEmpty()) {
			int index = playerSelected.indexOf(" - Points:");
			System.out.println("FIXME: Index of ' - Points:': " + index); //FIXME
			playerSelectedParsed = playerSelected.substring(0, index);
			playerSelectedLabel.setText("Player Selected: " + playerSelectedParsed);
		}
		
		//Enable the songOnly, artistOnly and Both buttons if a selection has been made to both songList and playerList.
		if (songListView.getSelectionModel().getSelectedItem() != null && playerListView.getSelectionModel().getSelectedItem() != null) {
			songOnlyButton.setDisable(false);
			artistOnlyButton.setDisable(false);
			bothButton.setDisable(false);
			
			
		}
	}
	
	//Called when songOnly or artistOnly button is pressed or clicked, this method will add 1 to the selected player's total points
	//and updates the points in the Hashmap playerList using the setPlayersList method in the GameData class. It will then update the 
	//observable list in order to update the current points in the ListView 'playerList'.
	private void onePointButtonHandler() {
		playerList.get(playerIndex).setPoints(playerList.get(playerIndex).getPoints() + 1);
		System.out.println("FIXME: " + playerList.get(playerIndex).toString()); //FIXME
		
		int index = observablePlayerList.indexOf(playerSelected);
		System.out.println("FIXME: index of player selected: " + index); //FIXME
		observablePlayerList.set(playerIndex, playerList.get(playerIndex).toString());
		playerListView.getItems().setAll(observablePlayerList);
		//songOnlyButton.requestFocus();
		songOnlyButton.setDisable(true);
		artistOnlyButton.setDisable(true);
		bothButton.setDisable(true);
		finishButton.setDisable(false);
		messageLabel.setText("1 point has been added to " + playerSelectedParsed + "'s total points!");
		messageLabel.setVisible(true);
		messageLabel.requestFocus();
		playerSelectedLabel.setText("Player Selected: N/A");
	}
	
	//Called when Both button is pressed or clicked, this method will add 2 to the selected player's total points
	//and updates the points in the Hashmap playerList using the setPlayersList method in the GameData class. It will then update the 
	//observable list in order to update the current points in the ListView 'playerList'.
	private void twoPointButtonHandler() {
		
		playerList.get(playerIndex).setPoints(playerList.get(playerIndex).getPoints() + 2);
		System.out.println("FIXME: " + playerList.get(playerIndex).toString()); //FIXME
		
		int index = observablePlayerList.indexOf(playerSelected);
		System.out.println("FIXME: index of player selected: " + index); //FIXME
		observablePlayerList.set(playerIndex, playerList.get(playerIndex).toString());
		playerListView.getItems().setAll(observablePlayerList);
		songOnlyButton.setDisable(true);
		artistOnlyButton.setDisable(true);
		bothButton.setDisable(true);
		finishButton.setDisable(false);
		messageLabel.setText("2 points have been added to " + playerSelectedParsed + "'s total points!");
		messageLabel.setVisible(true);
		messageLabel.requestFocus();
		playerSelectedLabel.setText("Player Selected: N/A");
		
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
	
	private void loadTieDetectedScene(javafx.event.ActionEvent e) {
		try {
			Parent tieDetectedPage = FXMLLoader.load(getClass().getResource("TieDetected.fxml"));
			Scene tieDetectedScene = new Scene(tieDetectedPage, globalValues.getProgramWidth(), globalValues.getProgramHeight());
			tieDetectedScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage tieDetectedStage = (Stage)((Node)e.getSource()).getScene().getWindow();
			tieDetectedStage.setScene(tieDetectedScene);
			tieDetectedStage.show();
			System.out.println("TieDetected.fxml loaded successfully!");
		} catch (Exception e1) {
			e1.printStackTrace();
			messageLabel.setText("Error: Failed to load TieDetected.fxml. If this error persists, please notify the developer.");
			System.out.println("Failed to load TieDetected.fxml!");
		}
	}

}
