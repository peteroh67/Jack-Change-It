package jackchangeit;

import java.util.List;

public final class JackChangeIt {

	static final int ACE_OF_H_PICK_UP_AMOUNT = 5;

	static final int MAX_NUM_OF_PLAYERS = 6;

	static final int MIN_NUM_OF_PLAYERS = 3;

	static final int NUMBER_OF_STARTING_CARDS = 7;

	private boolean gameOver;

	private DeckOfCards deck;

	private PlayerList players;

	private CardSuit chosenJackSuit;

	public JackChangeIt() {
		deck = new DeckOfCards();
	}

	PlayerList getPlayers() {
		return this.players;
	}

	CardSuit getChosenJackSuit() {
		return this.chosenJackSuit;
	}
	
	void setChosenjackSuit(CardSuit suitChoice) {
		chosenJackSuit = suitChoice;
	}

	/**
	 * 
	 */
	void createPlayers(List<Player> players) {
		this.players = new PlayerList(players);
	}

	/**
	 * Shuffles the deck ready for a new game. Adds 7 cards to each player,
	 * sequentially.
	 */
	void dealCards() {
		deck.shuffleDeck();

		for (int i = 0; i < NUMBER_OF_STARTING_CARDS; i++) {
			for (Player player : players.getPlayerList()) {
				player.addToPlayerCards(deck.dealCard());
			}
		}
	}

	boolean getAStartingCard() {
		Card card = deck.dealCard();
		deck.addCardToBurnDeck(card);

		if (card.isTrickCard()) {
			return false;
		}
		return true;
	}

	/**
	 * Deals 1 card and adds it to the current players hand.
	 * 
	 * @return
	 */
	void pickUpACard() {
		getCurrentPlayer().addToPlayerCards(deck.dealCard());
	}

	/**
	 * Reverses the order of play. This is called when a player plays a Queen.
	 */
	void reversePlay() {
		players.reversePlayerOrder();
	}

	/**
	 * Makes the next player miss a turn. This is called when a player plays an
	 * Eight.
	 */
	void missATurn() {
		players.incrementCurrentPlayer();
	}

	/**
	 * Makes the next player pick up 2 cards for when a player plays a 2. This
	 * constitutes the players go so missATurn is called.
	 */
	void pickUpTwo() {
		players.getNextPlayer().addToPlayerCards(deck.dealCard());
		players.getNextPlayer().addToPlayerCards(deck.dealCard());
	}
	
	boolean isNextPlayerHoldingFiveOfHearts() {
		Card fiveOfHearts = new Card(CardFace.FIVE, CardSuit.HEART);

		Player nextPlayer = players.getNextPlayer();
		if (nextPlayer.getPlayerCards().contains(fiveOfHearts)) {
			return true;
		}
		return false;
	}

	void fiveOfHeartsDefence() {
		Card fiveOfHearts = new Card(CardFace.FIVE, CardSuit.HEART);
		Player nextPlayer = players.getNextPlayer();
		nextPlayer.getPlayerCards().remove(fiveOfHearts);
	}

	void pickUpFive() {
		for (int i = 1; i <= ACE_OF_H_PICK_UP_AMOUNT; i++) {
			players.getNextPlayer().addToPlayerCards(deck.dealCard());
		}
	}
	
	/**
	 * A check is done to confirm player has a valid card to play. This will usually
	 * be done by checking if the face and suit of the last played card match any of
	 * the players cards. Alternatively if the last played card was a jack then one
	 * of the players cards must match the last players chosen suit. Or if the
	 * player holds a jack, this can be played at any time
	 * 
	 * @return true if a valid card is found or false if none are found.
	 */
	boolean playerHasAValidCard() {
		for (int i = 0; i < getCurrentPlayer().getPlayerCards().size(); i++) {
			if (verifyLegalMove(getCurrentPlayer().getPlayerCards().get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Validate a players chosen card. A jack is always a valid move. If the last
	 * played card was a jack then the players card is valid if it matches the last
	 * players chosen suit. If the last played card was not a jack then the players
	 * chosen card must match either the suit or the face of the the last played
	 * card.
	 * 
	 * @param Card c to be verified
	 * @return boolean true if card is a valid move or false if illegal move
	 */
	boolean verifyLegalMove(Card card) {

		if ((card.getFace().equals(CardFace.JACK))
				|| (getLastPlayedCard().getFace().equals(CardFace.JACK) && card.getSuit().equals(chosenJackSuit))
				|| (!card.getFace().equals(CardFace.JACK) && (card.getFace().equals(getLastPlayedCard().getFace())
						|| card.getSuit().equals(getLastPlayedCard().getSuit())))) {
			return true;
		}
		return false;
	}
	
	void nextPlayersTurn() {
		players.incrementCurrentPlayer();
	}
	
	void burnCard(Card card) {
		deck.addCardToBurnDeck(card);
	}

	/**
	 * Gets the current player.
	 * 
	 * @return the player whose turn it is
	 */
	Player getCurrentPlayer() {
		return players.getCurrentPlayer();
	}
	
	String getCurrentPlayersName() {
		return getCurrentPlayer().getPlayerName();
	}
	
	Player getNextPlayer() {
		return players.getNextPlayer();
	}
	
	String getNextPlayersName() {
		return players.getNextPlayer().getPlayerName();
	}

	/**
	 * Gets the card at the top of the burn deck. The face and suit of this card
	 * will determine the next players valid cards in most cases. If the top burn
	 * card is a jack then the last players chosen suit is stored in the variable
	 * lastChosenJackChangeSuit
	 * 
	 * @return
	 */
	Card getLastPlayedCard() {
		return deck.getTopBurnCard();
	}


	/**
	 * Numbers and outputs all of the current players cards.
	 */
	List<Card> getCurrentPlayersCards() {
		return getCurrentPlayer().getPlayerCards();
	
	}

	/**
	 * Check if a player has played their last card.
	 */
	boolean haPlayerPlayedTheirLastCard() {
		if (getCurrentPlayer().getPlayerCards().isEmpty()) {
			return true;
		}
		return false;
	}

	boolean isGameOver() {
		return gameOver;
	}

	void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
}
