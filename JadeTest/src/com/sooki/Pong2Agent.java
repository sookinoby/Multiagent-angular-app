package com.sooki;

import java.util.ArrayList;
import java.util.Random;

import com.sooki.behaviour.PunishBehvaiour;
import com.sooki.environment.BoardState;
import com.sooki.environment.Environment;
import com.sooki.memory.LimitedMemory;




import com.sooki.utility.Helper;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

public class Pong2Agent extends Agent {
	

	protected void setup() {
		addBehaviour(new PunishBehvaiour(this,16,"bob"));
	
	}



}// end class myAgent
