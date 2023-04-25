package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

public class ResultsController {
		GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
		HeaderButtonsController headerButtonsController = new HeaderButtonsController();
		Main mainClass = new Main();
		GameData gameData = new GameData();
		private ObservableList<String> observablePlayerList = FXCollections.observableArrayList();
		
		
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
		@FXML private ListView<String> songList;
		@FXML private ListView<String> resultsList;
		@FXML private Button returnHomeButton;
		
		// End of Results.fxml specific objects

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
		returnHomeButton.setOnAction(e -> {
			try {
				Parent parent = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
				Scene scene = new Scene(parent, globalValues.programWidth, globalValues.programHeight);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			    stage.setScene(scene);
			    stage.show();
			    System.out.println("MainScene.fxml loaded successfully!");
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("Failed to load MainScene.fxml!");
			}
		});
		
		initializeSongList();
		sortScores();
		}
		
		
		//COMMENTS HERE
		private void initializeSongList() {
			//TO DO
			String[] songNameList = gameData.getSongNameList(); //Used to read elements of songNameList in GameData class.
			int songNum = 0;
			
			for(int i = 0; i < gameData.getNumOfSongs(); i++) {
				songNum += 1;
				if(gameData.getSongList(songNameList[i]) != "") {
					songList.getItems().add(songNum + ". " + songNameList[i] + " By: " + gameData.getSongList(songNameList[i]));
				}
				else {
					songList.getItems().add(songNum + ". " + songNameList[i]);
				}
			}
		}
		

		
		//COMMENTS HERE
		private void initializeResultsList() {
			//TO DO
			ArrayList<String> sortedPlayerList = gameData.getSortedPlayerList();
			
			for(int i = 0; i < sortedPlayerList.size(); i++) {
				resultsList.getItems().add(sortedPlayerList.get(i));
			}
		}
		
		//Iterates through playerNameList[] and playerList hashmap (which returns value points using key playerName) and stores the points
		//in an Arraylist. The Arraylist is then sorted in descending order. Then using a for loop to iterate through playerList again
		//the algorithm will check each player name against the values in the arraylist one at a time until the value that matches
		//that player's points. The algorithm will check to see if the player has the highest points, and if they don't it will skip them
		//and continue checking until the player with the highest points is found. After which they are added to an observable arraylist
		//and then the top score of the sorted arraylist will be removed so that the 2nd highest is at the top. This process repeats until 
		//all players have been sorted from highest to lowest.
		protected void sortScores() {
			ArrayList<String> tempPlayerList = new ArrayList<>();
			ArrayList<Integer> tempScoresList = new ArrayList<>();
			ArrayList<String> tempSortedPlayerList = new ArrayList<>();
			String[] playerNameList = gameData.getPlayerNameList(); //Used to read elements of playerNameList in GameData class.
			int currScore = 0;
			int prevScore = 0;
			int position = 0;
			
			for(int i = 0; i < gameData.getNumOfPlayers(); i++) {
				tempScoresList.add(gameData.getPlayerScores(playerNameList[i]));
				System.out.println("FIXME: tempScoresList(" + i + "): " + tempScoresList.get(i)); //FIXME
				
				tempPlayerList.add(playerNameList[i]);
				System.out.println("FIXME: tempPlayerList(" + i + "): " + tempPlayerList.get(i)); //FIXME
			}
			
			Collections.sort(tempScoresList, Collections.reverseOrder());
			
			//FIXME: Loop to print the elements of tempScoresList to check that its sorted corrected. Remove/comment out in final code
			for(int i = 0; i < tempScoresList.size(); i++) {
				System.out.println("FIXME: tempScoresList(" + i + "): " + tempScoresList.get(i)); //FIXME
			}
			
			System.out.println(); //FIXME - Adds a space between tempScoresList fixme loop output above, and code below.
			
			//Outer loop, iterates through tempScoresList.
			for(int i = 0; i < tempScoresList.size(); i++) {
				//Inner loop, iterates through each player in tempPlayerList for every iteration of the outer loop. The inner loop terminates
				//early if the score mapped to a player matches the current score in TempScoresList.
				for(int k = 0; k < tempPlayerList.size(); k++) {

					if(tempScoresList.get(i) == gameData.getPlayerScores(tempPlayerList.get(k))) {
						System.out.println("FIXME: i: " + i); //FIXME
						System.out.println("FIXME: k: " + k); //FIXME
						System.out.println("FIXME: tempPlayerList(" + k + "): " + tempPlayerList.get(k)); //FIXME
						System.out.println("FIXME: tempScoresList(" + i + "): " + tempScoresList.get(i)); //FIXME
						
						if(gameData.getPlayerScores(tempPlayerList.get(k)) == 0) {
							tempPlayerList.remove(k);
							continue;
						}
						
						currScore = tempScoresList.get(i);
						
						if(currScore != prevScore) {
							position += 1;
							System.out.println("FIXME: position: " + position); //FIXME
						}
						
						if(currScore == prevScore && position == 1) {
							if(!tempSortedPlayerList.get(0).contains("(Tie)")) {
							String tempString = tempSortedPlayerList.get(0);
							tempString = tempString.concat(" (Tie)");
							tempSortedPlayerList.set(0, tempString);
							}
							tempSortedPlayerList.add(position + ". " + tempPlayerList.get(k) + " - " + tempScoresList.get(i) + " Points " + " (Tie)");
							prevScore = currScore;
							tempPlayerList.remove(k);
							continue;
						}
						
						if(gameData.getPlayerScores(tempPlayerList.get(k)) == 1) {
							tempSortedPlayerList.add(position + ". " + tempPlayerList.get(k) + " - " + tempScoresList.get(i) + " Point");
							prevScore = currScore;
							tempPlayerList.remove(k);
							continue;
						}

						tempSortedPlayerList.add(position + ". " + tempPlayerList.get(k) + " - " + tempScoresList.get(i) + " Points");
						prevScore = currScore;
						tempPlayerList.remove(k);
						break;
					}
				}
			
			}
			
			gameData.setSortedPlayerList(tempSortedPlayerList);
			for(int i = 0; i < tempSortedPlayerList.size(); i++) {
				System.out.println("FIXME: tempSortedPlayerList(" + i + "): " + tempSortedPlayerList.get(i)); //FIXME
			}
			
			initializeResultsList(); //FIXME - COMMENT OUT OR EDIT ONCE TIE-BREAKER MODE IS COMPLETED!
		}
}
