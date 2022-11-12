package jackchangeit;

public final class Card {
	
	private CardFace face;
	
	private CardSuit suit;
	
	/**
	 * @param cardFace
	 * @param cardSuit
	 */
	public Card(CardFace cardFace, CardSuit cardSuit) {
		this.face = cardFace;
		this.suit = cardSuit;
	}
	
	CardFace getFace() {
		return face;
	}
	
	CardSuit getSuit() {
		return suit;
	}
	
	boolean isTrickCard() {
		if (getFace().equals(CardFace.EIGHT) || getFace().equals(CardFace.JACK)
				|| getFace().equals(CardFace.QUEEN) || getFace().equals(CardFace.TWO)
				|| ((getFace().equals(CardFace.ACE) && getSuit().equals(CardSuit.HEART)))
				|| ((getFace().equals(CardFace.FIVE) && getSuit().equals(CardSuit.HEART)))) {
			return true;
		} 
		return false;	
	}
	
	int getCardPoints() {
		int points = 0;
		if ((this.getFace().equals(CardFace.ACE) && this.getSuit().equals(CardSuit.HEART))
				|| this.getFace().equals(CardFace.FIVE) && this.getSuit().equals(CardSuit.HEART)) {
			points += 20;
		} else {
			switch (this.getFace()) {
			case ACE:
				points = 1;
				break;
			case THREE:
				points = 3;
				break;
			case FOUR:
				points = 4;
				break;
			case FIVE:
				points = 5;
				break;
			case SIX:
				points = 6;
				break;
			case SEVEN:
				points = 7;
				break;
			case NINE:
				points = 9;
				break;
			case TEN:
				points = 10;
				break;
			case KING:
				points = 10;
				break;
			case TWO:
			case EIGHT:
			case JACK:
			case QUEEN:
				points += 20;
				break;
			default:
				//
			}
		}
		return points;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return getFace() + " of " + getSuit();
	}

}
