package model;

import model.interfaces.Player;


public class SimplePlayer implements Player
{
	private String ID;
	private String playerName;
	private int points;
	private int bet;
	private int result = 0;
	
	public SimplePlayer(String id, String playerName, int initialPoints)
	{
		if (id == null)
		{
			throw new IllegalArgumentException("null args.");
		}
		this.ID = id;
		this.playerName = playerName;
		this.points = initialPoints;
	}
	
	public String getPlayerName()
	{
		return playerName;
	}

	public void setPlayerName(String playerName) 
	{
		this.playerName = playerName;
	}

	public int getPoints() 
	{
		return points;
	}

	public void setPoints(int points) 
	{
		this.points += points;
	}

	public String getPlayerId() 
	{
		return ID;
	}

	public boolean setBet(int bet) 
	{
		if (bet > 0 && bet <= points)
		{
			this.bet = bet;
			return true;
		}
		return false;
	}

	public int getBet() 
	{
		return bet;
	}

	public void resetBet() 
	{
		bet = 0;	
	}

	public int getResult() 
	{
		return result;
	}

	public void setResult(int result) 
	{
		this.result += result;
	}

	public boolean equals(Player player) 
	{
		return player.getPlayerId().equals(ID);
	}

	@Override
	public boolean equals(Object player)
	{
		if (player == null)
		{
			return false;
		}
		if (player instanceof Player)
		{
			return ((Player) player).equals(this);
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return ID.hashCode();
	}
	
	@Override
	public int compareTo(Player player)
	{
		if (player.getPlayerId().compareTo(ID) < 0)
		{
			return -1;   
		}
		if (player.getPlayerId().compareTo(ID) > 0) 
		{
			return 1;
		}
		else return 0;
	}
	
	@Override
	public String toString() 
	{
		return String.format("Player: id=%s, name=%s, bet=%d, points=%d, RESULT .. %d", ID, playerName, bet, points, result);
	}
}