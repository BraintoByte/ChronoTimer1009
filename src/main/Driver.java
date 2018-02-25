package main;

import java.io.IOException;

public class Driver {
	
	public static void main(String[] args) {
		
		Simulator sim = new Simulator();
		try {
			sim.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}