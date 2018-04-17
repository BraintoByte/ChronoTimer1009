package hardware.buttons;

import hardware.user.Button;

/**
 * @author Andy
 * The Trig button, an extension of the Button class @see hardware.user.Button.
 */
public class Trig extends Button{

	/**
	 * @param id - the ID of the button
	 * Constructor for Trig button.
	 */
	public Trig(int id) {
		super("TRIG", id);
	}
	
	/* (non-Javadoc)
	 * @see hardware.user.Button#setNumber(int[])
	 */
	@Override
	public void validateAndSetNumbers(int[] nbrs) {
		super.validateAndSetNumbers(nbrs);
	}
	
	/* (non-Javadoc)
	 * @see hardware.user.Button#isValidNumber(int)
	 */
	@Override
	public boolean isValidNumber(int number) {
		return super.isValidNumber(number);
	}
}
