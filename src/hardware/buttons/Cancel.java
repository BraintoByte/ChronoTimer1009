package hardware.buttons;

import hardware.Button;

public class Cancel extends Button{

	protected boolean isOn;
	
	public Cancel(int id) {
		super("CANCEL", id);
	}
	
	@Override
	public boolean isOn() {
		return this.isOn;
	}
	
	@Override
	public void setOn(boolean on) {
		this.isOn = on;
	}
}