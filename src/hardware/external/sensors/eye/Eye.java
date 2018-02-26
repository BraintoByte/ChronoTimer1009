package hardware.external.sensors.eye;

import entitiesStatic.Clock;
import hardware.external.Sensor;

public class Eye implements Sensor {
	
	private static Eyes eyes = Eyes.getSingletonEyes();
	private boolean enabled;
	private final int id;
	private long startTime;
	private long endTime;
	
	
	
	public Eye(int id) {
		
		this.id = id;
		this.eyes.addEye(this);
		
	}
	
	private void triggerStart(){
		
		this.startTime = Clock.getTimeInLong();
		
	}
	
	private void triggerEnd(){
		
		this.endTime = Clock.getTimeInLong();
		
	}
	
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
	
	public boolean isRunning(){
		
		return enabled;
		
	}
	
	
	
	@Override
	public String getCurrentFormattedTime() {
		return Clock.getFormattedTime();
	}
	
	@Override
	public long getCurrentTimeInLong() {
		return Clock.getTimeInLong();
	}
	
	
	@Override
	public long computeTime() {
		return Clock.computeTime(this.startTime, this.endTime);
	}
	
	@Override
	public int getId() {
		
		return this.id;
	}

	@Override
	public boolean hasBeam() {
		return true;
	}
}
