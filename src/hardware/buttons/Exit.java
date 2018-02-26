package hardware.buttons;

import hardware.user.Button;

public class Exit extends Button {
	
	
	public Exit(int id) {
		super("EXIT", id);
	}

	@Override
	public void setOn(boolean isOn) {
		super.setOn(isOn);
	}
	
	@Override
	public boolean isOn(){
		
		return super.isOn();
		
	}
	
	@Override
	public void EXIT(){
		
		super.EXIT();
		
	}
}