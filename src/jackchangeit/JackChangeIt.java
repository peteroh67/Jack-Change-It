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
		IO.gameOver(gameState);
	}

	private void createPlayers() {

		int numberOfPlayers = IO.getNumberOfPlayers();
		List<Player> gamePlayers = new ArrayList<>();

		for (int i = 1; i <= numberOfPlayers; i++) {
			String name = IO.getName(i);
			Player newPlayer = new Player(name);
			gamePlayers.add(newPlayer);

		}

		IO.playerNamesSuccessful(gamePlayers);

		gameState.createPlayers(gamePlayers);
	}

	/**
	 * Shuffles the deck ready for a new game. Adds 7 cards to each player,
	 * sequentially.
	 */
	void dealCards() {
		gameState.dealCards();
		IO.cardsHaveBeenDealt();
	}

	/**
	 * Gets a starting card for a new game and ensures starting card is not a trick
	 * card, 2, J, Q, AofH, 5ofH.
	 */
	private void getAStartingCard() {

		gameState.getAStartingCard();
		IO.startingCard(gameState);

		if (gameState.getLastPlayedCard().isTrickCard()) {
			getAStartingCard();
		}
	}

	/**
	 * Main game loop. Loops until a player runs out of cards or chooses to quit
	 * 
	 */
	void playGame() {
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
	void pickUpOrPlayChoice() {
		int pickUpOrPlay = IO.pickupOrPlayChoice();

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
	void takeTurnOrQuitChoice() {

		int playerChoice = IO.takeTurnOrQuitChoice();

		if (playerChoice == 1) {
			IO.preturnPlayerGameState(gameState);
			pickUpOrPlayChoice();
		} else if (playerChoice == 2) {
			gameState.setGameOver(true);
		}
	}

	/**
	 * Check if the player has a valid card and if so select a card and play it. If
	 * player does not have a valid card then they must pick up a card
	 */
	void playerChoosesToPlayACard() {
		if (gameState.hasPlayerAValidCard()) {
			playCard(selectACard());
		} else {
			IO.noValidCards();
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
	Card selectACard() {
		int selectedCardIndex = IO.getPlayersChosenCard(gameState);

		Card c = gameState.getCurrentPlayer().getCard(selectedCardIndex);

		if (gameState.verifyLegalMove(c)) {
			return c;
		} else {
			IO.invalidCard();
			return selectACard();
		}

	}

	/**
	 * The selected card is added to the burn deck and the card is output. The card
	 * is then checked for tricks, and if true then the correct action is called.
	 * 
	 * @param selectedCard
	 */
	void playCard(Card selectedCard) {
		IO.playCard(selectedCard, gameState);
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
	void playTrickCard(Card selectedCard) {
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

	/**
	 * Stores the players chosen suit for the game in the variable
	 * lastChosenJackChangeSuit.
	 * 
	 */
	void selectASuit() {
		changeSuit(IO.selectASuit());

	}

	/**
	 * When a Jack is played the player may choose which suit is now valid for the
	 * gameplay
	 * 
	 * @param suitChoice
	 */
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
		gameState.setChosenjackSuit(chosenJackSuit);
		IO.jackChangedSuit(gameState);
	}

	/**
	 * 5 of Hearts can block the A of Hearts, so if the Ace is played a check is
	 * done to see if the target holds the five. If they hold the five they get the
	 * option to block the attack. If they do not hold the five then they must pick
	 * up 5
	 */
	void playAceOfHearts() {
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
	void fiveOfHeartsDefence() {
		String nextPlayerName = gameState.getNextPlayer().getPlayerName();

		int fiveOfHeartsChoice = IO.fiveOfHeartsDefence(nextPlayerName);

		if (fiveOfHeartsChoice == 1) {
			playFiveOfHearts();
			IO.fiveOfHeartsIsPlayed(nextPlayerName);
		} else {
			pickUpFive();
		}
	}

	/**
	 * 5 of Hearts is played as defence to Ace of Hearts
	 */
	void playFiveOfHearts() {
		Card fiveOfHearts = new Card(CardFace.FIVE, CardSuit.HEART);
		gameState.getNextPlayer().removeCardFromHand(fiveOfHearts);
		missATurn();
	}

	/**
	 * Deals 5 cards to the next player
	 */
	void pickUpFive() {
		IO.pickUpFive(gameState.getNextPlayer().getPlayerName());
		gameState.pickUpFive();
		missATurn();
	}

	/**
	 * Deals 1 card and adds it to the current players hand.
	 * 
	 * @return
	 */
	void pickUpACard() {
		gameState.pickUpACard();
		IO.pickUpACard(gameState.getCurrentPlayer());
	}

	/**
	 * Reverses the order of play. This is called when a player plays a Queen.
	 */
	void reversePlay() {
		IO.reverse();
		gameState.reversePlayerOrder();
	}

	/**
	 * Makes the next player miss a turn. This is called when a player plays an
	 * Eight.
	 */
	void missATurn() {
		IO.missATurn(gameState.getNextPlayer().getPlayerName());
		gameState.missATurn();
	}

	/**
	 * Makes the next player pick up 2 cards for when a player plays a 2.
	 */
	void pickUpTwo() {
		IO.pickUpTwo(gameState.getNextPlayer().getPlayerName());
		gameState.pickUpTwo();
		missATurn();
	}

}
