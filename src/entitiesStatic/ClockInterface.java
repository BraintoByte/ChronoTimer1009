package entitiesStatic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Andy & Matt
 * 
 * The ClockInterface Class, apart of the entitesStatic package of the Chronotimer1009.
 * The ClockInterface is the interface for the Clock class in the system.
 */
public class ClockInterface {

	private static long currentTime;
	private static final DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

	/**
	 * @param start time as a long
	 * @param finish time as a long
	 * @return Difference between start and finish in seconds as a string
	 * 
	 * Gets the total time/duration formatted as a string.
	 */
	public static String getTotalTimeFormatted(long start, long finish){
		
		return ((double) computeDifference(start, finish) /1000) + " Seconds";
	}

	/**
	 * @param time as a long
	 * @return time parameter formatted as a string in the form "HH:mm:ss".
	 */
	public static String formatTime(long time){

		return timeFormatter.format(time) + "";
	}

	/**
	 * @return the current time of the system as a string in the form "HH:mm:ss".
	 */
	public static String getCurrentTimeFormatted(){

		Date currentDate = new Date(currentTime);

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) { }

		return timeFormatter.format(currentDate) + "";
	}

	/**
	 * @return the current time of the system as a long.
	 */
	public static long getTimeInLong(){

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		
		return currentTime;
	}

	/**
	 * @param start time as a long
	 * @param finish time as a long
	 * @return the difference between start and finish.
	 */
	public static long computeDifference(long start, long finish){
		return finish - start;
	}

	/**
	 * @return the current time of the system.
	 */
	protected static long getCurrentTime() {
		return currentTime;
	}

	/**
	 * @param timeCurrent
	 * Sets the current time of the ClockInterface to timeCurrent.
	 * (Used by Clock to update the time)
	 */
	protected static void setCurrentTime(long timeCurrent) {
		ClockInterface.currentTime = timeCurrent;
	}
}
