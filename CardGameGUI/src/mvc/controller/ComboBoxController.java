package mvc.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import mvc.view.MainFrame;

public class ComboBoxController implements PropertyChangeListener
{
	private MainFrame frame;
	
	public ComboBoxController(MainFrame frame)
	{
		this.frame = frame;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e) 
	{
		if (e.getPropertyName() == "PLAYER_SELECTED")
		{
			frame.checkActiveButtons(frame.getToolBar().getSelectedPlayer());
			frame.getCardTable().showSelectedPlayerTable(frame.getToolBar().getSelectedPlayer().getPlayerId());
		}	
	}
}