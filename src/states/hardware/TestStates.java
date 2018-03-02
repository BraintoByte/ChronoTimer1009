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


	@Before
	public void setUp(){



	}


	@Test
	public void TestButtons(){

		System.setIn(new ByteArrayInputStream("f".getBytes()));
		sim = new Simulator();
		//		sim.start();
		//Figured!
		UI ui = new UI(sim);
		Clock clock = new Clock();
		//		Idle idleState = new Idle(ui, new Scanner(System.in));
		ButtonHandler btns = new ButtonHandler();
		RaceEventsManager race = new RaceEventsManager();
		race.setChannelSelected(1);
		assertTrue(race.getChannelSelected() == 1);
		//		assertTrue(race.getAmountConnectedOnSelectedChannel() == 0);
		//		assertEquals(0, race.allPairedSensors().length);

		assertFalse(race.getCurrentChannel().isPairedToSensor());

		race.setChannelSelected(2);
		assertFalse(race.getCurrentChannel().isPairedToSensor());

		race.theseManySensors(1, 1, 1);

		race.setChannelSelected(1);
		race.CONN(true, false, false);
		//		assertEquals(1, race.allPairedSensors().length);
		//
		assertTrue(race.getCurrentChannel().isPairedToSensor());


		race.setChannelSelected(2);
		race.CONN(false, true, false);

		assertTrue(race.getCurrentChannel().isPairedToSensor());

		race.getCurrentChannel().unPairToSensor();

		assertFalse(race.getCurrentChannel().isPairedToSensor());

		race.CONN(false, false, true);

		assertTrue(race.getCurrentChannel().isPairedToSensor());



		//		assertEquals(2, race.allPairedSensors().length);
		//
		//		race.CONN(false, false, true);
		//		assertEquals(3, race.allPairedSensors().length);
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
		//		race.startNRacers(200);

		int[] bibs = new int[200];
		
		
		System.out.println("Please wait while the racers are made");
		
		bibs = makeTheRacersTest(race, bibs);
		assertEquals(race.racersPoolSize(), bibs.length);
		race.startNRacers(bibs.length);
		assertEquals(bibs.length, race.racersActive());
		assertEquals(race.racersActive(), 200);
		race.stopLastRace();
		assertEquals(race.racersPoolSize(), bibs.length);
		assertEquals(race.racersActive(), 0);

	}

	private int[] makeTheRacersTest(RaceEventsManager race, int[] bibs){

		for(int i = 0; i < bibs.length; i++){
			
			
			Random rand = new Random();
			int randomNum = rand.nextInt((5000 - 0) + 1) + 0;


			race.makeOneRacer(randomNum);

			bibs[i] = randomNum;

		}

		return bibs;

	}

}