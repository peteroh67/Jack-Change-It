package jackchangeit;

import java.util.ArrayList;
import java.util.List;

public final class Player {
	
	/**
	 * Players name to be used throughout the game.
	 */
	private String playerName;
	
	/**
	 * Holds the cards in the players hand..
	 */
	private List<Card> playerCards;

	/**
	 * Create player and set name.
	 * 
	 * @param playerName.
	 */
	public Player(String playerName) {
		this.playerName = playerName;
		playerCards = new ArrayList<>();
	}

	/**
	 * Get the players name.
	 * @return playerName.
	 */
	String getPlayerName() {
		return playerName;
	}

	/**
	 * Get the players cards.
	 * @return playerCards.
	 */
	List<Card> getPlayerCards() {
		return playerCards;
	}
	
	/**
	 * Get the card at the specified index in the players card List
	 * @param cardIndex
	 * @return Card
	 */
	Card getCard(int cardIndex) {
		return playerCards.get(cardIndex);
	}

	/**
	 * Add a new card to the players cards.
	 * @param card to be added to playerCards.
	 */
	void addToPlayerCards(Card card) {
		playerCards.add(card);
	}

	/**
	 * Removes the card at the specified index from the players hand.
	 * @param cardIndex
	 */
	void removeCardFromHand(int cardIndex) {
		playerCards.remove(cardIndex);
	}
	
	/**
	 * Removes the specified card from the players hand.
	 * @param card.
	 */
	void removeCardFromHand(Card card) {
		playerCards.remove(card);
	}

	/**
	 * Get the index in the playerCards List that holds the specified Card.
	 * @param card.
	 * @return cardIndex.
	 */
	int getCardIndex(Card card) {
		int cardIndex = -1;

		for (int i = 0; i < getPlayerCards().size(); i++) {
			if (getCard(i).equals(card)) {
				cardIndex = i;
			}
		}
		return cardIndex;
	}

	/**
	 * Calculates how many points each player finished the game with for ranking.
	 * Trick cards add 20 points and all other cards add their numerical equivalent
	 * 
	 * @return totalPoints
	 */
	int calculatePlayerPoints() {
		int totalPoints = 0;

		for (Card card : getPlayerCards()) {
			totalPoints += card.getCardPoints();	
		}
		return totalPoints;
	}

}
