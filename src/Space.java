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

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public abstract class Space
{
	private String name;
	private int cost;
	private Player owner = null;
	boolean owned = false;
	
	public Space(String name, int cost){
		this.name = name;
		this.cost = cost;
	}
	public String getName(){
		return name;
	}
	public Player getOwner(){
		return owner;
	}
	public int getPrice(){
		return cost;
	}
	public void purchase(Player player){
		owned = true;
		owner = player;
	}
	public boolean isOwned(){
		return owned;
	}
	public ImageIcon getIcon(){
		Image img = null;
		try {
			img = ImageIO.read(ResourceLoader.load("img/" + name + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ImageIcon(img);
		/*
		java.net.URL url = Board.class.getResource("/resources/" + name + ".jpg");
		return new ImageIcon(url); */
	}
}
class PropertySpace extends Space
{
	int rent, housePrice, hotelPrice;
	int house1, house2, house3, house4, hotel;
	int amtHouse = 0, amtHotel = 0;
	String color;
	
	public PropertySpace(String name, int cost, int rent, int housePrice, int hotelPrice,
						 int house1, int house2, int house3, int house4, int hotel, String color){
		super(name, cost);
		this.rent = rent;
		this.housePrice = housePrice;
		this.hotelPrice = hotelPrice;
		this.house1 = house1;
		this.house2 = house2;
		this.house3 = house3;
		this.house4 = house4;
		this.hotel = hotel;
		this.color = color;
	}
	
	public int rent()
	{
		if (amtHotel == 1)
			return hotel;
		if (amtHouse == 0)
			return rent;
		if (amtHouse == 1)
			return house1;
		if (amtHouse == 2)
			return house2;
		if (amtHouse == 3)
			return house3;
		if (amtHouse == 4)
			return house4;
		return 0;
	}
	
	public void addHouse(){
		amtHouse++;
	}
	public void addHotel(){
		amtHotel++;
		amtHouse = 0;
	}
	public int numHouses()
	{
		return amtHouse;
	}
	public int numHotels()
	{
		return amtHotel;
	}
	public int housePrice(){
		return housePrice;
	}
	public int hotelPrice(){
		return hotelPrice;
	}
	public String color(){
		return color;
	}
}

class RailroadSpace extends Space
{
	public RailroadSpace(String name){
		super(name, 200);
		owned = false;
	}
}

class UtilitySpace extends Space
{
	public UtilitySpace(String name){
		super(name, 150);
	}
}

class TaxSpace extends Space
{
	public TaxSpace(String name){
		super(name, 0);
	}
}

class CommunityChestSpace extends Space
{
	public CommunityChestSpace(){
		super("Community Chess", 0);
	}
}

class ChanceSpace extends Space
{
	public ChanceSpace(){
		super("Chance",0);
	}
}

class GoToJailSpace extends Space
{
	public GoToJailSpace(){
		super("Go to Jail",0);
	}
}
class JailSpace extends Space
{
	public JailSpace(){
		super("Drag and Drop Programming Class",0);
	}
}

class ParkingSpace extends Space
{
	public ParkingSpace(){
		super("Preaching to the Choir",0);
	}
}

class GoSpace extends Space
{	
	public GoSpace(){
		super("Go",0);
	}
}