/*package com.sooki.controller;



import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sooki.environment.BoardConfiguration;
import com.sooki.inter.EnvironmentApi;

*//**
 * This simple VideoSvc allows clients to send HTTP POST requests with
 * videos that are stored in memory using a list. Clients can send HTTP GET
 * requests to receive a JSON listing of the videos that have been sent to
 * the controller so far. Stopping the controller will cause it to lose the history of
 * videos that have been sent to it because they are stored in memory.
 * 
 * Notice how much simpler this VideoSvc is than the original VideoServlet?
 * Spring allows us to dramatically simplify our service. Another important
 * aspect of this version is that we have defined a VideoSvcApi that provides
 * strong typing on both the client and service interface to ensure that we
 * don't send the wrong paraemters, etc.
 * 
 * @author jules
 *
 *//*

// Tell Spring that this class is a Controller that should 
// handle certain HTTP requests for the DispatcherServlet
@Controller
public class Great implements EnvironmentApi  {
	
	// An in-memory list that the servlet uses to store the
	// videos that are sent to it by clients
	static int n=16;
	static BoardConfiguration bc;
	@RequestMapping(value=ENVIRONMENT_SVC_PATH, method=RequestMethod.POST)
	public @ResponseBody boolean initialise(){
		return true;
	}
	
	// Receives GET requests to /video and returns the current
	// list of videos in memory. Spring automatically converts
	// the list of videos to JSON because of the @ResponseBody
	// annotation.
	
}*/