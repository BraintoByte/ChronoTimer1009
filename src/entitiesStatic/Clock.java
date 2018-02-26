package entitiesStatic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Clock {

	private static final DateTimeFormatter formattedTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	public static String getFormattedTime(){

		LocalDateTime now = LocalDateTime.now();
		return formattedTime.format(now);

	}
	
	public static String getTotalTimeFormatted(long currentDateTime){
		
		Date currentDate = new Date(currentDateTime);
		
		return currentDate + "";
		
	}
	
	public static long getTimeInLong(){  //Nice
		
		return System.currentTimeMillis();
		
	}
	
	public static long computeTime(long start, long finish){
		
		return finish - start;
		
	}
}
