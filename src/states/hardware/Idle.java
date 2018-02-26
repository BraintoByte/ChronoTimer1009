package states.hardware;

import java.util.InputMismatchException;
import java.util.Scanner;

import hardware.user.ButtonHandler;
import interfaces.UI;
import states.State;

public class Idle extends State {


	private boolean isIdle;
	private Scanner input;
	private int channelSelected;

	public Idle(UI ui, Scanner input) {
		super(ui, input);
		this.input = input;
		this.isIdle = true;
	}

	@Override
	public void update() {

		idleWait();

	}

	@Override
	public void display() {

		idleWait();

	}
	
	
	//TODO: CHANGE THE SENSOR ASSIGNMENT MECHANISM, IT SHOULD BE CONN <TYPE> <CHANNEL> NOW IT IS CONN <TYPE> <NUMBER>, MAYBE CHANGE IT?! PLEASE MAKE IT LESS RETARDED!
	
	//XXX: ALL COMMENTS HERE ARE FOR ME NOT FOR YOU DON'T DO ABSOULUTELY ANYTHING IN THE CODE! THANK YOU!

	private void idleWait(){

		while(true){

			String str = input.nextLine();

			if(str.split("\\s").length <= 1){

				switch(str){
				case "POWER":
					isIdle = false;
					ui.getBtnHandler().setPowerOnOff(false);
					State.setState(ui.getSimulator().getInitState());
					break;
				case "CANCEL":
					break;
				case "TIME":
					System.out.println(ui.getBtnHandler().getCurrentTime());
					break;
				case "START":
					break;
				case "FINISH":
					break;
				case "EXIT":
					isIdle = false;
					ui.getBtnHandler().EXIT();
					break;
				case "RESET":
					break;
				case "TEST":
					System.out.println("Sensors connected on: " + channelSelected);
					break;
				}
			}else{

				switch(str.split("\\s")[0].trim()){
				case "TRIG":
					break;
				case "TOG":
					break;
				case "CONN":
					
					try{
						
						if(channelSelected == 0){
							
							channelSelected++;
							
						}
						
						ui.getRaceManager().setChannelSelected(channelSelected);
						ui.getRaceManager().theseManySensors(4, 4);
						ui.getRaceManager().CONN(Integer.parseInt(str.split("\\s")[2]), str.split("\\s")[1].equalsIgnoreCase("eye") ? true : false);
						System.out.println(ui.getRaceManager().amountConnectedCh1());
						
					}catch(InputMismatchException ex){
						
						ex.printStackTrace();
						
					}
					break;
				case "TEST":
					
					try{
						
						channelSelected = Integer.parseInt(str.split("\\s")[1]);
						
					}catch(InputMismatchException ex){
						
						ex.printStackTrace();
						
					}
					
					ui.getRaceManager().setChannelSelected(channelSelected);
					System.out.println("Sensors connected on channel: " + channelSelected + " is " + ui.getRaceManager().getAmountConnectedOnSelectedChannel());
					break;
				}
			}
			
			if(!isIdle){
				
				isIdle = true;
				break;
				
			}
		}
	}
}
