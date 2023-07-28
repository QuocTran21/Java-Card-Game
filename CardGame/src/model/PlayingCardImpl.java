package model;

import model.interfaces.PlayingCard;


public class PlayingCardImpl implements PlayingCard
{
	private final Suit SUIT;
	private final Value VALUE;
	
	public PlayingCardImpl(Suit suit, Value value)
	{
		if ( suit == null || value == null)
		{
			throw new IllegalArgumentException("null args.");
		}
		this.SUIT = suit;
		this.VALUE = value;
	}
	
	public Suit getSuit() 
	{
		return SUIT;
	}

	public Value getValue() 
	{
		return VALUE;
	}

	public int getScore() 
	{
		switch(VALUE)
		{
		case EIGHT: 
			return 8;
		case NINE: 
			return 9;
		case TEN: 
			return 10;
		case JACK: 
			return 10;
		case QUEEN: 
			return 10;
		case KING: 
			return 10;
		case ACE: 
			return 11;	
		default: 
			return 0;
		}
	}

	@Override
	public String toString()
	{
		return String.format("Suit: %s, Value: %s, Score: %d", suit(), value(), getScore());
	}
	
	public boolean equals(PlayingCard card) 
	{
		return (card.getValue().equals(VALUE)) && (card.getSuit().equals(SUIT));
	}
	
	@Override
	public boolean equals(Object card)
	{
		if (card == null)
		{
			return false;
		}
		if (card instanceof PlayingCard)
		{
			return ((PlayingCard) card).equals(this);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return VALUE.hashCode() + SUIT.hashCode();
	}
	
	//Helper methods
	private String suit()
	{
		return SUIT.toString().charAt(0) + SUIT.toString().substring(1).toLowerCase();
	}
	
	private String value()
	{
		return VALUE.toString().charAt(0) + VALUE.toString().substring(1).toLowerCase();
	}
}