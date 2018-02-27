package entitiesDynamic;

import java.util.LinkedList;
import java.util.Queue;

public class Pool {

	private int racersLimit;
	private int racersAmount;
	private Queue<Racer> racers;

	/**
	 * 
	 */
	public Pool(){

		racers = new LinkedList<>();
		racersLimit = 0;

	}

	/**
	 * 
	 */
	private void init(){




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
	 * @param racersLimit
	 */
	public void setRacersLimit(int racersLimit){

		this.racersLimit = racersLimit;

	}

	/**
	 * @param racersAmount
	 */
	public void setRacersAmount(int racersAmount){

		if(racersLimit != 0){

			if(racersAmount <= racersLimit){

				this.racersAmount = racersAmount;
				makeRacers();

			}
		}
	}
}
