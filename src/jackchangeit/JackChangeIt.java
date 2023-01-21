package jackchangeit;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the details and methods needed to play Jack Change It. It
 * contains classes, IO which represents a user interface, and GameState which
 * holds the details of the current game.
 * 
 * @author Peter O'Hare
 *
 */
public final class JackChangeIt {

	static final int NUMBER_OF_STARTING_CARDS = 7;
	static final int ACE_OF_H_PICK_UP_AMOUNT = 5;
	static final int MAX_NUM_OF_PLAYERS = 6;
	static final int MIN_NUM_OF_PLAYERS = 3;

	private IO IO;
	private GameState gameState;

	public JackChangeIt() {
		gameState = new GameState();
		IO = new IO();
	}

	/**
	 * This method initiates a new game. It contains method calls for each stage of
	 * the game
	 */
	public void start() {
		createPlayers();
		dealCards();
		getAStartingCard();
		playGame();
		IO.outputGameOver(gameState);
	}

	private void createPlayers() {

		int numberOfPlayers = IO.inputNumberOfPlayers();
		List<Player> gamePlayers = new ArrayList<>();

		for (int i = 1; i <= numberOfPlayers; i++) {
			String name = IO.inputName(i);
			Player newPlayer = new Player(name);
			gamePlayers.add(newPlayer);

		}

		IO.outputAllPlayerNames(gamePlayers);

		gameState.createPlayers(gamePlayers);
	}

	/**
	 * Shuffles the deck ready for a new game. Adds 7 cards to each player,
	 * sequentially.
	 */
	private void dealCards() {
		gameState.dealCards();
		IO.outputCardsHaveBeenDealt();
	}

	/**
	 * Gets a starting card for a new game and ensures starting card is not a trick
	 * card, 2, J, Q, AofH, 5ofH.
	 */
	private void getAStartingCard() {

		gameState.getAStartingCard();
		IO.outputStartingCard(gameState);

		if (gameState.getLastPlayedCard().isTrickCard()) {
			getAStartingCard();
		}
	}

	/**
	 * Main game loop. Loops until a player runs out of cards or chooses to quit
	 * 
	 */
	private void playGame() {
		do {
			IO.outputPlayersTurn(gameState.getCurrentPlayer());
			takeTurnOrQuitChoice();

			if (gameState.hasPlayerPlayedTheirLastCard()) {
				gameState.setGameOver(true);
			}
			if (!gameState.isGameOver()) {
				gameState.nextPlayersTurn();
			}
		} while (!gameState.isGameOver());
	}

	/**
	 * Player decides to play a card from their hand or pick up a new Card
	 */
	private void pickUpOrPlayChoice() {
		int pickUpOrPlay = IO.inputPickupOrPlayChoice();

		if (pickUpOrPlay == 1) {
			playerChoosesToPlayACard();
		} else {
			pickUpACard();
		}
	}

	/**
	 * Gives player the option to take a turn or quit the game. If player quits game
	 * the flag gameOver is set to true, ending the game by exiting the main game
	 * loop.
	 */
	private void takeTurnOrQuitChoice() {

		int playerChoice = IO.inputTakeTurnOrQuitChoice();

		if (playerChoice == 1) {
			IO.outputPreturnPlayerGameState(gameState);
			pickUpOrPlayChoice();
		} else if (playerChoice == 2) {
			gameState.setGameOver(true);
		}
	}

	/**
	 * Check if the player has a valid card and if so select a card and play it. If
	 * player does not have a valid card then they must pick up a card
	 */
	private void playerChoosesToPlayACard() {
		if (gameState.hasPlayerAValidCard()) {
			playCard(selectACard());
		} else {
			IO.outputNoValidCards();
			pickUpACard();
		}
	}

	/**
	 * IO is called so the player can select a card from their hand. If this is a
	 * legal move then it is returned. If it is not a legal move then the player
	 * must choose a different card
	 * 
	 * @return the players chosen card
	 */
	private Card selectACard() {
		int selectedCardIndex = IO.inputPlayersChosenCard(gameState.getCurrentPlayer());

		Card c = gameState.getCurrentPlayer().getCard(selectedCardIndex);

		if (gameState.verifyLegalMove(c)) {
			return c;
		} else {
			IO.outputInvalidCard();
			return selectACard();
		}

	}

	/**
	 * The selected card is added to the burn deck and the card is output. The card
	 * is then checked for tricks, and if true then the correct action is called.
	 * 
	 * @param selectedCard
	 */
	private void playCard(Card selectedCard) {
		IO.outputPlayCard(selectedCard, gameState.getCurrentPlayer());
		gameState.getCurrentPlayer().removeCardFromHand(selectedCard);
		gameState.burnCard(selectedCard);

		if (selectedCard.isTrickCard()) {
			playTrickCard(selectedCard);
		}
	}

	/**
	 * If a trick card is played the sequence for this type of trick is initiated.
	 * 
	 * @param selectedCard
	 */
	private void playTrickCard(Card selectedCard) {
		switch (selectedCard.getFace()) {
		case TWO:
			pickUpTwo();
			break;
		case EIGHT:
			missATurn();
			break;
		case QUEEN:
			reversePlay();
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


	private void selectASuit() {
		changeSuit(IO.inputSelectASuit());

	}

	/**
	 * When a Jack is played the player may choose which suit is now valid for the
	 * gameplay
	 * 
	 * @param suitChoice
	 */
	private void changeSuit(int suitChoice) {
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
		gameState.setChosenjackSuit(chosenJackSuit);
		IO.outputJackChangedSuit(gameState);
	}

	/**
	 * 5 of Hearts can block the A of Hearts, so if the Ace is played a check is
	 * done to see if the target holds the five. If they hold the five they get the
	 * option to block the attack. If they do not hold the five then they must pick
	 * up 5
	 */
	private void playAceOfHearts() {
		if (gameState.isNextPlayerHoldingFiveOfHearts()) {
			fiveOfHeartsDefence();
		} else {
			pickUpFive();
		}
	}

	/**
	 * The player holds a five of Hearts. They must choose to play this card or pick
	 * up 5 cards
	 */
	private void fiveOfHeartsDefence() {
		Player nextPlayer = gameState.getNextPlayer();

		int fiveOfHeartsChoice = IO.outputFiveOfHeartsDefence(nextPlayer);

		if (fiveOfHeartsChoice == 1) {
			playFiveOfHearts();
			IO.outputFiveOfHeartsIsPlayed(nextPlayer);
		} else {
			pickUpFive();
		}
	}

	/**
	 * 5 of Hearts is played as defence to Ace of Hearts
	 */
	private void playFiveOfHearts() {
		Card fiveOfHearts = new Card(CardFace.FIVE, CardSuit.HEART);
		gameState.getNextPlayer().removeCardFromHand(fiveOfHearts);
		missATurn();
	}

	/**
	 * Deals 5 cards to the next player
	 */
	private void pickUpFive() {
		IO.outputPickUpFive(gameState.getNextPlayer());
		gameState.pickUpFive();
		missATurn();
	}

	/**
	 * Deals 1 card and adds it to the current players hand.
	 * 
	 * @return
	 */
	private void pickUpACard() {
		gameState.pickUpACard();
		IO.outputPickUpACard(gameState.getCurrentPlayer());
	}

	/**
	 * Reverses the order of play. This is called when a player plays a Queen.
	 */
	private void reversePlay() {
		IO.outputReverse();
		gameState.reversePlayerOrder();
	}

	/**
	 * Makes the next player miss a turn. This is called when a player plays an
	 * Eight.
	 */
	private void missATurn() {
		IO.outputMissATurn(gameState.getNextPlayer());
		gameState.missATurn();
	}

	/**
	 * Makes the next player pick up 2 cards for when a player plays a 2.
	 */
	private void pickUpTwo() {
		IO.outputPickUpTwo(gameState.getNextPlayer());
		gameState.pickUpTwo();
		missATurn();
	}

}
