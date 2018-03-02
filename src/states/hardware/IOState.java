package states.hardware;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import Utils.Util;
import entitiesStatic.ClockInterface;
import interfaces.UI;
import states.State;

public class IOState extends State {
	
	private int channelSelected;
	private boolean independent;

	public IOState(UI ui, Scanner input) {
		super(ui, input);
		input();
	}

	@Override
	public void update() {
		
		input();
		
	}

	@Override
	public void display() {
		
		input();
		
	}
	
	
	public void input(){
		
		Util.readFileAsString(ui.getSimulator().getFilePath());
		
		while(Util.areCommandIssued()){
			
			String str = Util.getNextCommand();
			
			if(str.split("\\s").length <= 1){

				switch(str){
				case "POWER":
					ui.getBtnHandler().setPowerOnOff(false);
					ui.getSimulator().getClock().setActive(false);
					State.setState(ui.getSimulator().getInitState());
					break;
				case "CANCEL":

					ui.getRaceManager().setChannelSelected(1);

					if(ui.getRaceManager().racersActive() >= 1){

						ui.getRaceManager().CANCEL();

					}

					break;
				case "START":     //Any amount can start in parallel, that's what I have in my notes
					//You cannot start a racers after another has finished, because otherwise how do you keep track of the shift

					if(independent){


						ui.getRaceManager().setChannelSelected(1);

						if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

							ui.getRaceManager().setChannelSelected(2);

							if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

								ui.getRaceManager().setChannelSelected(1);
								ui.getRaceManager().startNRacers(1);

							}
						}
					}

					break;
				case "FINISH":

					if(ui.getRaceManager().racersActive() > 0){

						ui.getRaceManager().stopLastRace();

					}
					break;
				case "EXIT":
					ui.getBtnHandler().EXIT();
					break;
				case "RESET":
					break;
				}
			}else{

				switch(str.split("\\s")[0].trim()){
				case "EVENT IND":

					independent = true;

					break;
				case "TRIG":

					try{

						channelSelected = Integer.parseInt(str.split("\\s")[1].trim());

						if(channelSelected == 1){

							Random rand = new Random();       //You told me it was random, nothing in the guidelines suggests otherwise
							int randomNum = rand.nextInt((250 - 0) + 1) + 0;

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
					break;
				case "NUM":

					try{

						ui.getRaceManager().makeOneRacer(Integer.parseInt(str.split("\\s")[1].trim()));

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

						channelSelected = Integer.parseInt(str.split("\\s")[1]);
						ui.getRaceManager().setChannelSelected(channelSelected);
						ui.getRaceManager().getCurrentChannel().enable(!ui.getRaceManager().getCurrentChannel().isEnabled());

					}catch(InputMismatchException e){}

					break;
				case "CONN":

					try{

						channelSelected = Integer.parseInt(str.split("\\s")[2]);
						ui.getRaceManager().setChannelSelected(channelSelected);

						if(!ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

							ui.getRaceManager().CONN(str.split("\\s")[1].equalsIgnoreCase("eye"), 
									str.split("\\s")[1].equalsIgnoreCase("gate"), str.split("\\s")[1].equalsIgnoreCase("pad"));

							if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

								System.out.println("PAIRED ON: " + channelSelected);

							}
						}

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
					System.out.println("Sensors connected on channel: " + channelSelected + " is " + 
							ui.getRaceManager().getCurrentChannel().isPairedToSensor());
					break;
				}
			}

			if(ui.getSimulator().getClock().isClockRunning()){

				System.out.println(ClockInterface.getCurrentTimeFormatted());

			}
		}
	}
}