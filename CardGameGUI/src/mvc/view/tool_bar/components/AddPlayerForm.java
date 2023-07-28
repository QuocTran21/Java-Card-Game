package mvc.view.tool_bar.components;

import java.awt.GridLayout;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.SimplePlayer;
import model.interfaces.Player;
import mvc.view.MainFrame;

@SuppressWarnings("serial")
public class AddPlayerForm extends JPanel
{	
	public AddPlayerForm(MainFrame frame)
	{
	    JTextField playerNameField = new JTextField();
	    JTextField playerCreditsField = new JTextField();
		
		setLayout(new GridLayout(4, 1));	
		
	    add(new JLabel("Player Name:"));
	    add(playerNameField);
	    add(new JLabel("Starting Credits:"));
	    add(playerCreditsField);
		
		int input = JOptionPane.showConfirmDialog(null, this, "Add Player",JOptionPane.OK_CANCEL_OPTION);
		
		if (input == JOptionPane.OK_OPTION) 
		{
	        try 
	        {
	        	int credit = Integer.parseInt(playerCreditsField.getText());
	        	
	        	Pattern pattern = Pattern.compile("[a-zA-Z]+");
	        	if (!pattern.matcher(playerNameField.getText()).matches())
	        	{
	        		JOptionPane.showMessageDialog(null, "Letters Only In Player Name", "Invalid Player Name", JOptionPane.PLAIN_MESSAGE);
	        		return;
	        	}
	        	
	        	frame.getModel().addPlayer(new SimplePlayer(Integer.toString(uniqueID(frame)), playerNameField.getText(), credit));
	        }
	        catch (NumberFormatException exception)
	        {
	        	JOptionPane.showMessageDialog(null, "Positive Numbers Only In Credit", "Invalid Credit", JOptionPane.PLAIN_MESSAGE);
	        }
	    }
	}
	
	private int uniqueID(MainFrame frame)
	{
		if (frame.getModel().getGameEngine().getAllPlayers().isEmpty())
		{
			return 0;
		}
		
		int playerID = 0;
    	Boolean isUnique = false;
    	while (!isUnique)
    	{
        	for (Player next : frame.getModel().getGameEngine().getAllPlayers())
        	{
        		if (playerID == Integer.parseInt(next.getPlayerId()))
        		{	
        			isUnique = false;
        			playerID++;
        			break;
        		}
        		isUnique = true;
        	}
    	}
    	return playerID;
	}
}