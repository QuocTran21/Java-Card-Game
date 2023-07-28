package mvc.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.interfaces.Player;
import mvc.view.card_table.components.PlayerCardTable;

@SuppressWarnings("serial")
public class CardTable extends JPanel
{
	private HashMap<String, PlayerCardTable> playerTables = new HashMap<String, PlayerCardTable>();
	
	public CardTable(MainFrame frame)
	{
		setLayout(new GridLayout(1,1));
		setBackground(new Color(85,107,47));
		
		for (Player player : frame.getModel().getGameEngine().getAllPlayers())
		{
			playerTables.put(player.getPlayerId(), new PlayerCardTable());
		}
		playerTables.put("House", new PlayerCardTable());
		add(new PlayerCardTable());
	}
	
	public void addNewPlayerTable(Player player)
	{
		playerTables.put(player.getPlayerId(), new PlayerCardTable());
	}
	
	public void removePlayerTable(Player player)
	{
		playerTables.remove(player.getPlayerId());
	}
	
	public void addHouseTable()
	{
		playerTables.put("House", new PlayerCardTable());
		showSelectedPlayerTable("House");
	}
	
	public void showSelectedPlayerTable(String id)
	{	
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{	
				remove(0);
				add(playerTables.get(id));
				revalidate();
				repaint();
			}
		});
	}
	
	public PlayerCardTable getPlayerTable(String index)
	{
		return playerTables.get(index);
	}
}