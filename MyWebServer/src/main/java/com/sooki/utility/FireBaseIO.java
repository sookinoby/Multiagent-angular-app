package com.sooki.utility;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

import com.sooki.inter.FireBaseInterface;

public class FireBaseIO {
	private final static String API_URL = "https://vivid-heat-5137.firebaseio.com/";
	
	static FireBaseInterface firebaseapi;
	public static void seeCard(FireMessage fm) {
		
		try {
			firebaseapi = new RestAdapter.Builder()
		.setEndpoint(API_URL)
		.setLogLevel(LogLevel.FULL)
		.build()
		.create(FireBaseInterface.class);
		}
		catch (Exception e)
		{
			
		}
	}
	

}
