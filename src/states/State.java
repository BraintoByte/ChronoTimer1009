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
	
	protected void setState(State state){
		
		current = state;
		
	}
	
	public static State getState(){
		
		return current;
		
	}
	
	
	
	protected abstract void setIO(boolean on);
	public abstract void setNumber(int nbr);
	public abstract int getNumber();
	public abstract boolean isIO();
	
//	public States getState();
//	public boolean IO();
//	public int stateIndex();
//	public 
	
	
	

}
