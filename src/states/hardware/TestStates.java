package states.hardware;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import entitiesDynamic.Pool;
import entitiesDynamic.Racer;

import org.junit.*;

import entitiesStatic.Clock;
import entitiesStatic.ClockInterface;
import environment.RaceEventsManager;
import hardware.user.ButtonHandler;
import interfaces.UI;
import main.Simulator;
import states.State;

public class TestStates {

	private Simulator sim;
	private UI ui;
	private Clock clock;
	private RaceEventsManager raceManager;
	
	public void setUp(){

		System.setIn(new ByteArrayInputStream("f".getBytes()));
		sim = new Simulator();
		ui = new UI(sim);
		clock = new Clock();
		raceManager = new RaceEventsManager();
	}
	
	@Test
	public void TestChannels() {
		setUp();
		
		raceManager.theseManySensors(1, 1, 1);										// specify number of each sensor type
		
		raceManager.setChannelSelected(1);											// select channel 1
		assertTrue(raceManager.getChannelSelected() == 1);
		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		raceManager.CONN(true, false, false);										// connect eye sensor to channel 1
		assertTrue(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor
		
		raceManager.getCurrentChannel().unPairToSensor();							// disconnect sensor from channel 1
		
		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor
		
		raceManager.setChannelSelected(2);											// select channel 2
		assertTrue(raceManager.getChannelSelected() == 2);
		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor
		
		raceManager.CONN(false, true, false);										// connect gate sensor to channel 2
		assertTrue(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		raceManager.getCurrentChannel().unPairToSensor();							// disconnect sensor from channel 2

		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		raceManager.CONN(false, false, true);										// connect pad sensor to channel 2
		
		assertTrue(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor
		
		// test channels 3 & 4
		raceManager.setChannelSelected(3);											// select channel 3
		assertTrue(raceManager.getChannelSelected() == 3);
		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		raceManager.CONN(true, false, false);										// connect eye sensor to channel 3
		assertTrue(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor
		
		raceManager.getCurrentChannel().unPairToSensor();							// disconnect sensor from channel 3
		
		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor
		
		raceManager.setChannelSelected(4);											// select channel 4
		assertTrue(raceManager.getChannelSelected() == 4);
		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor
		
		raceManager.CONN(false, true, false);										// connect gate sensor to channel 4
		assertTrue(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		raceManager.getCurrentChannel().unPairToSensor();							// disconnect sensor from channel 4

		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		raceManager.CONN(false, false, true);										// connect pad sensor to channel 4
		
		assertFalse(raceManager.getCurrentChannel().isPairedToSensor());			// check if paired to sensor
	}
	
	public void test() {
		
//		State.setState(null);												// set initial state
//		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//		try {
//			clock.setTime(new Time(formatter.parse("12:00:11").getTime()));	// set system time to 12:00:11
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//		clock.clockStart();													// start clock
//		clock.setActive(true);
//
//		raceManager.propRace();													// setup pool for racers
//		int[] bibs = new int[200];
//		
//		System.out.println("Please wait while the racers are made, fireworks are about to start!");
//		
//		bibs = makeTheRacersTest(raceManager, bibs);								// populates array of bib#'s and adds racers to pool
//		assertEquals(raceManager.racersPoolSize(), bibs.length);					// checks pool size
//		raceManager.getRaces()[0].startNRacers(bibs.length);								// starts run for all racers in bibs[]
//		assertEquals(bibs.length, raceManager.racersActive());						// check active size
//		assertEquals(raceManager.racersActive(), 200);
//		raceManager.stopLastRace();												// finishes all racers in active and puts them back into pool
//		assertEquals(raceManager.racersPoolSize(), bibs.length);					// checks pool size
//		assertEquals(raceManager.racersActive(), 0);								// checks active size
//		
//		raceManager.startNRacers(1);												// only racer "001" starts
//		assertEquals(raceManager.racersActive(), 1);								// check active size
//		raceManager.finishRacer();													// racer "001" finishes
//		assertEquals(raceManager.racersActive(), 0);								// check active size
//		
//																			// tests CANCEL command
//		Racer racer = Pool.getPool().startRacer();							// gets next racer to start
//		Pool.getPool().returnCancel(racer);									// adds racer back into pool as the next racer
//		raceManager.startNRacers(1);												// only racer "001" starts
//		raceManager.CANCEL();														// racer "001" is canceled
//		assertEquals(racer, Pool.getPool().startRacer());					// checks if racer "001" is in pool as next racer to start
//		Pool.getPool().returnCancel(racer);
//		assertEquals(bibs.length, Pool.getPool().racersAmount());			// checks pool size
		
		
	}
	

	private int[] makeTheRacersTest(RaceEventsManager race, int[] bibs) {

		for(int i = 0; i < bibs.length; i++){
			
			
			Random rand = new Random();
			int randomNum = rand.nextInt((5000 - 0) + 1) + 0;


			race.makeOneRacer(randomNum);

			bibs[i] = randomNum;

		}

		return bibs;

	}

}