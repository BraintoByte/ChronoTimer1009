package hardware.buttons;

import hardware.user.Button;

/**
 * @author Andy
 * The Reset button, an extension of the Button class @see hardware.user.Button.
 */
public class Reset extends Button{

	/**
	 * @param id - the ID of the button
	 * Constructor for Reset button.
	 */
	public Reset(int id) {
		super("RESET", id);
	}
	
	/* (non-Javadoc)
	 * @see hardware.user.Button#setOn(boolean)
	 */
	@Override
	public void setOn(boolean on) {
		super.setOn(on);
	}
	
	/* (non-Javadoc)
	 * @see hardware.user.Button#isOn()
	 */
	@Override
	public boolean isOn() {
		return super.isOn();
	}
}