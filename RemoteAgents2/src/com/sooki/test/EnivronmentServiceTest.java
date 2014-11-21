package com.sooki.test;



import java.util.List;



import com.sooki.inter.EnvironmentApi;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

/**
 * 
 * This test sends a POST request to the VideoServlet to add a new video and
 * then sends a second GET request to check that the video showed up in the list
 * of videos.
 * 
 * The test requires that the application be running first (see the directions in
 * the README.md file for how to launch the application.
 * 
 * To run this test, right-click on it in Eclipse and select
 * "Run As"->"JUnit Test"
 * 
 * Pay attention to how this test that actually uses HTTP and the test that
 * just directly makes method calls on a VideoSvc object are essentially
 * identical. All that changes is the setup of the videoService variable.
 * Yes, this could be refactored to eliminate code duplication...but the
 * goal was to show how much Retrofit simplifies interaction with our 
 * service!
 * 
 * @author jules
 *
 */
public class EnivronmentServiceTest {

	private final String TEST_URL = "http://localhost:8080";

	/**
	 * This is how we turn the VideoSvcApi into an object that
	 * will translate method calls on the VideoSvcApi's interface
	 * methods into HTTP requests on the server. Parameters / return
	 * values are being marshalled to/from JSON.
	 */
	private EnvironmentApi environmentapi = new RestAdapter.Builder()
			.setEndpoint(TEST_URL)
			.setLogLevel(LogLevel.FULL)
			.build()
			.create(EnvironmentApi.class);

	/**
	 * This test sends a POST request to the VideoServlet to add a new video and
	 * then sends a second GET request to check that the video showed up in the
	 * list of videos.
	 * 
	 * @throws Exception
	 */

	public void testInitialise() throws Exception {
		boolean ok = environmentapi.initialise();
		System.out.println(ok);
	}
	
	
	public static void main(String[] args)
	{
		EnivronmentServiceTest ap = new EnivronmentServiceTest();
		try{
		ap.testInitialise();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
}