package entitiesDynamic;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Andy & Matt
 * 
 * The Pool class, apart of the entitiesDynamic package of the ChronoTimer1009.
 * The Pool is where competitors (racers) wait to start their event.
 */
public class Pool {

	private int racersAmount;
	private Deque<Racer> racers;

	private Pool(){

		racers = new LinkedList<>();
	}
	
	/**
	 * @author Andy
	 * 
	 * Pool as a singleton because of the threading.
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
	 * Adds a racer to the beginning of the pool (queue).
	 */
	public void addRacerBeginning(Racer racer){
		
		racers.addFirst(racer);
	}
	
	/**
	 * @param racer
	 * Adds a racer to the end of the pool (queue).
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
	 * See {@link #setRacersAmount(int)} to change the racersAmount field.
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
	 * Sets the number of racers to be in the pool then adds them via the {@link #makeRacers()} method.
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
	
	/**
	 * @return an array of int representing all the bibs in the pool (queue).
	 */
	public int[] getAllBibs(){
		
		int[] temp = new int[racers.size()];
		int count = 0;
		
		for(Racer r : racers){
			
			temp[count++] = r.getBib();
			
		}
		
		return temp;
		
	}
	
	/**
	 * @return true if the pool is cleared
	 */
	public boolean clearPool() {
		
		this.racers.clear();
		this.racersAmount = 0;
		return true;
	}
	
	/**
	 * @param bib -  the bib number of the racer to clear from the pool
	 * @return true if the racer was cleared
	 * 
	 * Clears the unuque racer with bib number equal to the parameter bib.
	 */
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
	
	/**
	 * {@link java.util.Deque#peek()}
	 * @return the Racer at the beginning of the pool.
	 */
	public Racer peek() {
		return racers.peek();
	}
	
	/**
	 * {@link java.util.Deque#peekLast()}
	 * @return the Racer at the end of the pool.
	 */
	public Racer peekLast(){
		return racers.peekLast();
	}
	
}