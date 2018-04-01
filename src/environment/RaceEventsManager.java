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

			//			 (channelSelected == 2 || channelSelected == 4) && 
			//			if(currentRun.getRaces() != null){
			//
			//				currentRun.keepRecord();
			//
			//			}


			if(channelSelected > 8){
				System.out.println("WRONG CHANNEL!");
				return;
			}

			//			setChannelSelected(channelSelected);

			if(getCurrentChannel().isEnabled()){

				if(channelSelected % 2 == 1){

					if(currentRun.getRaces() == null){

						setRace();

					}

					currentRun.getRaces()[channelSelected - 1].startNRacers(1, racePool);


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


	//
	//	/**
	//	 * @return the number of active Races (i.e. Lanes for PARIND).
	//	 */
	//
	//	public int racesActive(){
	//
	//		if(racesActive == null)
	//			return 0;
	//
	//		int count = 0;
	//
	//		for(int i = 0; i < racesActive.length; i++){
	//
	//			if(racesActive[i] != null && racesActive[i].isActive()){
	//				count++;
	//			}
	//		}
	//
	//		return count;
	//	}


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

	public void setNewRun(){
		runNbr++;
		currentRun = new Run(runNbr);
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
	private void setRace() {

		//		if((currentRun.getRaces() != null && currentRun.getRaces()[0] != null && racesActive() == 0) 
		//				|| currentRun.getRaces() == null){
		//			
		//			
		//			
		//			
		//		}

		if(!checkRunInitiated()){
			return;
		}

		switch(type){

		case IND:
			//			setChannelSelected(1); //From idle

			if(racesActive() > 0){

				System.out.println("Can't have more then one race in IND!");
				return;

			}
			currentRun.setRaceFromScratch(1);
			currentRun.setNewRace(1);
			break;
		case PARIND:

			if(racesActive() <= 8){

				currentRun.setNewRace(channelSelected);

			}

			break;
		case GROUP:
			break;

		}
	}

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

//	public void makeSingle(int bib){
//
//		if(!checkRunInitiated() && bib > 0 && bib < 999){
//
//			currentRun.makeRacers(bib);
//
//		}
//	}

	//
	//	//	public void stopLastRace(){
	//	//		
	//	//		while(!active.isEmpty()){
	//	//
	//	//			finishRacer();
	//	//			
	//	//		}
	//	//	}
	//	//	
	//	//	
	//	//	public void startNRacers(int n){
	//	//
	//	//		if(n <= racePool.racersAmount()){
	//	//
	//	//			for(int i = 0; i < n; i++){
	//	//				
	//	//				Channels.channels[0].TriggerSensor();
	//	//				Racer racer = racePool.startRacer();
	//	//				Channels.channels[0].activate(racer.getBib());
	//	//				active.add(racer);
	//	//	
	//	//			}
	//	//		}
	//	//	}
	//	//	
	//	//	
	//	//	public void finishRacer(){
	//	//		
	//	//		Racer racer = active.remove();
	//	//		
	//	//		
	//	//		Random rand = new Random();       //You told me it was random, nothing in the guidelines suggests otherwise
	//	//		int randomNum = rand.nextInt((20 - 0) + 1) + 0;
	//	//		int randomNum2 = rand.nextInt((20 - 0) + 1) + 0;
	//	//
	//	//		if(randomNum == randomNum2){
	//	//
	//	//			racer.setDNF();
	//	//			
	//	//		}
	//	//		
	//	//		Channels.channels[1].TriggerSensor();
	//	//		Channels.channels[1].activate(racer.getBib());
	//	//		racePool.returnRacer(racer);
	//	//		
	//	//	}
	//
	//
	//	/**
	//	 * @return size of racePool
	//	 */
	//	public int racersPoolSize(){
	//		
	//		return racePool == null ? 0 : racePool.getRacersAmount();
	//
	//	}
	//
	//	//	public int racersActive(){
	//	//
	//	//		return active.size();
	//	//
	//	//	}
	//	//	
	//	//	public void CANCEL(){
	//	//		
	//	//		racePool.returnCancel(active.remove());
	//	//		
	//	//	}
	//
	//	/**
	//	 * @return the ID of the selected channel
	//	 */
	//	public int getChannelSelected() {
	//		return channelSelected;
	//	}
	//
	//	/**
	//	 * @return the selected channel
	//	 */

	//
	//	/**
	//	 * @param gates
	//	 * @param eyes
	//	 * @param pads
	//	 * 
	//	 * Allocates space/makes the number of gates, eyes, and pads according to the truth values of the parameters. 
	//	 */
	//	public void theseManySensors(int gates, int eyes, int pads){
	//		sensorCoupler = new SensorCoupler();
	//		sensorCoupler.getSensors().makeSensors(eyes, gates, pads, eyes > 0, gates > 0, pads > 0);
	//	}
	//
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
	//
	//	/**
	//	 * @return the Racer at the beginning of racePool
	//	 * 
	//	 * Removes and returns the Racer at the beginning of racePool.
	//	 */
	//	protected Racer getRacer(){
	//		return racePool.removeRacerBeginning();
	//	}

	//	/**
	//	 * Helper of {@link RaceEventsManager#allPairedSensors()} puts one channel sensors into the final array from index to index
	//	 * and sends back the ending index
	//	 * 
	//	 * @param sensors
	//	 * @param tempSensor
	//	 * @param fromIndex
	//	 * @return fromIndex
	//	 */
	//	
	//	private int putIntoArray(Sensor[] sensors, Object[] tempSensor, int fromIndex){
	//
	//		for(int i = 0; i < tempSensor.length; i++){
	//
	//			sensors[fromIndex] = (Sensor) tempSensor[i];
	//			fromIndex++;
	//		}
	//
	//		return fromIndex;
	//
	//	}

	//	/**
	//	 * @param run - the ID of the Run to get
	//	 * @return array of Races (lanes) for the Run with ID = run
	//	 */
	//	public Race[] getSelectedRun(int run) throws ConcurrentModificationException {
	//
	//		Iterator<Integer> it = record.keySet().iterator();
	//		Stack<Race> tempStack = new Stack<>();
	//
	//		while(it.hasNext()){
	//
	//			Race temp = record.get(it.next());
	//
	//			if(temp.getRun() == run)
	//				tempStack.push(record.get(temp.getRaceNbr()));
	//
	//		}
	//
	//		Race[] tempArr = (Race[]) tempStack.toArray(new Race[tempStack.size()]);
	//
	//		return tempArr;
	//	}
}