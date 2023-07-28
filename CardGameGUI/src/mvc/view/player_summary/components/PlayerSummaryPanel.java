package mvc.view.player_summary.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import mvc.view.MainFrame;

@SuppressWarnings("serial")
public class PlayerSummaryPanel extends JPanel
{
	private PreviousScoresRepo previousScores = new PreviousScoresRepo();
	
	//frame the summary in another frame to add scroll
	public PlayerSummaryPanel(MainFrame frame)
	{
		setLayout(new GridBagLayout());
		setBackground(new Color(176,196,222));
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Player Summary", TitledBorder.LEFT, TitledBorder.TOP));
		
		updatePlayerSummaryGroups(frame);
	}
	
	public void updatePlayerSummaryGroups(MainFrame frame)
	{	
		GridBagConstraints gbc = new GridBagConstraints();
	    gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		add(new PlayerSummaryGroup(frame, previousScores), gbc);
	}
	
	public PlayerSummaryGroup getPlayerSummaryGroup()
	{
		return (PlayerSummaryGroup) getComponent(0);
	}
	
	public void remove()
	{
		remove(0);
	}
}