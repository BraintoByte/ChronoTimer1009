package hardware.user;

import java.util.Scanner;

import entitiesStatic.Clock;

public class ButtonHandler {

	private class ButtonManager {
		

		protected Button getPower() {
			return Button.buttons[0];
		}
		protected Button getReset() {
			return Button.buttons[1];
		}
		protected Button getStart() {
			return Button.buttons[2];
		}
		protected Button getFinish() {
			return Button.buttons[3];
		}
		protected Button getTog() {
			return Button.buttons[4];
		}
		protected Button getTrig() {
			return Button.buttons[5];
		}
		protected Button getExit() {
			return Button.buttons[6];
		}
		protected Button getCancel() {
			return Button.buttons[7];
		}
		protected Button getTime(){
			
			return Button.buttons[8];
			
		}
	}
	
	private ButtonManager manager;
	
	public ButtonHandler(){
		
		manager = new ButtonManager();
		
		
//		if(manager.getPower().isOn){
//			
//			
//			
//		}else{
//			
//			offState();
//			
//		}
	}
	
	
	public void EXIT(){
		
		manager.getExit().EXIT();
		
	}
	
	public boolean getPowerState(){
		
		return manager.getPower().isOn;
		
	}
	
	public boolean setPowerOnOff(boolean on){
		
		manager.getPower().setOn(on);
		
		System.out.println(manager.getPower().isOn);
		
		return manager.getPower().isOn;  //For later in the develpment!
		
	}
	
	public boolean getOtherStates(){
		
		return manager.getCancel().isOn;
		
	}
	
	public String getCurrentTime(){
		
		return manager.getTime().getCurrentTime();
		
	}
	
}