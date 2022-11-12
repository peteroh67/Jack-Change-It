package jackchangeit;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		JackChangeItStartUp jackChangeIt = new JackChangeItStartUp(scanner);
		
		jackChangeIt.start();
		scanner.close();
	}

}
