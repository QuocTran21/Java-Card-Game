package mvc.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import model.interfaces.Player;
import mvc.model.Model;

@SuppressWarnings("serial")
public class StatusBarPanel extends JPanel
{
	private JLabel status = new JLabel();
	
	public StatusBarPanel()
	{
		setLayout(new GridLayout(1, 1));
		
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		status.setBorder(blackBorder);
		
		add(status);
	}
	
	public void updateStatusBarPanel(Model model, CardTable cardTable)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				int playersToDeal = model.getGameEngine().getAllPlayers().size();
				for (Player player : model.getGameEngine().getAllPlayers())
				{	
					if (cardTable.getPlayerTable(player.getPlayerId()).hasDealt() || player.getResult() == -1)
					{
						playersToDeal--;
					}	
				}

				if (model.getGameEngine().getAllPlayers().isEmpty())
				{
					status.setText("No players on table");
				}
				else if (playersToDeal != 0) 
				{
					status.setText(String.format("%d remaining to deal.", playersToDeal));
				}
				else 
				{
					status.setText("Dealing to House");
				}
			}
		});
	}
}