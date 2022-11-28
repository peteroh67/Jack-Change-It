package jackchangeit;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author Peter O'Hare
 *
 */
public final class DeckOfCards {

	/**
	 * Holds the previously played cards. The last card in the list will be the last
	 * card played
	 */
	private ArrayList<Card> burnDeck;

	/**
	 * Holds the queue of cards to be dealt representing the shuffled deck
	 */
	private Queue<Card> deckQueue;

	/**
	 * 52 cards are constructed and added to the List burnDeck. They have not yet
	 * been shuffled so are not yet suitable for play
	 */
	public DeckOfCards() {
		burnDeck = new ArrayList<>();
		deckQueue = new LinkedList<>();

		for (CardSuit suit : CardSuit.values()) {
			for (CardFace face : CardFace.values()) {
				Card card = new Card(face, suit);
				burnDeck.add(card);
			}
		}
	}

	/**
	 * Gets the list of played cards in burnDeck
	 * 
	 * @return burnDeck
	 */
	private ArrayList<Card> getBurnDeck() {
		return new ArrayList<>(burnDeck);
	}

	/**
	 * The burnDeck is shuffled. The cards are added to a queue and the burnDeck is
	 * cleared
	 */
	void shuffleDeck() {

		SecureRandom randomNumbers = new SecureRandom();

		// for each card, pick another random Card(0-51) then swap them
		for (int firstCard = 0; firstCard < burnDeck.size(); firstCard++) {
			int secondCard = randomNumbers.nextInt(burnDeck.size());

			Collections.swap(burnDeck, firstCard, secondCard);
		}

		// next add the shuffled deck to the queue ready for play
		for (int i = 0; i < burnDeck.size(); i++) {
			deckQueue.add(burnDeck.get(i));
		}
		// All cards now in queue so empty the burnDeck
		burnDeck.clear();

	}

	/**
	 * As cards are played they are polled from the queue and added back to the burn
	 * deck The are also added to the burn deck at the start of the game when
	 * getting a starting card
	 * 
	 * @param card
	 */
	void addCardToBurnDeck(Card card) {
		burnDeck.add(card);
	}

	/**
	 * Deals one card. Checks if any cards remain to be dealt. If the deck is empty
	 * then all 52 cards are in the burn pile so this is shuffled and the queue is
	 * re-populated. The top burn card must be removed first so it is not shuffled
	 * into the queue, then it is re-added to the burn deck as the only card. A
	 * recursive method call will then deal the next card.
	 */
	Card dealCard() {
		if (!deckQueue.isEmpty()) {
			return deckQueue.poll(); //
		} else {
			Card c = burnDeck.remove(getBurnDeck().size() - 1);
			shuffleDeck();
			addCardToBurnDeck(c);
			return dealCard();
		}
	}

	/**
	 * Gets the card at the end of the list of burned cards. This represents the top
	 * of the burn pile.
	 * 
	 * @return
	 */
	Card getTopBurnCard() {
		return getBurnDeck().get(getBurnDeck().size() - 1);
	}

}
