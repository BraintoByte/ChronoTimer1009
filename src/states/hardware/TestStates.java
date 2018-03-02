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

		System.setIn(new ByteArrayInputStream("f".getBytes()));
		sim = new Simulator();
		UI ui = new UI(sim);
		Clock clock = new Clock();
		RaceEventsManager race = new RaceEventsManager();
		race.setChannelSelected(1);
		assertTrue(race.getChannelSelected() == 1);
		assertFalse(race.getCurrentChannel().isPairedToSensor());

		race.setChannelSelected(2);
		assertFalse(race.getCurrentChannel().isPairedToSensor());

		race.theseManySensors(1, 1, 1);

		race.setChannelSelected(1);
		race.CONN(true, false, false);
		assertTrue(race.getCurrentChannel().isPairedToSensor());


		race.setChannelSelected(2);
		race.CONN(false, true, false);

		assertTrue(race.getCurrentChannel().isPairedToSensor());

		race.getCurrentChannel().unPairToSensor();

		assertFalse(race.getCurrentChannel().isPairedToSensor());

		race.CONN(false, false, true);

		assertTrue(race.getCurrentChannel().isPairedToSensor());
		State.setState(null);
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		try {
			clock.setTime(new Time(formatter.parse("12:00:11").getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}


		clock.clockStart();
		clock.setActive(true);


		race.propRace();
		int[] bibs = new int[200];
		
		
		System.out.println("Please wait while the racers are made, fireworks are about to start!");
		
		bibs = makeTheRacersTest(race, bibs);
		assertEquals(race.racersPoolSize(), bibs.length);
		race.startNRacers(bibs.length);
		assertEquals(bibs.length, race.racersActive());
		assertEquals(race.racersActive(), 200);
		race.stopLastRace();
		assertEquals(race.racersPoolSize(), bibs.length);
		assertEquals(race.racersActive(), 0);
		
		
		race.startNRacers(1);
		assertEquals(race.racersActive(), 1);
		race.finishRacer();
		assertEquals(race.racersActive(), 0);
		
		Racer racer = Pool.getPool().startRacer();
		Pool.getPool().returnCancel(racer);
		race.startNRacers(1);
		race.CANCEL();
		assertEquals(racer, Pool.getPool().startRacer());
		Pool.getPool().returnCancel(racer);
		assertEquals(bibs.length, Pool.getPool().racersAmount());
		
		
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