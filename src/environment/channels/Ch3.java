package environment.channels;

import environment.Channels;
import hardware.external.Sensor;

public class Ch3 extends Channels{

	public Ch3(int chId) {
		super("Ch3", chId);
	}
	
	@Override
	public void activate(int bid) {
		super.activate(bid);
	}
	
	@Override
	public void reset() {
		super.reset();
	}
	
	@Override
	public Long retrieve(int bid) {
		return super.retrieve(bid);
	}
	
	@Override
	public void TriggerSensor() {
		
		super.TriggerSensor();
	}
	
	@Override
	public boolean isSensorTriggered() {
		
		return super.isSensorTriggered();
	}
	
	@Override
	public void enable(boolean isEnabled) {
		
		super.enable(isEnabled);
	}
	
	@Override
	public boolean isEnabled() {
		
		return super.isEnabled();
	}
	

	@Override
	public boolean isPairedToSensor() {
		return super.isPairedToSensor();
	}
	
	@Override
	public void pairToSensor(Sensor sensor) {
		super.pairToSensor(sensor);
	}
	

	@Override
	public Sensor unPairToSensor() {
		return super.unPairToSensor();
	}
}