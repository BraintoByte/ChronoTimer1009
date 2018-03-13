package environment;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import entitiesDynamic.Pool;
import entitiesDynamic.Racer;
import entitiesStatic.ClockInterface;
import environment.channels.Ch1;
import environment.channels.Ch2;
import environment.channels.Ch3;
import environment.channels.Ch4;
import hardware.external.Sensor;
import hardware.external.sensors.eye.Eye;
import hardware.external.sensors.gate.Gate;

public abstract class Channels {

	public static Channels[] channels = new Channels[2];
	private HashMap<Integer, Long> activeRacers = new HashMap<>();
	private Sensor sensorPaired;
	private String name;
	private final int chId;
	private boolean isEnabled;

	private static Channels ch1 = new Ch1(1);
	private static Channels ch2 = new Ch2(2);
	private static Channels ch3 = new Ch3(3);
	private static Channels ch4 = new Ch4(4);



	/**
	 * @param name
	 * @param chId
	 */
	public Channels(String name, int chId) {

		this.name = name;
		this.chId = chId;
		channels[chId - 1] = this;

	}


	public void activate(int bid){
		
		activeRacers.put(bid, ClockInterface.getTimeInLong());
		
	}
	
	public Long retrieve(int bid){
		
		return activeRacers.get(bid);
		
	}
	
	public void reset(){
		
		activeRacers.clear();
		
	}
	
	/**
	 * @param isEnabled
	 */
	public void enable(boolean isEnabled){

		this.isEnabled = isEnabled;

	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void TriggerSensor(){

		sensorPaired.trigger();
		
	}

	public boolean isSensorTriggered(){

		return sensorPaired.isTriggered();

	}

	/**
	 * @param sensor
	 */
	public void pairToSensor(Sensor sensor){

		if(sensorPaired == null){

			sensorPaired = sensor;

		}
	}

	/**
	 * @param sensor
	 * @return
	 */
	public Sensor unPairToSensor(){

		if(sensorPaired != null){

			Sensor temp = sensorPaired;

			sensorPaired = null;

			return temp;

		}

		return null;
	}

	/**
	 * @return
	 */
	public boolean isPairedToSensor(){
		return sensorPaired != null;
	}
}