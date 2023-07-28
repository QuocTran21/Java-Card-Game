package mvc.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;

import mvc.view.MainFrame;

public class BetController implements PropertyChangeListener
{
	private MainFrame frame;
	
	public BetController(MainFrame frame)
	{
		this.frame = frame;
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) 
	{		
		if (e.getPropertyName() == "BET_PRESSED")
		{  
			String userInput = JOptionPane.showInputDialog(null, String.format("Enter Bet For %s: ", 
															frame.getToolBar().getSelectedPlayer().getPlayerName()),
															"Place Bet", JOptionPane.INFORMATION_MESSAGE);  
			
			if (userInput == null) {return;} //cancel is pressed
			
			int bet = 0;
			try
			{
				 bet = Integer.parseInt(userInput);
				 
				 if (bet < 0 || bet > frame.getToolBar().getSelectedPlayer().getPoints())
				 {
					 JOptionPane.showMessageDialog(null, String.format("Please Enter A Bet From 0-%d",
							 					frame.getToolBar().getSelectedPlayer().getPoints()), "Out Of Range", JOptionPane.PLAIN_MESSAGE);
					 return;
				 }
				 if (bet == 0)
				 {
					 frame.getModel().zeroBet(bet); //player sits out of round
				 }
				 frame.getModel().placeBet(bet);
			}
			catch(NumberFormatException exception)
			{
				JOptionPane.showMessageDialog(null, "Please Enter Numbers Only", "Invalid Bet", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
}