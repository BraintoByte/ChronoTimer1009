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

import java.util.NoSuchElementException;

public class RaceEventsManagerTesting {

//	public void testStartNewRace() {
//		
//		RaceEventsManager r = new RaceEventsManager();
//		
//		assertFalse(r.startNewRace(1));				//start new race without racers
//		
//		r.makeRacers(1);							//create racers 1-3 in the pool
//		r.makeRacers(2);
//		r.makeRacers(3);
//		
//		assertFalse(r.startNewRace(1));				//start new race without channels or sensors set
//		
//		r.theseManySensors(2, 0, 0); 				//create 2 gate sensors
//		r.setChannelSelected(1);
//		r.CONN(false, true, false); 				//connect gate to channel 1
//		r.setChannelSelected(2);
//		r.CONN(false, true, false); 				//connect gate to channel 2
//		r.propRace();
//		
//		assertTrue(r.startNewRace(1));				//start new race with id 1
//		
//		assertTrue(r.startNewRace(2));				//start new race with id 2
//		
//		assertFalse(r.startNewRace(2));				//attempt to start new race with previous race number
//		
//		assertTrue(r.startNewRace(20));				//start new race with id 20
//		
//	}
//	
//	public void testReset(){
//		
//		RaceEventsManager r = new RaceEventsManager();
//		
//		r.theseManySensors(2, 0, 0); 							//create 2 gate sensors
//		r.setChannelSelected(1);
//		r.CONN(false, true, false); 							//connect gate to channel 1
//		r.setChannelSelected(2);
//		r.CONN(false, true, false); 							//connect gate to channel 2
//		r.propRace();
//		
//		r.setChannelSelected(1);
//		assertTrue(r.getCurrentChannel().isPairedToSensor());	//channel 1 should have a connected sensor
//		
//		r.setChannelSelected(2);
//		assertTrue(r.getCurrentChannel().isPairedToSensor());	//channel 2 should have a connected sensor
//		
//		r.makeRacers(1);
//		r.makeRacers(2);
//		r.makeRacers(3);										//make racers 1-3
//		
//		assertEquals(3, r.racersPoolSize()); 					//pool should have 3 racers
//		
//		r.startNewRace(1);										//start a new run, id 1
//		
//		r.resetRun();												//reset the race
//		
//		r.setChannelSelected(1);
//		assertFalse(r.getCurrentChannel().isPairedToSensor());	//channel 1 should have no sensor after reset
//		
//		r.setChannelSelected(2);
//		assertFalse(r.getCurrentChannel().isPairedToSensor());	//channel 2 should have no sensor after reset
//		
//		assertEquals(0, r.racersPoolSize());					//pool should be empty after reset
//		
//		r.startNewRace(1);										//should be able to start race with id 1 again?
//		
//		r.resetRun();												//reset the race
//		
//		r.setChannelSelected(1);
//		assertFalse(r.getCurrentChannel().isPairedToSensor());	//channel 1 should have no sensor after reset
//		
//		r.setChannelSelected(2);
//		assertFalse(r.getCurrentChannel().isPairedToSensor());	//channel 2 should have no sensor after reset
//		
//		assertEquals(0, r.racersPoolSize());					//pool should be empty after reset
//		
//	}
//	
//	public void testINDStress(){
//		
//		RaceEventsManager r = new RaceEventsManager();
//		
//		r.theseManySensors(2, 0, 0); 							//create 2 gate sensors
//		r.setChannelSelected(1);
//		r.CONN(false, true, false); 							//connect gate to channel 1
//		r.setChannelSelected(2);
//		r.CONN(false, true, false); 							//connect gate to channel 2
//		r.propRace();
//		r.setChannelSelected(1);
//		
//		for (int i = 1; i < 1000; i++) {
//			r.makeRacers(i);
//		}														//make racers 1-999
//		
//		assertEquals(999, r.racersPoolSize());					//pool should have 999 occupants
//		
//		r.startNewRace(1);
//		
//		Race[] lanes = r.getRaces();
//		
//		lanes[0].startNRacers(r.racersPoolSize()); 				//start all of the racers
//		
//		assertEquals(0, r.racersPoolSize());					//pool should now be 0
//		assertEquals(999, lanes[0].racersActive());				//999 racers active
//		
//		for (int i = 0; i < 100; i++) {
//			lanes[0].finishRacer(false);
//			lanes[0].finishRacer(false);
//			lanes[0].finishRacer(false);
//			lanes[0].finishRacer(false);
//			lanes[0].finishRacer(true);
//		}														//finish 500 racers, every fifth flagged DNF
//		
//		assertEquals(500, r.racersPoolSize());					//those 500 racers are returned to pool
//		assertEquals(499, lanes[0].racersActive());				//499 racers still active	
//		
//		lanes[0].stopLastRace(); 								//eek! Avalanche! The ski race has been cancelled!
//		
//		assertEquals(0, lanes[0].racersActive());				//no active racers, they are all scrambling for safety
//		
//		assertEquals(999, r.racersPoolSize());					//phew, all racers have returned to the loft and been accounted for
//		
//	}
//
//	public void testPARINDStress(){
//		
//RaceEventsManager r = new RaceEventsManager();
//		
//		r.theseManySensors(4, 0, 0); 							//create 2 gate sensors
//		r.setChannelSelected(1);
//		r.CONN(false, true, false); 							//connect gate to channel 1
//		r.setChannelSelected(2);
//		r.CONN(false, true, false); 							//connect gate to channel 2
//		r.setChannelSelected(3);
//		r.CONN(false, true, false); 							//connect gate to channel 3
//		r.setChannelSelected(4);
//		r.CONN(false, true, false); 							//connect gate to channel 4
//		r.propRace();
//		r.setChannelSelected(1);
//		
//		for (int i = 1; i < 1000; i++){
//			r.makeRacers(i);
//		}														//make racers 1-999
//		
//		assertEquals(999, r.racersPoolSize());					//pool should have 999 racers
//		
//		r.startNewRace(1);										//start new run
//		
//		r.startNewRace(2);										//start new run
//		
//		Race[] lanes = r.getRaces();							
//		
//		lanes[0].startNRacers(500);								//start half racers in first run
//		
//		lanes[1].startNRacers(499); 							//start other half racers in second run
//		
//		assertEquals(500, lanes[0].racersActive());				//500 racers active in first lane
//		assertEquals(499, lanes[1].racersActive());				//499 racers active in second lane
//		assertEquals(0, r.racersPoolSize());					//no racers in the pool
//		
//		for (int i = 0; i < 50; i++) {
//			lanes[0].finishRacer(false);
//			lanes[0].finishRacer(false);
//			lanes[0].finishRacer(false);
//			lanes[0].finishRacer(false);
//			lanes[0].finishRacer(true);
//			
//			lanes[1].finishRacer(false);
//			lanes[1].finishRacer(false);
//			lanes[1].finishRacer(false);
//			lanes[1].finishRacer(false);
//			lanes[1].finishRacer(true);
//		}														//finish 250 racers in each lane, DNF every fifth
//		
//		assertEquals(250, lanes[0].racersActive());				//250 racers yet to finish in lane 1
//		assertEquals(249, lanes[0].racersActive());				//249 racers yet to finish in lane 2
//		assertEquals(500, r.racersPoolSize());					//500 racers now in the pool
//		
//		lanes[1].stopLastRace(); 								//this run has been cancelled
//		assertEquals(0, lanes[1].racersActive());				//no racers racing anymore
//		assertEquals(749, r.racersPoolSize());					//749 racers in the pool now
//		
//		for (int i = 0; i < 50; i++){
//			lanes[0].finishRacer(false);
//			lanes[0].finishRacer(false);
//			lanes[0].finishRacer(false);
//			lanes[0].finishRacer(false);
//			lanes[0].finishRacer(true);
//		}														//the show must go on, and the other race path is fine
//																//finish the last 250 racers in lane 1, DNF every fifth
//		
//		assertEquals(0, lanes[0].racersActive());
//		assertEquals(0, lanes[1].racersActive());				//no active racers in each race
//		assertEquals(999, r.racersPoolSize());					//999 racers in the pool
//		
//	}
//	
//	public void testGRPStress(){
//		
//		RaceEventsManager r = new RaceEventsManager();
//		
//		r.theseManySensors(2, 0, 0); 							//create 2 gate sensors
//		r.setChannelSelected(1);
//		r.CONN(false, true, false); 							//connect gate to channel 1
//		r.setChannelSelected(2);
//		r.CONN(false, true, false); 							//connect gate to channel 2
//		r.propRace();
//		r.setChannelSelected(1);
//		
//		for (int i = 1; i < 1000; i++){
//			r.makeRacers(i);
//		}														//make racers 1-999
//		
//		r.startNewRace(1);										//start a new run
//		Race[] lanes = r.getRaces();
//		
//		lanes[0].startNRacers(999);								//begin all the racers
//		
//		assertEquals(0, r.racersPoolSize());					//no racers in the pool
//		assertEquals(999, lanes[0].racersActive());				//all 999 racers active
//		
//		for (int i = 0; i < 447; i++) {
//			lanes[0].finishRacer(false);
//		}														//first 447 finish legitimately 
//		
//		lanes[0].finishRacer(true);
//		lanes[0].finishRacer(true);								//are you kidding me? these two guys have been running the marathon with heelies on. Disqualified.
//		
//		for (int i = 0; i < 500; i++){
//			lanes[0].finishRacer(false);
//		}														//remaining racers finish legitimately
//		
//		assertEquals(999, r.racersPoolSize());					//999 racers in the pool
//		assertEquals(0, lanes[0].racersActive());				//no racers active
//		
//	}
	
}
