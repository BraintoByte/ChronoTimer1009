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
	 * @return
	 */
	public int getBib() {
		return bib;
	}

	
	/**
	 * 
	 */
	public void setDNF() {
		timeStart = 0;
		timeFinish = 0;
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