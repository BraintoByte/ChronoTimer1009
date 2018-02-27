package hardware.external.sensors.eye;

import entitiesStatic.Clock;
import entitiesStatic.ClockInterface;
import hardware.external.Sensor;

public class Eye implements Sensor {
	
	private static Eyes eyes = Eyes.getSingletonEyes();
	private boolean enabled;
	private final int id;
	private long startTime;
	private long endTime;
	
	
	
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
	
	/**
	 * @return
	 */
	public boolean trigger(){
		
		if(enabled){
			
			triggerStart();
			enabled = !enabled;
			
			return true;
		
		}
			
			triggerEnd();
			enabled = !enabled;
			
			return true;
			
	}
	
	/**
	 * @return
	 */
	public boolean isRunning(){
		
		return enabled;
		
	}
	
	
	public void resetEye(){
		
		this.enabled = false;
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
}
