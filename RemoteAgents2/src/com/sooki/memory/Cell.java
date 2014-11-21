package com.sooki.memory;

public class Cell {
	
	int position;
	int value;
	
	public Cell(int position, int value) {
		super();
		this.position = position;
		this.value = value;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Cell p = (Cell) obj;
		return this.value == p.value && this.position == p.position ;
	}
	
	public boolean matchingPair(Object obj)
	{
		Cell p = (Cell) obj;
		return this.position != p.position &&  this.value == p.value;
		
	}
	
	public boolean fakeEntry(Object obj)
	{
		Cell p = (Cell) obj;
		return this.position == p.position &&  this.value != p.value;
		
	}

	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return position + "," + value;
	}

}
