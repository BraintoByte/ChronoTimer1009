package main;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import Utils.Util;
public class Simulator {
	
    public static Scanner in = new Scanner(System.in);   //Make it private, don't initiate here!
    
    
    //Constructor missing
    
    
    public void start() throws IOException {   //Change this with a try and catch!
        
        System.out.print("[F]ile or [C]onsole Input?");
        if(in.next().equalsIgnoreCase("F")){
            System.out.println("File name?");
           //compute(Utils.Util.readFileAsString("CTSRUN1.txt")); 
            boolean goodFile = false;
            while(!goodFile) {
                try {
                    in = new Scanner(new FileReader(in.next()));
                } catch (Exception FileNotFoundException) {
                    System.out.println("File not found, please try again.");
                }
            }
        }    
    }
    
    //will command computations
    
    public void parse(String line){    //This does not work
    	
    	Scanner sc = new Scanner(line);
    	String found;
    	
    	while(sc.hasNext()){
    		if((found = sc.next()).equals("POWER")){
    			compute(found);
    		} else if((found = sc.next()).equals("CONN")){
    			compute(found + " " + sc.next() + " " + sc.next());
    		} else if((found = sc.next()).equals("GATE")){
    			compute(found + " " + sc.next() + " " + sc.next());
    		} else if((found = sc.next()).equals("TRIG")){
    			compute(found + " " + sc.next());
    		} else if((found = sc.next()).equals("TOG")){
    			compute(found + " " + sc.next());
    		} else if((found = sc.next()).equals("EXIT")){
    			compute(found);
    		}
    	}
    
  	
    }
    public void compute(String command){
    	
    	
    }
}