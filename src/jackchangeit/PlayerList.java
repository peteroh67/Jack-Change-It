package jackchangeit;

import java.util.Collections;
import java.util.List;

public final class PlayerList {

	private List<Player> players;

	private int currentPlayerIndex;

	/**
	 * Constructs a List of players of size numberOfPlayers using names input from
	 * the user
	 * 
	 * @param numberOfPlayers
	 * @param scanner
	 */
	public PlayerList(List<Player> players) {
		this.players = players;
		currentPlayerIndex = 0;
	}

	/**
	 * Get the List of players
	 * 
	 * @return players
	 */
	List<Player> getPlayerList() {
		return players;
	}
	
	/**
	 * Get the current player whose turn it is
	 * 
	 * @return current player
	 */
	Player getCurrentPlayer() {
		return players.get(getCurrentPlayerIndex());
	}

	/**
	 * Get the List index of the current player
	 * 
	 * @return currentPlayerIndex
	 */
	int getCurrentPlayerIndex() {
		return this.currentPlayerIndex;
	}



	/**
	 * Get the next player whose turn it is. If the current player is located in the
	 * last index in the List then the player at index 0 is next
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
		// if current player is the last in list then reset to index 0
		if (getCurrentPlayerIndex() == (getPlayerList().size() - 1)) {
			setCurrentPlayerIndex(0);
		} else {
			// current player is not last in list so increment current player index by 1
			setCurrentPlayerIndex(getCurrentPlayerIndex() + 1);
		}
	}
	
	/**
	 * Set the current player index
	 * 
	 * @param currentPlayerIndex
	 */
	void setCurrentPlayerIndex(int currentPlayerIndex) {
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

	/**
	 * CurrentPlayerIndex will be incremented in the main game loop so it must be
	 * decremented when player order is reversed to maintain correct order.
	 */
	void decrementCurrentPlayer(Player nextPlayer) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(nextPlayer)) {
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
	 * @return nextPlayer after reversal
	 */
	Player getNextPlayerAfterReversal() {
		if (getCurrentPlayerIndex() == 0) {
			return players.get(players.size() - 1);
		} else {
			return players.get(getCurrentPlayerIndex() - 1);
		}
	}
}
