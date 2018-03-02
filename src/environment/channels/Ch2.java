package environment.channels;

import environment.Channels;
import hardware.external.Sensor;

public class Ch2 extends Channels {

	/**
	 * @param chId
	 */
	public Ch2(int chId) {
		super("ch2", chId);	
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