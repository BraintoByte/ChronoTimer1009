package states.hardware;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

import Utils.Util;
import entitiesStatic.Clock;
import entitiesStatic.ClockInterface;
import environment.Channels;
import environment.Race;
import exceptions.NoSuchRacerException;
import interfaces.UI;
import states.State;

/**
 * @author Andy & Matt
 * The Idle State, apart of states.hardware, is the State in which the system idles, or waits for the user to input a command.
 */
public class Idle extends State {

	private boolean isIdle;
	private Scanner input;
	private int channelSelected;
	private boolean independent;
	private boolean parallel;

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

		while(true){

			String str;

			str = input.nextLine();

			if(!(str.contains("EVENT") || str.contains("POWER") || str.contains("EXIT") 
					|| str.contains("RESET") || str.contains("TIME") || str.contains("TOG") || str.contains("CONN") || str.contains("TESTING"))
					&& parallel == false && independent == false){

				independent = true;

			}


			if(ui.getSimulator().getRun() == 0){

				ui.getRaceManager().propRace();

			}


			if(str.split("\\s").length <= 1){

				switch(str){
				case "POWER":
					powerOnOff();
					System.out.println("Power " + (ui.getBtnHandler().getPowerState() ? "on" : "off"));
					break;
				case "NEWRUN":

					int enabled = channelsEnabled(1);

					if(enabled > 2 && independent){

						System.out.println("Cannot have more then 1 channel on IND");
						break;

					}

					setRace();
					ui.getSimulator().setActiveRun(true);

					System.out.println("Races is null: " + (ui.getRaceManager().getRaces() == null));
					System.out.println("Channels active: " + channelsEnabled(1));

					break;

				case "ENDRUN":

					if(channelsEnabled(1) > 0){

						for(int i = 0; i < ui.getRaceManager().getRaces().length; i++){

							if(ui.getRaceManager().getRaces()[i] != null){

								ui.getRaceManager().getRaces()[i].stopLastRace();

							}
						}
					}

					ui.getSimulator().setActiveRun(false);
					break;

				case "CANCEL":


					System.out.println("Before cancel: " + ui.getRaceManager().racersPoolSize());

					if(ui.getRaceManager().getChannelSelected() != 1 || ui.getRaceManager().getChannelSelected() != 3){

						channelSelected -= 1;
						ui.getRaceManager().setChannelSelected(channelSelected);

					}

					ui.getRaceManager().getRaces()[channelSelected - 1].CANCEL();

					System.out.println("After cancel: " + ui.getRaceManager().racersPoolSize());

					break;
				case "START":     //Any amount can start in parallel, that's what I have in my notes
					//You cannot start a racers after another has finished, because otherwise how do you keep track of the shift

					System.out.println("Before start: " + ui.getRaceManager().racersPoolSize());

					//						start();

					if(ui.getSimulator().getRun() != 0 && ui.getSimulator().isActiveRun()){

						trig("TRIG 1", false);

					}else{

						System.out.println("NO RUN!");

					}

					System.out.println("After start: " + ui.getRaceManager().racersPoolSize());

					break;
				case "FINISH":

					System.out.println("Before finish: " + ui.getRaceManager().racersPoolSize());

					if(ui.getSimulator().getRun() != 0 && ui.getSimulator().isActiveRun()){

						trig("TRIG 2", false);

					}else{

						System.out.println("NO RUN!");

					}

					System.out.println("After finish: " + ui.getRaceManager().racersPoolSize());

					break;
				case "EXIT":
					isIdle = false;
					ui.getBtnHandler().EXIT();
					break;
				case "RESET":
					
					Channels.reset();
					ui.getRaceManager().reset();
					ui.getSimulator().setRun(0);
					ui.getSimulator().setActiveRun(false);
					powerOnOff();
					powerOnOff();
					break;
				case "TESTING":
					testing();
					break;
				case "RACETEST":
					ui.getRaceManager().makeOneRacer(111);
					ui.getRaceManager().makeOneRacer(112);
					ui.getRaceManager().makeOneRacer(224);
					ui.getRaceManager().makeOneRacer(115);
					ui.getRaceManager().makeOneRacer(227);
					ui.getRaceManager().makeOneRacer(444);
					ui.getRaceManager().makeOneRacer(144);
					ui.getRaceManager().makeOneRacer(777);
					break;
				}
			}else{

				switch(str.split("\\s")[0].trim()){

				case "EVENT":

					if(!ui.getSimulator().isActiveRun()){

						if(str.split("\\s")[1].trim().equals("IND")){

							independent = true;
							parallel = false;

						}else if(str.split("\\s")[1].trim().equals("PARIND")){

							parallel = true;
							independent = false;

						}
					}else{

						System.out.println("Please end the run");

					}

					break;
				case "EXPORT":

					try{

						int run = Integer.parseInt(str.split("\\s")[1]);

						Race[] tempRaceArray = (Race[]) ui.getRaceManager().getSelectedRun(run);

						System.out.print("Please enter the fileName: ");

						String fileName = input.nextLine();

						try {
							Util.save(fileName, tempRaceArray);
						} catch (IOException e) {
							e.printStackTrace();
						}

					}catch(InputMismatchException e){

						e.printStackTrace();

					}

				case "PRINT":

					try{

						Race[] tempRaceArray = (Race[]) ui.getRaceManager().getSelectedRun(Integer.parseInt(str.split("\\s")[1]));


						for(int i = 0; i < tempRaceArray.length; i++){

							if(tempRaceArray[i] != null){

								Stack<Integer> tempStack = tempRaceArray[i].returnBibs();


								while(!tempStack.isEmpty()){

									long start;
									long finish;
									int bid = tempStack.pop();


									ui.getRaceManager().setChannelSelected(tempRaceArray[i].getChannelsActive()[0]);

									start = ui.getRaceManager().getCurrentChannel().retrieve(bid);		// why are the channels in charge of the racers times?!?!
																										// shouldn't the racers use their respective fields?
									ui.getRaceManager().setChannelSelected(tempRaceArray[i].getChannelsActive()[1]);

									finish = ui.getRaceManager().getCurrentChannel().retrieve(bid);

									System.out.println("<" + bid + ">" + " " + "<" + ClockInterface.getTotalTimeFormatted(start, finish) + ">");

								}
							}
						}

					}catch(InputMismatchException ex){

						ex.printStackTrace();

					}



					break;
				case "TRIG":

					System.out.println("Before trig: " + ui.getRaceManager().racersPoolSize());

					if(ui.getSimulator().getRun() != 0 && ui.getSimulator().isActiveRun()){

						trig(str, false);

					}else{

						System.out.println("NO RUN!");

					}

					System.out.println("After trig: " + ui.getRaceManager().racersPoolSize());

					break;
				case "NUM":

					try{

						ui.getRaceManager().makeOneRacer(Integer.parseInt(str.split("\\s")[1].trim()));


					}catch(InputMismatchException e){}

					break;
				case "DNF":

					try{

						int race = Integer.parseInt(str.split("\\s")[1]) - 1;
						ui.getRaceManager().getRaces()[race].finishRacer(true);

					}catch(InputMismatchException ex){

						ex.printStackTrace();

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

					}catch(InputMismatchException e){}

					break;
				case "CONN":

					conn(str);

					break;
				}

				if(!isIdle){

					isIdle = true;
					break;

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

		}catch(ParseException ex){

			ex.printStackTrace();

		}
	}

	/**
	 * Toggles the power of the system.
	 */
	private void powerOnOff(){

		isIdle = false;

		ui.getBtnHandler().setPowerOnOff(!ui.getBtnHandler().getPowerState());
		ui.getSimulator().getClock().setActive(ui.getBtnHandler().getPowerState());

		ui.getSimulator().getClock().setTime(new Date());

		State.setState(ui.getSimulator().getInitState());
	}

	/**
	 * Triggers channel 1 if it is paired to a sensor.
	 */
	private void start(){


		ui.getRaceManager().setChannelSelected(1);

		if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

			ui.getRaceManager().setChannelSelected(2);

			if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

				ui.getRaceManager().getRaces()[0].startNRacers(1);

			}
		}
	}

	/**
	 * @param str
	 * @param DNF
	 * Triggers the channel specified in str, where str = "TRIG<chanID>" and if the next racer to finish Did not Finish (DNF = true),
	 * then their time is recorded as DNF.
	 */
	private void trig(String str, boolean DNF){     //We need to refactor this, is channel enabled method, is channel valid method choice 1 choice 2

		try{

			channelSelected = Integer.parseInt(str.split("\\s")[1].trim());

			ui.getRaceManager().setChannelSelected(channelSelected);

			if(ui.getRaceManager().getCurrentChannel().isEnabled()){

				if(ui.getValidChannels()[0] == channelSelected || ui.getValidChannels()[2] == channelSelected){

					ui.getRaceManager().setChannelSelected(channelSelected);

					if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

						ui.getRaceManager().setChannelSelected(channelSelected + 1);

						if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

							ui.getRaceManager().setChannelSelected(channelSelected);

							if(ui.getRaceManager().getRaces()[channelSelected == 1 ? 0 : 1] != null){

								ui.getRaceManager().getRaces()[channelSelected == 1 ? 0 : 1].startNRacers(1);

							}
						}
					}
				}else if(ui.getValidChannels()[1] == channelSelected || ui.getValidChannels()[3] == channelSelected){

					ui.getRaceManager().setChannelSelected(channelSelected);

					for(int i = 0; i < ui.getRaceManager().getRaces().length; i++){

						if(ui.getRaceManager().getRaces()[i] != null){

							if(ui.getRaceManager().getRaces()[i].getChannelsActive()[1] == channelSelected){

								if(ui.getRaceManager().getRaces()[i].racersActive() != 0){

									ui.getRaceManager().getRaces()[i].finishRacer(DNF);

								}else{

									System.out.println("No more racers!");

								}

								break;
							}
						}
					}
				}
			}else{

				System.out.println("Channel " + channelSelected + " is not toggled!");

			}

		}catch(InputMismatchException e){

			e.printStackTrace();

		}
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

		}catch(InputMismatchException ex){

			ex.printStackTrace();

		}
	}

	/**
	 * Creates a New Run with the specified event type (independent | parallel).
	 */
	private void setRace() {

		if(independent){

			if((ui.getRaceManager().getRaces() != null && ui.getRaceManager().getRaces()[0] != null && ui.getRaceManager().racesActive() == 0) 
					|| ui.getRaceManager().getRaces() == null){

				if(channelsEnabled(1) > 2){

					System.out.println("Cannot have more then 1 channel on IND");
					return;

				}

				ui.getRaceManager().resetIndex();
				ui.getRaceManager().setChannelSelected(1);
				ui.getRaceManager().startNewRace(ui.getSimulator().getRun() + 1);
				ui.getSimulator().setRun(ui.getSimulator().getRun() + 1);


			}

		}else if(parallel){

			if((ui.getRaceManager().getRaces() != null && ui.getRaceManager().getRaces()[0] != null 
					&& !ui.getRaceManager().getRaces()[0].isActive()) || ui.getRaceManager().racesActive() < 2){

				ui.getRaceManager().resetIndex();
				ui.getRaceManager().setChannelSelected(1);
				ui.getRaceManager().startNewRace(ui.getSimulator().getRun() + 1);
				ui.getSimulator().setRun(ui.getSimulator().getRun() + 1);
				ui.getRaceManager().setChannelSelected(3);
				ui.getRaceManager().startNewRace(ui.getSimulator().getRun() + 1);
				ui.getSimulator().setRun(ui.getSimulator().getRun() + 1);

			}
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

	/**
	 * @return true if there is an active Race
	 */
	private boolean isRaceActive(){

		if(ui.getRaceManager().getRaces()[channelSelected - 1] == null){

			System.out.println(channelSelected);

			if(ui.getRaceManager().getRaces()[channelSelected - 1].isActive()){

				return true;

			}
		}

		return false;

	}

	//ONLY FOR TESTING TO PUT IN A FILE!//

	/**
	 * Prints some helpful information for testing/debugging.
	 */
	private void testing(){

		if(ui.getRaceManager().getRaces() != null){

			System.out.println("Races amount: " + ui.getRaceManager().getRaces().length);

			for(int i = 0; i < ui.getRaceManager().getRaces().length; i++){

				if(ui.getRaceManager().getRaces()[i] != null){

					System.out.println("Active on race " + (i + 1) + ": " + ui.getRaceManager().getRaces()[i].isActive());
					System.out.println("RaceNbr: " + ui.getRaceManager().getRaces()[i].getRaceNbr());
					System.out.println("RunNbr: " + ui.getRaceManager().getRaces()[i].getRun());
					System.out.println("Racers active: " + ui.getRaceManager().getRaces()[i].racersActive());
					System.out.println("Races active: " + ui.getRaceManager().racesActive());
					System.out.println("Racers pool size: " + ui.getRaceManager().racersPoolSize());

				}
			}

			System.out.println("Channels active: " + channelsEnabled(1));

		}
	}

	//END TESTING//
}