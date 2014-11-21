package com.sooki.utility;

public class Memory {
	
	int n = 16;
	int mem[];
	public Memory()
	{
		mem = new int[n];
		for(int i=0;i <n; i++)
		{
			mem[i] = -1;
		}
	}
	
	public boolean put(int position1,int position2,int a,int b)
	{
		if(mem[position1] == -1 & mem[position2] == -1)
		{
		mem[position1] = a;
		mem[position2] = b;
		return true;
		}
		return false;
	}
	
	public boolean erase(int position1,int position2)
	{
		mem[position1] = -1;
		mem[position2] = -1;
		return true;
	}
	
	public int []  getMemory()
	{
		return mem;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder br = new StringBuilder();
		for(int i=0;i<n;i++)
			br.append(mem[i] + " ");
		return br.toString();
	}


}
