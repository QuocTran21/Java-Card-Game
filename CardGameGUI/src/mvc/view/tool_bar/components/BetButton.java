package mvc.view.tool_bar.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class BetButton extends JButton implements ActionListener
{
	public BetButton()
	{
		setText("Bet");
		setAlignmentX(CENTER_ALIGNMENT);
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if (source == this) 
		{     
			firePropertyChange("BET_PRESSED", null, null);			
		}
	}
}