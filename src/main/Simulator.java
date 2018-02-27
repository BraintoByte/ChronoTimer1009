package main;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import Utils.Util;
import entitiesStatic.Clock;
import hardware.user.ButtonHandler;
import interfaces.UI;
import states.State;
import states.hardware.ButtonsActivation;
import states.hardware.Idle;
public class Simulator implements Runnable {    //Chain

	private Thread treadSimulator;
	private Clock clock;
	private boolean running;
	private Scanner input;
	private UI ui;

	private State initState;
	private State idleState;

	
	/**
	 * 
	 */
	public Simulator(){}
	

	/**
	 * 
	 */
	public void initialize(){

		input = new Scanner(System.in);
		this.ui = new UI(this);
		this.initState = new ButtonsActivation(ui, input);
		this.idleState = new Idle(ui, input);
		this.clock = new Clock();
		
//		System.out.println("In initialize");

		State.setState(initState);
		
	}

	/**
	 * 
	 */
	private void update(){

//		System.out.println("In update");
		
		if(State.getState() != null){

//			System.out.println("In if update");
			State.getState().update();

		}
	}

	/**
	 * 
	 */
	private void display(){

//		System.out.println("In display");
		
		if(State.getState() != null){

//			System.out.println("In if display");
			
			State.getState().display();

		}
	}


	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run(){

		initialize();

		
		int fps = 60;
		double timeUpdate = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;

		while(running){

			
			now = System.nanoTime();
			delta += (now - lastTime) / timeUpdate;
			timer += now - lastTime;
			lastTime = now;

			if(delta >= 1){
				
				
				update();
				display();
				ticks = ticks + 1;
				delta = delta - 1;

			}

			if(timer >= 1000000000){

				System.out.println("Frames per sec: " + ticks);
				ticks = 0;
				timer = 0;

			}
		}

		stop();
		
	}
	

	/**
	 * 
	 */
	public synchronized void start(){

//		System.out.println("In while if 2 run");
		
		if(running){

			return;

		}

		running = true;
		this.treadSimulator = new Thread(this);
		this.treadSimulator.start();

	}
	
	


	/**
	 * 
	 */
	public synchronized void stop(){

		System.out.println("In while if 2 run");
		
		if(!running){

			return;

		}

		running = false;


		try{

			treadSimulator.join();

		}catch(InterruptedException ex){

			ex.printStackTrace();

		}
	}


	/**
	 * @return
	 */
	public State getIdleState() {
		return idleState;
	}
	
	/**
	 * @return
	 */
	public State getInitState() {
		return initState;
	}
	
	public Clock getClock() {
		return clock;
	}
	
//	public UI getUi() {
//		return ui;
//	}
	
	
	//Constructor missing

	//  System.out.print("[F]ile or [C]onsole Input?");
	//  if(in.next().equalsIgnoreCase("F")){
	//      System.out.println("File name?");
	//     //compute(Utils.Util.readFileAsString("CTSRUN1.txt")); 
	//      boolean goodFile = false;
	//      while(!goodFile) {
	//          try {
	//              in = new Scanner(new FileReader(in.next()));
	//          } catch (Exception FileNotFoundException) {
	//              System.out.println("File not found, please try again.");
	//          }
	//      }
	//  }   


	//  public static void start() {   //Change this with a try and catch!
	//      
	//  	Scanner input = new Scanner(System.in);
	//  	
	//  	
	//  	
	//  	
	//       
	//  }


	//will command computations

	//    public static void parse(String line){    //This does not work
	//    	
	//    	Scanner sc = new Scanner(line);
	//    	String found;
	//    	
	//    	while(sc.hasNext()){
	//    		if((found = sc.next()).equals("POWER")){
	//    			compute(found);
	//    		} else if((found = sc.next()).equals("CONN")){
	//    			compute(found + " " + sc.next() + " " + sc.next());
	//    		} else if((found = sc.next()).equals("GATE")){
	//    			compute(found + " " + sc.next() + " " + sc.next());
	//    		} else if((found = sc.next()).equals("TRIG")){
	//    			compute(found + " " + sc.next());
	//    		} else if((found = sc.next()).equals("TOG")){
	//    			compute(found + " " + sc.next());
	//    		} else if((found = sc.next()).equals("EXIT")){
	//    			compute(found);
	//    		}
	//    	}
	//    }

	//    public void compute(String command){
	//    	
	//    	
	//    }
}