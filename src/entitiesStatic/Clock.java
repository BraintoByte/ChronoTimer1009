package entitiesStatic;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Clock implements Runnable {

	/**
	 * 
	 */

	private boolean isClockRunning;
	private boolean isActive;
	private Thread treadClock;
	private Time time;
	private boolean isTimeSet;
	private final int precision_factor_dydx = 1;

	public Clock(){}



	private void keepTrackOfTime(){

		time = new Time(time.getTime() + precision_factor_dydx);
		ClockInterface.setTimeCurrent(time.getTime());

	}

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

			}
		}

		clockStop();

	}

	public synchronized void clockStart(){

		if(isClockRunning){

			return;

		}

		if(isTimeSet){

			isClockRunning = true;
			this.treadClock = new Thread(this);
			this.treadClock.start();
			isActive = true;

		}
	}


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

	public void reset(){
		
		this.time = new Time(000000);
		
	}

	public void setTime(Time time) {
		this.time = time;
		isTimeSet = true;
	}

	public boolean isClockRunning() {
		return isClockRunning;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isActive() {
		return isActive;
	}
}
