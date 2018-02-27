package states.hardware;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

import entitiesStatic.ClockInterface;
import hardware.external.Sensor;
import hardware.user.ButtonHandler;
import interfaces.UI;
import states.State;

public class Idle extends State {


	private boolean isIdle;
	private Scanner input;
	private int channelSelected;

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


	//TODO: CHANGE THE SENSOR ASSIGNMENT MECHANISM, IT SHOULD BE CONN <TYPE> <CHANNEL> NOW IT IS CONN <TYPE> <NUMBER>, MAYBE CHANGE IT?! PLEASE MAKE IT LESS RETARDED!

	//XXX: ALL COMMENTS HERE ARE FOR ME NOT FOR YOU DON'T DO ABSOULUTELY ANYTHING IN THE CODE! THANK YOU!


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



	/**
	 * 
	 */
	private void idleWait(){

		while(true){

			String str = input.nextLine();

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

				case "TOG":
					break;
				case "CONN":

					try{

						channelSelected = Integer.parseInt(str.split("\\s")[2]);

						ui.getRaceManager().setChannelSelected(channelSelected);
						ui.getRaceManager().CONN(str.split("\\s")[1].equalsIgnoreCase("eye"), 
								str.split("\\s")[1].equalsIgnoreCase("gate"), str.split("\\s")[1].equalsIgnoreCase("pad"));
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
}
