package com.sooki.behaviour;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Random;

import com.sooki.environment.BoardState;
import com.sooki.environment.Environment;
import com.sooki.memory.LimitedMemory;
import com.sooki.utility.Helper;

class GeneralGameBehaviour extends SimpleBehaviour implements CustomBehaviourInterface {
	Agent cur;
	int numberOfMovesMade = 0 ;
	int maxLieCount = 2;
	int currentLie = 0;
	Random generator;
	LimitedMemory primary;
	LimitedMemory secondary;
	ACLMessage msg ;
	AID r;
	int n;
	int numberScored = 0;
	boolean first = true;
	public GeneralGameBehaviour(Agent a,int n, String passingTokenAgentName) {
		super(a);
		this.cur = a;
		this.n = n;
		this.msg = new ACLMessage(ACLMessage.INFORM);
		this.r = new AID(passingTokenAgentName,AID.ISLOCALNAME);
		this.msg.addReceiver(r);
		
	//	memory = new Memory();
		this.primary = new LimitedMemory();
		this.secondary = new LimitedMemory();
		this.generator = new Random();
		this.generator.setSeed(0);
	}

	public BoardState percept() {

		BoardState b = Environment.getBoardState("test");
		return b;

	}

	public BoardState passToken() {
		return null;

	}

	public boolean isTokenObtained() {
		return true;
	}
	
	public String constructTruthMessage(int pos1,int val1,int pos2,int val2)
	{
		StringBuilder br = new StringBuilder();
		br.append(pos1 + "=" + val1 + ";" + pos2 + "=" + val2);
		return br.toString();
		
	}
	
	public String construtFalseMessage(int pos1,int val1,int pos2,int val2)
	{
		StringBuilder br = new StringBuilder();
		int random1 = generator.nextInt(n);
		int random2 = generator.nextInt(n);
		br.append(pos1 + "=" + random1 + ";" + pos2 + "=" + random2);
		return br.toString();
		
	}
	
	public boolean analyzeMessage(String content)
	{ 
		boolean isfaking = false;
		try {
		System.out.println("the content is"  + content);
		
		String posval [] = content.split(";");
		String me1[] = posval[0].split("=");
		String me2[] =  posval[1].split("=");
		
		int pos1 = Integer.parseInt(me1[0]);
		int pos2 = Integer.parseInt(me2[0]);
		int mem1 = Integer.parseInt(me1[1]);
		int mem2 = Integer.parseInt(me1[1]);
		
		secondary.add(pos1, mem1);
		secondary.add(pos2, mem2);
		isfaking = primary.isFaking(secondary);
		if(isfaking)
		{
			primary.removeBadMemory(secondary);
			System.out.println( cur.getLocalName() + " Says: Other agent lied to me");
		}
	}
	catch (Exception e)
	{
		System.out.println("Recieved invalid message");
	}
		return isfaking;
	}
	
	
	public boolean makeMove(int a, int b,boolean faking) {
		int re[] = Environment.seeCard(a, b);
		if (re[0] == re[1]) {
			primary.remove(a, re[0]);
			primary.remove(b, re[1]);
			
			if(re[2] ==-1 && re[3]==-1)
				numberScored++;
			numberOfMovesMade++;
			msg.setContent(constructTruthMessage(a, re[0],b, re[0]));
			System.out.println("");
			System.out.println("The value was" + re[0] + "," + re[1]);
			return true;
		} else {
			System.out.println("");
			System.out.println("The value was" + re[0] + "," + re[1]);
			primary.add(a, re[0]);
			primary.add(b, re[1]);
			
			float prob = generator.nextFloat();
			numberOfMovesMade++;
			if(prob > 0.5)
			{
				msg.setContent(constructTruthMessage(a, re[0],b, re[0]));
			}
			else {
				msg.setContent(construtFalseMessage(a, re[0],b, re[0]));
			}
			
			return false;
		}
	}
	
	public boolean passTheToken() {
		return true;
	}
	
	
	public ArrayList<Integer> possibleMoves(BoardState b)
	{
		ArrayList<Integer> possible = new ArrayList<Integer>();
		for(int i=0;i<n;i++)
		{
			if(b.board[i] == -1)
			{
				possible.add(i);
			}
			else{
			}
		}
		return possible;
	}
	
	// Does not recursively check for memory entry. Checks once, and if is not listed in possible moves, 
	// returns no move found -1,-1
	public int [] successMovesAvailable(ArrayList<Integer>  possible)
	{
	//	int memArray [] = memory.getMemory();
		int moves[] = new int [2];
		moves = primary.matchingEntrySameMemory();
		if(moves[0] != -1 && moves[1] != -1 && possible.contains(moves[0]) && possible.contains(moves[1]) )
		return moves;
		
		if(moves[0] != -1 && moves[1] != -1 && !possible.contains(moves[0]) )
		{
			primary.remove(moves[0], -1);
		}
		if(moves[0] != -1 && moves[1] != -1 && !possible.contains(moves[0]) )
		{
			primary.remove(moves[1],-1);
		}
		moves[0] = -1;
		moves[1] = -1;
		return moves;
		
	}
	
	public int [] possibleSucessMovesAvailable(ArrayList<Integer>  possible)
	{
	//	int memArray [] = memory.getMemory();
		int moves[] = new int [2];
		moves = primary.matchingEntryMixed(secondary);
		if(moves[0] != -1 && moves[1] != -1 && possible.contains(moves[0]) && possible.contains(moves[1]) )
			return moves;
			
			if(moves[0] != -1 && moves[1] != -1 && !possible.contains(moves[0]) )
			{
				primary.remove(moves[0], -1);
				secondary.remove(moves[0], -1);
			}
			if(moves[0] != -1 && moves[1] != -1 && !possible.contains(moves[0]) )
			{
				primary.remove(moves[0], -1);
			}
			moves[0] = -1;
			moves[1] = -1;
			return moves;
	}
	
	public void printPossible(ArrayList<Integer>  possible ) {
		for(Integer q : possible)
		{
			System.out.print(q + " ");
		}
	}
	public int [] generateRandom(ArrayList<Integer>  possible)
	{
			
		    int moves[] = new int[2];
		    moves[0] = -1;
			moves[1] = -1;
		    if(possible.size() > 1)
		    {
			int ran = generator.nextInt(possible.size());
			moves[0] = possible.get(ran);
			possible.remove(ran);
			ran = generator.nextInt(possible.size());
			moves[1] = possible.get(ran);
			possible.remove(ran);
			return moves;
		    }
		    return moves;
					
		
	}
	
	public void playGame(boolean faking)
	{
		
	
			boolean secondaryMemoryUsed = false;
			BoardState  b = percept();
			ArrayList<Integer>  possible =  possibleMoves(b);
			b.printBoardState();
			printPossible(possible);
			int moves[] = successMovesAvailable(possible);
			if(moves[0] == -1 || moves[1] == -1)
			{
				if(faking == false && maxLieCount > currentLie  )
				{
					moves = possibleSucessMovesAvailable(possible);
					secondaryMemoryUsed = true;
					
				}
				if(moves[0] == -1 || moves[1] == -1)
				{
					moves = generateRandom(possible);
					secondaryMemoryUsed = false;
				}
				
				
			//	System.out.println("No success move was found");
			}	
			
			if(moves[0] == -1 || moves[1] == -1)
			{
				System.out.println("game ended no possible moves");
			}	
			else {
				System.out.println("The move made " + moves[0] + " "+ moves[1]);
				boolean success = makeMove(moves[0] ,moves[1],faking);
				if(success == false && faking == false && secondaryMemoryUsed == true)
				{
					currentLie++;
					secondary.remove(moves[0],moves[1]);
				}
			//	System.out.println(memory);
			}
			System.out.println("The score " + numberScored);
			System.out.println("The number of moves made " + numberOfMovesMade);
			System.out.println("----------------"  + cur.getLocalName() + " is done with round" + "----------------" );
			System.out.println();
		

	}
	@Override
	public void action() {

		
		ACLMessage recieved = cur.blockingReceive();
		if(recieved != null)
		{
			System.out.println("----------------" + cur.getLocalName() + " is playing the round" + "----------------");
			System.out.println("agent score is" + numberScored);
			
			boolean faking = analyzeMessage(recieved.getContent());
			if(faking)
				currentLie++;
			playGame(faking);
			Helper.delay(1000);
			cur.send(msg);
			
		}
		}
		
	

	private boolean finished = false;

	public boolean done() {
		return finished;
	}

} // ----------- End myBehaviour