package com.sooki.behaviour;

import jade.core.Agent;

import com.sooki.environment.Environment;

public class PunishBehvaiour extends GeneralGameBehaviour implements CustomBehaviourInterface {
	
	public PunishBehvaiour(Agent a, int n, String passingTokenAgentName) {
		super(a, n, passingTokenAgentName);
		// TODO Auto-generated constructor stub
	}

	@Override
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
			
			
			numberOfMovesMade++;
			if(faking)
			{
				msg.setContent(construtFalseMessage(a, re[0],b, re[0]));
			}
			else {
				msg.setContent(constructTruthMessage(a, re[0],b, re[0]));
			}
			
			return false;
		}
	}	

}
