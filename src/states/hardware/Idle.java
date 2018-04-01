package states.hardware;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

import Utils.Util;
import entitiesDynamic.Racer;
import entitiesStatic.ClockInterface;
import environment.Channels;
import environment.Run.Race;
import exceptions.NoSuchRacerException;
import interfaces.UI;
import states.State;

/**
 * @author Andy & Matt
 * The Idle State, apart of states.hardware, is the State in which the system idles, or waits for the user to input a command.
 */
public class Idle extends State {

	public enum Run_Types {

		IND,
		PARIND,
		GROUP;

	}


	private boolean isIdle;
	private Scanner input;
	private int channelSelected;
	private boolean displayingTime;


	/**
	 * @param ui
	 * @param input
	 * Constructor for Idle that takes UI and Scanner as parameters.
	 */
	public Idle(UI ui, Scanner input) {
		super(ui, input);
		this.input = input;
		this.isIdle = true;
	}

	/* (non-Javadoc)
	 * @see states.State#update()
	 */
	@Override
	public void update() {

		idleWait();
	}

	/* (non-Javadoc)
	 * @see states.State#display()
	 */
	@Override
	public void display() {

		idleWait();

	}

	/**
	 * The idle method for this State which runs continuesly, waiting for user input.
	 */
	protected void idleWait(){

		//Testing only//

		//		for(int i = 1; i < 5; i++){
		//
		//			ui.getRaceManager().setChannelSelected(i);
		//
		//			if(!ui.getRaceManager().getCurrentChannel().isPairedToSensor()){
		//
		//				conn("CONN EYE " + i);
		//
		//			}
		//		}
		//
		//		if(channelSelected > 4){
		//
		//			channelSelected = 1;
		//
		//		}

		//End for testing only//
		String str;

		while(true){

			str = input.nextLine();

			if("POWER".equals(str)) {

				powerOnOff();

			}else if("EXIT".equals(str)) {

				isIdle = false;
				ui.getBtnHandler().EXIT();

			}else if(ui.getBtnHandler().getPowerState()) {

				if(!(str.contains("EVENT") || str.contains("POWER") || str.contains("EXIT") 
						|| str.contains("RESET") || str.contains("TIME") || str.contains("TOG") || str.contains("CONN") || str.contains("TESTING"))){

					if(ui.getRaceManager().getType() == null){

						System.out.println("You have not initialized what type of even it is, so by default it's "
								+ "initialized to independent");
						ui.getRaceManager().setType(Run_Types.IND);

					}
				}


				if(str.split("\\s").length <= 1){

					switch(str){
					//					case "POWER":
					//						powerOnOff();
					//						System.out.println("Power " + (ui.getBtnHandler().getPowerState() ? "on" : "off"));
					//						break;
					case "NEWRUN":

						int enabled = channelsEnabled(1);

						if(enabled > 2 && ui.getRaceManager().getType() == Run_Types.IND){

							System.out.println("Cannot have more then 1 channel on IND");
							break;

						}

						if(!ui.getRaceManager().isRunActive()){

							ui.getRaceManager().setNewRun();
							System.out.println("New run created!");

						}else{

							System.out.println("Run is active please stop the last run before starting a new one!");

						}

						break;
					case "ENDRUN":


						if(channelsEnabled(1) > 0){

							ui.getRaceManager().keepRecord();
							ui.getRaceManager().endRun();

						}
						break;
					case "CANCEL":				//Redo!


						//						System.out.println("Before cancel: " + ui.getRaceManager().racersPoolSize());
						//							// new
						//						
						//						if(ui.getRaceManager().getChannelSelected() == 1 || ui.getRaceManager().getChannelSelected() == 2){
						//							ui.getRaceManager().getRaces()[0].CANCEL();
						//						}
						//						else if(ui.getRaceManager().getChannelSelected() == 3 || ui.getRaceManager().getChannelSelected() == 4) {
						//							ui.getRaceManager().getRaces()[1].CANCEL();
						//						}

						// new
						//						System.out.println("After cancel: " + ui.getRaceManager().racersPoolSize());

						break;
					case "START":     //Any amount can start in parallel, that's what I have in my notes
						//You cannot start a racers after another has finished, because otherwise how do you keep track of the shift

						//						ui.getRaceManager().keepRecord();

						ui.getRaceManager().trig("TRIG 1", false);

						break;
					case "FINISH":

						ui.getRaceManager().trig("TRIG 2", false);

						break;
					case "TIMEDISP":
						displayingTime = !displayingTime;
						ui.getSimulator().getClock().setDisplayCurrent(displayingTime);
						System.out.println("Displaying time: " + displayingTime);
						break;
						//					case "EXIT":
						//						isIdle = false;
						//						ui.getBtnHandler().EXIT();
						//						break;
					case "RESET":
						powerOnOff();   // turns power off then back on
						powerOnOff();
						break;
					case "RACETEST":

						try{

							//						channelSelected = Integer.parseInt(str.split("\\s")[1]);

							for (int i = 0; i < 4; i++) {

								ui.getRaceManager().setChannelSelected(i + 1);
								ui.getRaceManager().getCurrentChannel().enable(!ui.getRaceManager().getCurrentChannel().isEnabled());
								System.out.println("Channel: " + (i + 1) + " togged!");

							}

						}catch(InputMismatchException | NumberFormatException e){

							System.out.println("Wrong input!");

						}

						conn("CONN GATE 1");
						conn("CONN GATE 2");
						conn("CONN GATE 3");
						conn("CONN GATE 4");

						System.out.println("All sensors are connected your are good to go!");

						ui.getRaceManager().setUpRaceForArbitrarySimulation();


						break;
					}
				}else{

					switch(str.split("\\s")[0].trim()){

					case "EVENT":

						if(str.split("\\s")[1].trim().equals("IND")){

							ui.getRaceManager().setType(Run_Types.IND);


						}else if(str.split("\\s")[1].trim().equals("PARIND")){

							ui.getRaceManager().setType(Run_Types.PARIND);

						}
						break;
					case "TIMEDFREQ":

						try{

							ui.getSimulator().getClock().setTimeDispFreq(Integer.parseInt(str.split("\\s")[1]));
							System.out.println("Setting it to: " + (Integer.parseInt(str.split("\\s")[1]) / 1000) + "s");

						}catch(InputMismatchException | NumberFormatException e){}

						break;
					case "EXPORT":

						try{

							int run = Integer.parseInt(str.split("\\s")[1]);
							Iterator<Race> it = ui.getRaceManager().getRecords();
							Race temp = null;

							while(it.hasNext()){

								temp = it.next();

								if(temp != null && temp.getRun() == run){

									break;

								}
							}

							System.out.print("Please enter the fileName: ");

							String fileName = input.nextLine();

							try {
								Util.save(fileName, false, temp);
							} catch (IOException e) {

								e.printStackTrace();
								System.out.println("Something went wrong in saving the file, please try again!");

							}

						} catch(InputMismatchException | NumberFormatException e){

							System.out.println("Wrong input!");

						}catch(ConcurrentModificationException e){

							System.out.println("No export while runs are active! Please try again later!");

						}

						break;
					case "TRIG":


						if(ui.getRaceManager().racesActive() == 0){

							System.out.print("No races active!");
							break;

						}


						ui.getRaceManager().trig(str, false);
						break;
					case "NUM":

						try{

							ui.getRaceManager().makeRacers(Integer.parseInt(str.split("\\s")[1].trim()));


						}catch(InputMismatchException | NumberFormatException e){

							System.out.println("Wrong input!");

						}

						break;
					case "PRINT":

						try{

							keepPrint(Integer.parseInt(str.split("\\s")[1]));

						}catch(InputMismatchException | NumberFormatException e){

							System.out.println("WOOOOOOOOOPS!");

						}

						break;
					case "DNF":

						// new
						try {

							int lane = Integer.parseInt(str.split("\\s")[1]);

							if(lane > 0 && lane < 8){

								switch((lane % 2)){
								case 0:
									ui.getRaceManager().trig("TRIG " + (lane + 2), true);
									break;
								case 1:
									ui.getRaceManager().trig("TRIG " + (lane + 1), true);
									break;
								}
							}


						}catch(NumberFormatException | InputMismatchException e){

							System.out.println("OOPS");

						}

						break;
					case "TIME":    //Sets the current local time

						setTime(str);

						break;
					case "TOG":

						try{

							channelSelected = Integer.parseInt(str.split("\\s")[1]);

							if(channelSelected > 0 && channelSelected <= 4){

								ui.getRaceManager().setChannelSelected(channelSelected);
								ui.getRaceManager().getCurrentChannel().enable(!ui.getRaceManager().getCurrentChannel().isEnabled());

							}

						}catch(InputMismatchException | NumberFormatException e){

							System.out.println("Wrong input!");

						}

						break;
					case "CONN":

						if(str.split("\\s").length == 3){

							conn(str);

						}else{

							System.out.println("Seriously are we pulling the half written command trick?!");

						}

						break;
					}

					if(!isIdle){

						isIdle = true;
						break;

					}
				}
			}
		}
	}

	/**
	 * @param str
	 * Sets the time of the system to the string represented by str in the form "HH:mm:ss"
	 */
	private void setTime(String str){

		try{

			DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SS");
			ui.getSimulator().getClock().setTime(new Time(formatter.parse(str.split("\\s")[1].trim()).getTime()));


			if(!ui.getSimulator().getClock().isClockRunning()){

				ui.getSimulator().getClock().clockStart();


				try {

					Thread.sleep(800);

				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}

		}catch(ParseException | InputMismatchException ex){

			System.out.println("You know it's wrong to input that!");

		}
	}

	/**
	 * Toggles the power of the system.
	 */
	private void powerOnOff(){

		boolean isOn = !ui.getBtnHandler().getPowerState();
		ui.getBtnHandler().setPowerOnOff(isOn);
		ui.getSimulator().getClock().setActive(isOn);

		// happens for powering on only
		if(isOn) {
			ui.getRaceManager().resetRun();
			ui.getSimulator().getClock().setTime(new Date());
		}

		System.out.println("Power " + (ui.getBtnHandler().getPowerState() ? "on" : "off"));

	}

	/**
	 * @param str
	 * Connects the channel and sensor specified in str, where str = "CONN <Sensor> <chanID>"
	 */

	private void conn(String str){


		try{

			channelSelected = Integer.parseInt(str.split("\\s")[2]);

			if(channelSelected > 0 && channelSelected <= 4){

				ui.getRaceManager().setChannelSelected(channelSelected);

				if(!ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

					ui.getRaceManager().CONN(str.split("\\s")[1].equalsIgnoreCase("eye"), 
							str.split("\\s")[1].equalsIgnoreCase("gate"), str.split("\\s")[1].equalsIgnoreCase("pad"));
				}
			}

		}catch(InputMismatchException | NumberFormatException ex){

			System.out.println("Please don't do that, come on! You know that's wrong!");

		}
	}



	//	private boolean isGoodToGo(){
	//
	//		Iterator<Race> it = ui.getRaceManager().getRecords();
	//		int count = 0;
	//
	//		while(it.hasNext()){
	//
	//			Race temp = it.next();
	//
	//			if(temp != null && temp.getRun() == ui.getSimulator().getRun()){
	//
	//				return true;
	//
	//			}
	//		}
	//
	//		return false;
	//	}

	public void keepPrint(int run){     //Should the runs be replaced?!

		try{

			Iterator<Race> it = ui.getRaceManager().getRecords();

			//			if(tempRaceArray == null){
			//
			//				System.out.println("NOPE NO RUNS!");
			//
			//			}
			//
			//			if(tempRaceArray.length == 0){
			//
			//				System.out.println("No run! Are you sure that you started a run OR you probably ended a run prematurely. This latter feature was added\n"
			//						+ " because nothing was specified in the sprint on what would happen if you ended a run when some racers are still going\n"
			//						+ " should all those racers be DNF should the race being cancelled or should all the racers finish at that exact time?!\n"
			//						+ " we opted for the easiest most logical solution, the race is cancelled because something wrong might have happened so\n"
			//						+ " there is no race if that was the case! Try to properly finish all racers make sure the race is really over! THX\n");
			//
			//			}

			Race temp = null;

			if(!it.hasNext()){

				System.out.println("No race!");

			}else{

				while(it.hasNext()){

					temp = it.next();

					if(temp != null && temp.getRun() == run){

						break;

					}
				}

				if(temp.getRun() == run){

					Iterator<Racer> racer = temp.getRecord();
					System.out.println("Run <" + run + ">");

					while(racer.hasNext()){

						Racer tempRacer = racer.next();

						if(tempRacer != null && !tempRacer.isDNF()){

							System.out.println("<" + tempRacer.getBib() + ">" + " " + "<" + ClockInterface.getTotalTimeFormatted(tempRacer.getStartInLong(), tempRacer.getFinishInLong()) + ">");

						}else{

							if(tempRacer.isDNF()){

								System.out.println("<" + tempRacer.getBib() + ">" + " " + "<" + " DNF" + ">");

							}
						}
					}
				}else{

					System.out.println("Run selected does not exists!");

				}
			}

		}catch(ConcurrentModificationException e){

			System.out.println("Something went wrong in retriving the race, did you ended run?!");

		}
	}

	/**
	 * @param from
	 * @return the number of enabled channels with ID in the range [from, 4)
	 * Counts the number of enabled channels from parameter 'from'.
	 */
	private int channelsEnabled(int from){

		int count = 0;

		for(int i = from; i < 4; i += 2){

			ui.getRaceManager().setChannelSelected(i);

			if(ui.getRaceManager().getCurrentChannel().isEnabled()){

				count++;

			}
		}

		return count;

	}

	//	/**
	//	 * @return true if there is an active Race
	//	 */
	//	private boolean isRaceActive(){
	//
	//		if(ui.getRaceManager().getRaces()[channelSelected - 1] == null){
	//
	//			System.out.println(channelSelected);
	//
	//			if(ui.getRaceManager().getRaces()[channelSelected - 1].isActive()){
	//
	//				return true;
	//
	//			}
	//		}
	//
	//		return false;
	//
	//	}

	//ONLY FOR TESTING TO PUT IN A FILE!//

	//	/**
	//	 * Prints some helpful information for testing/debugging.
	//	 */
	//	private void testing(){
	//
	//		if(ui.getRaceManager().getRaces() != null){
	//
	//			System.out.println("Races amount: " + ui.getRaceManager().getRaces().length);
	//
	//			for(int i = 0; i < ui.getRaceManager().getRaces().length; i++){
	//
	//				if(ui.getRaceManager().getRaces()[i] != null){
	//
	//					System.out.println("Active on race " + (i + 1) + ": " + ui.getRaceManager().getRaces()[i].isActive());
	//					System.out.println("RaceNbr: " + ui.getRaceManager().getRaces()[i].getRaceNbr());
	//					System.out.println("RunNbr: " + ui.getRaceManager().getRaces()[i].getRun());
	//					System.out.println("Racers active: " + ui.getRaceManager().getRaces()[i].racersActive());
	//					System.out.println("Races active: " + ui.getRaceManager().racesActive());
	//					System.out.println("Racers pool size: " + ui.getRaceManager().racersPoolSize());
	//					System.out.println("Current time: " + ClockInterface.getCurrentTimeFormatted());
	//
	//				}
	//			}
	//
	//			System.out.println("Channels active: " + channelsEnabled(1));
	//
	//		}
	//	}

	//END TESTING//
}