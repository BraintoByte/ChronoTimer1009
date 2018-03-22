package hardware.buttons;

import hardware.user.Button;

/**
 * @author Andy
 * The Time button, an extension of the Button class @see hardware.user.Button.
 */
public class Time extends Button{

	/**
	 * @param id - the ID of the button
	 * Constructor for Time button.
	 */
	public Time(int id) {
		super("TIME", id);
	}
	
	/* (non-Javadoc)
	 * @see hardware.user.Button#getCurrentTime()
	 */
	@Override
	public String getCurrentTime(){
		return super.getCurrentTime();
	}
}