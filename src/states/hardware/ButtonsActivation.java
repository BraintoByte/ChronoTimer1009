package states.hardware;

import java.util.Scanner;

import environment.RaceEventsManager;
import hardware.user.ButtonHandler;
import interfaces.UI;
import states.State;

public class ButtonsActivation extends State {
	
	private ButtonHandler btnHandler;
	private Scanner input;
	
	
	/**
	 * @param ui
	 * @param input
	 */
	public ButtonsActivation(UI ui, Scanner input){
		
		super(ui, input);
		this.btnHandler = new ButtonHandler();
		this.input = input;
		
	}

	/* (non-Javadoc)
	 * @see states.State#update()
	 */
	@Override
	public void update() {
		
		if(!btnHandler.getPowerState()){
			
			offState();
			
		}else{
			
			
			State.setState(ui.getSimulator().getIdleState());
			
		}
	}
	
	
	/* (non-Javadoc)
	 * @see states.State#display()
	 */
	@Override
	public void display() {
		
		if(!btnHandler.getPowerState()){
			
			offState();
			
		}
		
	}
	
	/**
	 * 
	 */
	private void offState(){
		
		
		String str = input.next();
		
		while(!str.equals("POWER") && !str.equals("EXIT")){
			
			str = input.next();
			
		}
		
//		System.out.println("Power is ON!");
		
//		input.close();
		
		if(str.equals("EXIT")){
			
			btnHandler.EXIT();
		
		}else{
			
			System.out.println("Power is ON!");
			btnHandler.setPowerOnOff(true);
			ui.setBtnHandler(btnHandler);
			ui.setRaceManager(new RaceEventsManager());
			ui.getRaceManager().theseManySensors(4, 4, 4);
			ui.getSimulator().getClock().setActive(true);
//			initialize();
			
		}
	}
}
