package states.hardware;

import java.util.Scanner;

import hardware.user.ButtonHandler;
import interfaces.UI;
import states.State;

public class Idle extends State {


	private boolean isIdle;
	private Scanner input;

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

	private void idleWait(){

		while(true){

			String str = input.next();

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
				}
			}else{

				switch(str.split("\\s")[0]){
				case "TRIG":
					break;
				case "TOG":
					break;
				case "CONN":
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
