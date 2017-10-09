/*
 * Copyright (c) 2016 Karamel Quitayen and Justin LaGree. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

/** 
 * The MemeopolyGUI class implements an application that
 * plays a customized version of Monopoly (one with memes).
 */

package memeopoly;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Board extends JPanel
{
	private static final long serialVersionUID = 1L;
	public Player[] p;
	public Space[] s;
	
	Board(MemeopolyGame g)
	{
		p = g.players;
		s = g.spaces;
		repaint();
	}
	
	public void setPlayerArr(Player[] p){
		this.p = p;
	}
	public int getX(int spaceIndex, int playerIndex)
	{
		if (spaceIndex == 0 || spaceIndex == 30)
			return (playerIndex == 0 || playerIndex == 2)? 565: 585;
		else if (spaceIndex == 1 || spaceIndex == 29)
			return (playerIndex == 0 || playerIndex == 2)? 485: 505;
		else if (spaceIndex == 2 || spaceIndex == 28)
			return (playerIndex == 0 || playerIndex == 2)? 435: 455;
		else if (spaceIndex == 3 || spaceIndex == 27)
			return (playerIndex == 0 || playerIndex == 2)? 385: 405;
		else if (spaceIndex == 4 || spaceIndex == 26)
			return (playerIndex == 0 || playerIndex == 2)? 335: 355;
		else if (spaceIndex == 5 || spaceIndex == 25)
			return (playerIndex == 0 || playerIndex == 2)? 285: 305;
		else if (spaceIndex == 6 || spaceIndex == 24)
			return (playerIndex == 0 || playerIndex == 2)? 235: 255;
		else if (spaceIndex == 7 || spaceIndex == 23)
			return (playerIndex == 0 || playerIndex == 2)? 185: 205;
		else if (spaceIndex == 8 || spaceIndex == 22)
			return (playerIndex == 0 || playerIndex == 2)? 135: 155;
		else if (spaceIndex == 9 || spaceIndex == 21)
			return (playerIndex == 0 || playerIndex == 2)? 85: 105;
		else if (spaceIndex == 10)
		{
			if (p[playerIndex].isInJail())
				return (playerIndex == 0 || playerIndex == 2)? 35: 55;
			else
				return 7;
		}
		else if (spaceIndex >= 11 && spaceIndex <= 19)
			return (playerIndex == 0 || playerIndex == 2)? 10: 30;
		else if (spaceIndex == 20) //FREE PARKING
			return (playerIndex == 0 || playerIndex == 2)? 20: 40;
		else if (spaceIndex >= 31 && spaceIndex <= 39)
			return (playerIndex == 0 || playerIndex == 2)? 555: 575;
		else
			return 0;
	}
	
	
	public int getY(int spaceIndex, int playerIndex)
	{
		if (spaceIndex < 10)
			return (playerIndex <= 1)? 560: 580;
		else if (spaceIndex == 10)
		{
			if (p[playerIndex].isInJail())
				return (playerIndex <= 1)? 530: 550;
			else
			{
				if (playerIndex == 0)
					return 525;
				else if (playerIndex == 1)
					return 545;
				else if (playerIndex == 2)
					return 565;
				else
					return 585;
			}
		}
		else if (spaceIndex == 11 || spaceIndex == 39)
			return (playerIndex <= 1)? 480: 500;
		else if (spaceIndex == 12 || spaceIndex == 38)
			return (playerIndex <= 1)? 435: 455;
		else if (spaceIndex == 13 || spaceIndex == 37)
			return (playerIndex <= 1)? 385: 405;
		else if (spaceIndex == 14 || spaceIndex == 36)
			return (playerIndex <= 1)? 335: 355;
		else if (spaceIndex == 15 || spaceIndex == 35)
			return (playerIndex <= 1)? 285: 305;
		
		else if (spaceIndex == 16 || spaceIndex == 34)
			return (playerIndex <= 1)? 235: 255;
		else if (spaceIndex == 17 || spaceIndex == 33)
			return (playerIndex <= 1)? 185: 205;
		else if (spaceIndex == 18 || spaceIndex == 32)
			return (playerIndex <= 1)? 135: 155;
		else if (spaceIndex == 19 || spaceIndex == 31)
			return (playerIndex <= 1)? 85: 105;
		else if (spaceIndex == 20 || spaceIndex == 30)
			return (playerIndex <= 1)? 35: 55;
		else if (spaceIndex > 20 && spaceIndex < 30)
			return (playerIndex <= 1)? 15: 35;
		else
			return 0;
	}

	public int getX(int spaceIndex)
	{
		if (spaceIndex == 1 || spaceIndex == 29)
			return 495;
		else if (spaceIndex == 28)
			return 445;
		else if (spaceIndex == 3 || spaceIndex == 27)
			return 395;
		else if (spaceIndex == 26)
			return 345;
		else if (spaceIndex == 5 || spaceIndex == 25)
			return 295;
		else if (spaceIndex == 6 || spaceIndex == 24)
			return 245;
		else if (spaceIndex == 23)
			return 195;
		else if (spaceIndex == 8)
			return 145;
		else if (spaceIndex == 9 || spaceIndex == 21)
			return 95;
		else if (spaceIndex >= 11 && spaceIndex <= 19)
			return 78;
		else if (spaceIndex >= 31 && spaceIndex <= 39)
			return 517;
		else
			return 0;
	}
	
	public int getY(int spaceIndex)
	{
		if (spaceIndex < 10)
			return 517;
		else if (spaceIndex == 11 || spaceIndex == 39)
			return 490;
		else if (spaceIndex == 12 || spaceIndex == 38)
			return 445;
		else if (spaceIndex == 13 || spaceIndex == 37)
			return 395;
		else if (spaceIndex == 14 || spaceIndex == 36)
			return 345;
		else if (spaceIndex == 15 || spaceIndex == 35)
			return 295;
		else if (spaceIndex == 16 || spaceIndex == 34)
			return 245;
		else if (spaceIndex == 17 || spaceIndex == 33)
			return 195;
		else if (spaceIndex == 18 || spaceIndex == 32)
			return 145;
		else if (spaceIndex == 19 || spaceIndex == 31)
			return 95;
		else if (spaceIndex == 20 || spaceIndex == 30)
			return 45;
		else if (spaceIndex > 20 && spaceIndex < 30)
			return 78;
		else
			return 0;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		//draw board
		Image image = null;
		try {
			image = ImageIO.read(ResourceLoader.load("img/Monopoly Board.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(image, 0,  0,  null);
		
		
		for (int i = 0; i < p.length; i++)
		{
			switch(i){
				case 0: g.setColor(Color.RED); break;
				case 1: g.setColor(Color.GREEN); break;
				case 2: g.setColor(Color.BLUE); break;
				case 3: g.setColor(Color.YELLOW); break;
			}
			g.fillOval(getX(p[i].getLocation(), i), getY(p[i].getLocation(), i), 10, 10);
		}
		
		//box to idicate property owner
		for (int i = 0; i < 40; i++)
		{
			if (s[i].isOwned())
			{
				switch(s[i].getOwner().getIndex())
				{
					case 0: g.setColor(Color.RED); break;
					case 1: g.setColor(Color.GREEN); break;
					case 2: g.setColor(Color.BLUE); break;
					case 3: g.setColor(Color.YELLOW); break;
				}
				if ((i > 10 && i<20) || (i>30 && i<40))
					g.fillRect(getX(i), getY(i), 5, 10);
				else
					g.fillRect(getX(i), getY(i), 10, 5);
			}
		}
		for (int i = 0; i < 40; i++)
		{
			if (s[i].isOwned())
			{
				String upgrades = "";
				g.setColor(Color.BLACK);
				if (s[i] instanceof PropertySpace)
				{
					PropertySpace p = (PropertySpace)s[i];
					if (p.isOwned() && p.numHouses() == 0)
					{
						upgrades = "$";
						if (i>0 && i<10)
							g.drawString(upgrades, getX(i)-17, getY(i)+20);
						else if (i>10 && i<20)
							g.drawString(upgrades, getX(i)-20, getY(i)-5);
						else if (i>20 && i<30)
							g.drawString(upgrades, getX(i)-5, getY(i)-10);
						else if (i>30 && i<40)
							g.drawString(upgrades, getX(i)+10, getY(i)+5);
					}
					else if (p.numHotels() == 1)
					{
						upgrades = "H";
						if (i>0 && i<10)
							g.drawString(upgrades, getX(i)-17, getY(i)+20);
						else if (i>10 && i<20)
							g.drawString(upgrades, getX(i)-20, getY(i)-5);
						else if (i>20 && i<30)
							g.drawString(upgrades, getX(i)-5, getY(i)-10);
						else if (i>30 && i<40)
							g.drawString(upgrades, getX(i)+10, getY(i)+5);
					}
					else
					{
						for (int j = 0; j<p.numHouses();j++)
							upgrades += ((i >0 && i<10) || (i>20 && i<30))? "h " : "h\n";
						
						if (i>0 && i<10)
							drawString(g, upgrades, getX(i)-17, getY(i)+20);
						else if (i>10 && i<20)
							drawString(g, upgrades, getX(i)-20, getY(i)-5);
						else if (i>20 && i<30)
							drawString(g, upgrades, getX(i)-5, getY(i)-10);
						else if (i>30 && i<40)
							drawString(g, upgrades, getX(i)+10, getY(i)+5);
					}
				}
			}
		}
	}
	private void drawString(Graphics g, String text, int x, int y) {
		y -= g.getFontMetrics().getHeight();
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight()-5);
    }
}