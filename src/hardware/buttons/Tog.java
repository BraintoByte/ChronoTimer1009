package hardware.buttons;

import hardware.user.Button;

/**
 * @author Andy
 * The Tog button, an extension of the Button class @see hardware.user.Button.
 */
public class Tog extends Button{

	/**
	 * @param id - the ID of the button
	 * Constructor for Tog button.
	 */
	public Tog(int id) {
		super("TOG", id);
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
