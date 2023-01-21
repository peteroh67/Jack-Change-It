# Jack-Change-It
Play the popular card game in the console for 3 - 6 players
This has been developed for learning purposes.

***GAME RULES***
Players are each dealt 7 starting cards. They take turns, starting clockwise, playing 1 card each. The winner is the first to get rid of all cards.
A starting card is chosen from the deck. The first player must play a card that matches the suit or face of this starting card.
Subsequent players must play a card that matches the suit or face of the new top card, and so on.
If a player does not have a valid card to play, then they must pick up a card from the deck. 
Players can also choose to pick up a card instead of playing a valid card.

***Trick cards***
2 -- The next player must pick up 2 cards instead of playing a card |
8 -- The next player misses a turn |
Q -- Changes the direction of play |
J -- Any Jack can be played at any time. The player chooses to change the playable suit to one of their choice. The next player must then play a card of this suit |
A of H -- The next player must pick up 5 cards instead of playing a card, unless they hold the Five of Hearts |
5 of H -- Defends against the Ace of Hearts |

Once a player runs out of cards. The other players cards are tallied to get total player points and provide a ranking. 
The lower the ranking the better and the player who ran out of cards gets 0 points.
All trick cards get a total of 20 points. All other cards get their face value.

![Jack Change It](https://user-images.githubusercontent.com/67584385/213870381-3e2f6a3c-c851-46af-bd27-6c039962bac0.png)

As a console game, all input and output is achieved through the IO class. Users make their decisions primarily by entering int values following an output of their possible valid inputs. IO has a Scanner as an instance variable which is initialised in the constructor and closed using method closeScanner() which is called by the outputGameOver() method. Many of the methods in IO handle output only. The methods requiring int user input will utilise method getValidUserInput() to validate the input and check for exceptions. The only other input requirement is when the users enter their name during the game setup. This is handled by method inputName() which takes a String input.

Gamestate contains instance variables PlayerList and DeckOfCards to manage the state of the Game. 

PlayerList contains a List of 3 - 6 players. This list is passed in the constructor when Gamestate is initialised in JackChangeIt method createPlayers().

JackChangeIt contains the constants required for gameplay, instance variables IO and Gamestate. JackChangeIt contains numerous methods which will control the input/output using the IO variable, and then use the Gamestate variable to manage the subsequent changes in the state of the game. 

JackChangeIt method playGame() contains the main game loop. Prior to playGame() the players are created, the cards are dealt and a valid starting card is assigned. After playGame() IO is called to output the game over status which includes player points and the winner.



