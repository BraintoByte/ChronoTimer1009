package environment.channels;

import environment.Channels;
import hardware.external.Sensor;

public class Ch1 extends Channels {
	
	/**
	 * @param id
	 */
	public Ch1(int id) {
		
		super("ch1", id);
		
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
	
	/* (non-Javadoc)
	 * @see environment.Channels#isPairedToSensor()
	 */
	@Override
	public boolean isPairedToSensor() {
		return super.isPairedToSensor();
	}
	
	/* (non-Javadoc)
	 * @see environment.Channels#pairToSensor(hardware.external.Sensor)
	 */
	@Override
	public void pairToSensor(Sensor sensor) {
		super.pairToSensor(sensor);
	}
	
	/* (non-Javadoc)
	 * @see environment.Channels#unPairToSensor(hardware.external.Sensor)
	 */
	@Override
	public Sensor unPairToSensor() {
		return super.unPairToSensor();
	}
}