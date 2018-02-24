package states;

import states.buttons.IO;
import states.buttons.IONum;

public abstract class State implements IO, IONum {
	
	private static State current = null;
	protected StateManager manager;
	
	
	/* BUTTONS */
	
	public State(StateManager manager){
		
		this.manager = manager;
		
	}
	
	public static void setState(State state){
		
		current = state;
		
	}
	
	public static State getState(){
		
		return current;
		
	}
	
	public abstract boolean isIO();
	public abstract int number();
	
//	public States getState();
//	public boolean IO();
//	public int stateIndex();
//	public 
	
	
	

}
