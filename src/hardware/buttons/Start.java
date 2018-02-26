package hardware.buttons;

import hardware.user.Button;

public class Start extends Button{

	public Start(int id) {
		super("START", id);
	}
	
	@Override
	public boolean isOn() {
		return super.isOn();
	}
	
	@Override
	public void setOn(boolean on) {
		super.setOn(on);
	}
}
