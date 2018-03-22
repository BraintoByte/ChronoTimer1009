package hardware.external.sensor.pad;

import entitiesStatic.Clock;
import entitiesStatic.ClockInterface;
import hardware.external.Sensor;

/**
 * @author Andy
 * The Pad sensor.
 */
public class Pad implements Sensor{

	private static Pads pads = Pads.getSingletonPads();
	private final int id;
	private long startTime;
	private long endTime;
	private boolean isTrigger;

	/**
	 * @param id - the ID of the Sensor
	 * Constructor for the Pad Sensor.
	 */
	public Pad(int id) {

		this.id = id;
		this.pads.addPad(this);
	}
	
	/**
	 * Resets the start and end time of this sensor.
	 */
	public void resetPad(){
		this.startTime = 0;
		this.endTime = 0;
	}

	/* (non-Javadoc)
	 * @see hardware.external.Sensor#getCurrentFormattedTime()
	 */
	@Override
	public String getCurrentFormattedTime() {
		return ClockInterface.getCurrentTimeFormatted();
	}
	
	/* (non-Javadoc)
	 * @see hardware.external.Sensor#getCurrentTimeInLong()
	 */
	@Override
	public long getCurrentTimeInLong() {
		return ClockInterface.getTimeInLong();
	}
	
	
	/* (non-Javadoc)
	 * @see hardware.external.Sensor#computeTime()
	 */
	@Override
	public long computeDifferenceInTime() {
		return ClockInterface.computeDifference(this.startTime, this.endTime);
	}
	
	/* (non-Javadoc)
	 * @see hardware.external.Sensor#getId()
	 */
	@Override
	public int getId() {
		
		return this.id;
	}
	
	/* (non-Javadoc)
	 * @see hardware.external.Sensor#hasBeam()
	 */
	@Override
	public boolean hasBeam() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see hardware.external.Sensor#trigger()
	 */
	@Override
	public void trigger() {
		isTrigger = !isTrigger;
	}
	
	/* (non-Javadoc)
	 * @see hardware.external.Sensor#isTriggered()
	 */
	@Override
	public boolean isTriggered() {
		return isTrigger;
	}
}
