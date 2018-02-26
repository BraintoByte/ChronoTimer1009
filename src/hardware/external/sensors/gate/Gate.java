package hardware.external.sensors.gate;

import entitiesStatic.Clock;
import hardware.external.Sensor;

public class Gate implements Sensor {
	
	private static Gates gates = Gates.getSingletonGates();
	private final int id;
	private long startTime;
	private long endTime;
	
	
	
	public Gate(int id) {
		
		this.id = id;
		this.gates.addGate(this);
		
	}
	
	public void setStartTime(){
		
		this.startTime = Clock.getTimeInLong();
		
	}
	
	public void setEndTime(){
		
		this.endTime = Clock.getTimeInLong();
		
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
		return false;
	}
}
