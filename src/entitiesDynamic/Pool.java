package entitiesDynamic;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Pool {

	private int racersAmount;
	private Deque<Racer> racers;

	/**
	 * 
	 */
	private Pool(){

		racers = new LinkedList<>();

	}
	
	private static class SingletonPool{
		
		private static final Pool pool = new Pool();
		
	}
	
	public static Pool getPool(){
		
		return SingletonPool.pool;
		
	}
	
	
	public int racersAmount(){
		
		return racers.size();
		
	}
	
	public void returnCancel(Racer racer){
		
		racers.addFirst(racer);
		
	}
	
	public void returnRacer(Racer racer){
		
		racers.add(racer);
		
	}
	
	/**
	 * 
	 * @return a racer
	 */
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
	
	public void makeRacer(int racerBib){
		
		racers.add(new Racer(racerBib));
		racersAmount++;
		
	}
	

	
	/**
	 * @param racersAmount
	 */
	public void setRacersAmount(int racersAmount){

		this.racersAmount = racersAmount;
		makeRacers();

	}
}