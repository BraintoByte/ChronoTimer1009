package hardware.user;

/**
 * @author Andy & Matt
 * The ButtonHandler class, apart of the hardware.user package, is in charge of setting the Button's states among other things.
 */
public class ButtonHandler {

	/**
	 * @author Andy
	 * The ButtonManager class, an inner class of ButtonHandler, is responsible for getting all of the Buttons.
	 */
	private class ButtonManager {

		/**
		 * @return the Power Button
		 */
		protected Button getPower() {
			return Button.buttons[0];
		}
		
		/**
		 * @return the Reset Button
		 */
		protected Button getReset() {
			return Button.buttons[1];
		}
		
		/**
		 * @return the Start Button
		 */
		protected Button getStart() {
			return Button.buttons[2];
		}
		
		/**
		 * @return the Finish Button
		 */
		protected Button getFinish() {
			return Button.buttons[3];
		}
		
		/**
		 * @return the Tog Button
		 */
		protected Button getTog() {
			return Button.buttons[4];
		}
		
		/**
		 * @return the Trig Button
		 */
		protected Button getTrig() {
			return Button.buttons[5];
		}
		
		/**
		 * @return the Exit Button
		 */
		protected Button getExit() {
			return Button.buttons[6];
		}
		
		/**
		 * @return the Cancel Button
		 */
		protected Button getCancel() {
			return Button.buttons[7];
		}
		
		/**
		 * @return the Time Button
		 */
		protected Button getTime(){
			return Button.buttons[8];
		}
	}

	private ButtonManager manager;

	/**
	 * Constructor for ButtonHandler
	 */
	public ButtonHandler(){
		manager = new ButtonManager();
	}

	/**
	 * Exits the simulator
	 */
	public void EXIT(){
		manager.getExit().EXIT();
	}

	/**
	 * @return true if the Button is on
	 */
	public boolean getPowerState(){
		return manager.getPower().isOn;
	}

	/**
	 * @param state
	 * @return true if the Button is on
	 * 
	 * Sets the Buttons power state to the parameter state.
	 */
	public boolean setPowerOnOff(boolean state){

		manager.getPower().setOn(state);
		return manager.getPower().isOn;  //For later in the develpment!
	}

	/**
	 * @return true if the Cancel Button is on
	 */
	public boolean getCancelState(){
		return manager.getCancel().isOn;
	}

	/**
	 * @return the current time of the system
	 */
	public String getCurrentTime(){
		return manager.getTime().getCurrentTime();
	}	
}