package com.sooki.utility;

public class TwoValueHolder {
	 int x;
	 int y;
	public TwoValueHolder()
	{}
	
	public TwoValueHolder(int x, int y) {
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
		TwoValueHolder p = (TwoValueHolder) obj;
		return this.x == p.x && this.y == p.y;
	}
		
}


