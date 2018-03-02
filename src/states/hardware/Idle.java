package states.hardware;


import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

import interfaces.UI;
import states.State;

public class Idle extends State {


	private boolean isIdle;
	private Scanner input;
	private int channelSelected;
	private boolean independent;

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


	/**
	 * 
	 */
	protected void idleWait(){

		while(true){

			String str;

			str = input.nextLine();


			if(str.split("\\s").length <= 1){

				switch(str){
				case "POWER":
					powerOnOff();
					break;
				case "CANCEL":

					ui.getRaceManager().setChannelSelected(1);

					if(ui.getRaceManager().racersActive() >= 1){

						ui.getRaceManager().CANCEL();

					}

					break;
				case "START":     //Any amount can start in parallel, that's what I have in my notes
					//You cannot start a racers after another has finished, because otherwise how do you keep track of the shift

					start();


					break;
				case "FINISH":

					if(ui.getRaceManager().racersActive() > 0){

						ui.getRaceManager().stopLastRace();

					}
					break;
				case "EXIT":
					isIdle = false;
					ui.getBtnHandler().EXIT();
					break;
				case "RESET":

					powerOnOff();   //You said it's like power on/off

					break;
				}
			}else{

				switch(str.split("\\s")[0].trim()){


				case "EVENT IND":

					independent = true;

					break;
				case "TRIG":

					trig(str);

					break;
				case "NUM":

					try{

						ui.getRaceManager().makeOneRacer(Integer.parseInt(str.split("\\s")[1].trim()));

					}catch(InputMismatchException e){}

					break;
				case "TIME":    //Sets the current local time

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
					break;
				case "TOG":

					try{

						channelSelected = Integer.parseInt(str.split("\\s")[1]);

						if(channelSelected > 0 && channelSelected <= 2){

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


	private void powerOnOff(){

		isIdle = false;
		ui.getBtnHandler().setPowerOnOff(false);
		ui.getSimulator().getClock().setActive(false);
		State.setState(ui.getSimulator().getInitState());
	}


	private void start(){

		if(independent == true){

			ui.getRaceManager().setChannelSelected(1);

			if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

				ui.getRaceManager().setChannelSelected(2);

				if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

					ui.getRaceManager().setChannelSelected(1);
					ui.getRaceManager().startNRacers(1);
					

				}
			}
		}
	}

	private void trig(String str){

		try{

			channelSelected = Integer.parseInt(str.split("\\s")[1].trim());

			if(channelSelected == 1){

				ui.getRaceManager().setChannelSelected(1);

				if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

					ui.getRaceManager().setChannelSelected(2);

					if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

						ui.getRaceManager().setChannelSelected(1);
						ui.getRaceManager().startNRacers(ui.getRaceManager().racersPoolSize());
						
					}
				}

			}else if(channelSelected == 2){

				ui.getRaceManager().finishRacer();

			}
		}catch(InputMismatchException e){}
	}


	private void conn(String str){


		try{

			channelSelected = Integer.parseInt(str.split("\\s")[2]);

			if(channelSelected > 0 && channelSelected <= 2){

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
}