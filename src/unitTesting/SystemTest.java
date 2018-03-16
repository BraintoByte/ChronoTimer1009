package unitTesting;

import static org.junit.Assert.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.junit.Test;
import entitiesStatic.Clock;
import environment.RaceEventsManager;
import states.State;

public class SystemTest {

	private  Clock clock = new Clock();
	private  RaceEventsManager raceManager = new RaceEventsManager();


	@Test
	public void TestChannels() {

		// tests channels 1 & 2
		raceManager.theseManySensors(2, 2, 2);										// specify number of each sensor type

		raceManager.setChannelSelected(1);											// select channel 1
		assertTrue(raceManager.getChannelSelected() == 1);
		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		raceManager.CONN(true, false, false);										// connect eye sensor to channel 1
		assertTrue(raceManager.getCurrentChannel().isPairedToSensor());				// check if paired to sensor

		raceManager.getCurrentChannel().unPairToSensor();							// disconnect sensor from channel 1

		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		raceManager.setChannelSelected(2);											// select channel 2
		assertTrue(raceManager.getChannelSelected() == 2);
		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		raceManager.CONN(false, true, false);										// connect gate sensor to channel 2
		assertTrue(raceManager.getCurrentChannel().isPairedToSensor());				// check if paired to sensor

		raceManager.getCurrentChannel().unPairToSensor();							// disconnect sensor from channel 2

		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		raceManager.CONN(false, false, true);										// connect pad sensor to channel 2

		assertTrue(raceManager.getCurrentChannel().isPairedToSensor());				// check if paired to sensor

		// test channels 3 & 4
		raceManager.setChannelSelected(3);											// select channel 3
		assertTrue(raceManager.getChannelSelected() == 3);
		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		raceManager.CONN(true, false, false);										// connect eye sensor to channel 3
		assertTrue(raceManager.getCurrentChannel().isPairedToSensor());				// check if paired to sensor

		raceManager.getCurrentChannel().unPairToSensor();							// disconnect sensor from channel 3

		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		raceManager.setChannelSelected(4);											// select channel 4
		assertTrue(raceManager.getChannelSelected() == 4);
		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		raceManager.CONN(false, true, false);										// connect gate sensor to channel 4
		assertTrue(raceManager.getCurrentChannel().isPairedToSensor());				// check if paired to sensor

		raceManager.getCurrentChannel().unPairToSensor();							// disconnect sensor from channel 4

		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		raceManager.CONN(false, false, true);										// connect pad sensor to channel 4

		assertTrue(raceManager.getCurrentChannel().isPairedToSensor());				// check if paired to sensor
	}

	// more extensive tests for the clock & clock interface will come later
	@Test
	public void TestClock() {

		State.setState(null);														// set initial state

		assertFalse(clock.isClockRunning());										// check if clock is running

		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		try {
			clock.setTime(new Time(formatter.parse("12:00:11").getTime()));			// set system time to 12:00:11

		} catch (ParseException e) {
			e.printStackTrace();
		}

		clock.clockStart();															// start clock
		clock.setActive(true);														// activate clock

		assertTrue(clock.isActive());												// check if active
		assertTrue(clock.isClockRunning());											// check if running

	}

	/*
	 *  Stress test for IND type run.
	 *  This test queues up 200 competitors and cycles them through the Race
	 */
	@Test
	public void TestINDRun(){
		System.out.println("Be prepared to wait 2 minutes for tests to finish...\n");
		System.out.println("Please wait while the test simulates an IND run...");
		raceManager.setChannelSelected(1);
		raceManager.propRace();													// setup pool for racers
		raceManager.startNewRace(1);
		int[] bibs = new int[200];

		bibs = makeTheRacersTest(raceManager, bibs);								// populates array of bib#'s and adds racers to pool
		assertEquals(raceManager.racersPoolSize(), bibs.length);					// checks pool size

		raceManager.getRaces()[0].startNRacers(bibs.length);						// starts run for all racers in bibs[]
		assertEquals(bibs.length, raceManager.getRaces()[0].racersActive());		// check active size
		assertEquals(raceManager.getRaces()[0].racersActive(), 200);
		raceManager.getRaces()[0].stopLastRace();								// finishes all racers in active and puts them back into pool
		assertEquals(raceManager.racersPoolSize(), bibs.length);					// checks pool size
		assertEquals(raceManager.getRaces()[0].racersActive(), 0);					// checks active size
		endrun();

	}

	/*
	 * 	Stress test for PARIND type run.
	 *  This test queues up 400 competitors and cycles them through the Race -
	 *  200 competitors go through lane 1 (channels 1 & 2)
	 *  200 competitors go through lane 2 (channels 3 & 4)
	 */
	@Test
	public void TestPARINDRun() {

		System.out.println("Please wait while the test simulates a PARIND run...");
		raceManager.setChannelSelected(1);
		raceManager.propRace();													// setup pool for racers
		raceManager.startNewRace(2);
		//raceManager.startNewRace(3);
		int[] bibs = new int[400];	

		bibs = makeTheRacersTest(raceManager, bibs);								// populates array of bib#'s and adds racers to pool

		// again issue with the pool!!! should not have one pool for all the races!!!

		assertEquals(raceManager.racersPoolSize(), bibs.length);					// checks pool size

		raceManager.getRaces()[0].startNRacers(200);								// starts run for 200 racers in lane 1 (chan 1 & 2)
		assertEquals(raceManager.getRaces()[0].racersActive(), 200);				// check lane 1 size

		assertEquals(raceManager.getRaces()[1].racersActive(), 0);					// other lane is empty

		raceManager.getRaces()[1].startNRacers(200);								// starts run for 200 racers in lane 1 (chan 1 & 2)
		assertEquals(raceManager.getRaces()[1].racersActive(), 200);				// check lane 2 size

		raceManager.getRaces()[0].stopLastRace();									// finishes all racers in lane 1
		assertEquals(raceManager.racersPoolSize(), 200);							// checks pool size

		assertEquals(raceManager.getRaces()[1].racersActive(), 200);				// check lane 2 size

		raceManager.getRaces()[1].stopLastRace();									// finishes all racers in lane 2

		assertEquals(raceManager.getRaces()[1].racersActive(), 0);					// checks lane 2 empty
		endrun();
		
	}
	
	private void endrun(){
		
		if(raceManager.getRaces() != null){

			for(int i = 0; i < raceManager.getRaces().length; i++){

				raceManager.getRaces()[i].stopLastRace();

			}
		}
	}

	private int[] makeTheRacersTest(RaceEventsManager raceMan, int[] bibs) {

		for(int i = 0; i < bibs.length; i++){

			raceMan.makeOneRacer(i);
			bibs[i] = i;
		}

		return bibs;

	}

}