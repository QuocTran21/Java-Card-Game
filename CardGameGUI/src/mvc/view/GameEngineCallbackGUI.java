package mvc.view;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

public class GameEngineCallbackGUI implements GameEngineCallback
{
	MainFrame mainFrame;

	public GameEngineCallbackGUI(MainFrame mainFrame) 
	{
		this.mainFrame = mainFrame;
	}

	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) 
	{
		dealCard(player.getPlayerId(), card, false);
		mainFrame.getPlayerSummaryView().getPlayerSummaryPanel().getPlayerSummaryGroup().updateScore(player, player.getResult(), engine, mainFrame);
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) 
	{
		dealCard(player.getPlayerId(), card, true);
	}

	@Override
	public void result(Player player, int result, GameEngine engine) 
	{
		mainFrame.getCardTable().getPlayerTable(player.getPlayerId()).setHasDealt(true);
		mainFrame.getPlayerSummaryView().getPlayerSummaryPanel().getPlayerSummaryGroup().updateScore(player, player.getResult(), engine, mainFrame);
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) 
	{
		dealCard("House", card, false);
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) 
	{
		dealCard("House", card, true);
	}

	@Override
	public void houseResult(int result, GameEngine engine) 
	{
		mainFrame.setPreviousScores(result);
		
		int userInput = JOptionPane.showConfirmDialog(null, String.format("House scored %d\nPlay Again?", result),
													  "House Results", JOptionPane.YES_NO_OPTION);
		if(userInput == JOptionPane.NO_OPTION) 
		{
			System.exit(0);
		} 
		resetHasDealtToFalse(engine);
		mainFrame.resetCreditBetScoreOnPlayerSummary();
		mainFrame.updateStatusBarPanel();
	}
	
	private void dealCard(String playerID, PlayingCard card, Boolean isBusted)
	{	
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				mainFrame.getCardTable().getPlayerTable(playerID).dealCardsOnTable(card.getSuit().toString(), card.getValue().toString(), isBusted);
			}
		});
	}
	
	private void resetHasDealtToFalse(GameEngine engine)
	{
		for (Player player : engine.getAllPlayers())
		{
			mainFrame.getCardTable().getPlayerTable(player.getPlayerId()).setHasDealt(false);
		}
	}
}