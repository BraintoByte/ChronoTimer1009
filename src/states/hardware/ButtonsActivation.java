package states.hardware;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import Utils.Printer;
import Utils.Util;
import entitiesStatic.ClockInterface;
import environment.RaceEventsManager;
import hardware.user.ButtonHandler;
import hardware.user.InterfaceHandler;
import interfaces.UI;
import states.State;

public class ButtonsActivation extends State {

	private ButtonHandler btnHandler;
	private Scanner input;
	private boolean fromFile;
	private GraphicalControl controlGui;


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

			if(fromFile){

				State.setState(ui.getSimulator().getFileState());

			}else{

				State.setState(ui.getSimulator().getIdleState());

			}
		}
	}



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


		//		while(true){

		System.out.print("From [F]ile or [C]onsole or [G]raphical? ");
		String str = input.nextLine();
		//
		//			if(str.equalsIgnoreCase("f") || str.equalsIgnoreCase("c") || str.equalsIgnoreCase("POWER") || str.equalsIgnoreCase("EXIT")){
		//
		//				break;
		//				
		//			}
		//		}

		timeSetDefault(str);

		if(str.equalsIgnoreCase("c")){

			System.out.println("\nDear client,\n\nWe know how important trying this product is for you, and especially keeping track of time! \n"
					+ "So we added RACETEST and TESTING command for making a new race in an instant\nand TESTING for displaying it "
					+ "current runs and what's going on, plus as a bonus we have added TIMEDISP which displays the time\n(you can turn it off by retyping the command) and TIMEDFREQ"
					+ " to change the frequency of what rate you are going to display, IMPORTANT: if you type 1 that's not 1s!\nThat's 1 nano second, so if you want 1 second type 1000"
					+ " 10 seconds 10000 and so on!!\n");


			while(!str.equals("POWER") && !str.equals("EXIT")){


//				if(!ui.getSimulator().getClock().isActive()) {


					str = input.nextLine();
					timeSetDefault(str);
					
					
//				}
			}
		}


		if(str.equalsIgnoreCase("f") || str.equalsIgnoreCase("g") || str.equalsIgnoreCase("POWER") || str.equalsIgnoreCase("EXIT")){

			if(str.equals("EXIT")){

				btnHandler.EXIT();

			}else{

				ui.setBtnHandler(btnHandler);
				ui.setRaceManager(new RaceEventsManager());
				Printer.setUI(ui);
				ui.getRaceManager().theseManySensors(100, 100, 100);


				if(str.equalsIgnoreCase("f")){

					fromFile = true;
					InterfaceHandler.setGUI(false);
					System.out.print("Please provide filepath: ");

					str = "";

					while(str.equals("") && !str.matches("([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?")) {
						str = input.nextLine();
					}

					ui.getSimulator().setFilePath(str);
					ui.getSimulator().setFileState(new IOState(ui, input));


				}else if(str.equalsIgnoreCase("g")){
					
					ui.getUserInterface().setVisible(true);
					InterfaceHandler.setGUI(true); // ui.getBtnHandler().getPowerState()
					controlGui = new GraphicalControl(ui, input);
					State.setState(controlGui);
					
				}else{

					btnHandler.setPowerOnOff(true);

				}
			}


			if(!str.isEmpty() && !str.trim().isEmpty()){

				str = str.trim();
				System.out.println("Power " + (ui.getBtnHandler().getPowerState() ? "on" : "off"));

			}
		}
	}

	private void timeSetDefault(String str){

		try{

			if(!ui.getSimulator().getClock().isClockRunning() && !str.contains("TIME")){
				ui.getSimulator().getClock().setTime(new Time((new Date().getTime())));

				ui.getSimulator().getClock().clockStart();
				Thread.sleep(800);

			}

			if(str.contains("TIME")){

				DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SS");
				ui.getSimulator().getClock().setTime(new Time(formatter.parse(str.split("\\s")[1].trim()).getTime()));

			}
			
		}catch(InputMismatchException | InterruptedException | ParseException ex){

			System.out.println("Come on seriously? Check your format!!!!! Wrong input!!!!");

		}

	}

	public boolean isFromFile() {
		return fromFile;
	}
}