package com.sooki.utility;

import java.util.HashMap;

public class Storage {

	HashMap<Position,String> releaved;
	public Storage()
	{
		releaved = new HashMap<Position,String>();
	}
	
	public void put(Position key,String value)
	{
		
		releaved.put(key, value);
	}
	public String get(Position key)
	{
		return releaved.get(key);
	}
	public boolean contains(Position key)
	{
		return releaved.containsKey(key);
	}
	
	public boolean isEmpty()
	{
		return releaved.isEmpty();
	}
}
