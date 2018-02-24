package hardware;

import states.State;
import states.StateManager;

public abstract class Button extends State {
	
	private String name;
	
	public Button(StateManager manager) {
		super(manager);
		
	}
	
//	public boolean isPressed(){
//		
//		
//		
//	}
	
	protected abstract void setName();
	protected abstract void setState();
	

}
