package application;

import java.util.ArrayList;
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

public class TieDetectedController {
	GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
	HeaderButtonsController headerButtonsController = new HeaderButtonsController();
	GameData gameData = new GameData();
	TieBreakerMode tieBreakerMode = new TieBreakerMode();

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

	// TieDetected.fxml specific FXML objects //

	@FXML private Label tieDetectedLabel;
	@FXML private Label TieExplainationLabel;
	@FXML private ListView<Player> tiedPlayersListView;
	@FXML private Label questionLabel;
	@FXML private Button yesButton; //If pressed tieBreakerScreen.fxml is loaded
	@FXML private Button noButton; //If pressed user will be taken directly to Results.fxml and tiebreaker mode will be bypassed.
	@FXML private Label messageLabel;		
	// End of TieDetected.fxml specific objects

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
		
		TieExplainationLabel.setText("A tie has been detected between the following players: ");
		tiedPlayersListView.setPlaceholder(new Label("No players to display"));
		questionLabel.setText("Do you want to break the tie?");
		
		setTiedPlayersList();
		//System.out.println("Tiebreaker song: " + TieBreakerMode.getTieBreakerSong());
		//System.out.println("FIXME: getTieBreakerSong().isBlank(): " + TieBreakerMode.getTieBreakerSong().isBlank());
		
		//When 'Yes' Button is clicked or pressed Tie Breaker Song Scene is loaded.
		yesButton.setOnAction(e -> {
			if(!TieBreakerMode.getTieBreakerSong().isBlank()) {
				ArrayList<Song> songList = gameData.getSongList();
				songList.add(TieBreakerMode.getTieBreakerSong());
				gameData.setSongList(songList);
				loadTieBreakerWinnerScene(e);
			}
			
			else {
				loadTieBreakerSongScene(e);
			}
		});
		
		//When 'No' Button is clicked or pressed Results Scene is loaded.
		noButton.setOnAction(e -> {
			gameData.setTieBreakerMode(0); //Set Tiebreaker Mode to 'Off' 
			loadResultsScene(e);
		});
	}
	
	/**
	 * Create a list of tied players and add them to tiedPlayersListView.
	 * This method also sets the tiedPlayers ArrayList in the TieBreakerMode class
	 */
	private void setTiedPlayersList() {
		ArrayList<Player> playerList = GameData.getPlayerList();
		ArrayList<Player> tiedPlayerList = new ArrayList<>();
		
		tiedPlayerList.add(playerList.get(0));
		tiedPlayersListView.getItems().add(tiedPlayerList.get(0));
		
		for(int i = 1; i < playerList.size(); i++) {
			if(playerList.get(i).getPoints() == playerList.get(0).getPoints()) {
				tiedPlayerList.add(playerList.get(i));
				tiedPlayersListView.getItems().add(tiedPlayerList.get(i));
			}
			
			else {
				break;
			}
		}
		
		TieBreakerMode.setTiedPlayerList(tiedPlayerList);
		
		//Debugging statements
		/*
		System.out.println("FIXME: tiedPlayerList: " + TieBreakerMode.getTiedPlayerList());
		
		for(Player player : tiedPlayerList) {
			System.out.println("FIXME: " + player);
		}
		*/
		
	}
	
	/**
	 * When 'Yes' button is clicked, Tie Breaker Song Scene is loaded (unless a tie breaker song was imported from a song list).
	 */
	private void loadTieBreakerSongScene(javafx.event.ActionEvent e) {
		try {
			Parent tieBreakerSong = FXMLLoader.load(getClass().getResource("TieBreakerSongScreen.fxml"));
			Scene tieBreakerSongScene = new Scene(tieBreakerSong, globalValues.getProgramWidth(), globalValues.getProgramHeight());
			tieBreakerSongScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage tieBreakerSongStage = (Stage)((Node)e.getSource()).getScene().getWindow();
			tieBreakerSongStage.setScene(tieBreakerSongScene);
			tieBreakerSongStage.show();
			System.out.println("TieBreakerSongScreen.fxml loaded successfully!");
		} catch (Exception e1) {
			e1.printStackTrace();
			messageLabel.setText("Error: Failed to load TieBreakerSongScreen.fxml. If this error persists, please notify the developer.");
			System.out.println("Failed to load TieBreakerSongScreen.fxml!");
		}
	}
	
	/**
	 * When 'Yes' button is clicked and a Tiebreaker song has been imported from a song list, we skip straight to the Tiebreaker winner scene.
	 */
	private void loadTieBreakerWinnerScene(javafx.event.ActionEvent e) {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("TieBreakerWinnerSelection.fxml"));
			Scene scene = new Scene(parent, globalValues.getProgramWidth(), globalValues.getProgramHeight());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			stage.setScene(scene);
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
