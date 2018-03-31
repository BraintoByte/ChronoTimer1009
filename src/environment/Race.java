package environment;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import entitiesDynamic.Racer;
import entitiesStatic.ClockInterface;

/**
 * @author Andy & Matt
 * 
 * The Race class, apart of the environment package of the ChronoTimer1009.
 * The Race class represents a race in the system, sometimes populated with racers in a queue called 'active'.
 */
public class Race {

	private int[] onChannels;
	private Queue<Racer> active;
	private Queue<Racer> record;
	private RaceEventsManager manager;
	private int raceNbr;
	private int run;

	/**
	 * @param manager - the RaceEventManager associated with this Race.
	 * @param channels - an unspecified set of channels associated with this Race.
	 * 
	 * Constructor for Race that takes a RaceEventManager and channel(s) as parameters.
	 */
	public Race(RaceEventsManager manager, int ... channels){

		this.onChannels = channels;
		this.manager = manager;
		this.active = new LinkedList<>();
		this.record = new LinkedList<>();

	}

	/**
	 * Finishes all of the Racers in the active queue in the order that they are,
	 * then engraves (saves) the Race.
	 */
	public void stopLastRace(){

		while(!active.isEmpty()){
			finishRacer(false);
		}

		//		manager.engrave(false, 0);
	}

	/**
	 * @param n - the number of Racers
	 * Starts n Racers by removing them from the Pool and adding them to the active queue, 
	 * as well as records their bibs in 'bibsInRace'.
	 */
	public void startNRacers(int n){

		if(n <= manager.racersPoolSize()){

			for(int i = 0; i < n; i++){

				Racer racer = manager.getRacer();
				Channels.channels[onChannels[0] - 1].activate(racer.getBib());
				racer.setTimeStartFormatted(ClockInterface.getCurrentTimeFormatted());
				racer.setStartInLong(ClockInterface.getTimeInLong());
				active.add(racer);

			}
		}
	}

	/**
	 * @param raceNbr
	 * Sets the race number to raceNbr.
	 */
	public void setRaceNbr(int raceNbr) {
		this.raceNbr = raceNbr;
	}

	/**
	 * @return race number (raceNbr)
	 */
	public int getRaceNbr() {
		return raceNbr;
	}

	/**
	 * @param run
	 * Sets the runID (run) to run.
	 */
	public void setRun(int run) {
		this.run = run;
	}

	/**
	 * @return runID
	 * Gets the runID of this Run.
	 */
	public int getRun() {
		return run;
	}

	/**
	 * @param DNF
	 * @return Racer that finished
	 * Finishes and returns the lead Racer and records their finish time.
	 * If DNF is true the Racer is marked as Did Not Finish (DNF).
	 */
	public Racer finishRacer(boolean DNF){

		Racer racer = active.remove();

		if(DNF){

			racer.setDNF();

		}else{

			racer.setTimeFinishFormatted(ClockInterface.getCurrentTimeFormatted());
			racer.setFinishInLong(ClockInterface.getTimeInLong());

		}

//		Channels.channels[onChannels[0] - 1].TriggerSensor();			 
		Channels.channels[onChannels[1] - 1].activate(racer.getBib());
		manager.addRacerToEndOfPool(racer);
		Racer temp = racer.clone();
		
		System.out.println("Racer: " + temp.getBib() + " stopped");

		if(temp != null){

			record.add(temp);

		}

		return racer;
		
	}

	/**
	 * @return number of active racers (size of 'active' queue).
	 */
	public int racersActive() {    //To erase
		return active.size();
	}

	/**
	 * Removes the lead Racer from the 'active' queue and puts them back into the Pool as next to start.
	 */
	public void CANCEL(){
		manager.addRacerToBeginningOfPool(active.remove());
	}

	/**
	 * @return the stack of bib numbers (Racer ID's).
	 */
	//	public Stack<Integer> returnBibs(){
	//		return this.bibsInRace;
	//	}

	public Iterator<Racer> getRecord() {
		return record.iterator();
	}

	
	/**
	 * @return an array of active channel associated with this Race.
	 */
	public int[] getChannelsActive() {
		return onChannels;
	}

	/**
	 * @return true if there is an active Racer.
	 */
	public boolean isActive() {
		return !active.isEmpty();
	}
}