package mvc.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import model.interfaces.Player;
import mvc.model.Model;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	private Model model;
	private MenuBar menuBar;
	private ToolBar toolBar;
	private PlayerSummaryView playerSummaryView;
	private CardTable cardTable;
	private StatusBarPanel statusBarPanel;
	
	private Boolean isDealing = false;
	
	public MainFrame()
	{
		super("Further Programming Assignment 2: Casino Style Card Game - Quoc Tran");
		
		model = new Model(this);
		menuBar = new MenuBar(this);
	    toolBar = new ToolBar(this);
	    playerSummaryView = new PlayerSummaryView(this);
	    cardTable = new CardTable(this);
	    statusBarPanel = new StatusBarPanel();
		
		setBounds(120, 120, 1300, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
	    setJMenuBar(menuBar);
	
	    JScrollPane scrollableCardTable = new JScrollPane(cardTable, 20, 31);
	    scrollableCardTable.setPreferredSize(new Dimension());
	    
	    add(toolBar, gbc(0,0,0,0,5,11,2));						                            
	    add(playerSummaryView, gbc(0,1,0.24,0,1,17,1));
	    add(scrollableCardTable, gbc(1,1,0.76,0.2,4,13,1));
	    add(statusBarPanel, gbc(0,2,0,0,5,15,2));
	    
	    updateStatusBarPanel();
	    toolBar.getPlayerComboBox().setSelectedIndex(0);
	    
	    setVisible(true);
	}
	
	public GridBagConstraints gbc(int gridx, int gridy, double weightx, double weighty, int gridwidth, int anchor, int fill)
	{
		GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = gridx;
	    gbc.gridy = gridy;
		gbc.weightx = weightx;
	    gbc.weighty = weighty;
	    gbc.gridwidth = gridwidth;
	    gbc.anchor = anchor;
	    gbc.fill = fill;
	    return gbc;
	}
	
	//used when add player, remove player, deal button pressed, GUI callback at end of round
	public void updateStatusBarPanel()
	{
		statusBarPanel.updateStatusBarPanel(model, cardTable);
	}
	
	//used when adding or removing players
	public void refreshPlayerSummaryPanel()
	{
		playerSummaryView.getPlayerSummaryPanel().remove();
		playerSummaryView.getPlayerSummaryPanel().updatePlayerSummaryGroups(this);
		revalidate();
		repaint();
	}
	
	//used in GUI callback at end of round
	public void setPreviousScores(int houseResult)
	{
		for (Player player : model.getGameEngine().getAllPlayers())
		{
			String winLoss = "";
			int winLossAmount = player.getBet();
			if ((player.getResult() == -1))
			{
				winLoss = "Didn't play";
			}
			else if (player.getResult() == houseResult)
			{
				winLoss = String.format("%s | Draw ", player.getResult());
				winLossAmount = 0;
			}
			else if (player.getResult() > houseResult)
			{
				winLoss = String.format("%s | Win +", player.getResult());
			}
			else
			{
				winLoss = String.format("%s | Loss -", player.getResult());
			}
			playerSummaryView.getPlayerSummaryPanel().getPlayerSummaryGroup().storePrevious(winLoss, winLossAmount, player, model.getGameEngine());
		}
	}
	
	//used in GUI call back  at end of round
	public void resetCreditBetScoreOnPlayerSummary()
	{	
		for (Player player : model.getGameEngine().getAllPlayers())
		{
			playerSummaryView.getPlayerSummaryPanel().getPlayerSummaryGroup().updateCredits(player, player.getPoints(), model.getGameEngine());
			playerSummaryView.getPlayerSummaryPanel().getPlayerSummaryGroup().updateBets(player, 0, model.getGameEngine());
			playerSummaryView.getPlayerSummaryPanel().getPlayerSummaryGroup().updateScore(player, 0, model.getGameEngine(), this);
		}
	}
	
	//used when add player, remove player, bet button pressed, or combo box changed
	public void checkActiveButtons(Player player)
	{
		betActiveCheck(player);
		dealActiveCheck(player);
		addAndRemoveActiveCheck(player);
	}
	
	private void betActiveCheck(Player player)
	{
		if (player.getResult() != 0 || player.getPlayerName().equals("House"))
		{
			menuBar.getBet().setEnabled(false);
			toolBar.getBetButton().setEnabled(false);
			return;
		}
		menuBar.getBet().setEnabled(true);
		toolBar.getBetButton().setEnabled(true);
	}
	
	private void dealActiveCheck(Player player)
	{
		if (isDealing)
		{
			menuBar.getDeal().setEnabled(false);
			toolBar.getDealButton().setEnabled(false);
			return;
		}
		if (player.getResult() != 0 || player.getPlayerName().equals("House"))
		{
			menuBar.getDeal().setEnabled(false);
			toolBar.getDealButton().setEnabled(false);
			return;
		}
		if (player.getBet() != 0)
		{
			menuBar.getDeal().setEnabled(true);
			toolBar.getDealButton().setEnabled(true);
			return;
		}
		menuBar.getDeal().setEnabled(false);
		toolBar.getDealButton().setEnabled(false);
	}
	
	private void addAndRemoveActiveCheck(Player player)
	{
		if (isDealing)
		{
			menuBar.getAddPlayer().setEnabled(false);
			menuBar.getRemovePlayer().setEnabled(false);
			playerSummaryView.getAddButton().setEnabled(false);
			playerSummaryView.getRemoveButton().setEnabled(false);
			return;
		}
		if (player.getPlayerName() == "House")
		{
			menuBar.getAddPlayer().setEnabled(true);
			menuBar.getRemovePlayer().setEnabled(false);
			playerSummaryView.getAddButton().setEnabled(true);
			playerSummaryView.getRemoveButton().setEnabled(false);
			return;
		}

		menuBar.getAddPlayer().setEnabled(true);
		menuBar.getRemovePlayer().setEnabled(true);
		playerSummaryView.getAddButton().setEnabled(true);
		playerSummaryView.getRemoveButton().setEnabled(true);
	}
	
	//set true when dealing and false when finished
	public void setIsDealing(Boolean isDealing)
	{
		this.isDealing = isDealing;
	}
	
	public Model getModel()
	{
		return model;
	}
	
	public MenuBar getMyMenuBar()
	{
		return menuBar;
	}
	
	public ToolBar getToolBar()
	{
		return toolBar;
	}
	
	public PlayerSummaryView getPlayerSummaryView()
	{
		return playerSummaryView;
	}

	public CardTable getCardTable()
	{
		return cardTable;
	}
}