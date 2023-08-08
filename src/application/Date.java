package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class that will get the current date from the user's system and provide several methods to display it.
 * @author Brandon Green
 *
 */
public class Date {
	LocalDate date;

	Date() {
		date = LocalDate.now();
		//System.out.println("FIXME: Date: " + date); //FIXME
	}
	
	/**
	 * Method that returns the year (as set by the user's device).
	 * @returns a String with the current year.
	 */
	public String getYear() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy"); 
		
		return date.format(format);
	}
	
	/**
	 * A method that returns the date in USA Format with slashes as separator (MM/dd/yyyy).
	 * @returns A String of the date in USA Format, separated by slashes.
	 */
	public String getDateUSFormat() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy"); 
		
		return date.format(format);
	}
	
	/**
	 * A method that returns the date in USA Format with dashes as separator (MM-dd-yyyy).
	 * @returns A String of the date in USA Format, separated by dashes.
	 */
	public String getDateUSFormatDashes() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy"); 
		
		return date.format(format);
	}
}
