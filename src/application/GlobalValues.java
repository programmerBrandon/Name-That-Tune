package application;

import javafx.scene.image.Image;

/**
 * Class that holds variables and data that is needed by every scene (such as header, footer and window size data) in the program.
 * @author Brandon Green
 *
 */
public class GlobalValues {
	static Date date = new Date();
	
	private final int PROGRAM_WIDTH = 550; 
	private final int PROGRAM_HEIGHT = 550; 
	private final int MAX_SONGS_SUPPORTED = 10;
	private final int MAX_PLAYERS_SUPPORTED = 25;
	private final String VERSION = "Version: 0.4.0-alpha";
	//private final String COPYRIGHT = "©2023 Brandon Green";
	private final String COPYRIGHT = "©" + date.getYear() + " Brandon Green";
	private final String PROGRAM_NAME = "Name That Tune";
	private final Image PROGRAM_ICON = new Image("application/images/programIcon.png");
	private final Image HOME_ICON = new Image("application/images/homeIcon.png");
	private final Image INFO_ICON = new Image("application/images/infoIcon.png");
	
	public int getProgramWidth() {
		return PROGRAM_WIDTH;
	}
	
	public int getProgramHeight() {
		return PROGRAM_HEIGHT;
	}
	
	public int getMaxSongsSupported() {
		return MAX_SONGS_SUPPORTED;
	}
	
	public int getMaxPlayersSupported() {
		return MAX_PLAYERS_SUPPORTED;
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
