package com.sooki;

import java.util.ArrayList;
import java.util.Random;

import com.sooki.environment.BoardState;
import com.sooki.environment.Environment;
import com.sooki.memory.LimitedMemory;




import com.sooki.utility.Helper;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

public class BobAgent extends Agent {
	boolean first = true;
	int numberScored = 0;
	int n=36;
	//Memory memory;
	Random generator;
	LimitedMemory primary;
	LimitedMemory secondary_Fred,secondary_Jeff;
	ACLMessage msg_turn_message,msg_for_Jeff,msg_for_Fred ;
	AID r,rFred,rJeff;
	int numberOfMovesMade = 0 ;
	int maxLieCount = 2;
	int currentLie_Fred = 0;
	int currentLie_Jeff = 0;
	ArrayList<String> otherAgents;
	boolean faking_Fred = false, faking_Jeff =false;
	protected void setup() {
		addBehaviour(new myBehaviour(this));
		Environment.initialise();
		
		msg_turn_message = new ACLMessage(ACLMessage.INFORM);
		msg_for_Jeff = new ACLMessage(ACLMessage.INFORM);
		msg_for_Fred = new ACLMessage(ACLMessage.INFORM);
		r = new AID("Fred",AID.ISLOCALNAME);
		rFred = new AID("Fred",AID.ISLOCALNAME);
		rJeff = new AID("Jeff",AID.ISLOCALNAME);
		msg_turn_message.addReceiver(r);
		otherAgents = new ArrayList<>();
		msg_for_Jeff.addReceiver(rJeff);
		msg_for_Fred.addReceiver(rFred);
		
	//	memory = new Memory();
		primary = new LimitedMemory();
		secondary_Fred = new LimitedMemory();
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
		
		public boolean analyzeMessageofFred(String content)
		{ 
			boolean isfaking = false;
			try {
			//System.out.println("the content is"  + content);
			
			String posval [] = content.split(";");
			String me1[] = posval[0].split("=");
			String me2[] =  posval[1].split("=");
			
			int pos1 = Integer.parseInt(me1[0]);
			int pos2 = Integer.parseInt(me2[0]);
			int mem1 = Integer.parseInt(me1[1]);
			int mem2 = Integer.parseInt(me1[1]);
			
			secondary_Fred.add(pos1, mem1);
			secondary_Fred.add(pos2, mem2);
			isfaking = primary.isFaking(secondary_Fred);
			if(isfaking)
			{
				primary.removeBadMemory(secondary_Fred);
				//System.out.println( getLocalName() + " Says: Other agent lied to me");
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
		
		public boolean makeMove(int a, int b,boolean faking_Fred,boolean faking_Jeff) {
			int re[] = Environment.seeCard(a, b);
			msg_turn_message.setContent("goAhead");;
			if (re[0] == re[1]) {
				primary.remove(a, re[0]);
				primary.remove(b, re[1]);
				numberScored++;
				numberOfMovesMade++;
				msg_turn_message.setContent("goAhead");;
				msg_for_Jeff.setContent(constructTruthMessage(a, re[0],b, re[0]));
				msg_for_Fred.setContent(constructTruthMessage(a, re[0],b, re[0]));
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
					msg_turn_message.setContent("goAhead");;
					msg_for_Jeff.setContent(constructTruthMessage(a, re[0],b, re[0]));
					msg_for_Fred.setContent(constructTruthMessage(a, re[0],b, re[0]));
					
				}
				else {
					msg_turn_message.setContent("goAhead");;
					msg_for_Jeff.setContent(construtFalseMessage(a, re[0],b, re[0]));
					msg_for_Fred.setContent(construtFalseMessage(a, re[0],b, re[0]));
					
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
		
		public int [] possibleSucessMovesAvailableFromFred(ArrayList<Integer>  possible)
		{
		//	int memArray [] = memory.getMemory();
			int moves[] = new int [2];
			moves = primary.matchingEntryMixed(secondary_Fred);
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
		
		public void playGame(boolean faking_Fred,boolean faking_Jeff)
		{
			System.out.println("----------------" + cur.getLocalName() + " is playing the round" + "----------------");
		
				boolean secondaryMemoryUsedFred = false;
				boolean secondaryMemoryUsedJeff = false;
				BoardState  b = percept();
				ArrayList<Integer>  possible =  possibleMoves(b);
				b.printBoardState();
				printPossible(possible);
				int moves[] = successMovesAvailable(possible);
				if(moves[0] == -1 || moves[1] == -1)
				{
					if(currentLie_Fred < currentLie_Jeff)
					{
					if(faking_Fred == false && maxLieCount > currentLie_Fred  )
					{
						moves = possibleSucessMovesAvailableFromFred(possible);
						secondaryMemoryUsedFred = true;
						
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
						else if(faking_Fred == false && maxLieCount > currentLie_Fred  )
						{
							moves = possibleSucessMovesAvailableFromFred(possible);
							secondaryMemoryUsedFred = true;	
						}
					}
					
				
					
					
				//	System.out.println("No success move was found");
				}	
				if(moves[0] == -1 || moves[1] == -1 || !possible.contains(moves[0]) || !possible.contains(moves[1]))
				{
					System.out.println("playing random moves");
					moves = generateRandom(possible);
					secondaryMemoryUsedFred = false;
					secondaryMemoryUsedJeff = false;
				}
				if(moves[0] == -1 || moves[1] == -1)
				{
					System.out.println("game ended no possible moves");
				}	
				else {
					System.out.println("The move made " + moves[0] + " "+ moves[1]);
					boolean success = makeMove(moves[0] ,moves[1],faking_Fred,faking_Jeff);
					if(success == false && faking_Fred == false && secondaryMemoryUsedFred == true)
					{
						currentLie_Fred++;
						secondary_Fred.remove(moves[0],moves[1]);
					}
					else if(success == false && faking_Fred == false && secondaryMemoryUsedJeff == true)
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
			
			if(first)	
			{
			playGame(true,true);
			first = false;
			Helper.delay(1000);
			send(msg_for_Fred);
			send(msg_for_Jeff);
			send(msg_turn_message);
			
			}
			else {
			ACLMessage recieved = blockingReceive();
			if(recieved != null)
			{
				
				if(recieved.getSender().getLocalName().equals("Fred") & !recieved.getContent().equals("goAhead"))
				{
				faking_Fred = analyzeMessageofFred(recieved.getContent());
				if(faking_Fred)
					currentLie_Fred++;
				}
				if(recieved.getSender().getLocalName().equals("Jeff") & !recieved.getContent().equals("goAhead"))
				{
					faking_Jeff = analyzeMessageofJeff(recieved.getContent());
					if(faking_Jeff)
						currentLie_Jeff++;
				}
				if(recieved.getContent().equals("goAhead"))
				{
				playGame(faking_Fred,faking_Jeff);
				faking_Fred = false;
				faking_Jeff = false;
				Helper.delay(1000);
				send(msg_for_Fred);
				send(msg_for_Jeff);
				send(msg_turn_message);
				}
				
				
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
