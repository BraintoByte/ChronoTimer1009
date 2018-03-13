package states.hardware;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.TimeZone;

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

					powerOnOff();
					
					break;
				case "CANCEL":

					if(ui.getRaceManager().getChannelSelected() != 1 || ui.getRaceManager().getChannelSelected() != 3){
						
						channelSelected -= 1;
						ui.getRaceManager().setChannelSelected(channelSelected);
						
					}
					
					ui.getRaceManager().finishOneRacerOnRaceSelected();

					break;
				case "START":     //Any amount can start in parallel, that's what I have in my notes
					//You cannot start a racers after another has finished, because otherwise how do you keep track of the shift

					start();

					break;
				case "FINISH":

					if(ui.getBtnHandler().getPowerState()){

						if(ui.getRaceManager().racersActive() > 0){

							ui.getRaceManager().stopLastRace();

						}
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

					if(ui.getBtnHandler().getPowerState()){

						independent = true;

					}

					break;
				case "TRIG":
					
					trig(str);

					break;
				case "NUM":

					if(ui.getBtnHandler().getPowerState()){

						try{

							ui.getRaceManager().makeOneRacer(Integer.parseInt(str.split("\\s")[1].trim()));

						}catch(InputMismatchException e){}
					}
					break;
				case "TIME":    //Sets the current local time
					try{

						DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SS");
						String temp = str.split("\\s")[1].trim();
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

					if(ui.getBtnHandler().getPowerState()){
						try{

							channelSelected = Integer.parseInt(str.split("\\s")[1]);
							ui.getRaceManager().setChannelSelected(channelSelected);
							ui.getRaceManager().getCurrentChannel().enable(!ui.getRaceManager().getCurrentChannel().isEnabled());

						}catch(InputMismatchException e){}
					}

					break;
				case "CONN":

					conn(str);

					break;
				}
			}
		}
	}

	private void powerOnOff(){

		ui.getBtnHandler().setPowerOnOff(!ui.getBtnHandler().getPowerState());
		ui.getSimulator().getClock().setActive(!ui.getBtnHandler().getPowerState());
		
	}


	private void start(){

		if(ui.getBtnHandler().getPowerState()){

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
		}
	}

	private void trig(String str){

		if(ui.getBtnHandler().getPowerState()){
			try{

				channelSelected = Integer.parseInt(str.split("\\s")[1].trim());
				
				if(ui.getValidChannels()[0] == channelSelected || ui.getValidChannels()[2] == channelSelected){
					
					ui.getRaceManager().setChannelSelected(channelSelected);
					
					if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){
						
						ui.getRaceManager().setChannelSelected(channelSelected + 1);
						
						if(ui.getRaceManager().getCurrentChannel().isPairedToSensor()){
							
							ui.getRaceManager().setChannelSelected(channelSelected);
							ui.getRaceManager().startNewRace();
							
						}
					}
					
					
					
				}else if(ui.getValidChannels()[1] == channelSelected || ui.getValidChannels()[3] == channelSelected){
					
					ui.getRaceManager().setChannelSelected(channelSelected);
					
					
				}
				
			}catch(InputMismatchException e){
				
				e.printStackTrace();
				
			}

		}
	}


	private void conn(String str){
		
		if(ui.getBtnHandler().getPowerState()){
			try{

				channelSelected = Integer.parseInt(str.split("\\s")[2]);
				ui.getRaceManager().setChannelSelected(channelSelected);

				if(!ui.getRaceManager().getCurrentChannel().isPairedToSensor()){

					ui.getRaceManager().CONN(str.split("\\s")[1].equalsIgnoreCase("eye"), 
							str.split("\\s")[1].equalsIgnoreCase("gate"), str.split("\\s")[1].equalsIgnoreCase("pad"));
				}

			}catch(InputMismatchException ex){

				ex.printStackTrace();

			}
		}
	}

}