package application;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.stage.Stage;

public class MainScreenController {
	//Controller class for MainScene.fxml (Main Screen of the program).
	
	GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
	HeaderButtonsController headerButtonsController = new HeaderButtonsController();
	Main mainClass = new Main();
	
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
	
	// MainScene.fxml specific FXML objects //
	
	@FXML private Label welcomeText;
	
	//Main Scene 'Menu' buttons to navigate the program.
	
	@FXML private Button startButton; 
	@FXML private Button gameInfoButton;
	@FXML private Button programInstructionsButton;
	@FXML private Button releaseInfoButton;
	@FXML private Button creditsButton;
	
	// End Of Main Scene 'Menu' buttons //
	
	// End of MainScene.fxml specific objects //
	
	//Set text values for header and footer text using values from 'GlobalValues'.
	@FXML
	public void initialize()  {
	globalValues.currentFXML = "MainScene.fxml";
	headerIcon.setImage(globalValues.programIcon);
	homeIcon.setImage(globalValues.homeIcon);
	infoIcon.setImage(globalValues.infoIcon);
	programName.setText(globalValues.programNameText);
	copyrightText.setText(globalValues.copyrightText);
	versionText.setText(globalValues.versionNumberText);
	welcomeText.setText("Welcome to the Name That Tune Scoreboard generation tool. The purpose of this tool is to assist you with scoring a game of Name That Tune."
			+ "To learn how to use the program please click the 'Program Instructions' button below. Once you have become familiar with how the "
			+ "program works, press the 'Start' button to begin.");
	//homeButtonHeader.setOnAction(headerButtonsController);
	infoButtonHeader.setOnAction(headerButtonsController);
	
	startButton.setOnAction(e -> {
		try {
			Parent gameSetup = FXMLLoader.load(getClass().getResource("GameSetup.fxml"));
			Scene gameSetupScene = new Scene(gameSetup, globalValues.programWidth, globalValues.programHeight);
			gameSetupScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage gameSetupStage = (Stage)((Node)e.getSource()).getScene().getWindow();
			gameSetupStage.setScene(gameSetupScene);
			gameSetupStage.show();
		    System.out.println("Game Setup Scene loaded!");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Failed to load GameSetup.fxml!");
		}
	});
		
	gameInfoButton.setOnAction(e -> {
		try {
			Parent gameInfo = FXMLLoader.load(getClass().getResource("GameInfo.fxml"));
			Scene gameInfoScene = new Scene(gameInfo, globalValues.programWidth, globalValues.programHeight);
			gameInfoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage gameInfoStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		    gameInfoStage.setScene(gameInfoScene);
		    gameInfoStage.show();
		    System.out.println("Game Info Scene loaded!");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Failed to load GameInfo.fxml!");
		}
		
	});
	
	programInstructionsButton.setOnAction(e -> {
		try {
			Parent programInstructions = FXMLLoader.load(getClass().getResource("Instructions.fxml"));
			Scene instructionsScene = new Scene(programInstructions, globalValues.programWidth, globalValues.programHeight);
			instructionsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage instructionsStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		    instructionsStage.setScene(instructionsScene);
		    instructionsStage.show();
		    System.out.println("Program Instructions Scene loaded!");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Failed to load Instructions.fxml!");
		}
		
	});
	
	releaseInfoButton.setOnAction(e -> {
		try {
			Parent releaseInfo = FXMLLoader.load(getClass().getResource("ReleaseInfo.fxml"));
			Scene releaseInfoScene = new Scene(releaseInfo, globalValues.programWidth, globalValues.programHeight);
			releaseInfoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage releaseInfoStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		    releaseInfoStage.setScene(releaseInfoScene);
		    releaseInfoStage.show();
		    System.out.println("Program Release Information Scene loaded!");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Failed to load ReleaseInfo.fxml!");
		}
		
	});
	
	creditsButton.setOnAction(e -> {
		try {
			Parent credits = FXMLLoader.load(getClass().getResource("Credits.fxml"));
			Scene creditsScene = new Scene(credits, globalValues.programWidth, globalValues.programHeight);
			creditsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage creditsStage = (Stage)((Node)e.getSource()).getScene().getWindow();
		    creditsStage.setScene(creditsScene);
		    creditsStage.show();
		    System.out.println("Program Credits Scene loaded!");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Failed to load Credits.fxml!");
		}
		
	});
	
	}
}