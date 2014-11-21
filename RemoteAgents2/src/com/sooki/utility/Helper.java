package com.sooki.utility;

import java.util.Random;

public class Helper {

	public static Random generator = new Random();
	
	
	
	public static int RandomPosition(int boardWidth)
	{
		
		int number = generator.nextInt(boardWidth*boardWidth);
		return number;
		
	}
	public static void delay(int delay)
	{
	try {
		Thread.sleep(delay);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
}
