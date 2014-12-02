package com.sooki.environment;


public class Environment {
	static int n=36;
	static BoardConfiguration bc;
	public static void initialise()
	{
		 bc = BoardConfiguration.getInstance(n);
		/// bc.boardState[3]= true;
		// bc.boardState[11]= true;
		 
	}
	
	
	
	public static BoardState getBoardState(String agentId)
	{
		
		BoardState bs = BoardState.getState();
		return bs;
	}
	
	public static int[] seeCard(int a, int b)
	{
		return bc.getRevealed(a, b);
	}
	

	

	public static void main(String args[])
	{
		initialise();
		bc.printboard(n);
		BoardState b = Environment.getBoardState("test");
		b.printBoardState();
	
	}
		
		
}
	

