package entitiesStatic;

import java.sql.Time;
import java.util.Date;

/**
 * @author Andy
 *
 * The Clock class, apart of the entitiesStatic package of the ChronoTimer.
 * This class is responsible for keeping track of the time.
 */
public class Clock implements Runnable {

	private boolean isClockRunning;
	private boolean isActive;
	private Thread treadClock;
	private Time time;
	private boolean isTimeSet;
	private final int precision_factor_dydx = 4;
	private boolean displayCurrent;
	private int dxdy;
	private int timeDispFreq;

	public Clock(){}

	/**
	 * Helper method that updated the time field.
	 */
	private void keepTrackOfTime(){

		time = new Time(time.getTime() + precision_factor_dydx);

		ClockInterface.setCurrentTime(time.getTime());

	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		while(isClockRunning){


			try{

				Thread.sleep(precision_factor_dydx);

			}catch(InterruptedException ex){

				ex.printStackTrace();

			}


			if(isActive){

				keepTrackOfTime();

				if(displayCurrent && (dxdy == timeDispFreq)){

					System.out.println("Current time: " + ClockInterface.getCurrentTimeFormatted());
					dxdy = 0;

				}

				if(displayCurrent){

					dxdy++;
					timeDispFreq = timeDispFreq == 0 ? 1000 : timeDispFreq;

				}
			}
		}

		clockStop();

	}

	/**
	 * Starts the clock on a new thread if it is not currently running
	 */
	public synchronized void clockStart(){

		if(isClockRunning){

			return;

		}

		if(isTimeSet){

			isClockRunning = true;
			this.treadClock = new Thread(this);
			this.treadClock.start();
			isActive = true;
			dxdy = 0;

		}
	}

	/**
	 * Stops the clock and joins with the main thread.
	 */
	public synchronized void clockStop() {

		if(!isClockRunning){

			return;

		}

		isClockRunning = false;

		try{

			treadClock.join();

		}catch(InterruptedException ex){

			ex.printStackTrace();

		}
	}

	/**
	 * Resets the clock's time to the epoch.
	 */
	public void reset(){

		this.time = new Time(000000);

	}

	/**
	 * Setter for time field.
	 * @param time : Time
	 */
	public void setTime(Time time) {
		this.time = time;
		isTimeSet = true;
	}

	/**
	 * Setter for time field.
	 * @param time : Date
	 */
	public void setTime(Date time) {

		this.time = new Time(time.getTime());
		isTimeSet = true;
	}

	/**
	 * @return isClockRunning
	 */
	public boolean isClockRunning() {
		return isClockRunning;
	}

	/**
	 * Setter for isActive field.
	 * @param isActive
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * Getter for isActive field.
	 * @return isActive
	 */
	public boolean isActive() {
		return isActive;
	}
	
	/**
	 * Setter for displayCurrent field.
	 * @param displayCurrent
	 */
	public void setDisplayCurrent(boolean displayCurrent) {
		this.displayCurrent = displayCurrent;
	}
	
	/**
	 * Setter for timeDispFreq field.
	 * @param timeDispFreq
	 */
	public void setTimeDispFreq(int timeDispFreq) {
		this.timeDispFreq = timeDispFreq;
	}
}