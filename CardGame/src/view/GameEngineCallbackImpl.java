package view;

import java.util.Collection;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

/**
 * 
 * Skeleton/Partial example implementation of GameEngineCallback showing Java logging behaviour
 * 
 * @author Caspar Ryan
 * @see view.interfaces.GameEngineCallback
 * 
 */
public class GameEngineCallbackImpl implements GameEngineCallback
{
   public static final Logger logger = Logger.getLogger(GameEngineCallbackImpl.class.getName());

   // utility method to set output level of logging handlers
   public static Logger setAllHandlers(Level level, Logger logger, boolean recursive)
   {
      // end recursion?
      if (logger != null)
      {
         //logger.setLevel(level.INFO);
         for (Handler handler : logger.getHandlers())
            handler.setLevel(level);
         // recursion
         setAllHandlers(level, logger.getParent(), recursive);
      }
      return logger;
   }

   public GameEngineCallbackImpl()
   {
      // NOTE can also set the console to FINE in %JRE_HOME%\lib\logging.properties
      setAllHandlers(Level.FINE, logger, true);
   }

   public void nextCard(Player player, PlayingCard card, GameEngine engine)
   {
	   logger.log(Level.INFO, String.format("INFO: Card dealt to %s .. %s", player.getPlayerName(), card.toString()));
   }

   public void bustCard(Player player, PlayingCard card, GameEngine engine) 
   {
	   logger.log(Level.INFO, String.format("INFO: Card dealt to %s .. %s ... YOU BUSTED!", player.getPlayerName(), card.toString()));
   }
	
   public void result(Player player, int result, GameEngine engine)
   {
	   logger.log(Level.INFO, String.format("INFO: %s, final result=%d", player.getPlayerName(), player.getResult()));
   }
	
   public void nextHouseCard(PlayingCard card, GameEngine engine) 
   {
	   logger.log(Level.INFO, String.format("INFO: Card dealt to House .. %s", card.toString()));
   }
	
   public void houseBustCard(PlayingCard card, GameEngine engine) 
   {
	   logger.log(Level.INFO, String.format("INFO: Card dealt to House .. %s ... HOUSE BUSTED!", card.toString()));
   }
	
   public void houseResult(int result, GameEngine engine) 
   {
	   String finalPlayerResult = "";
	   Collection<Player> players = engine.getAllPlayers();
	   
	   for (Player next : players)
	   {
		   finalPlayerResult += next.toString() + "\n";
	   }
	   
	   logger.log(Level.INFO, String.format("INFO: House, final result=%d", result));
	   logger.log(Level.INFO, String.format("INFO: Final Player Results\n%s", finalPlayerResult));
   }
}