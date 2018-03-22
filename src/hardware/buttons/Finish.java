package hardware.buttons;

import hardware.user.Button;

/**
 * @author Andy
 * The Finish button, an extension of the Button class @see hardware.user.Button.
 */
public class Finish extends Button{

	/**
	 * @param id - the ID of the button
	 * Constructor for Finish button.
	 */
	public Finish(int id) {
		super("FINISH", id);
	}
	
	/* (non-Javadoc)
	 * @see hardware.user.Button#isOn()
	 */
	@Override
	public boolean isOn() {
		return super.isOn();
	}
	
	/* (non-Javadoc)
	 * @see hardware.user.Button#setOn(boolean)
	 */
	@Override
	public void setOn(boolean on) {
		super.setOn(on);
	}
}
