package mvc.view;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mvc.controller.AddPlayerActionListener;
import mvc.controller.RemovePlayerActionListener;
import mvc.view.player_summary.components.PlayerSummaryPanel;

@SuppressWarnings("serial")
public class PlayerSummaryView extends JPanel
{
	private PlayerSummaryPanel playerSummaryPanel;
	private JButton addButton = new JButton();
	private JButton removeButton = new JButton();
	
	public PlayerSummaryView(MainFrame frame)
	{
		this.playerSummaryPanel = new PlayerSummaryPanel(frame);
		addButton.setText("Add");
		removeButton.setText("Remove");
		addButton.addActionListener(new AddPlayerActionListener(frame));
		removeButton.addActionListener(new RemovePlayerActionListener(frame));
		
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension());
		ButtonGroup group = new ButtonGroup();
		group.add(addButton);
		group.add(removeButton);

	    add(new JScrollPane(playerSummaryPanel, 20, 30), frame.gbc(0,0,1,1,2,11,1));
	    add(addButton, frame.gbc(0,1,0.6,0,1,15,2));
	    add(removeButton, frame.gbc(1,1,0.4,0,1,15,2));
	}
	
	public JButton getAddButton()
	{
		return addButton;
	}
	
	public JButton getRemoveButton()
	{
		return removeButton;
	}
	
	public PlayerSummaryPanel getPlayerSummaryPanel()
	{
		return playerSummaryPanel;
	}
}