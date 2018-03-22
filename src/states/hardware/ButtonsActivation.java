package states.hardware;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import entitiesStatic.ClockInterface;
import environment.RaceEventsManager;
import hardware.user.ButtonHandler;
import interfaces.UI;
import states.State;

/**
 * @author Andy & Matt
 * The ButtonsActivation class, apart of the states.hardware package,
 * is responsible for displaying and updating all of the buttons in the ChronoTimer system.
 */
public class ButtonsActivation extends State {

	private ButtonHandler btnHandler;
	private Scanner input;
	private boolean fromFile;

	/**
	 * @param ui
	 * @param input
	 * Constructor for ButtonsActivation that takes UI and Scanner as parameters
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

		if(!btnHandler.getPowerState())
			offState();

		else{

			if(fromFile)
				State.setState(ui.getSimulator().getFileState());

			else
				State.setState(ui.getSimulator().getIdleState());

		}
	}

	/* (non-Javadoc)
	 * @see states.State#display()
	 */
	@Override
	public void display() {

		if(!btnHandler.getPowerState())
			offState();	
	}

	/**
	 * State of the system when the power is off.
	 * (Initial state after fist power up of Simulator)
	 */
	private void offState(){

		String str = "";
		do {

			System.out.print("From [F]ile or [C]onsole? ");
			str = input.next();

		}while(!("c".equalsIgnoreCase(str) || "f".equalsIgnoreCase(str) || "EXIT".equals(str)));

		// console input
		if(str.equalsIgnoreCase("c")){

			while(!str.equals("POWER") && !str.equals("EXIT")){

				// sets the clock to the current time!!!
				// no need to use command TIME upon start up
				try{

					ui.getSimulator().getClock().setTime(new Time((new Date().getTime())));

					if(!ui.getSimulator().getClock().isClockRunning()){

						ui.getSimulator().getClock().clockStart();

						Thread.sleep(800);
					}

				}catch(InterruptedException ex){

					ex.printStackTrace();
				}

				str = input.nextLine();
			}
			
		}
		// file input or EXIT
		if(str.equalsIgnoreCase("f") || str.equalsIgnoreCase("POWER") || str.equalsIgnoreCase("EXIT")){

			if(str.equals("EXIT")) {
				btnHandler.EXIT();

			}else{

				ui.setBtnHandler(btnHandler);
				ui.setRaceManager(new RaceEventsManager());
				ui.getSimulator().getClock().setActive(true);
				//ui.getRaceManager().propRace(2);
				ui.getRaceManager().theseManySensors(4, 4, 4);
				
				if(str.equalsIgnoreCase("f")){
					
					fromFile = true;
					System.out.print("Please provide filepath: ");
					str = input.next();
					ui.getSimulator().setFilePath(str);
					ui.getSimulator().setFileState(new IOState(ui, input));
					
				}else {
					btnHandler.setPowerOnOff(true);
				}
			}
		}
		
		if(!str.isEmpty() && !str.trim().isEmpty()){

			System.out.println("Power " + (ui.getBtnHandler().getPowerState() ? "on" : "off"));

		}

	}

	/**
	 * @return true if input is from a file
	 */
	public boolean isFromFile() {
		return fromFile;
	}

}