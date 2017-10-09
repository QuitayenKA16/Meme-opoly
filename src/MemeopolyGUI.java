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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

public class MemeopolyGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	static MemeopolyGame game;
	public JPanel mainPanel, dicePanel, btnPanel, formPanel;
	public JLayeredPane layeredPane = new JLayeredPane();
	
	public JButton rollBtn;
	public JButton finishedTurnBtn;
	public JButton propertyBtn;
	public JButton tradeBtn;
	
	public Board b;
	
	public JLabel error;
	public JTextField[] nameTextFields = new JTextField[4];
	
	public JMenuBar menuBar = new JMenuBar();
	public JMenu[] menu = {new JMenu("Game"), new JMenu("Credits")};
	public JMenuItem[] menuItems = {new JMenuItem("New"), new JMenuItem("Resume"), 
									new JMenuItem("Exit"), new JMenuItem("About")};
	
	private Timer diceTimer = new Timer(100, new DiceTimerListener());
	public int cntr, amtPlayers;
	
	InfoPanel info;
	
	public static void main(String[] args) {
		new MemeopolyGUI();
	}
	
	MemeopolyGUI(){
		setTitle("Meme-opoly");
		setLocation(20,20);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);;
		setVisible(true);
		
		menu[0].add(menuItems[0]);
		menu[0].add(menuItems[1]);
		menu[0].add(menuItems[2]);
		menu[1].add(menuItems[3]);
		menuItems[1].setEnabled(false);
		
		menuItems[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				getPlayerNames();
			}
		});
		menuItems[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				getContentPane().removeAll();
				getContentPane().add(layeredPane);
				setSize(605,650);
				refresh();
			}
		});
		menuItems[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				System.exit(0);
			}
		});
		menuItems[3].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				about();
			}
		});
		menuBar.add(menu[0]);
		menuBar.add(menu[1]);
		
		setJMenuBar(menuBar);
		about();
	}
	public void about()
	{
		setSize(600,600);
		getContentPane().removeAll();
		JPanel aboutPnl = new JPanel();
		aboutPnl.setSize(600,600);
		Image img = null;
		try {
			img = ImageIO.read(ResourceLoader.load("img/Meme-opoly CD Cover.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		aboutPnl.add(new JLabel(new ImageIcon(img)));
		getContentPane().add(aboutPnl);
		refresh();
	}
	
	public void getPlayerNames()
	{	
		getContentPane().removeAll();
		setSize(500,520);
		setBackground(new Color(205,231,208));
		JPanel titlePanel = new JPanel();
		titlePanel.setLocation(0,0);
		titlePanel.setSize(500,120);
		titlePanel.setBackground(new Color(205,231,208));
		Image img = null;
		try {
			img = ImageIO.read(ResourceLoader.load("img/meme-opoly.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel title = new JLabel(new ImageIcon(img));
		titlePanel.add(title);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLocation(0,400);
		btnPanel.setSize(500,50);
		btnPanel.setBackground(new Color(205,231,208));
		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				checkNames();
			}
		});
		btnPanel.add(start);
		
		rollBtn = new JButton("Roll");
		rollBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				cntr = 0;
				diceTimer.start();
			}
		});
		finishedTurnBtn = new JButton("Finish Turn");
		finishedTurnBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				finishedTurn();
			}
		});
		propertyBtn = new JButton("Buy Houses");
		propertyBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				propertiesDialog();
			}
		});
		tradeBtn = new JButton("Trade Property");
		tradeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				tradeDialog();
			}
		});
		
		mainPanel = new JPanel();
		mainPanel.setSize(500,50);
		mainPanel.setLocation(0,120);
		mainPanel.setBackground(new Color(205,231,208));
		JLabel lbl = new JLabel("Amount of Players:");
		lbl.setFont(new Font("Consolas", Font.PLAIN, 20));
		mainPanel.add(lbl);
		
		formPanel = new JPanel();
		formPanel.setSize(500,300);
		formPanel.setBackground(new Color(205,231,208));
		formPanel.setLocation(0,170);
		
		final JPanel[] form = new JPanel[4];
		final JComboBox<String> numPlayers;
		
		//set up global objects
		error = new JLabel("Each player must have name.");
		error.setFont(new Font("Consolas", Font.BOLD, 16));
		for (int i = 0; i < 4; i++)
		{
			nameTextFields[i] = new JTextField("", 15);
			nameTextFields[i].setFont(new Font("Consolas", Font.PLAIN, 18));
			nameTextFields[i].setText("");
			form[i] = new JPanel();
			form[i].setBackground(new Color(205,231,208));
			JLabel nameLbl = new JLabel("Player #" + (i+1) + ":");
			nameLbl.setFont(new Font("Consolas", Font.PLAIN, 18));
			switch (i){
				case 0: nameLbl.setForeground(Color.RED); break;
				case 1: nameLbl.setForeground(Color.GREEN); break;
				case 2: nameLbl.setForeground(Color.BLUE); break;
				case 3: nameLbl.setForeground(Color.YELLOW); break;
			}
			form[i].add(nameLbl);
			form[i].add(nameTextFields[i]);
		}
		amtPlayers = 2;
		
		formPanel.add(form[0]);
		formPanel.add(form[1]);
		//nameTextFields[0].setText("Karamel"); nameTextFields[1].setText("Justin");
		//nameTextFields[2].setText("Bob"); nameTextFields[3].setText("Kevin");
		
		String html = "<html><font face='Consolas' size='5'>";
		String[] amt = {html+"Two", html+"Three", html+"Four"};
		numPlayers = new JComboBox<String>(amt);
		numPlayers.setSelectedIndex(0);
		numPlayers.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				int i = numPlayers.getSelectedIndex();
				getContentPane().remove(formPanel);
				amtPlayers = i + 2;
				formPanel.remove(error);
				formPanel.removeAll();
				
				for (int j = 0; j < 4; j++)
					formPanel.add(form[j]);
				if (i < 2)
					formPanel.remove(form[3]);
				if (i < 1)
					formPanel.remove(form[2]);
					
				getContentPane().add(formPanel);
				refresh();
			}
		});
		
		mainPanel.add(numPlayers);
		getContentPane().add(titlePanel);
		getContentPane().add(mainPanel);
		getContentPane().add(formPanel);
		getContentPane().add(btnPanel);
		refresh();
	}
	
	public void checkNames()
	{
		boolean good = true;
		if (nameTextFields[0].getText().compareTo("") == 0 || nameTextFields[1].getText().compareTo("") == 0)
			good = false;
		else if (amtPlayers > 2)
			if ((nameTextFields[2].getText().compareTo("") == 0) || (amtPlayers == 4 && nameTextFields[3].getText().compareTo("") == 0))
				good = false;
		
		String[] names = new String[amtPlayers];
		if (good)
		{
			for (int i = 0; i < amtPlayers; i++)
				names[i] = nameTextFields[i].getText();
			game = new MemeopolyGame(names);
			startGame();
		}
		else
		{
			getContentPane().remove(formPanel);
			formPanel.add(error);
			getContentPane().add(formPanel);
			refresh();
		}
	}
	
	public class DiceTimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent Evt)
		{
			//change each dice image to a random new image
			Random randNumGen = new Random();
			dicePanel.removeAll();
			int diceVal1 = (randNumGen.nextInt(6) + 1);
			int diceVal2 = (randNumGen.nextInt(6) + 1);
			
			Image dimg1 = null;
			Image dimg2 = null;
			try {
				dimg1 = ImageIO.read(ResourceLoader.load("img/" + diceVal1 + ".GIF"));
				dimg2 = ImageIO.read(ResourceLoader.load("img/" + diceVal2 + ".GIF"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			dicePanel.add(new JLabel(new ImageIcon(dimg1)));
			dicePanel.add(new JLabel(new ImageIcon(dimg2)));
			refresh();
			
			cntr++;
			if (cntr == 5)
			{
				diceTimer.stop();
				btnPanel.removeAll();
				btnPanel.add(propertyBtn);
				btnPanel.add(tradeBtn);
				btnPanel.add(finishedTurnBtn);
				refresh();
				movePlayer(diceVal1 + diceVal2);
			}
		}
	}
	
	public void startGame()
	{
		menuItems[1].setEnabled(true);
		getContentPane().removeAll();
		setSize(605,673);
		
		info = new InfoPanel(game);
		info.repaint();
		
		//create layered panel
		layeredPane.removeAll();
		getContentPane().add(layeredPane);
		layeredPane.setBounds(0, 0, 605, 625);
		//b = new Board(game.players, game.spaces);
		b = new Board(game);
		b.repaint();
		b.setBounds(0,0,605,625);
		b.setOpaque(true);
		layeredPane.add(b, new Integer(0), 0);
		
		mainPanel = new JPanel();
		mainPanel.removeAll();
		mainPanel.setBounds(100,80,400,50);
		mainPanel.setOpaque(false);
		layeredPane.add(mainPanel, new Integer(1), 0);
		
		dicePanel = new JPanel();
		dicePanel.removeAll();
		dicePanel.setBounds(100,120,400,80);
		dicePanel.setOpaque(false);
		layeredPane.add(dicePanel, new Integer(1), 0);
		
		btnPanel = new JPanel();
		btnPanel.removeAll();
		btnPanel.setBounds(100,200,400,50);
		btnPanel.setOpaque(false);
		layeredPane.add(btnPanel, new Integer(1), 0);
		
		info.setBounds(100, 260, 400, 200);
		info.setOpaque(false);
		layeredPane.add(info, new Integer(1), 0);
		refresh();
		startTurn();
	}
	
	public void startTurn()
	{
		mainPanel.removeAll();
		JLabel turnLbl = new JLabel(game.currentTurn().getName() + ": $" + game.currentTurn().getMoney());
		turnLbl.setFont(new Font("Consolas", Font.BOLD, 20));
		mainPanel.add(turnLbl);
		refresh();
		
		if (game.currentTurn().isInJail())
		{
			dicePanel.removeAll();
			btnPanel.removeAll();
			btnPanel.add(propertyBtn);
			btnPanel.add(tradeBtn);
			btnPanel.add(finishedTurnBtn);
			jailDialog();
		}
		else
		{
			dicePanel.removeAll();
			btnPanel.removeAll();
			btnPanel.add(rollBtn);
			btnPanel.add(propertyBtn);
			btnPanel.add(tradeBtn);
			refresh();
		}
	}
	
	public void startTurn(int a)
	{	
		if (!game.currentTurn().isInJail())
		{
			mainPanel.removeAll();
			JLabel turnLbl = new JLabel(game.currentTurn().getName() + ": $" + game.currentTurn().getMoney());
			turnLbl.setFont(new Font("Consolas", Font.BOLD, 20));
			mainPanel.add(turnLbl);

			btnPanel.removeAll();
			btnPanel.add(propertyBtn);
			btnPanel.add(tradeBtn);
			btnPanel.add(finishedTurnBtn);
			refresh();
		}
	}
	public void startTurn(boolean b)
	{
		if (!game.currentTurn().isInJail())
		{
			mainPanel.removeAll();
			JLabel turnLbl = new JLabel(game.currentTurn().getName() + ": $" + game.currentTurn().getMoney());
			turnLbl.setFont(new Font("Consolas", Font.BOLD, 20));
			mainPanel.add(turnLbl);
			refresh();
		}
	}
	
	public void finishedTurn()
	{
		game.nextTurn();
		startTurn();
	}
	
	public void movePlayer(int roll)
	{
		game.currentTurn().move(roll);
		int newLoc = game.currentTurn().getLocation();
		
		if (game.currentTurn().hasPassedGo())
		{
			JOptionPane.showMessageDialog(null, "You passed GO! Collect 200!", "GO", JOptionPane.DEFAULT_OPTION);
			game.currentTurn().resetGo();
		}
		
		if (game.spaces[newLoc] instanceof PropertySpace)
		{
			if (!((game.spaces[newLoc]).isOwned()))
				buyDialog(game.spaces[newLoc]);
			else
			{
				if (game.currentTurn() != game.spaces[newLoc].getOwner())
				{
					JOptionPane.showMessageDialog(null, "You landed on " +
							game.spaces[newLoc].getOwner().getName() + "'s meme. Pay $" +
							((PropertySpace) game.spaces[newLoc]).rent());
					game.currentTurn().subtractMoney(((PropertySpace)game.spaces[newLoc]).rent());
					game.spaces[newLoc].getOwner().addMoney(((PropertySpace)game.spaces[newLoc]).rent());
				}
			}
		}
		if (game.spaces[newLoc] instanceof RailroadSpace)
		{
			if (!((game.spaces[newLoc]).isOwned()))
				buyDialog(game.spaces[newLoc]);
			else
			{
				int pay = 0;
				int numRailroadsOwned = game.spaces[newLoc].getOwner().numRailroads();
				if (numRailroadsOwned == 1)
					pay = 25;
				else if (numRailroadsOwned == 2)
					pay = 50;
				else if (numRailroadsOwned == 3)
					pay = 100;
				else if (numRailroadsOwned == 4)
					pay = 200;
				
				if (!game.currentTurn().getName().equals(game.spaces[newLoc].getOwner().getName()))
				{
					JOptionPane.showMessageDialog(null, "You landed on " +
							game.spaces[newLoc].getOwner().getName() + "'s Jake meme. Pay $" +pay);
					game.currentTurn().subtractMoney(pay);
				}
			}
		}
		else if (game.spaces[newLoc] instanceof TaxSpace)
		{
			if (game.spaces[newLoc].getName().equals("Review Questions"))
			{
				JOptionPane.showMessageDialog(null, "Oh shucks! Review Questions were due. Pay $200.", "Review Questions", JOptionPane.ERROR_MESSAGE);
				game.currentTurn().subtractMoney(200);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Oh no, you failed the writing portion of the test... again. Pay $100.", "Writing Portion of Test", JOptionPane.ERROR_MESSAGE);
				game.currentTurn().subtractMoney(100);
			}
		}
		else if (game.spaces[newLoc] instanceof UtilitySpace)
		{
			if (!((game.spaces[newLoc]).isOwned()))
				buyDialog(game.spaces[newLoc]);
			else
			{
				utilityDialog(game.spaces[newLoc].getOwner());
			}
		}
		else if (game.spaces[newLoc] instanceof ChanceSpace)
		{
			int rnd1 = (int)(Math.random()*2); //1= get money, 2= lose money
			int rnd2 = (int)(Math.random()*151 + 50); //amt lose/gained
			if (rnd1 == 0)
			{
				game.currentTurn().addMoney(rnd2);
				JOptionPane.showMessageDialog(null, "Hey, you got $" + rnd2 + "!!", "Math.Random", JOptionPane.QUESTION_MESSAGE);
			}
			else
			{
				game.currentTurn().subtractMoney(rnd2);
				JOptionPane.showMessageDialog(null, "Oh no, you lost $" + rnd2 + "!!", "Math.Random", JOptionPane.QUESTION_MESSAGE);
			}
		}
		else if (game.spaces[newLoc] instanceof CommunityChestSpace)
		{
			int amt = (int)(Math.random()*51 + 50); //amt lose/gained
			game.currentTurn().addMoney(amt);
			JOptionPane.showMessageDialog(null, "Mrs. P finally graded your program. You got a " + amt + "!", "Programs Graded", JOptionPane.QUESTION_MESSAGE);
		}
		else if (newLoc == 30) //landed on go to jail space
		{
			JOptionPane.showMessageDialog(null, "You were caught by the hall monitor. Go straight to class.");
			game.currentTurn().sendToJail();
			refresh();
		}
		startTurn(0);
		if (game.currentTurn().getMoney() < 0)
		{
			JOptionPane.showMessageDialog(null, "Oh no! You went bankrupt. You lose.");
			game.playerBankrupt(game.currentTurn().getIndex());
			if (game.getPlayers().length == 1)
			{
				winDialog();
			}
			else
			{
				b.setPlayerArr(game.getPlayers());
				refresh();
				game.nextTurn();
				startTurn();
			}
		}
	}
	
	public void winDialog()
	{
		final JDialog dialog = new JDialog();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL); //must choose one
		dialog.setResizable(false);
		dialog.setTitle("Winner!");
		dialog.setLayout(new FlowLayout());
		dialog.setSize(300,100);
		dialog.setLocationRelativeTo(null);
		
		Player winner = game.getPlayers()[0];
		JLabel winnerlbl = new JLabel(winner.getName() + " won Meme-opoly!");
		winnerlbl.setFont(new Font("Consolas", Font.BOLD, 20));
		dialog.add(winnerlbl);
		
		JButton okay = new JButton("Okay");
 	    okay.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				about();
				menuItems[1].setEnabled(false);
				refresh();
				dialog.dispose();
			}
		});
 	    dialog.add(okay);
 	    dialog.repaint();
 	    dialog.validate();
		dialog.setVisible(true);
	}
	
	public void jailDialog()
	{
		final JDialog dialog = new JDialog();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL); //must choose one
		dialog.setResizable(false);
		dialog.setTitle("Landed on Utility");
		dialog.setLayout(null);
		dialog.setSize(370,90);
		dialog.setLocationRelativeTo(null);
		
		final JPanel btnPnl = new JPanel();
		btnPnl.setSize(350,50);
		btnPnl.setLocation(10,10);
		btnPnl.setBackground(new Color(205,231,208));
		JLabel tries = new JLabel("Roll Tries Left: " + (3 - game.currentTurn().numJailTries()));
		tries.setFont(new Font("Consolas", Font.BOLD, 18));
		btnPnl.add(tries);
		
		final JPanel dicePnl = new JPanel();
		dicePnl.setSize(350,150);
		dicePnl.setLocation(10,10);
		dicePnl.setBackground(new Color(205,231,208));
		
		final JButton roll = new JButton("Try roll");
		final JButton bail = new JButton("Pay bail");
 	    bail.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				if (game.currentTurn().getMoney() < 50)
					JOptionPane.showMessageDialog(null, "You don't have enough money to pay for bail.");
				else
				{
					JOptionPane.showMessageDialog(null, "You paid $50 for bail.");
					game.currentTurn().subtractMoney(50);
					game.currentTurn().bailOutofJail();
					dialog.dispose();
	   				refresh();
				}
			}
		});
		roll.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt)
			{
				dialog.setSize(370,190);
				dialog.remove(btnPnl);
				dialog.add(dicePnl);
		    	Random rng = new Random();
		    	int diceVal1 = (rng.nextInt(6) + 1);
		    	int diceVal2 = (rng.nextInt(6) + 1);
		    	Image dimg1 = null;
		    	Image dimg2 = null;
		    	try 
		    	{
		    		dimg1 = ImageIO.read(ResourceLoader.load("img/" + diceVal1 + ".GIF"));
		    		dimg2 = ImageIO.read(ResourceLoader.load("img/" + diceVal2 + ".GIF"));
		    	}
		    	catch (IOException e) {
		    		e.printStackTrace();
		    	}
		    	dicePnl.add(new JLabel(new ImageIcon(dimg1)));
		    	dicePnl.add(new JLabel(new ImageIcon(dimg2)));
		    	
		    	dialog.repaint();
		    	dialog.validate();
		    	if (diceVal1 == diceVal2)
		    	{
		    		JLabel lbl1 = new JLabel("Congrats, you rolled doubles!");
		    		JLabel lbl2 = new JLabel("You're free to go!");
		    		lbl1.setFont(new Font("Consolas", Font.PLAIN, 16));
					lbl2.setFont(new Font("Consolas", Font.PLAIN, 16));
					dicePnl.add(lbl1); dicePnl.add(lbl2);
		
					JButton okay = new JButton("Okay");
			    	   okay.addActionListener(new ActionListener(){
			   			public void actionPerformed(ActionEvent evt){
			   				game.currentTurn().bailOutofJail();
			   				dialog.dispose();
			   				refresh();
			   			}
			   		});
			    	dicePnl.add(okay);
			    	dialog.repaint();
			    	dialog.validate();
		    	}
		    	else
		    	{
		    		JLabel lbl1 = new JLabel("Uh oh, you didn't roll a doubles!");
		    		JLabel lbl2 = new JLabel("You're stuck in jail.");
		    		lbl1.setFont(new Font("Consolas", Font.PLAIN, 16));
					lbl2.setFont(new Font("Consolas", Font.PLAIN, 16));
					dicePnl.add(lbl1); dicePnl.add(lbl2);
					
					JButton okay = new JButton("Okay");
			    	   okay.addActionListener(new ActionListener(){
			   			public void actionPerformed(ActionEvent evt){
			   				game.currentTurn().failedJailTry();
			   				dialog.dispose();
			   				refresh();
			   			}
			   		});
			    	dicePnl.add(okay);
			    	dialog.repaint();
			    	dialog.validate();
		    	}
			}
		});
		if (game.currentTurn().numJailTries() < 3)
			btnPnl.add(roll);
		btnPnl.add(bail);
		
		dialog.add(btnPnl);
		dialog.setVisible(true);
	}
	
	public void utilityDialog(final Player p)
	{
		final JDialog dialog = new JDialog();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL); //must choose one
		dialog.setResizable(false);
		dialog.setTitle("Landed on Utility");
		dialog.setLayout(null);
		dialog.setSize(350,120);
		dialog.setLocationRelativeTo(null);
		
		final JPanel dicePnl = new JPanel();
		dicePnl.setSize(330,80);
		dicePnl.setLocation(10,10);
		dicePnl.setBackground(new Color(205,231,208));
		JButton roll = new JButton("Roll");
		roll.setLocation(150,300);
		dicePnl.add(roll);
		
		roll.addActionListener(new ActionListener(){
		       public void actionPerformed(ActionEvent evt)
		       {
		    	   dialog.setSize(350,260);
		    	   dicePnl.removeAll();
		    	   Random rng = new Random();
		    	   int diceVal1 = (rng.nextInt(6) + 1);
		    	   int diceVal2 = (rng.nextInt(6) + 1); 
		    	   Image dimg1 = null;
		    	   Image dimg2 = null;
		    	   try 
		    	   {
		    		   dimg1 = ImageIO.read(ResourceLoader.load("img/" + diceVal1 + ".GIF"));
		    		   dimg2 = ImageIO.read(ResourceLoader.load("img/" + diceVal2 + ".GIF"));
		    	   }
		    	   catch (IOException e) {
		    		   e.printStackTrace();
		    	   }
		    	   dicePnl.add(new JLabel(new ImageIcon(dimg1)));
		    	   dicePnl.add(new JLabel(new ImageIcon(dimg2)));
		    	   
		    	   JPanel btnPnl = new JPanel();
		   		   btnPnl.setSize(330,130);
		   		   btnPnl.setLocation(10,100);
		   		   btnPnl.setBackground(new Color(205,231,208));
		    	   int rent;
		    	   String txt;
		    	   String owns;
		    	   if (p.numUtilities() == 1)
		    	   {
		    		   txt = "4 * (";
		    		   rent = 4 * (diceVal1 + diceVal2);
		    		   owns = p.getName() + " owns one utility.";
		    	   }
		    	   else
		    	   {
		    		   txt = "10 * ";
		    		   rent = 10 * (diceVal1 + diceVal2);
		    		   owns = p.getName() + " owns both utilities.";
		    	   }
		    	   final int frent = rent;
		    	   txt += diceVal1  + " + " + diceVal2 + ") = $" + rent;
		    	   
		    	   JLabel lbl1 = new JLabel(owns);
		    	   lbl1.setFont(new Font("Consolas", Font.PLAIN, 20));
		    	   btnPnl.add(lbl1);
		    	   JLabel lbl2 = new JLabel(txt);
		    	   lbl2.setFont(new Font("Consolas", Font.BOLD, 20));
		    	   btnPnl.add(lbl2);
		    	   JLabel lbl3 = new JLabel("You owe " + p.getName() + " $" + rent + ".");
		    	   lbl3.setFont(new Font("Consolas", Font.BOLD, 22));
		    	   btnPnl.add(lbl3);
		    	   
		    	   JButton okay = new JButton("Okay");
		    	   okay.addActionListener(new ActionListener(){
		   			public void actionPerformed(ActionEvent evt){
		   				game.currentTurn().subtractMoney(frent);
		   				p.addMoney(frent);
		   				dialog.dispose();
		   			}
		   			});
		    	   btnPnl.add(okay);
		    	   dialog.add(btnPnl);
		    	   dialog.repaint();
		    	   dialog.validate();
		      }
		});
		dialog.add(dicePnl);
		dialog.setVisible(true);
	}
	
	public void tradeDialog()
	{
		final JDialog dialog = new JDialog();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL); //must choose one
		dialog.setResizable(false);
		dialog.setTitle("Trade Properties");
		dialog.setLayout(null);
		dialog.setSize(600,100);
		dialog.setLocationRelativeTo(null);
		
		final String[] names = new String[game.getPlayers().length];
		for (int i = 0; i < names.length; i++)
			names[i] = game.playerAt(i).getName();
			
		final JComboBox<String> playerList = new JComboBox<String>(names);
		playerList.setSelectedIndex(0);
		final JPanel btnPnl = new JPanel();
		btnPnl.setBackground(new Color(205,231,208));
		btnPnl.setSize(600,40);
		btnPnl.setLocation(0,0);
		
		final JPanel p1Pnl = new JPanel();
		final JPanel p2Pnl = new JPanel();
		p1Pnl.setSize(250,150);
		p2Pnl.setSize(250,150);
		p1Pnl.setLocation(0,40);
		p2Pnl.setLocation(350,40);
		p1Pnl.setBackground(new Color(205,231,208));
		p2Pnl.setBackground(new Color(205,231,208));
		
		final JPanel tradePnl = new JPanel();
		tradePnl.setSize(100,150);
		tradePnl.setLocation(250, 40);
		tradePnl.setBackground(new Color(205,231,208));
		
		final JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				dialog.dispose();
			}
		});
		final JButton tradeWithBtn = new JButton("Trade with");
		tradeWithBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				if (!names[playerList.getSelectedIndex()].equals(game.currentTurn().getName()))
				{
					btnPnl.removeAll();
					p1Pnl.removeAll();
					p2Pnl.removeAll();
						
					dialog.setSize(600,210);
					
					final Player p1 = game.currentTurn();
					final Player p2 = game.playerAt(playerList.getSelectedIndex());
					p1Pnl.setBorder(BorderFactory.createTitledBorder(null, p1.getName() + " wants to trade... ",
							TitledBorder.LEFT, TitledBorder.TOP, new Font("Consolas", Font.BOLD, 14), Color.BLACK));
					p2Pnl.setBorder(BorderFactory.createTitledBorder(null, "for " + p2.getName() + "'s...",
							TitledBorder.LEFT, TitledBorder.TOP, new Font("Consolas", Font.BOLD, 14), Color.BLACK));
			
					final String[] p1Spaces = new String[p1.getSpaces().size()+1];
					final String[] p2Spaces = new String[p2.getSpaces().size()+1];
					for (int i = 0; i < p1Spaces.length-1; i++)
						p1Spaces[i] = p1.spaceAt(i).getName();
					for (int i = 0; i < p2Spaces.length-1; i++)
						p2Spaces[i] = p2.spaceAt(i).getName();
					p1Spaces[p1Spaces.length-1] = "-None selected-";
					p2Spaces[p2Spaces.length-1] = "-None selected-";
					final JComboBox<String> p1SpaceList = new JComboBox<String>(p1Spaces);
					final JComboBox<String> p2SpaceList = new JComboBox<String>(p2Spaces);
					p1SpaceList.setSelectedIndex(p1Spaces.length-1);
					p2SpaceList.setSelectedIndex(p2Spaces.length-1);
					p1Pnl.add(p1SpaceList);
					p2Pnl.add(p2SpaceList);
				
					JLabel moneyLbl1 = new JLabel("Trade Money: $");
					moneyLbl1.setFont(new Font("Consolas", Font.BOLD, 14));
					p1Pnl.add(moneyLbl1);
					
					final JTextField moneyAmt = new JFormattedTextField("0");
					moneyAmt.setFont(new Font("Consolas", Font.PLAIN, 18));
					moneyAmt.setColumns(6);
					p1Pnl.add(moneyAmt);
					
					JButton tradeForBtn = new JButton("Trade");
					tradeForBtn.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent evt){
							if (p2SpaceList.getSelectedIndex() == p2Spaces.length-1)
								JOptionPane.showMessageDialog(null, "You must choose something to trade for.");
							else
							{
								boolean good = true;
								if (moneyAmt.getText().length() < 1)
									good = false;
								else
								{
									for (int i = 0; i < moneyAmt.getText().length(); i++)
									{
										int val = (int)moneyAmt.getText().charAt(i);
										if (val < 48 || val > 57)
											good = false;
									}
								}
								if (!good)
									JOptionPane.showMessageDialog(null, "Please enter a valid number to trade or 0.");
								else
								{
									final int amt = Integer.parseInt(moneyAmt.getText());
									if (amt > p1.getMoney())
										JOptionPane.showMessageDialog(null, "You do not have that amount of money to trade.");
									else if (p1SpaceList.getSelectedIndex() == p1Spaces.length-1 && amt == 0)
										JOptionPane.showMessageDialog(null, "You must have something to trade.");
									else
									{
										final JButton acceptBtn = new JButton("Accept Trade");
										final JButton rejectBtn = new JButton("Reject Trade");
										acceptBtn.addActionListener(new ActionListener(){
											public void actionPerformed(ActionEvent evt){
												JOptionPane.showMessageDialog(null, "You successfully traded with " + p2.getName());
												p1.getTrade(p2.spaceAt(p2SpaceList.getSelectedIndex()));
												p2.tradeSpace(p2SpaceList.getSelectedIndex());
												if (p1SpaceList.getSelectedIndex() != p1Spaces.length - 1)
												{
													p2.getTrade(p1.spaceAt(p1SpaceList.getSelectedIndex()));
													p1.tradeSpace(p1SpaceList.getSelectedIndex());
												}
												if (amt > 0)
												{
													p1.subtractMoney(amt);
													p2.addMoney(amt);
												}
												refresh();
												dialog.dispose();
											}
										});
										
										rejectBtn.addActionListener(new ActionListener(){
											public void actionPerformed(ActionEvent evt){
												JOptionPane.showMessageDialog(null, p2.getName() + " rejects your trade offer.");
												p2Pnl.remove(acceptBtn);
												p2Pnl.remove(rejectBtn);
												dialog.repaint();
												dialog.validate();
											}
										});
										p2Pnl.add(acceptBtn);
										p2Pnl.add(rejectBtn);
										dialog.repaint();
										dialog.validate();
									}
								}
							}
						}
					});
					JButton backBtn = new JButton("Go Back");
					backBtn.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent evt){
							dialog.setSize(600,60);
							btnPnl.removeAll();
							btnPnl.add(playerList);
							btnPnl.add(tradeWithBtn);
							btnPnl.add(cancelBtn);
							dialog.repaint();
							dialog.validate();
						}
					});
					tradePnl.removeAll();
					tradePnl.add(tradeForBtn);
					btnPnl.add(backBtn);
					
					dialog.repaint();
					dialog.validate();
				}
			}
		});
		
		btnPnl.add(playerList);
		btnPnl.add(tradeWithBtn);
		btnPnl.add(cancelBtn);
		dialog.add(btnPnl);
		dialog.add(p1Pnl);
		dialog.add(p2Pnl);
		dialog.add(tradePnl);
		dialog.setVisible(true);
	}
	
	public void propertiesDialog()
	{
		final JDialog dialog = new JDialog();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL); //must choose one
		dialog.setResizable(false);
		dialog.setTitle(game.currentTurn().getName() + ": $" + game.currentTurn().getMoney());
		dialog.setLayout(null);
		dialog.setLocationRelativeTo(null);
		final JButton backBtn = new JButton("Go Back");
		backBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				dialog.dispose();
			}
		});
		
		final JPanel btnPnl = new JPanel();
		btnPnl.setLocation(0,0);
		btnPnl.setBackground(new Color(205,231,208));
		
		if (game.currentTurn().numProperties() > 0)
		{	
			final String[] colors = new String[]{"Brown", "Light Blue", "Magenta", "Orange",
							 "Red", "Yellow", "Green", "Blue"};
			final JComboBox<String> colorList = new JComboBox<String>(colors);
			
			final JPanel blah = new JPanel();
			blah.setLocation(0,40);
			blah.setBackground(new Color(205,231,208));
			
			colorList.setSelectedIndex(0);
			btnPnl.add(colorList);
			btnPnl.add(backBtn);
			dialog.add(btnPnl);
			dialog.add(blah);
			
			dialog.repaint();
			dialog.validate();
			
			final Vector<UpgradePropertyPanel> selectedProperty = new Vector<UpgradePropertyPanel>();
			
			colorList.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt)
				{
					blah.setLayout(null);
					blah.removeAll();
					int w=0;
					cntr = 0;
					selectedProperty.removeAllElements();
					for (int i = 0; i < game.currentTurn().numProperties(); i++)
					{
						if (game.currentTurn().propertyAt(i).color().equals(colors[colorList.getSelectedIndex()]))
						{
							cntr++;
							selectedProperty.add(new UpgradePropertyPanel(game.currentTurn().propertyAt(i)));
							selectedProperty.elementAt(selectedProperty.size()-1).setLocation((cntr-1)*290 +  10, 0);
							blah.add(selectedProperty.elementAt(selectedProperty.size()-1));
						}
					}
					if (cntr == 0)
					{
						dialog.setSize(300,150);
						btnPnl.setSize(300,40);
						w=300;
						blah.setLayout(new FlowLayout());
						blah.add(new JLabel("You don't own any properties of this color."));
					}
					else if (cntr == 1)
					{
						dialog.setSize(300,340);
						btnPnl.setSize(300,40);
						w=300;
					}
					else if (cntr == 2)
					{
						dialog.setSize(590,340);
						btnPnl.setSize(590,40);
						w=590;
					}
					else if (cntr == 3)
					{
						dialog.setSize(880,340);
						btnPnl.setSize(880,40);
						w=880;
					}
					blah.setSize(w,250);
					dialog.repaint();
					dialog.validate();
					
					if (((colorList.getSelectedIndex() == 0 || colorList.getSelectedIndex() == 7)&&(cntr == 2))||
							((colorList.getSelectedIndex()>0 && colorList.getSelectedIndex()<7)&&(cntr==3)))
					{
						dialog.setSize(w,420);
						blah.setSize(w,340);
						for (int i = 0; i < cntr; i++)
						{
							final PropertySpace s = selectedProperty.elementAt(i).getSpace();
							final JButton buyHouse1 = new JButton("Buy 1 House");
							final JButton buyHouse2 = new JButton("Buy 2 Houses");
							final JButton buyHouse3 = new JButton("Buy 3 Houses");
							final JButton buyHouse4 = new JButton("Buy 4 Houses");
							final JButton buyHotel = new JButton("Buy Hotel");
							
							if (s.numHotels() == 1)
							{
								buyHouse1.setEnabled(false);
								buyHouse2.setEnabled(false);
								buyHouse3.setEnabled(false);
								buyHouse4.setEnabled(false);
								buyHotel.setEnabled(false);
							}
							else if (s.numHouses() == 4)
							{
								buyHouse1.setEnabled(false);
								buyHouse2.setEnabled(false);
								buyHouse3.setEnabled(false);
								buyHouse4.setEnabled(false);
								buyHotel.setEnabled(true);
							}
							else if (s.numHouses() == 3)
							{
								buyHouse1.setEnabled(true);
								buyHouse2.setEnabled(false);
								buyHouse3.setEnabled(false);
								buyHouse4.setEnabled(false);
								buyHotel.setEnabled(false);
							}
							else if (s.numHouses() == 2)
							{
								buyHouse1.setEnabled(true);
								buyHouse2.setEnabled(true);
								buyHouse3.setEnabled(false);
								buyHouse4.setEnabled(false);
								buyHotel.setEnabled(false);
							}
							else if (s.numHouses() == 1)
							{
								buyHouse1.setEnabled(true);
								buyHouse2.setEnabled(true);
								buyHouse3.setEnabled(true);
								buyHouse4.setEnabled(false);
								buyHotel.setEnabled(false);
							}
							else if (s.numHouses() == 0)
							{
								buyHouse1.setEnabled(true);
								buyHouse2.setEnabled(true);
								buyHouse3.setEnabled(true);
								buyHouse4.setEnabled(true);
								buyHotel.setEnabled(false);
							}
							
							
							buyHouse1.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent evt){
									int c = JOptionPane.showConfirmDialog (null, "Buy 1 house for $" + s.housePrice() + "?",
											"Confirm Buy",JOptionPane.YES_NO_OPTION);
									if (c == JOptionPane.YES_OPTION)
									{
										game.currentTurn().subtractMoney(s.housePrice());
										if (game.currentTurn().getMoney() > -1)
										{
											s.addHouse();
											dialog.setTitle(game.currentTurn().getName() + ": $" + game.currentTurn().getMoney());
											if (s.numHouses() == 4)
											{
												buyHouse1.setEnabled(false);
												buyHouse2.setEnabled(false);
												buyHouse3.setEnabled(false);
												buyHouse4.setEnabled(false);
												buyHotel.setEnabled(true);
											}
											else if (s.numHouses() == 3)
											{
												buyHouse1.setEnabled(true);
												buyHouse2.setEnabled(false);
												buyHouse3.setEnabled(false);
												buyHouse4.setEnabled(false);
												buyHotel.setEnabled(false);
											}
											else if (s.numHouses() == 2)
											{
												buyHouse1.setEnabled(true);
												buyHouse2.setEnabled(true);
												buyHouse3.setEnabled(false);
												buyHouse4.setEnabled(false);
												buyHotel.setEnabled(false);
											}
											else if (s.numHouses() == 1)
											{
												buyHouse1.setEnabled(true);
												buyHouse2.setEnabled(true);
												buyHouse3.setEnabled(true);
												buyHouse4.setEnabled(false);
												buyHotel.setEnabled(false);
											}
											startTurn(false);
										}
										else
										{
											game.currentTurn().addMoney(s.housePrice());
											JOptionPane.showMessageDialog(null, "You do not have enough money");
										}
									}
								}
							});
							buyHouse2.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent evt){
									int c = JOptionPane.showConfirmDialog (null, "Buy 2 houses for $" + s.housePrice()*2 + "?",
											"Confirm Buy",JOptionPane.YES_NO_OPTION);
									if(c == JOptionPane.YES_OPTION)
									{
										game.currentTurn().subtractMoney(s.housePrice()*2);
										if (game.currentTurn().getMoney() > -1)
										{
											s.addHouse(); s.addHouse();
											dialog.setTitle(game.currentTurn().getName() + ": $" + game.currentTurn().getMoney());
											if (s.numHouses() == 4)
											{
												buyHouse1.setEnabled(false);
												buyHouse2.setEnabled(false);
												buyHouse3.setEnabled(false);
												buyHouse4.setEnabled(false);
												buyHotel.setEnabled(true);
											}
											else if (s.numHouses() == 3)
											{
												buyHouse1.setEnabled(true);
												buyHouse2.setEnabled(false);
												buyHouse3.setEnabled(false);
												buyHouse4.setEnabled(false);
												buyHotel.setEnabled(false);
											}
											else if (s.numHouses() == 2)
											{
												buyHouse1.setEnabled(true);
												buyHouse2.setEnabled(true);
												buyHouse3.setEnabled(false);
												buyHouse4.setEnabled(false);
												buyHotel.setEnabled(false);
											}
											startTurn(false);
										}
										else
										{
											game.currentTurn().addMoney(s.housePrice()*2);
											JOptionPane.showMessageDialog(null, "You do not have enough money");
										}
									}
								}
							});
							buyHouse3.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent evt){
									int c = JOptionPane.showConfirmDialog (null, "Buy 3 houses for $" + s.housePrice()*3 + "?",
											"Confirm Buy",JOptionPane.YES_NO_OPTION);
									if(c == JOptionPane.YES_OPTION)
									{
										game.currentTurn().subtractMoney(s.housePrice()*3);
										if (game.currentTurn().getMoney() > -1)
										{
											s.addHouse(); s.addHouse(); s.addHouse();
											dialog.setTitle(game.currentTurn().getName() + ": $" + game.currentTurn().getMoney());
											if (s.numHouses() == 4)
											{
												buyHouse1.setEnabled(false);
												buyHouse2.setEnabled(false);
												buyHouse3.setEnabled(false);
												buyHouse4.setEnabled(false);
												buyHotel.setEnabled(true);
											}
											else if (s.numHouses() == 3)
											{
												buyHouse1.setEnabled(true);
												buyHouse2.setEnabled(false);
												buyHouse3.setEnabled(false);
												buyHouse4.setEnabled(false);
												buyHotel.setEnabled(false);
											}
											startTurn(false);
										}
										else
										{
											game.currentTurn().addMoney(s.housePrice()*3);
											JOptionPane.showMessageDialog(null, "You do not have enough money");
										}
									}
								}
							});
							buyHouse4.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent evt){
									int c = JOptionPane.showConfirmDialog (null, "Buy 4 houses for $" + s.housePrice()*4 + "?",
											"Confirm Buy",JOptionPane.YES_NO_OPTION);
									if(c == JOptionPane.YES_OPTION)
									{
										game.currentTurn().subtractMoney(s.housePrice()*4);
										if (game.currentTurn().getMoney() > -1)
										{
											s.addHouse(); s.addHouse(); s.addHouse(); s.addHouse();
											dialog.setTitle(game.currentTurn().getName() + ": $" + game.currentTurn().getMoney());
											if (s.numHouses() == 4)
											{
												buyHouse1.setEnabled(false);
												buyHouse2.setEnabled(false);
												buyHouse3.setEnabled(false);
												buyHouse4.setEnabled(false);
												buyHotel.setEnabled(true);
											}
											startTurn(false);
										}
										else
										{
											game.currentTurn().addMoney(s.housePrice()*4);
											JOptionPane.showMessageDialog(null, "You do not have enough money");
										}
									}
								}
							});
							buyHotel.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent evt){
									int c = JOptionPane.showConfirmDialog (null, "Trade houses and buy hotel for $" + s.hotelPrice() + "?",
											"Confirm Buy",JOptionPane.YES_NO_OPTION);
									if(c == JOptionPane.YES_OPTION)
									{
										game.currentTurn().subtractMoney(s.hotelPrice());
										if (game.currentTurn().getMoney() > -1)
										{
											s.addHotel();
											dialog.setTitle(game.currentTurn().getName() + ": $" + game.currentTurn().getMoney());
											buyHouse1.setEnabled(false);
											buyHouse2.setEnabled(false);
											buyHouse3.setEnabled(false);
											buyHouse4.setEnabled(false);
											buyHotel.setEnabled(false);
											startTurn(false);
										}
										else
										{
											game.currentTurn().addMoney(s.hotelPrice());
											JOptionPane.showMessageDialog(null, "You do not have enough money");
										}
									}
								}
							});
							
							selectedProperty.elementAt(i).setSize(280,330);
							selectedProperty.elementAt(i).add(buyHouse1);
							selectedProperty.elementAt(i).add(buyHouse2);
							selectedProperty.elementAt(i).add(buyHouse3);
							selectedProperty.elementAt(i).add(buyHouse4);
							selectedProperty.elementAt(i).add(buyHotel);
						}
					}
					refresh();
					dialog.repaint();
					dialog.validate();
				}});
			colorList.setSelectedIndex(0);
		}
		else
		{
			dialog.setSize(250,150);
			btnPnl.setSize(250,150);
			btnPnl.add(new JLabel("You don't own any color properties."));
			btnPnl.add(backBtn);
			dialog.add(btnPnl);
		}
		dialog.repaint();
		dialog.validate();
		dialog.setVisible(true);
	}
	
	public void buyDialog(final Space space) 
	{
		final JDialog dialog = new JDialog();
	    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    dialog.setModalityType(JDialog.ModalityType.APPLICATION_MODAL); //must choose one
	    dialog.setResizable(false);
	    dialog.setTitle("Buy the meme?");
	    dialog.setSize(220,330);
	    dialog.setLocationRelativeTo(null);
	    dialog.setLayout(new FlowLayout());
	    dialog.add(new JLabel(space.getIcon())); ///show property card
	    dialog.add(new JLabel("Buy for $" + space.getPrice() + "?"));
	    JButton buyBtn = new JButton("Buy");
	    buyBtn.addActionListener(new ActionListener(){
	       public void actionPerformed(ActionEvent evt){
	    	   if (game.currentTurn().getMoney() < space.getPrice())
	    		   JOptionPane.showMessageDialog(null, "You don't have enough money.");
	        	else
	        	{
	        		game.currentTurn().buyProperty(space);
	        		startTurn(0);
	        		dialog.dispose();
	        	}
	        }
		});
	        
	    JButton noBtn = new JButton("No thanks");
	    noBtn.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent evt){
	        	dialog.dispose();
	        }
		});
	    dialog.add(buyBtn);
	    dialog.add(noBtn);
	    dialog.setVisible(true);
	}
	
	public void refresh()
	{
		getContentPane().repaint();
		getContentPane().validate();
	}
}

class UpgradePropertyPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	PropertySpace s;
	UpgradePropertyPanel(PropertySpace s)
	{
		setSize(280,237);
		setBorder(BorderFactory.createTitledBorder(null, "",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Consolas", Font.BOLD, 14), Color.BLACK));
		this.s = s;
		setBackground(new Color(205,231,208));
		add(new JLabel(s.getIcon()));
	}
	public PropertySpace getSpace(){
		return s;
	}
}