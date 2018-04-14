package hardware.user;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Iterator;

import Utils.Util;
import entitiesDynamic.Racer;
import entitiesStatic.ClockInterface;
import environment.Run.Race;
import interfaces.UI;
import states.hardware.Idle.Run_Types;

public class InterfaceHandler {

	private UserGraphical userInterface;
	private static boolean isGUI;
	private static UI ui;
	private static int channelSelected;
	private static boolean displayingTime;
	private static int count;

	public InterfaceHandler(UI ui){

		this.ui = ui;

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(UserGraphical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(UserGraphical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(UserGraphical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(UserGraphical.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		this.userInterface = UserGraphical.getSingleton();
		//		this.userInterface.setVisible(false);     //Temporary true

	}




	public static void setTime(String str){

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
	public static void powerOnOff(){

		boolean isOn = !ui.getBtnHandler().getPowerState();
		ui.getBtnHandler().setPowerOnOff(isOn);
		ui.getSimulator().getClock().setActive(isOn);

		// happens for powering on only
		if(isOn) {
			ui.getRaceManager().resetRun();
			ui.getSimulator().getClock().setTime(new Date());
		}

		InterfaceHandler.setGUI(ui.getBtnHandler().getPowerState());

		if(isGUI){

			ui.getUserInterface().getTxtAConsole().append("Power " + (ui.getBtnHandler().getPowerState() ? "on...\n" : "off...\n"));

		}else{

			ui.getUserInterface().getTxtAConsole().append("Power " + (ui.getBtnHandler().getPowerState() ? "on...\n" : "off...\n"));
			System.out.println("Power " + (ui.getBtnHandler().getPowerState() ? "on" : "off"));

		}
	}





	/**
	 * @param str
	 * Connects the channel and sensor specified in str, where str = "CONN <Sensor> <chanID>"
	 */

	public static void conn(String str){


		try{

			int channelSelected = Integer.parseInt(str.split("\\s")[2]);

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


	public static void keepPrint(int run){     //Should the runs be replaced?!

		try{

			Iterator<Race> it = ui.getRaceManager().getRecords();

			boolean exists = false;
			Race temp = null;

			if(!it.hasNext()){

				System.out.println("No race!");

			}else{

				if(isGUI){
					
					ui.getUserInterface().getjTextArea1().append("Run <" + run + ">\n");
					
				}else{
					
					System.out.println("Run <" + run + ">");
				
				}
				
				boolean isGroup = false;
				int groupID = 1;

				while(it.hasNext()){

					temp = it.next();

					if(temp != null && temp.getRun() == run){

						Iterator<Racer> it2 = temp.getRecord();
						exists = true;
						isGroup = temp.isGRP();

						while(it2.hasNext()){

							Racer tempRacer = it2.next();

							if(tempRacer != null) {
								
								if(isGroup) {

									// TODO
									// still have to allow for user to change finish numbers for GRP

									if(!tempRacer.isDNF()) {

										if(isGUI){

											ui.getUserInterface().getjTextArea1().append(String.format("<%05d> ", groupID).toString() + "<"
													+ ClockInterface.getTotalTimeFormatted(tempRacer.getStartInLong(), tempRacer.getFinishInLong()) + ">\n");

										}else{
											System.out.format("<%05d> ", groupID);
											System.out.println("<" + ClockInterface.getTotalTimeFormatted(tempRacer.getStartInLong(), tempRacer.getFinishInLong()) + ">");
										}
									}else{

										if(isGUI){

											ui.getUserInterface().getjTextArea1().append(String.format("<%05d>  <DNF>\n", groupID).toString());

										}else{

											System.out.format("<%05d> <DNF>\n", groupID);

										}
										groupID++;
									}
								} else {

									if(isGUI){

										ui.getUserInterface().getjTextArea1().append((!tempRacer.isDNF() ? ("<" + tempRacer.getBib() + ">"
												+ " " + "<"
												+ ClockInterface.getTotalTimeFormatted(tempRacer.getStartInLong(), tempRacer.getFinishInLong()) + ">\n") : ("<" + tempRacer.getBib() + ">" + " " + "<" + "DNF" + ">\n")));

									}else{

										System.out.println(!tempRacer.isDNF() ? "<" + (tempRacer.getBib() + ">"
												+ " " + "<"
												+ ClockInterface.getTotalTimeFormatted(tempRacer.getStartInLong(), tempRacer.getFinishInLong()) + ">") : "<" + tempRacer.getBib() + ">" + " " + "<" + "DNF" + ">");

									}
								}
							}
						}
					}
				}
			}

			if(!exists){

				System.out.println("No such run!");

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
	public static int channelsEnabled(int from){

		int count = 0;

		for(int i = from; i < 4; i += 2){

			ui.getRaceManager().setChannelSelected(i);

			if(ui.getRaceManager().getCurrentChannel().isEnabled()){

				count++;

			}
		}

		return count;

	}


	public static void inputCommand(String str){

		if("POWER".equals(str)) {

			InterfaceHandler.powerOnOff();

		}else if("EXIT".equals(str)) {

			ui.getBtnHandler().EXIT();

		}else if(ui.getBtnHandler().getPowerState()) {

			if(!(str.contains("EVENT") || str.contains("POWER") || str.contains("EXIT") 
					|| str.contains("RESET") || str.contains("TIME") || str.contains("TOG") || str.contains("CONN") || str.contains("TESTING"))){

				if(ui.getRaceManager().getType() == null){

					System.out.println("You have not initialized what type of even it is, so by default it's "
							+ "initialized to independent!");
					ui.getRaceManager().setType(Run_Types.IND);

				}
			}


			if(str.split("\\s").length <= 1){

				switch(str){
				case "NEWRUN":

					int enabled = InterfaceHandler.channelsEnabled(1);

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

					ui.getRaceManager().keepRecord();
					ui.getRaceManager().endRun();

					System.out.println("Run ended");
					break;
				case "CANCEL":				//Redo!


					//					System.out.println("Before cancel: " + ui.getRaceManager().racersPoolSize());
					//
					//					if(ui.getRaceManager().getChannelSelected() == 1 || ui.getRaceManager().getChannelSelected() == 2){
					//						ui.getRaceManager().getRaces()[0].CANCEL();
					//					}
					//					else if(ui.getRaceManager().getChannelSelected() == 3 || ui.getRaceManager().getChannelSelected() == 4) {
					//						ui.getRaceManager().getRaces()[1].CANCEL();
					//					}
					//
					//					System.out.println("After cancel: " + ui.getRaceManager().racersPoolSize());

					break;
				case "SWAP":
					if(ui.getRaceManager().swap())
						System.out.println("Swap sucessful");
					else
						System.out.println("Swap failed");
					break;
				case "START":   
					//Any amount can start in parallel, that's what I have in my notes
					//You cannot start a racers after another has finished, because otherwise how do you keep track of the shift

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
				case "RESET":
					InterfaceHandler.powerOnOff();   // turns power off then back on
					InterfaceHandler.powerOnOff();
					break;
				case "RACETEST":

					try{

						for (int i = 0; i < 7; i++) {

							ui.getRaceManager().setChannelSelected(i + 1);

							if(!ui.getRaceManager().getCurrentChannel().isEnabled()){

								ui.getRaceManager().getCurrentChannel().enable(!ui.getRaceManager().getCurrentChannel().isEnabled());

							}

							System.out.println("Channel: " + (i + 1) + " togged!");

						}

					}catch(InputMismatchException | NumberFormatException e){

						System.out.println("Wrong input!");

					}

					InterfaceHandler.conn("CONN GATE 1");
					InterfaceHandler.conn("CONN GATE 2");
					InterfaceHandler.conn("CONN GATE 3");
					InterfaceHandler.conn("CONN GATE 4");
					InterfaceHandler.conn("CONN EYE 5");
					InterfaceHandler.conn("CONN EYE 6");
					InterfaceHandler.conn("CONN EYE 7");
					InterfaceHandler.conn("CONN EYE 8");

					System.out.println("All sensors are connected your are good to go!");

					ui.getRaceManager().setUpRaceForArbitrarySimulation();


					break;
				}
			}else{

				switch(str.split("\\s")[0].trim()){

				case "EVENT":

					switch(str.split("\\s")[1].trim()){
					case "IND":
						ui.getRaceManager().setType(Run_Types.IND);
						break;
					case "PARIND":
						ui.getRaceManager().setType(Run_Types.PARIND);
						break;
					case "GRP":
						ui.getRaceManager().setType(Run_Types.GRP);
						break;
					case "PARGRP":
						ui.getRaceManager().setType(Run_Types.PARGRP);
						break;
					}
					break;
				case "TIMEDFREQ":

					try{

						ui.getSimulator().getClock().setTimeDispFreq(Integer.parseInt(str.split("\\s")[1]));
						System.out.println("Setting it to: " + (Integer.parseInt(str.split("\\s")[1]) / 1000) + "s");

					}catch(InputMismatchException | NumberFormatException e){}

					break;
				case "CLR":

					try {
						int bib = Integer.parseInt(str.split("\\s")[1]);

						if(ui.getRaceManager().clearRacer(bib)) 
							System.out.println("Racer " + bib + " cleared from pool");


					}catch(InputMismatchException | NumberFormatException e) {}
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

						String fileName = System.getProperty("user.dir") + "export" + count;
						count++;

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

						if(channelSelected > 0 && channelSelected <= 8){

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
			}
		}	
	}



	public UserGraphical getUserInterface() {
		return userInterface;
	}

	public static void setGUI(boolean isGUI) {
		InterfaceHandler.isGUI = isGUI;
		ui.getRaceManager().setGui(isGUI);
	}
}