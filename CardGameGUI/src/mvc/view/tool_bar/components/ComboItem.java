package mvc.view.tool_bar.components;

import model.interfaces.Player;

public class ComboItem
{
    private String key;
    private Player value;

    public ComboItem(String key, Player value)
    {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString()
    {   	
    	return key;
    }

    public Player getValue()
    {
        return value;
    }
}