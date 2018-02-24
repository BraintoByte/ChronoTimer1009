package hardware.buttons;

import hardware.Button;
import states.State;
import states.StateManager;

public class Power extends Button{
	
	private boolean isOn;
	
	public Power(int id) {
		super("POWER", id);
	}

	@Override
	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}
	
	@Override
	public boolean isOn(){
		
		return this.isOn;
		
	}
}