package environment;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import entitiesDynamic.Pool;
import entitiesDynamic.Racer;

public class Race {

	private int[] onChannels;
	private Queue<Racer> active;
	private RaceEventsManager manager;
	private int raceNbr;
	private int run;

	public Race(RaceEventsManager manager, int ... channels){

		this.onChannels = channels;
		this.manager = manager;
		this.active = new LinkedList<>();
		
	}


	public void stopLastRace(){
		
		manager.engrave(false, 0);
		
		while(!active.isEmpty()){

			finishRacer(false);

		}
	}


	public void startNRacers(int n){
		
		
		
		if(n <= manager.racersPoolSize()){

			for(int i = 0; i < n; i++){
				
				Racer racer = manager.getRacer();
				Channels.channels[onChannels[0] - 1].activate(racer.getBib());
				active.add(racer);

			}
		}
	}
	
	
	public void setRaceNbr(int raceNbr) {
		this.raceNbr = raceNbr;
	}
	
	public int getRaceNbr() {
		return raceNbr;
	}
	
	public void setRun(int run) {
		this.run = run;
	}
	
	public int getRun() {
		return run;
	}

	public Racer finishRacer(boolean DNF){
		
		manager.engrave(true, this.raceNbr);
		
		Racer racer = active.remove();
		
		if(DNF){
			
			racer.setDNF();
			
		}
		
		
		//		Random rand = new Random();       //You told me it was random, nothing in the guidelines suggests otherwise
		//		int randomNum = rand.nextInt((20 - 0) + 1) + 0;
		//		int randomNum2 = rand.nextInt((20 - 0) + 1) + 0;
		//
		//		if(randomNum == randomNum2){
		//
		//			racer.setDNF();
		//
		//		}

		Channels.channels[onChannels[0] - 1].TriggerSensor();
		Channels.channels[onChannels[1] - 1].activate(racer.getBib());
		manager.returnRacer(racer);
		
		return racer;
		//		racePool.returnRacer(racer);

	}


	public int racersActive(){

		return active.size();

	}

	public void CANCEL(){

		manager.returnRacerCancel(active.remove());

	}


	/**
	 * Gets the selected channels
	 * 
	 * @return the channel selected
	 */
	public int[] getChannelsActive() {
		return onChannels;
	}
	
	public boolean isActive() {
		return !active.isEmpty();
	}
}