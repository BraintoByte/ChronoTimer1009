package hardware.buttons;

import hardware.user.Button;

public class Cancel extends Button{

	
	/**
	 * @param id
	 */
	public Cancel(int id) {
		super("CANCEL", id);
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
}