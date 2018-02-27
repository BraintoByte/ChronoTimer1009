package hardware.buttons;

import hardware.user.Button;

public class Exit extends Button {
	
	
	/**
	 * @param id
	 */
	public Exit(int id) {
		super("EXIT", id);
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
	
	/* (non-Javadoc)
	 * @see hardware.user.Button#EXIT()
	 */
	@Override
	public void EXIT(){
		
		super.EXIT();
		
	}
}