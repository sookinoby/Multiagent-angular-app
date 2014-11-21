package com.sooki.utility;

import java.util.ArrayList;
import java.util.HashMap;

public class Storage2 {

	int maximum = 40;
	ArrayList<Position> memlist [];
	public Storage2()
	{
		memlist = new ArrayList[maximum];
		for(int i=0;i<maximum;i++)
			memlist[i] = new ArrayList<Position>(2);
	}
	
	public void put(int key,Position value)
	{
		
		memlist[key].add(value);
	}
	public ArrayList<Position> get(int key)
	{
		return memlist[key];
	}
	
	public boolean hasValidEntry()
	{
		for(int i=0;i<maximum;i++)
			if(memlist[i].size() == 2)
			{
				return true;
			}
		return false;
		
	}
	
	public void remove(int key)
	{
		memlist[key] = null;
	}
	public ArrayList<Position> getValidEntry()
	{
		for(int i=0;i<maximum;i++)
			if(memlist[i].size() == 2)
			{
				ArrayList<Position> newList = new ArrayList<Position>(2);
				newList.add(memlist[i].get(0));
				newList.add(memlist[i].get(1));
				memlist[i] = new ArrayList<Position>(2);
				return newList;
			}
		return null;
		
	}
}
