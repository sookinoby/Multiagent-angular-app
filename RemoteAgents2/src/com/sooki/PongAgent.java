package com.sooki;


import java.util.Random;










import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

import com.sooki.environment.BoardState;
import com.sooki.inter.EnvironmentApi;
import com.sooki.utility.Helper;
import com.sooki.utility.TwoValueHolder;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class PongAgent extends Agent {
	private final String API_URL = "http://localhost:8080";
	int numberScored;
	int n=16;
	ACLMessage msg ;
	Random generator;
	EnvironmentApi environmentapi;
	protected void setup() {
		addBehaviour(new myBehaviour(this));
		try {
		 environmentapi = new RestAdapter.Builder()
		.setEndpoint(API_URL)
		.setLogLevel(LogLevel.FULL)
		.build()
		.create(EnvironmentApi.class);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	//	msg = new ACLMessage(ACLMessage.INFORM);
	//	AID r = new AID("test",AID.ISLOCALNAME);
	//	msg.setContent("your turn");
	//	msg.addReceiver(r);
			
	
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
		System.out.println("hello 21456666");
		System.out.println(environmentapi.initialise());
		BoardState b = environmentapi.getBoardState(getLocalName());
		TwoValueHolder toRev = new TwoValueHolder(0,1);
	
		TwoValueHolder  Revelead[] = environmentapi.seeCard(cur.getLocalName(),toRev);
		int re[] = new int [4];
		re[0] = Revelead[0].getX();
		re[1] = Revelead[0].getY();
		re[2] = Revelead[1].getX();
		re[3] = Revelead[1].getY();
	
		block(2000);
			
		}

		
		private boolean finished = false;

		public boolean done() {
			return finished;
		}

		

	} // ----------- End myBehaviour

}// end class myAgent
