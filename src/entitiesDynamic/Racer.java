package entitiesDynamic;

import entitiesStatic.ClockInterface;

public class Racer {
	
	private int bib;
	private String timeStartFormatted;
	private String timeFinishFormatted;
	private long timeStart;
	private long timeFinish;
	private boolean DNF;
	private boolean isActive;
	
	/**
	 * @param bib
	 */
	public Racer(int bib){

		this.bib = bib;
		this.DNF = false;
		this.timeStart = 0;
		this.timeFinish = 0;
		this.isActive = false;
		
	}
	
	
	/**
	 * 
	 */
	protected void setTimeStart(){
		
		timeStart = ClockInterface.getTimeInLong();
		setStartTimeFormatted();
		isActive = true;
		
	}
	
	/**
	 * 
	 */
	protected void setTimeFinish(){
		
		this.timeFinish = ClockInterface.getTimeInLong();
		setFinishTimeFormatted();
		isActive = false;
		
	}
	
	/**
	 * @return
	 */
	public long getTotalTime(){
		
		return DNF ? 0 : ClockInterface.computeTime(timeStart, timeFinish);
		
	}
	
	/**
	 * 
	 */
	private void setStartTimeFormatted(){
		
		timeStartFormatted = ClockInterface.getCurrentTimeFormatted();
		
	}
	
	/**
	 * 
	 */
	private void setFinishTimeFormatted(){
		
		timeFinishFormatted = DNF ? "DNF" : ClockInterface.getCurrentTimeFormatted();
		
	}

	/**
	 * @return
	 */
	public int getBib() {
		return bib;
	}


	/**
	 * @return
	 */
	public String getTimeStartFormatted() {
		return timeStartFormatted;
	}


	/**
	 * @return
	 */
	public String getTimeFinishFormatted() {
		return timeFinishFormatted;
	}

	
	/**
	 * 
	 */
	public void setDNF() {
		setFinishTimeFormatted();
		isActive = false;
		this.DNF = true;
	}

	/**
	 * @return
	 */
	public boolean isDNF() {
		return DNF;
	}
}