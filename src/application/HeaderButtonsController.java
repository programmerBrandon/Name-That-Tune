package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HeaderButtonsController implements EventHandler<ActionEvent> {
	GlobalValues globalValues = new GlobalValues();
	
	@FXML private Button homeButtonHeader;
	@FXML private Button infoButtonHeader;

	@Override
	public void handle(ActionEvent event) {
		//System.out.println("Home Button Pressed!");
		String name = ((Button) event.getTarget()).getText();
		//System.out.println(name); //FIXME
		// TODO Auto-generated method stub
		if(name.equals("Home")) {
			System.out.println("Home Button Pressed!");
		   /*	
		  	Alert warning = new Alert(AlertType.WARNING);
			warning.setTitle("Warning!");
			warning.setContentText("Warning: Proceeding to the homepage will result in any data entered being lost and you will need to re-enter all data from scratch again. \n \n Are you sure you wish to proceed?");
			warning.showAndWait();
			*/
			
			try {
				Parent mainScreen = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
				Scene mainScene = new Scene(mainScreen, globalValues.programWidth, globalValues.programHeight);
				mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
			    mainStage.setScene(mainScene);
			    mainStage.show();
			    System.out.println("MainScene.fxml successfully loaded!");
			    
				} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("Failed to load MainScene.fxml!");
				}

		}
		
		if(name.equals("Info")) {
			System.out.println("Info Button Pressed!");
			
			try {
				Parent gameInfo = FXMLLoader.load(getClass().getResource("GameInfo.fxml"));
				Scene gameInfoScene = new Scene(gameInfo, globalValues.programWidth, globalValues.programHeight);
				gameInfoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage gameInfoStage = (Stage)((Node)event.getSource()).getScene().getWindow();
			    gameInfoStage.setScene(gameInfoScene);
			    gameInfoStage.show();
			    System.out.println("GameInfo.fxml loaded successfully!");
			    
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("Failed to load GameInfo.fxml!");
			}
		}
	}

}
