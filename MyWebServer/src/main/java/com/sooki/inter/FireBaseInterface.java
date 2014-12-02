package com.sooki.inter;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;

import com.sooki.environment.BoardState;
import com.sooki.utility.FireBaseIO;
import com.sooki.utility.FireMessage;
import com.sooki.utility.TwoValueHolder;

public interface FireBaseInterface {
	
public static final String ENVIRONMENT_SVC_PATH = "/messages";
	
	
	
	

	@POST(ENVIRONMENT_SVC_PATH+".json" )
	public Object seeCard(@Body FireMessage p);	
	
	@DELETE(ENVIRONMENT_SVC_PATH+".json" )
	public FireMessage deleteMessage();

}
