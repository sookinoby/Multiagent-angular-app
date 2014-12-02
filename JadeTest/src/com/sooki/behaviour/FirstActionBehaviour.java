package com.sooki.behaviour;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import com.sooki.environment.Environment;
import com.sooki.utility.Helper;

public class FirstActionBehaviour extends GeneralGameBehaviour {
	
	boolean first = true;
	public FirstActionBehaviour(Agent a, int n,String passingTokenAgentName) {
		super(a, n,passingTokenAgentName);
		// TODO Auto-generated constructor stub
		this.cur = a;
		this.n = n;
	}
	@Override
	public void action() {

		if(first)	
		{
			System.out.println("----------------" + cur.getLocalName() + " is playing the round" + "----------------");
			System.out.println("agent score is" + numberScored);
		playGame(true);
		first = false;
		Helper.delay(1000);
		cur.send(msg);
		}
		else {
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
		
	}

}
