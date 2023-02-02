# Jack-Change-It
Play the popular card game in the console for 3 - 6 players.  
This has been developed for learning purposes.

## Contents
1. Game rules.  
2. Basic class diagram.  
3. Classes and their attributes.  
4. Class diagram including attributes and methods.  
5. Overview of each objects responsibilities during gameplay.  
6. Example sequence diagrams for common gameplay. 

## GAME RULES
Players are each dealt 7 starting cards. They take turns, starting clockwise, playing 1 card each. The winner is the first to get rid of all cards.
A starting card is chosen from the deck. The first player must play a card that matches the suit or face of this starting card.
Subsequent players must play a card that matches the suit or face of the new top card, and so on.
If a player does not have a valid card to play, then they must pick up a card from the deck. 
Players can also choose to pick up a card instead of playing a valid card.

###### Trick cards
**2** -- The next player must pick up 2 cards instead of playing a card  
**8** -- The next player misses a turn  
**Q** -- Changes the direction of play  
**J** -- Any Jack can be played at any time. The player chooses to change the playable suit to one of their choice. The next player must then play a card of this suit  
**A of H** -- The next player must pick up 5 cards instead of playing a card, unless they hold the Five of Hearts  
**5 of H** -- Defends against the Ace of Hearts  

###### Ending the game
Once a player runs out of cards. The other players cards are tallied to get total player points and provide a ranking. 
The lower the ranking the better. The player who used all of their cards gets 0 points.
All trick cards get a total of 20 points. All other cards get their face value.

## Class Diagram of the Jack Change It Game showing the composition relationships
![JCIClassDiagram (1)](https://user-images.githubusercontent.com/67584385/216286249-2d699e1c-61ec-42ff-bb35-2e427701a250.png)

## Classes and their Attributes
**JackChangeIt**  
NUMBER_OF_STARTING_CARDS : A constant for the number of cards each player is dealt at the start of the game.
ACE_OF_H_PICK_UP_AMOUNT : A constant for the number of cards the next player is dealt if a player plays the Ace of Hearts.
MAX_NUM_OF_PLAYERS : A constant for the maximum number of players that can play the game.
MIN_NUM_OF_PLAYERS : A constant for the minimum number of players that can play the game.
IO : Responsible for all input and output.
gamestate : Manages the state of the game.

**IO**  
Scanner : Gets input from the players.

**Gamestate**  
deck : Manages the 52 cards involved in the gameplay.
players : Manages the players.
gameover : Boolean flag for if a player runs out of cards or quits the game.
chosenJackSuit : Stores the enum of the chosen suit when a player plays a Jack.

**DeckOfCards**  
burndeck : List of the cards which have already been played.
deckQueue : Queue of the cards due to be dealt next.

**PlayerList**  
players : List of the players in their order of play.
currentPlayerIndex : The List index of the current player whos turn it is to play a card.

**Player**  
playerName : String for the players chosen name.
playerCards : List of the cards currently in the players hand

**Card**
face : The face of the card; Ace, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K.
suit : The face of the card; heart, diamond, club or spade.

## Class Diagram of the Jack Change It Game including all attributes and methods
![Jack Change It](https://user-images.githubusercontent.com/67584385/213870381-3e2f6a3c-c851-46af-bd27-6c039962bac0.png)

## Overview of each objects responsibility during gameplay
When the program runs, the main method in class Main creates an object JackChangeIt and calls method Start();

As a console game, all input and output is achieved through the IO class. Users make their decisions primarily by entering int values following an output of their possible valid inputs. IO has a Scanner as an instance variable which is initialised in the constructor and closed using method closeScanner() which is called by the outputGameOver() method. Many of the methods in IO handle output only. The methods requiring int user input will utilise method getValidUserInput() to validate the input and check for exceptions. The only other input requirement is when the users enter their name during the game setup. This is handled by method inputName() which takes a String input.

Gamestate contains instance variables PlayerList and DeckOfCards to manage the state of the Game. Such changes in game state include changes to each Players held cards, the order of play, the cards remaining in the Deck, the chosen Suit for when a Player plays a Jack, and a boolean to record if the Game has ended. The game ends if a Player runs out of Cards, or chooses to quit instead of playing their turn.

PlayerList contains a List of 3 - 6 players. This list is passed in the Gamestate constructor when it is initialised in JackChangeIt method createPlayers(). PlayerList keeps track of the current player and manages the List. Each Player holds a List of Cards. Cards can be added and removed from this List as the game is played. Each card has a Face and a Suit, which are recorded as enum instance variables.

DeckOfCards contains a Queue of cards yet to be dealt/ played called deckQueue, and a List of already played cards called burnDeck. When a card is dealt it is removed from the deckQueue and added to a Players List of cards. When a player plays a Card it is removed from the Players List of Cards and added to burnDeck. At the start of the game when getting a valid starting card, the card is removed from deckQueue and added to burnDeck. At the start of the game the burnDeck has 52 cards. It is shuffled and the shuffled cards are removed from burndeck and added to deckQueue ready for play. If the deckQueue is empty and an attempt is made to deal a card, the cards in burnDeck are shuffled and added to the deckQueue.


JackChangeIt contains the constants required for gameplay, instance variables IO and Gamestate. JackChangeIt contains numerous methods which will control the input/output using the IO variable, and then use the Gamestate variable to manage the subsequent changes in the state of the game. 
JackChangeIt method playGame() contains the main game loop. Prior to playGame() the players are created, the cards are dealt and a valid starting card is assigned. After playGame() IO is called to output the game over status which includes player points and the winner.

## Example Sequence Diagrams


