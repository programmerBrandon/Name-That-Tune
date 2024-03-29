package application;

import java.io.File;
//import java.awt.Window;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.filechooser.FileNameExtensionFilter;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ResultsController {
		GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
		HeaderButtonsController headerButtonsController = new HeaderButtonsController();
		GameData gameData = new GameData();
		TieBreakerMode tieBreakerMode = new TieBreakerMode();
		Date date = new Date();
		//private ObservableList<String> playerList = FXCollections.observableArrayList();
		ArrayList<Player> playerList = gameData.getPlayerList();
		
		
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
		
		// Results.fxml specific FXML objects //
		@FXML private Label gameTitle;
		@FXML private ListView<String> songList;
		@FXML private ListView<String> resultsList;
		@FXML private Button saveResultsButton;
		@FXML private Button returnHomeButton;
		
		// End of Results.fxml specific objects

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
			initializeTitle();
			
			saveResultsButton.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Text Document (*.txt)", "*.txt");
				fileChooser.getExtensionFilters().add(filter);
				//fileChooser.getExtensionFilters(new FileNameExtensionFilter("TXT files (*.txt)", "*.txt"));
				try {
					javafx.stage.Window window = saveResultsButton.getScene().getWindow();
					File file = fileChooser.showSaveDialog(window);
					Save save = new Save();
					save.createFile(file);
					//System.out.println(fileChooser.showSaveDialog(window));
					//fileChooser.showSaveDialog(window);
					/*Parent parent = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
					Scene scene = new Scene(parent, globalValues.getProgramWidth(), globalValues.getProgramHeight());
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
					stage.setScene(scene);
					stage.show();*/
					//System.out.println("MainScene.fxml loaded successfully!");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println("Failed to load MainScene.fxml!");
				}
			});
			
			returnHomeButton.setOnAction(e -> {
				try {
					Parent parent = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
					Scene scene = new Scene(parent, globalValues.getProgramWidth(), globalValues.getProgramHeight());
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
					stage.setScene(scene);
					stage.show();
					System.out.println("MainScene.fxml loaded successfully!");
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println("Failed to load MainScene.fxml!");
				}
			});

			initializeSongList();
			resultsGenerator();
		}
		
		private void initializeTitle() {
			if(GameData.getGameTitle().equals("")) {
				gameTitle.setText("Name That Tune - " + date.getDateUSFormat());
			}
			
			else {
				gameTitle.setText("Name That Tune - " + GameData.getGameTitle());
			}
		}
		
		
		/**
		 * Iterate through the songList ArrayList in the Game Data class and add the elements to
		 * the songList ListView. If there was a tiebreaker, it adds (Tiebreaker) next to tiebreaker song.
		 */
		private void initializeSongList() {
			ArrayList<Song> tempSongList = gameData.getSongList();
			
			for(int i = 0; i < tempSongList.size(); i++) {
				
				if(tempSongList.get(i).getNumber() > gameData.getNumOfSongs()) {
					songList.getItems().add(tempSongList.get(i).toString() + " (Tiebreaker)");
				}
				
				else {
					songList.getItems().add(tempSongList.get(i).toString());
				}
				
			}
		}
		

		
		/**
		 * Uses sortedPlayerList from Game Data Class to initialize the results ListView. 
		 * Iterates through the sortedPlayerList and adds each element to the ListView.
		 */
		private void initializeResultsList() {
			ArrayList<String> sortedPlayerList = gameData.getSortedPlayerList();
			
			for(int i = 0; i < sortedPlayerList.size(); i++) {
				resultsList.getItems().add(sortedPlayerList.get(i));
				//resultsList.getItems().add(i + " " + playerList.get(i).toString());
				
			}
		}
		
		/**
		 * Method that generates the results of the game using 1 of 2 helper methods. 
		 * Creates a local Arraylist of type String to store results generated by helper method(s).
		 * This method checks if Tiebreaker Mode is on, it calls the tieBreakerOnGenerator(index) helper method
		 * If Tiebreaker Mode is off, it calls the tieBreakerOffGenerator(index) helper method.
		 */
		protected void resultsGenerator() {
			ArrayList<String> tempSortedPlayerList = new ArrayList<>();
			int position = 1;
			
			//Ensures the the playerList ArrayList located in GameData class has been sorted (which it should be).
			Collections.sort(playerList, Collections.reverseOrder());
			
			//If Tiebreaker mode is off, call tieBreakerOffGenerator(index) helper method.
			if(gameData.getTieBreakerMode() == 0) {
				tempSortedPlayerList = tieBreakerOffGenerator(position);
			}
			
			//Otherwise tiebreaker mode is on
			else {
				tempSortedPlayerList = tieBreakerOnGenerator(position);
				/*for(int i = 0; i < playerList.size(); i++) {
					if(i == 0 ) {
						tempSortedPlayerList.add(position + ". " + playerList.get(i).toStringAltFormat() + " (Winner) ");
					}
				}*/
				
			}
			
			gameData.setSortedPlayerList(tempSortedPlayerList);
			for(int i = 0; i < tempSortedPlayerList.size(); i++) {
				System.out.println("FIXME: tempSortedPlayerList(" + i + "): " + tempSortedPlayerList.get(i)); //FIXME
			}
			
			initializeResultsList();
		}
		
		/**
		 * Helper method for resultsGenerator() method.
		 * If tiebreaker mode is off or bypassed, then we need to iterate through playerlist and do 1 of the following:
		 * If there is a tie detected, add (Tie) next to the players tied for 1st place.
		 * If there is NOT a tie, then add (Winner) next to the winning player.
		 * @param position
		 * @return ArrayList of type String that contains the game results.
		 */
		private ArrayList<String> tieBreakerOffGenerator(int position) {
			ArrayList<String> tempSortedPlayerList = new ArrayList<String>();
			System.out.println("FIXME: checkForTies(): " + tieBreakerMode.checkForTies(playerList)); //FIXME
			
			//If no tie was detected and tiebreaker mode was not used (a clean win), add (Winner) next to the winning player.
			if(!tieBreakerMode.checkForTies(playerList)) {
				for(int i = 0; i < playerList.size(); i++) {
					if(i > 0 && playerList.get(i).getPoints() != playerList.get(i - 1).getPoints()) {
						position++;
					}

					if(i == 0) {
						tempSortedPlayerList.add(position + ". " + playerList.get(i).toStringAltFormat() + " (Winner)");
						continue;
					}
					
					if(playerList.get(i).getPoints() == 0) {
						continue;
					}

					tempSortedPlayerList.add(position + ". " + playerList.get(i).toStringAltFormat());
				}
				System.out.println("FIXME: tempSortedPlayerList: " + tempSortedPlayerList); //FIXME
				
			} 
			
			//Otherwise tiebreaker mode is off or was bypassed and the game ended in a tie.
			else {
				for(int i = 0; i < playerList.size(); i++) {

					if(i > 0 && playerList.get(i).getPoints() != playerList.get(i - 1).getPoints()) {
						position++;
					}
					
					//Add (Tie) next to any players who are tied for 1st place.
					if(playerList.get(i).getPoints() == playerList.get(0).getPoints() && i > 0) {
						if(!tempSortedPlayerList.get(0).contains("(Tie)")) { 
							tempSortedPlayerList.set(0, tempSortedPlayerList.get(0) + " (Tie)");
						}
						tempSortedPlayerList.add(position + ". " + playerList.get(i).toStringAltFormat() + " (Tie)");
					}

					else {
						
						if(playerList.get(i).getPoints() == 0) {
							continue;
						}
						
						tempSortedPlayerList.add(position + ". " + playerList.get(i).toStringAltFormat());
					}

				}
				//System.out.println("FIXME: tempSortedPlayerList: " + tempSortedPlayerList);
			}
			
			return tempSortedPlayerList;
		}
		
		/**
		 * Helper method for resultsGenerator() method.
		 * If tiebreaker mode is on, then we need to iterate through playerlist and do 1 of the following:
		 * If no tiebreaker was needed because a player won outright, then we add (Winner) next to the winning player.
		 * If a winner was determined via tiebreaker, then we add (Winner Via Tiebreaker) next to winning player.
		 * @param position
		 * @return ArrayList of type String that contains the game results.
		 */
		private ArrayList<String> tieBreakerOnGenerator(int position) {
			ArrayList<String> tempSortedPlayerList = new ArrayList<String>();
			
			//If Tiebreaker mode was on but there was no tie to break.
			if(!tieBreakerMode.checkForTies(playerList) && gameData.getSongList().size() == gameData.getNumOfSongs()) {
				
				for(int i = 0; i < playerList.size(); i++) {
					if(i > 0 && playerList.get(i).getPoints() != playerList.get(i - 1).getPoints()) {
						position++;
					}

					if(i == 0) {
						tempSortedPlayerList.add(position + ". " + playerList.get(i).toStringAltFormat() + " (Winner)");
						continue;
					}
					
					if(playerList.get(i).getPoints() == 0) {
						continue;
					}

					tempSortedPlayerList.add(position + ". " + playerList.get(i).toStringAltFormat());
				}				
			}
			
			//Otherwise winner was decided via tiebreaker. In which case, add (Winner By Tiebreaker) next winning player.
			else {
				for(int i = 0; i < playerList.size(); i++) {
					if(i == 0) {
						tempSortedPlayerList.add(position + ". " + playerList.get(i).toStringAltFormat() + " (Winner Via Tiebreaker)");
						continue;
					}

					if(i > 0 && playerList.get(i).getPoints() != playerList.get(i - 1).getPoints()) {
						position++;
					}
					
					if(playerList.get(i).getPoints() == 0) {
						continue;
					}

					tempSortedPlayerList.add(position + ". " + playerList.get(i).toStringAltFormat());
				}
			}
			

			return tempSortedPlayerList;
		}
}
