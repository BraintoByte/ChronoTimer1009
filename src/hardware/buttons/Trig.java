package hardware.buttons;

import hardware.Button;

public class Trig extends Button{

	public Trig(String name, int id) {
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
