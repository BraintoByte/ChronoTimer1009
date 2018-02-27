package hardware.buttons;

import hardware.user.Button;

public class Reset extends Button{

	/**
	 * @param id
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