package com.sooki;

import java.util.ArrayList;
import java.util.Random;

import com.sooki.BobAgent.myBehaviour;
import com.sooki.environment.BoardState;
import com.sooki.environment.Environment;
import com.sooki.memory.LimitedMemory;




import com.sooki.utility.Helper;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

public class FredAgent extends Agent {
	
	int numberScored = 0;
	int n=36;
	//Memory memory;
	Random generator;
	LimitedMemory primary;
	LimitedMemory secondary_Bob,secondary_Jeff;
	ACLMessage msg_turn_message,msg_for_Jeff,msg_for_Bob ;
	AID r,rBob,rJeff;
	int numberOfMovesMade = 0 ;
	int maxLieCount = 2;
	int currentLie_Bob = 0;
	int currentLie_Jeff = 0;
	ArrayList<String> otherAgents;
	boolean faking_Bob = false, faking_Jeff =false;
	protected void setup() {
		addBehaviour(new myBehaviour(this));
		Environment.initialise();
		
		msg_turn_message = new ACLMessage(ACLMessage.INFORM);
		msg_for_Jeff = new ACLMessage(ACLMessage.INFORM);
		msg_for_Bob = new ACLMessage(ACLMessage.INFORM);
		
		r = new AID("Jeff",AID.ISLOCALNAME);
		rBob = new AID("Bob",AID.ISLOCALNAME);
		rJeff = new AID("Jeff",AID.ISLOCALNAME);
		msg_turn_message.addReceiver(r);
		otherAgents = new ArrayList<>();
		msg_for_Jeff.addReceiver(rJeff);
		msg_for_Bob.addReceiver(rBob);
		
	//	memory = new Memory();
		primary = new LimitedMemory();
		secondary_Bob = new LimitedMemory();
		secondary_Jeff = new LimitedMemory();
		generator = new Random();
		generator.setSeed(0);
	}

	class myBehaviour extends SimpleBehaviour {
		Agent cur;
		public myBehaviour(Agent a) {
			super(a);
			this.cur = a;
		
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
		
		public boolean analyzeMessageofBob(String content)
		{ 
			boolean isfaking = false;
			try {
		//	System.out.println("the content is"  + content);
			
			String posval [] = content.split(";");
			String me1[] = posval[0].split("=");
			String me2[] =  posval[1].split("=");
			
			int pos1 = Integer.parseInt(me1[0]);
			int pos2 = Integer.parseInt(me2[0]);
			int mem1 = Integer.parseInt(me1[1]);
			int mem2 = Integer.parseInt(me1[1]);
			
			secondary_Bob.add(pos1, mem1);
			secondary_Bob.add(pos2, mem2);
			isfaking = primary.isFaking(secondary_Bob);
			if(isfaking)
			{
				primary.removeBadMemory(secondary_Bob);
			//	System.out.println( getLocalName() + " Says: Other agent lied to me");
			}
		}
		catch (Exception e)
		{
			System.out.println("Recieved invalid message");
		}
			return isfaking;
		}
		
		
		// Jeff is secondary2;
		public boolean analyzeMessageofJeff(String content)
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
			
			secondary_Jeff.add(pos1, mem1);
			secondary_Jeff.add(pos2, mem2);
			isfaking = primary.isFaking(secondary_Jeff);
			if(isfaking)
			{
				primary.removeBadMemory(secondary_Jeff);
				System.out.println( getLocalName() + " Says: Other agent lied to me");
			}
		}
		catch (Exception e)
		{
			System.out.println("Recieved invalid message");
		}
			return isfaking;
		}
		
		public boolean makeMove(int a, int b,boolean faking_Bob,boolean faking_Jeff) {
			int re[] = Environment.seeCard(a, b);
			
			msg_turn_message.setContent("goAhead");;
			if (re[0] == re[1]) {
				primary.remove(a, re[0]);
				primary.remove(b, re[1]);
				numberScored++;
				numberOfMovesMade++;
				msg_for_Jeff.setContent(constructTruthMessage(a, re[0],b, re[0]));
				msg_for_Bob.setContent(constructTruthMessage(a, re[0],b, re[0]));
				return true;
			} else {
				System.out.println("");
				System.out.println("The value was" + re[0] + "," + re[1]);
				primary.add(a, re[0]);
				primary.add(b, re[1]);
				
				float prob = generator.nextFloat();
				numberOfMovesMade++;
				if(faking_Bob)
				{
					msg_for_Bob.setContent(construtFalseMessage(a, re[0],b, re[0]));
				}
				else{
					msg_for_Bob.setContent(constructTruthMessage(a, re[0],b, re[0]));
				}
				if(faking_Jeff)
				{
					msg_for_Jeff.setContent(construtFalseMessage(a, re[0],b, re[0]));
				}
				else {
					
					msg_for_Jeff.setContent(constructTruthMessage(a, re[0],b, re[0]));
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
		
		
		public int [] successMovesAvailable(ArrayList<Integer>  possible)
		{
		//	int memArray [] = memory.getMemory();
			int moves[] = new int [2];
			moves = primary.matchingEntrySameMemory();
			return moves;
			
		}
		
		public int [] possibleSucessMovesAvailableFromBob(ArrayList<Integer>  possible)
		{
		//	int memArray [] = memory.getMemory();
			int moves[] = new int [2];
			moves = primary.matchingEntryMixed(secondary_Bob);
			return moves;
			
		}
		
		public int [] possibleSucessMovesAvailableFromJeff(ArrayList<Integer>  possible)
		{
		//	int memArray [] = memory.getMemory();
			int moves[] = new int [2];
			moves = primary.matchingEntryMixed(secondary_Jeff);
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
		
		public void playGame(boolean faking_Bob,boolean faking_Jeff)
		{
			System.out.println("----------------" + cur.getLocalName() + " is playing the round" + "----------------");
		
				boolean secondaryMemoryUsedBob = false;
				boolean secondaryMemoryUsedJeff = false;
				BoardState  b = percept();
				ArrayList<Integer>  possible =  possibleMoves(b);
				b.printBoardState();
				printPossible(possible);
				int moves[] = successMovesAvailable(possible);
				if(moves[0] == -1 || moves[1] == -1)
				{
					if(currentLie_Bob < currentLie_Jeff)
					{
					if(faking_Bob == false && maxLieCount > currentLie_Bob  )
					{
						moves = possibleSucessMovesAvailableFromBob(possible);
						secondaryMemoryUsedBob = true;
						
					}
					else if(faking_Jeff == false && maxLieCount > currentLie_Jeff  )
					{
						moves = possibleSucessMovesAvailableFromJeff(possible);
						secondaryMemoryUsedJeff = true;
						
					}
					}
					else {
						if(faking_Jeff == false && maxLieCount > currentLie_Jeff  )
						{
							moves = possibleSucessMovesAvailableFromJeff(possible);
							secondaryMemoryUsedJeff = true;
							
						}
						else if(faking_Bob == false && maxLieCount > currentLie_Bob  )
						{
							moves = possibleSucessMovesAvailableFromBob(possible);
							secondaryMemoryUsedBob = true;
							
						}
						
					}
					if(moves[0] == 6 && moves[1] == 14)
						System.out.println("wait");
		
					
					
				//	System.out.println("No success move was found");
				}	
				if(moves[0] == -1 || moves[1] == -1 || !possible.contains(moves[0]) || !possible.contains(moves[1]))
				{
					System.out.println("playing random move");
					moves = generateRandom(possible);
					secondaryMemoryUsedBob = false;
					secondaryMemoryUsedJeff = false;
				}
				
				if(moves[0] == -1 || moves[1] == -1)
				{
					System.out.println("game ended no possible moves");
				}	
				else {
					System.out.println("The move made " + moves[0] + " "+ moves[1]);
					boolean success = makeMove(moves[0] ,moves[1],faking_Bob,faking_Jeff);
					if(success == false && faking_Bob == false && secondaryMemoryUsedBob == true)
					{
						currentLie_Bob++;
						secondary_Bob.remove(moves[0],moves[1]);
					}
					else if(success == false && faking_Bob == false && secondaryMemoryUsedJeff == true)
					{
						currentLie_Jeff++;
						secondary_Jeff.remove(moves[0],moves[1]);
					}
				//	System.out.println(memory);
				}
				
				System.out.println("The score " + numberScored);
				System.out.println("The number of moves made " + numberOfMovesMade);
				System.out.println("----------------"  + cur.getLocalName() + " is done with round" + "----------------" );
				System.out.println();
			
	
		}

		public void action() {
			
			ACLMessage recieved = blockingReceive();
			if(recieved != null)
			{
				if(recieved.getSender().getLocalName().equals("Bob") & !recieved.getContent().equals("goAhead"))
				{
				faking_Bob = analyzeMessageofBob(recieved.getContent());
				if(faking_Bob)
					currentLie_Bob++;
				}
				if(recieved.getSender().getLocalName().equals("Jeff") & !recieved.getContent().equals("goAhead"))
				{
					faking_Jeff = analyzeMessageofJeff(recieved.getContent());
					if(faking_Jeff)
						currentLie_Jeff++;
				}
				if(recieved.getContent().equals("goAhead"))
				{
				playGame(faking_Bob,faking_Jeff);
				faking_Bob = false;
				faking_Jeff = false;
				Helper.delay(1000);
				send(msg_for_Bob);
				send(msg_for_Jeff);
				send(msg_turn_message);
				}
			}
			
			}


			
		
	
		private boolean finished = false;
		@Override
		public boolean done() {
			return finished;
		}

	} // ----------- End myBehaviour

}// end class myAgent
