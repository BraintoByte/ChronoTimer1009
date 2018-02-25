package entitiesDynamic;

import entitiesStatic.Time;

public class Racer {
	
	private int bib;
	private String timeStartFormatted;
	private String timeFinishFormatted;
	private long timeStart;
	private long timeFinish;
	private boolean DNF;
	private boolean isActive;
	
	public Racer(int bib){

		this.bib = bib;
		this.DNF = false;
		this.timeStart = 0;
		this.timeFinish = 0;
		this.isActive = false;
		
	}
	
	
	protected void setTimeStart(){
		
		timeStart = Time.getTimeInLong();
		setStartTimeFormatted();
		isActive = true;
		
	}
	
	protected void setTimeFinish(){
		
		this.timeFinish = Time.getTimeInLong();
		setFinishTimeFormatted();
		isActive = false;
		
	}
	
	public long getTotalTime(){
		
		return DNF ? 0 : Time.computeTime(timeStart, timeFinish);
		
	}
	
	private void setStartTimeFormatted(){
		
		timeStartFormatted = Time.getFormattedTime();
		
	}
	
	private void setFinishTimeFormatted(){
		
		timeFinishFormatted = DNF ? "DNF" : Time.getFormattedTime();
		
	}

	public int getBib() {
		return bib;
	}


	public String getTimeStartFormatted() {
		return timeStartFormatted;
	}


	public String getTimeFinishFormatted() {
		return timeFinishFormatted;
	}

	
	public void setDNF() {
		setFinishTimeFormatted();
		isActive = false;
		this.DNF = true;
	}

	public boolean isDNF() {
		return DNF;
	}
}
