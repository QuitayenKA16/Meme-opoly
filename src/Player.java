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

import java.util.Vector;

public class Player 
{
	private String name;
	private int money, location, index, jailTries;
	private Vector<PropertySpace> properties;
	private Vector<Space> spaces;
	private int railroadsOwned, utilitiesOwned;
	private boolean jail;
	private boolean passedGo = false;
	
	Player(String name, int index)
	{
		this.name = name;
		this.index = index;
		money = 1500;
		location = 0;
		properties = new Vector<PropertySpace>();
		spaces = new Vector<Space>();
		railroadsOwned = 0;
		utilitiesOwned = 0;
		jailTries = 0;
		jail = false;
	}
	
	public String getName(){
		return name;
	}
	
	public int getMoney(){
		return money;
	}
	
	public void addMoney(int a){
		money+=a;
	}
	
	public void subtractMoney(int a){
		money-=a;
		}
	
	public int getIndex(){
		return index;
	}
	public int getLocation(){
		return location;
	}
	
	public boolean hasPassedGo(){
		return passedGo;
	}
	public void resetGo(){
		passedGo = false;
	}
	public void move(int n){
		location += n; //0-39
		if (location > 39)
		{
			addMoney(200);
			passedGo = true;
			location -= 40;
		}
	}
	public void tradeSpace(int i){
		Space space = spaces.elementAt(i);
		spaces.removeElementAt(i);
		if (space instanceof PropertySpace)
			properties.remove((PropertySpace) space);
		else if (space instanceof RailroadSpace)
			railroadsOwned--;
		else if (space instanceof UtilitySpace)
			utilitiesOwned--;
	}
	public void getTrade(Space space)
	{
		space.purchase(this);
		spaces.add(space);
		if (space instanceof PropertySpace)
			properties.add((PropertySpace) space);
		else if (space instanceof RailroadSpace)
			railroadsOwned++;
		else if (space instanceof UtilitySpace)
			utilitiesOwned++;
	}
	
	public void buyProperty(Space space)
	{
		space.purchase(this);
		subtractMoney(space.getPrice());
		spaces.add(space);
		if (space instanceof PropertySpace)
			properties.add((PropertySpace) space);
		else if (space instanceof RailroadSpace)
			railroadsOwned++;
		else if (space instanceof UtilitySpace)
			utilitiesOwned++;
	}
	
	public PropertySpace propertyAt(int i){
		return properties.elementAt(i);
	}
	public Space spaceAt(int i){
		return spaces.elementAt(i);
	}
	public Vector<Space> getSpaces(){
		return spaces;
	}
	
	public int numProperties(){
		return properties.size();
	}
	public int numRailroads(){
		return railroadsOwned;
	}
	public int numUtilities(){
		return utilitiesOwned;
	}
	
	public boolean isInJail(){
		return jail;
	}
	public void sendToJail(){
		jail = true;
		location = 10;
	}
	public void setIndex(int i){
		index = i;
	}
	public void bailOutofJail(){
		jail = false;
	}
	public void failedJailTry(){
		jailTries++;
	}
	public int numJailTries(){
		return jailTries;
	}
	public void playerInfo()
	{
		System.out.println();
		System.out.println("----------------------------------");
		System.out.println(name + "'s Properties:");
		for (int i = 0; i < spaces.size(); i++)
		{
			System.out.println(i + ": " + spaces.elementAt(i).getName());
		}
		System.out.println();
		System.out.println("Total spaces:     " + spaces.size());
		System.out.println("Total properties: " + properties.size());
		System.out.println("Total utilties:   " + utilitiesOwned);
		System.out.println("Total railroads:  " + railroadsOwned);
		System.out.println(properties.size() + utilitiesOwned + railroadsOwned);
		System.out.println("Total money: " + money);
		System.out.println("----------------------------------");
		System.out.println();
	}
}
