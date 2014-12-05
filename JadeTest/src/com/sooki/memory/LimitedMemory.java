package com.sooki.memory;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

public class LimitedMemory {

	LinkedBlockingDeque<Cell> mem;
	int memLimit = 4;
	public LimitedMemory()
	{
		mem = new LinkedBlockingDeque<Cell>();
	}
	
	public void add(int position, int val)
	{
		Cell cel = new Cell(position, val);
		if(mem.size() > memLimit )
		{
		mem.removeFirst();
		mem.addLast(cel);
		}
		else {
		mem.addLast(cel);
		}
			
	}
	
	public void remove(int pos1,int pos2)
	{
		Cell one = findMatchingCell(pos1);
		Cell two = findMatchingCell(pos2);
		if(one != null)
		mem.remove(one);
		if(two != null)
		mem.remove(two);
		
			
	}
	
	public void remove(Cell cel)
	{
		this.mem.remove(cel);
			
	}
	
	public Cell findMatchingCell(int pos1)
	{
	
		for(Cell cel : mem)
		{
			if(cel.position == pos1)
			{
				return cel;
			}
		}
		return null;
			
	}
	
	public void add(Cell cel)
	{
		if(mem.size() > memLimit )
		{
		mem.removeLast();
		mem.addFirst(cel);
		}
		else {
		mem.addFirst(cel);
		}
			
	}
	
	public int[] matchingEntryMixed(LimitedMemory memory2)
	{
		int pos[] = new int[2];
		pos[0] = -1;
		pos[1] = -1;
		for(Cell ce : mem)
		{
			for(Cell pe : memory2.mem)
			{
				if(ce.matchingPair(pe))
				{
					pos[0] = ce.position;
					pos[1] = pe.position;
				}
			}
		}
		
		return pos;
	}
	
	public boolean isFaking(LimitedMemory memory2)
	{
		boolean fake = false;
		if(!memory2.mem.isEmpty())
		{
		for(Cell ce : mem)
		{
			for(Cell pe : memory2.mem)
			{
				if(ce.fakeEntry(pe))
				{
					fake = true;
				}
			}
		}
		}
		return fake;
		
	}
	
	public void removeBadMemory(LimitedMemory memory2)
	{
		if(!memory2.mem.isEmpty())
		{
		ArrayList<Cell> bad = new ArrayList<>();
		for(Cell ce : mem)
		{
			for(Cell pe : memory2.mem)
			{
				if(ce.fakeEntry(pe))
				{
				bad.add(pe);
				}
			}
		}
		if(!bad.isEmpty())
		{
		for(Cell qe : bad)
			memory2.remove(qe);
		}
		}
		
	}
	
	public int[] matchingEntrySameMemory()
	{
		int pos[] = new int[2];
		pos[0] = -1;
		pos[1] = -1;
		for(Cell ce : mem)
		{
			for(Cell pe : mem)
			{
				if(ce.matchingPair(pe))
				{
					pos[0] = ce.position;
					pos[1] = pe.position;
				}
			}
		}
		
		return pos;
	}
	

	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder br = new StringBuilder();
		for(Cell ce : mem)
		{
		 br.append("("+  ce.position +","  + ce.value);
		}
		return br.toString();
	}
	
	
	/*public static void main(String args[])
	{
		LimitedMemory me = new LimitedMemory();
		LimitedMemory me2 = new LimitedMemory();
		Cell cell1 = new Cell(0,0);
		Cell cell2 = new Cell(1,1);
		//Cell cell3 = new Cell(2,2);
		Cell cell4 = new Cell(2,1);
		me.add(cell1);
		me.add(cell2);
		//me.add(cell3);
		me.add(cell4);
		
		Cell cell5 = new Cell(0,0);
		Cell cell6 = new Cell(1,1);
		//Cell cell3 = new Cell(2,2);
		Cell cell7 = new Cell(2,2);
		
		me2.add(cell5);
		me2.add(cell6);
		//me.add(cell3);
		me2.add(cell7);
		
		int pos [] = me.matchingEntrySameMemory();
		//int pos2 [] = me.matchingEntryMixed(me2);
	//	System.out.println(cell1);
	//	System.out.println(cell2);
		System.out.println(me.isFaking(me2));
		me.removeBadMemory(me2);
		System.out.println(me.isFaking(me2));
	}*/
}
