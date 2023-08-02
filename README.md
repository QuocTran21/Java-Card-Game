## First Year University Assignment
By Quoc Tran (s3827826)

Simple card game in Java. Uses Java Swing for GUI.
The CardGame directory contains code that runs the game. Deals to players, calulates scores, resets tables etc.
CardGameGUI contains the code to draw and display the graphics.

### Rules:
- All players take a turn and make a bet.
- Once a bet is placed, the cards can be dealt to that player (half deck 8,9,10,J,Q,K,A).
- Cards are stopped after the player reaches 42 or more.
- Their score is the sum of cards that is just less then or equal to 42.
- When all players have been dealt, the house deals to itself.
- The player's scores are compared to the dealer.
- If that player's score is higher than the dealer, they win double their bet.

### Controls
- Use combo box to switch players
- Use buttons to place bets, deal cards, add or remove players

### Running the game:
- Load the project into an IDE.
- Install JDK into the IDE.
- Make sure both CardGame and CardGameGUI directories are loaded in the same project.
- Run CardGameGUI/src/mvc/app/MVCApplication.java
