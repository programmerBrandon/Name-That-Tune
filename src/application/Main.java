package application;
	
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
	GlobalValues globalValues = new GlobalValues();
	
	Scene mainScene, gameInfoScene, instructionsScene, releaseInfoScene, creditsScene;
	Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.getIcons().add(globalValues.getProgramIcon());
		//primaryStage.getIcons().add(new Image("app/images/programIcon.png"));
		//primaryStage.getIcons().add(new Image("images/programIcon.ico"));
		primaryStage.setTitle("Name That Tune");
		primaryStage.setResizable(false);
		//stage = primaryStage;
		try {
			//Main Scene (MainScene.fxml) - Startup scene for program.
			Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
			mainScene = new Scene(root, globalValues.getProgramWidth(), globalValues.getProgramHeight());
			mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//Scene for GameInfo.fxml
			Parent gameInfo = FXMLLoader.load(getClass().getResource("GameInfo.fxml"));
			gameInfoScene = new Scene(gameInfo, globalValues.getProgramWidth(), globalValues.getProgramHeight());
			gameInfoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//Scene for Instructions.fxml
			Parent gameInstructions = FXMLLoader.load(getClass().getResource("Instructions.fxml"));
			instructionsScene = new Scene(gameInstructions, globalValues.getProgramWidth(), globalValues.getProgramHeight());
			gameInfoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			//Scene for ReleaseInfo.fxml
			Parent releaseInfo = FXMLLoader.load(getClass().getResource("ReleaseInfo.fxml"));
			releaseInfoScene = new Scene(releaseInfo, globalValues.getProgramWidth(), globalValues.getProgramHeight());
			releaseInfoScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//Scene for Credits.fxml
			Parent credits = FXMLLoader.load(getClass().getResource("Credits.fxml"));
			creditsScene = new Scene(credits, globalValues.getProgramWidth(), globalValues.getProgramHeight());
			creditsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(mainScene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Critical Error: Looks like there is 1 more or Syntax errors that need to be addressed. ");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
