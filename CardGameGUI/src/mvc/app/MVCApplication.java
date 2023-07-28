package mvc.app;

import javax.swing.SwingUtilities;

import mvc.view.MainFrame;

public class MVCApplication
{
	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new MainFrame();
			}
		});
	}
}