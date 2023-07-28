package mvc.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import mvc.controller.AddPlayerActionListener;
import mvc.controller.BetController;
import mvc.controller.DealController;
import mvc.controller.RemovePlayerActionListener;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar implements ActionListener
{
	private JMenu menu = new JMenu("Player Options");
	private JMenuItem bet = new JMenuItem("Bet");
	private JMenuItem deal = new JMenuItem("Deal");
	private JMenuItem addPlayer = new JMenuItem("Add Player");
	private JMenuItem removePlayer = new JMenuItem("Remove Player");
	private JMenuItem exit = new JMenuItem("Exit");
	
	public MenuBar(MainFrame frame)
	{
		bet.addActionListener(this);
		deal.addActionListener(this);
		addPlayer.addActionListener(new AddPlayerActionListener(frame));
		removePlayer.addActionListener( new RemovePlayerActionListener(frame));
		exit.addActionListener(this);
		
		addPropertyChangeListener(new BetController(frame));
		addPropertyChangeListener(new DealController(frame));
		
		menu.add(bet);
		menu.add(deal);
		menu.addSeparator();
		menu.add(addPlayer);
		menu.add(removePlayer);
		menu.addSeparator();
		menu.add(exit);
		add(menu);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		if (source == bet) 
		{     
			firePropertyChange("BET_PRESSED", null, null);			
		}
		if (source == deal) 
		{     
			firePropertyChange("DEAL_PRESSED", null, null);			
		}
		if (source == exit) 
		{     
			int userInput = JOptionPane.showConfirmDialog(null, "Would You Like To Quit?", "Exit Game", JOptionPane.YES_NO_OPTION);
			if(userInput == JOptionPane.YES_OPTION) 
			{
				System.exit(0);
			} 
		}
	}
	
	public JMenuItem getBet()
	{
		return bet;
	}
	public JMenuItem getDeal()
	{
		return deal;
	}
	public JMenuItem getAddPlayer()
	{
		return addPlayer;
	}
	public JMenuItem getRemovePlayer()
	{
		return removePlayer;
	}
}