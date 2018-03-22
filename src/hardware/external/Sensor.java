package hardware.external;

/**
 * @author Andy
 * Sensor, apart of the hardware.external package, is the interface for all of the sensors (i.e. eye, gate, pad).
 */
public interface Sensor {
	
	/**
	 * @return the current time as a string in the form "HH:mm:ss"
	 */
	public String getCurrentFormattedTime();
	
	/**
	 * @return the current time as a long
	 */
	public long getCurrentTimeInLong();
	
	// @Andy I don't think we should have sensor keep track of time...
	public long computeDifferenceInTime();
	
	/**
	 * @return the ID of the Sensor
	 */
	public int getId();
	
	/**
	 * @return true if the sensor has a beam
	 */
	public boolean hasBeam();
	
	/**
	 * Triggers the Sensor
	 */
	public void trigger();
	
	/**
	 * @return true if the sensor has been triggered
	 */
	public boolean isTriggered();

}
