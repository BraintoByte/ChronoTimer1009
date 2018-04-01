package environment;


import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import org.junit.Before;

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
	 * @author Andy
	 * SensorCoupler, an inner class of RaceEventManager, is responsible for coupling sensors to channels.
	 */
	private class SensorCoupler {

		private SensorFactory factory;
		private Stack<Integer> gateAvailable;
		private Stack<Integer> eyeAvailable;
		private Stack<Integer> padAvailable;

		/**
		 * Constructor for SensorCoupler
		 */
		private SensorCoupler(){

			factory = new SensorFactory();

		}

		/**
		 * @param channel
		 * @param sensor
		 * @param channel
		 * @param eye
		 * @throws NoSuchSensorException
		 * 
		 * Couples a sensor, if available, according to the boolean values (parameters) to the specified channel.
		 */
		protected void couple(int channel, boolean eye, boolean gate, boolean pad) throws NoSuchSensorException {

			if(factory.getAmountEye() > 0 || factory.getAmountGates() > 0){

				if(channel <= Channels.channels.length){

					int sensor = findNextAvailableSensor(eye, gate, pad);

					Channels.channels[channel - 1].pairToSensor(eye ? factory.findEyeIteratively(sensor) : gate ? factory.findGateIteratively(sensor) : 
						factory.findPadIteratively(sensor));
				}

			}else{

				throw new NoSuchSensorException(factory);

			}
		}

		/**
		 * @param gate
		 * @param eye
		 * @param pad
		 * @return ID of next available sensor
		 * 
		 * finds and returns the ID of the next available sensor.
		 */
		private int findNextAvailableSensor(boolean eye, boolean gate, boolean pad){

			if(gate){

				if(gateAvailable == null || gateAvailable.isEmpty()) {
					gateAvailable = factory.sensorToStack(gate, eye, pad);
				}

				if(gateAvailable.isEmpty()) {
					throw new IllegalAccessError();
				}

				return gateAvailable.pop();

			}else if(eye){

				if(eyeAvailable == null || eyeAvailable.isEmpty()) {
					eyeAvailable = factory.sensorToStack(gate, eye, pad);
				}

				if(eyeAvailable.isEmpty()) {
					throw new IllegalAccessError();
				}

				return eyeAvailable.pop();

			}else if(pad){

				if(padAvailable == null || padAvailable.isEmpty()) {
					padAvailable = factory.sensorToStack(gate, eye, pad);
				}

				if(padAvailable.isEmpty()) {
					throw new IllegalAccessError();
				}

				return padAvailable.pop();
			}

			return -1;
		}

		/**
		 * @param channel
		 * 
		 * uncouples the sensor that is paired to channel, if any.
		 */
		protected void uncoupleSensors(int channel){

			if(channel - 1 < Channels.channels.length)
				Channels.channels[channel - 1].unPairToSensor();
		}

		/**
		 * @return sensor factory associated with this SensorCoupler.
		 */
		protected SensorFactory getSensors(){
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

	/**
	 * @param run
	 * @return true if a new run was made
	 * 
	 * Creates a new Race with RunID = run, and increments index and raceNbr.
	 */
	//	public boolean startNewRace(int run){

	public void makeRacers(int racer) {
		if(racer >= 0){
			racePool.makeRacer(racer);	
		}
	}

	public void resetPool(){
		racePool.clearPool();
	}

	/**
	 * @param gates
	 * @param eyes
	 * @param pads
	 * 
	 * Allocates space/makes the number of gates, eyes, and pads according to the truth values of the parameters. 
	 */
	public void theseManySensors(int gates, int eyes, int pads){
		sensorCoupler = new SensorCoupler();
		sensorCoupler.getSensors().makeSensors(eyes, gates, pads, eyes > 0, gates > 0, pads > 0);
	}


	/**
	 * Resets everything in RaceEventManager.
	 * clears the racePool, sets currentRun to null, and creates a new HashMap for record.
	 */
	public void resetRun(){			//I don't think we will need this but just in case

		if(!checkRunInitiated()){
			return;
		}

		for(Channels c:Channels.channels) {
			if(c != null && c.isPairedToSensor()) {
				Sensor tmp = c.unPairToSensor();
				boolean isPad = Pad.class.isInstance(tmp);
				boolean isGate = Gate.class.isInstance(tmp);
				boolean isEye = Eye.class.isInstance(tmp);
				sensorCoupler.factory.backToTheSource(tmp, isPad, isEye, isGate);
			}
			c.enable(false);
		}

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

		if(checkRunInitiated()){

			System.out.println("PLEASE END THE CURRENT RUN FIRST!");
			return;

		}

		this.type = type;
	}

	public Run_Types getType() {
		return type;
	}


	/**
	 * @param str
	 * @param DNF
	 * Triggers the channel specified in str, where str = "TRIG<chanID>" and if the next racer to finish Did not Finish (DNF = true),
	 * then their time is recorded as DNF.
	 */
	public void trig(String str, boolean DNF){     //We need to refactor this, is channel enabled method, is channel valid method choice 1 choice 2

		System.out.println("Racers inactive before action: " + racePool.getRacersAmount());

		if(!checkRunInitiated()){
			System.out.println("NO RUN INITIATED!");
			return;
		}

		try{

			channelSelected = Integer.parseInt(str.split("\\s")[1].trim());

			if(channelSelected > 8){
				System.out.println("WRONG CHANNEL!");
				return;
			}

			//			setChannelSelected(channelSelected);

			if(getCurrentChannel().isEnabled()){

				if(channelSelected % 2 == 1){

					//					if(currentRun.getRaces() == null){
					//
					//						setRace();
					//
					//					}

					//					currentRun.getRaces()[channelSelected].startNRacers(1, racePool);
					Race tempRace = currentRun.getRaceFromChannel(channelSelected);

					if(tempRace != null){
						
						tempRace.startNRacers(1, racePool);
						keepRecord();
						System.out.println("Racers inactive after action: " + racePool.getRacersAmount());
						return;

					}

					if(currentRun.setNewRace(channelSelected)){
						tempRace = currentRun.getRaceFromChannel(channelSelected);
						tempRace.startNRacers(1, racePool);
						keepRecord();
						System.out.println("Race STARTED!");
						return;
					}

					System.out.println("YOU CANNOT CREATE THESE MANY RUNS!");


				}else if(channelSelected % 2 == 0){

					Race[] active = currentRun.getRaces();

					if(active != null){

						for(int i = 0; i < active.length; i++){

							if(active[i] != null){

								if(currentRun.getRaces()[i].getChannelsActive()[1] == channelSelected){

									if(active[i].racersActive() != 0){

										active[i].finishRacer(DNF, racePool);

									}else{

										System.out.println("No more racers!");

									}

									break;
								}
							}
						}


						keepRecord();

					}else{

						System.out.println("NO MORE RACERS!");

					}
				}
			}else{

				System.out.println("Channel " + channelSelected + " is not toggled!");

			}
		}catch(InputMismatchException | NumberFormatException e){

			System.out.println("WRONG INPUT!");

		}

		System.out.println("Racers inactive after action: " + racePool.getRacersAmount());

	}


	public boolean keepRecord(){

		if(!checkRunInitiated()){
			return false;
		}

		int count = 0;
		Race[] racesActive = currentRun.getRaces();

		if(racesActive != null && racesActive.length > 0){

			for(int i = 0; i < racesActive.length; i++){

				if(racesActive[i] == null){
					break;
				}

				recordRaces.add(racesActive[i]);

				if(!racesActive[i].isActive()){

					count++;

				}
			}

			if(count < 8){

				return false;

			}

			racesActive = null;

			return true;

		}

		return false;

	}


	public int racesActive(){
		return checkRunInitiated() && currentRun.getRaces() == null ? 0 : currentRun.getRaces().length;
	}

	public void endRun(){

		if(!checkRunInitiated()){
			System.out.println("NO ACTIVE RUN!");
			return;
		}

		Race[] racesActive = currentRun.getRaces();

		for(int i = 0; i < racesActive.length; i++){      //Change this

			if(racesActive[i] != null){

				racesActive[i].stopLastRace(racePool);

			}
		}

		currentRun = null;

	}
	
	public boolean isRunActive(){
		
		return currentRun != null;
		
	}

	public void setNewRun(){
		runNbr++;
		currentRun = new Run(runNbr, type);
	}

	private boolean checkRunInitiated(){
		return currentRun != null;
	}

	public int getChannelSelected() {
		return channelSelected;
	}



	/**
	 * Creates a New Run with the specified event type (independent | parallel).
	 */
	//	private void setRace() {
	//
	//		if(!checkRunInitiated()){
	//			return;
	//		}
	//
	//		switch(type){
	//
	//		case IND:
	//			//			setChannelSelected(1); //From idle
	//
	//			if(racesActive() > 0){
	//
	//				System.out.println("Can't have more then one race in IND!");
	//				return;
	//
	//			}
	//			currentRun.setRaceFromScratch(1);
	//			currentRun.setNewRace(1);
	//			break;
	//		case PARIND:
	//			
	//			if(racesActive() == 0){
	//				currentRun.setRaceFromScratch(8);
	//			}
	//
	//			if(racesActive() <= 8){
	//				currentRun.setNewRace(channelSelected);
	//			}
	//
	//			break;
	//		case GROUP:
	//			break;
	//
	//		}
	//	}

	//					^^
	//					||
	//|-------------------------------------------------------------------------|
	//		if(type == Run_Types.IND){
	//
	//			if((currentRun.getRaces() != null && currentRun.getRaces()[0] != null && racesActive() == 0) 
	//					|| currentRun.getRaces() == null){
	//			
	//			
	//
	////				if(channelsEnabled(1) > 2){
	////
	////					System.out.println("Cannot have more then 1 channel on IND");
	////					return;
	////
	////				}
	//
	//				currentRun.resetIndex();
	//				setChannelSelected(1);
	//				currentRun.startNewRace(ui.getSimulator().getRun() + 1);
	////				ui.getSimulator().setRun(ui.getSimulator().getRun() + 1);
	//
	//			}
	//
	//		}else if(type == Run_Types.PARIND){
	//
	//			if((currentRun.getRaces() != null && currentRun.getRaces()[0] != null 
	//					&& !currentRun.getRaces()[0].isActive()) || racesActive() <= 8){
	//
	//				currentRun.resetIndex();
	//				setChannelSelected(1);
	//				currentRun.setNewRace(run);
	////				currentRun.startNewRace(ui.getSimulator().getRun() + 1);
	////				ui.getSimulator().setRun(ui.getSimulator().getRun() + 1);
	//				setChannelSelected(3);
	////				currentRun.startNewRace(ui.getSimulator().getRun() + 1);
	////				ui.getSimulator().setRun(ui.getSimulator().getRun() + 1);
	//
	//			}
	//		}else if(type == Run_Types.GROUP){
	//			
	//			System.out.println("NOTHING HERE YET!");
	//			
	//		}
	//|------------------------------------------------------------------------|


	public Iterator<Race> getRecords() {
		return recordRaces.iterator();
	}


	/**
	 * @param channelSelected
	 * @return true if channel exists and is selected
	 * 
	 * Set a specific channel to be the selected channel
	 */

	public boolean setChannelSelected(int channelSelected) {

		if(channelSelected <= Channels.channels.length){

			this.channelSelected = channelSelected;

			return true;
		}

		return false;
	}


	public void setUpRaceForArbitrarySimulation(){

		Random rand = new Random();

		for(int i = 0; i < 10; i++){

			int randRacer = rand.nextInt((999 - 1) + 1) + 1;

			makeRacers(randRacer);
			System.out.println("Racer bib: " + randRacer + " was added!");

		}
	}


	public Channels getCurrentChannel(){
		return Channels.channels[channelSelected - 1];
	}


	/**
	 * @param eye
	 * @param gate
	 * @param pad
	 * 
	 * Connects the selected channel to either an eye, gate, or pad accroding to the truth values of the parameters.
	 */
	public void CONN(boolean eye, boolean gate, boolean pad){

		if(channelSelected != 0){
			try {

				sensorCoupler.couple(channelSelected, eye, gate, pad);

			} catch (NoSuchSensorException e) {

				e.printStackTrace();
			}
		}
	}
}