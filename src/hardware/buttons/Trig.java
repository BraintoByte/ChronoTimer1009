package hardware.buttons;

import hardware.user.Button;

public class Trig extends Button{

	public Trig(int id) {
		super("TRIG", id);
	}
	
	@Override
	public void setNumber(int[] nbrs) {
		super.setNumber(nbrs);
	}
	
	@Override
	public boolean isValidNumber(int number) {
		return super.isValidNumber(number);
	}
}
