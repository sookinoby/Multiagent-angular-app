package com.sooki.environment;

public class BoardState {
	
	 public int n;
	 public int  board[];
	 public  boolean boardSt[];
	 public BoardState()
	 {
		 BoardConfiguration bc = BoardConfiguration.getInstance();
		 n = bc.n;
		 int board_current [] = new int[n];
		 board = new int[n];
		 boardSt = new boolean[n];
		
		 board_current = bc.getBoard();
		 boardSt = bc.getStates();
		
		 boardSt = bc.getStates();
		 for(int i=0;i<n;i++)
		 {
			 board[i] = -1;
		 }
		 for(int i=0;i<n;i++)
		 {
			if(boardSt[i] == true)
			{
				board[i] = board_current[i];
			}
		 }
		 
	 } 
	 
	 public static BoardState getState()
	 {
		 
		 BoardState bs = new BoardState();
		
		 return bs;
	 }
	 
	 public void printBoardState()
	 {
		 int width = (int)Math.sqrt(n);
		 for (int i=0;i < width; i++)
			{
				for (int j=0;j < width; j++)
				{
					System.out.print(this.board[i*width + j] + " ");
				}
					System.out.println("");
			}
		
	 }

}
