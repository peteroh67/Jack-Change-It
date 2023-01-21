package jackchangeit;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class is the user interface for a game of Jack Change It. All textual
 * output is through this class and all input occurs via the Scanner, which is
 * instantiated when an IO object is instantiated and closed when the game ends.
 * 
 * @author Peter O'Hare
 *
 */
public class IO {

	private Scanner scanner;

	public IO() {
		this.scanner = new Scanner(System.in);
	}

	public void closeScanner() {
		scanner.close();
	}

	void outputWelcome() {
		System.out.println("------------------Welcome to Jack Change It.------------------");
	}

	/**
	 * 
	 * @param lowerBound
	 * @param upperBound
	 * @return
	 */
	private int getValidUserInput(int lowerBound, int upperBound) {

		int userInput = -1;

		while (true) {

			try {
				userInput = scanner.nextInt();

				if (userInput < lowerBound || userInput > upperBound) {
					System.out.println("Please enter a valid number in the range " + lowerBound + " - " + upperBound);
				} else {
					return userInput;
				}

			} catch (InputMismatchException e) {
				System.err.println("Please enter a valid number");
				if (scanner.hasNext()) {
					scanner.nextLine();
				}
			} catch (NoSuchElementException e) {
				System.err.println("No valid input");
			}

		}

	}

	/**
	 * 
	 * @param playerNumber
	 * @return
	 */
	String inputName(int playerNumber) {

		while (true) {
			try {

				System.out.println("Player " + playerNumber + " : Enter your name.");
				String playerName = scanner.nextLine();
				System.out.println("You have entered " + playerName);
				return playerName;
			} catch (NoSuchElementException e) {
				System.err.printf("Try again. Invalid input.%n");
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	int inputNumberOfPlayers() {

		System.out.printf("How many players? %d - %s%n", JackChangeIt.MIN_NUM_OF_PLAYERS,
				JackChangeIt.MAX_NUM_OF_PLAYERS);

		int numOfPlayers = getValidUserInput(JackChangeIt.MIN_NUM_OF_PLAYERS, JackChangeIt.MAX_NUM_OF_PLAYERS);
		System.out.println("You have chosen a game of " + numOfPlayers + " players");
		scanner.nextLine();
		return numOfPlayers;
	}

	public void outputAllPlayerNames(List<Player> players) {
		System.out.println("The " + players.size() + " player names are : ");

		for (int i = 0; i < players.size(); i++) {
			System.out.print(players.get(i).getPlayerName() + "\t");
		}
		System.out.println();
	}

	void outputCardsHaveBeenDealt() {
		System.out.println("The deck has been shuffled and each player has been dealt "
				+ JackChangeIt.NUMBER_OF_STARTING_CARDS + " cards.");
	}

	void outputStartingCard(GameState gameState) {
		System.out.println("Dealing a starting card...");

		Card c = gameState.getLastPlayedCard();
		System.out.println(c.toString());

		if (c.isTrickCard()) {
			System.out.println("Cannot start on a trickCard");
		}
	}

	void outputPlayersTurn(Player player) {
		System.out.println("It is " + player.getPlayerName() + "'s turn.");
	}

	int inputTakeTurnOrQuitChoice() {
		System.out.println("Please select an option");
		System.out.println("1 Take turn");
		System.out.println("2 Quit game");

		return getValidUserInput(1, 2);
	}

	void outputPreturnPlayerGameState(GameState gameState) {

		System.out.println(gameState.getCurrentPlayersName() + " - Your cards : ");
		outputPlayersCards(gameState.getCurrentPlayer());

		System.out.println("\nThe last card to be played was " + gameState.getLastPlayedCard());

		if (gameState.getLastPlayedCard().getFace().equals(CardFace.JACK)) {
			System.out.println("The valid suit is " + gameState.getChosenJackSuit());
		}

	}

	void outputPlayersCards(Player player) {
		List<Card> cards = player.getPlayerCards();
		for (int i = 0; i < cards.size(); i++) {
			System.out.println(i + " - " + cards.get(i));
		}
	}

	int inputPickupOrPlayChoice() {
		System.out.println("\nSelect an option : ");
		System.out.println("1 - Play a card");
		System.out.println("2 - Pick up a card");

		return getValidUserInput(1, 2);
	}

	int inputPlayersChosenCard(Player player) {
		System.out.println("Enter the number of your chosen card : ");

		return getValidUserInput(0, player.getPlayerCards().size() - 1);
	}

	void outputPickUpACard(Player player) {
		System.out.println(player.getPlayerName() + " has picked up a card.");
	}

	void outputPlayCard(Card selectedCard, Player player) {
		System.out.println(player.getPlayerName() + " has played " + selectedCard.toString());
	}

	public void outputInvalidCard() {
		System.out.println("This card can not be played.");
	}

	public int inputSelectASuit() {
		System.out.println("Select your chosen suit : ");
		System.out.println("1 Hearts");
		System.out.println("2 Diamonds");
		System.out.println("3 Clubs");
		System.out.println("4 Spades");

		return getValidUserInput(1, 4);
	}

	public void outputNoValidCards() {
		System.out.println("You have no valid playing cards !");
	}

	public int outputFiveOfHeartsDefence(Player nextPlayer) {
		System.out.println(nextPlayer.getPlayerName() + " is holding the 5 of Hearts so they can block the Ace ! !");
		System.out.println(nextPlayer.getPlayerName() + " : Do you want to play the 5 of Hearts or pick up 5?");
		System.out.println("1 - Play the 5 of Hearts.");
		System.out.println("2 - Pick up 5.");

		return getValidUserInput(1, 2);
	}

	public void outputFiveOfHeartsIsPlayed(Player nextPlayer) {
		System.out.println(nextPlayer.getPlayerName() + " has played the FIVE of HEART in defence.");
	}

	public void outputJackChangedSuit(GameState gameState) {
		System.out.println("You have changed the suit to " + gameState.getChosenJackSuit());
	}

	public void outputMissATurn(Player player) {
		System.out.println(player.getPlayerName() + " misses a turn.");
	}

	public void outputPickUpTwo(Player nextPlayer) {
		System.out.println(nextPlayer.getPlayerName() + " has picked up 2 cards");
	}

	public void outputReverse() {
		System.out.println("The player order has been reversed !");
	}

	public void outputPickUpFive(Player nextPlayer) {
		System.out.println(nextPlayer.getPlayerName() + " has picked up 5 cards.");
	}

	private void outputPlayersPoints(GameState gameState) {

		System.out.println("------------------------ Player Points ------------------------");

		for (Player player : gameState.getPlayers()) {
			System.out.println(player.getPlayerName() + " --- " + player.calculatePlayerPoints());
		}

	}

	void outputGameOver(GameState gameState) {

		System.out.println("Game over ! !");

		for (Player player : gameState.getPlayers()) {
			if (player.getPlayerCards().isEmpty()) {
				System.out.println(player.getPlayerName() + " has run out of cards !");
				System.out.println(player.getPlayerName() + " has won the game ! !");
			}
		}

		outputPlayersPoints(gameState);
		closeScanner();
	}

}
