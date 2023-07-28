package mvc.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import mvc.view.MainFrame;

public class DealController implements PropertyChangeListener
{
	private MainFrame frame;
	
	public DealController(MainFrame frame)
	{
		this.frame = frame;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e) 
	{
		if (e.getPropertyName() == "DEAL_PRESSED")
		{
			new Thread()
			{
				@Override
				public void run()
				{
					frame.getModel().dealToPlayer(frame.getToolBar().getSelectedPlayer());

					if(frame.getModel().checkIfDealersTurn())
					{
						frame.getModel().dealToHouse();
					}
				}
			}.start();
		}
	}
}