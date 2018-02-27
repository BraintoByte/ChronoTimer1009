package hardware.buttons;

import hardware.user.Button;

public class Finish extends Button{

	/**
	 * @param id
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
