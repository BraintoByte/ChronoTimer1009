package environment;

import Exceptions.NoSuchSensorException;
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

		protected void couple(int sensor, int channel, boolean eye) throws NoSuchSensorException {

			if(factory.getAmountEye() > 0 || factory.getAmountGates() > 0){

				if(channel < Channels.channels.length){

					Channels.channels[channel].pairToSensor(eye ? factory.findEye(sensor) : factory.findGate(sensor));
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


	public boolean setChannelSelected(int channelSelected) {
		
		if(channelSelected < Channels.channels.length){
			
			this.channelSelected = channelSelected;
			
			return true;
		
		}
		
		return false;
		
	}

	public int getChannelSelected() {
		return channelSelected;
	}

	public void theseManySensors(int gates, int eyes){

		sensors = new SensorCoupler();
		sensors.initialiazeSensors().makeSensors(gates + eyes, eyes > 0, gates > 0);

	}

	public void CONN(int sensor, boolean eye){

		if(channelSelected != 0){

			try {

				sensors.couple(sensor, channelSelected, eye);

			} catch (NoSuchSensorException e) {

				e.printStackTrace();

			}
		}
	}

	public int amountConnectedCh1(){  //JUST TESTS

		return Channels.channels[0].sensorsAmount();

	}

	public int amountConnectedCh2(){    //JUST TESTS

		return Channels.channels[1].sensorsAmount();

	}


	public int getAmountConnectedOnSelectedChannel(){    //JUST TESTS

		return Channels.channels[channelSelected].sensorsAmount();

	}


	public Sensor[] allPairedSensors(){

		Sensor[] sensors = new Sensor[Channels.channels[0].sensorsAmount() + Channels.channels[1].sensorsAmount()];
		int index = 0;

		for(int i = 0; i < Channels.channels.length; i++){

			index = putIntoArray(sensors, Channels.channels[i].pairedToSensors(), index);

		}

		return sensors;

	}

	private int putIntoArray(Sensor[] sensors, Sensor[] tempSensor, int fromIndex){

		for(int i = 0; i < sensors.length; i++){

			sensors[fromIndex] = tempSensor[i];
			fromIndex++;
		}

		return fromIndex;

	}
}