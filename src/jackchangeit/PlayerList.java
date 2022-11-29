package jackchangeit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Player List holds a list of players and a pointer for the current player in a
 * game of Jack Change It. There are methods to manipulate the list when trick
 * cards are played such as Queen reverses order.
 * 
 * @author Peter O'Hare
 *
 */
public final class PlayerList {

	private List<Player> players;
	private int currentPlayerIndex;

	/**
	 * Copies the list argument for encapsulation
	 * @param players
	 */
	public PlayerList(List<Player> players) {
		this.players = new ArrayList<>(players);
		currentPlayerIndex = 0;
	}

	List<Player> getPlayerList() {
		return new ArrayList<>(this.players);
	}

	Player getCurrentPlayer() {
		return players.get(getCurrentPlayerIndex());
	}

	/**
	 * Get the player whose turn it is next. Used when some trick cards are played
	 * If the current player is located in the last index in the List then the
	 * player at index 0 is next
	 * 
	 * @return next player
	 */
	Player getNextPlayer() {
		if (getCurrentPlayerIndex() == (getPlayerList().size() - 1)) {
			return getPlayerList().get(0);
		} else {
			return getPlayerList().get(getCurrentPlayerIndex() + 1);
		}
	}

	/**
	 * Increments the current player index. If the current player is located in the
	 * last index in the List then the currentPlayerIndex is set to index 0.
	 */
	void incrementCurrentPlayer() {
		if (getCurrentPlayerIndex() == (getPlayerList().size() - 1)) {
			setCurrentPlayerIndex(0);
		} else {
			setCurrentPlayerIndex(getCurrentPlayerIndex() + 1);
		}
	}

	private void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}

	/**
	 * The player order is reversed when a player plays a Queen.
	 */
	void reversePlayerOrder() {

		Player nextPlayer = getNextPlayerAfterReversal();
		Collections.reverse(players);
		decrementCurrentPlayer(nextPlayer);

	}
	
	private int getCurrentPlayerIndex() {
		return this.currentPlayerIndex;
	}

	/**
	 * CurrentPlayerIndex will be incremented in the main game loop so it must be
	 * decremented when player order is reversed to maintain correct order.
	 */
	private void decrementCurrentPlayer(Player nextPlayer) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(nextPlayer/*getNextPlayer()*/)) {
				if (i == 0) {
					setCurrentPlayerIndex(players.size() - 1);
				} else {
					setCurrentPlayerIndex(i - 1);
				}
			}
		}
	}

	/**
	 * Get the player whose turn it will be when player order is reversed
	 * 
	 * @return nextPlayer after reversal
	 */
	private Player getNextPlayerAfterReversal() {
		if (getCurrentPlayerIndex() == 0) {
			return players.get(players.size() - 1);
		} else {
			return players.get(getCurrentPlayerIndex() - 1);
		}
	}
}
