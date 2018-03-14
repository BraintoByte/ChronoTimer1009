package unitTesting;

import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import org.junit.Test;
import entitiesDynamic.Pool;
import entitiesDynamic.Racer;
import entitiesStatic.Clock;
import entitiesStatic.ClockInterface;
import environment.RaceEventsManager;
import interfaces.UI;
import main.Simulator;
import states.State;

public class SystemTest {

	private Simulator sim;
	private UI ui;
	private Clock clock;
	private RaceEventsManager raceManager;
	
	public void setUp(){

		//System.setIn(new ByteArrayInputStream("f".getBytes()));
		sim = new Simulator();
		ui = new UI(sim);
		clock = new Clock();
		raceManager = new RaceEventsManager();
	}
	
	@Test
	public void TestChannels() {
		setUp();
		
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
		
		assertTrue(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor
	}
	
	@Test
	public void TestClock() {
		setUp();
		
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
	
	@Test
	public void TestINDRun(){
//		setUp();
//		
//		System.out.println("Please wait while the test simulates an IND run");
//		
//		raceManager.propRace(2);														// setup pool for racers
//		raceManager.startNewRace();
//		int[] bibs = new int[200];
//		
//		bibs = makeTheRacersTest(raceManager, bibs);								// populates array of bib#'s and adds racers to pool
//		assertEquals(raceManager.racersPoolSize(), bibs.length);					// checks pool size
//		//System.out.print(raceManager.getRaces()[0]);
//		raceManager.getRaces()[0].startNRacers(bibs.length);						// starts run for all racers in bibs[]
//		assertEquals(bibs.length, raceManager.racersActive());						// check active size
//		assertEquals(raceManager.racersActive(), 200);
//		raceManager.getRaces()[0].finishEntireRace();								// finishes all racers in active and puts them back into pool
//		assertEquals(raceManager.racersPoolSize(), bibs.length);					// checks pool size
//		assertEquals(raceManager.racersActive(), 0);								// checks active size
//		
//		raceManager.startNRacers(1);												// only racer "001" starts
//		assertEquals(raceManager.racersActive(), 1);								// check active size
//		raceManager.getRaces()[0].finishEntireRace();								// racer "001" finishes
//		assertEquals(raceManager.racersActive(), 0);								// check active size
//		
//																					// tests CANCEL command
//		Racer racer = Pool.getPool().startRacer();									// gets next racer to start
//		Pool.getPool().returnCancel(racer);											// adds racer back into pool as the next racer
//		raceManager.startNRacers(1);												// only racer "001" starts
//		
//		// how to handle CANCEL? 
//		raceManager.CANCEL();														// racer "001" is canceled
//		
//		assertEquals(racer, Pool.getPool().startRacer());							// checks if racer "001" is in pool as next racer to start
//		Pool.getPool().returnCancel(racer);
//		assertEquals(bibs.length, Pool.getPool().racersAmount());					// checks pool size
	}
	
	private int[] makeTheRacersTest(RaceEventsManager raceMan, int[] bibs) {

		for(int i = 0; i < bibs.length; i++){
			
			Random rand = new Random();
			int randomNum = rand.nextInt((5000 - 0) + 1) + 0;

			raceMan.makeOneRacer(randomNum);

			bibs[i] = randomNum;
		}

		return bibs;

	}

}