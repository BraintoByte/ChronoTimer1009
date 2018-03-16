package entitiesStatic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;

public class ClockInterface {

	private static long timeCurrent;
	private static final DateFormat formattedTime = new SimpleDateFormat("HH:mm:ss");

	/**
	 * @param currentDateTime
	 * @return
	 */
	public static String getTotalTimeFormatted(long start, long finish){
		
		Date startDate = new Date(start);
		Date finishDate = new Date(finish);
		
		
		long difference = finishDate.getTime() - startDate.getTime();
		
		return "" + ((double) difference/1000) + "Seconds";

	}

	public static String formatTime(long time){

		Date currentDate = new Date(time);
		return formattedTime.format(time) + "";

	}

	public static String getCurrentTimeFormatted(){

		Date currentDate = new Date(timeCurrent);

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {

		}

		return formattedTime.format(currentDate) + "";

	}

	/**
	 * @return
	 */
	public static long getTimeInLong(){  //Nice

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {

		}
		return timeCurrent;

	}

	/**
	 * @param start
	 * @param finish
	 * @return
	 */
	public static long computeTime(long start, long finish){

		return finish - start;

	}


	protected static long getTimeCurrent() {
		return timeCurrent;
	}

	protected static void setTimeCurrent(long timeCurrent) {
		ClockInterface.timeCurrent = timeCurrent;
	}
}
