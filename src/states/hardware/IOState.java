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
import java.util.Scanner;
import java.util.Stack;
import Utils.Util;
import entitiesDynamic.Racer;
import entitiesStatic.ClockInterface;
import environment.Channels;
import environment.Run.Race;
import hardware.user.InterfaceHandler;
import interfaces.UI;
import states.State;
import states.hardware.Idle.Run_Types;

/**
 * @author Andy & Matt
 *
 */
public class IOState extends State {

	private int channelSelected;
	private boolean displayingTime;

	/**
	 * @param ui
	 * @param input
	 * Constructor for IOState that takes UI and Scanner as parameters.
	 */
	public IOState(UI ui, Scanner input) {
		super(ui, input);
		input();
	}

	/* (non-Javadoc)
	 * @see states.State#update()
	 */
	@Override
	public void update() {
		input();
	}

	/* (non-Javadoc)
	 * @see states.State#display()
	 */
	@Override
	public void display() {

		input();
	}

	/**
	 * The input method for this State which runs continuesly, waiting for user input.
	 */
	public void input(){

		Util.readFileAsString(ui.getSimulator().getFilePath());

		while(Util.areCommandIssued()){

			String str = Util.getNextCommand();
			String time = "TIME " + Util.getTimeCommand();
			InterfaceHandler.inputCommand(time);				//"TIME " + Util.getTimeCommand()
			InterfaceHandler.inputCommand(str);

			//			if("POWER".equals(str)) {
			//				
			//				InterfaceHandler.powerOnOff();
			//				
			//				
			//			}else if("EXIT".equals(str)) {
			//				
			//				ui.getBtnHandler().EXIT();
			//				
			//			}else if(ui.getBtnHandler().getPowerState()) {
			//
			//				if(!(str.contains("EVENT") || str.contains("POWER") || str.contains("EXIT") 
			//						|| str.contains("RESET") || str.contains("TIME") || str.contains("TOG") || str.contains("CONN") || str.contains("TESTING"))){
			//
			//					if(ui.getRaceManager().getType() == null){
			//
			//						System.out.println("You have not initialized what type of even it is, so by default it's "
			//								+ "initialized to independent!");
			//						ui.getRaceManager().setType(Run_Types.IND);
			//
			//					}
			//
			//				}
			//
			//				if(str.split("\\s").length <= 1){
			//
			//					switch(str){
			//					case "NEWRUN":
			//
			//						int enabled = InterfaceHandler.channelsEnabled(1);
			//
			//						if(enabled > 2 && ui.getRaceManager().getType() == Run_Types.IND){
			//
			//							System.out.println("Cannot have more then 1 channel on IND");
			//							break;
			//
			//						}
			//
			//						if(!ui.getRaceManager().isRunActive()){
			//
			//							ui.getRaceManager().setNewRun();
			//							System.out.println("New run created!");
			//
			//						}else{
			//
			//							System.out.println("Run is active please stop the last run before starting a new one!");
			//
			//						}
			//
			//						break;
			//					case "ENDRUN":
			//
			//						ui.getRaceManager().keepRecord();
			//						ui.getRaceManager().endRun();
			//
			//						System.out.println("Run ended");
			//						break;
			//					case "CANCEL":				//Redo!
			//						
			//						if(channelSelected % 2 == 0){
			//							
			//							ui.getRaceManager().CANCEL();
			//							
			//						}else{
			//							
			//							channelSelected--;
			//							ui.getRaceManager().setChannelSelected(channelSelected);
			//							ui.getRaceManager().CANCEL();
			//							
			//						}
			//						
			//						break;
			//					case "SWAP":
			//						if(ui.getRaceManager().swap())
			//							System.out.println("Swap sucessful");
			//						else
			//							System.out.println("Swap failed");
			//						break;
			//					case "START":     
			//						//Any amount can start in parallel, that's what I have in my notes
			//						//You cannot start a racers after another has finished, because otherwise how do you keep track of the shift
			//
			//						ui.getRaceManager().trig("TRIG 1", false);
			//
			//						break;
			//					case "FINISH":
			//
			//						ui.getRaceManager().trig("TRIG 2", false);
			//
			//						break;
			//					case "TIMEDISP":
			//						displayingTime = !displayingTime;
			//						ui.getSimulator().getClock().setDisplayCurrent(displayingTime);
			//						System.out.println("Displaying time: " + displayingTime);
			//						break;
			//					case "RESET":
			//						InterfaceHandler.powerOnOff();   // turns power off then back on
			//						InterfaceHandler.powerOnOff();
			//						break;
			//					case "RACETEST":
			//
			//						try{
			//
			//							for (int i = 0; i < 8; i++) {
			//
			//								ui.getRaceManager().setChannelSelected(i + 1);
			//								ui.getRaceManager().getCurrentChannel().enable(!ui.getRaceManager().getCurrentChannel().isEnabled());
			//								System.out.println("Channel: " + (i + 1) + " togged!");
			//
			//							}
			//
			//						}catch(InputMismatchException | NumberFormatException e){
			//
			//							System.out.println("Wrong input!");
			//
			//						}
			//
			//						InterfaceHandler.conn("CONN GATE 1");
			//						InterfaceHandler.conn("CONN GATE 2");
			//						InterfaceHandler.conn("CONN GATE 3");
			//						InterfaceHandler.conn("CONN GATE 4");
			//						InterfaceHandler.conn("CONN EYE 5");
			//						InterfaceHandler.conn("CONN EYE 6");
			//						InterfaceHandler.conn("CONN EYE 7");
			//						InterfaceHandler.conn("CONN EYE 8");
			//
			//						System.out.println("All sensors are connected your are good to go!");
			//
			//						ui.getRaceManager().setUpRaceForArbitrarySimulation();
			//
			//
			//						break;
			//					}
			//				}else{
			//
			//					switch(str.split("\\s")[0].trim()){
			//
			//					case "EVENT":
			//
			//						switch(str.split("\\s")[1].trim()){
			//						case "IND":
			//							ui.getRaceManager().setType(Run_Types.IND);
			//							break;
			//						case "PARIND":
			//							ui.getRaceManager().setType(Run_Types.PARIND);
			//							break;
			//						case "GRP":
			//							ui.getRaceManager().setType(Run_Types.GRP);
			//							break;
			//						case "PARGRP":
			//							ui.getRaceManager().setType(Run_Types.PARGRP);
			//							break;
			//						}
			//						break;
			//					case "TIMEDFREQ":
			//
			//						try{
			//
			//							ui.getSimulator().getClock().setTimeDispFreq(Integer.parseInt(str.split("\\s")[1]));
			//							System.out.println("Setting it to: " + (Integer.parseInt(str.split("\\s")[1]) / 1000) + "s");
			//
			//						}catch(InputMismatchException | NumberFormatException e){}
			//
			//						break;
			//					case "CLR":
			//						
			//						try {
			//							int bib = Integer.parseInt(str.split("\\s")[1]);
			//						
			//							if(ui.getRaceManager().clearRacer(bib)) 
			//								System.out.println("Racer " + bib + " cleared from pool");
			//	
			//							
			//						}catch(InputMismatchException | NumberFormatException e) {}
			//						break;
			//					case "EXPORT":
			//
			//						try{
			//
			//							int run = Integer.parseInt(str.split("\\s")[1]);
			//							Iterator<Race> it = ui.getRaceManager().getRecords();
			//							Race temp = null;
			//
			//							while(it.hasNext()){
			//
			//								temp = it.next();
			//
			//								if(temp != null && temp.getRun() == run){
			//
			//									break;
			//
			//								}
			//							}
			//
			//							String fileName = "\\RUN"+ run +".txt";
			//
			//							try {
			//								Util.save(fileName, true, temp);
			//							} catch (IOException e) {
			//
			//								e.printStackTrace();
			//								System.out.println("Something went wrong in saving the file, please try again!");
			//
			//							}
			//
			//						} catch(InputMismatchException | NumberFormatException e){
			//
			//							System.out.println("Wrong input!");
			//
			//						}catch(ConcurrentModificationException e){
			//
			//							System.out.println("No export while runs are active! Please try again later!");
			//
			//						}
			//
			//						break;
			//					case "PRINT":
			//
			//						try{
			//
			//							InterfaceHandler.keepPrint(Integer.parseInt(str.split("\\s")[1]));
			//
			//						}catch(InputMismatchException | NumberFormatException e){
			//
			//							System.out.println("WOOOOOOOOOPS!");
			//
			//						}
			//
			//						break;
			//					case "TRIG":
			//
			//						ui.getRaceManager().trig(str, false);
			//						break;
			//					case "NUM":
			//
			//						try{
			//
			//							ui.getRaceManager().makeRacers(Integer.parseInt(str.split("\\s")[1].trim()));
			//
			//
			//						}catch(InputMismatchException | NumberFormatException e){
			//
			//							System.out.println("Wrong input!");
			//
			//						}
			//
			//						break;
			//					case "DNF":
			//
			//						try {
			//
			//							int lane = Integer.parseInt(str.split("\\s")[1]);
			//
			//							if(lane > 0 && lane < 8){
			//
			//								switch((lane % 2)){
			//								case 0:
			//									ui.getRaceManager().trig("TRIG " + (lane + 2), true);
			//									break;
			//								case 1:
			//									ui.getRaceManager().trig("TRIG " + (lane + 1), true);
			//									break;
			//								}
			//							}
			//
			//
			//						}catch(NumberFormatException | InputMismatchException e){
			//
			//							System.out.println("OOPS");
			//						}
			//
			//						break;
			//					case "TIME":    //Sets the current local time
			//
			//						InterfaceHandler.setTime(str);
			//
			//						break;
			//					case "TOG":
			//
			//						try{
			//
			//							channelSelected = Integer.parseInt(str.split("\\s")[1]);
			//
			//							if(channelSelected > 0 && channelSelected <= 4){
			//
			//								ui.getRaceManager().setChannelSelected(channelSelected);
			//								ui.getRaceManager().getCurrentChannel().enable(!ui.getRaceManager().getCurrentChannel().isEnabled());
			//
			//							}
			//
			//						}catch(InputMismatchException | NumberFormatException e){
			//
			//							System.out.println("Wrong input!");
			//
			//						}
			//
			//						break;
			//					case "CONN":
			//
			//						if(str.split("\\s").length == 3){
			//
			//							InterfaceHandler.conn(str);
			//
			//						}else{
			//
			//							System.out.println("Seriously are we pulling the half written command trick?!");
			//
			//						}
			//
			//						break;
			//					}
			//				}
			//		}
		}

		System.exit(1);

	}
}