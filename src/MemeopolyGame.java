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

public class MemeopolyGame {
	public Player[] players;
	public Space[] spaces = new Space[40];
	private int turn;
	
	MemeopolyGame(String names[])
	{
		players = new Player[names.length];
		for (int i=0; i<names.length;i++)
		{
			players[i] = new Player(names[i], i);
		}
		setUpSpaces();
	}
	
	private void setUpSpaces()
	{
		spaces = new Space[40];
		spaces[0] = new GoSpace();    //name, r,hse,hot,h1,h2,h3,h4,H	
		spaces[1] = new PropertySpace("While Loops",60,2,50,50,10,30,90,160,250, "Brown");
		spaces[2] = new CommunityChestSpace();
		spaces[3] = new PropertySpace("Cap Carl",60,4,50,50,20,60,180,320,450,"Brown");
		spaces[4] = new TaxSpace("Review Questions");
		spaces[5] = new RailroadSpace("Jakes Bedtime");
		spaces[6] = new PropertySpace("App Inventor",100,6,50,50,30,90,270,400,550,"Light Blue"); 
		spaces[7] = new ChanceSpace();
		spaces[8] = new PropertySpace("Easy CTF Delays",100,6,50,50,30,90,270,400,550,"Light Blue");
		spaces[9] = new PropertySpace("Poker",120,8,50,50,40,100,300,450,600,"Light Blue");
		spaces[10] = new JailSpace();
		
		spaces[11] = new PropertySpace("Evil Necromancer",140,10,100,100,50,150,450,625,750,"Magenta");
		spaces[12] = new UtilitySpace("Copy and Paste");
		spaces[13] = new PropertySpace("Cheerleading Lasers",140,10,100,100,50,150,450,625,750,"Magenta");
		spaces[14] = new PropertySpace("95 Scorcher",160,12,100,100,60,180,500,700,900,"Magenta");
		spaces[15] = new RailroadSpace("Im in the area");
		spaces[16] = new PropertySpace("Matt Moneymaker",180,14,100,100,70,200,550,750,950, "Orange");
		spaces[17] = new CommunityChestSpace();
		spaces[18] = new PropertySpace("Good Luck Brian",180,14,100,100,70,200,550,750,950,"Orange");
		spaces[19] = new PropertySpace("Elders React to BMI Calculator",200,16,100,100,80,220,600,800,1000,"Orange");
		spaces[20] = new ParkingSpace();
		
		spaces[21] = new PropertySpace("MLG Splash Screens",220,18,150,150,90,250,700,875,1050, "Red");
		spaces[22] = new ChanceSpace();
		spaces[23] = new PropertySpace("Array of Radio Buttons",220,18,150,150,90,250,700,875,1050, "Red");
		spaces[24] = new PropertySpace("Starting Junior Year",240,20,150,150,100,300,750,925,1100, "Red");
		spaces[25] = new RailroadSpace("NHTI Sweatshirt");
		spaces[26] = new PropertySpace("Chump Quality Control",260,22,150,150,110,330,800,975,1150,"Yellow");
		spaces[27] = new PropertySpace("Atticus Finch",260,22,150,150,110,330,800,975,1150,"Yellow");
		spaces[28] = new UtilitySpace("Stack Overflow");
		spaces[29] = new PropertySpace("==",280,24,150,150,120,360,850,1025,1200,"Yellow");
		spaces[30] = new JailSpace();
		
		spaces[31] = new PropertySpace("5!",300,26,200,200,130,390,900,1100,1275,"Green");
		spaces[32] = new PropertySpace("Programming Parties",300,26,200,200,130,390,900,1100,1275,"Green");
		spaces[33] = new CommunityChestSpace();
		spaces[34] = new PropertySpace("Corgi Gifs",320,28,200,200,150,450,1000,1200,1400,"Green");
		spaces[35] = new RailroadSpace("Jakes Backpack");
		spaces[36] = new ChanceSpace();
		spaces[37] = new PropertySpace("For Loops",350,35,200,200,175,500,1100,1300,1500, "Blue");
		spaces[38] = new TaxSpace("Writing Portion of Test");
		spaces[39] = new PropertySpace("Mrs. P",400,50,200,200,200,600,1400,1700,2000, "Blue");
	}
	
	public int turn(){ //return the index in vector of turn
		return turn;
	}
	
	public void nextTurn()
	{
		turn++; //increments turn
		if (turn == players.length) //if past last player
			turn = 0; //reset
	}
	
	public Player currentTurn(){ //returns the Player object who's turn it is
		return players[turn];
	}
	
	public Player playerAt(int i){
		return players[i];
	}
	
	public void movePlayer(int roll){
		currentTurn().move(roll);
	}
	public void playerBankrupt(int index)
	{
		Player p = players[index];
		for (int i = 0; i < 40; i++)
		{
			if (spaces[i] instanceof PropertySpace || spaces[i] instanceof RailroadSpace || spaces[i] instanceof UtilitySpace)
			{
				if (spaces[i].isOwned() && spaces[i].getOwner().equals(p))
				{
					switch (i)
					{
						case 1: spaces[1] = new PropertySpace("While Loops",60,2,50,50,10,30,90,160,250, "Brown"); break;
						case 3: spaces[3] = new PropertySpace("Cap Carl",60,4,50,50,20,60,180,320,450,"Brown"); break;
						case 5: spaces[5] = new RailroadSpace("Jakes Bedtime"); break;
						case 6: spaces[6] = new PropertySpace("App Inventor",100,6,50,50,30,90,270,400,550,"Light Blue"); break; 
						case 8: spaces[8] = new PropertySpace("Easy CTF Delays",100,6,50,50,30,90,270,400,550,"Light Blue"); break;
						case 9: spaces[9] = new PropertySpace("Poker",120,8,50,50,40,100,300,450,600,"Light Blue"); break;
						
						case 11: spaces[11] = new PropertySpace("Evil Necromancer",140,10,100,100,50,150,450,625,750,"Magenta"); break;
						case 12: spaces[12] = new UtilitySpace("Copy and Paste"); break;
						case 13: spaces[13] = new PropertySpace("Cheerleading Lasers",140,10,100,100,50,150,450,625,750,"Magenta"); break;
						case 14: spaces[14] = new PropertySpace("95 Scorcher",160,12,100,100,60,180,500,700,900,"Magenta"); break;
						case 15: spaces[15] = new RailroadSpace("Im in the area"); break;
						case 16: spaces[16] = new PropertySpace("Matt Moneymaker",180,14,100,100,70,200,550,750,950, "Orange"); break;
						case 18: spaces[18] = new PropertySpace("Good Luck Brian",180,14,100,100,70,200,550,750,950,"Orange"); break;
						case 19: spaces[19] = new PropertySpace("Elders React to BMI Calculator",200,16,100,100,80,220,600,800,1000,"Orange"); break;
						
						case 21: spaces[21] = new PropertySpace("MLG Splash Screens",220,18,150,150,90,250,700,875,1050, "Red"); break;
						case 23: spaces[23] = new PropertySpace("Array of Radio Buttons",220,18,150,150,90,250,700,875,1050, "Red"); break;
						case 24: spaces[24] = new PropertySpace("Starting Junior Year",240,20,150,150,100,300,750,925,1100, "Red"); break; 
						case 25: spaces[25] = new RailroadSpace("NHTI Sweatshirt"); break; 
						case 26: spaces[26] = new PropertySpace("Chump Quality Control",260,22,150,150,110,330,800,975,1150,"Yellow"); break;
						case 27: spaces[27] = new PropertySpace("Atticus Finch",260,22,150,150,110,330,800,975,1150,"Yellow"); break;
						case 28: spaces[28] = new UtilitySpace("Stack Overflow"); break;
						case 29: spaces[29] = new PropertySpace("==",280,24,150,150,120,360,850,1025,1200,"Yellow"); break;
						
						case 31: spaces[31] = new PropertySpace("5!",300,26,200,200,130,390,900,1100,1275,"Green"); break;
						case 32: spaces[32] = new PropertySpace("Programming Parties",300,26,200,200,130,390,900,1100,1275,"Green"); break;
						case 34: spaces[34] = new PropertySpace("Corgi Gifs",320,28,200,200,150,450,1000,1200,1400,"Green"); break;
						case 35: spaces[35] = new RailroadSpace("Jakes Backpack"); break;
						case 37: spaces[37] = new PropertySpace("For Loops",350,35,200,200,175,500,1100,1300,1500, "Blue"); break;
						case 39: spaces[39] = new PropertySpace("Mrs. P",400,50,200,200,200,600,1400,1700,2000, "Blue"); break;
					}
				}
			}
		}
		
		Player[] temp = new Player[players.length-1];
		int j = 0;
		for (int i = 0; i < players.length; i++)
		{
			if (!players[i].equals(p))
			{
				temp[j] = players[i];
				j++;
			}
		}
			
		players = new Player[temp.length];
		for (int i = 0; i < temp.length; i++)
		{
			players[i] = temp[i];
			players[i].setIndex(i);
		}
		turn--;
		
	}
	
	public Player[] getPlayers(){
		return players;
	}
	
	public int getPlayerLocation(int playerIndex){
		return players[playerIndex].getLocation();
	}
}
