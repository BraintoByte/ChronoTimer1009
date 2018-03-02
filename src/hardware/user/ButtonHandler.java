package hardware.user;

import java.util.Scanner;

import entitiesStatic.Clock;

public class ButtonHandler {

	private class ButtonManager {
		

		/**
		 * @return
		 */
		protected Button getPower() {
			return Button.buttons[0];
		}
		/**
		 * @return
		 */
		protected Button getReset() {
			return Button.buttons[1];
		}
		/**
		 * @return
		 */
		protected Button getStart() {
			return Button.buttons[2];
		}
		/**
		 * @return
		 */
		protected Button getFinish() {
			return Button.buttons[3];
		}
		/**
		 * @return
		 */
		protected Button getTog() {
			return Button.buttons[4];
		}
		/**
		 * @return
		 */
		protected Button getTrig() {
			return Button.buttons[5];
		}
		/**
		 * @return
		 */
		protected Button getExit() {
			return Button.buttons[6];
		}
		/**
		 * @return
		 */
		protected Button getCancel() {
			return Button.buttons[7];
		}
		/**
		 * @return
		 */
		protected Button getTime(){
			
			return Button.buttons[8];
			
		}
	}
	
	private ButtonManager manager;
	
	/**
	 * 
	 */
	public ButtonHandler(){
		manager = new ButtonManager();
	}
	
	
	/**
	 * 
	 */
	public void EXIT(){
		
		manager.getExit().EXIT();
		
	}
	
	/**
	 * @return
	 */
	public boolean getPowerState(){
		
		return manager.getPower().isOn;
		
	}
	
	/**
	 * @param on
	 * @return
	 */
	public boolean setPowerOnOff(boolean on){
		
		manager.getPower().setOn(on);
		return manager.getPower().isOn;  //For later in the develpment!
		
	}
	
	/**
	 * @return
	 */
	public boolean getOtherStates(){
		
		return manager.getCancel().isOn;
		
	}
	
	/**
	 * @return
	 */
	public String getCurrentTime(){
		
		return manager.getTime().getCurrentTime();
		
	}
}