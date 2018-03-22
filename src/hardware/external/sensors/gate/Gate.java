package hardware.external.sensors.gate;

import entitiesStatic.ClockInterface;
import hardware.external.Sensor;

/**
 * @author Andy
 * The Gate sensor.
 */
public class Gate implements Sensor {
	
	private static Gates gates = Gates.getSingletonGates();
	private final int id;
	private long startTime;
	private long endTime;
	private boolean isTrigger;
	
	/**
	 * @param id - the ID of the Sensor
	 * Constructor for the Gate Sensor.
	 */
	public Gate(int id) {
		
		this.id = id;
		this.gates.addGate(this);
	}
	
	/**
	 * Sets the start time of this sensor to the current time.
	 */
	public void setStartTime(){
		
		this.startTime = ClockInterface.getTimeInLong();
	}
	
	/**
	 * Sets the end time of this sensor to the current time.
	 */
	public void setEndTime(){
		
		this.endTime = ClockInterface.getTimeInLong();
	}
	
	/**
	 * Resets the start and end time of this sensor.
	 */
	public void resetGate(){
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
