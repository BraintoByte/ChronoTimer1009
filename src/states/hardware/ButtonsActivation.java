package states.hardware;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import entitiesStatic.ClockInterface;
import environment.RaceEventsManager;
import hardware.user.ButtonHandler;
import interfaces.UI;
import states.State;

public class ButtonsActivation extends State {

	private ButtonHandler btnHandler;
	private Scanner input;
	private boolean fromFile;


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

		System.out.print("From [F]ile or [C]onsole? ");
		String str = input.nextLine();


		if(str.equalsIgnoreCase("c")){

			while(!str.equals("POWER") && !str.equals("EXIT")){

				str = input.nextLine();

				if(str.contains("TIME")){

					try{

						DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
						ui.getSimulator().getClock().setTime(new Time(formatter.parse(str.split("\\s")[1].trim()).getTime()));

						if(!ui.getSimulator().getClock().isClockRunning()){

							ui.getSimulator().getClock().clockStart();

							Thread.sleep(800);

						}

					}catch(ParseException | InterruptedException ex){

						ex.printStackTrace();

					}
				}
			}
		}


		if(str.equalsIgnoreCase("f") || str.equalsIgnoreCase("POWER") || str.equalsIgnoreCase("EXIT")){

			if(str.equals("EXIT")){

				btnHandler.EXIT();

			}else{


				ui.setBtnHandler(btnHandler);
				ui.setRaceManager(new RaceEventsManager());
				ui.getSimulator().getClock().setActive(true);
				//				ui.getRaceManager().propRace(2);
				ui.getRaceManager().theseManySensors(4, 4, 4);


				if(str.equalsIgnoreCase("f")){

					fromFile = true;
					System.out.print("Please provide filepath: ");
					str = input.next();
					ui.getSimulator().setFilePath(str);
					ui.getSimulator().setFileState(new IOState(ui, input));

				}else{

					btnHandler.setPowerOnOff(true);

				}
			}
		}

		if(!str.isEmpty() && !str.trim().isEmpty()){

			System.out.println("Power " + (ui.getBtnHandler().getPowerState() ? "on" : "off"));

		}
	}


	public boolean isFromFile() {
		return fromFile;
	}
}