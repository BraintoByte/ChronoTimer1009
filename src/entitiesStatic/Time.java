package entitiesStatic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {

	private static final DateTimeFormatter formattedTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	public static String getFormattedTime(){

		LocalDateTime now = LocalDateTime.now();
		return formattedTime.format(now);

	}
	
	public static long getTimeInDouble(){  //Nice
		
		return System.currentTimeMillis();
		
	}
	
	public static long computeTime(long start, long finish){
		
		return finish - start;
		
	}
}
