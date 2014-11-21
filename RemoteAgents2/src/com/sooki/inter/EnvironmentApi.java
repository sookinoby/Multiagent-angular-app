package com.sooki.inter;



import java.util.List;

import com.sooki.environment.BoardState;
import com.sooki.utility.TwoValueHolder;

import retrofit.http.Body;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.POST;


public interface EnvironmentApi {
	public static final String ENVIRONMENT_SVC_PATH = "/env";
	
	@POST(ENVIRONMENT_SVC_PATH)
	public boolean initialise();
	

	@GET(ENVIRONMENT_SVC_PATH)
	public BoardState getBoardState(@EncodedPath("Name") String agentName);

	public TwoValueHolder seeCard(@Body TwoValueHolder p);
	
	
	
}
