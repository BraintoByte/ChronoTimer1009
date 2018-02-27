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
	
	/* (non-Javadoc)
	 * @see environment.Channels#isEnabled(boolean)
	 */
	@Override
	public void isEnabled(boolean isEnabled) {
		super.isEnabled(isEnabled);
	}
	
	/* (non-Javadoc)
	 * @see environment.Channels#pairedToSensors()
	 */
	@Override
	public Object[] pairedToSensors() {
		return super.pairedToSensors();
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
	public Sensor unPairToSensor(Sensor sensor) {
		return super.unPairToSensor(sensor);
	}
}
