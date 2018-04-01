package environment.channels;

import environment.Channels;
import hardware.external.Sensor;

public class Ch5 extends Channels{
	
	public Ch5(int chId) {
		super("Channels", chId);
	}
	
	
	/* (non-Javadoc)
	 * @see environment.Channels#activate(int)
	 */
	@Override
	public void activate(int bid) {
		super.activate(bid);
	}

	/* (non-Javadoc)
	 * @see environment.Channels#retrieve(int)
	 */
	@Override
	public Long retrieve(int bid) {
		return super.retrieve(bid);
	}
	
	/* (non-Javadoc)
	 * @see environment.Channels#TriggerSensor()
	 */
	@Override
	public void TriggerSensor() {
		super.TriggerSensor();
	}
	
	/* (non-Javadoc)
	 * @see environment.Channels#isSensorTriggered()
	 */
	@Override
	public boolean isSensorTriggered() {
		return super.isSensorTriggered();
	}
	
	/* (non-Javadoc)
	 * @see environment.Channels#enable(boolean)
	 */
	@Override
	public void enable(boolean isEnabled) {
		super.enable(isEnabled);
	}
	
	/* (non-Javadoc)
	 * @see environment.Channels#isEnabled()
	 */
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
	 * @see environment.Channels#unPairToSensor()
	 */
	@Override
	public Sensor unPairToSensor() {
		return super.unPairToSensor();
	}

}
