package hardware.buttons;

import hardware.user.Button;

public class Cancel extends Button{

	
	public Cancel(int id) {
		super("CANCEL", id);
	}
	
	@Override
	public void setOn(boolean isOn) {
		super.setOn(isOn);
	}
	
	@Override
	public boolean isOn(){
		return super.isOn();
	}
}