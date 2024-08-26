package application;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TieBreakerWinnerSelectionController {
	GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
	HeaderButtonsController headerButtonsController = new HeaderButtonsController();
	Main mainClass = new Main();
	GameData gameData = new GameData();
	TieBreakerMode tieBreakerMode = new TieBreakerMode();
	SongInfoSetupController songInfoSetupController = new SongInfoSetupController();
	private ArrayList<Player> tiedPlayerList = tieBreakerMode.getTiedPlayerList();
	private int playerIndex = -1;
	

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

	// TieBreakerWinner.fxml specific FXML objects //

	@FXML private Label instructionsLabel;
	@FXML private Label tieBreakerSongLabel;
	@FXML private ListView<String> selectWinnerListView;
	@FXML private Button selectWinnerButton;
	@FXML private Button generateResultsButton;
	@FXML private Label messageLabel;		
	// End of TieBreakerWinner.fxml specific objects

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
		selectWinnerButton.setDisable(true);
		generateResultsButton.setDisable(true);
		generateResultsButton.setVisible(false);
		selectWinnerListView.setPlaceholder(new Label("No players to display"));
		
		
		instructionsLabel.setText("Play 5 to 15 seconds of the tiebreaker song and then select a winner from the list of players"
		 		+ " below and click the 'Select Winner' button to choose a winner.");
		
		initializeSelectWinnerListView();
		
		selectWinnerListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(selectWinnerListView.getSelectionModel().getSelectedItem() != null) {
					playerIndex = selectWinnerListView.getSelectionModel().getSelectedIndex();
					selectWinnerButton.setDisable(false);
				}
				
				else {
					selectWinnerButton.setDisable(true);
				}
				
			}	
		});
		
		tieBreakerSongLabel.setText(TieBreakerMode.getTieBreakerSong().toStringNoNumber());
		//tieBreakerMode.setWinner(1); //FIXME
		
		/*selectWinnerButton.setOnAction((event) -> {
			addButtonHandler();
		});*/
		
		selectWinnerButton.setOnAction(event -> { 
			selectWinnerButtonHandler();
		});
		
		generateResultsButton.setOnAction(event -> {
			//Fix the tiebreaker song number if it does not match the size of the song list.
			ArrayList<Song> tempList = gameData.getSongList();
			
			if(tempList.get(tempList.size() - 1).getNumber() != tempList.size()) {
				int lastSongNum = tempList.get(tempList.size() - 2).getNumber();
				Song tempSong = tempList.get(tempList.size() - 1);
				tempSong.setNumber(++lastSongNum);
				tempList.set(tempList.size() - 1, tempSong);
				//tempList.set(tempList.size() - 1).get(tempList.size() - 1).setNumber(lastSongNum++);
				gameData.setSongList(tempList);
				
				//System.out.println("FIXME: tempList:" + tempList.toString()); //FIXME
				//System.out.println("FIXME: lastSongNum: " + lastSongNum); //FIXME
				//System.out.println("FIXME: tempList.get(tempList.size() - 1): " + tempList.get(tempList.size() - 1)); //FIXME
				//System.out.println("FIXME: tempSong: " + tempSong); //FIXME
			}
			
			loadResultsScene(event);
		});
	}
	
	/**
	 * Method to get tied players list from TieBreakerMode class and add each one to the listview.
	 */
	private void initializeSelectWinnerListView() {
		for(int i = 0; i < tiedPlayerList.size(); i++) {
			selectWinnerListView.getItems().add(tiedPlayerList.get(i).getName());
		}
		
	}
	
	/**
	 * When the selectWinnerButton is pressed, it sets the winner of the tiebreaker using the setWinner() helper method in the
	 * TieBreakerMode class. It also displays a success message and enables the generateResultsButton.
	 */
	private void selectWinnerButtonHandler() {
		tieBreakerMode.setWinner(playerIndex);
		messageLabel.setText(tiedPlayerList.get(playerIndex).getName() + " has been set as the winner.\n"
				+ " Click 'Generate Results' button below to view the results!");
		selectWinnerButton.setDisable(true);
		generateResultsButton.setDisable(false);
		generateResultsButton.setVisible(true);
		messageLabel.setVisible(true);
	}
	
	/**
	 * Helper method that changes the current scene to Results.FXML scene when an event is triggered.
	 */
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
