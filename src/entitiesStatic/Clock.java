package entitiesStatic;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Andy & Matt
 * 
 * The Clock Class, apart of the entitesStatic package of the Chronotimer1009.
 * The Clock is responsible for keeping track of the system time.
 */
public class Clock implements Runnable {

	private boolean isClockRunning;		// @Andy
	private boolean isActive;			// do isActive and isClockRunning do the same thing?
	private Thread threadClock;
	private Time time;
	private boolean isTimeSet;
	private final int precision_factor_dydx = 1;

	/**
	 * Constructor for Clock.
	 */
	public Clock(){}

	/**
	 * Updates the time field and updates the ClockInterface's current time, effectively keeping track of the time.
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

				Thread.sleep(precision_factor_dydx);	// sleep for 1 second

			}catch(InterruptedException ex){

				ex.printStackTrace();
			}

			if(isActive)
				keepTrackOfTime();
			
		}

		clockStop();

	}

	/**
	 * Activates the clock on a new thread if the time was set. 
	 */
	public synchronized void clockStart(){

		if(isClockRunning)
			return;

		if(isTimeSet){

			isClockRunning = true;
			this.threadClock = new Thread(this);
			this.threadClock.start();
			isActive = true;
		}
	}


	/**
	 * Stops the clock by joining the clock thread.
	 */
	public synchronized void clockStop() {

		if(!isClockRunning)
			return;
		

		isClockRunning = false;

		try{

			threadClock.join();

		}catch(InterruptedException ex){

			ex.printStackTrace();

		}
	}

	// @Andy - why reset to that date?
	
	/**
	 * Resets the clock's time to January 1, 1970, 00:00:00 GMT
	 */
	public void reset(){
		
		this.time = new Time(000000);
	}

	/**
	 * @param time to set the clock to
	 * Sets the clock's time.
	 */
	public void setTime(Time time) {
		
		this.time = time;
		isTimeSet = true;
	}
	
	public void setTime(Date time) {
		
		this.time = new Time(time.getTime());
		isTimeSet = true;
	}

	/**
	 * @return true if clock is running.
	 */
	public boolean isClockRunning() {
		return isClockRunning;
	}

	/**
	 * @param isActive
	 * Sets the clock's isActive field to the parameter 'isActive'.
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return true if the clock is active.
	 */
	public boolean isActive() {
		return isActive;
	}
}
