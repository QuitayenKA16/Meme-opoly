package minesweeper;

public class MinesweeperGame 
{
	public int[][] board;
	//0 = empty;
	//1,2,3,4,5,6,7,8 = numMines touching;
	//10 = has mine;
	
	public int numRows, numCols, numMines;
	
	MinesweeperGame(int numRows, int numCols, int numMines)
	{
		this.numRows = numRows;
		this.numCols = numCols;
		this.numMines = numMines;
		board = new int[numRows][numCols];
	}
	
	public void firstClick(int row, int col)
	{
		addMines(row, col);
		printBoard();
		fillBoard();
		printBoard();
	}
	
	public void fillBoard()
	{
		int numMines = 0;
		for (int row = 0; row < numRows; row++)
			for (int col = 0 ; col < numCols; col++)
				if (board[row][col] != 10)
				{
					numMines = 0;
					if (inBounds(row-1, col-1) && board[row-1][col-1] == 10)
						numMines++;
					if (inBounds(row-1, col) && board[row-1][col] == 10)
						numMines++;
					if (inBounds(row-1, col+1) && board[row-1][col+1] == 10)
						numMines++;
					
					if (inBounds(row, col-1) && board[row][col-1] == 10)
						numMines++;
					if (inBounds(row, col+1) && board[row][col+1] == 10)
						numMines++;
					
					if (inBounds(row+1, col-1) && board[row+1][col-1] == 10)
						numMines++;
					if (inBounds(row+1, col) && board[row+1][col] == 10)
						numMines++;
					if (inBounds(row+1, col+1) && board[row+1][col+1] == 10)
						numMines++;
					board[row][col] = numMines;
				}
	}
	
	public boolean inBounds(int row, int col){
		return (row > -1 && row < numRows && col > -1 && col < numCols);
	}
	
	public void addMines(int firstRow, int firstCol)
	{
		boolean empty;
		int row=0, col=0;
		for (int i = 0; i < numMines; i++)
		{
			empty = false;
			while (!empty)
			{
				row = (int)(Math.random() * numRows);
				col = (int)(Math.random() * numCols);
				if (row == firstRow-1 && col == firstCol-1 ||
					row == firstRow-1 && col == firstCol ||
					row == firstRow-1 && col == firstCol+1 ||
					
					row == firstRow && col == firstCol-1 ||
					row == firstRow && col == firstCol ||
					row == firstRow && col == firstCol+1 ||
					
					row == firstRow+1 && col == firstCol-1 ||
					row == firstRow+1 && col == firstCol||
					row == firstRow+1 && col == firstCol+1)
					empty = false;
				else if (board[row][col] == 0)
					empty = true;
			}
			board[row][col] = 10;
		}
	}
	
	public void printBoard()
	{
		System.out.println("-------------------------------------------------------------");
		for (int row = 0; row < numRows; row++)
		{
			String txt = "";
			for (int col = 0; col < numCols; col++)
			{
				if (board[row][col] == 0)
					txt = " ";
				else if (board[row][col] == 10)
					txt = "•";
				else
					txt = Integer.toString(board[row][col]);
				System.out.print("[" + txt + "]");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public int getSpot(int row, int col)
	{
		return board[row][col];
	}
	
}
