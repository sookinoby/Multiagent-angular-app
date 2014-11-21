package com.sooki.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sooki.environment.BoardConfiguration;
import com.sooki.environment.BoardState;
import com.sooki.inter.EnvironmentApi;
import com.sooki.utility.TwoValueHolder;

@Controller
public class MyEnv implements EnvironmentApi {
	static int n=16;
	static BoardConfiguration bc;
	@RequestMapping(value=ENVIRONMENT_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody boolean initialise()
	{
		 bc = BoardConfiguration.getInstance(n);
		/// bc.boardState[3]= true;
		// bc.boardState[11]= true;
		 System.out.println("Initialised");
		 return true;
	}
	
	
	@RequestMapping(value=ENVIRONMENT_SVC_PATH, method=RequestMethod.GET)
	public @ResponseBody BoardState getBoardState()
	{
		System.out.println("tel");
		BoardState bs = BoardState.getState();
		System.out.println(bs);
		return bs;
	}



	public @ResponseBody TwoValueHolder seeCard(@RequestBody TwoValueHolder p) {
		// TODO Auto-generated method stub
		int values[] = bc.getRevealed(p.getX(), p.getY());
		TwoValueHolder val = new TwoValueHolder(values[0], values[1]);
		return val;
	}
	

	

	/*public static void main(String args[])
	{
		initialise();
		bc.printboard(n);
		BoardState b = Environment.getBoardState("test");
		b.printBoardState();
	
	}*/
		
		
}
	

