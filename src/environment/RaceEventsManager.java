package environment;

import java.util.Stack;

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

		protected SensorFactory getSensors(){

			return factory;

		}
	}

	private int channelSelected;
	private SensorCoupler sensors;
	private int sensorCount;


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

		return Channels.channels[channelSelected - 1].sensorsAmount();

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
	private int putIntoArray(Sensor[] sensors, Object[] tempSensor, int fromIndex){

		for(int i = 0; i < tempSensor.length; i++){

			sensors[fromIndex] = (Sensor) tempSensor[i];
			fromIndex++;
		}

		return fromIndex;

	}
}