package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {
	//GameData gameData = new GameData();
	LocalDate date;

	Date() {
		date = LocalDate.now();
		//System.out.println("Date: " + date);
	}
	
	public String getYear() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy"); 
		
		String formattedDate = date.format(format);
		return formattedDate;
	}
	
	public String getDateUSFormat() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy"); 
		
		String formattedDate = date.format(format);
		return formattedDate;
	}
}
