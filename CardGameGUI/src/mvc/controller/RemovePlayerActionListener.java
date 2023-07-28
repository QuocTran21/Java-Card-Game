package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import mvc.view.MainFrame;

public class RemovePlayerActionListener implements ActionListener
{
	MainFrame frame;
	
	public RemovePlayerActionListener(MainFrame frame)
	{
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int result = JOptionPane.showConfirmDialog(null, String.format("Remove %s?", 
				   frame.getToolBar().getSelectedPlayer().getPlayerName()),"Remove Player", JOptionPane.YES_NO_OPTION);
		
		if(result == JOptionPane.YES_OPTION) 
		{
			frame.getModel().removePlayerThroughMenu(frame.getToolBar().getSelectedPlayer());
		} 
	}
}