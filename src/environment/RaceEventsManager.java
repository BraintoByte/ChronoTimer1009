package environment;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.junit.Before;

import Utils.Printer;
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
 */
public class RaceEventsManager {

	/**
	 * @author Andy SensorCoupler, an inner class of RaceEventManager, is
	 *         responsible for coupling sensors to channels.
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
	private Run currentRun;
	private int runNbr;
	private Run_Types type;
	private Pool racePool = Pool.getPool();
	private boolean isGui;
	private int[] bibs = new int[1];

	/**
	 * @param run
	 * @return true if a new run was made
	 * 
	 *         Creates a new Race with RunID = run, and increments index and
	 *         raceNbr.
	 */
	// public boolean startNewRace(int run){

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

	private boolean isRacerIn(int racer) {
		return Arrays.binarySearch(bibs, racer) >= 0;
	}

	public void resetPool() {
		if(racePool != null)
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
	 * to null, and creates a new HashMap for record.
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

		Printer.clearMiddleTxt(0);
		Printer.clearMiddleTxt(1);
		Printer.clearMiddleTxt(2);
		type = Run_Types.IND;
		resetPool();
		bibs = new int[1];
		runNbr = 0;
		currentRun = null;
		recordRaces = new LinkedList<>();

	}

	public void setRunNbr(int runNbr) {
		this.runNbr = runNbr;
	}

	public int getRunNbr() {
		return runNbr;
	}

	public void setType(Run_Types type) {

		if (checkRunInitiated()) {

			Printer.printToConsole("Please end the current run\n");
			return;

		}

		this.type = type;
		Printer.printToConsole("Event set to " + type.toString() + "\n");
	}

	public Run_Types getType() {
		return type;
	}

	public void CANCEL(int channel) {

//		System.out.println(channelSelected);
		if(currentRun.isThereRacersActive(channel)){
			racePool.addRacerBeginning(currentRun.CANCEL(channel));
		}
		
		if(isGui) {
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
	public void trig(String str, boolean DNF) { // We need to refactor this, is channel enabled method, is channel valid

		// method choice 1 choice 2

		if (!isGui) {
			System.out.println("Racers inactive before action: " + racePool.getRacersAmount());
		}
		if (!checkRunInitiated()) {

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

			if (getCurrentChannel().isEnabled()) {

				int n = 1;
				if (type == Run_Types.GRP) {
					switch (channelSelected) {
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
						if (!isGui) {
							System.out.println("Cannot trigger channels > 2 for GRP event");
						}
						return;
					}
					n = racePool.getRacersAmount();
				}

				if (channelSelected % 2 == 1) {

					Race tempRace = currentRun.getRaceFromChannel(channelSelected);

					if (tempRace != null) {

						tempRace.startNRacers(n, racePool);

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

				} else if (channelSelected % 2 == 0) {

					Race[] active = currentRun.getRaces();

					if (active != null) {

						for (int i = 0; i < active.length; i++) {

							if (active[i] != null) {

								if (currentRun.getRaces()[i].getChannelsActive()[1] == channelSelected) {

									if (active[i].racersActive() != 0) {

										active[i].finishRacer(DNF, racePool);

									} else {
										if (!isGui) {
											System.out.println("No more racers!");
										}
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
			} else {
				if (!isGui) {
					System.out.println("Channel " + channelSelected + " is not toggled!");
				}
			}
		} catch (InputMismatchException | NumberFormatException e) {

			System.out.println("WRONG INPUT!");

		}

		if (!isGui) {
			System.out.println("Racers inactive after action: " + racePool.getRacersAmount());
		} else {
			printPoolToGUI();
			printActiveToGUI();
		}
	}

	public boolean keepRecord() {

		if (!checkRunInitiated()) {
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
	
	

	public int racesActive() {
		return checkRunInitiated() && currentRun.getRaces() == null ? 0 : currentRun.getRaces().length;
	}

	public void endRun() {

		if (!checkRunInitiated()) {

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

		} 
		currentRun = null;
		if(isGui) {
			Printer.clearMiddleTxt(0);
			Printer.clearMiddleTxt(1);
		}
		
		// incase we need to reset pool
//		resetPool();
		
		Printer.printToConsole("Run ended\n");

//		else {
//
//			Printer.printToConsole("YOU CANNOT STOP WHAT'S NOT STARTED!\n");
//		}
	}

	public boolean isRunActive() {
		return currentRun != null;
	}

	public void setNewRun() {
		runNbr++;
		currentRun = new Run(runNbr, type);
		if(isGui)
			printPoolToGUI();
	}

	private boolean checkRunInitiated() {
		return currentRun != null;
	}

	public int getChannelSelected() {
		return channelSelected;
	}

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

	public Channels getCurrentChannel() {
		return Channels.channels[channelSelected - 1];
	}

	/**
	 * @param eye
	 * @param gate
	 * @param pad
	 * 
	 *            Connects the selected channel to either an eye, gate, or pad
	 *            accroding to the truth values of the parameters.
	 */
	public void CONN(boolean eye, boolean gate, boolean pad) {

		if (channelSelected != 0) {
			try {

				sensorCoupler.couple(channelSelected, eye, gate, pad);

			} catch (NoSuchSensorException e) {

				e.printStackTrace();
			}
		}
	}

	public boolean swap() {
		boolean result = currentRun != null ? currentRun.swap() : false;
		if(isGui)
			printActiveToGUI();
		return result;
	}

	public boolean clearRacer(int bib) {
		boolean result = racePool != null ? racePool.clearRacer(bib) : false;
		if (isGui)
			printPoolToGUI();
		return result;
	}

	public void setGui(boolean isGui) {
		this.isGui = isGui;
	}

	protected Pool getPool() {
		return racePool;
	}

	private void printActiveToGUI() {

		Printer.clearMiddleTxt(1);

		if(currentRun != null && currentRun.getRaces() != null){

			for (Race r : currentRun.getRaces()) {

				if (r != null) {
					Iterator<Racer> it = r.getActive().iterator();
					while (it.hasNext()) {
						Printer.printToMiddle(1, "<" + it.next().getBib() + ">\n");
					}
				}
			}
		}else{
			Printer.printToConsole("You cannot finish what's not started!");
		}
	}
	

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
				Printer.printToMiddle(0, "<" + tmp[i] + ">\n");
				cnt++;
			}
			i++;
		}
	}
}