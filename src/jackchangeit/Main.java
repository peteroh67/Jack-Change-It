package jackchangeit;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		JackChangeIt jackChangeIt = new JackChangeIt();
		
		UserInterface UI = new UserInterface(scanner, jackChangeIt);
		
		UI.start();
		scanner.close();
	}

}
