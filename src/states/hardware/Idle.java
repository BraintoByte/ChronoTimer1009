package states.hardware;


import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

import Utils.Util;
import environment.Race;
import interfaces.UI;
import states.State;

public class Idle extends State {


	private boolean isIdle;
	private Scanner input;
	private int channelSelected;
	private boolean independent;
	private boolean parallel;

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
					System.out.println("Power " + (ui.getBtnHandler().getPowerState() ? "on" : "off"));
					break;

				case "NEWRUN":

					//					ui.getRaceManager().setChannelSelected(1);



					if((ui.getRaceManager().getRaces()[0] != null || 
					!ui.getRaceManager().getRaces()[0].isActive()) && (ui.getRaceManager().getRaces()[1] != null || ui.getRaceManager().getRaces()[1].isActive())){

						setRace();
						ui.getRaceManager().setChannelSelected(1);
						ui.getRaceManager().setChannelSelected(3);

					}

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

					start();

					System.out.println("After start: " + ui.getRaceManager().racersPoolSize());

					break;
				case "FINISH":

					System.out.println("Before finish: " + ui.getRaceManager().racersPoolSize());

					channelSelected = 2;

					if(isRaceActive()){

						ui.getRaceManager().getRaces()[channelSelected - 1].finishRacer(false);

					}

					System.out.println("After finish: " + ui.getRaceManager().racersPoolSize());

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

				case "EVENT":

					if(str.split("\\s")[1].trim().equals("IND")){

						independent = true;
						parallel = false;

					}else{

						parallel = true;
						independent = false;

					}

					setRace();

					break;
				case "EXPORT":

					try{

						int run = Integer.parseInt(str.split("\\s")[1]);

						Race[] tempRaceArray = (Race[]) ui.getRaceManager().getSelectedRun(run);

						for(int i = 0; i < tempRaceArray.length; i++){

							try {
								Util.save(tempRaceArray[i]);
							} catch (IOException e) {
								e.printStackTrace();
							}

						}

					}catch(InputMismatchException e){

						e.printStackTrace();

					}

				case "PRINT":




				case "TRIG":

					System.out.println("Before trig: " + ui.getRaceManager().racersPoolSize());

					trig(str);

					System.out.println("After trig: " + ui.getRaceManager().racersPoolSize());

					break;
				case "NUM":

					try{

						ui.getRaceManager().makeOneRacer(Integer.parseInt(str.split("\\s")[1].trim()));

					}catch(InputMismatchException e){}

					break;
				case "DNF":

					int race = Integer.parseInt(str.split("\\s")[1]) - 1;
					ui.getRaceManager().getRaces()[race].finishRacer(true);

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


		ui.getRaceManager().setChannelSelected(1);

		if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

			ui.getRaceManager().setChannelSelected(2);

			if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

				ui.getRaceManager().getRaces()[0].startNRacers(1);

			}
		}
	}

	private void trig(String str){

		try{

			channelSelected = Integer.parseInt(str.split("\\s")[1].trim());

			if(ui.getValidChannels()[0] == channelSelected || ui.getValidChannels()[2] == channelSelected){

				ui.getRaceManager().setChannelSelected(channelSelected);

				if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

					ui.getRaceManager().setChannelSelected(channelSelected + 1);

					if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

						ui.getRaceManager().setChannelSelected(channelSelected);
						ui.getRaceManager().getRaces()[channelSelected - 1].startNRacers(1);

					}
				}



			}else if(ui.getValidChannels()[1] == channelSelected || ui.getValidChannels()[3] == channelSelected){

				ui.getRaceManager().setChannelSelected(channelSelected);


			}

		}catch(InputMismatchException e){

			e.printStackTrace();

		}
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

	private void setRace(){

		int n = 0;

		if(independent){

			ui.getRaceManager().propRace(1);
			ui.getSimulator().setRun(ui.getSimulator().getRun() + 1);
			ui.getRaceManager().startNewRace(ui.getSimulator().getRun());
			n = 200;

		}else{

			ui.getRaceManager().propRace(2);
			ui.getSimulator().setRun(ui.getSimulator().getRun() + 1);
			ui.getRaceManager().startNewRace(ui.getSimulator().getRun());
			ui.getRaceManager().startNewRace(ui.getSimulator().getRun());
			n = 400;

		}

		for(int i = 0; i <= n; i++){

			ui.getRaceManager().makeOneRacer(i);

		}
	}

	private boolean isRaceActive(){

		if(ui.getRaceManager().getRaces()[channelSelected - 1] == null){

			if(ui.getRaceManager().getRaces()[channelSelected - 1].isActive()){

				return true;

			}
		}

		return false;

	}
}