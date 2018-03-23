package environment;

import java.util.HashMap;
import entitiesStatic.ClockInterface;
import environment.channels.Ch1;
import environment.channels.Ch2;
import environment.channels.Ch3;
import environment.channels.Ch4;
import hardware.external.Sensor;

/**
 * @author Andy & Matt
 * The Channel Class, apart of the environment package of the Chronotimer1009.
 * Channel is in charge of handling triggers from either a paired sensor or a button/command.
 */
public abstract class Channels {

	public static Channels[] channels = new Channels[4];
	private HashMap<Integer, Long> activeRacers = new HashMap<>();
	private Sensor sensorPaired;
	private String name;
	private final int chId;
	private boolean isEnabled;

	// available channels
	private static Channels ch1 = new Ch1(1);
	private static Channels ch2 = new Ch2(2);
	private static Channels ch3 = new Ch3(3);
	private static Channels ch4 = new Ch4(4);

	/**
	 * @param name - the name of the channel
	 * @param chId - the ID of the channel
	 * Constructor for Channel.
	 */
	public Channels(String name, int chId) {

		this.name = name;
		this.chId = chId;
		channels[chId - 1] = this;
	}

	/**
	 * @param bib - the bib number of the racer to activate. 
	 * Adds the racer (i.e. the bib number) to the activeRacers HashMap.
	 */
	public void activate(int bib){
		
		activeRacers.put(bib, ClockInterface.getTimeInLong());
	}
	
	/**
	 * @param bib - the bib number of the racer to retrieve
	 * @return the time associated with the racer
	 */
	public Long retrieve(int bib){
		
		return activeRacers.get(bib);
	}
	
	/**
	 * Resets all of the channels.
	 */
	public static void reset(){
		
		for(Channels ch: channels) {
			ch.clearData();
			ch.unPairToSensor();
			ch.enable(false);
		}
		
	}
	
	/**
	 * Clears the activeRacers HashMap.
	 */
	public void clearData() {
		activeRacers.clear();
	}
	
	/**
	 * @param isEnabled
	 * Sets the channel's enabled state to isEnabled.
	 */
	public void enable(boolean isEnabled){

		this.isEnabled = isEnabled;
	}

	/**
	 * @return true if the channel is enabled.
	 */
	public boolean isEnabled() {
		
		return this.isEnabled;

	}

	/**
	 * Triggers the paired sensor.
	 */
	public void TriggerSensor(){

		sensorPaired.trigger();
	}

	/**
	 * @return true if the paired sensor is triggered
	 */
	public boolean isSensorTriggered(){

		return sensorPaired.isTriggered();
	}

	/**
	 * @param sensor
	 * Pairs sensor to the channel.
	 */
	public void pairToSensor(Sensor sensor){

		if(!isPairedToSensor())
			sensorPaired = sensor;
	}

	/**
	 * @return sensor or null
	 * unpairs the paired sensor if any and returns it.
	 */
	public Sensor unPairToSensor(){

		if(isPairedToSensor()){

			Sensor temp = sensorPaired;
			sensorPaired = null;
			return temp;
		}

		return null;
	}

	/**
	 * @return true if there is a sensor paired.
	 */
	public boolean isPairedToSensor(){
		return sensorPaired != null;
	}
}