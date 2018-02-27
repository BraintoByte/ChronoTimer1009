package entitiesStatic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockInterface {
	
	private static long timeCurrent;
	private static final DateFormat formattedTime = new SimpleDateFormat("HH:mm:ss");
	
	/**
	 * @param currentDateTime
	 * @return
	 */
	public static String getTotalTimeFormatted(long currentDateTime){

		Date currentDate = new Date(currentDateTime);

		return currentDate + "";

	}
	
	public static String getCurrentTimeFormatted(){
		
		Date currentDate = new Date(timeCurrent);
		
		return formattedTime.format(currentDate) + "";
		
	}

	/**
	 * @return
	 */
	public static long getTimeInLong(){  //Nice

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
