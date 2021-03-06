package entitiesDynamic;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Andy & Matt
 * 
 * The Pool class, apart of the entitiesDynamic package of the ChronoTimer1009.
 * The Pool is where competitors (racers) wait to start their event.
 * Racers are added to the Pool after finishing a race with their recorded duration/time.
 */
public class Pool {

	private int racersAmount;
	private Deque<Racer> racers;

	/**
	 * Constructor for Pool
	 */
	private Pool(){

		racers = new LinkedList<>();
	}
	
	/**
	 * @author mattm
	 * Pool has to be a singleton because of the threading,
	 * however this may have to change with the addition of the lane class/changes to the Race class.
	 */
	private static class SingletonPool{
		
		private static final Pool pool = new Pool();
		
	}
	
	/**
	 * @return the singleton pool
	 */
	public static Pool getPool(){
		
		return SingletonPool.pool;
	}
	
	/**
	 * @param racer
	 * Adds a racer to the first position in the pool (queue).
	 */
	public void addRacerBeginning(Racer racer){
		
		racers.addFirst(racer);
	}
	
	/**
	 * @param racer
	 * Adds a racer to the last position in the pool (queue).
	 */
	public void addRacerLast(Racer racer){
		
		racers.add(racer);
	}
	
	/**
	 * Removes a racer from the beginning of the pool (queue).
	 * @return a racer
	 */
	public Racer removeRacerBeginning(){
		
		return racers.remove();
	}
	
	/**
	 * Creates and adds 'racersAmount' of racers to the pool.
	 * See setRacersAmount method to change the racersAmount field.
	 */
	private void makeRacers(){

		for(int i = 0; i < racersAmount; i++)
			racers.add(new Racer(i));
		
	}
	
	/**
	 * @param racerBib
	 * Creates a racer with bib number racerBib and adds them to the end of the pool (queue).
	 */
	public void makeRacer(int racerBib){
		
		racers.add(new Racer(racerBib));
		racersAmount++;
	}
	
	/**
	 * @param racersAmount
	 * Sets the number of racers to be in the pool then adds them via the makeRacers method.
	 */
	public void setRacersAmount(int racersAmount){

		this.racersAmount = racersAmount;
		makeRacers();
	}
	
	/**
	 * @return amount of racers in pool
	 */
	public int getRacersAmount(){
		
		return racers.size();
	}
	
	public int[] getAllBibs(){
		
		int[] temp = new int[racers.size()];
		int count = 0;
		
		for(Racer r : racers){
			
			temp[count++] = r.getBib();
			
		}
		
		return temp;
		
	}
	
	
	// new
	
	/**
	 * 
	 * @return true if the pool is cleared
	 */
	public boolean clearPool() {
		
		this.racers.clear();
		this.racersAmount = 0;
		return true;
	}
	
	public boolean clearRacer(int bib) {
		
		Iterator<Racer> it = racers.iterator();
		LinkedList<Racer> result = new LinkedList<Racer>();
		Racer tmp;
		boolean flag = false;
		
		while(it.hasNext()) {
			tmp = it.next();
			if(tmp.getBib() == bib) 
				flag = true;
			else
				result.add(tmp);
			
		}
		
		racers = result;
		return flag;
		
	}
	
	public Racer first() {
		return racers.peek();
	}
	
	public Racer last(){
		return racers.peekLast();
	}
	
	// new
}