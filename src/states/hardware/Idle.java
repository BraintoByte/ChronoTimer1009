package states.hardware;

import static org.junit.Assert.*;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import org.junit.Test;

import Utils.Util;
import entitiesStatic.ClockInterface;
import hardware.external.Sensor;
import interfaces.UI;
import junit.framework.TestCase;
import states.State;

public class Idle extends State {


	private boolean isIdle;
	private Scanner input;
	private int channelSelected;
	private boolean fromFile;
	protected StringBuilder externalStr;

	/**
	 * @param ui
	 * @param input
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


	//	12:00:00.0	TIME 12:00:01.0
	//	12:01:04.0	POWER
	//	12:01:12.0	CONN GATE 1
	//	12:01:15.0	CONN EYE 2
	//	12:01:16.0	CONN GATE 3
	//	12:01:17.0	CONN EYE 4
	//	12:02:00.0	TOG 1
	//	12:02:04.0	TOG 2
	//	12:02:50.0	TRIG 1
	//	12:02:52.0	TRIG 3
	//	12:03:35.0	TRIG 2
	//	12:03:55.0	TRIG 4
	//	13:04:30.0	TRIG 1
	//	13:05:00.0	TRIG 1
	//	13:05:02.0	TRIG 2
	//	13:05:15.0	TRIG 2
	//	13:05:18.0	TRIG 4
	//	13:35:52.0	POWER
	//	13:35:55.0	EXIT

	protected void isFromFile(){

		if(Util.areCommandIssued()){

			fromFile = true;

			if(externalStr != null){

				externalStr.setLength(0);

			}

			externalStr = new StringBuilder(Util.getNextCommand());

			return;

		}

		fromFile = false;

	}


	/**
	 * 
	 */
	protected void idleWait(){

		while(true){

			String str;

			if(fromFile){

				str = externalStr.toString();

			}else{

				str = input.nextLine();

			}
			
			
			if(str.split("\\s").length <= 1){

				switch(str){
				case "POWER":
					isIdle = false;
					ui.getBtnHandler().setPowerOnOff(false);
					ui.getSimulator().getClock().setActive(false);
					State.setState(ui.getSimulator().getInitState());
					break;
				case "CANCEL":
					break;
				case "START":     //Any amount can start in parallel, that's what I have in my notes
					//You cannot start a racers after another has finished, because otherwise how do you keep track of the shift

					ui.getRaceManager().setChannelSelected(1);

					if(!ui.getRaceManager().getCurrentChannel().isEnabled() && ui.getRaceManager().getAmountConnectedOnSelectedChannel() >= 1){

						ui.getRaceManager().setChannelSelected(2);
						boolean tempCondition = ((ui.getRaceManager().racersActive() == 1 && ui.getRaceManager().getCurrentChannel().isEnabled()) || 
								(ui.getRaceManager().racersActive() == 0 && !ui.getRaceManager().getCurrentChannel().isEnabled())) && 
								ui.getRaceManager().getAmountConnectedOnSelectedChannel() >= 1;

								if(tempCondition){

									ui.getRaceManager().setChannelSelected(1);
									ui.getRaceManager().startNRacers(1);

								}
					}

					break;
				case "FINISH":
					ui.getRaceManager().stopLastRace();
					break;
				case "EXIT":
					isIdle = false;
					ui.getBtnHandler().EXIT();
					break;
				case "RESET":
					break;
				}
			}else{

				switch(str.split("\\s")[0].trim()){
				case "TRIG":

					try{

						channelSelected = Integer.parseInt(str.split("\\s")[1].trim());

						if(channelSelected == 1){

							Random rand = new Random();       //You told me it was random, nothing in the guidelines suggests otherwise
							int randomNum = rand.nextInt((250 - 0) + 1) + 0;

							ui.getRaceManager().setChannelSelected(1);

							if(!ui.getRaceManager().getCurrentChannel().isEnabled() && ui.getRaceManager().getAmountConnectedOnSelectedChannel() >= 1){

								ui.getRaceManager().setChannelSelected(2);
								boolean tempCondition = ((ui.getRaceManager().racersActive() == 1 && ui.getRaceManager().getCurrentChannel().isEnabled()) || 
										(ui.getRaceManager().racersActive() == 0 && !ui.getRaceManager().getCurrentChannel().isEnabled())) && 
										ui.getRaceManager().getAmountConnectedOnSelectedChannel() >= 1;

										if(tempCondition){

											ui.getRaceManager().setChannelSelected(1);
											ui.getRaceManager().startNRacers(randomNum);

										}
							}
						}else if(channelSelected == 2){

							ui.getRaceManager().stopLastRace();

						}

					}catch(InputMismatchException e){}


					

					break;
				case "TIME":    //Sets the current local time

					try{

						DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
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
					break;
				case "TOG":
					
					try{

						channelSelected = Integer.parseInt(str.split("\\s")[2]);
						ui.getRaceManager().setChannelSelected(channelSelected);
						ui.getRaceManager().getCurrentChannel().enable(!ui.getRaceManager().getCurrentChannel().isEnabled());

					}catch(InputMismatchException e){}

					break;
				case "CONN":

					try{

						channelSelected = Integer.parseInt(str.split("\\s")[2]);

						ui.getRaceManager().setChannelSelected(channelSelected);
						ui.getRaceManager().CONN(str.split("\\s")[1].equalsIgnoreCase("eye"), 
								str.split("\\s")[1].equalsIgnoreCase("gate"), str.split("\\s")[1].equalsIgnoreCase("pad"));
						System.out.println(ui.getRaceManager().amountConnectedCh1());
						System.out.println(ui.getRaceManager().amountConnectedCh2());


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
					Sensor[] sensors = ui.getRaceManager().allPairedSensors();


					System.out.println("List of paired sensors: ");

					for(Sensor s : sensors){

						System.out.print(s.getId() + " ");

					}

					break;
				}
			}

			if(ui.getSimulator().getClock().isClockRunning()){

				System.out.println(ClockInterface.getCurrentTimeFormatted());

			}

			if(!isIdle){

				isIdle = true;
				break;

			}
		}
	}

	public void setFromFile(boolean fromFile) {
		this.fromFile = fromFile;
	}
}