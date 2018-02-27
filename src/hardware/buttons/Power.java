package hardware.buttons;

import hardware.user.Button;

public class Power extends Button{
	
	
	/**
	 * @param id
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
}