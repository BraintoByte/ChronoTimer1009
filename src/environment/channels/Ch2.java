package environment.channels;

import environment.Channels;
import hardware.external.Sensor;

public class Ch2 extends Channels {

	public Ch2(int chId) {
		super("ch2", chId);	
	}
	
	@Override
	public void isEnabled(boolean isEnabled) {
		super.isEnabled(isEnabled);
	}
	
	@Override
	public Sensor[] pairedToSensors() {
		return super.pairedToSensors();
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
	public Sensor unPairToSensor(Sensor sensor) {
		return super.unPairToSensor(sensor);
	}
}