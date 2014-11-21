package com.sooki.environment;

public class BoardConfiguration {
	
	
	private static BoardConfiguration bc;
	public int n;
	public int board[];
	public boolean boardState[];
	private BoardConfiguration(int len)
	{
		n = len;
		board = new int[n];
		boardState = new boolean[n];
		int k=0;
		for(int i=0;i<n;i++)
		{
			if(k<n/2)
			{
				board[i] = k;
			}
			else
			{
				k = 0;
			}
			boardState[i] = false;
			k++;
				
		}
	}
	
	public static BoardConfiguration getInstance(int n)
	{
		if(bc == null)
		{
			bc =  new BoardConfiguration(n);
		}
		return bc;
	}
	
	public static void deleteBoard()
	{
		bc = null;
	}
	
	public static BoardConfiguration getInstance()
	{
		return bc;
	}
	
	public int[] getBoard()
	{
		return board;
	}
	
	public int[] getRevealed(int a, int b)
	{
		int x = board[a];
		int y = board[b];
		int arr [] = new int[2];
		arr[0] = x;
		arr[1] = y;
		if(x==y)
		{
			boardState[a] = true;
			boardState[b] = true;
		}
		return arr;
	} 
	
	public boolean[] getStates()
	{
	
		return boardState;
	} 
	
 public  void printboard(int n)
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
 
 public int getValueAt(int a)
 {
	 return this.board[a];
 }
 


}
