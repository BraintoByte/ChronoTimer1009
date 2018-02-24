package hardware.buttons;

import hardware.Button;

public class Exit extends Button {
	
	protected boolean isOn;
	
	public Exit(int id) {
		super("EXIT", id);
	}

	@Override
	public boolean isOn() {
		return isOn();
	}
	
	@Override
	public void setOn(boolean on) {
		
		this.isOn = on;
		
	}
}