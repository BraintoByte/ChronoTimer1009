package entitiesDynamic;

import entitiesStatic.ClockInterface;

/**
 * @author Andy & Matt
 * The Racer class, apart of the entitiesDynamic package of the ChronoTimer1009.
 * A racer object represents a competitor in the timing system identified with a bib number.
 * Racers also have an associated name, start time, finish time, and DNF field that are used in other areas of the system.
 */
public class Racer {
	
	private int bib;						// @Andy
	private String timeStartFormatted;		// Why are these not being used?
	private String timeFinishFormatted;
	private long timeStart;
	private long timeFinish;
	private boolean DNF;
	private boolean isActive;
	
	/**
	 * @param bib number as an integer
	 * Constructor for Racer.
	 */
	public Racer(int bib){

		this.bib = bib;
		this.DNF = false;
		this.timeStart = 0;
		this.timeFinish = 0;
		this.isActive = false;
	}

	/**
	 * @return bib number
	 */
	public int getBib() {
		return bib;
	}

	/**
	 * Sets the racer's time to Did Not Finish (DNF).
	 */
	public void setDNF() {
		timeStart = 0;
		timeFinish = 0;
		isActive = false;
		this.DNF = true;
	}

	/**
	 * @return true if the racer DNF.
	 */
	public boolean isDNF() {
		return DNF;
	}
}