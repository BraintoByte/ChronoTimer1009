package unitTesting;

import static org.junit.Assert.*;
import org.junit.Test;
import entitiesDynamic.Racer;
import environment.RaceEventsManager;

public class TestRaceFunctions {
	
	
	//NEEDS TO BE REDONE!
	
//	private RaceEventsManager raceManager = new RaceEventsManager();
//	
//	@Test
//	public void TestNEWRUN(){
//
//		raceManager.setChannelSelected(1);											// select channel 1
//
//		raceManager.propRace();														// setup for run (EVENT IND)
//		assertTrue(raceManager.startNewRace(1));									// create a new run (Race #1)
//		assertEquals(raceManager.racersPoolSize(), 0);								// pool is empty
//
//		raceManager.makeOneRacer(1);												// add a racer to the pool
//
//		assertEquals(raceManager.racersPoolSize(), 1);								// pool has one in it
//
//		assertFalse(raceManager.startNewRace(2));									// should not be able to make new run (run #2)
//
//		raceManager.getRaces()[0].startNRacers(1);									// start the one racer
//
//		assertEquals(raceManager.racersPoolSize(), 0);								// pool should be empty
//		assertEquals(raceManager.getRaces()[0].racersActive(), 1);					// should be one racer active
//
//		raceManager.getRaces()[0].finishRacer(false);								// finish racer without a DNF
//
//		assertEquals(raceManager.getRaces()[0].racersActive(), 0);					// active should be empty
//
//		endrun();												// end current run (Race #1)
//
//		raceManager.propRace();														// set up for race #2 (EVENT IND)
//		assertTrue(raceManager.startNewRace(2));									// create race #2
//
//		endrun();												// end current run (Race #2)
//	}
//
//	@Test
//	public void TestCANCEL() {
//
//		raceManager.setChannelSelected(1);											// select channel 1
//		raceManager.propRace();													// setup for run (EVENT IND)
//		raceManager.startNewRace(3);												// create a new run (Race #3)
//		
//		raceManager.makeOneRacer(1);
//		
//		raceManager.getRaces()[0].startNRacers(1);									// only racer "001" starts
//		assertEquals(raceManager.findRacerChannel(), 1);					// check active size
//		raceManager.getRaces()[0].stopLastRace();									// racer "001" finishes
//		assertEquals(raceManager.getRaces()[0].racersActive(), 0);					// check active size
//
//		Racer racer = raceManager.							// gets next racer to start
//		raceManager.getPool().returnCancel(racer);									// adds racer back into pool as the next racer
//		raceManager.getRaces()[0].startNRacers(1);									// only racer "001" starts
//
//		raceManager.getRaces()[0].CANCEL();											// racer "001" is canceled
//
//		assertEquals(racer, raceManager.getPool().startRacer());					// checks if racer "001" is in pool as next racer to start
//		raceManager.getPool().returnCancel(racer);
//		assertEquals(raceManager.getPool().racersAmount(), 1);						// checks pool size
//		
//		endrun();												// end current run (Race #3)
//	}
//	
//	@Test
//	public void TestDNF() {
//		
//		raceManager.setChannelSelected(1);											// select channel 1
//
//		raceManager.propRace();													// setup for run (EVENT IND)
//		assertTrue(raceManager.startNewRace(4));									// create a new run (Race #4)
//		assertEquals(raceManager.racersPoolSize(), 0);								// pool is empty
//
//		raceManager.makeOneRacer(1);												// add a racer to the pool
//
//		assertEquals(raceManager.racersPoolSize(), 1);								// pool has one in it
//
//		assertFalse(raceManager.startNewRace(5));									// should not be able to make new run (run #5)
//
//		assertFalse(raceManager.getPool().peekRacer().isDNF());						// check if racer DNF
//		
//		raceManager.getRaces()[0].startNRacers(1);									// start the one racer
//
//		assertEquals(raceManager.racersPoolSize(), 0);								// pool should be empty
//		assertEquals(raceManager.getRaces()[0].racersActive(), 1);					// should be one racer active
//
//		raceManager.getRaces()[0].finishRacer(true);								// finish racer with a DNF
//
//		assertEquals(raceManager.getRaces()[0].racersActive(), 0);					// active should be empty
//
//		assertTrue(raceManager.getPool().peekRacer().isDNF());						// check if racer DNF
//		
//		endrun();											// end current run (Race #4)
//		
//	}
//	
//	private void endrun(){
//		
//		if(raceManager.getRaces() != null){
//
//			for(int i = 0; i < raceManager.getRaces().length; i++){
//
//				raceManager.getRaces()[i].stopLastRace();
//
//			}
//		}
//	}
}