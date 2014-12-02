package com.sooki.utility;

import java.util.Random;

public class FireMessage {
	
	String agent;
	int card;
	
	
	public FireMessage() {
		// TODO Auto-generated constructor stub
	}
	public FireMessage(String agent, int value) {
		super();
		
		
		this.agent = agent;
		this.card = value;
	}
	
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public int getValue() {
		return card;
	}
	public void setValue(int value) {
		this.card = value;
	};
	
	

}
