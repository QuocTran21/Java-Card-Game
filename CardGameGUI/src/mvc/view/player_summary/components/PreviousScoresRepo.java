package mvc.view.player_summary.components;

import java.util.HashMap;

public class PreviousScoresRepo 
{
	private HashMap<String, String> previousScores = new HashMap<String, String>();
	
	public HashMap<String, String> getPreviousScores()
	{
		return previousScores;
	}
	
	public void storePreviousScores(String playerID, String previousScore)
	{
		previousScores.put(playerID, previousScore);
	}
	
	public void removePreviousScores(String playerID)
	{
		previousScores.remove(playerID);
	}
}