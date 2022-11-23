package jackchangeit;

import java.util.List;

public class GameState {
	
	private DeckOfCards deck;
	private PlayerList players;
	private boolean gameOver;
	private CardSuit chosenJackSuit;
	
	public GameState() {
		deck = new DeckOfCards();
		gameOver = false;
	}
	
	boolean dealCards() {
		
		deck.shuffleDeck();

		for (int i = 0; i < JackChangeIt.NUMBER_OF_STARTING_CARDS; i++) {
			for (Player player : players.getPlayerList()) {
				player.addToPlayerCards(deck.dealCard());
			}
		}
		return true;
	}
	
	List<Player> getPlayers() {
		return this.players.getPlayerList();
	}

	CardSuit getChosenJackSuit() {
		return this.chosenJackSuit;
	}
	
	void setChosenjackSuit(CardSuit suitChoice) {
		chosenJackSuit = suitChoice;
	}

	void createPlayers(List<Player> players) {
		this.players = new PlayerList(players);
	}

	Player getCurrentPlayer() {
		return players.getCurrentPlayer();
	}
	
	String getCurrentPlayersName() {
		return getCurrentPlayer().getPlayerName();
	}
	
	Player getNextPlayer() {
		return players.getNextPlayer();
	}
	
	Card getLastPlayedCard() {
		return deck.getTopBurnCard();
	}

	List<Card> getCurrentPlayersCards() {
		return getCurrentPlayer().getPlayerCards();
	
	}

	boolean hasPlayerPlayedTheirLastCard() {
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

	boolean isNextPlayerHoldingFiveOfHearts() {
		Card fiveOfHearts = new Card(CardFace.FIVE, CardSuit.HEART);

		Player nextPlayer = players.getNextPlayer();
		if (nextPlayer.getPlayerCards().contains(fiveOfHearts)) {
			return true;
		}
		return false;
	}

	public void pickUpFive() {
		for (int i = 1; i <= JackChangeIt.ACE_OF_H_PICK_UP_AMOUNT; i++) {
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
	public boolean hasPlayerAValidCard() {
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

	public void burnCard(Card selectedCard) {
		deck.addCardToBurnDeck(selectedCard);
		
	}

	public void missATurn() {
		players.incrementCurrentPlayer();		
	}

	public void pickUpTwo() {
		players.getNextPlayer().addToPlayerCards(deck.dealCard());
		players.getNextPlayer().addToPlayerCards(deck.dealCard());		
	}

	public void getAStartingCard() {
		Card c = deck.dealCard();
		deck.addCardToBurnDeck(c);
	}

	public void reversePlayerOrder() {
		players.reversePlayerOrder();
	}

	public void pickUpACard() {
		getCurrentPlayer().addToPlayerCards(deck.dealCard());		
	}

	void nextPlayersTurn() {
		players.incrementCurrentPlayer();
	}
}
