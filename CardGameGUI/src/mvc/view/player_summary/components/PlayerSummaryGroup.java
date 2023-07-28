package mvc.view.player_summary.components;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import mvc.view.MainFrame;

@SuppressWarnings("serial")
public class PlayerSummaryGroup extends JPanel
{
	private HashMap<String, String> previousScores;
	
	public PlayerSummaryGroup(MainFrame frame, PreviousScoresRepo previousScores)
	{
		this.previousScores = previousScores.getPreviousScores();
		GridLayout gridLayout = new GridLayout(frame.getModel().getGameEngine().getAllPlayers().size(), 1);
	    gridLayout.setVgap(5);
	    gridLayout.setHgap(5);
	    
	    setLayout(gridLayout);
	    setBackground(new Color(176,196,222));

		for (Player player : frame.getModel().getGameEngine().getAllPlayers()) 
		{	
			//create a summary for all players stacked vertically
			add(new PlayerSummary(player.getPlayerId(), player.getPlayerName(), 
								  player.getPoints(), player.getBet(), player.getResult()));
		}
		
		for (Player player : frame.getModel().getGameEngine().getAllPlayers()) 
		{	
			setPrevious(player, frame.getModel().getGameEngine());
		}
	}
	
	//Iterate through each player summary. 
	//If index of the player and summary match, perform the update
	public void updateCredits(Player selectedPlayer, int credit, GameEngine engine)
	{
		for (int i = 0; i < engine.getAllPlayers().size(); i++) 
		{	
			PlayerSummary playerSummary = (PlayerSummary) getComponent(i);
			
			if(selectedPlayer.getPlayerId().equals(playerSummary.getID()))
			{
				playerSummary.setCreditLabel(credit);
			}
		}
	}
	
	public void updateBets(Player selectedPlayer, int bet, GameEngine engine)
	{
		for (int i = 0; i < engine.getAllPlayers().size(); i++) 
		{	
			PlayerSummary playerSummary = (PlayerSummary) getComponent(i);
			
			if(selectedPlayer.getPlayerId().equals(playerSummary.getID()))
			{
				playerSummary.setBetLabel(bet);
			}
		}
	}
	
	public void updateScore(Player selectedPlayer, int score, GameEngine engine, MainFrame frame)
	{
		for (int i = 0; i < engine.getAllPlayers().size(); i++) 
		{	
			PlayerSummary playerSummary = (PlayerSummary) getComponent(i);
			
			if(selectedPlayer.getPlayerId().equals(playerSummary.getID()))
			{
				playerSummary.setScoreLabel(score, frame.getCardTable().getPlayerTable(selectedPlayer.getPlayerId()).hasDealt());
			}
		}
	}
	
	public void setPrevious(Player selectedPlayer, GameEngine engine)
	{
		for (int i = 0; i < engine.getAllPlayers().size(); i++) 
		{	
			PlayerSummary playerSummary = (PlayerSummary) getComponent(i);
			
			if(selectedPlayer.getPlayerId().equals(playerSummary.getID()))
			{
				if(previousScores.containsKey(selectedPlayer.getPlayerId()))	
				{
					playerSummary.setPreviousLabel(previousScores.get(selectedPlayer.getPlayerId()));
				}
			}
		}
	}
	
	public void storePrevious(String winLoss, int amount, Player selectedPlayer, GameEngine engine)
	{
		String amountString = (amount == 0) ? "" : Integer.toString(amount);
		
		String previousScore = String.format("Previous: %s%s", winLoss, amountString);
		previousScores.put(selectedPlayer.getPlayerId(), previousScore);
		
		setPrevious(selectedPlayer, engine);
	}
	
	public void removePreviousScores(Player player)
	{
		previousScores.remove(player.getPlayerId());
	}
}