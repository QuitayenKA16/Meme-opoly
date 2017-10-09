package minesweeper;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MinesweeperGUI extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	public MinesweeperGame game;
	public Button[][] board;
	public JPanel boardPnl = new JPanel();
	public int numRows, numCols, numMines, clickedRow, clickedCol;
	public boolean firstClick = true;
	
	MinesweeperGUI()
	{
		setTitle("Minesweeper");
		setLocation(20,20);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(null);;
		setVisible(true);
		
		numRows = 20;
		numCols = 20;
		numMines = 50;
		setSize(numCols*24, (numRows+1)*24);
		game = new MinesweeperGame(numRows, numCols, numMines);
		//game.firstClick(10, 10);
		board = new Button[numRows][numCols];
		
		boardPnl.setLayout(new GridLayout(numRows, numCols));
		boardPnl.setSize(numCols*24, numRows*24);
		boardPnl.setLocation(0,0);
		for (int row = 0; row < numRows; row++)
			for (int col = 0; col < numCols; col++)
			{
				board[row][col] = new Button(row, col);
				board[row][col].addActionListener(this);
				boardPnl.add(board[row][col]);
			}
		getContentPane().add(boardPnl);
		getContentPane().repaint();
		getContentPane().validate();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		for (int row = 0; row < numRows; row++)
			for (int col = 0; col < numCols; col++)
			{
				if (e.getSource() == board[row][col])
				{
					if (firstClick)
					{
						firstClick = false;
						game.firstClick(row, col);
						for (int r = 0; r < numRows; r++)
							for (int c = 0; c < numCols; c++)
							{
								board[r][c].setValue(game.getSpot(r, c));
							}
						recursion(row, col);
						
					}
					else if (game.getSpot(row, col) == 0)
						recursion(row, col);
					else if (game.getSpot(row,col) == 10)	
					{
						Image img = null;
						String path = "Images/red mine.jpg";
						try {
							img = ImageIO.read(ResourceLoader.load(path));
						} catch (IOException et) {
							et.printStackTrace();
						}
						Image smallImg = img.getScaledInstance(24, 24, Image.SCALE_FAST);
						board[row][col].setDisabledIcon(new ImageIcon(smallImg));
						showMines();
					}
					board[row][col].setEnabled(false);
				}
			}
	}
	
	public void recursion(int x, int y) {
		// first, check the bounds.  If it's out, leave
		if (x<0 || y<0 || x>=board.length || y>=board[0].length)
			return;
		
		if (!board[x][y].isEnabled())
			return;
		
		if (game.getSpot(x,y) == 0)
		{
			board[x][y].setEnabled(false);
			recursion(x - 1, y - 1);
			recursion(x - 1, y);
	        recursion(x - 1, y + 1);
	        recursion(x + 1, y);

	        recursion(x, y - 1);
	        recursion(x, y + 1);
	        recursion(x + 1, y - 1);
	        recursion(x + 1, y + 1);
		}
		else
		{
			board[x][y].setEnabled(false);
		}
	}

	
	public void showMines()
	{
		for (int row = 0; row < numRows; row++)
			for (int col = 0; col < numCols; col++)
			{
				if (game.getSpot(row, col) != 10 && board[row][col].isEnabled())
				{
					Image img = null;
					String path = "Images/button.jpg";
					try {
						img = ImageIO.read(ResourceLoader.load(path));
					} catch (IOException et) {
						et.printStackTrace();
					}
					Image smallImg = img.getScaledInstance(24, 24, Image.SCALE_FAST);
					board[row][col].setDisabledIcon(new ImageIcon(smallImg));
				}
				board[row][col].setEnabled(false);
			}
	}
}

class Button extends JButton
{
	private static final long serialVersionUID = 1L;
	int row, col;
	ImageIcon icon;
	
	Button(int row, int col)
	{
		this.row = row;
		this.col = col;
		
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		setSize(20,20);
		
		Image img = null;
		String path = "Images/button.jpg";
		try {
			img = ImageIO.read(ResourceLoader.load(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image smallImg = img.getScaledInstance(24, 24, Image.SCALE_FAST);
		setIcon(new ImageIcon(smallImg));
		setDisabledIcon(new ImageIcon(smallImg));
	}
	
	public void setValue(int value)
	{
		Image img = null;
		String path;
		if (value == 0)
			path = "Images/button clicked.jpg";
		else if (value == 10)
			path = "Images/mine.jpg";
		else
			path = "Images/" + value + ".jpg";
		try {
			img = ImageIO.read(ResourceLoader.load(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image smallImg = img.getScaledInstance(24, 24, Image.SCALE_FAST);
		setDisabledIcon(new ImageIcon(smallImg));
	}
}
