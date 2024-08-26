package application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class containing methods for creating, validating, formatting and saving the title.
 * @author Brandon Green
 *
 */
public class Title {
	private String prefix = "Name That Tune";
	private String title = "";
	//private String formattedTitle = "";
	
	public Title() {}
	
	public Title(String title) {
		this.title = title;
	}
	
	public Title(String prefix, String title) {
		this.prefix = prefix;
		this.title = title;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String toString() {
		return formatTitle();
	}
	
	/**
	 * 
	 * @return Either a formatted title or a default title with the current date in US format (with slashes).
	 */
	public String toAltString() {
		if(!title.isBlank()) {
			return formatTitle();
		}
		
		else {
			Date date = new Date();
			return prefix + " - " + date.getDateUSFormat();
		}
	}
	
	/**
	 * Formats the title to add the prefix before it.
	 * @return A formatted title as a string.
	 */
	private String formatTitle() {
		if(!title.equals("")) {
			return prefix + " - " + title;
		}
		
		else {
			Date date = new Date();
			return prefix + " - " + date.getDateUSFormatDashes();
		}
	}
	
	/**
	 * Verifies the title does not contain any illegal characters.
	 * @return true if title contains illegal characters, false otherwise.
	 */
	public boolean validateTitle() {
		Pattern invalidCharsPattern = Pattern.compile("[<>:/|?\"\\\\*.]");
		Matcher matcher = invalidCharsPattern.matcher(title);
		
		return matcher.find();
	}
	
	/**
	 * 
	 * @return true if the title variable is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return title.isBlank();
	}
	
	/**
	 * Saves a formatted title to GameData.
	 */
	public void saveTitle() {
		GameData.setTitle(new Title(prefix, title));
		//GameData.setGameTitle(formatTitle());
	}
	
}
