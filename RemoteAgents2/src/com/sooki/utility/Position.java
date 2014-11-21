package com.sooki.utility;

public class Position {
	final int x;
	final int y;
	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		      return 13*y+43*x;
		 }
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return x + "," + y;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Position p = (Position) obj;
		return this.x == p.x && this.y == p.y;
	}
		
}


