package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;
import view.interfaces.GameEngineCallback;


public class GameEngineImpl implements GameEngine
{
	private HashMap<String, Player> playersInGame = new HashMap<String, Player>();
	private Deque<GameEngineCallback> gecbCollection = new ArrayDeque<GameEngineCallback>();
	private Deque<PlayingCard> deck;
	
	public GameEngineImpl()
	{	
		deck = getShuffledHalfDeck();
	}
	
	public void dealPlayer(Player player, int delay) throws IllegalArgumentException
	{
		delayCheck(delay);
		
		while (player.getResult() + deck.getFirst().getScore() <= BUST_LEVEL)
		{		
			player.setResult(deck.getFirst().getScore());
			callBack(gecbCollection, "NEXT_CARD", player, null);
			pause(delay);
			
			deck = removeCardAndRefillEmpty(deck);
			
		}
		if (player.getResult() == BUST_LEVEL)
		{
			callBack(gecbCollection, "RESULT", player, null);
			
			deck = removeCardAndRefillEmpty(deck);
		}
		else //Player busted.
		{
			callBack(gecbCollection, "BUST", player, null);
			pause(delay);
			callBack(gecbCollection, "RESULT", player, null);
			
			deck = removeCardAndRefillEmpty(deck);
		}
	}
	
	public void dealHouse(int delay) throws IllegalArgumentException
	{
		delayCheck(delay);
		
		int houseResult = 0;
			
		while (houseResult + deck.getFirst().getScore() <= BUST_LEVEL)
		{	
			houseResult += deck.getFirst().getScore();
			callBack(gecbCollection, "HOUSE_NEXT", null, null);
			pause(delay);
			
			deck = removeCardAndRefillEmpty(deck);
		}
		if (houseResult == BUST_LEVEL)
		{
			//applyWinLoss() and resetBet() to all players.
			endRound(playersInGame, houseResult, gecbCollection);
			
			deck = removeCardAndRefillEmpty(deck);
		}
		else //House busted.
		{
			callBack(gecbCollection, "HOUSE_BUST", null, null);
			pause(delay);
			endRound(playersInGame, houseResult, gecbCollection);

			deck = removeCardAndRefillEmpty(deck);
		}
	}
	
	public void applyWinLoss(Player player, int houseResult)
	{
		if (player.getResult() > houseResult)
		{
			player.setPoints(player.getBet());
		}
		else if (player.getResult() < houseResult)
		{
			player.setPoints(-player.getBet());
		}
	}

	public void addPlayer(Player player)
	{
		//hashmap replaces double indexes
		playersInGame.put(player.getPlayerId(), player);
	}
	
	public Player getPlayer(String id)
	{
		return playersInGame.get(id);
	}
	
	public boolean removePlayer(Player player)
	{
		if(playersInGame.containsKey(player.getPlayerId()))
		{
			playersInGame.remove(player.getPlayerId());
			return true;
		}
		return false;
	}
	
	public boolean placeBet(Player player, int bet)
	{
		return (player.setBet(bet));
	}
	
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback)
	{
		gecbCollection.add(gameEngineCallback);
	}
	
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback)
	{
		if (gecbCollection.contains(gameEngineCallback))
		{
			gecbCollection.remove(gameEngineCallback);
			
			return true;
		}
		return false;
	}
	
	public Collection<Player> getAllPlayers()
	{
		//Create new list to not alter the playersInGame list.
		List<Player> players = new ArrayList<Player>();
		
		for (Entry<String, Player> next : playersInGame.entrySet()) 
		{ 
			players.add(next.getValue());
		}

		Collections.sort(players);
		Collections.reverse(players);

		Collection<Player> immutablePlayers = Collections.unmodifiableCollection(players);
		return immutablePlayers;
	}
	
	public Deque<PlayingCard> getShuffledHalfDeck()
	{
		List<PlayingCard> deck = new ArrayList<PlayingCard>();
		
		for (Suit suit : Suit.values()) 
		{ 
			for (Value value : Value.values())
			{
			    deck.add(new PlayingCardImpl(suit, value));
			}
		}
		Collections.shuffle(deck);
		
		Deque<PlayingCard> shuffledDeck = new ArrayDeque<PlayingCard>(deck);
		
		return shuffledDeck;
	}	
	
	//Helper methods.
	private Deque<PlayingCard> removeCardAndRefillEmpty(Deque<PlayingCard> deck)
	{
		deck.remove();
		deck = deck.isEmpty() ? getShuffledHalfDeck() : deck;
		
		return deck;
	}
	
	//Loop for GameEngineCallback collection.
	private void callBack(Deque<GameEngineCallback> gecbCollection, String function, Player player, Integer houseResult)
	{
		for (GameEngineCallback next : gecbCollection)
		{
			switch(function)
			{
			case "NEXT_CARD":
				next.nextCard(player, deck.getFirst(), this);
				break;
			case "RESULT":
				next.result(player, player.getResult(), this);
				break;
			case "BUST":
				next.bustCard(player, deck.getFirst(), this);
				break;
			case "HOUSE_NEXT":	
				next.nextHouseCard(deck.getFirst(), this);
				break;
			case "HOUSE_BUST":
				next.houseBustCard(deck.getFirst(), this);
				break;
			case "HOUSE_RESULT":
				next.houseResult(houseResult, this);
			default:
				break;
			}
		}
	}
	
	private void endRound(HashMap<String, Player> playersInGame, int houseResult, Deque<GameEngineCallback> gecbCollection)
	{	
		for (Entry<String, Player> next : playersInGame.entrySet()) 
		{ 
			applyWinLoss(next.getValue(), houseResult);
		}
		
		callBack(gecbCollection, "HOUSE_RESULT", null, houseResult);
		
		for (Entry<String, Player> next : playersInGame.entrySet()) 
		{ 
			next.getValue().resetBet();
			next.getValue().setResult(-next.getValue().getResult()); //reset score to zero.
		}
	}
	
	//Code adapted from: https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java
	private void pause(int delay)
	{
		try
		{
			Thread.sleep(delay);
		}
		catch(InterruptedException ex)
		{
			Thread.currentThread().interrupt();
		}		
	}
	
	private void delayCheck(int delay) throws IllegalArgumentException
	{
		if (delay < 0 || delay > 1000)
		{
			throw new IllegalArgumentException("Invalid delay amount.");
		}
	}
}