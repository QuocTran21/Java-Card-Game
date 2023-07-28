package mvc.view.player_summary.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;


@SuppressWarnings("serial")
public class PlayerSummary extends JPanel
{	
	private JLabel nameLabel = new JLabel();
	private JLabel creditLabel = new JLabel();
	private JLabel betLabel = new JLabel();
	private JLabel scoreLabel = new JLabel();
	private JLabel previousLabel = new JLabel();
	
	public PlayerSummary(String id, String name, int credit, int bet, int score)
	{
		nameLabel.setText(String.format("%s. %s", id, name));
		nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 13));
		setCreditLabel(credit);
		setBetLabel(bet);
		setScoreLabel(score, false);
		setPreviousLabel("Previous: 0");
		
		setLayout(new GridLayout(5, 1));
		setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		setBackground(new Color(255,255,240));
		
		add(nameLabel);
		add(creditLabel);
		add(betLabel);
		add(scoreLabel);
		add(previousLabel);
	}
	
	public String getID()
	{
		return nameLabel.getText().substring(0, nameLabel.getText().indexOf("."));
	}
	
	public void setCreditLabel(int credit)
	{
		creditLabel.setText(String.format("Credit : %d", credit));
	}
	
	public void setBetLabel(int bet)
	{
		betLabel.setText(String.format("Bet : %d", bet));
	}
	
	public void setScoreLabel(int score, Boolean hasDealt)
	{
		String scoreString = (score == -1) ? "(Sitting out)" : Integer.toString(score);

		if (hasDealt)
			scoreString += " (Cards dealt)";
		scoreLabel.setText(String.format("Score : %s", scoreString));
	}
	
	public void setPreviousLabel(String previousScore)
	{
		previousLabel.setText(previousScore);
	}
}