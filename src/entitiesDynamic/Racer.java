package entitiesDynamic;

import entitiesStatic.ClockInterface;

/**
 * @author Andy & Matt
 * 
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
	private boolean isAnonymous;
	
	/**
	 * @param bib number as an integer
	 * Constructor for Racer.
	 */
	public Racer(int bib){

		this.bib = bib;
		this.DNF = false;
		this.isActive = false;
		
	}
	
	/**
	 * Resets all of the Racers fields.
	 */
	public void reset(){
		
		this.DNF = false;
		this.isActive = false;
		this.isAnonymous = false;
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
	
	/**
	 * Used for anonymous Group races.
	 * Sets this racer as an anonymous racer/"ghost" racer.
	 * Allows the user to edit the "ghost" Racer's bib number {@link #setBib(int)}.
	 */
	public void setAnonymous() {
		isAnonymous = true;
	}
	
	/**
	 * @param bib - the new bib number for this Racer
	 * @return true if successful
	 * 
	 * Used for anonymous Group races.
	 * Assigns this Racer's bib to the parameter bib if the Racer is an anonymous Racer.
	 */
	public boolean setBib(int bib) {
		
		if(!isAnonymous)
			return false;
		
		this.bib = bib;
		return true;
		
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Racer clone() {
		
		Racer temp = null;
		
		try {
		
			temp = (Racer) super.clone();
		
		} catch(CloneNotSupportedException ex) {
			
		}
		
		return temp;
	}
	
	/**
	 * @param start
	 * Setter for start field.
	 */
	public void setStartInLong(long start) {
		this.start = start;
	}
	
	/**
	 * @param finish
	 * Setter for finish field.
	 */
	public void setFinishInLong(long finish) {
		this.finish = finish;
	}
	
	/**
	 * Getter for start field.
	 */
	public long getStartInLong() {
		return start;
	}
	
	/**
	 * Getter for finish field.
	 * @return finish
	 */
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
	}

	/**
	 * @return true if the racer DNF.
	 */
	public boolean isDNF() {
		return DNF;
	}

	/**
	 * Getter for totalTime field.
	 * @return totalTime
	 */
	public double getTotalTime() {
		return totalTime;
	}

	/**
	 * Setter for totalTime field.
	 * @param time
	 */
	public void setTotalTime(double time) {
		this.totalTime = time;
	}
	
	/**
	 * Sets the isActive field to the boolean parameter active, 
	 * then starts a thread that continuously computes and updates the Racer's runningTime
	 * @param active
	 */
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
	
	/**
	 * @return true if Racer is active
	 */
	public boolean isActive() {
		return isActive;
	}
	
	/**
	 * Getter for runningTime field.
	 * @return runningTime
	 */
	public double getRunningTime() {
		return runningTime;
	}
	
}