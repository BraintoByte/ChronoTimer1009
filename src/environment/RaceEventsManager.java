package environment;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import com.google.gson.Gson;
import Utils.Printer;
import Utils.Util;
import entitiesDynamic.Pool;
import entitiesDynamic.Racer;
import entitiesStatic.ClockInterface;
import environment.Run.Race;
import exceptions.NoSuchSensorException;
import hardware.external.Sensor;
import hardware.external.SensorFactory;
import hardware.external.sensor.pad.Pad;
import hardware.external.sensors.eye.Eye;
import hardware.external.sensors.gate.Gate;
import states.hardware.Idle.Run_Types;

/**
 * @author Andy & Matt
 * 
 * The RaceEventsManager class, apart of the environment package of the ChronoTimer.
 * This class is responsible for managing the Race events.
 */
public class RaceEventsManager {

	/**
	 * @author Andy 
	 * SensorCoupler, an inner class of RaceEventManager, is
	 * responsible for coupling sensors to channels.
	 */
	private class SensorCoupler {

		private SensorFactory factory;
		private Stack<Integer> gateAvailable;
		private Stack<Integer> eyeAvailable;
		private Stack<Integer> padAvailable;

		/**
		 * Constructor for SensorCoupler
		 */
		private SensorCoupler() {

			factory = new SensorFactory();

		}

		/**
		 * @param channel
		 * @param sensor
		 * @param channel
		 * @param eye
		 * @throws NoSuchSensorException
		 * 
		 *             Couples a sensor, if available, according to the boolean values
		 *             (parameters) to the specified channel.
		 */
		protected void couple(int channel, boolean eye, boolean gate, boolean pad) throws NoSuchSensorException {

			if (factory.getAmountEye() > 0 || factory.getAmountGates() > 0) {

				if (channel <= Channels.channels.length) {

					int sensor = findNextAvailableSensor(eye, gate, pad);

					Channels.channels[channel - 1].pairToSensor(eye ? factory.findEyeIteratively(sensor)
							: gate ? factory.findGateIteratively(sensor) : factory.findPadIteratively(sensor));
				}

			} else {

				throw new NoSuchSensorException(factory);

			}
		}

		/**
		 * @param gate
		 * @param eye
		 * @param pad
		 * @return ID of next available sensor
		 * 
		 *         finds and returns the ID of the next available sensor.
		 */
		private int findNextAvailableSensor(boolean eye, boolean gate, boolean pad) {

			if (gate) {

				if (gateAvailable == null || gateAvailable.isEmpty()) {
					gateAvailable = factory.sensorToStack(gate, eye, pad);
				}

				if (gateAvailable.isEmpty()) {
					throw new IllegalAccessError();
				}

				return gateAvailable.pop();

			} else if (eye) {

				if (eyeAvailable == null || eyeAvailable.isEmpty()) {
					eyeAvailable = factory.sensorToStack(gate, eye, pad);
				}

				if (eyeAvailable.isEmpty()) {
					throw new IllegalAccessError();
				}

				return eyeAvailable.pop();

			} else if (pad) {

				if (padAvailable == null || padAvailable.isEmpty()) {
					padAvailable = factory.sensorToStack(gate, eye, pad);
				}

				if (padAvailable.isEmpty()) {
					throw new IllegalAccessError();
				}

				return padAvailable.pop();
			}

			return -1;
		}

		/**
		 * @param channel
		 * 
		 *            uncouples the sensor that is paired to channel, if any.
		 */
		protected void uncoupleSensors(int channel) {

			if (channel - 1 < Channels.channels.length)
				Channels.channels[channel - 1].unPairToSensor();
		}

		/**
		 * @return sensor factory associated with this SensorCoupler.
		 */
		protected SensorFactory getSensors() {
			return factory;
		}
	}

	private int channelSelected;
	private SensorCoupler sensorCoupler;
	private Queue<Race> recordRaces = new LinkedList<>();
	volatile private Run currentRun;
	private int runNbr;
	private Run_Types type;
	private Pool racePool = Pool.getPool();
	private boolean isGui;
	private int[] bibs = new int[1];
	private int parGrpCount = 0;
	volatile private static long startTime;
	private int anonymousIndex;
	volatile private boolean updaterEnabled;

	/**
	 * Makes a racer with bib number racer and adds them to the racePool.
	 * Also updates the bibs field and prints the pool to the GUI.
	 * @param racer - the bib number
	 * @return true if the racer was created
	 */
	public boolean makeRacers(int racer) {

		if (racer >= 0 && racer < 1000 && !isRacerIn(racer)) {
			racePool.makeRacer(racer);
			bibs = racePool.getAllBibs();
			if (isGui)
				printPoolToGUI();
			return true;
		}
		return false;
	}

	/**
	 * Searches the bibs array for the parameter racer.
	 * @param racer - the bib to search for
	 * @return true if racer is in bibs
	 */
	private boolean isRacerIn(int racer) {
		return Arrays.binarySearch(bibs, racer) >= 0;
	}

	/**
	 * Clears racePool
	 */
	public void resetPool() {
		if (racePool != null)
			racePool.clearPool();
	}

	/**
	 * @param gates
	 * @param eyes
	 * @param pads
	 * 
	 *            Allocates space/makes the number of gates, eyes, and pads
	 *            according to the truth values of the parameters.
	 */

	public void theseManySensors(int gates, int eyes, int pads) {
		sensorCoupler = new SensorCoupler();
		sensorCoupler.getSensors().makeSensors(eyes, gates, pads, eyes > 0, gates > 0, pads > 0);
	}

	/**
	 * Resets everything in RaceEventManager. clears the racePool, sets currentRun
	 * to null, resets runNbr & anonymousIndex, and creates a new LinkedList for recordRaces.
	 */
	public void resetRun() { // I don't think we will need this but just in case

		for (Channels c : Channels.channels) {
			if (c != null && c.isPairedToSensor()) {
				Sensor tmp = c.unPairToSensor();
				boolean isPad = Pad.class.isInstance(tmp);
				boolean isGate = Gate.class.isInstance(tmp);
				boolean isEye = Eye.class.isInstance(tmp);
				sensorCoupler.factory.backToTheSource(tmp, isPad, isEye, isGate);
			}
			c.enable(false);
		}

		if (isGui) {
			Printer.clearMiddleTxt(0);
			Printer.clearMiddleTxt(1);
			Printer.clearMiddleTxt(2);
		}
		type = Run_Types.IND;
		resetPool();
		bibs = new int[1];
		runNbr = 0;
		anonymousIndex = 0;
		currentRun = null;
		recordRaces = new LinkedList<>();

	}

	/**
	 * Setter for runNbr field
	 * @param runNbr
	 */
	public void setRunNbr(int runNbr) {
		this.runNbr = runNbr;
	}

	/**
	 * Getter for runNbr
	 * @return runNbr
	 */
	public int getRunNbr() {
		return runNbr;
	}

	/**
	 * Setter for type field
	 * @param type
	 */
	public void setType(Run_Types type) {

		if (isRunActive()) {

			Printer.printToConsole("Please end the current run\n");
			return;

		}

		this.type = type;
		Printer.printToConsole("Event set to " + type.toString() + "\n");
	}

	/**
	 * Getter for type field
	 * @return type
	 */
	public Run_Types getType() {
		return type;
	}

	/**
	 * Cancels the last racer to start on in the lane with the parameter channel.
	 * @param channel
	 */
	public void CANCEL(int channel) {

		if (currentRun.getRaceFromChannel(channel) != null) {
			Racer tmp = currentRun.CANCEL(channel);
			if (tmp != null)
				racePool.addRacerBeginning(tmp);
		}

		if (isGui) {
			printPoolToGUI();
			printActiveToGUI();
		}
	}

	/**
	 * @param str
	 * @param DNF
	 *            Triggers the channel specified in str, where str = "TRIG<chanID>"
	 *            and if the next racer to finish Did not Finish (DNF = true), then
	 *            their time is recorded as DNF.
	 */
	public void trig(String str, boolean DNF) {

		// method choice 1 choice 2

		boolean setPargrp = false;

		if (!isGui) {
			System.out.println("Racers inactive before action: " + racePool.getRacersAmount());
		}
		if (!isRunActive()) {

			Printer.printToConsole("NO RUN INITIATED!\n");
			return;
		}

		try {

			channelSelected = Integer.parseInt(str.split("\\s")[1].trim());

			if (channelSelected > 8) {
				System.out.println("WRONG CHANNEL!");
				return;
			}

			setChannelSelected(channelSelected);

			int n = 1;

			if (getCurrentChannel().isEnabled()) {

				if (type == Run_Types.GRP) {

					if (channelSelected > 2 && !isGui) {

						System.out.println("Cannot trigger channels > 2 for GRP event");
						return;
					}

					if ((n = racePool.getRacersAmount()) == 0 && channelSelected == 1) {

						// make anonymous race
						currentRun.setIsAnonymous();
					}

				} else if (type == Run_Types.PARGRP) {

					if (channelSelected == 1 && currentRun.getRaces() == null) {

						for (int i = 0; i < 8; i++) {
							channelSelected = i;
							triggerStart(n);
						}

						setPargrp = true;
						parGrpCount = 8;

					} else if (channelSelected == 1 && currentRun.getRaces() != null) {

						if (parGrpCount < 8 && !currentRun.getARace(0).isActive()) {

							int tempSize = 7 - parGrpCount;

							for (int i = 0; i <= tempSize; i++) {
								if (!currentRun.getARace(i).isActive()) {
									channelSelected = i;
									triggerStart(n);
									parGrpCount++;
								}
							}
							setPargrp = true;
						}
					}
				}
			}

			if (type != Run_Types.PARGRP) {

				if (channelSelected % 2 == 1) {
					triggerStart(n);
				} else if (channelSelected % 2 == 0) {
					triggerStop(DNF);
				} else {
					if (!isGui) {
						System.out.println("Channel " + channelSelected + " is not toggled!");
					}
				}

			} else {

				if (!setPargrp) {
					if (DNF) {
						triggerStop(DNF);
					} else {
						triggerParGRP();
					}
					if (!isGui) {
						System.out.println("Channel " + channelSelected + " is not toggled!");
					}
				}
			}
		} catch (InputMismatchException | NumberFormatException e) {
			Printer.printToConsole("WRONG INPUT!\n");
		}

		if (!isGui) {
			System.out.println("Racers inactive after action: " + racePool.getRacersAmount());
		} else {

			printPoolToGUI();
			printActiveToGUI();
		}
	}

	/**
	 * Updates the recordRaces field with the currentRun.
	 * @return true if successful
	 */
	public boolean keepRecord() {

		if (!isRunActive()) {
			return false;
		}

		Race[] racesActive = currentRun.getRaces();

		if (racesActive != null && racesActive.length > 0) {

			for (int i = 0; i < racesActive.length; i++) {

				if (racesActive[i] == null) {
					break;
				}

				if (recordRaces.contains(racesActive[i])) {

					recordRaces.remove(racesActive[i]);

				}

				recordRaces.add(racesActive[i]);

			}
		}
		return true;
	}

	/**
	 * Helper method that starts n number of racers for the currentRun.
	 * @param n - the number of Racers to start
	 */
	private void triggerStart(int n) {

		Race tempRace = currentRun.getRaceFromChannel(channelSelected);

		if (tempRace != null) {

			tempRace.startNRacers(n, racePool);
			startTime = ClockInterface.getTimeInLong();
			keepRecord();
			if (!isGui) {
				System.out.println("Racers inactive after action: " + racePool.getRacersAmount());
			} else {
				printPoolToGUI();
				printActiveToGUI();
			}

			return;
		}

		if (currentRun.setNewRace(channelSelected)) {
			tempRace = currentRun.getRaceFromChannel(channelSelected);

			tempRace.startNRacers(n, racePool);
			startTime = ClockInterface.getTimeInLong();
			keepRecord();

			if (!isGui) {
				System.out.println("Race STARTED!");
			} else {
				printPoolToGUI();
				printActiveToGUI();
			}
			return;
		}

		if (!isGui) {
			System.out.println("YOU CANNOT CREATE THESE MANY RUNS!");
		}

	}

	/**
	 * Helper method that starts a PARGRP event. 
	 */
	private void triggerParGRP() {

		if (channelSelected < 9 && currentRun.getRaceFromChannel(channelSelected - 1) != null
				&& currentRun.getRaceFromChannel(channelSelected - 1).isActive()) {

			channelSelected = channelSelected - 1;
			triggerStop(false);
			parGrpCount--;

		}
	}

	/**
	 * Helper method that finishes the next racer to finish for the currrentRun.
	 * If the parameter DNF is true the Racer will not have a finish time.
	 * @param DNF 
	 */
	private void triggerStop(boolean DNF) {

		Race[] active = currentRun.getRaces();

		if (active != null) {

			for (int i = 0; i < active.length; i++) {

				if (active[i] != null) {

					if (currentRun.getRaces()[i].getChannelsActive()[1] == channelSelected) {


						active[i].finishRacer(DNF, racePool);

						if (!isGui) {
							System.out.println("No more racers!");
						}
						break;
					}
				}
			}

			keepRecord();

		} else {
			if (!isGui) {
				System.out.println("No more racers!");
			}
		}
	}

	/**
	 * @return the number of active lanes for the currentRun
	 */
	public int racesActive() {
		return isRunActive() && currentRun.getRaces() == null ? 0 : currentRun.getRaces().length;
	}

	/**
	 * Ends the currentRun by DNFing any active racers, adding the Run to the recordRaces, 
	 * then sorts the list of finished Racers based on finishTimes and sends the results as a Json serialized String to the http URL specified in config.txt
	 * {@link #sendCommandToServer(String)}
	 */
	public void endRun() {

		if (!isRunActive()) {

			Printer.printToConsole("NO ACTIVE RUN!\n");
			return;
		}

		Race[] racesActive = currentRun.getRaces();

		if (racesActive != null) {

			for (int i = 0; i < racesActive.length; i++) { // Change this

				if (racesActive[i] != null) {

					racesActive[i].stopLastRace(racePool);

				}
			}

			List<Racer> tmpList = new ArrayList<Racer>();
			Iterator<Racer> tmp;

			for (Race r : racesActive) {
				if (r != null) {
					tmp = r.getRecord();
					while (tmp.hasNext())
						tmpList.add(tmp.next());
				}
			}

			tmpList.sort(new Comparator<Racer>() {

				@Override
				public int compare(Racer arg0, Racer arg1) {

					if (arg0.isDNF()) {

						if (arg1.isDNF())
							return 0;

						return 1;
					} else if (arg1.isDNF()) {

						return -1;
					} else if (arg0.getTotalTime() < arg1.getTotalTime())
						return -1;
					else if (arg0.getTotalTime() > arg1.getTotalTime())

						return 1;
					else
						return 0;
				}
			});

			sendCommandToServer("ADD " + runNbr + " " + new Gson().toJson(tmpList));
		}

		currentRun = null;
		anonymousIndex = 0;
		if (isGui) {
			Printer.clearMiddleTxt(0);
			Printer.clearMiddleTxt(1);
		}

		resetPool();

		Printer.printToConsole("Run ended\n");

	}

	/**
	 * @return true is there is a currentRun
	 */
	public boolean isRunActive() {
		return currentRun != null;
	}

	/**
	 * Sets the currentRun to a new Run and increments the runNbr field.
	 */
	public void setNewRun() {
		runNbr++;
		currentRun = new Run(runNbr, type);
		if (isGui) {
			Printer.clearMiddleTxt(2);
			printPoolToGUI();
		}
	}

	/**
	 * Getter for channelSelected field.
	 * @return channelSelected
	 */
	public int getChannelSelected() {
		return channelSelected;
	}

	/**
	 * Gets an Iterator of type Race for the recordRaces field.
	 * @return Iterator for recordRaces
	 */
	public Iterator<Race> getRecords() {
		return recordRaces.iterator();
	}

	/**
	 * @param channelSelected
	 * @return true if channel exists and is selected
	 * 
	 *         Set a specific channel to be the selected channel
	 */
	public boolean setChannelSelected(int channelSelected) {

		if (channelSelected <= Channels.channels.length && channelSelected >= 0) {

			this.channelSelected = channelSelected;

			return true;
		}

		return false;
	}
	
	public void setUpRaceForArbitrarySimulation() {

		Random rand = new Random();

		for (int i = 0; i < 10; i++) {

			int randRacer = rand.nextInt((999 - 1) + 1) + 1;

			makeRacers(randRacer);

			if (!isGui)
				System.out.println("Racer bib: " + randRacer + " was added!");

		}

		if (isGui)
			printPoolToGUI();

	}

	/**
	 * Gets the selected Channel object
	 * @return Channel corresponding to the channelSelcted
	 */
	public Channels getCurrentChannel() {
		return Channels.channels[channelSelected - 1];
	}

	/**
	 * @param eye
	 * @param gate
	 * @param pad
	 * 
	 *            Connects the selected channel to either an eye, gate, or pad
	 *            according to the truth values of the parameters.
	 */
	public void CONN(boolean eye, boolean gate, boolean pad) {

		if (channelSelected != 0) {
			try {

				sensorCoupler.couple(channelSelected, eye, gate, pad);

			} catch (NoSuchSensorException e) {

			}
		}
	}

	/**
	 * Swaps the lead two Racers for an IND type Run.
	 * @return
	 */
	public boolean swap() {
		boolean result = currentRun != null ? currentRun.swap() : false;
		if (isGui)
			printActiveToGUI();
		return result;
	}

	/**
	 * Clears the Racer with bib number bib from the racePool.
	 * {@link entitiesDynamic.Pool#clearRacer(int)}
	 * @param bib - the bib of the Racer to clear
	 * @return true if successful
	 */
	public boolean clearRacer(int bib) {
		boolean result = racePool != null ? racePool.clearRacer(bib) : false;
		if (isGui)
			printPoolToGUI();
		return result;
	}

	/**
	 * Setter for isGui field.
	 * @param isGui
	 */
	public void setGui(boolean isGui) {
		this.isGui = isGui;
	}

	/**
	 * Getter for racePool field.
	 * @return
	 */
	public Pool getPool() {
		return racePool;
	}

	/**
	 * Displays and updates the active racers queue to the GUI component by creating and 
	 * starting a thread if the updaterEnabled field is false.
	 */
	private void printActiveToGUI() {

		if (!updaterEnabled) {

			updaterEnabled = true;

			new Thread(new Runnable() {

				@Override
				public void run() {

					Race[] races;
					Racer tempRacer;
					Object[] racers;
					String display = "";
					boolean isGRP = type == Run_Types.GRP || type == Run_Types.PARGRP;

					while (currentRun != null && currentRun.isActive()) {

						try {

							Thread.sleep(1);

							races = currentRun.getRaces();

							if (isGRP) {

								display += "<" + (double) ClockInterface.computeDifference(startTime,
										ClockInterface.getTimeInLong()) / 1000 + ">\n";
							}

							for (int i = 0; i < races.length; i++) {

								if (races[i] != null) {

									racers = races[i].getActive().toArray();

									for (int j = 0; j < racers.length; j++) {

										tempRacer = (Racer) racers[j];
										if (isGRP) {

											display += String.format("<%03d>\n", tempRacer.getBib());

										} else {
											
											display += String.format("<%03d> <" + tempRacer.getRunningTime() + "> \n", tempRacer.getBib());
											
										}

									}
								}
							}

						} catch (Exception e) {}

						Printer.updateMiddleArea(display);
						display = "";

					}

					Printer.updateMiddleArea("");
					updaterEnabled = false;
				}
			}).start();
		}
	}

	/**
	 * Prints racePool to the GUI component.
	 */
	private void printPoolToGUI() {

		Printer.clearMiddleTxt(0);
		if (type == Run_Types.GRP)
			return;

		int cap;
		if (type == Run_Types.IND) {
			cap = 3;
		} else {
			cap = 2;
		}

		int[] tmp = racePool.getAllBibs();

		int i = 0;
		int cnt = 0;
		while (cnt < cap) {

			if (i >= tmp.length)
				break;

			if (bibs[i] != 0) {
				Printer.printToMiddle(0, String.format("<%03d>\n", tmp[i]));
				cnt++;
			}
			i++;
		}
	}

	/**
	 * 
	 * @param newBib
	 */
	public void updateAnonymousRacer(int newBib) {

		if(newBib < 1 || newBib > 999)
			return;
		
		Printer.clearMiddleTxt(2);
		boolean incriment = false;
		Racer r;
		Iterator<Racer> it = currentRun.getARace(0).getRecord();
		int cnt = 0;
		while (it.hasNext()) {

			r = it.next();
			
			if (cnt++ == anonymousIndex) { 
				r.setBib(newBib);
				incriment = true;
			}
				
				Printer.printToMiddle(2, String.format("<%03d> <" + r.getTotalTime() + ">\n", r.getBib()));
			
		}
		
		if(incriment)
			anonymousIndex++;
		
	}

	public Run getCurrentRun() {
		return currentRun;
	}

	protected static long getStartTime() {
		return startTime;
	}

	private void sendCommandToServer(String command) {

		try {

			Util.readFileAsString("config.txt");

			URL site = new URL(Util.getContent());

			HttpURLConnection conn = (HttpURLConnection) site.openConnection();

			conn.setConnectTimeout(1000);

			conn.setRequestMethod("POST");

			conn.setDoOutput(true);
			conn.setDoInput(true);

			DataOutputStream out = new DataOutputStream(conn.getOutputStream());

			// clear first
			if (command.split(" ").length > 1 && command.split(" ")[1].equals("1")) {
				sendCommandToServer("CLEAR");
			}

			out.writeBytes(command);
			out.flush();
			out.close();

			InputStreamReader inputStr = new InputStreamReader(conn.getInputStream());

			// string to hold the result of reading in the response
			StringBuilder sb = new StringBuilder();

			// read the characters from the request byte by byte and build up
			// the Response
			int nextChar;
			while ((nextChar = inputStr.read()) > -1) {
				sb = sb.append((char) nextChar);
			}

		} catch (Exception e) {
			Printer.printToConsole("Error exporting to Server\n");
		}

	}
}