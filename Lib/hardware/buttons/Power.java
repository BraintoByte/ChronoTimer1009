package hardware.buttons;

import javax.swing.JToggleButton;
import hardware.user.Button;

/**
 * @author Andy
 * The Power button, an extension of the Button class @see hardware.user.Button.
 */
public class Power extends Button{
	
	private volatile JToggleButton button;
	
	/**
	 * @param id - the ID of the button
	 * Constructor for Power button.
	 */
	public Power(int id) {
		super("POWER", id);
	}

	/* (non-Javadoc)
	 * @see hardware.user.Button#setOn(boolean)
	 */
	@Override
	public void setOn(boolean isOn) {
		super.setOn(isOn);
	}
	
	/* (non-Javadoc)
	 * @see hardware.user.Button#isOn()
	 */
	@Override
	public boolean isOn(){
		return super.isOn();
	}

	public void setButton(JToggleButton button) {
		this.button = button;
	}

	public JToggleButton getButton() {
		return this.button;
	}
	
}