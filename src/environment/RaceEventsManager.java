package environment;

import java.util.LinkedList;
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
			//			gateAvailable = new Stack<>();
			//			eyeAvailable = new Stack<>();
			//			padAvailable = new Stack<>();

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
					System.out.println(Channels.channels[channel - 1].isPairedToSensor());

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
	private Queue<Racer> active;
	
	
	/**
	 * 
	 * 
	 */
	
	public void propRace(){

		racePool = new Pool();
		active = new LinkedList<>();
		racePool.setRacersAmount(250);

	}


	/**
	 * Set a specific channel to be the selected channel
	 * 
	 * @param channelSelected
	 * @return true if channel exists and will be selected, false if not!
	 */
	public boolean setChannelSelected(int channelSelected) {

		if(channelSelected <= Channels.channels.length){

			this.channelSelected = channelSelected;

			return true;

		}

		return false;

	}
	
	
	/**
	 * 
	 * 
	 */
	
	public void stopLastRace(){
		
		while(!active.isEmpty()){

			Racer temp = active.poll();
			
			Random rand = new Random();       //You told me it was random, nothing in the guidelines suggests otherwise
			int randomNum = rand.nextInt((20 - 0) + 1) + 0;
			int randomNum2 = rand.nextInt((20 - 0) + 1) + 0;

			if(randomNum == randomNum2){

				temp.setDNF();
				
			}
			
			rand = new Random();       //You told me it was random, nothing in the guidelines suggests otherwise
			randomNum = rand.nextInt((20000000 - 25000) + 1) + 0;
			randomNum2 = rand.nextInt((20000000 - 40000) + 1) + 0;
			
			if(active.size() > 1){
				
				temp.addTimeForSimulation(randomNum > randomNum2 ? randomNum : randomNum2);
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}      //Uncomment for GLORIUS suspense effect! JUST LIKE THE OLYMPICS!
				
			}
			
			temp.setTimeFinish();
			racePool.returnRacer(temp);
			
			Channels.channels[1].enable(true);    //Doesn't matter
			Channels.channels[0].enable(true);   //In the future this will be more coordinated
			Channels.channels[1].TriggerSensor();
			

		}
		
		System.out.println("Active racers: " + active.size());
		
	}
	
	/**
	 * 
	 * 
	 * @param n
	 */
	
	public void startNRacers(int n){


		if(n <= racePool.racersAmount()){

			for(int i = 0; i < n; i++){

				Racer temp = racePool.startRacer();

				temp.setTimeStart();
				active.add(temp);
	
			}
		}
		
		System.out.println("Active racers: " + active.size() + " Time: " + active.peek().getTimeStartFormatted());

		if(active.size() != 0){

			Channels.channels[0].enable(true);
			Channels.channels[0].TriggerSensor();

		}
	}

	public int racersActive(){

		return active.size();

	}
	
	public void CANCEL(){
		
		System.out.println("Pool before: " + racePool.racersAmount() + " Active: " + racersActive());
		racePool.returnCancel(active.remove());
		System.out.println("Pool now: " + racePool.racersAmount() + " Active: " + racersActive());
		
	}
	
	

	
	
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

	/**
	 * Helper of {@link RaceEventsManager#allPairedSensors()} puts one channel sensors into the final array from index to index
	 * and sends back the ending index
	 * 
	 * @param sensors
	 * @param tempSensor
	 * @param fromIndex
	 * @return fromIndex
	 */
	private int putIntoArray(Sensor[] sensors, Object[] tempSensor, int fromIndex){

		for(int i = 0; i < tempSensor.length; i++){

			sensors[fromIndex] = (Sensor) tempSensor[i];
			fromIndex++;
		}

		return fromIndex;

	}
}