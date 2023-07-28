package mvc.model;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import mvc.view.GameEngineCallbackGUI;
import mvc.view.MainFrame;
import view.GameEngineCallbackImpl;

public class Model 
{
	private final GameEngine GAME_ENGINE = new GameEngineImpl();
	private final int DEAL_DELAY = 100;
	private MainFrame mainFrame;
	
	public Model(MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		
		GAME_ENGINE.addPlayer(new SimplePlayer("6", "Foffman", 1000));
		GAME_ENGINE.addPlayer(new SimplePlayer("1", "Ryo Hazuki", 500));
		GAME_ENGINE.addPlayer(new SimplePlayer("3", "Rose", 1000));
		GAME_ENGINE.addGameEngineCallback(new GameEngineCallbackImpl());
		GAME_ENGINE.addGameEngineCallback(new GameEngineCallbackGUI(mainFrame));
	}
	
	public void addPlayer(Player player)
	{
		GAME_ENGINE.addPlayer(player);
		mainFrame.refreshPlayerSummaryPanel();
		mainFrame.getToolBar().updateComboBox(GAME_ENGINE);
		mainFrame.getCardTable().addNewPlayerTable(player);
		mainFrame.updateStatusBarPanel();
		setComboBoxToThisPlayer(player);
		JOptionPane.showMessageDialog(null, String.format("%s Added", player.getPlayerName()), 
													"Player Added", JOptionPane.PLAIN_MESSAGE);
		mainFrame.checkActiveButtons(player);
	}
	
	private void setComboBoxToThisPlayer(Player player)
	{
		int indexOfPlayer = 0;

		for (Player next : GAME_ENGINE.getAllPlayers())
		{
			if(player.getPlayerId() == next.getPlayerId())
			{
				break; //do nothing
			}
			else 
			{
				indexOfPlayer += 1;
			}
		}
		mainFrame.getToolBar().getPlayerComboBox().setSelectedIndex(indexOfPlayer);
	}
	
	public void removePlayerThroughMenu(Player player)
	{
		removePlayer(player);
		mainFrame.getToolBar().getPlayerComboBox().setSelectedIndex(0);

		if (checkIfDealersTurn())
		{
			new Thread()
			{
				@Override
				public void run()
				{
					dealToHouse();
				}
			}.start();
		}
	}
	
	private void removePlayersWithZero()
	{
		for (Player player : GAME_ENGINE.getAllPlayers())
		{
			if (player.getPoints() == 0)
			{
				setComboBoxToThisPlayer(player);
				JOptionPane.showMessageDialog(new JFrame(), String.format("%s Has No Credits Remaining", player.getPlayerName()), 
						"Removing Player", JOptionPane.PLAIN_MESSAGE);
				removePlayer(player);
			}
		}
	}
	
	private void removePlayer(Player player)
	{
		//show empty table to indicate player removed
		mainFrame.getCardTable().addNewPlayerTable(player);
		mainFrame.getCardTable().showSelectedPlayerTable(player.getPlayerId());
		GAME_ENGINE.removePlayer(player);
		mainFrame.refreshPlayerSummaryPanel();
		JOptionPane.showMessageDialog(new JFrame(), String.format("%s Removed", player.getPlayerName()), 
													"Player Removed", JOptionPane.PLAIN_MESSAGE);
		mainFrame.getCardTable().removePlayerTable(player);
		mainFrame.checkActiveButtons(mainFrame.getToolBar().getSelectedPlayer());
		mainFrame.getPlayerSummaryView().getPlayerSummaryPanel().getPlayerSummaryGroup().removePreviousScores(player);
		mainFrame.updateStatusBarPanel();
		mainFrame.getToolBar().updateComboBox(GAME_ENGINE);
	}
	
	public void placeBet(int bet)
	{
		GAME_ENGINE.placeBet(mainFrame.getToolBar().getSelectedPlayer(), bet);
		mainFrame.checkActiveButtons(mainFrame.getToolBar().getSelectedPlayer());
		mainFrame.getPlayerSummaryView().getPlayerSummaryPanel().getPlayerSummaryGroup().updateBets(mainFrame.getToolBar().getSelectedPlayer(), 
																			 						mainFrame.getToolBar().getSelectedPlayer().getBet(), 
																			 						mainFrame.getModel().getGameEngine());
	}
	
	public void zeroBet(int bet)
	{
		int input = JOptionPane.showConfirmDialog(null,"Sit Out Of This Round?", "Zero Bet", JOptionPane.YES_NO_OPTION);

		if (input == JOptionPane.YES_OPTION) 
		{
			mainFrame.getCardTable().addNewPlayerTable(mainFrame.getToolBar().getSelectedPlayer());
			mainFrame.getCardTable().showSelectedPlayerTable(mainFrame.getToolBar().getSelectedPlayer().getPlayerId());
			mainFrame.getToolBar().getSelectedPlayer().setResult(-1);
			mainFrame.getPlayerSummaryView().getPlayerSummaryPanel().getPlayerSummaryGroup().updateScore(mainFrame.getToolBar().getSelectedPlayer(),
																										 -1, GAME_ENGINE, mainFrame);
			mainFrame.getToolBar().getSelectedPlayer().resetBet();
			mainFrame.updateStatusBarPanel();				 
			placeBet(bet);

			if (checkIfDealersTurn())
			{
				new Thread()
				{
					@Override
					public void run()
					{
						dealToHouse();
					}
				}.start();
			}
		}
	}
	
	public void dealToPlayer(Player player)
	{
		//deactivate deal button and menu items
		setIsDealing(true);
		mainFrame.getToolBar().getDealButton().setEnabled(false);
		mainFrame.getMyMenuBar().getDeal().setEnabled(false);
		mainFrame.getToolBar().getBetButton().setEnabled(false);
		mainFrame.getMyMenuBar().getBet().setEnabled(false);
		//add blank table and deal
		mainFrame.getCardTable().addNewPlayerTable(player);
		mainFrame.getCardTable().showSelectedPlayerTable(player.getPlayerId());
		GAME_ENGINE.dealPlayer(player, DEAL_DELAY);
		mainFrame.updateStatusBarPanel();
		//activate deal button and menu items
		setIsDealing(false);
	}
	
	public void dealToHouse()
	{
		JOptionPane.showMessageDialog(null, "Dealing to house", "House Ready", JOptionPane.PLAIN_MESSAGE);
		//set combo box on house and lock it
		mainFrame.getToolBar().getPlayerComboBox().setEnabled(false);
		mainFrame.getToolBar().getPlayerComboBox().setSelectedIndex(GAME_ENGINE.getAllPlayers().size());
		setIsDealing(true);
		//deal cards to house
		mainFrame.getCardTable().addHouseTable();
		GAME_ENGINE.dealHouse(DEAL_DELAY);
		removePlayersWithZero();
		//reactivate combo box for next round
		mainFrame.getToolBar().getPlayerComboBox().setEnabled(true);
		mainFrame.getToolBar().getPlayerComboBox().setSelectedIndex(GAME_ENGINE.getAllPlayers().size());
		setIsDealing(false);
	}
	
	private void setIsDealing(Boolean isDealing)
	{
		mainFrame.setIsDealing(isDealing);
		mainFrame.checkActiveButtons(mainFrame.getToolBar().getSelectedPlayer());
	}
	
	public Boolean checkIfDealersTurn()
	{
		if (GAME_ENGINE.getAllPlayers().isEmpty())
		{
			return false;
		}

		int playersDealt = 0;
		for (Player player : GAME_ENGINE.getAllPlayers())
		{	
			if (mainFrame.getCardTable().getPlayerTable(player.getPlayerId()).hasDealt() || player.getResult() == -1)
			{
				playersDealt++;
			}
		}

		return (playersDealt == GAME_ENGINE.getAllPlayers().size()) ? true : false;
	}
	
	public GameEngine getGameEngine()
	{
		return GAME_ENGINE;
	}
}