package mvc.view.tool_bar.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class PlayerComboBox extends JComboBox<Object> implements ActionListener
{	
	public PlayerComboBox(GameEngine engine)
	{
		addActionListener(this);
		
		for(Player player : engine.getAllPlayers())
		{
			addItem(new ComboItem(String.format("%s. %s", player.getPlayerId(), player.getPlayerName()), player));
		}
		addItem(new ComboItem("House", new SimplePlayer("House", "House", 21)));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if (source == this) 
		{  
			firePropertyChange("PLAYER_SELECTED", "old", getActionCommand());			
		}
	}
}