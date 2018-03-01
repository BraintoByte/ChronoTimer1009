package hardware.external.sensors.gate;

import entitiesStatic.ClockInterface;
import hardware.external.Sensor;

public class Gate implements Sensor {
	
	private static Gates gates = Gates.getSingletonGates();
	private final int id;
	private long startTime;
	private long endTime;
	private boolean isTrigger;
	
	
	
	/**
	 * @param id
	 */
	public Gate(int id) {
		
		this.id = id;
		this.gates.addGate(this);
		
	}
	
	/**
	 * 
	 */
	public void setStartTime(){
		
		this.startTime = ClockInterface.getTimeInLong();
		
	}
	
	/**
	 * 
	 */
	public void setEndTime(){
		
		this.endTime = ClockInterface.getTimeInLong();
		
	}
	
	
	
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
		return false;
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
