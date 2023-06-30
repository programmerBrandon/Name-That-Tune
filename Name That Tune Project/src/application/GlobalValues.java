package application;

import javafx.scene.image.Image;

public class GlobalValues {
	//Information that needs to be updated across all 'scenes' like the header and footer is stored in this class and the info should be
	//updated in the variables below if changes are needed.
	private final int PROGRAM_WIDTH = 550; //Temporary duplicate while converting code to use getter methods
	private final int PROGRAM_HEIGHT = 500; //Temporary duplicate while converting code to use getter methods
	private final String VERSION = "Version: Alpha 2.0 [0.2.0-alpha]"; //Temporary duplicate while converting code to use getter methods
	private final String COPYRIGHT = "©2023 Brandon Green"; //Temporary duplicate while converting code to use getter methods
	private final String PROGRAM_NAME = "Name That Tune"; //Temporary duplicate while converting code to use getter methods
	private final  Image PROGRAM_ICON = new Image("application/images/programIcon.png");
	private final Image HOME_ICON = new Image("application/images/homeIcon.png");
	private final Image INFO_ICON = new Image("application/images/infoIcon.png");
	
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
