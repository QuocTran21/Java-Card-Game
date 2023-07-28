package mvc.view;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JToolBar;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import mvc.controller.BetController;
import mvc.controller.ComboBoxController;
import mvc.controller.DealController;
import mvc.view.tool_bar.components.BetButton;
import mvc.view.tool_bar.components.ComboItem;
import mvc.view.tool_bar.components.DealButton;
import mvc.view.tool_bar.components.PlayerComboBox;

@SuppressWarnings("serial")
public class ToolBar extends JToolBar
{
	private DealButton dealButton;
	private BetButton betButton;
	private PlayerComboBox playerComboBox;
	private MainFrame frame;
	
	public ToolBar(MainFrame frame)
	{
		setBackground(new Color(222,184,135));
		setLayout(new GridBagLayout());
		
		this.frame = frame;
		
		dealButton = new DealButton();
		betButton = new BetButton();
		playerComboBox = new PlayerComboBox(frame.getModel().getGameEngine());
		
		dealButton.addPropertyChangeListener(new DealController(frame));
		betButton.addPropertyChangeListener(new BetController(frame));
		playerComboBox.addPropertyChangeListener(new ComboBoxController(frame));
	
		ButtonGroup group = new ButtonGroup();
		group.add(dealButton);
		group.add(betButton);
		
		add(dealButton, frame.gbc(0, 0, 0.138, 0, 1, 17, 2));
		add(betButton, frame.gbc(1, 0, 0.138, 0, 1, 17, 2));
		add(playerComboBox, frame.gbc(2, 0, 0.96, 0, 8, 13, 2));
		
		setFloatable(false);
	}
	
	public void updateComboBox(GameEngine engine)
	{
		remove(playerComboBox);
		playerComboBox = new PlayerComboBox(engine);
		playerComboBox.addPropertyChangeListener(new ComboBoxController(frame));
		add(playerComboBox, frame.gbc(2, 0, 0.9, 0, 8, 13, 2));
		revalidate();
		repaint();
	}
	
	public DealButton getDealButton()
	{
		return dealButton;
	}
	
	public BetButton getBetButton()
	{
		return betButton;
	}
	
	public Player getSelectedPlayer()
	{
		ComboItem item = (ComboItem) playerComboBox.getSelectedItem();
		return item.getValue();
	}
	
	public PlayerComboBox getPlayerComboBox()
	{
		return playerComboBox;
	}
}