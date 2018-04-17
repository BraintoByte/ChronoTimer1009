package entitiesDynamic;

import static org.junit.Assert.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;

import org.junit.Test;
import entitiesStatic.Clock;
import environment.RaceEventsManager;
import states.State;
import java.util.NoSuchElementException;

public class poolTesting {

//	public void testSize() {
//		
//		Pool pool = new Pool();							//create a new, empty pool
//		assertTrue(pool.getRacersAmount() == 0);			//new pool should have 0 racers
//		
//		pool.makeRacer(1);
//		pool.makeRacer(2);								//add racers 1 and 2
//		assertTrue(pool.getRacersAmount() == 2);			//pool should have 2 racers
//		
//		Pool secondPool = pool.getPool();				//set second pool to be the first pool
//		assertTrue(secondPool.getRacersAmount() == 2);		//second pool should have 2 racers
//		
//		pool.makeRacer(3); 								//add racer 3 to first pool
//		assertTrue(pool.getRacersAmount() == 3);			//first pool should have 3 racers
//		assertTrue(secondPool.getRacersAmount() == 2);		//second pool should still have 2 racers.
//		
//		secondPool.removeRacerBeginning();						//second pool starts a racer, removing it from the pool
//		assertTrue(secondPool.getRacersAmount() == 1);		//second pool should have 1 racer
//		
//		secondPool.removeRacerBeginning();						//starts and removes final racer in second pool
//		secondPool.setRacersAmount(12); 				//sets second pool to 12 racers and makes those 12 racers
//		assertTrue(secondPool.getRacersAmount() == 12);	//second pool should have 12 racers
//		
//	}
//	
//	public void testAddingRacers() {
//		
//		Pool pool = new Pool();							//create a new, empty pool
//		assertTrue(pool.peekRacer() == null);			//peek racer should be null, as there are no racers in the pool
//		
//		pool.makeRacer(15); 							//add racer 15 to pool
//		assertTrue(pool.peekRacer().getBib() == 15);	//peek racer (and only racer) should have bib of 15
//		
//		pool.removeRacerBeginning();								//starts and therefore removes racer 15 from pool
//		pool.setRacersAmount(20);
//		assertTrue(pool.getRacersAmount() == 20);			// pool should have created 20 racers
//				
//	}
//	
//	public void testStartingRacers() {
//		
//		Pool pool = new Pool();							//create new, empty pool
//		//assertTrue(pool.startRacer() == null);		//pool should have no racer to start
//		
//		pool.setRacersAmount(10);						//pool should create and add 10 racers to it
//		assertEquals(10, pool.getRacersAmount());			//pool should have 10 racers in it
//
//		assertEquals(0, pool.removeRacerBeginning().getBib());	//first racer to start should have bib 0
//		assertEquals(9, pool.getRacersAmount());			//number of racers should now be 9
//		
//		assertEquals(1, pool.removeRacerBeginning().getBib());	//first racer to start should have bib 1
//		assertEquals(8, pool.getRacersAmount());			//number of racers should now be 9
//		
//		pool.removeRacerBeginning();
//		pool.removeRacerBeginning();
//		pool.removeRacerBeginning();
//		pool.removeRacerBeginning();
//		pool.removeRacerBeginning();
//		pool.removeRacerBeginning();								//start 3rd - 8th racers (bibs 2-7)
//		
//		assertEquals(2, pool.getRacersAmount());			//should be 2 racers in the pool
//		
//		pool.makeRacer(10); 							//add a new racer (bib 10) to the pool
//		assertEquals(3, pool.getRacersAmount());			//pool should now have 3 racers
//		
//		pool.setRacersAmount(5); 						//reset pool to 5 racers, bibs 0-5
//		assertEquals(5, pool.getRacersAmount());			//pool should now have 5 racers, replacing the old ones
//		
//		assertEquals(0, pool.removeRacerBeginning().getBib());	//first racer to start after re-setting the racers should have bib 0
//		assertEquals(1, pool.removeRacerBeginning().getBib());	//next racer to start should have bib 1
//		assertEquals(2, pool.removeRacerBeginning().getBib());	//next racer to start should have bib 2
//		assertEquals(3, pool.removeRacerBeginning().getBib());	//next racer to start should have bib 3
//		assertEquals(4, pool.removeRacerBeginning().getBib());	//next racer to start should have bib 4
//		
//		assertEquals(0, pool.getRacersAmount());			//all racers should have left pool by now
//		
//		try {
//			pool.removeRacerBeginning();
//		} catch (NoSuchElementException ex) {
//			//expected... how to test? do we test?
//		}
//		
//		assertEquals(0, pool.getRacersAmount());			//pool should still be empty
//		
//	}
//	
//	public void testReturns(){
//		
//		Racer r1 = new Racer(11);
//		Racer r2 = new Racer(22);
//		Racer r3 = new Racer(33);						//create racers 11, 22, 33
//		
//		Pool pool = new Pool();							//create new, empty pool
//		assertEquals(0, pool.getRacersAmount());			//pool should be empty
//		
//		pool.addRacerLast(r2);							//return racer 22 to the pool
//		assertTrue(pool.peekRacer().equals(r2));		//only racer in pool should be racer 22
//		assertEquals(1, pool.getRacersAmount());			//pool should have 1 racer
//		
//		pool.addRacerLast(r3); 							//return racer 33 to the pool
//		assertTrue(pool.peekRacer().equals(r2));		//racer 22 should still be at the head of the pool
//		assertEquals(2, pool.getRacersAmount());			//pool should have 2 occupants
//		
//		pool.removeRacerBeginning();								//start head racer, racer 22, removing from pool
//		assertTrue(pool.peekRacer().equals(r3));		//racer 22 should now be at the head of the pool
//		assertEquals(1, pool.getRacersAmount());			//pool should now have 1 occupant
//		
//		pool.addRacerBeginning(r1); 							//returnCancel racer 11 to pool
//		assertTrue(pool.peekRacer().equals(r1));		//racer 11 should be head of the pool, as per returnCancel method
//		assertEquals(2, pool.getRacersAmount());			//pool should now have 2 occupants
//		
//		pool.addRacerLast(r2);							//return racer 22 to the pool
//		assertTrue(pool.peekRacer().equals(r1));		//racer 11 should be head of the pool still
//		assertEquals(3, pool.getRacersAmount());			//pool should now have 3 occupants
//		
//		assertTrue(pool.removeRacerBeginning().equals(r1));		//first racer in pool should be racer 11
//		assertTrue(pool.removeRacerBeginning().equals(r3));		//next racer in pool should be racer 33
//		assertTrue(pool.removeRacerBeginning().equals(r2));		//next(final) racer in pool should be racer 22
//		
//		assertEquals(0, pool.getRacersAmount());			//pool should be empty
//		
//	}
//	
//	public void testSizeWithREM(){
//		
//		RaceEventsManager r = new RaceEventsManager();			//create new race event manager
//		r.theseManySensors(2, 0, 0); 							//create 2 gate sensors
//		r.setChannelSelected(1);
//		r.CONN(false, true, false); 							//connect gate to channel 1
//		r.setChannelSelected(2);
//		r.CONN(false, true, false); 							//connect gate to channel 2
//		r.propRace();
//		
//		assertEquals(0, r.racersPoolSize());					//new pool should have 0 racers
//		
//		r.makeRacers(1);										
//		r.makeRacers(2);										//add racers 1 and 2
//		assertEquals(2, r.racersPoolSize());					//pool should have 2 racers
//		
//		r.makeRacers(3);										//add racer 3
//		assertEquals(3, r.racersPoolSize());					//pool should have 3 racers
//		
//		r.startNewRace(1);										//begin a new run
//		Race[] lanes = r.getRaces();
//		
//		lanes[0].startNRacers(1);								//start a racer(removing from pool)
//		assertEquals(2, r.racersPoolSize());					//pool should have 2 racers
//		
//		r.getPool().setRacersAmount(5); 						//set pool to have 5 racers
//		assertEquals(5, r.racersPoolSize());					//pool should have 5 racers
//		
//	}
//	
//	public void testAddingRacersWithREM(){
//		
//		RaceEventsManager r = new RaceEventsManager();			//create new race event manager
//		r.theseManySensors(2, 0, 0); 							//create 2 gate sensors
//		r.setChannelSelected(1);
//		r.CONN(false, true, false); 							//connect gate to channel 1
//		r.setChannelSelected(2);
//		r.CONN(false, true, false); 							//connect gate to channel 2
//		r.propRace();
//		
//		assertTrue(r.getPool().peekRacer() == null);			//peek racer should be null, no racers in pool
//		
//		r.makeRacers(15);										//add racer 15 to pool
//		assertEquals(15, r.getPool().peekRacer().getBib());		//peek racer (and only racer) should have bib 15
//		
//		r.getPool().setRacersAmount(20);						//set pool to have 20 racers
//		assertEquals(20, r.racersPoolSize());					//pool should have 20 racers
//		
//	}
//	
//	public void testStartingRacersWithREM(){
//		
//		RaceEventsManager r = new RaceEventsManager();			//create new race event manager
//		r.theseManySensors(2, 0, 0); 							//create 2 gate sensors
//		r.setChannelSelected(1);
//		r.CONN(false, true, false); 							//connect gate to channel 1
//		r.setChannelSelected(2);
//		r.CONN(false, true, false); 							//connect gate to channel 2
//		r.propRace();
//		
//		r.getPool().setRacersAmount(10); 						//set racers to 10
//		assertEquals(10, r.racersPoolSize());					//pool should have 10
//		
//		r.startNewRace(1);										//start a new run
//		Race[] lanes = r.getRaces();
//		
//		lanes[0].startNRacers(1);								//start 1 racer
//		assertEquals(9, r.racersPoolSize());					//pool size should be 9 now
//		
//		lanes[0].startNRacers(1);								//start 1 racer
//		assertEquals(8, r.racersPoolSize());					//pool size should be 8 now
//		
//		lanes[0].startNRacers(1);
//		lanes[0].startNRacers(1);
//		lanes[0].startNRacers(1);
//		lanes[0].startNRacers(1);
//		lanes[0].startNRacers(1);
//		lanes[0].startNRacers(1); 								//start racers 3-8 (bibs 2-7)
//		
//		assertEquals(2, r.racersPoolSize());					//should be 2 racers in the pool
//		
//		r.makeRacers(10); 									//add new racer (bib 10) to the pool
//		assertEquals(3, r.racersPoolSize());					//pool should now have 3 racers
//		
//		r.getPool().setRacersAmount(5); 						//reset pool to 5 racers, bibs 0-5
//		assertEquals(5, r.racersPoolSize());					//pool should now have 5 racers, replacing old ones
//		
//		lanes[0].startNRacers(5); 								//start all 5 racers in pool
//		
//		assertEquals(0, r.racersPoolSize());					//all racers should have left pool by now
//		
//		try {
//			lanes[0].startNRacers(0);
//		} catch (NoSuchElementException ex) {
//			//expected... how to test? do we test?
//		}
//		
//		assertEquals(0, r.racersPoolSize());					//pool should still be empty
//		
//	}
//	
//	public void testReturnsWithREM(){
//		
//		RaceEventsManager r = new RaceEventsManager();			//create new race event manager
//		r.theseManySensors(2, 0, 0); 							//create 2 gate sensors
//		r.setChannelSelected(1);
//		r.CONN(false, true, false); 							//connect gate to channel 1
//		r.setChannelSelected(2);
//		r.CONN(false, true, false); 							//connect gate to channel 2
//		r.propRace();
//		
//		Racer r1 = new Racer(11);
//		Racer r2 = new Racer(22);
//		Racer r3 = new Racer(33);						//create racers 11, 22, 33
//		
//		assertEquals(0, r.racersPoolSize());			//pool should be empty
//		
//		r.startNewRace(1);								//start new run
//		Race[] lanes = r.getRaces();
//		
//		r.getPool().returnRacer(r2);					//return racer 22 to the pool
//		assertTrue(r.getPool().peekRacer().equals(r2));	//only racer in pool should be racer 22
//		assertEquals(1, r.racersPoolSize());			//pool should have 1 racer
//		
//		r.getPool().returnRacer(r3); 					//return racer 33 to the pool
//		assertTrue(r.getPool().peekRacer().equals(r2));	//racer 22 should still be at the head of the pool
//		assertEquals(2, r.getPool().racersAmount());	//pool should have 2 occupants
//		
//		lanes[0].startNRacers(1);						//start head racer, racer 22, removing from pool
//		assertTrue(r.getPool().peekRacer().equals(r3));	//racer 22 should now be at the head of the pool
//		assertEquals(1, r.getPool().racersAmount());	//pool should now have 1 occupant
//		
//		lanes[0].CANCEL();  							//returnCancel racer 11 to pool
//		assertTrue(r.getPool().peekRacer().equals(r1));	//racer 11 should be head of the pool, as per returnCancel method
//		assertEquals(2, r.getPool().racersAmount());	//pool should now have 2 occupants
//		
//		r.getPool().returnRacer(r2);					//return racer 22 to the pool
//		assertTrue(r.getPool().peekRacer().equals(r1));	//racer 11 should be head of the pool still
//		assertEquals(3, r.getPool().racersAmount());	//pool should now have 3 occupants
//		
//		assertTrue(r.getPool().startRacer().equals(r1));//first racer in pool should be racer 11
//		assertTrue(r.getPool().startRacer().equals(r3));//next racer in pool should be racer 33
//		assertTrue(r.getPool().startRacer().equals(r2));//next(final) racer in pool should be racer 22
//		
//		assertEquals(0, r.racersPoolSize());			//pool should be empty
//		
//	}
	
}
