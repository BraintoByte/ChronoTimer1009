package hardware.buttons;

import hardware.Button;

public class Tog extends Button{

	public Tog(int id) {
		super("TOG", id);
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
