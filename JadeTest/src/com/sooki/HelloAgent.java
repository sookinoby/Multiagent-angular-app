package com.sooki;


import com.sooki.environment.Environment;

import com.sooki.behaviour.*;

import jade.core.Agent;


public class HelloAgent extends Agent {
	boolean first = true;
	

	//Memory memory;
	
   int n = 16;
	protected void setup() {
		addBehaviour(new FirstActionBehaviour(this, n,"fred"));
		Environment.initialise();
		

	}



}// end class myAgent
