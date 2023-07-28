# Java-Card-Game
## First Year University Assignment

Simple card game in Java. Uses Java Swing for GUI.
CardGame contains the code that runs the game. Deals to players, calulates scores, resets tables etc.
CardGameGUI contains the code to draw and display the graphics and panels.

### Rules:
All players take a turn and make a bet.
Once a bet is place, the cards can be dealt to that player.
Cards are stopped after the player reachers 42 or above.
There score is the sum of cards less then or equal to 42.

When all players have been dealt, the house deals to itself.
The players scores are compare to the dealer.
If the players score is higher than the dealer, they win double their bet.


### Running the game:
Load the project into an IDE. Install JDK into the IDE.
Make sure both CardGame and CardGameGUI directories are loaded in the same project.
Run CardGameGUI/src/mvc/app/MVCApplication.java
