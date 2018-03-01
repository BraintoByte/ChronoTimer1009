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
	public void enable(boolean isEnabled) {
		// TODO Auto-generated method stub
		super.enable(isEnabled);
	}
	
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return super.isEnabled();
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
	public Sensor unPairToSensor() {
		return super.unPairToSensor();
	}
}