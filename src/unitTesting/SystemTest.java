package unitTesting;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;

import org.junit.Test;
import entitiesStatic.Clock;
import entitiesStatic.ClockInterface;
import environment.RaceEventsManager;
import main.Simulator;
import states.State;

/**
 * @author Andy & Matt
 * Tests some of the system level tests like connecting sensors to channels and ensuring the clock works.
 * TestINDRun() and TestPARINDRun are stress tests that ensure the two event types work properly.
 */
public class SystemTest {

	private  RaceEventsManager raceManager = new RaceEventsManager();

	@Test
	public void testClockTime(){
		
		Clock clock = new Clock();
		System.out.println("NOTE: SOMETIMES THE CLOCK MIGHT BE A FEW SS OFF ALTHOUGH IT WILL SEEM LIKE"
				+ "LIKE IT'S 1 SECOND OFF, THIS HAPPENS BECAUSE IF YOU ARE AT 12:00:00.59 and the test hits at 12:00:01.00"
				+ "\n this can also be because the thread sleeps a few nanoseconds before!"
				+ "\nIT WILL COME OUT WRONG, PLEASE LOOK AT THE TEST CASE!");

		clock.setTime(new Time((new Date().getTime())));

		clock.clockStart();
		
		
		try {
			Thread.sleep(800);
		} catch (InterruptedException e2) {}


		assertEquals(new Time((new Date().getTime())) + "", ClockInterface.getCurrentTimeFormatted() + "");

		DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SS");
		try {

			clock.setTime(new Time(formatter.parse("12:00:11.00").getTime()));


			System.out.println("TESTING 10 SECONDS ELAPSE, PLEASE WAIT");
			
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {}
			
			assertTrue((new Time(formatter.parse("12:00:19.00").getTime()) + "").equals(ClockInterface.getCurrentTimeFormatted() + "")
					|| (new Time(formatter.parse("12:00:20.00").getTime()) + "").equals(ClockInterface.getCurrentTimeFormatted() + "")
					|| (new Time(formatter.parse("12:00:21.00").getTime()) + "").equals(ClockInterface.getCurrentTimeFormatted() + ""));

		} catch (ParseException e1) {}
	}



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

		Clock clock = new Clock();
		State.setState(null);														// set initial state

		assertFalse(clock.isClockRunning());	

		clock.clockStart();															// start clock
		clock.setActive(true);														// activate clock

		assertTrue(clock.isActive());												// check if active

	}

	/*
	 *  Stress test for IND type run.
	 *  This test queues up 200 competitors and cycles them through the Race
	 */
		@Test
		public void TestINDRun(){
//			System.out.println("Be prepared to wait 2 minutes for tests to finish...\n");
//			System.out.println("Please wait while the test simulates an IND run...");
//			raceManager.setChannelSelected(1);
//			raceManager.propRace();													// setup pool for racers
//			raceManager.startNewRace(1);
//			raceManager.theseManySensors(4, 4, 4);
//			connectSensors();
//			int[] bibs = new int[200];
//	
//			bibs = makeTheRacersTest(raceManager, bibs);								// populates array of bib#'s and adds racers to pool
//			assertEquals(raceManager.racersPoolSize(), bibs.length);					// checks pool size
//	
//			raceManager.getRaces()[0].startNRacers(bibs.length);						// starts run for all racers in bibs[]
//			assertEquals(bibs.length, raceManager.getRaces()[0].racersActive());		// check active size
//			assertEquals(raceManager.getRaces()[0].racersActive(), 200);
//			raceManager.getRaces()[0].stopLastRace();								// finishes all racers in active and puts them back into pool
//			assertEquals(raceManager.racersPoolSize(), bibs.length);					// checks pool size
//			assertEquals(raceManager.getRaces()[0].racersActive(), 0);					// checks active size
//			endrun();
	
		}
	
		/*
		 * 	Stress test for PARIND type run.
		 *  This test queues up 400 competitors and cycles them through the Race -
		 *  200 competitors go through lane 1 (channels 1 & 2)
		 *  200 competitors go through lane 2 (channels 3 & 4)
		 */
		@Test
		public void TestPARINDRun() {
	
//			System.out.println("Please wait while the test simulates a PARIND run...");
//			raceManager.setChannelSelected(1);
//			raceManager.propRace();														// setup pool for racers
//			assertNull(raceManager.getRaces());
//			raceManager.startNewRace(1);
//			assertNull(raceManager.getRaces()[1]);
//			raceManager.setChannelSelected(3);
//			raceManager.startNewRace(2);
//			raceManager.theseManySensors(4, 4, 4);
//			connectSensors();
//			//raceManager.startNewRace(3);
//			int[] bibs = new int[400];	
//	
//			bibs = makeTheRacersTest(raceManager, bibs);								// populates array of bib#'s and adds racers to pool
//	
//			raceManager.getRaces()[0].startNRacers(200);								// starts run for 200 racers in lane 1 (chan 1 & 2)
//			assertEquals(raceManager.getRaces()[0].racersActive(), 200);				// check lane 1 size
//	
//			
//			raceManager.getRaces()[1].startNRacers(0);
//			assertEquals(raceManager.getRaces()[1].racersActive(), 0);					// other lane is empty
//	
//			raceManager.getRaces()[1].startNRacers(200);								// starts run for 200 racers in lane 1 (chan 1 & 2)
//			assertEquals(raceManager.getRaces()[1].racersActive(), 200);				// check lane 2 size
//	
//			raceManager.getRaces()[0].stopLastRace();									// finishes all racers in lane 1
//			assertEquals(raceManager.racersPoolSize(), 400);							// checks pool size
//	
//			assertEquals(raceManager.getRaces()[1].racersActive(), 200);				// check lane 2 size
//	
//			raceManager.getRaces()[1].stopLastRace();									// finishes all racers in lane 2
//	
//			assertEquals(raceManager.getRaces()[1].racersActive(), 0);					// checks lane 2 empty
//			endrun();
//	
		}

	private void endrun(){

//		if(raceManager.getRaces() != null){
//
//			for(int i = 0; i < raceManager.getRaces().length; i++){
//
//				if(raceManager.getRaces()[i] != null){
//
//					raceManager.getRaces()[i].stopLastRace();
//
//				}
//			}
//		}
	}

//	private int[] makeTheRacersTest(RaceEventsManager raceMan, int[] bibs) {
//
//		for(int i = 0; i < bibs.length; i++){
//
//			raceMan.makeOneRacer(i);
//			bibs[i] = i;
//		}
//
//		return bibs;
//
//	}

	private void connectSensors(){

		for(int i = 1; i < 5; i++){

			raceManager.setChannelSelected(i);

			if(!raceManager.getCurrentChannel().isPairedToSensor()){

				conn("CONN EYE " + i);

			}
		}
	}

	private void conn(String str){


		try{

			int channelSelected = Integer.parseInt(str.split("\\s")[2]);

			if(channelSelected > 0 && channelSelected <= 4){

				raceManager.setChannelSelected(channelSelected);

				if(!raceManager.getCurrentChannel().isPairedToSensor()){

					raceManager.CONN(str.split("\\s")[1].equalsIgnoreCase("eye"), 
							str.split("\\s")[1].equalsIgnoreCase("gate"), str.split("\\s")[1].equalsIgnoreCase("pad"));
				}
			}

		}catch(InputMismatchException ex){

			ex.printStackTrace();

		}
	}

}