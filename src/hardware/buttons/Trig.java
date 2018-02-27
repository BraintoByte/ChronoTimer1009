package hardware.buttons;

import hardware.user.Button;

public class Trig extends Button{

	/**
	 * @param id
	 */
	public Trig(int id) {
		super("TRIG", id);
	}
	
	/* (non-Javadoc)
	 * @see hardware.user.Button#setNumber(int[])
	 */
	@Override
	public void setNumber(int[] nbrs) {
		super.setNumber(nbrs);
	}
	
	/* (non-Javadoc)
	 * @see hardware.user.Button#isValidNumber(int)
	 */
	@Override
	public boolean isValidNumber(int number) {
		return super.isValidNumber(number);
	}
}
