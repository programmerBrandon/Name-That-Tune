package application;

import java.io.FileInputStream;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class GlobalValues {
	//Information that needs to be updated across all 'scenes' like the header and footer is stored in this class and the info should be
	//updated in the variables below if changes are needed.
	public int programWidth = 550;
	public int programHeight = 500;
	public String versionNumberText = "Version: Pre-Alpha 1.0";
	public String copyrightText = "©2022 Brandon Green";
	public String programNameText = "Name That Tune";
	public Image programIcon = new Image("application/images/programIcon.png");
	//public Image homeIcon = new Image("file:home-icon.png");
	//FileInputStream homeIconStream = new FileInputStream("images/home-icon.png");
	//URL homeIconUrl = getClass().getResource(getClass().getResource("images/home-icon.png"));
	public Image homeIcon = new Image("application/images/homeIcon.png");
	public Image infoIcon = new Image("application/images/infoIcon.png");
	//public String programIcon = "programIcon.png";
	public String programIconURL = "https://i.imgur.com/w81HTXL.png";
	public int menuSceneSelector = 0; //0 = Main, 1 = Game Info, 2 = Instructions, 3 = Release Info, 4 = Credits
	public String sceneSelector = "mainScene";
	public String currentFXML = "Null";
}
