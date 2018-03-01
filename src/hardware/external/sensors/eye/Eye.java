package hardware.external.sensors.eye;

import entitiesStatic.Clock;
import entitiesStatic.ClockInterface;
import hardware.external.Sensor;

public class Eye implements Sensor {
	
	private static Eyes eyes = Eyes.getSingletonEyes();
	private final int id;
	private long startTime;
	private long endTime;
	private boolean isTrigger;
	
	
	
	/**
	 * @param id
	 */
	public Eye(int id) {
		
		this.id = id;
		this.eyes.addEye(this);
		
	}
	
	/**
	 * 
	 */
	private void triggerStart(){
		
		this.startTime = ClockInterface.getTimeInLong();
		
	}
	
	/**
	 * 
	 */
	private void triggerEnd(){
		
		this.endTime = ClockInterface.getTimeInLong();
		
	}
	
	
	public void resetEye(){
		
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
	public long computeTime() {
		return ClockInterface.computeTime(this.startTime, this.endTime);
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
		return true;
	}

	@Override
	public void trigger() {
		isTrigger = !isTrigger;
	}
	
	@Override
	public boolean isTriggered() {
		return isTrigger;
	}
}
