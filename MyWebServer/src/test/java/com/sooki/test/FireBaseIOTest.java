package com.sooki.test;

import java.util.Random;

import org.junit.Test;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

import com.sooki.inter.FireBaseInterface;
import com.sooki.utility.FireMessage;

public class FireBaseIOTest {
	
private final static String API_URL = "https://vivid-heat-5137.firebaseio.com/";
	
	


	static FireBaseInterface firebaseapi;
	@Test
	public void testSeeCard() {
		
		FireMessage  fm = new FireMessage("bob", 2);
		try {
			firebaseapi = new RestAdapter.Builder()
		.setEndpoint(API_URL)
		.setLogLevel(LogLevel.FULL)
		.build()
		.create(FireBaseInterface.class);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		firebaseapi.seeCard(fm);
		
	}
		@Test
	public void testDeleteCard() {
		
			try {
			firebaseapi = new RestAdapter.Builder()
		.setEndpoint(API_URL)
		.setLogLevel(LogLevel.FULL)
		.build()
		.create(FireBaseInterface.class);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		firebaseapi.deleteMessage();
		
	}

}
