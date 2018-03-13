package environment;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import entitiesDynamic.Pool;
import entitiesDynamic.Racer;
import environment.races.RaceEvent;

public abstract class Race {

	private int[] onChannels;
	private Queue<Racer> active = new LinkedList<>();;
	private RaceEventsManager manager;
	private Race[] races = { new RaceEvent(1, 2), new RaceEvent(3, 4) };          //Just in case the number of races gets out of wack!


	public Race(int...channels){
		
		this.onChannels = channels;
		
	}



	/**
	 * Set a specific channel to be the selected channel
	 * 
	 * @param channelSelected
	 * @return true if channel exists and will be selected, false if not!
	 */

	public boolean changeChannels(int...channels) {

		if(channels.length == 2){

			this.onChannels = channels;
			return true;

		}

		return false;
	}


	public void stopLastRace(){

		while(!active.isEmpty()){

			finishRacer();

		}
	}


	//	public void startNRacers(int n){
	//
	//		if(n <= racePool.racersAmount()){
	//
	//			for(int i = 0; i < n; i++){
	//
	//				Channels.channels[0].TriggerSensor();
	//				Racer racer = racePool.startRacer();
	//				Channels.channels[0].activate(racer.getBib());
	//				active.add(racer);
	//
	//			}
	//		}
	//	}


	public void finishRacer(){

		Racer racer = active.remove();
		Random rand = new Random();       //You told me it was random, nothing in the guidelines suggests otherwise
		int randomNum = rand.nextInt((20 - 0) + 1) + 0;
		int randomNum2 = rand.nextInt((20 - 0) + 1) + 0;

		if(randomNum == randomNum2){

			racer.setDNF();

		}

		Channels.channels[1].TriggerSensor();
		Channels.channels[1].activate(racer.getBib());
		//		racePool.returnRacer(racer);

	}


	public int racersActive(){

		return active.size();

	}

	public void CANCEL(){



	}


	/**
	 * Gets the selected channels
	 * 
	 * @return the channel selected
	 */
	public int[] getChannelsActive() {
		return onChannels;
	}
}