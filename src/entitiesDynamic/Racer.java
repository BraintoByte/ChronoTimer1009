package entitiesDynamic;

import Utils.Printer;
import entitiesStatic.ClockInterface;

/**
 * @author Andy & Matt
 * The Racer class, apart of the entitiesDynamic package of the ChronoTimer1009.
 * A racer object represents a competitor in the timing system identified with a bib number.
 * Racers also have an associated name, start time, finish time, and DNF field that are used in other areas of the system.
 */
public class Racer implements Cloneable {
	
	private int bib;
	private String timeStartFormatted;
	private String timeFinishFormatted;
	private long finish;
	private long start;
	private double totalTime;
	private boolean DNF;
	volatile private boolean isActive;
	volatile private double runningTime;
	
	/**
	 * @param bib number as an integer
	 * Constructor for Racer.
	 */
	public Racer(int bib){

		this.bib = bib;
		this.DNF = false;
		this.isActive = false;
		
	}
	
	public void reset(){
		
		this.DNF = false;
		this.isActive = false;
		timeStartFormatted = "";
		timeFinishFormatted = "";
		finish = 0;
		start = 0;
		
	}

	/**
	 * @return bib number
	 */
	public int getBib() {
		return bib;
	}
	
	public void setTimeStartFormatted(String timeStartFormatted) {
		this.timeStartFormatted = timeStartFormatted;
	}
	
	public void setTimeFinishFormatted(String timeFinishFormatted) {
		this.timeFinishFormatted = timeFinishFormatted;
	}
	
	public String getTimeStartFormatted() {
		return timeStartFormatted;
	}

	public String getTimeFinishFormatted() {
		return timeFinishFormatted;
	}
	
	@Override
	public Racer clone() {
		
		Racer temp = null;
		
		try{
		
			temp = (Racer) super.clone();
		
		}catch(CloneNotSupportedException ex){
			
		}
		
		return temp;
	}
	
	
	public void setStartInLong(long start) {
		this.start = start;
	}
	
	public void setFinishInLong(long finish) {
		this.finish = finish;
	}
	
	public long getStartInLong() {
		return start;
	}
	
	public long getFinishInLong() {
		return finish;
	}

	/**
	 * Sets the racer's time to Did Not Finish (DNF).
	 */
	public void setDNF() {
		isActive = false;
		this.DNF = true;
		this.timeStartFormatted = "DNF";
		this.timeFinishFormatted = "DNF";
		Printer.printToConsole("RACER: " + bib + " DNFFED!\n");
	}

	/**
	 * @return true if the racer DNF.
	 */
	public boolean isDNF() {
		return DNF;
	}

	public double getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(double d) {
		this.totalTime = d;
	}
	
	public void setIsActive(boolean active) {
		isActive = active;
		if(active) {
			
			new Thread(new Runnable() {

				@Override
				public void run() {
					
					while(isActive) {
						
						runningTime = (double) ClockInterface.computeDifference(start, ClockInterface.getTimeInLong()) /1000;
					}
				}
				
			}).start();
			
		}
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public double getRunningTime() {
		return runningTime;
	}
}