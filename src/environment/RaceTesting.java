package environment;

import static org.junit.Assert.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import entitiesDynamic.Pool;
import entitiesDynamic.Racer;
import org.junit.Test;
import entitiesStatic.Clock;
import states.State;
import exceptions.NoSuchRacerException;

public class RaceTesting {

//	public void testHodgePodge (){
//		
//		RaceEventsManager r = new RaceEventsManager();			//create new race event manager
//		r.theseManySensors(2, 0, 0); 							//create 2 gate sensors
//		r.setChannelSelected(1);
//		r.CONN(false, true, false); 							//connect gate to channel 1
//		r.setChannelSelected(2);
//		r.CONN(false, true, false); 							//connect gate to channel 2
//		r.propRace();
//		r.getPool().setRacersAmount(5); 						//add 5 racers to the pool, bibs 0-4
//		r.setChannelSelected(1);
//		r.startNewRace(2);										//initialize 8 races(lanes); run number should be 2
//		Race[] races = r.getRaces();
//		assertTrue(races[0].getRun() == 2);						//run number should be 2
//		
//		races[0].setRaceNbr(1); 								//set race(lane) 1
//		assertTrue(races[0].getRaceNbr() == 1);					//race(lane) number should be 1
//
//		races[0].setRun(7); 									//set run number to 7
//		assertTrue(races[0].getRun() == 7);						//run number should be 7
//		
//		assertFalse(races[0].isActive());						//race(lane) 1 should not be active, 0 racers currently racing
//		races[0].startNRacers(5); 								//start the 5 racers on race(lane) 1
//		assertTrue(races[0].isActive());						//race(lane) 1 should be active, 5 racers currently racing
//		
//		assertTrue(races[0].finishRacer(false).getBib() == 0);	//first racer to finish should have bib 0
//		races[0].startNRacers(1); 								//start 1 racer when there are none in the pool; should do nothing
//		
//		races[0].startNRacers(0); 								//start 0 racers; should do nothing
//		
//		races[0].startNRacers(-1); 								//start negative racers; should do nothing
//		
//		assertTrue(races[0].isActive());						//race(lane) 1 should be active, 4 racers currently racing
//		
//		races[0].finishRacer(true);								//second racer (bib 1) finishes with DNF
//		
//		races[0].stopLastRace(); 								//should stop the run
//		
//		assertFalse(races[0].isActive());						//race(lane) 1 should be inactive
//		
//	}
//	
//	public void testStartingRacers(){
//		
//		RaceEventsManager r = new RaceEventsManager();			//create the race event manager
//		r.theseManySensors(2, 0, 0);
//		r.setChannelSelected(1);
//		r.CONN(false, true, false);
//		r.setChannelSelected(2);
//		r.CONN(false, true, false); 							//create and connect gate sensors to channels 1 and 2
//		
//		r.propRace();
//		assertFalse(r.startNewRace(1)); 						//cannot start a new run, pool is empty.
//		
//		r.getPool().setRacersAmount(5); 						//create 5 racers in the pool, bibs 0-4
//		assertTrue(r.startNewRace(1));							//can now start a new run, ID 1
//		assertEquals(5, r.racersPoolSize());					//pool should contain 5 racers
//		
//		
//		Race[] lanes = r.getRaces();							//set lanes to be the races(lanes) of the current run
//		assertEquals(0, lanes[0].racersActive());				//no racers have begun racing, # active racers should be 0
//		
//		lanes[0].startNRacers(10); 								//attempting to start 10 racers, more than in the pool
//		assertEquals(5, r.racersPoolSize());
//		assertEquals(0, lanes[0].racersActive());				//no racers have begun racing, # active racers should be 0
//		
//		lanes[0].startNRacers(0); 								//attempting to start 0 racers, should do nothing
//		assertEquals(5, r.racersPoolSize());					//pool size should still be 5, as no racer was started
//		assertEquals(0, lanes[0].racersActive());				//no racers have begun racing, # active racers should be 0
//		
//		lanes[0].startNRacers(-3); 								//attempting to start negative racers, should do nothing
//		assertEquals(5, r.racersPoolSize());					//pool size should still be 5, as no racer was started
//		assertEquals(0, lanes[0].racersActive());				//no racers have begun racing, # active racers should be 0
//		
//		//Begin to actually start racers.
//		
//		lanes[0].startNRacers(1);
//		assertEquals(4, r.racersPoolSize());					//pool size should be 4; a racer just started racing
//		assertEquals(1, lanes[0].racersActive());				//racer begins racing, active racers should be 1
//		
//		lanes[0].startNRacers(1);
//		assertEquals(3, r.racersPoolSize());					//pool size should be 3; a racer just started racing
//		assertEquals(2, lanes[0].racersActive());				//racer begins racing, active racers should be 2
//		
//		lanes[0].startNRacers(1);
//		assertEquals(2, r.racersPoolSize());					//pool size should be 2; a racer just started racing
//		assertEquals(3, lanes[0].racersActive());				//racer begins racing, active racers should be 3
//		
//		lanes[0].startNRacers(1);
//		assertEquals(1, r.racersPoolSize());					//pool size should be 1; a racer just started racing
//		assertEquals(4, lanes[0].racersActive());				//racer begins racing, active racers should be 4
//		
//		lanes[0].startNRacers(1);
//		assertEquals(0, r.racersPoolSize());					//pool size should be 0; a racer just started racing
//		assertEquals(5, lanes[0].racersActive());				//racer begins racing, active racers should be 5
//		
//		//End actually starting Racers
//		
//		lanes[0].startNRacers(1);		 						//should do nothing; no racers left in pool
//		assertEquals(0, r.racersPoolSize());					//pool size should still be 0
//		assertEquals(5, lanes[0].racersActive());				//all racers active, active racers should be 5
//		
//		lanes[0].startNRacers(-1); 								//should do nothing; no racers left and negative parameter
//		assertEquals(0, r.racersPoolSize());					//pool size should still be 0
//		assertEquals(5, lanes[0].racersActive());				//all racers active, active racers should be 5
//		
//	}
//	
//	public void testFinishingRacers(){
//		
//		RaceEventsManager r = new RaceEventsManager();			//create the race event manager
//		r.theseManySensors(2, 0, 0);
//		r.setChannelSelected(1);
//		r.CONN(false, true, false);
//		r.setChannelSelected(2);
//		r.CONN(false, true, false); 							//create and connect gate sensors to channels 1 and 2
//		
//		r.propRace();
//		
//		r.getPool().setRacersAmount(5); 						//create 5 racers in the pool, bibs 0-4
//		r.startNewRace(1);										//can now start a new run, ID 1
//		
//		Race[] lanes = r.getRaces();							//set lanes to be the races(lanes) of the current run
//		
//		lanes[0].startNRacers(5);								//start all 5 racers
//		
//		assertEquals(0, r.racersPoolSize());					//all 5 racers have started, should be 0 in pool
//		assertEquals(5, lanes[0].racersActive());				//all 5 racers have started should be 5 active racers
//		
//		//Begin finishing racers
//		
//		Racer r1 = lanes[0].finishRacer(false);
//		assertEquals(1, r.racersPoolSize());					//first racer has finished, should be returned to pool
//		assertFalse(r1.isDNF());								//r1 did not flag DNF
//		assertEquals(0, r1.getBib());							//r1 was 1st racer to finish, 1st racer should have bib 0
//		assertEquals(4, lanes[0].racersActive());				//1 of 5 racers finished, active racers should be 4
//		assertTrue(r1.equals(r.getPool().peekRacer()));			//r1 should be at the head of the pool
//		
//		Racer r2 = lanes[0].finishRacer(false);
//		assertEquals(2, r.racersPoolSize());					//racer has finished, should be returned to pool; 2 total
//		assertFalse(r2.isDNF());								//r2 did not flag DNF
//		assertEquals(1, r2.getBib());							//r2 was 2nd racer to finish, 2nd racer should have bib 1
//		assertEquals(3, lanes[0].racersActive());				//2 of 5 racers finished, active racers should be 3
//		assertTrue(r2.equals(r.getPool().peekRacer()));			//r2 should be at the head of the pool
//		
//		Racer r3 = lanes[0].finishRacer(false);
//		assertEquals(3, r.racersPoolSize());					//racer has finished, should be returned to pool; 3 total
//		assertFalse(r3.isDNF());								//r3 did not flag DNF
//		assertEquals(2, r3.getBib());							//r3 was 3rd racer to finish, 3rd racer should have bib 2
//		assertEquals(2, lanes[0].racersActive());				//3 of 5 racers finished, active racers should be 2
//		assertTrue(r3.equals(r.getPool().peekRacer()));			//r3 should be at the head of the pool
//		
//		Racer r4 = lanes[0].finishRacer(false);					//racer r1 is the 4th to finish, legally. 
//		assertEquals(4, r.racersPoolSize());					//racer has finished, should be returned to pool; 4 total
//		assertFalse(r4.isDNF());								//r4 did not flag DNF
//		assertEquals(3, r4.getBib());							//r4 was 4th racer to finish, 4th racer should have bib 3
//		assertEquals(1, lanes[0].racersActive());				//4 of 5 racers finished, active racers should be 1
//		assertTrue(r4.equals(r.getPool().peekRacer()));			//r4 should be at the head of the pool
//		
//		Racer r5 = lanes[0].finishRacer(true);					//racer r2 is the 5th(last) to finish, and does so illegally
//		assertEquals(5, r.racersPoolSize());					//racer has finished, should be returned to pool; 5 total
//		assertTrue(r5.isDNF());									//r5 did flag DNF
//		assertEquals(4, r5.getBib());							//r5 was 5th racer to finish, 5th racer should have bib 4
//		assertEquals(0, lanes[0].racersActive());				//5 of 5 racers finished, active racers should be 0
//		assertTrue(r5.equals(r.getPool().peekRacer()));			//r5 should be at the head of the pool
//		
//		//End finished racers
//		
//		lanes[0].finishRacer(true);
//		lanes[0].finishRacer(false);							//try finishing racers when none are active, should do nothing
//		assertEquals(5, r.racersPoolSize());					//pool size should remain 5
//		assertEquals(0, lanes[0].racersActive());				//all racers still finished, active racers should be 0
//		
//		r1.setDNF(); 											//oh no! r1 was wearing illegal attire. disqualified.
//		assertTrue(r1.isDNF());									//yep, he's done-zo
//		r1.setDNF(); 											//hmm, another judge noticed too and also logged the DQ
//		assertTrue(r1.isDNF());									//yep, he's double done-zo
//		
//	}
//	
//	public void testRaceConclusion() {
//		
//		RaceEventsManager r = new RaceEventsManager();			//create the race event manager
//		r.theseManySensors(2, 0, 0);
//		r.setChannelSelected(1);
//		r.CONN(false, true, false);
//		r.setChannelSelected(2);
//		r.CONN(false, true, false); 							//create and connect gate sensors to channels 1 and 2
//		
//		r.propRace();
//		
//		r.getPool().setRacersAmount(10);						//create and add 10 racers to the pool, bibs 0-9
//		r.startNewRace(1);										//start a new run with ID 1
//		
//		Race[] lanes = r.getRaces();							
//		lanes[0].startNRacers(10);								//start all the racers
//		assertEquals(0, r.racersPoolSize());					//all racers have begun racing, pool should be empty
//		assertEquals(10, lanes[0].racersActive());				//all racers have begun racing, active racers should be 10
//		
//		lanes[0].stopLastRace();								//stop all racers in the lane
//		assertEquals(10, r.racersPoolSize());					//all racers have ceased racing; pool should be 10
//		assertEquals(0, lanes[0].racersActive());				//all racers have ceased racing; active racers should be 0
//		
//		//RESET//
//		r.reset();												//reset the raceEventsManager and everything within
//		//RESET//
//		
//		r.theseManySensors(2, 0, 0);
//		r.setChannelSelected(1);
//		r.CONN(false, true, false);
//		r.setChannelSelected(2);
//		r.CONN(false, true, false); 							//create and connect gate sensors to channels 1 and 2
//		
//		r.propRace();
//		r.getPool().setRacersAmount(10);						//create and add 10 racers to the pool, bibs 0-9
//		r.startNewRace(2);										//start a new run with ID 2
//		
//		lanes = r.getRaces();									//set lanes to be the races(lanes) of the current run
//		
//		lanes[0].startNRacers(3); 								//start 3 of 10 racers
//		assertEquals(3, lanes[0].racersActive());				//3 racers should be active
//		assertEquals(7, r.racersPoolSize());					//7 racers should be in the pool
//		
//		lanes[0].stopLastRace(); 								//stop all racers in the lane
//		assertEquals(10, r.racersPoolSize());					//all racers have ceased racing; pool should be 10
//		assertEquals(0, lanes[0].racersActive());				//all racers have ceased racing; active racers should be 0
//		
//		//RESET//
//		r.reset();												//reset the raceEventsManager and everything within
//		//RESET//
//		
//		r.theseManySensors(2, 0, 0);
//		r.setChannelSelected(1);
//		r.CONN(false, true, false);
//		r.setChannelSelected(2);
//		r.CONN(false, true, false); 							//create and connect gate sensors to channels 1 and 2
//		
//		r.propRace();
//		r.getPool().setRacersAmount(10);						//create and add 10 racers to the pool, bibs 0-9
//		r.startNewRace(3);										//start a new run with ID 3
//		
//		lanes = r.getRaces();									//set lanes to be the races(lanes) of the current run
//		
//		assertEquals(0, lanes[0].racersActive());				//no racers have begun, active racers should be 0
//		assertEquals(10, r.racersPoolSize());					//no racers have begun, pool size should be 10
//		
//		lanes[0].stopLastRace(); 								//stop all racers in the lane
//		
//		assertEquals(0, lanes[0].racersActive());				//no racers had begun, active racers should still be 0
//		assertEquals(10, r.racersPoolSize());					//no racers had begun, pool size should still be 10
//		
//	}
	
}
