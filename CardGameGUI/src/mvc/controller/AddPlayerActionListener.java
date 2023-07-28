package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mvc.view.MainFrame;
import mvc.view.tool_bar.components.AddPlayerForm;

public class AddPlayerActionListener implements ActionListener
{
	private MainFrame frame;
	
	public AddPlayerActionListener(MainFrame frame)
	{
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		new AddPlayerForm(frame);
	}
}