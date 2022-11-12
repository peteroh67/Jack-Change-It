package jackchangeit;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JackChangeItStartUp {
	
	private JackChangeIt jackChangeIt;
	private UserInterface UI;
	private Scanner scanner;
	
	public JackChangeItStartUp(Scanner scanner) {
		jackChangeIt = new JackChangeIt();	
		this.scanner = scanner;
		UI = new UserInterface(scanner, jackChangeIt);
	}
	
	public JackChangeIt getJackChangeIt() {
		return jackChangeIt;
	}

	public UserInterface getUI() {
		return UI;
	}
	
	Scanner getScanner(){
		return this.scanner;
	}
	
	public void start() {
		System.out.println("------------------Welcome to Jack Change It.------------------");
		createPlayers();
		dealCards();
		getValidStartingCard();
		UI.playGame();
	}

	/**
	 * 
	 */
	private void createPlayers() {

		int numberOfPlayers;
		List<Player> gamePlayers = new ArrayList<>();
		boolean nameIsValid = true;

		System.out.printf("How many players? %d - %s%n", JackChangeIt.MIN_NUM_OF_PLAYERS,
				JackChangeIt.MAX_NUM_OF_PLAYERS);

		numberOfPlayers = UI.getValidUserInput(JackChangeIt.MIN_NUM_OF_PLAYERS, JackChangeIt.MAX_NUM_OF_PLAYERS);

		for (int i = 1; i <= numberOfPlayers; i++) {
			do {
				try {
					nameIsValid = true;
					System.out.println("Player " + i + " : Enter your name.");
					String name = getScanner().nextLine();

					if (name.isBlank()) {
						throw new RuntimeException();
					}
					Player newPlayer = new Player(name);
					gamePlayers.add(newPlayer);

				} catch (RuntimeException e) { // other exceptions need caught
					System.err.printf("Try again. Invalid input.%n");
					nameIsValid = false;

				}
			} while (!nameIsValid);

		}
		getJackChangeIt().createPlayers(gamePlayers);
	}

	/**
	 * Ensures starting card is not a trick card, 2, J, Q, AofH, 5ofH.
	 * 
	 * @return
	 */
	private void getValidStartingCard() {

		System.out.println("Getting a starting card..");

		while (true) {
			boolean isCardValid = getJackChangeIt().getAStartingCard();

			if (!isCardValid) {
				System.out.println(getJackChangeIt().getLastPlayedCard());
				System.out.println("Cannot start on a trick card");

			} else {
				System.out.println("The starting card is : " + getJackChangeIt().getLastPlayedCard());
				return;
			}
		}
	}

	private void dealCards() {
		getJackChangeIt().dealCards();
		System.out.println("The deck has been shuffled and each player has been dealt "
				+ JackChangeIt.NUMBER_OF_STARTING_CARDS + " cards.");

	}
}
