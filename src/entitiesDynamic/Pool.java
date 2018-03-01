package entitiesDynamic;

import java.util.LinkedList;
import java.util.Queue;

public class Pool {

	private int racersAmount;
	private Queue<Racer> racers;

	/**
	 * 
	 */
	public Pool(){

		racers = new LinkedList<>();

	}
	
	public int racersAmount(){
		
		return racers.size();
		
		
	}
	
	public void returnRacer(Racer racer){
		
		System.out.println(" Time: " + racer.getTimeFinishFormatted());
		racers.add(racer);
		
	}
	
	public Racer startRacer(){
		
		return racers.remove();
		
	}
		

	/**
	 * 
	 */
	private void makeRacers(){

		for(int i = 0; i < racersAmount; i++){

			racers.add(new Racer(i));

		}
	}

	/**
	 * @param racersAmount
	 */
	public void setRacersAmount(int racersAmount){


		this.racersAmount = racersAmount;
		makeRacers();

	}
}