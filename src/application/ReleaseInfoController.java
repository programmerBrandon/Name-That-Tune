package application;

import javafx.application.Application;
import javafx.application.HostServices;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.StringConcatFactory;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class ReleaseInfoController {
	//Controller class for ReleaseInfo.fxml.
	
	GlobalValues globalValues = new GlobalValues(); //Object to GlobalValues class which contents the global values used in all scenes.
	HeaderButtonsController headerButtonsController = new HeaderButtonsController();
	Desktop desktop;
	
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
	@FXML private Button releaseNotesButton;
	@FXML private Hyperlink bugReportLink;
	@FXML private Hyperlink gitHubRepoLink;
	@FXML private Label messageLabel;
	// End of ReleaseInfo.fxml specific objects //
	
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
		//bugReportLink = new Hyperlink("https://github.com/programmerBrandon/Name-That-Tune/issues");
	//currentRelease.setText(globalValues.versionNumberText + " [UNSTABLE]");
		currentRelease.setText(globalValues.getVersionNumber() + " - [UNSTABLE]");
		releaseDate.setText("Release Date: 6/30/2023");
		improvementsList.setText("~NEW FEATURE: Tiebreaker mode is now functional. \n"
				+ "~Tiebreaker mode is now enabled by default. \n"
				+ "~Results page now adds '(Winner)' or '(Winner Via Tiebreaker)' next to  winning player in results list when game doesn't end in tie. \n"
				+ "~Redesigned this release page and added some new features. \n"
				+ "~Credits page is now partially completed. \n"
				+ "~Minor Bug Fixes");
		/*issuesList.setText("-This is still an early stage prototype, as such many features still don't work.\n"
				+ "-'Tiebreaker Mode' & 'Save Results' features are still in development and do not work. 'Tiebreaker Mode' is expected "
				+ "to be ready in Alpha 2.0. \n"
				+ "-Any other bugs, issues, feedback or suggestions should be reported directly to Brandon. FEATURES & LAYOUT SUBJECT TO "
				+ "CHANGE UNTIL BETA PHASE.");*/
		System.out.println("Desktop.isDesktopSupported(): " + desktop.isDesktopSupported());
		System.out.println(System.getProperty("user.dir"));
		
		if(desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
		}
		
		try {
			desktop = Desktop.getDesktop();
		} catch(Exception e) {
			System.out.println("FIXME: Desktop class is not supported!");
			e.printStackTrace();
		}
		
		releaseNotesButton.setOnAction(e -> {
			//File file = new File("");
			if(desktop.isDesktopSupported()) {
				
				/*try {
					Class cls = Class.forName("ReleaseInfoController");
					ClassLoader classLoader = ClassLoader.getSystemClassLoader();
					InputStream input = ReleaseInfoController.class.getResourceAsStream("/ReleaseNotes.txt");
					BufferedReader reader = new BufferedReader(new InputStreamReader(input));
					System.out.println("FIXME: reader: " + reader);
				} catch (Exception e1) {
					// TODO: handle exception
				}
				//desktop = Desktop.getDesktop();
				
				/*try (InputStream input = getClass().getResourceAsStream("/Release Notes.txt");
					    BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
					    String fileInput = reader.toString();
					    System.out.println("FIXME: fileInput: " + fileInput);
				//file = new File("");
				} catch(Exception e1) {
					System.out.println("ERROR!");
				}*/
				
				try {
					desktop.browse(new URI("https://github.com/programmerBrandon/Name-That-Tune/blob/main/ReleaseNotes.txt"));
					//desktop.open(file);
					messageLabel.setText("Release Notes Opened Successfully!");
					messageLabel.setVisible(true);
					System.out.println("FIXME: 'Release Notes.txt' opened!");
				} catch (Exception e1) {
					messageLabel.setText("Error: Could not open 'Release Notes.txt'!");
					messageLabel.setVisible(true);
					e1.printStackTrace();
				} 
			}
			
			else {
				messageLabel.setText("This feature is unsupported on your system. We apologize for the inconvenience!' ");
				messageLabel.setVisible(true);
			}
		});
		
		bugReportLink.setOnAction(e -> {
			if(desktop.isDesktopSupported()) {
				//desktop = Desktop.getDesktop();
				try {
					desktop.browse(new URI("https://github.com/programmerBrandon/Name-That-Tune/issues"));
					messageLabel.setText("Bug Report link opened successfully in web browser!");
					messageLabel.setVisible(true);
					System.out.println("FIXME: 'https://github.com/programmerBrandon/Name-That-Tune/issues' opened in the default web browser!");
				} catch (Exception e1) {
					messageLabel.setText("Error: Could not open 'https://github.com/programmerBrandon/Name-That-Tune/issues");
					messageLabel.setVisible(true);
					e1.printStackTrace();
				} 
			}
			
			else {
				messageLabel.setText("This feature is unsupported on your system. We apologize for the inconvenience!' ");
				messageLabel.setVisible(true);
			}
		});
		
		gitHubRepoLink.setOnAction(e -> {
			if(desktop.isDesktopSupported()) {
				//desktop = Desktop.getDesktop();
				try {
					desktop.browse(new URI("https://github.com/programmerBrandon/Name-That-Tune"));
					messageLabel.setText("Githup Repo link opened successfully in web browser!");
					messageLabel.setVisible(true);
					System.out.println("FIXME: 'https://github.com/programmerBrandon/Name-That-Tune' opened in the default web browser!");
				} catch (Exception e1) {
					messageLabel.setText("Error: Could not open 'https://github.com/programmerBrandon/Name-That-Tune' ");
					messageLabel.setVisible(true);
					e1.printStackTrace();
				} 
			}
			
			else {
				messageLabel.setText("This feature is unsupported on your system. We apologize for the inconvenience!");
				messageLabel.setVisible(true);
			}
		});
	}
}
