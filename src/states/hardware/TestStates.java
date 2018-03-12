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


	@Test
	public void TestRace(){

		System.setIn(new ByteArrayInputStream("f".getBytes()));				// setup
		sim = new Simulator();
		UI ui = new UI(sim);
		Clock clock = new Clock();
		RaceEventsManager race = new RaceEventsManager();
		
		race.setChannelSelected(1);											// select channel 1
		assertTrue(race.getChannelSelected() == 1);
		assertFalse(race.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		race.setChannelSelected(2);											// select channel 2
		assertTrue(race.getChannelSelected() == 2);
		assertFalse(race.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		race.theseManySensors(1, 1, 1);										// specify number of each sensor type

		race.setChannelSelected(1);											// select channel 1
		race.CONN(true, false, false);										// connect eye sensor to channel 1
		assertTrue(race.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		race.setChannelSelected(2);											// select channel 2
		race.CONN(false, true, false);										// connect gate sensor to channel 2

		assertTrue(race.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		race.getCurrentChannel().unPairToSensor();							// disconnect sensor from channel 2

		assertFalse(race.getCurrentChannel().isPairedToSensor());			// check if paired to sensor

		race.CONN(false, false, true);										// connect pad sensor to channel 2

		assertTrue(race.getCurrentChannel().isPairedToSensor());			// check if paired to sensor
		State.setState(null);												// set initial state
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		try {
			clock.setTime(new Time(formatter.parse("12:00:11").getTime()));	// set system time to 12:00:11
		} catch (ParseException e) {
			e.printStackTrace();
		}

		clock.clockStart();													// start clock
		clock.setActive(true);

		race.propRace();													// setup pool for racers
		int[] bibs = new int[200];
		
		System.out.println("Please wait while the racers are made, fireworks are about to start!");
		
		bibs = makeTheRacersTest(race, bibs);								// creates array of racers and adds them to pool (waiting queue)
		assertEquals(race.racersPoolSize(), bibs.length);					// checks pool size
		race.startNRacers(bibs.length);										// starts run for all racers in bibs[]
		assertEquals(bibs.length, race.racersActive());						// check active size
		assertEquals(race.racersActive(), 200);
		race.stopLastRace();												// finishes all racers in active and puts them back into pool
		assertEquals(race.racersPoolSize(), bibs.length);					// checks pool size
		assertEquals(race.racersActive(), 0);								// checks active size
		
		race.startNRacers(1);												// only racer "001" starts
		assertEquals(race.racersActive(), 1);								// check active size
		race.finishRacer();													// racer "001" finishes
		assertEquals(race.racersActive(), 0);								// check active size
		
																			// tests CANCEL command
		Racer racer = Pool.getPool().startRacer();							// gets next racer to start
		Pool.getPool().returnCancel(racer);									// adds racer back into pool as the next racer
		race.startNRacers(1);												// only racer "001" starts
		race.CANCEL();														// racer "001" is canceled
		assertEquals(racer, Pool.getPool().startRacer());					// checks if racer "001" is in pool as next racer to start
		Pool.getPool().returnCancel(racer);
		assertEquals(bibs.length, Pool.getPool().racersAmount());			// checks pool size
		
		
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