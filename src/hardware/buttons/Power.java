package hardware.buttons;

import hardware.user.Button;

public class Power extends Button{
	
	
	public Power(int id) {
		super("POWER", id);
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