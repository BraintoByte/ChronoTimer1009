package environment;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import Utils.Printer;
import entitiesDynamic.Pool;
import entitiesDynamic.Racer;
import entitiesStatic.ClockInterface;
import hardware.user.InterfaceHandler;
import states.hardware.Idle.Run_Types;

public class Run {

	public class Race {

		private boolean isGroup;
		private int[] onChannels;
		private Queue<Racer> active;
		private Queue<Racer> record;
		private int raceNbr;

		/**
		 * @param channels
		 *  - an unspecified set of channels associated with this Race.
		 * 
		 * Constructor for Race that takes a RaceEventManager and channel(s)
		 * as parameters.
		 */
		protected Race(int... channels) {

			this.onChannels = channels;
			this.active = new LinkedList<>();
			this.record = new LinkedList<>();

		}

		/**
		 * Finishes all of the Racers in the active queue in the order that they are,
		 * then engraves (saves) the Race.
		 */
		protected void stopLastRace(Pool racePool) {

			while (!active.isEmpty()) {
				finishRacer(true, racePool);
			}
		}

		/**
		 * @param n
		 *            - the number of Racers Starts n Racers by removing them from the
		 *            Pool and adding them to the active queue, as well as records their
		 *            bibs in 'bibsInRace'.
		 */
		protected void startNRacers(int n, Pool racePool) {

			if (n <= racePool.getRacersAmount()) {

				for (int i = 0; i < n; i++) {

					Racer racer = racePool.removeRacerBeginning();
					racer.setTimeStartFormatted(ClockInterface.getCurrentTimeFormatted());
					racer.setStartInLong(ClockInterface.getTimeInLong());
					racer.setIsActive(true);
					active.add(racer);

				}

			}
		}

		/**
		 * @param raceNbr
		 *            Sets the race number to raceNbr.
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
		 * @return runID Gets the runID of this Run.
		 */
		public int getRun() {
			return runNbr;
		}

		public boolean isGRP() {
			return isGroup;
		}

		/**
		 * @param DNF
		 * @return Racer that finished Finishes and returns the lead Racer and records
		 *         their finish time. If DNF is true the Racer is marked as Did Not
		 *         Finish (DNF).
		 */
		protected Racer finishRacer(boolean DNF, Pool racePool) {

            Racer racer;
            if (!active.isEmpty()) {
                racer = active.remove();
                racer.setIsActive(false);

                if (type != Run_Types.PARIND && InterfaceHandler.isGUI()) {

                    Printer.clearMiddleTxt(2);

                }

                if (DNF) {

                    racer.setDNF();
                    if (InterfaceHandler.isGUI()) {

                        if (type == Run_Types.PARIND) {

                            if (countPrint == 1) {

                                Printer.clearMiddleTxt(2);
                                countPrint = 0;

                                Printer.printToMiddle(2, "<" + previousPrinted + "> <DNF>\n");

                            }

                            previousPrinted = racer.getBib();

                            Printer.printToMiddle(2, "<" + racer.getBib() + "> <DNF>\n");

                            countPrint++;
                        } else {

                            Printer.printToMiddle(2, "<" + racer.getBib() + "> <DNF>\n");
                        }
                    }

                } else {

                    racer.setTimeFinishFormatted(ClockInterface.getCurrentTimeFormatted());
                    racer.setFinishInLong(ClockInterface.getTimeInLong());
                    racer.setTotalTime((double) ClockInterface.computeDifference(racer.getStartInLong(), racer.getFinishInLong())/ 1000);

                    if (InterfaceHandler.isGUI()) {
                        if (type == Run_Types.PARIND) {

                            if (countPrint == 1) {

                                Printer.clearMiddleTxt(2);
                                countPrint = 0;

                                Printer.printToMiddle(2, "<" + previousPrinted + "> <" + 
                                ((double) ClockInterface.computeDifference(previousStart, previousLong) / 1000) + ">\n");

                            }

                            previousPrinted = racer.getBib();
                            previousLong = racer.getFinishInLong();
                            previousStart = racer.getStartInLong();

                            Printer.printToMiddle(2, "<" + racer.getBib() + "> <" + racer.getTotalTime() + ">\n");

                            countPrint++;
                        } else {

                            Printer.printToMiddle(2, "<" + racer.getBib() + "> <" + racer.getTotalTime() + ">\n");
                            
                        }
                    }

                }

                if (!isGroup && !InterfaceHandler.isGUI())
                    System.out.println("Racer: " + racer.getBib() + " stopped");

                record.add(racer);
                return racer;
            }
            return null;
        }

		/**
		 * @return number of active racers (size of 'active' queue).
		 */
		protected int racersActive() { // To erase
			return active.size();
		}

		/**
		 * Removes the lead Racer from the 'active' queue and puts them back into the
		 * Pool as next to start.
		 */
		protected Racer CANCEL() {
			if (!active.isEmpty())
				return RemoveLast(active);
			return null;
		}

		/**
		 * @return the stack of bib numbers (Racer ID's).
		 */
		// public Stack<Integer> returnBibs(){
		// return this.bibsInRace;
		// }

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

		protected Queue<Racer> getActive() {
			return active;
		}

		private Racer RemoveLast(Queue<Racer> q) {
			Racer first = q.peek();
			Racer current = null;
			if (q.size() == 1)
				return q.remove();

			while (true) {
				current = q.remove();
				if (q.peek() == first) {
					break;
				}
				q.add(current);
			}
			return current;
		}
	}

	private int raceNbr;
	private int index;
	private Race[] racesActive;
	private int runNbr;
	private Run_Types type;
	private int countPrint;
	private int previousPrinted;
	private long previousLong;
	private long previousStart;

	protected Run(int runNbr, Run_Types type) {

		this.runNbr = runNbr;
		this.type = type;

	}

	protected boolean setNewRace(int channelSelected) {

		if (racesActive == null) {
			switch (type) {
			case IND:
			case GRP:
				setRaceFromScratch(1);
				break;
			case PARIND:
			case PARGRP:
				setRaceFromScratch(8);
				break;
			default:
				break;
			}
		}

		if (raceNbr < racesActive.length) { // For later on when an input is created, don't worry not an NOP!

			if(type != Run_Types.PARGRP){
				racesActive[index] = new Race(channelSelected, channelSelected + 1);
			}else{
				racesActive[index] = new Race(channelSelected, channelSelected);
			}
			
			racesActive[index].setRaceNbr(raceNbr + 1);
			if (type == Run_Types.GRP)
				racesActive[index].isGroup = true;

			raceNbr++;
			index++;

			return true;
		}

		return false;

	}

	protected Racer CANCEL(int channel) {

		return getRaceFromChannel(channel).CANCEL();
	}

	protected Race[] getRaces() {
		return racesActive;
	}

	protected Race getRaceFromChannel(int channel) {

		if (racesActive != null) {

			for (int i = 0; i < racesActive.length; i++) {

				if (racesActive[i] != null
						&& (racesActive[i].onChannels[0] == channel || racesActive[i].onChannels[1] == channel)) {

					return racesActive[i];

				}
			}
		}

		return null;
	}

	protected boolean isThereRacersActive(int findIt) {

		return racesActive[findIt].isActive();

	}

	private void setRaceFromScratch(int eSize) {
		racesActive = new Race[eSize];
	}

	protected void resetIndex() {
		index = 0;
	}

	protected Race getARace(int raceNbr) {
		return racesActive[raceNbr];
	}

	protected boolean swap() {

		if (type == Run_Types.IND && racesActive != null && racesActive[0] != null && racesActive[0].active != null
				&& racesActive[0].active.size() > 1) {
			try {

				LinkedList<Racer> result = (LinkedList<Racer>) racesActive[0].active;

				result.add(1, result.remove(0));
				racesActive[0].active = result;
				return true;

			} catch (ClassCastException e) {

			}
		}
		return false;
	}
}