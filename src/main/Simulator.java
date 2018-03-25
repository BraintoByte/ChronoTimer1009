package main;

import static org.junit.Assert.*;
import java.util.Scanner;
import org.junit.Test;
import entitiesStatic.Clock;
import interfaces.UI;
import states.State;
import states.hardware.ButtonsActivation;
import states.hardware.IOState;
import states.hardware.Idle;

/**
 * @author Andy & Matt
 * The Simulator class, apart of the main package of the ChronoTimer, is what drives all of the functions of the system,
 * and is what the user/client interacts with, among other things (e.g. UI).
 */
public class Simulator implements Runnable {    //Chain

	private Thread threadSimulator;
	private Clock clock;
	private boolean running;
	private Scanner input;
	private UI ui;
	private String filePath;
	private int run;
	private boolean activeRun;

	private State initState;
	private State idleState;
	private State fileState;

	/**
	 * Construct for Simulator
	 */
	public Simulator(){}

	/**
	 * Initializes all of the fields of Simulator.
	 * The system is in the initial state after this.
	 */
	public void initialize(){

		input = new Scanner(System.in);
		this.ui = new UI(this);
		this.initState = new ButtonsActivation(ui, input);
		this.idleState = new Idle(ui, input);
		this.clock = new Clock();
		
		State.setState(initState);
		
	}

	/**
	 * Updates the current state is not null.
	 */
	private void update(){
		
		if(State.getState() != null)
			State.getState().update();
	}

	/**
	 * Displays the current state if not null.
	 */
	private void display(){

		if(State.getState() != null)
			State.getState().display();
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

				ticks = 0;
				timer = 0;
			}
		}

		stop();
	}
	
	/**
	 * Starts the Simulator on a thread
	 */
	public synchronized void start(){
		
		if(running)
			return;

		running = true;
		this.threadSimulator = new Thread(this);
		this.threadSimulator.start();
	}

	/**
	 * If running, joins threadSimulator to the original thread effectively stopping the simulator's thread.
	 */
	public synchronized void stop(){
		
		if(!running)
			return;

		running = false;

		try{

			threadSimulator.join();

		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
		
	}

	/**
	 * @return the idle state of this Simulator
	 */
	public State getIdleState() {
		return idleState;
	}
	
	/**
	 * @return the file path of this Simulator
	 */
	public String getFilePath() {
		return filePath;
	}
	
	/**
	 * @param filePath
	 * Sets the file path of this Simulator to filePath.
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * @return the initial state of this Simulator
	 */
	public State getInitState() {
		return initState;
	}
	
	/**
	 * @return the file state of this Simulator
	 */
	public State getFileState() {
		return fileState;
	}
	
	/**
	 * @param fileState
	 * Sets the file state of this Simulator to fileState.
	 */
	public void setFileState(State fileState) {
		this.fileState = fileState;
	}
	
	/**
	 * @return the Clock associated with this Simulator
	 */
	public Clock getClock() {
		return clock;
	}
	
	/**
	 * @return the User Interface (UI) associated with this Simulator
	 */
	public UI getUi() {
		return ui;
	}
	
	/**
	 * @return the current Run's ID number
	 */
	public int getRun() {
		return run;
	}
	
	/**
	 * @param run
	 * Sets the current Run's ID to run.
	 */
	public void setRun(int run) {
		this.run = run;
	}
	
	/**
	 * @return true if there is an active Run
	 */
	public boolean isActiveRun() {
		return activeRun;
	}
	
	/**
	 * @param activeRun
	 * Sets the state of the field activeRun to the truth value of the parameter activeRun.
	 */
	public void setActiveRun(boolean activeRun) {
		this.activeRun = activeRun;
	}
}