package environment;

import exceptions.NoSuchSensorException;
import hardware.external.Sensor;
import hardware.external.SensorFactory;

public class RaceEventsManager {


	//	private class ChannelManager {
	//
	//		protected Channels getChannel1(){
	//			return Channels.channels[0];
	//		}
	//
	//		protected Channels getChannel2(){
	//			return Channels.channels[1];
	//		}
	//	}
	
	//TODO: MAKE A SENSOR VARIABLE THAT KEEPS TRACK OF THE SENSOR AMOUNTS AND IN CONSEQUENCE ASSIGN A NEW NUMBER TO IT!
	//TODO: CHANGE THE SENSOR ASSIGNMENTS MECHANISM, IT'S FUCKING RETARDED!
	
	//XXX: ALL COMMENTS HERE ARE FOR ME NOT FOR YOU DON'T DO ABSOULUTELY ANYTHING IN THE CODE! THANK YOU!

	private class SensorCoupler {

		private SensorFactory factory;

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
		
		protected void couple(int sensor, int channel, boolean eye, boolean gate, boolean pad) throws NoSuchSensorException {

			if(factory.getAmountEye() > 0 || factory.getAmountGates() > 0){

				if(channel < Channels.channels.length){

					Channels.channels[channel].pairToSensor(eye ? factory.findEye(sensor) : gate ? factory.findGate(sensor) : factory.findPad(sensor));
					System.out.println(Channels.channels[channel].isPairedToSensor());

				}
				
			}else{

				throw new NoSuchSensorException(factory);

			}
		}

		protected SensorFactory initialiazeSensors(){

			return factory;

		}
	}

	private int channelSelected;
	private SensorCoupler sensors;


	/**
	 * Set a specific channel to be the selected channel
	 * 
	 * @param channelSelected
	 * @return true if channel exists and will be selected, false if not!
	 */
	public boolean setChannelSelected(int channelSelected) {
		
		if(channelSelected < Channels.channels.length){
			
			this.channelSelected = channelSelected;
			
			return true;
		
		}
		
		return false;
		
	}

	/**
	 * Gets the selected channel
	 * 
	 * @return the channel selected
	 */
	public int getChannelSelected() {
		return channelSelected;
	}

	/**
	 * @param gates
	 * @param eyes
	 */
	public void theseManySensors(int gates, int eyes, int pads){

		sensors = new SensorCoupler();
		sensors.initialiazeSensors().makeSensors(gates + eyes + pads, eyes > 0, gates > 0, pads > 0);

	}

	/**
	 * Command CONN to connect a sensor to a channel
	 * 
	 * @param sensor
	 * @param eye
	 */
	public void CONN(int sensor, boolean eye, boolean gate, boolean pad){

		if(channelSelected != 0){

			try {

				sensors.couple(sensor, channelSelected, eye, gate, pad);

			} catch (NoSuchSensorException e) {

				e.printStackTrace();

			}
		}
	}

	/**
	 * @return
	 */
	public int amountConnectedCh1(){  //JUST TESTS

		return Channels.channels[0].sensorsAmount();

	}

	/**
	 * @return
	 */
	public int amountConnectedCh2(){    //JUST TESTS

		return Channels.channels[1].sensorsAmount();

	}


	/**
	 * Gets the total amount of sensors connected to the selected channels
	 * 
	 * @return Channels.channels[channelSelected].sensorsAmount()
	 */
	public int getAmountConnectedOnSelectedChannel(){    //JUST TESTS

		return Channels.channels[channelSelected].sensorsAmount();

	}


	/**
	 * Gets all sensors back
	 * 
	 * @return An array of sensors
	 */
	public Sensor[] allPairedSensors(){

		Sensor[] sensors = new Sensor[Channels.channels[0].sensorsAmount() + Channels.channels[1].sensorsAmount()];
		int index = 0;

		for(int i = 0; i < Channels.channels.length; i++){

			index = putIntoArray(sensors, Channels.channels[i].pairedToSensors(), index);

		}

		return sensors;

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
	private int putIntoArray(Sensor[] sensors, Sensor[] tempSensor, int fromIndex){

		for(int i = 0; i < sensors.length; i++){

			sensors[fromIndex] = tempSensor[i];
			fromIndex++;
		}

		return fromIndex;

	}
}