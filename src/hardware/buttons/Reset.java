package hardware.buttons;

import hardware.user.Button;

public class Reset extends Button{

	public Reset(int id) {
		super("RESET", id);
	}
	
	
	@Override
	public void setOn(boolean on) {
		super.setOn(on);
	}
	
	@Override
	public boolean isOn() {
		return super.isOn();
	}
}