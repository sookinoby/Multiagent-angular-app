package com.sooki.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.http.Body;
import retrofit.http.EncodedPath;
import retrofit.http.EncodedQuery;
import retrofit.http.POST;

import com.sooki.environment.BoardConfiguration;
import com.sooki.environment.BoardState;
import com.sooki.inter.EnvironmentApi;
import com.sooki.inter.FireBaseInterface;
import com.sooki.utility.FireMessage;
import com.sooki.utility.TwoValueHolder;

@Controller
public class MyEnv implements EnvironmentApi {
	static FireBaseInterface firebaseapi;
	static String API_URL = "https://vivid-heat-5137.firebaseio.com/";
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
	public @ResponseBody BoardState getBoardState(@RequestParam("Name") String agentName)
	{

		System.out.println("tel" + agentName);
		BoardState bs = BoardState.getState();
		
		return bs;
	}
	
	
	@RequestMapping(value="/envsee/{name}", method=RequestMethod.POST)
	public @ResponseBody TwoValueHolder[] seeCard(@PathVariable("name") String agentName,@RequestBody TwoValueHolder p) {
		// TODO Auto-generated method stub
		System.out.println(p.getX());
		System.out.println(p.getY());
		int values[] = bc.getRevealed(p.getX(), p.getY());
		TwoValueHolder arrayVal[] = new TwoValueHolder[2];
		TwoValueHolder val = new TwoValueHolder(values[0], values[1]);
		TwoValueHolder val2 = new TwoValueHolder(values[2], values[3]);
		arrayVal[0] = val;
		arrayVal[1] = val2;
		try {
			firebaseapi = new RestAdapter.Builder()
		.setEndpoint(API_URL)
		.setLogLevel(LogLevel.FULL)
		.build()
		.create(FireBaseInterface.class);
			
		String name = agentName;
		FireMessage fm = new FireMessage(name,p.getX());
		firebaseapi.seeCard(fm);
		fm = new FireMessage(name,p.getY());
		Thread.sleep(1000);
		firebaseapi.seeCard(fm);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		
		return arrayVal;
	}


	@Override
	public void deleteCard() {
		// TODO Auto-generated method stub
		
		bc = null;
		try {
			firebaseapi = new RestAdapter.Builder()
		.setEndpoint(API_URL)
		.setLogLevel(LogLevel.FULL)
		.build()
		.create(FireBaseInterface.class);
		firebaseapi.deleteMessage();
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		
	}
	

	

	/*public static void main(String args[])
	{
		initialise();
		bc.printboard(n);
		BoardState b = Environment.getBoardState("test");
		b.printBoardState();
	
	}*/
		
		
}
	

