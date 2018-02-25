package main;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import Utils.Util;
public class Simulator {
    public static Scanner in = new Scanner(System.in);
    
    
    public void start() throws IOException {
        
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
    
    
    public void parse(String line){
    	
    	
    	
    }
    public void compute(String command){
    	
    	
    }
}