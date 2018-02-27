package hardware.external.pad;

import entitiesStatic.Clock;
import entitiesStatic.ClockInterface;
import hardware.external.Sensor;

public class Pad implements Sensor{

	private static Pads pads = Pads.getSingletonPads();
	private boolean pressed;
	private int pressCount;
	private final int id;
	private long startTime;
	private long endTime;


	/**
	 * @param id
	 */
	public Pad(int id) {

		this.id = id;
		this.pads.addPad(this);

	}


	/**
	 * @return
	 */
	public boolean hasBeenPressed(){
		return pressed;
	}

	/**
	 * 
	 */
	public void press(){

		if(pressCount > 2){
			
			trigger();
			this.pressCount++;
			this.pressed = !this.pressed;
			
		}
	}
	
	/**
	 * 
	 */
	private void trigger(){
		
		if(pressed && pressCount == 0){
			
			startTime = ClockInterface.getTimeInLong();
			
		}else{
			
			endTime = ClockInterface.getTimeInLong();
			
		}
	}
	
	/**
	 * 
	 */
	public void resetPad(){
		this.pressCount = 0;
		this.pressed = false;
		this.startTime = 0;
		this.endTime = 0;
	}

	/**
	 * @return
	 */
	public int getPressCount() {
		return pressCount;
	}


	/* (non-Javadoc)
	 * @see hardware.external.Sensor#getCurrentFormattedTime()
	 */
	/* (non-Javadoc)
	 * @see hardware.external.Sensor#getCurrentFormattedTime()
	 */
	@Override
	public String getCurrentFormattedTime() {
		return ClockInterface.getCurrentTimeFormatted();
	}
	
	/* (non-Javadoc)
	 * @see hardware.external.Sensor#getCurrentTimeInLong()
	 */
	/* (non-Javadoc)
	 * @see hardware.external.Sensor#getCurrentTimeInLong()
	 */
	@Override
	public long getCurrentTimeInLong() {
		return ClockInterface.getTimeInLong();
	}
	
	
	/* (non-Javadoc)
	 * @see hardware.external.Sensor#computeTime()
	 */
	/* (non-Javadoc)
	 * @see hardware.external.Sensor#computeTime()
	 */
	@Override
	public long computeTime() {
		return ClockInterface.computeTime(this.startTime, this.endTime);
	}
	
	/* (non-Javadoc)
	 * @see hardware.external.Sensor#getId()
	 */
	/* (non-Javadoc)
	 * @see hardware.external.Sensor#getId()
	 */
	@Override
	public int getId() {
		
		return this.id;
	}

	/* (non-Javadoc)
	 * @see hardware.external.Sensor#hasBeam()
	 */
	/* (non-Javadoc)
	 * @see hardware.external.Sensor#hasBeam()
	 */
	@Override
	public boolean hasBeam() {
		return false;
	}
}
