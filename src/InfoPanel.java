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

import javax.swing.JPanel;

public class InfoPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	MemeopolyGame game;
	InfoPanel(MemeopolyGame mGame)
	{
		game = mGame;
		setBackground(Color.WHITE);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		for (int i=0; i<game.players.length;i++)
		{
			switch(i){
			case 0: g.setColor(Color.RED); break;
			case 1: g.setColor(Color.GREEN); break;
			case 2: g.setColor(Color.BLUE); break;
			case 3: g.setColor(Color.YELLOW); break;
			}
			
			g.fillOval(20, (i*20)+90, 10, 10);
			
			g.setColor(Color.BLACK);
			g.drawString(game.playerAt(i).getName() + "  $"+game.playerAt(i).getMoney(),40, (i*20)+100);
		}
		
	}
}
