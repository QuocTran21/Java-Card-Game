S3827826
Quoc Tran
COSC2391 - Further Programming
Assignment 2
------------------------------

Run the MVCApplication class in the mvc.app package. This opens the MainFrame of the application. 

The game starts with three players. You can change this within the Model class.
You can also add and remove players by clicking on the 'Player Options' drop down menu.

If a zero bet is placed, the players score is set to -1 which is used to show that the player is sitting out of the round.

I spent a lot of time making it possible to deal to multiple players at the same time when the card delay is high. As long as the
players score is set before the delay in the game engine, this should work correctly. Although, I did notice that double cards
occasionally showed up in two different players hands.
I wish I read that this wasn't necessary earlier. I ended up turning off the deal button while another player is dealing. 
In a similar vein, I turned off the ability to add and remove players while dealing. Whilst it worked to add a player,
removing a player caused null pointer exceptions as the players table is removed and the cards cannot be drawn on it.
I originally put try catch blocks around but it seems to make more sense that a player shouldn't be able to leave mid deal.

The Model class creates one instance of the game engine and adds the two GameEngineCallBacks to it. Most of the functionality 
methods are found here including adding and removing players, dealing to players, and dealing to house.

The MainFrame class does most of the updating by calling methods in its child Components.

I opted to have the player panel on the side of the screen. It makes the card table real estate a bit smaller, but I liked the 
look of it there. It can be a little hard to see the scores on the player panel and to keep track of the game, so I tried to the 
display the current phase of the game by adding text when a player has been dealt or is sitting out. 
The status bar is a very simple implementation that simply shows how many players are remaining to deal before it is the 
dealers turn. I thought this was also helpful to keep track of the status of the game.

I created a ComboItem class that is a (String, Player) object. This is how I created the custom player names that is 
displayed in the ComboBox.

For the player's dealt cards, I kept each players table in a hash map with their player id as the key. Whenever the combo box is 
changed, the players table is selected and shown on the screen. Each players table keeps track of the cards suit and value 
that they have been dealt. Each time the players table is selected that has already been dealt, the cards are repainted on each display.

I created a repository for previous scores using a hash map using player id as the key also. This way I could created the 
String message and use it whenever I updated the previous score section on the player summary.

I tried to follow the MVC format as well as I could from my understanding of it and tried to reduce methods into functional parts.


Bugs:
- I didn't manage to make the card table scroll vertically. So some of the cards are cut off if the screens' height is too short.