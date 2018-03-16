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

public class RaceEventsManager {


	private class SensorCoupler {

		private SensorFactory factory;
		private Stack<Integer> gateAvailable;
		private Stack<Integer> eyeAvailable;
		private Stack<Integer> padAvailable;


		private SensorCoupler(){

			factory = new SensorFactory();

		}

		/**
		 * Couples a sensor to a channel
		 * 
		 * @param sensor
		 * @param channel
		 * @param eye
		 * @throws NoSuchSensorException
		 */

		protected void couple(int channel, boolean eye, boolean gate, boolean pad) throws NoSuchSensorException {

			if(factory.getAmountEye() > 0 || factory.getAmountGates() > 0){

				if(channel <= Channels.channels.length){

					int sensor = findNextAvailableSensor(gate, eye, pad);

					Channels.channels[channel - 1].pairToSensor(eye ? factory.findEyeIteratively(sensor) : gate ? factory.findGateIteratively(sensor) : 
						factory.findPadIteratively(sensor));

				}

			}else{

				throw new NoSuchSensorException(factory);

			}
		}

		private int findNextAvailableSensor(boolean gate, boolean eye, boolean pad){

			if(gate){

				if(gateAvailable == null || gateAvailable.isEmpty()){

					gateAvailable = factory.sensorToStack(gate, eye, pad);

				}

				if(gateAvailable.isEmpty()){

					throw new IllegalAccessError();

				}

				return gateAvailable.pop();

			}else if(eye){


				if(eyeAvailable == null || eyeAvailable.isEmpty()){

					eyeAvailable = factory.sensorToStack(gate, eye, pad);

				}

				if(eyeAvailable.isEmpty()){

					throw new IllegalAccessError();

				}

				return eyeAvailable.pop();

			}else if(pad){

				if(padAvailable == null || padAvailable.isEmpty()){

					padAvailable = factory.sensorToStack(gate, eye, pad);

				}

				if(padAvailable.isEmpty()){

					throw new IllegalAccessError();

				}

				return padAvailable.pop();

			}

			return -1;
		}

		protected void uncoupleSensors(int channel){

			if(channel - 1 < Channels.channels.length){

				Channels.channels[channel - 1].unPairToSensor();

			}
		}

		protected SensorFactory getSensors(){

			return factory;

		}
	}

	private int channelSelected;
	private SensorCoupler sensors;
	private Pool racePool;
	protected Race[] races;
	private int raceNbr;
	private HashMap<Integer, Race> record = new HashMap<>();
	private int racesRecords = 1;
	private int index;





	public boolean startNewRace(int run){

		if(races == null){

			races = new Race[8];    //For future races, max of 8, will implement group as well, that's why 8

		}
		
		
//		else{
//
//			ensureCapacity(races.length * 2);
//
//		}
		
		
		if(raceNbr < races.length){      //For later on when an input is created, don't worry not an NOP!

			races[index] = new Race(this, channelSelected, channelSelected + 1);
			races[index].setRaceNbr(raceNbr + 1);
			races[index].setRun(run);
			raceNbr++;
			index++;

			return true;

		}

		return false;
		
	}
	
	public void reset(){
		
		index = 0;
		
	}


	private void ensureCapacity(int eSize){

		Race[] temp = new Race[eSize];

		for(int i = 0; i < races.length; i++){

			temp[i] = races[i];

		}

		races = temp;

	}
	
	
	public int racesActive(){
		
		if(races == null){
			
			return 0;
			
		}
		
		int count = 0;
		
		for(int i = 0; i < races.length; i++){
			
			if(races[i] != null && races[i].isActive()){
				
				count++;
				
			}
		}
		
		return count;
		
	}


	public void propRace(){

		if(racePool == null){

			racePool = Pool.getPool();

		}
	}

	public void makeOneRacer(int racer){

		if(racer >= 0){
			racePool.makeRacer(racer);
		}
	}


	protected void returnRacer(Racer racer){

		racePool.returnRacer(racer);

	}


	protected void returnRacerCancel(Racer racer){

		racePool.returnCancel(racer);

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

	public Race[] getRaces() {
		return races;
	}

	//
	//
	//	/**
	//	 * Set a specific channel to be the selected channel
	//	 * 
	//	 * @param channelSelected
	//	 * @return true if channel exists and will be selected, false if not!
	//	 */
	//
	public boolean setChannelSelected(int channelSelected) {

		if(channelSelected <= Channels.channels.length){

			this.channelSelected = channelSelected;

			return true;

		}

		return false;

	}
	//	
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
	//	
	public int racersPoolSize(){

		return racePool == null ? 0 : racePool.racersAmount();

	}
	//	
	//
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
	 * Gets the selected channel
	 * 
	 * @return the channel selected
	 */

	public int getChannelSelected() {
		return channelSelected;
	}

	public Channels getCurrentChannel(){

		return Channels.channels[channelSelected - 1];

	}

	/**
	 * @param gates
	 * @param eyes
	 */
	public void theseManySensors(int gates, int eyes, int pads){

		sensors = new SensorCoupler();
		sensors.getSensors().makeSensors(eyes, gates, pads, eyes > 0, gates > 0, pads > 0);

	}

	/**
	 * Command CONN to connect a sensor to a channel
	 * 
	 * @param sensor
	 * @param eye
	 */
	public void CONN(boolean eye, boolean gate, boolean pad){

		if(channelSelected != 0){

			try {

				sensors.couple(channelSelected, eye, gate, pad);

			} catch (NoSuchSensorException e) {

				e.printStackTrace();

			}
		}
	}


	protected Racer getRacer(){

		return racePool.startRacer();

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


	public Race[] getSelectedRun(int run){

		Iterator<Integer> it = record.keySet().iterator();
		Stack<Race> tempStack = new Stack<>();

		while(it.hasNext()){

			Race temp = record.get(it.next());

			if(temp.getRun() == run){

				tempStack.push(record.remove(temp.getRaceNbr()));

			}
		}
		
		Race[] tempArr = (Race[]) tempStack.toArray(new Race[tempStack.size()]);

		return tempArr;

	}
}