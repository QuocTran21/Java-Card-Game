package mvc.view.tool_bar.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class DealButton extends JButton implements ActionListener
{	
	public DealButton()
	{
		setText("Deal");
		setEnabled(false);
		setAlignmentX(CENTER_ALIGNMENT);
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		if (source == this) 
		{
			firePropertyChange("DEAL_PRESSED", null, null);			
		}
	}
}