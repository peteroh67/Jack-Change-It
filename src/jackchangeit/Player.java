package jackchangeit;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for each player in a game of Jack Change It. Each player
 * has a name and a hand of cards.
 * 
 * @author Peter O'Hare
 *
 */
public final class Player {

	private String playerName;
	private List<Card> playerCards;

	public Player(String playerName) {
		this.playerName = playerName;
		playerCards = new ArrayList<>();
	}

	String getPlayerName() {
		return playerName;
	}

	/*
	 * returns a copy of the playerCards list so that changes must be made through
	 * class methods -- encapsulation
	 */
	List<Card> getPlayerCards() {
		return new ArrayList<>(playerCards);
	}

	Card getCard(int cardIndex) {
		return playerCards.get(cardIndex);
	}

	void addToPlayerCards(Card card) {
		playerCards.add(card);
	}

	void removeCardFromHand(int cardIndex) {
		playerCards.remove(cardIndex);
	}

	void removeCardFromHand(Card card) {
		playerCards.remove(card);
	}

	/**
	 * If a player holds the card argument then its index in the card list is
	 * returned. If the card is not present -1 is returned
	 * 
	 * @param card
	 * @return
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
