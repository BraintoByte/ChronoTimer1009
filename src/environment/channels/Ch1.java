package environment.channels;

import environment.Channels;
import hardware.external.Sensor;

public class Ch1 extends Channels {
	
	public Ch1(int id) {
		
		super("ch1", id);
		
	}
	
	@Override
	public void isEnabled(boolean isEnabled) {
		super.isEnabled(isEnabled);
	}
	
	@Override
	public Sensor[] isPairedToSensor() {
		return super.isPairedToSensor();
	}
	
	@Override
	public void pairToSensor(Sensor sensor) {
		super.pairToSensor(sensor);
	}
	
	@Override
	public void unPairToSensor(Sensor sensor) {
		super.unPairToSensor(sensor);
	}
}
