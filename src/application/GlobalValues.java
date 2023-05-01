package application;

import javafx.scene.image.Image;

public class GlobalValues {
	//Information that needs to be updated across all 'scenes' like the header and footer is stored in this class and the info should be
	//updated in the variables below if changes are needed.
	private final int PROGRAM_WIDTH = 550; //Temporary duplicate while converting code to use getter methods
	private final int PROGRAM_HEIGHT = 500; //Temporary duplicate while converting code to use getter methods
	private final String VERSION = "Version: Alpha 1.1 [0.1.1-alpha]"; //Temporary duplicate while converting code to use getter methods
	private final String COPYRIGHT = "©2023 Brandon Green"; //Temporary duplicate while converting code to use getter methods
	private final String PROGRAM_NAME = "Name That Tune"; //Temporary duplicate while converting code to use getter methods
	private final  Image PROGRAM_ICON = new Image("application/images/programIcon.png");
	private final Image HOME_ICON = new Image("application/images/homeIcon.png");
	private final Image INFO_ICON = new Image("application/images/infoIcon.png");
	
	//Below are currently used value that will eventually be removed in favor of variables above that allow for proper encapsulation
	/*public int programWidth = 550;
	public int programHeight = 500;
	public String versionNumberText = "Version: Alpha 1.1 [0.1.1-alpha]";
	public String copyrightText = "©2023 Brandon Green";
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
	*/
	public int getProgramWidth() {
		return PROGRAM_WIDTH;
	}
	
	public int getProgramHeight() {
		return PROGRAM_HEIGHT;
	}

	public String getVersionNumber() {
		return VERSION;
	}

	public String getCopyright() {
		return COPYRIGHT;
	}

	public String getProgramName() {
		return PROGRAM_NAME;
	}


	public Image getProgramIcon() {
		return PROGRAM_ICON;
	}


	public Image getHomeIcon() {
		return HOME_ICON;
	}

	public Image getInfoIcon() {
		return INFO_ICON;
	}
}
