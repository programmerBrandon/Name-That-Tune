package application;

import java.time.LocalDate;

public class Date {
	GameData gameData = new GameData();
	LocalDate date = LocalDate.now();

	Date() {
		System.out.println("Date: " + date);
	}
}
