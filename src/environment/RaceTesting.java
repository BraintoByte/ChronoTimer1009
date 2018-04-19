package environment;

import static org.junit.Assert.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import entitiesDynamic.Pool;
import entitiesDynamic.Racer;
import environment.Run;
import states.hardware.Idle.Run_Types;

import org.junit.Test;
import entitiesStatic.Clock;
import states.State;
import exceptions.NoSuchRacerException;

public class RaceTesting {
	@Test
	public void testHodgePodge (){
		
		RaceEventsManager r = new RaceEventsManager();			//create new race event manager
		r.theseManySensors(2, 0, 0); 							//create 2 gate sensors
		r.setChannelSelected(1);
		r.CONN(false, true, false); 							//connect gate to channel 1
		r.setChannelSelected(2);
		r.CONN(false, true, false); 							//connect gate to channel 2
		r.getPool().setRacersAmount(5); 						//add 5 racers to the pool, bibs 0-4
		r.setChannelSelected(1);

		r.setType(Run_Types.IND);
		r.setRunNbr(1);
		r.setNewRun();											//initialize 8 races(lanes); run number should be 2
		Run run = r.getCurrentRun();
		run.setNewRace(0);
		
		assertEquals(2, r.getRunNbr());							//run number should be 2
		
		r.setRunNbr(1); 										//set race(lane) 1
		assertEquals(1, r.getRunNbr());							//race(lane) number should be 1

		r.setRunNbr(7); 										//set run number to 7
		assertEquals(7, r.getRunNbr());							//run number should be 7
		
		assertTrue(r.isRunActive());							//race(lane) 1 should not be active, 0 racers currently racing
		run.getARace(0).startNRacers(5, r.getPool());			//start the 5 racers on race(lane) 1
		assertTrue(r.isRunActive());							//race(lane) 1 should be active, 5 racers currently racing
		
		assertEquals(0, run.getARace(0).finishRacer(false, r.getPool()).getBib());//first racer to finish should have bib 0
		
		run.getARace(0).startNRacers(1, r.getPool()); 			//start 1 racer when there are none in the pool; should do nothing
		
		run.getARace(0).startNRacers(0, r.getPool());			//start 0 racers; should do nothing
		
		run.getARace(0).startNRacers(-1, r.getPool());			//start negative racers; should do nothing
		
		assertTrue(r.isRunActive());							//race(lane) 1 should be active, 4 racers currently racing
		
		run.getARace(0).finishRacer(true, r.getPool());			//second racer (bib 1) finishes with DNF
		
		
		run.getARace(0).stopLastRace(r.getPool());				//should stop the run
		
		assertTrue(r.isRunActive());							//race(lane) 1 should be inactive
		r.endRun();
		assertFalse(r.isRunActive());
		
	}
	@Test
	public void testStartingRacers(){
		
		RaceEventsManager r = new RaceEventsManager();			//create the race event manager
		r.theseManySensors(2, 0, 0);
		r.setChannelSelected(1);
		r.CONN(false, true, false);
		r.setChannelSelected(2);
		r.CONN(false, true, false); 							//create and connect gate sensors to channels 1 and 2
		r.resetPool();
		
		r.getPool().setRacersAmount(5); 						//create 5 racers in the pool, bibs 0-4
		r.setType(Run_Types.IND);
		r.setRunNbr(1);
		r.setNewRun();
		assertTrue(r.isRunActive());							//can now start a new run, ID 1
		assertEquals(5, r.getPool().getRacersAmount());			//pool should contain 5 racer	
		
		
		Run run = r.getCurrentRun();							//set lanes to be the races(lanes) of the current run
		run.setNewRace(1);
		assertEquals(0, run.getARace(0).racersActive());		//no racers have begun racing, # active racers should be 0
		
		r.getCurrentRun().getARace(0).startNRacers(10, r.getPool());	//attempting to start 10 racers, more than in the pool
		assertEquals(5, r.getPool().getRacersAmount());
		assertEquals(0, run.getARace(0).racersActive());				//no racers have begun racing, # active racers should be 0
		
		r.getCurrentRun().getARace(0).startNRacers(0, r.getPool());	//attempting to start 0 racers, should do nothing
		assertEquals(5, r.getPool().getRacersAmount());			//pool size should still be 5, as no racer was started
		assertEquals(0, run.getARace(0).racersActive());				//no racers have begun racing, # active racers should be 0
		
		r.getCurrentRun().getARace(0).startNRacers(-3, r.getPool());	//attempting to start negative racers, should do nothing
		assertEquals(5, r.getPool().getRacersAmount());			//pool size should still be 5, as no racer was started
		assertEquals(0, run.getARace(0).racersActive());				//no racers have begun racing, # active racers should be 0
		
		//Begin to actually start racers.
		
		r.getCurrentRun().getARace(0).startNRacers(1, r.getPool());
		assertEquals(4, r.getPool().getRacersAmount());			//pool size should be 4; a racer just started racing
		assertEquals(1, run.getARace(0).racersActive());				//racer begins racing, active racers should be 1
		
		r.getCurrentRun().getARace(0).startNRacers(1, r.getPool());
		assertEquals(3, r.getPool().getRacersAmount());			//pool size should be 3; a racer just started racing
		assertEquals(2, run.getARace(0).racersActive());				//racer begins racing, active racers should be 2
		
		r.getCurrentRun().getARace(0).startNRacers(1, r.getPool());
		assertEquals(2, r.getPool().getRacersAmount());			//pool size should be 2; a racer just started racing
		assertEquals(3, run.getARace(0).racersActive());				//racer begins racing, active racers should be 3
		
		r.getCurrentRun().getARace(0).startNRacers(1, r.getPool());
		assertEquals(1, r.getPool().getRacersAmount());			//pool size should be 1; a racer just started racing
		assertEquals(4, run.getARace(0).racersActive());				//racer begins racing, active racers should be 4
		
		r.getCurrentRun().getARace(0).startNRacers(1, r.getPool());
		assertEquals(0, r.getPool().getRacersAmount());			//pool size should be 0; a racer just started racing
		assertEquals(5, run.getARace(0).racersActive());				//racer begins racing, active racers should be 5
		
		//End actually starting Racers
		
		r.getCurrentRun().getARace(0).startNRacers(1, r.getPool());		//should do nothing; no racers left in pool
		assertEquals(0, r.getPool().getRacersAmount());			//pool size should still be 0
		assertEquals(5, run.getARace(0).racersActive());				//all racers active, active racers should be 5
		
		r.getCurrentRun().getARace(0).startNRacers(-1, r.getPool());		//should do nothing; no racers left and negative parameter
		assertEquals(0, r.getPool().getRacersAmount());			//pool size should still be 0
		assertEquals(5, run.getARace(0).racersActive());				//all racers active, active racers should be 5
		
	}
	@Test
	public void testFinishingRacers(){
		
		RaceEventsManager r = new RaceEventsManager();			//create the race event manager
		r.theseManySensors(2, 0, 0);
		r.setChannelSelected(1);
		r.CONN(false, true, false);
		r.setChannelSelected(2);
		r.CONN(false, true, false); 							//create and connect gate sensors to channels 1 and 2
		
		r.getPool().setRacersAmount(5); 						//create 5 racers in the pool, bibs 0-4
		r.setType(Run_Types.IND);
		r.setRunNbr(1);
		r.setNewRun();											//can now start a new run, ID 1
		
		Run run = r.getCurrentRun();							//set lanes to be the races(lanes) of the current run
		run.setNewRace(1);
		
		r.getCurrentRun().getARace(0).startNRacers(5, r.getPool());		//start all 5 racers
		
		assertEquals(0, r.getPool().getRacersAmount());			//all 5 racers have started, should be 0 in pool
		assertEquals(5, run.getARace(0).racersActive());				//all 5 racers have started should be 5 active racers
		
		//Begin finishing racers
	
		Racer r1 = run.getARace(0).finishRacer(false, r.getPool());
		assertEquals(1, r.getPool().getRacersAmount());			//first racer has finished, should be returned to pool
		assertFalse(r1.isDNF());								//r1 did not flag DNF
		assertEquals(0, r1.getBib());							//r1 was 1st racer to finish, 1st racer should have bib 0
		assertEquals(4, run.getARace(0).racersActive());				//1 of 5 racers finished, active racers should be 4
		
		Racer r2 = run.getARace(0).finishRacer(false, r.getPool());
		assertEquals(2, r.getPool().getRacersAmount());			//racer has finished, should be returned to pool; 2 total
		assertFalse(r2.isDNF());								//r2 did not flag DNF
		assertEquals(1, r2.getBib());							//r2 was 2nd racer to finish, 2nd racer should have bib 1
		assertEquals(3, run.getARace(0).racersActive());				//2 of 5 racers finished, active racers should be 3
		
		Racer r3 = run.getARace(0).finishRacer(false, r.getPool());
		assertEquals(3, r.getPool().getRacersAmount());			//racer has finished, should be returned to pool; 3 total
		assertFalse(r3.isDNF());								//r3 did not flag DNF
		assertEquals(2, r3.getBib());							//r3 was 3rd racer to finish, 3rd racer should have bib 2
		assertEquals(2, run.getARace(0).racersActive());				//3 of 5 racers finished, active racers should be 2
		
		Racer r4 = run.getARace(0).finishRacer(false, r.getPool());	//racer r4 is the 4th to finish, legally. 
		assertEquals(4, r.getPool().getRacersAmount());			//racer has finished, should be returned to pool; 4 total
		assertFalse(r4.isDNF());								//r4 did not flag DNF
		assertEquals(3, r4.getBib());							//r4 was 4th racer to finish, 4th racer should have bib 3
		assertEquals(1, run.getARace(0).racersActive());				//4 of 5 racers finished, active racers should be 1
		
		Racer r5 = run.getARace(0).finishRacer(true, r.getPool());	//racer r5 is the 5th(last) to finish, and does so illegally
		assertEquals(5, r.getPool().getRacersAmount());			//racer has finished, should be returned to pool; 5 total
		assertTrue(r5.isDNF());									//r5 did flag DNF
		assertEquals(4, r5.getBib());							//r5 was 5th racer to finish, 5th racer should have bib 4
		assertEquals(0, run.getARace(0).racersActive());				//5 of 5 racers finished, active racers should be 0
		
		//End finished racers
		
		assertEquals(5, r.getPool().getRacersAmount());			//pool size should remain 5
		assertEquals(0, run.getARace(0).racersActive());				//all racers still finished, active racers should be 0
		
		r1.setDNF(); 											//oh no! r1 was wearing illegal attire. disqualified.
		assertTrue(r1.isDNF());									//yep, he's done-zo
		r1.setDNF(); 											//hmm, another judge noticed too and also logged the DQ
		assertTrue(r1.isDNF());									//yep, he's double done-zo
		
	}
	@Test
	public void testRaceConclusion() {
		
		RaceEventsManager r = new RaceEventsManager();			//create the race event manager
		r.theseManySensors(2, 0, 0);
		r.setChannelSelected(1);
		r.CONN(false, true, false);
		r.setChannelSelected(2);
		r.CONN(false, true, false); 							//create and connect gate sensors to channels 1 and 2
		r.resetPool();
		
		r.getPool().setRacersAmount(10);						//create and add 10 racers to the pool, bibs 0-9
		r.setType(Run_Types.IND);
		r.setRunNbr(0);
		r.setNewRun();
		
		Run run = r.getCurrentRun();
		run.setNewRace(1);			
		
		r.getCurrentRun().getARace(0).startNRacers(10, r.getPool());		//start all the racers
		assertEquals(0, r.getPool().getRacersAmount());			//all racers have begun racing, pool should be empty
		assertEquals(10, run.getARace(0).racersActive());				//all racers have begun racing, active racers should be 10
		
		run.getARace(0).stopLastRace(r.getPool());							//stop all racers in the lane
		assertEquals(10, r.getPool().getRacersAmount());		//all racers have ceased racing; pool should be 10
		assertEquals(0, run.getARace(0).racersActive());				//all racers have ceased racing; active racers should be 0
		
		//RESET//
		r.resetPool();
		r.resetRun();											//reset the raceEventsManager and everything within
		//RESET//
		
		r.theseManySensors(2, 0, 0);
		r.setChannelSelected(1);
		r.CONN(false, true, false);
		r.setChannelSelected(2);
		r.CONN(false, true, false); 							//create and connect gate sensors to channels 1 and 2

		r.getPool().setRacersAmount(10);						//create and add 10 racers to the pool, bibs 0-9
		r.setType(Run_Types.IND);
		r.setRunNbr(1);
		r.setNewRun();
		run = r.getCurrentRun();									//set lanes to be the races(lanes) of the current run
		run.setNewRace(1);
		
		r.getCurrentRun().getARace(0).startNRacers(3, r.getPool());		//start 3 of 10 racers
		assertEquals(3, run.getARace(0).racersActive());				//3 racers should be active
		assertEquals(7, r.getPool().getRacersAmount());			//7 racers should be in the pool
		
		run.getARace(0).stopLastRace(r.getPool()); 								//stop all racers in the lane
		assertEquals(10, r.getPool().getRacersAmount());					//all racers have ceased racing; pool should be 10
		assertEquals(0, run.getARace(0).racersActive());				//all racers have ceased racing; active racers should be 0
		
		//RESET//
		r.resetPool();
		r.resetRun();											//reset the raceEventsManager and everything within
		//RESET//
		
		r.theseManySensors(2, 0, 0);
		r.setChannelSelected(1);
		r.CONN(false, true, false);
		r.setChannelSelected(2);
		r.CONN(false, true, false); 							//create and connect gate sensors to channels 1 and 2
		
		r.getPool().setRacersAmount(10);						//create and add 10 racers to the pool, bibs 0-9
		r.setType(Run_Types.IND);
		r.setRunNbr(2);
		r.setNewRun();											//start a new run with ID 3
		
		run = r.getCurrentRun();								//set lanes to be the races(lanes) of the current run
		run.setNewRace(1);
		
		assertEquals(0, run.getARace(0).racersActive());				//no racers have begun, active racers should be 0
		assertEquals(10, r.getPool().getRacersAmount());		//no racers have begun, pool size should be 10
		
		run.getARace(0).stopLastRace(r.getPool());							//stop all racers in the lane
		
		assertEquals(0, run.getARace(0).racersActive());				//no racers had begun, active racers should still be 0
		assertEquals(10, r.getPool().getRacersAmount());		//no racers had begun, pool size should still be 10
		
	}
	
	
}
