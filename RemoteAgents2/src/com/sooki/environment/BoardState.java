package com.sooki.environment;

public class BoardState {
	
	 public int n;
	 public int  board[];
	 public  boolean boardSt[];


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
