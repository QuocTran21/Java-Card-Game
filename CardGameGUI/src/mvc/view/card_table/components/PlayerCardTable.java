package mvc.view.card_table.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.CellRendererPane;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PlayerCardTable extends JPanel
{
	private List<Image> suits = new ArrayList<Image>();
	private List<String> values = new ArrayList<String>();
	private final String RED = "<html><font color=\"#FF0000\">%s</font></html>";
	private final String BLACK = "<html><font color=\"#000000\">%s</font></html>";
	private Boolean isBusted = false;
	private Boolean hasDealt = false;
	
	public void dealCardsOnTable(String suit, String value, Boolean isBusted)
	{
		this.isBusted = isBusted;
		
		String colouredValue = (suit.equals("DIAMONDS") || suit.equals("HEARTS")) ? String.format(RED, convert(value)) : String.format(BLACK, convert(value));
		values.add(colouredValue);

		try 
		{
			suits.add(ImageIO.read(new File(String.format("img/suits/%s.PNG", suit))));
		} 
		catch (IOException e) {}
		
		revalidate();
		repaint();
	}
	
	private String convert(String value)
	{
		switch(value)
		{
		case "EIGHT": 
			return "8";
		case "NINE": 
			return "9";
		case "TEN": 
			return "10";
		case "JACK": 
			return "J";
		case "QUEEN": 
			return "Q";
		case "KING": 
			return "K";
		case "ACE": 
			return "A";
		default: 
			return "0";
		}
	}
	
	public void paintComponent(Graphics g)
	{
		int cardWidth = getWidth()/6;
		int cardHeight = (int) (1.5 * cardWidth);
		int suitSize = cardWidth/6;
		int valueSize = cardWidth/8;
		
		int card_x = 0;
		int card_y = (getHeight() - cardHeight)/2;
		int suit_x = (cardWidth - suitSize)/2;
		int suit_y = (cardHeight - suitSize)/2;
		int value_x = cardWidth/8;
		int value_y = cardHeight/7;
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		setBackground(new Color(85,107,47));
			
		for (int i = 0; i < suits.size(); i++)
		{
			drawCardBackGround(g2, Color.WHITE, card_x, card_y, cardWidth, cardHeight);
			drawSuit(g2, i, card_x, card_y, suit_x, suit_y, suitSize);
			drawValue(g2, i, card_x, card_y, value_x, value_y, valueSize, cardWidth, cardHeight);
			drawPadding(g2, card_x, card_y, cardWidth, cardHeight);
			card_x += getWidth()/6;
		}
		
		if(isBusted)
		{
			card_x -= getWidth()/6;
			drawCardBackGround(g2, Color.GRAY, card_x, card_y, cardWidth, cardHeight);
			drawSuit(g2, suits.size()-1, card_x, card_y, suit_x, suit_y, suitSize);
			drawValue(g2, suits.size()-1, card_x, card_y, value_x, value_y, valueSize, cardWidth, cardHeight);
			drawPadding(g2, card_x, card_y, cardWidth, cardHeight);
		}
	}
	
	private void drawCardBackGround(Graphics2D g2, Color color, int card_x, int card_y, int cardWidth, int cardHeight)
	{
		g2.setColor(color);
		g2.fillRoundRect(card_x, card_y, cardWidth, cardHeight, 30, 30);
	}
	
	private void drawSuit(Graphics2D g2, int index, int card_x, int card_y, int suit_x, int suit_y, int suitSize)
	{
		g2.drawImage(suits.get(index), card_x+suit_x , card_y+suit_y, suitSize, suitSize, null, null);
	}
	
	private void drawValue(Graphics2D g2, int index, int card_x, int card_y, int value_x, int value_y, int valueSize, int cardWidth, int cardHeight)
	{
		JLabel renderer = new JLabel(values.get(index));
		renderer.setFont(new Font("Din1451", Font.PLAIN, valueSize));
		CellRendererPane crp = new CellRendererPane();
	    crp.paintComponent(g2, renderer, this, (int) (card_x+1.7*value_x), (int) (card_y+0.8*value_y), cardWidth/6, cardWidth/6);
	    crp.paintComponent(g2, renderer, this, (int) (card_x+cardWidth-1.7*value_x-valueSize), 
	    									   (int) (card_y+cardHeight-1.6*value_y), cardWidth/6, cardWidth/6);
	}
	
	private void drawPadding(Graphics2D g2, int card_x, int card_y, int cardWidth, int cardHeight)
	{
		g2.setColor(new Color(85,107,47));
		g2.setStroke(new BasicStroke(20));
		g2.drawRoundRect(card_x, card_y, cardWidth, cardHeight, 30, 30);
	}
	
	public Boolean hasDealt()
	{
		return hasDealt;
	}
	
	public void setHasDealt(Boolean hasDealt)
	{
		this.hasDealt = hasDealt;
	}
}