package application;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ReleaseInfoController {
	//Controller class for ReleaseInfo.fxml.
	
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
	
	// ReleaseInfo.fxml specific FXML objects //
	@FXML private Label currentRelease;
	@FXML private Label releaseDate;
	@FXML private Label improvementsList;
	@FXML private Label issuesList;
	@FXML private Label releaseWarnings;
	// End of ReleaseInfo.fxml specific objects //
	
	@FXML
	public void initialize()  {
	globalValues.currentFXML = "MainScene.fxml";
	headerIcon.setImage(globalValues.programIcon);
	homeIcon.setImage(globalValues.homeIcon);
	infoIcon.setImage(globalValues.infoIcon);
	programName.setText(globalValues.programNameText); 
	copyrightText.setText(globalValues.copyrightText); 
	versionText.setText(globalValues.versionNumberText); 
	homeButtonHeader.setOnAction(headerButtonsController);
	infoButtonHeader.setOnAction(headerButtonsController);
	currentRelease.setText(globalValues.versionNumberText + " [UNSTABLE]");
	releaseDate.setText("Release Date: 11/10/2022");
	improvementsList.setText("-Initial Prototype with the basic/fundamental features working.\n"
			+ "-All functions needed to generate the song list and 'results' work.\n"
			+ "-Initial design layout complete.");
	issuesList.setText("-This is an early stage prototype. There WILL be bugs and not all functions work.\n"
			+ "-'Tiebreaker Mode' and 'Save Results' features are still in development and do not work. 'Tiebreaker Mode' is expected "
			+ "to be ready by Pre-Alpha 1.1.\n"
			+ "-Any other bugs, issues, feedback or suggestions should be reported directly to Brandon. FEATURES & LAYOUT SUBJECT TO "
			+ "CHANGE UNTIL BETA PHASE.");
	}
}
