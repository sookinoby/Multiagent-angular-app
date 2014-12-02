package com.sooki;


import java.util.Random;






import com.sooki.utility.Helper;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class PongAgent extends Agent {
	int numberScored;
	int n=36;
	ACLMessage msg ;
	Random generator;
	protected void setup() {
		addBehaviour(new myBehaviour(this));
		
		msg = new ACLMessage(ACLMessage.INFORM);
		AID r = new AID("test",AID.ISLOCALNAME);
		msg.setContent("your turn");
		msg.addReceiver(r);
			
	
	}

	class myBehaviour extends SimpleBehaviour {
		Agent cur;
		public myBehaviour(Agent a) {
			super(a);
			this.cur = a;
		
		}
		
		public void analyzeMessage(String content)
		{
			String posval [] = content.split(";");
			String me1[] = posval[0].split("=");
			String me2[] =  posval[1].split("=");
			
			int pos1 = Integer.parseInt(me1[0]);
			int pos2 = Integer.parseInt(me2[0]);
			int mem1 = Integer.parseInt(me1[1]);
			int mem2 = Integer.parseInt(me1[1]);
			
			System.out.println(pos1 + "=" + mem1 + ";" + pos2 + "=" + mem2 );
		}
		
		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage recieved = blockingReceive();
			if(recieved != null)
			{
				System.out.println("----------------" + cur.getLocalName() + " is playing the round" + "----------------");
				System.out.print(cur.getLocalName() + " "); analyzeMessage(recieved.getContent() );  
				Helper.delay(500);
				System.out.println("----------------"  + cur.getLocalName() + " is done with round" + "----------------" );
				System.out.println("");
				send(msg);
                
               
              
			}
			
		}

		
		private boolean finished = false;

		public boolean done() {
			return finished;
		}

		

	} // ----------- End myBehaviour

}// end class myAgent
