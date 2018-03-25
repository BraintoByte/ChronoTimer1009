package environment;


import java.util.HashMap;
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
import exceptions.NoSuchSensorException;
import hardware.external.Sensor;
import hardware.external.SensorFactory;

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
	private Pool racePool;
	protected Race[] races;
	private int raceNbr;
	private HashMap<Integer, Race> record = new HashMap<>();
	private int racesRecords = 1;
	private int index;

	/**
	 * @param run
	 * @return true if a new run was made
	 * 
	 * Creates a new Race with RunID = run, and increments index and raceNbr.
	 */
	public boolean startNewRace(int run){

		if(races == null)
			races = new Race[8];    //F or future races, max of 8, will implement group as well, that's why 8

//		else{
//
//			ensureCapacity(races.length * 2);
//
//		}
		
		if(raceNbr < races.length){      // For later on when an input is created, don't worry not an NOP!

			races[index] = new Race(this, channelSelected, channelSelected + 1);
			races[index].setRaceNbr(raceNbr + 1);
			races[index].setRun(run);
			raceNbr++;
			index++;

			return true;
		}

		return false;
	}
	
	/**
	 * Resets the index to 0.
	 */
	public void reset(){
		index = 0;
	}

	/**
	 * @param eSize
	 * 
	 * Resizes the races array to eSize.
	 */
	private void ensureCapacity(int eSize){

		Race[] temp = new Race[eSize];

		for(int i = 0; i < races.length; i++){

			temp[i] = races[i];
		}
		races = temp;
	}
	
	/**
	 * @return the number of active Races (i.e. Lanes for PARIND).
	 */
	public int racesActive(){
		
		if(races == null)
			return 0;

		int count = 0;
		
		for(int i = 0; i < races.length; i++){
			
			if(races[i] != null && races[i].isActive())
				count++;
		}
		
		return count;
	}

	/**
	 * Prepares racePool if null (i.e. gets the singleton from the Pool class).
	 */
	public void propRace(){

		if(racePool == null)
			racePool = Pool.getPool();
	}

	/**
	 * @param racer - the racer's bib number
	 * 
	 * Creates a Racer with bib number racer and adds them to racePool.
	 */
	public void makeOneRacer(int racer){

		if(racer >= 0)
			racePool.makeRacer(racer);	
	}

	/**
	 * @param racer - the Racer to add
	 * 
	 * adds racer to the last position of racePool.
	 */
	protected void addRacerToEndOfPool(Racer racer){
		racePool.addRacerLast(racer);
	}

	/**
	 * @param racer - the Racer to add
	 * 
	 * adds racer to the beginning of racePool.
	 */
	protected void addRacerToBeginningOfPool(Racer racer){
		racePool.addRacerBeginning(racer);
	}

	//	public void finishOneRacerOnRaceSelected(){
	//		
	//		races[raceNbr].CANCEL();
	//		
	//	}
	//	
	//	public void finishRaceSelected(){
	//		
	//		if(channelSelected == 1 || channelSelected == 2){
	//			
	//			races[0].finishRacer();
	//			
	//		}else{
	//			
	//			races[1].finishRacer();
	//			
	//		}
	//	}
	//	
	//	public void startOneRacerOnSelectedChannel(){
	//		
	//		races[raceNbr].startNRacers(1);
	//		
	//	}

	/**
	 * @param mod
	 * @param raceNbr
	 * 
	 * if mod is true - modifies the current entry in record with race number = raceNbr,
	 * else creates a new entry in record for the Race with race number = raceNbr. 
	 */
	protected void engrave(boolean mod, int raceNbr){

		if(mod){

			record.remove(raceNbr);

			int temp = channelSelected == 1 || channelSelected == 2 ? 0 : 1;

			record.put(raceNbr, races[temp]);

		}else{

			if(races[0] != null){

				record.put(racesRecords, races[0]);
				racesRecords++;
			}

			if(races[1] != null){

				record.put(racesRecords, races[1]);
				racesRecords++;
			}
		}
	}

	/**
	 * @return the array of races (lanes).
	 */
	public Race[] getRaces() {
		return races;
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

	//	public void stopLastRace(){
	//		
	//		while(!active.isEmpty()){
	//
	//			finishRacer();
	//			
	//		}
	//	}
	//	
	//	
	//	public void startNRacers(int n){
	//
	//		if(n <= racePool.racersAmount()){
	//
	//			for(int i = 0; i < n; i++){
	//				
	//				Channels.channels[0].TriggerSensor();
	//				Racer racer = racePool.startRacer();
	//				Channels.channels[0].activate(racer.getBib());
	//				active.add(racer);
	//	
	//			}
	//		}
	//	}
	//	
	//	
	//	public void finishRacer(){
	//		
	//		Racer racer = active.remove();
	//		
	//		
	//		Random rand = new Random();       //You told me it was random, nothing in the guidelines suggests otherwise
	//		int randomNum = rand.nextInt((20 - 0) + 1) + 0;
	//		int randomNum2 = rand.nextInt((20 - 0) + 1) + 0;
	//
	//		if(randomNum == randomNum2){
	//
	//			racer.setDNF();
	//			
	//		}
	//		
	//		Channels.channels[1].TriggerSensor();
	//		Channels.channels[1].activate(racer.getBib());
	//		racePool.returnRacer(racer);
	//		
	//	}
	
	/**
	 * @return size of racePool
	 */
	public int racersPoolSize(){

		return racePool == null ? 0 : racePool.getRacersAmount();
	}

	//	public int racersActive(){
	//
	//		return active.size();
	//
	//	}
	//	
	//	public void CANCEL(){
	//		
	//		racePool.returnCancel(active.remove());
	//		
	//	}

	/**
	 * @return the ID of the selected channel
	 */
	public int getChannelSelected() {
		return channelSelected;
	}

	/**
	 * @return the selected channel
	 */
	public Channels getCurrentChannel(){

		return Channels.channels[channelSelected - 1];
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

	/**
	 * @return the Racer at the beginning of racePool
	 * 
	 * Removes and returns the Racer at the beginning of racePool.
	 */
	protected Racer getRacer(){

		return racePool.removeRacerBeginning();
	}

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

	/**
	 * @param run - the ID of the Run to get
	 * @return array of Races (lanes) for the Run with ID = run
	 */
	public Race[] getSelectedRun(int run){

		Iterator<Integer> it = record.keySet().iterator();
		Stack<Race> tempStack = new Stack<>();

		while(it.hasNext()){

			Race temp = record.get(it.next());

			if(temp.getRun() == run)
				tempStack.push(record.remove(temp.getRaceNbr()));
			
		}
		
		Race[] tempArr = (Race[]) tempStack.toArray(new Race[tempStack.size()]);

		return tempArr;
	}
	
}