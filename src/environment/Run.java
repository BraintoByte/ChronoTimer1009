package environment;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import entitiesDynamic.Pool;
import entitiesDynamic.Racer;
import entitiesStatic.ClockInterface;
import environment.Run.Race;
import states.hardware.Idle.Run_Types;

public class Run {

	public class Race {

		private int[] onChannels;
		private Queue<Racer> active;
		private Queue<Racer> record;
		//		private RaceEventsManager manager;
		private int raceNbr;

		/**
		 * @param manager - the RaceEventManager associated with this Race.
		 * @param channels - an unspecified set of channels associated with this Race.
		 * 
		 * Constructor for Race that takes a RaceEventManager and channel(s) as parameters.
		 */
		protected Race(int ... channels){

			this.onChannels = channels;
			//			this.manager = manager;
			this.active = new LinkedList<>();
			this.record = new LinkedList<>();

		}

		/**
		 * Finishes all of the Racers in the active queue in the order that they are,
		 * then engraves (saves) the Race.
		 */
		protected void stopLastRace(Pool racePool){

			while(!active.isEmpty()){
				finishRacer(false, racePool);
			}
		}

		/**
		 * @param n - the number of Racers
		 * Starts n Racers by removing them from the Pool and adding them to the active queue, 
		 * as well as records their bibs in 'bibsInRace'.
		 */
		protected void startNRacers(int n, Pool racePool){

			if(n <= racePool.getRacersAmount()){

				for(int i = 0; i < n; i++){

					Racer racer = racePool.removeRacerBeginning();
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
		protected void setRaceNbr(int raceNbr) {
			this.raceNbr = raceNbr;
		}

		/**
		 * @return race number (raceNbr)
		 */
		protected int getRaceNbr() {
			return raceNbr;
		}

		/**
		 * @return runID
		 * Gets the runID of this Run.
		 */
		public int getRun() {
			return runNbr;
		}

		/**
		 * @param DNF
		 * @return Racer that finished
		 * Finishes and returns the lead Racer and records their finish time.
		 * If DNF is true the Racer is marked as Did Not Finish (DNF).
		 */
		protected Racer finishRacer(boolean DNF, Pool racePool){

			Racer racer = active.remove();

			if(DNF){

				racer.setDNF();

			}else{

				racer.setTimeFinishFormatted(ClockInterface.getCurrentTimeFormatted());
				racer.setFinishInLong(ClockInterface.getTimeInLong());

			}

			//			Channels.channels[onChannels[0] - 1].TriggerSensor();			 
			Channels.channels[onChannels[1] - 1].activate(racer.getBib());
			racePool.addRacerLast(racer);
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
		protected int racersActive() {    //To erase
			return active.size();
		}

		/**
		 * Removes the lead Racer from the 'active' queue and puts them back into the Pool as next to start.
		 */
		protected void CANCEL(Pool racePool){
			racePool.addRacerBeginning(active.remove());
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
		protected int[] getChannelsActive() {
			return onChannels;
		}

		/**
		 * @return true if there is an active Racer.
		 */
		protected boolean isActive() {
			return !active.isEmpty();
		}
	}



	private int raceNbr;
	private int index;
	private Race[] racesActive;
	private int runNbr;
	private Run_Types type;



	protected Run(int runNbr, Run_Types type){

		this.runNbr = runNbr;
		this.type = type;

	}


	protected boolean setNewRace(int channelSelected) {

		if(racesActive == null){
			switch(type){
			case IND:
				setRaceFromScratch(1);
				break;
			case PARIND:
				setNewRace(8);
				break;
			}
		}


		if(raceNbr < racesActive.length){      // For later on when an input is created, don't worry not an NOP!

			racesActive[index] = new Race(channelSelected, channelSelected + 1);
			racesActive[index].setRaceNbr(raceNbr + 1);
			raceNbr++;
			index++;

			return true;
		}

		return false;

	}

	protected Race[] getRaces() {
		return racesActive;
	}

	protected Race getRaceFromChannel(int channel){

		if(racesActive != null){
			
			for (int i = 0; i < racesActive.length; i++) {

				if(racesActive[i].onChannels[0] == channel || racesActive[i].onChannels[1] == channel){

					return racesActive[i];

				}
			}
		}

		return null;

	}

	private boolean setRaceFromScratch(int eSize){

		if(eSize < 8){

			racesActive = new Race[eSize];
			return true;

		}

		return false;
	}

	protected void resetIndex(){
		index = 0;
	}

	protected Race getARace(int raceNbr){
		return racesActive[raceNbr];
	}
}