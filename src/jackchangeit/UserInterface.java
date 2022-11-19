package jackchangeit;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserInterface {

	private Scanner scanner;
	private JackChangeIt jackChangeIt;

	public UserInterface(Scanner scanner, JackChangeIt jackChangeIt) {
		this.scanner = scanner;
		this.jackChangeIt = jackChangeIt;
	}

	private Scanner getScanner() {
		return scanner;
	}

	private JackChangeIt getJackChangeIt() {
		return this.jackChangeIt;
	}

	/**
	 * Main game loop.
	 * 
	 */
	void playGame() {
		do {
			System.out.println("It is " + getJackChangeIt().getCurrentPlayersName() + "'s turn.");
			takeTurnOrQuitChoice();

			if (getJackChangeIt().haPlayerPlayedTheirLastCard()) {
				getJackChangeIt().setGameOver(true);
			}
			if (!getJackChangeIt().isGameOver()) {
				getJackChangeIt().nextPlayersTurn();
			}
		} while (!getJackChangeIt().isGameOver());

		gameOver();

	}

	/**
	 * Gives player the option to take a turn or quit the game. If player quits game
	 * the flag gameOver is set to true, ending the game by exiting the main game
	 * loop.
	 */
	void takeTurnOrQuitChoice() {

		int playerChoice;

		System.out.println("Please select an option");
		System.out.println("1 Take turn");
		System.out.println("2 Quit game");

		playerChoice = getValidUserInput(1, 2);

		if (playerChoice == 1) {
			outputCurrentPlayersCardState();
			pickupOrPlayChoice();
		} else if (playerChoice == 2) {
			getJackChangeIt().setGameOver(true);
		}
	}

	/**
	 * Outputs the current players cards then prompts the player to choose a card to
	 * play or pick up a card.
	 * 
	 */
	void outputCurrentPlayersCardState() {

		System.out.println(getJackChangeIt().getCurrentPlayersName() + " - Your cards : ");
		displayPlayersCards();

		System.out.println("\nThe last card to be played was " + getJackChangeIt().getLastPlayedCard());

		if (getJackChangeIt().getLastPlayedCard().getFace().equals(CardFace.JACK)) {
			System.out.println("The valid suit is " + getJackChangeIt().getChosenJackSuit());
		}

	}

	/**
	 * If player chooses to play a card, then a check is done to confirm the player
	 * has a valid card to play. Calls are made to select a valid card, and play the
	 * chosen card.
	 * 
	 */
	void pickupOrPlayChoice() {
		System.out.println("\nSelect an option : ");
		System.out.println("1 - Play a card");
		System.out.println("2 - Pick up a card");
		
		int turnSelection = getValidUserInput(1, 2); 

		if (turnSelection == 1) {
			playerChoosesToPlayACard();
		} else {
			pickUp();
		}
	}
	
	void playerChoosesToPlayACard() {
		if (getJackChangeIt().playerHasAValidCard()) {
			playCard(selectACard());
		} else {
			System.out.println("You have no valid playing cards !");
			pickUp();
		}
	}

	/**
	 * The selected card is added to the burn deck and the card is output. The card
	 * is then checked for tricks, and if true then the correct action is called.
	 * 
	 * @param selectedCard
	 */
	void playCard(Card selectedCard) {
		System.out.println(jackChangeIt.getCurrentPlayersName() + " has played " + selectedCard.toString());
		getJackChangeIt().getCurrentPlayer().removeCardFromHand(selectedCard);
		getJackChangeIt().burnCard(selectedCard);

		if (selectedCard.isTrickCard()) {
			playTrickCard(selectedCard);
		}
	}
	
	void playTrickCard(Card selectedCard) {
		switch (selectedCard.getFace()) {
		case TWO:
			getJackChangeIt().pickUpTwo();
			System.out.println(getJackChangeIt().getNextPlayersName() + " has picked up 2 cards");
			getJackChangeIt().missATurn();
			break;
		case EIGHT:
			System.out.println(getJackChangeIt().getNextPlayersName() + " misses a turn.");
			getJackChangeIt().missATurn();
			break;
		case QUEEN:
			getJackChangeIt().reversePlay();
			System.out.println("The player order has been reversed !");
			break;
		case JACK:
			selectASuit();
			break;
		case ACE:
			playAceOfHearts();
			break;
		default:
			break;
		}
	}

	/**
	 * The players cards have been displayed next to integers in the calling method.
	 * If the chosen card is verified as a legal move then it is removed from the
	 * players hand and returned.
	 * 
	 * @return the players chosen card
	 */
	Card selectACard() {
		int selectedCardIndex;
		System.out.println("Enter the number of your chosen card : ");

		selectedCardIndex = getValidUserInput(0, getJackChangeIt().getCurrentPlayersCards().size() - 1);

		Card c = getJackChangeIt().getCurrentPlayer().getCard(selectedCardIndex);

		if (getJackChangeIt().verifyLegalMove(c)) {
			return c;
		} else {
			System.out.println("This card can not be played. Try again.");
			return selectACard();
		}

	}

	void pickUp() {
		getJackChangeIt().pickUpACard();
		System.out.println(jackChangeIt.getCurrentPlayersName() + " has picked up a card.");
	}

	int getValidUserInput(int lowerBound, int upperBound) throws NoSuchElementException{
		int userInput = -1;

		while (true) {
			try {
				userInput = getScanner().nextInt();

				if (userInput < lowerBound || userInput > upperBound){
					System.out.println("Please enter a valid number in the range " + lowerBound + " - " + upperBound);
				} else {
					return userInput;
				}
			} catch  (InputMismatchException e) {
				System.err.println("Please enter a valid number");
			} catch (NoSuchElementException e){
				throw new NoSuchElementException("No suitable input");
			} finally{
				getScanner().nextLine();
			}
		}

	}

	/**
	 * Stores the players chosen suit for the game in the variable
	 * lastChosenJackChangeSuit.
	 * 
	 */
	void selectASuit() {

		System.out.println("Select your chosen suit : ");
		System.out.println("1 Hearts");
		System.out.println("2 Diamonds");
		System.out.println("3 Clubs");
		System.out.println("4 Spades");
		changeSuit(getValidUserInput(1, 4));

	}

	void changeSuit(int suitChoice) {
		CardSuit chosenJackSuit = null;

		if (suitChoice == 1) {
			chosenJackSuit = CardSuit.HEART;
		} else if (suitChoice == 2) {
			chosenJackSuit = CardSuit.DIAMOND;
		} else if (suitChoice == 3) {
			chosenJackSuit = CardSuit.CLUB;
		} else if (suitChoice == 4) {
			chosenJackSuit = CardSuit.SPADE;
		}
		getJackChangeIt().setChosenjackSuit(chosenJackSuit);
		System.out.println("You have changed the suit to " + getJackChangeIt().getChosenJackSuit());
	}

	void playAceOfHearts() {
		if (getJackChangeIt().isNextPlayerHoldingFiveOfHearts()) {
			fiveOfHeartsDefence();
		} else {
			pickUpFive();
		}
	}

	void fiveOfHeartsDefence() {
		String nextPlayer = getJackChangeIt().getNextPlayersName();

		System.out.println(nextPlayer + " is holding the 5 of Hearts so they can block the Ace ! !");
		System.out.println(nextPlayer + " : Do you want to play the 5 of Hearts or pick up 5?");
		System.out.println("1 - Play the 5 of Hearts.");
		System.out.println("2 - Pick up 5.");

		int userChoice = getValidUserInput(1, 2);

		if (userChoice == 1) {
			getJackChangeIt().fiveOfHeartsDefence();
			System.out.println(nextPlayer + " has played the FIVE of HEART in defence.");
			getJackChangeIt().missATurn();
		} else {
			pickUpFive();
		}
	}

	void pickUpFive() {
		getJackChangeIt().pickUpFive();
		System.out.println(getJackChangeIt().getNextPlayersName() + " has picked up 5 cards.");
	}

	void displayPlayersCards() {
		List<Card> cards = getJackChangeIt().getCurrentPlayersCards();
		for (int i = 0; i < cards.size(); i++) {
			System.out.println(i + " - " + cards.get(i));
		}
	}

	/**
	 * 
	 */
	void gameOver() {

		System.out.println("Game over ! !");

		for (Player player : getJackChangeIt().getPlayers().getPlayerList()) {
			if (player.getPlayerCards().isEmpty()) {
				System.out.println(getJackChangeIt().getCurrentPlayersName() + " has run out of cards !");
				System.out.println(getJackChangeIt().getCurrentPlayersName() + " has won the game ! !");
			}
		}

		outputPlayersPoints();

	}

	void outputPlayersPoints() {

		System.out.println("------------------------ Player Points ------------------------");

		for (Player player : getJackChangeIt().getPlayers().getPlayerList()) {
			System.out.println(player.getPlayerName() + " --- " + player.calculatePlayerPoints());
		}

	}

}
